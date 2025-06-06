package com.bicasteam.movigestion.api.profiles.domain.repositories;

import com.bicasteam.movigestion.api.profiles.domain.model.aggregates.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByEmail(String email);
    Optional<Profile> findByEmailAndPassword(String email, String password);
}
