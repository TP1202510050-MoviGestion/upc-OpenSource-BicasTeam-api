package com.bicasteam.movigestion.api.profiles.application.internal;

import com.bicasteam.movigestion.api.profiles.domain.model.aggregates.Profile;
import com.bicasteam.movigestion.api.profiles.domain.model.commands.CreateProfileCommand;
import com.bicasteam.movigestion.api.profiles.domain.repositories.ProfileRepository;
import com.bicasteam.movigestion.api.profiles.domain.services.ProfileCommandService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public Optional<Profile> handle(CreateProfileCommand command) {
        Profile profile = new Profile(command);
        try {
            profileRepository.save(profile);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(profile);
    }
    // Implementación del nuevo método `save`

    @Override
    @Transactional
    public boolean deleteProfileById(Long id) {
        if (profileRepository.existsById(id)) {
            profileRepository.deleteById(id);
            return true;
        }
        return false;
    }

}

