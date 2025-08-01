package com.bicasteam.movigestion.api.profiles.interfaces.rest.resources;


public record CreateProfileResource(
        String name,
        String lastName,
        String email,
        String password,
        String type,
        String phone,
        String companyName,
        String companyRuc,
        String profilePhoto) {}