package com.example.store.projektstore.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "firstName", nullable = false)
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[A-Z][a-z]*", message = "Imie zaczynamy od wielkiej litery")
    private String firstName;

    @NotBlank
    @Column(name = "lastName", nullable = false)
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[A-Z][a-z]*", message = "Nazwisko zaczynamy od wielkier litery")
    private String lastName;

    @NotBlank
    @Column(name = "login", nullable = false, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "Login musi mieć od 3 do 20 znaków ")
    private String login;

    @NotBlank
    @Column(name = "password", nullable = false)
    @Size(min = 5)
    private String password;

    @Column(name = "age", nullable = false)
    @Min(value = 18, message = "Uzytkownik musi miec co najmniej 18 lat")
    private int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InformationStore> informationStoreList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}