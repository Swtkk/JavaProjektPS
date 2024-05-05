package com.example.store.projektstore.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Getter
@Setter
@Table(name = "informations")
@NoArgsConstructor
@AllArgsConstructor
public class InformationStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Powiązanie z użytkownikiem

    @Column(name = "title", nullable = false)
    @NotBlank(message = "Długość tytułu powinna wynosić od 3 do 20 znaków")
    @Size(min = 3, max = 20, message = "Długość tytułu powinna wynosić od 3 do 20 znaków")
    private String title;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "Długość opisu powinna wynosić od 3 do 500 znaków")
    @Size(min = 3, max = 500, message = "Długość opisu powinna wynosić od 3 do 500 znaków")
    private String description;


    @Column(name = "link")
    private String link;

    @Column(name = "date_added")
    private LocalDate dateAdded = LocalDate.now();

    @Column(name = "category")
    @Size(min = 3, max = 20, message = "Kategoria powinna wynosić od 3 do 20 znaków i zawierać tylko małe litery")
    @Pattern(regexp = "^[a-z]+$", message = "Kategoria powinna wynosić od 3 do 20 znaków i zawierać tylko małe litery")
    private String category;


}








