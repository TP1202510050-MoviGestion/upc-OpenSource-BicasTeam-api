package com.bicasteam.movigestion.api.profiles.interfaces.rest;

import com.bicasteam.movigestion.api.profiles.domain.model.aggregates.Profile;
import com.bicasteam.movigestion.api.profiles.domain.model.commands.CreateProfileCommand;
import com.bicasteam.movigestion.api.profiles.domain.repositories.ProfileRepository;
import com.bicasteam.movigestion.api.profiles.domain.services.ProfileCommandService;
import com.bicasteam.movigestion.api.profiles.domain.services.ProfileQueryService;
import com.bicasteam.movigestion.api.profiles.interfaces.rest.resources.CreateProfileResource;
import com.bicasteam.movigestion.api.profiles.interfaces.rest.resources.ProfileResource;
import com.bicasteam.movigestion.api.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import com.bicasteam.movigestion.api.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;
    private final ProfileRepository profileRepository; // Agrega esto

    public ProfileController(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService,
                             ProfileRepository profileRepository) { // Agrega aquí también
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
        this.profileRepository = profileRepository; // Asigna aquí también
    }

    @PostMapping
    public ResponseEntity<ProfileResource> createProfile(@RequestBody CreateProfileResource resource) {
        CreateProfileCommand command = CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        Optional<Profile> result = profileCommandService.handle(command);
        return result.map(profile -> new ResponseEntity<>(
                        ProfileResourceFromEntityAssembler.toResourceFromEntity(profile), HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long id) {
        Optional<Profile> result = profileQueryService.findById(id);
        return result.map(profile -> ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ProfileResource>> getAllProfiles() {
        List<Profile> profiles = profileQueryService.findAll();
        List<ProfileResource> resources = profiles.stream()
                .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ProfileResource> getProfileByEmail(@PathVariable String email) {
        Optional<Profile> result = profileQueryService.findByEmail(email);
        return result.map(profile -> ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}/password/{password}")
    public ResponseEntity<ProfileResource> getProfileByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
        Optional<Profile> result = profileQueryService.findByEmailAndPassword(email, password);
        return result.map(profile -> ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/email/{email}/password/{password}")
    public ResponseEntity<ProfileResource> updateProfile(@PathVariable String email, @PathVariable String password,
                                                         @RequestBody CreateProfileResource resource) {
        Optional<Profile> existingProfile = profileQueryService.findByEmailAndPassword(email, password);
        if (existingProfile.isPresent()) {
            Profile profile = existingProfile.get();

            // Actualiza solo los campos necesarios
            profile.setName(resource.name());
            profile.setLastName(resource.lastName());
            profile.setEmail(resource.email());
            profile.setPassword(resource.password()); // Encriptar la contraseña si es necesario

            // Guarda el perfil actualizado
            profileRepository.save(profile);

            // Retorna el recurso actualizado
            return ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        boolean isDeleted = profileCommandService.deleteProfileById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

}
