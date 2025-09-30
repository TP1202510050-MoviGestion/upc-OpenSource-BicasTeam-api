pipeline {
    // Definimos el agente (máquina) donde se ejecutará el pipeline.
    // Necesita tener JDK 17 y Maven instalados.
    agent any

    // Variables de entorno con tus datos específicos.
    environment {
        AZURE_APP_NAME = 'app-250626000818'
        AZURE_RESOURCE_GROUP = 'rg-app-250626000818' // Confirmado desde tu captura
        AZURE_CREDENTIALS_ID = 'azure-service-principal' // Este es el ID que crearás en Jenkins
    }

    stages {
        stage('1. Checkout Code') {
            steps {
                echo 'Clonando el repositorio de la API...'
                checkout scm
            }
        }

        stage('2. Build & White-Box Tests') {
            steps {
                echo 'Ejecutando pruebas de caja blanca (unitarias y de integración)...'
                // Comando para limpiar, testear y empaquetar la aplicación
                sh './mvnw clean package'
            }
        }

        stage('3. Archive Artifacts') {
            steps {
                echo 'Archivando el artefacto JAR para el despliegue...'
                // Guarda el JAR generado para que la etapa de despliegue pueda usarlo
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('4. Deploy to Azure') {
            steps {
                echo "Desplegando en Azure Web App: ${AZURE_APP_NAME}..."
                // Este paso utiliza el plugin de Azure CLI en Jenkins
                // y se autentica usando las credenciales que crearás.
                withCredentials([azureServicePrincipal(credentialsId: AZURE_CREDENTIALS_ID,
                                                       subscriptionId: 'e1a73935-816a-4942-97fe-cfdcee794e6f', // TU ID DE SUSCRIPCIÓN
                                                       tenantId: '0e0cb060-09ad-49f5-a005-68b9b49aa1f6')]) {     // TU TENANT ID
                    sh """
                        az webapp deploy --resource-group ${AZURE_RESOURCE_GROUP} --name ${AZURE_APP_NAME} --src-path target/*.jar --type jar
                    """
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado.'
            cleanWs()
        }
    }
}