package com.service.beta.data.model;

import com.service.beta.data.enum_data.Gender;
import com.service.beta.data.object.Address;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "db_user")
public class DbUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name="role")
    private String role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

}
