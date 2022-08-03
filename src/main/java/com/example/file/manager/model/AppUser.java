package com.example.file.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data // Create getters and setters
@NoArgsConstructor
public class AppUser {

    @ElementCollection(fetch = FetchType.EAGER)
    List<AppUserRole> appUserRoles;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Size(min = 8, message = "Minimum password length: 8 characters")
    private String password;

}
