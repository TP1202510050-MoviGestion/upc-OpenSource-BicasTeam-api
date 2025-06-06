package com.bicasteam.movigestion.api.profiles.domain.services;


import com.bicasteam.movigestion.api.profiles.domain.model.aggregates.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> findById(Long id);
    List<Profile> findAll();
    Optional<Profile> findByEmail(String email);
    Optional<Profile> findByEmailAndPassword(String email, String password);
}
