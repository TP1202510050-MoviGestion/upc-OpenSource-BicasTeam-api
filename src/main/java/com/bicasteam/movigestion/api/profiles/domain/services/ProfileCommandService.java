package com.bicasteam.movigestion.api.profiles.domain.services;


import com.bicasteam.movigestion.api.profiles.domain.model.commands.CreateProfileCommand;
import com.bicasteam.movigestion.api.profiles.domain.model.aggregates.Profile;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<Profile> handle(CreateProfileCommand command);
    // Nuevo método para guardar o actualizar un perfil
    Profile save(Profile profile);    // método para guardar/actualizar entidades
    boolean deleteProfileById(Long id);

}
