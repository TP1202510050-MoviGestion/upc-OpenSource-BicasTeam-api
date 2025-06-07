package com.bicasteam.movigestion.api.profiles.interfaces.rest.transform;


import com.bicasteam.movigestion.api.profiles.domain.model.commands.CreateProfileCommand;
import com.bicasteam.movigestion.api.profiles.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler {
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource r) {
        return new CreateProfileCommand(
                r.name(), r.lastName(), r.email(), r.password(), r.type(),
                r.phone(), r.companyName(), r.companyRuc(), r.profilePhoto());
    }
}
