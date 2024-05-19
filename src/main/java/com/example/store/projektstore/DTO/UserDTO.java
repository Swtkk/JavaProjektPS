//package com.example.store.projektstore.DTO;
//
//import com.example.store.projektstore.Model.InformationStore;
//import com.example.store.projektstore.Model.Role;
//import jakarta.persistence.Column;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//import lombok.Data;
//
//import java.util.List;
//import java.util.Set;
//
//@Data
//public class UserDTO {
//    private Long id;
//    @NotBlank(message = "{err.blank}")
//    @Size(min = 3, max = 20, message = "{size.field}")
//    @Pattern(regexp = "^[A-Z][a-z]*", message = "{pattern.camelCase}")
//    private String firstName;
//    @NotBlank(message = "{err.blank}")
//    @Size(min = 3, max = 20, message = "{size.field}")
//    @Pattern(regexp = "^[A-Z][a-z]*", message = "{pattern.camelCase}")
//    private String lastName;
//    @NotBlank(message = "{err.blank}")
//    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "{size.field}")
//    private String login;
//    @NotBlank(message = "{err.blank}")
//    @Size(min = 5, message = "Pole musi zawierac co najmniej 5 znakow")
//    private String password;
//    @Min(value = 18, message = "Uzytkownik musi miec co najmniej 18 lat")
//    private int age;
//    private List<InformationStore> informationStoreList;
//    private Set<Role> roles;
//}
