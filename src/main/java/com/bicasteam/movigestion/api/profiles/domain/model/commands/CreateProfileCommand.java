package com.bicasteam.movigestion.api.profiles.domain.model.commands;


public record CreateProfileCommand(
        String name,
        String lastName,
        String email,
        String password,
        String type,
        String phone,
        String companyName,
        String companyRuc,
        String profilePhoto) {}