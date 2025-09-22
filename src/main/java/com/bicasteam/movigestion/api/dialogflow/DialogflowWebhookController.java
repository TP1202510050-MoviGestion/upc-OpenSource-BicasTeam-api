// Archivo: src/main/java/com/bicasteam/movigestion/api/dialogflow/DialogflowWebhookController.java

package com.bicasteam.movigestion.api.dialogflow;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/dialogflow-webhook")
public class DialogflowWebhookController {
    private final String API_BASE_URL = "https://app-250626000818.azurewebsites.net/api";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<Map<String, Object>> handleWebhook(@RequestBody Map<String, Object> request) {
        Map<String, Object> queryResult = (Map<String, Object>) request.get("queryResult");
        String intentName = ((Map<String, String>) queryResult.get("intent")).get("displayName");
        Map<String, Object> userPayload = getUserPayload(queryResult);

        Object fulfillmentResponse;
        try {
            switch (intentName) {
                case "Evento-Bienvenida-App":
                    fulfillmentResponse = handleWelcomeIntent(userPayload);
                    break;
                case "ConsultarRutas":
                    fulfillmentResponse = handleConsultarRutas(userPayload);
                    break;
                case "ConsultarReportes":
                    fulfillmentResponse = handleConsultarReportes(userPayload);
                    break;
                case "ConsultarVehiculos":
                    fulfillmentResponse = handleConsultarVehiculos(userPayload);
                    break;
                case "AnalisisGeneral":
                    fulfillmentResponse = handleAnalisisGeneral(userPayload);
                    break;
                default:
                    fulfillmentResponse = "Intent no reconocido: " + intentName;
                    break;
            }
        } catch (Exception e) {
            fulfillmentResponse = "Lo siento, ocurrió un error interno al procesar tu solicitud.";
            e.printStackTrace();
        }

        Map<String, Object> response = createDialogflowResponse(fulfillmentResponse);
        return ResponseEntity.ok(response);
    }

    private Map<String, Object> getUserPayload(Map<String, Object> queryResult) {
        if (queryResult.containsKey("outputContexts") && queryResult.get("outputContexts") instanceof List) {
            List<Map<String, Object>> outputContexts = (List<Map<String, Object>>) queryResult.get("outputContexts");

            // Busca primero el contexto de sesión.
            Optional<Map<String, Object>> sessionContext = outputContexts.stream()
                    .filter(c -> c.get("name").toString().endsWith("/contexts/sesion-usuario"))
                    .findFirst();

            if (sessionContext.isPresent() && sessionContext.get().containsKey("parameters")) {
                System.out.println("[DEBUG] Payload encontrado en el contexto 'sesion-usuario'.");
                return (Map<String, Object>) sessionContext.get().get("parameters");
            }

            // Si no, busca el contexto del evento de bienvenida.
            Optional<Map<String, Object>> welcomeContext = outputContexts.stream()
                    .filter(c -> c.get("name").toString().contains("/contexts/evento-bienvenida-app"))
                    .findFirst();

            if (welcomeContext.isPresent() && welcomeContext.get().containsKey("parameters")) {
                System.out.println("[DEBUG] Payload encontrado en el contexto 'evento-bienvenida-app'.");
                return (Map<String, Object>) welcomeContext.get().get("parameters");
            }
        }

        // Si no se encuentra en ningún contexto, intenta con queryParams como último recurso
        if (queryResult.containsKey("queryParams") && queryResult.get("queryParams") instanceof Map) {
            Map<String, Object> queryParams = (Map<String, Object>) queryResult.get("queryParams");
            if (queryParams.containsKey("payload") && queryParams.get("payload") instanceof Map) {
                System.out.println("[DEBUG] Payload encontrado en 'queryParams.payload'.");
                return (Map<String, Object>) queryParams.get("payload");
            }
        }

        System.out.println("[DEBUG] No se encontró payload en ningún contexto ni en queryParams. Devolviendo mapa vacío.");
        return Collections.emptyMap();
    }


