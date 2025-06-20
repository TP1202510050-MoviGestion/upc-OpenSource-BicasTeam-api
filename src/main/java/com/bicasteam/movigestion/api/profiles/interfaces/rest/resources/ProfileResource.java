package com.bicasteam.movigestion.api.profiles.interfaces.rest.resources;


public record ProfileResource(
        Long   id,
        String name,
        String lastName,
        String email,
        String type,
        String phone,
        String companyName,
        String companyRuc,
        String profilePhoto) {}