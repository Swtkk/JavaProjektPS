//package com.example.store.projektstore.DTO;
//
//import com.example.store.projektstore.Model.User;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//import lombok.Data;
//import lombok.Value;
//
//import java.io.Serializable;
//import java.time.LocalDate;
//
//@Data
//public class InformationStoreDto {
//    private Long id;
//    private Long userId;
//    @NotBlank(message = "{err.blank}")
//    @Size(min = 3, max = 20, message = "{size.field}")
//    private String title;
//    @NotBlank(message = "Długość opisu powinna wynosić od 3 do 500 znaków")
//    @Size(min = 3, max = 500, message = "Długość opisu powinna wynosić od 3 do 500 znaków")
//    private String description;
//    private String link;
//    private LocalDate dateAdded;
//    @Size(min = 3, max = 20)
//    @Pattern(regexp = "^[a-z]+$", message = "Kategoria powinna wynosić od 3 do 20 znaków i zawierać tylko małe litery")
//    private String category;
//
//}