    private Map<String, Object> handleWelcomeIntent(Map<String, Object> userPayload) {
        String name = userPayload.getOrDefault("name", "").toString();
        String lastName = userPayload.getOrDefault("lastName", "").toString();
        String type = userPayload.getOrDefault("type", "").toString();
        String fullName = (name + " " + lastName).trim();

        String welcomeMessage;
        if ("Gerente".equals(type)) {
            welcomeMessage = String.format("¡Hola, %s! Soy tu asistente virtual. ¿Necesitas información de las rutas, vehículos, reportes o un análisis general de operaciones?", fullName);
        } else {
            welcomeMessage = String.format("¡Hola, %s! Soy tu asistente virtual. ¿Necesitas información sobre tu ruta, tu vehículo, o deseas hacer un reporte?", fullName);
        }

        Map<String, Object> outputContext = Map.of(
                "name", "projects/green-source-462801-q9/agent/sessions/flutter_session/contexts/sesion-usuario",
                "lifespanCount", 10,
                "parameters", userPayload
        );

        // El handle de bienvenida sigue devolviendo un Map con el texto y el contexto
        return Map.of("text", welcomeMessage, "context", outputContext);
    }


    private boolean dateFilter(Map<String, Object> item, String periodo, String dateKey) {
        if (periodo == null || periodo.isEmpty()) {
            return true; // Si no hay periodo, no se filtra
        }

        Object dateValue = item.get(dateKey);
        if (dateValue == null) {
            return false; // Si el item no tiene la fecha, se excluye
        }

        String dateString = dateValue.toString();
        LocalDate itemDate;
        try {
            // Intenta parsear la fecha, que viene en formato ISO (ej: "2024-10-18T00:00:00")
            itemDate = LocalDateTime.parse(dateString.substring(0, 19)).toLocalDate();
        } catch (Exception e) {
            return false; // Si el formato no es válido, se excluye
        }

        LocalDate now = LocalDate.now();

        switch (periodo.toLowerCase()) {
            case "hoy":
                return itemDate.isEqual(now);
            case "semana":
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                return itemDate.get(weekFields.weekOfYear()) == now.get(weekFields.weekOfYear()) &&
                        itemDate.getYear() == now.getYear();
            case "mes":
                return itemDate.getMonth() == now.getMonth() && itemDate.getYear() == now.getYear();
            default:
                return true;
        }
    }
    private String formatDateTime(Object dateTimeObj, DateTimeFormatter formatter) {
        if (dateTimeObj == null) {
            return "N/A";
        }
        try {
            String dateTimeStr = dateTimeObj.toString();
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr.substring(0, 19));
            return dateTime.format(formatter);
        } catch (Exception e) {
            return "Fecha inválida";
        }
    }

    private Object handleConsultarRutas(Map<String, Object> userPayload) throws Exception {
        String type = userPayload.getOrDefault("type", "").toString();
        String periodo = userPayload.getOrDefault("periodo", "").toString();
        List<Map<String, Object>> allRoutes = fetchAllData("/routes");
        List<Map<String, Object>> filteredRoutes;

        if ("Gerente".equals(type)) {
            Map<String, String> companyInfo = getCompanyInfoForManager(userPayload);
            String companyName = companyInfo.get("companyName");
            if (companyName.isEmpty()) return "No pude verificar tu empresa.";
            filteredRoutes = allRoutes.stream()
                    .filter(r -> companyName.equalsIgnoreCase(String.valueOf(r.get("companyName"))))
                    .filter(r -> dateFilter(r, periodo, "createdAt"))
                    .collect(Collectors.toList());
        } else { // Transportista
            String fullName = (userPayload.getOrDefault("name", "") + " " + userPayload.getOrDefault("lastName", "")).trim();
            if (fullName.isEmpty()) return "No pude identificar tu perfil.";
            filteredRoutes = allRoutes.stream()
                    .filter(r -> fullName.equalsIgnoreCase(String.valueOf(r.get("driverName"))))
                    .filter(r -> dateFilter(r, periodo, "createdAt"))
                    .collect(Collectors.toList());
        }

        if (filteredRoutes.isEmpty()) return "No se encontraron rutas para el periodo especificado.";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        List<Map<String, Object>> items = filteredRoutes.stream().map(r -> Map.of(
                "Título", r.get("customer"),
                "Subtítulo", r.get("nameRoute"),
                "Detalles", List.of(
                        Map.of("key", "Tipo", "value", r.get("type"), "icon", "type"),
                        Map.of("key", "Estado", "value", r.get("status"), "icon", "status"),
                        Map.of("key", "Conductor", "value", r.get("driverName"), "icon", "driver"),
                        Map.of("key", "Salida", "value", formatDateTime(r.get("departureTime"), dtf), "icon", "time"),
                        Map.of("key", "Llegada", "value", formatDateTime(r.get("arrivalTime"), dtf), "icon", "time"),
                        Map.of("key", "Creada", "value", formatDateTime(r.get("createdAt"), dtf), "icon", "date")
                )
        )).collect(Collectors.toList());

        return Map.of("type", "list_card", "title", "Resultados de Rutas", "items", items);
    }

    private Object handleConsultarReportes(Map<String, Object> userPayload) throws Exception {
        String type = userPayload.getOrDefault("type", "").toString();
        String periodo = userPayload.getOrDefault("periodo", "").toString();
        List<Map<String, Object>> allReports = fetchAllData("/reports");
        List<Map<String, Object>> filteredReports;

        if ("Gerente".equals(type)) {
            Map<String, String> companyInfo = getCompanyInfoForManager(userPayload);
            String companyName = companyInfo.get("companyName");
            if (companyName.isEmpty()) return "No pude verificar tu empresa.";
            filteredReports = allReports.stream()
                    .filter(r -> companyName.equalsIgnoreCase(String.valueOf(r.get("companyName"))))
                    .filter(r -> dateFilter(r, periodo, "createdAt"))
                    .collect(Collectors.toList());
        } else { // Transportista
            String fullName = (userPayload.getOrDefault("name", "") + " " + userPayload.getOrDefault("lastName", "")).trim();
            if (fullName.isEmpty()) return "No pude identificar tu perfil.";
            filteredReports = allReports.stream()
                    .filter(r -> fullName.equalsIgnoreCase(String.valueOf(r.get("driverName"))))
                    .filter(r -> dateFilter(r, periodo, "createdAt"))
                    .collect(Collectors.toList());
        }

        if (filteredReports.isEmpty()) return "No se encontraron reportes para el periodo especificado.";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Map<String, Object>> items = filteredReports.stream().map(r -> Map.of(
                "Título", r.get("type"),
                "Subtítulo", "Por: " + r.get("driverName"),
                "Detalles", List.of(
                        Map.of("key", "Estado", "value", r.get("status"), "icon", "status"),
                        Map.of("key", "Fecha", "value", formatDateTime(r.get("createdAt"), dtf), "icon", "date")
                )
        )).collect(Collectors.toList());

        return Map.of("type", "list_card", "title", "Resultados de Reportes", "items", items);
    }

    private Object handleConsultarVehiculos(Map<String, Object> userPayload) throws Exception {
        String type = userPayload.getOrDefault("type", "").toString();
        String periodo = userPayload.getOrDefault("periodo", "").toString(); // Asumiendo que los vehículos tienen 'createdAt'
        List<Map<String, Object>> allVehicles = fetchAllData("/vehicles");
        List<Map<String, Object>> filteredVehicles;

        if ("Gerente".equals(type)) {
            Map<String, String> companyInfo = getCompanyInfoForManager(userPayload);
            String companyName = companyInfo.get("companyName");
            if (companyName.isEmpty()) return "No pude verificar tu empresa.";
            filteredVehicles = allVehicles.stream()
                    .filter(v -> companyName.equalsIgnoreCase(String.valueOf(v.get("companyName"))))
                    .collect(Collectors.toList());
        } else { // Transportista
            String fullName = (userPayload.getOrDefault("name", "") + " " + userPayload.getOrDefault("lastName", "")).trim();
            if (fullName.isEmpty()) return "No pude identificar tu perfil.";
            filteredVehicles = allVehicles.stream()
                    .filter(v -> fullName.equalsIgnoreCase(String.valueOf(v.get("driverName"))))
                    .collect(Collectors.toList());
        }

        if (filteredVehicles.isEmpty()) return "No se encontraron vehículos que coincidan con tu búsqueda.";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Map<String, Object>> items = filteredVehicles.stream().map(v -> Map.of(
                "Título", v.get("licensePlate"),
                "Subtítulo", v.get("brand") + " " + v.get("model"),
                "Detalles", List.of(
                        Map.of("key", "Estado", "value", v.get("status"), "icon", "status"),
                        Map.of("key", "Conductor", "value", v.get("driverName"), "icon", "driver"),
                        Map.of("key", "Últ. Inspección", "value", formatDateTime(v.get("lastTechnicalInspectionDate"), dtf), "icon", "date"),
                        Map.of("key", "Próx. Taller", "value", formatDateTime(v.get("dateToGoTheWorkshop"), dtf), "icon", "date"),
                        Map.of("key", "Últ. Telemetría", "value", formatDateTime(v.get("lastTelemetryTimestamp"), dtf), "icon", "telemetry")
                )
        )).collect(Collectors.toList());

        return Map.of("type", "list_card", "title", "Resultados de Vehículos", "items", items);
    }


    // --- MÉTODO ACTUALIZADO PARA DEVOLVER JSON ---
    private Map<String, Object> handleAnalisisGeneral(Map<String, Object> userPayload) throws Exception {
        String type = userPayload.getOrDefault("type", "").toString();
        List<Map<String, Object>> routes, reports, vehicles;

        // La lógica de filtrado se mantiene igual
        if ("Gerente".equals(type)) {
            Map<String, String> companyInfo = getCompanyInfoForManager(userPayload);
            String companyName = companyInfo.get("companyName");
            if (companyName.isEmpty()) {
                // Devolvemos un payload de error si no podemos identificar la empresa
                return Map.of("type", "error", "message", "No pude verificar tu empresa para darte un resumen.");
            }
            routes = fetchAllData("/routes").stream().filter(r -> companyName.equalsIgnoreCase(String.valueOf(r.get("companyName")))).collect(Collectors.toList());
            reports = fetchAllData("/reports").stream().filter(r -> companyName.equalsIgnoreCase(String.valueOf(r.get("companyName")))).collect(Collectors.toList());
            vehicles = fetchAllData("/vehicles").stream().filter(v -> companyName.equalsIgnoreCase(String.valueOf(v.get("companyName")))).collect(Collectors.toList());
        } else { // Transportista
            String fullName = (userPayload.getOrDefault("name", "") + " " + userPayload.getOrDefault("lastName", "")).trim();
            if (fullName.isEmpty()) {
                return Map.of("type", "error", "message", "No pude identificar tu perfil para darte un resumen.");
            }
            routes = fetchAllData("/routes").stream().filter(r -> fullName.equalsIgnoreCase(String.valueOf(r.get("driverName")))).collect(Collectors.toList());
            reports = fetchAllData("/reports").stream().filter(r -> fullName.equalsIgnoreCase(String.valueOf(r.get("driverName")))).collect(Collectors.toList());
            vehicles = fetchAllData("/vehicles").stream().filter(v -> fullName.equalsIgnoreCase(String.valueOf(v.get("driverName")))).collect(Collectors.toList());
        }


        final int maxSize = 2;

        // --- CONSTRUCCIÓN DEL JSON ESTRUCTURADO ---
        List<Map<String, Object>> sections = new ArrayList<>();

        // Sección de Rutas
        long rutasRegulares = routes.stream().filter(r -> "regular".equalsIgnoreCase(String.valueOf(r.get("type")))).count();
        long rutasEventos = routes.stream().filter(r -> "evento".equalsIgnoreCase(String.valueOf(r.get("type")))).count();
        sections.add(Map.of(
                "title", "Asignación de Rutas",
                "icon", "route",
                "items", List.of(
                        Map.of("text", "Total: " + routes.size() + " rutas", "icon", "total"),
                        Map.of("text", rutasRegulares + " de tipo regular", "icon", "regular"),
                        Map.of("text", rutasEventos + " de tipo eventos", "icon", "event")
                )
        ));

        // --- INICIO DE LA CORRECCIÓN ---
        // Sección de Reportes Recientes
        List<Map<String, Object>> reportItems = reports.stream()
                .limit(maxSize)
                .map(r -> {
                    // Creamos un mapa mutable para poder especificar los tipos
                    Map<String, Object> item = new HashMap<>();
                    item.put("text", String.format("%s – %s", r.get("type"), r.get("driverName")));
                    item.put("icon", r.get("type").toString().toLowerCase().contains("accidente") ? "accident" : "traffic");
                    return item;
                })
                .collect(Collectors.toList());

        sections.add(Map.of(
                "title", "Reportes Recientes",
                "icon", "report",
                "items", reportItems
        ));

        // Sección de Ubicación de Vehículos
        List<Map<String, Object>> vehicleItems = vehicles.stream()
                .limit(6)
                .map(v -> {
                    Map<String, Object> item = new HashMap<>();
                    String location = "Ubicación no encontrada";
                    if (v.get("lastLatitude") != null) {
                        location = "Ubicación registrada";
                    }
                    item.put("text", String.format("%s → %s", v.get("licensePlate"), location));
                    item.put("icon", v.get("lastLatitude") != null ? "vehicle_ok" : "vehicle_warning");
                    return item;
                })
                .collect(Collectors.toList());

        sections.add(Map.of(
                "title", "Última Ubicación de Vehículos",
                "icon", "location",
                "items", vehicleItems
        ));
        return Map.of(
                "type", "summary_card",
                "sections", sections
        );
    }

    // --- HELPERS ---

    private List<Map<String, Object>> fetchAllData(String endpoint) throws Exception {
        String url = API_BASE_URL + endpoint;
        String jsonResponse = restTemplate.getForObject(url, String.class);
        return objectMapper.readValue(jsonResponse, new TypeReference<List<Map<String, Object>>>(){});
    }

    private String formatSimpleList(List<Map<String, Object>> items, String key, int limit) {
        if (items == null || items.isEmpty()) return "";
        return items.stream()
                .limit(limit)
                .map(item -> "- " + item.get(key))
                .collect(Collectors.joining("\n"));
    }


    private Map<String, Object> createDialogflowResponse(Object fulfillmentResponse) {
        Map<String, Object> responseBody;

        if (fulfillmentResponse instanceof String) {
            // Caso 1: La respuesta es un simple texto.
            responseBody = Map.of("text", Map.of("text", List.of(fulfillmentResponse)));
        } else if (fulfillmentResponse instanceof Map && ((Map) fulfillmentResponse).containsKey("text")) {
            // Caso 2: Es el resultado del saludo, que incluye texto y un contexto.
            Map<String, Object> welcomeResult = (Map<String, Object>) fulfillmentResponse;
            Map<String, Object> response = new HashMap<>();
            response.put("fulfillmentMessages", Collections.singletonList(Map.of("text", Map.of("text", List.of(welcomeResult.get("text"))))));
            response.put("outputContexts", Collections.singletonList(welcomeResult.get("context")));
            return response;
        } else {
            // Caso 3: La respuesta es un payload estructurado (ej. la tarjeta de resumen).
            responseBody = Map.of("payload", fulfillmentResponse);
        }

        return Map.of("fulfillmentMessages", List.of(responseBody));
    }

    private Optional<Map<String, Object>> findProfileByName(String name, String lastName) {
        try {
            List<Map<String, Object>> profiles = fetchAllData("/profiles");
            String targetFullName = (name + " " + lastName).trim().toLowerCase();
            return profiles.stream()
                    .filter(p -> {
                        String currentFullName = (p.getOrDefault("name", "").toString() + " " + p.getOrDefault("lastName", "").toString()).trim().toLowerCase();
                        return currentFullName.equals(targetFullName);
                    })
                    .findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Map<String, String> getCompanyInfoForManager(Map<String, Object> userPayload) throws Exception {
        String companyName = userPayload.getOrDefault("companyName", "").toString();
        String companyRuc = userPayload.getOrDefault("companyRuc", "").toString();

        // Si los datos ya vienen en el payload de Flutter, los usamos directamente.
        if (!companyName.isEmpty() && !companyRuc.isEmpty()) {
            return Map.of("companyName", companyName, "companyRuc", companyRuc);
        }

        // Si no vienen, como respaldo, buscamos el perfil del gerente en la base de datos.
        // Esto solo debería ocurrir en la primera petición (el evento de bienvenida).
        String userName = userPayload.getOrDefault("name", "").toString();
        String userLastName = userPayload.getOrDefault("lastName", "").toString();

        if (userName.isEmpty() || userLastName.isEmpty()) {
            return Map.of("companyName", "", "companyRuc", ""); // No se puede buscar
        }

        List<Map<String, Object>> profiles = fetchAllData("/profiles");
        String targetFullName = (userName + " " + userLastName).trim().toLowerCase();

        Optional<Map<String, Object>> managerProfileOpt = profiles.stream()
                .filter(p -> {
                    String currentFullName = (p.getOrDefault("name", "").toString() + " " + p.getOrDefault("lastName", "").toString()).trim().toLowerCase();
                    return "Gerente".equals(p.get("type")) && currentFullName.equals(targetFullName);
                })
                .findFirst();

        if (managerProfileOpt.isPresent()) {
            Map<String, Object> managerProfile = managerProfileOpt.get();
            return Map.of(
                    "companyName", managerProfile.getOrDefault("companyName", "").toString(),
                    "companyRuc", managerProfile.getOrDefault("companyRuc", "").toString()
            );
        }

        // Si no se encuentra, devuelve vacío.
        return Map.of("companyName", "", "companyRuc", "");
    }
}