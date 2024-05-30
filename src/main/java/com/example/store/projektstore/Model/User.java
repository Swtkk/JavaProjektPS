package com.example.store.projektstore.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "{err.blank}")
    @Column(name = "firstName", nullable = false)
    @Size(min = 3, max = 20, message = "{size.field}")
    @Pattern(regexp = "^[A-Z][a-z]*", message = "{pattern.camelCase}")
    private String firstName;

    @NotBlank(message = "{err.blank}")
    @Column(name = "lastName", nullable = false)
    @Size(min = 3, max = 20, message = "{size.field}")
    @Pattern(regexp = "^[A-Z][a-z]*", message = "{pattern.camelCase}")
    private String lastName;

    @NotBlank(message = "{err.blank}")
    @Column(name = "login", nullable = false, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "{size.field}")
    private String login;

    @NotBlank(message = "{err.blank}")
    @Column(name = "password", nullable = false)
    @Size(min = 5, message = "Pole musi zawierac co najmniej 5 znakow")
    private String password;

    @Column(name = "age", nullable = false)
    @Min(value = 18, message = "Uzytkownik musi miec co najmniej 18 lat")
    private int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InformationStore> informationStoreList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addInformation(InformationStore information) {
        this.informationStoreList.add(information);
        information.setUser(this);
    }
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<InformationStore> sentInformations;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                // Omit `informations` to avoid recursive calls
                '}';
    }
}