// src/main/java/com/bicasteam/movigestion/api/profiles/interfaces/rest/resources/ChangePasswordResource.java
package com.bicasteam.movigestion.api.profiles.interfaces.rest.resources;

public record ChangePasswordResource(
        String email,
        String oldPassword,
        String newPassword
) {}
