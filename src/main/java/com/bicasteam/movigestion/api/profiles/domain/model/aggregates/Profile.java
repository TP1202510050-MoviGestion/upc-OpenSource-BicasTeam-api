package com.bicasteam.movigestion.api.profiles.domain.model.aggregates;

import com.bicasteam.movigestion.api.profiles.domain.model.commands.CreateProfileCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private String type;

    // 📦 NUEVOS ATRIBUTOS
    private String phone;          // Ej. +51 987654321
    private String companyName;    // Razón social
    private String companyRuc;     // RUC / NIT
    @Lob
    @Column(name = "profile_photo", columnDefinition = "LONGTEXT")
    private String profilePhoto;   // URL o Base64

    public Profile(CreateProfileCommand cmd) {
        this.name         = cmd.name();
        this.lastName     = cmd.lastName();
        this.email        = cmd.email();
        this.password     = cmd.password();
        this.type         = cmd.type();
        this.phone        = cmd.phone();
        this.companyName  = cmd.companyName();
        this.companyRuc   = cmd.companyRuc();
        this.profilePhoto = cmd.profilePhoto();
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setPhone(String phone)               { this.phone = phone; }

    public void setCompanyName(String companyName)   { this.companyName = companyName; }

    public void setCompanyRuc(String companyRuc)     { this.companyRuc = companyRuc; }

    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }

}
