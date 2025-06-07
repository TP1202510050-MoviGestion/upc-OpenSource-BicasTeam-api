package com.bicasteam.movigestion.api.profiles.interfaces.rest.transform;

import com.bicasteam.movigestion.api.profiles.domain.model.aggregates.Profile;
import com.bicasteam.movigestion.api.profiles.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile p) {
        return new ProfileResource(
                p.getId(), p.getName(), p.getLastName(), p.getEmail(), p.getType(),
                p.getPhone(), p.getCompanyName(), p.getCompanyRuc(), p.getProfilePhoto());
    }
}
