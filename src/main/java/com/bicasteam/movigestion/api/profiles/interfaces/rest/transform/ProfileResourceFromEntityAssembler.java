package com.bicasteam.movigestion.api.profiles.interfaces.rest.transform;

import com.bicasteam.movigestion.api.profiles.domain.model.aggregates.Profile;
import com.bicasteam.movigestion.api.profiles.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(entity.getId(), entity.getName(), entity.getLastName(), entity.getEmail(), entity.getType());
    }
}
