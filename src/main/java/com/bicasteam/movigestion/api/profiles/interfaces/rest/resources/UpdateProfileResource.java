// src/main/java/com/bicasteam/movigestion/api/profiles/interfaces/rest/resources/UpdateProfileResource.java
package com.bicasteam.movigestion.api.profiles.interfaces.rest.resources;

public record UpdateProfileResource(
        String name,
        String lastName,
        String email,
        String phone,
        String companyName,
        String companyRuc,
        String profilePhoto
) {}
