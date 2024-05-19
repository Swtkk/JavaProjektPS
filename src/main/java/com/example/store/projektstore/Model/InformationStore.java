package com.example.store.projektstore.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private User user;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "{err.blank}")
    @Size(min = 3, max = 20, message = "{size.field}")
    private String title;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "Długość opisu powinna wynosić od 3 do 500 znaków")
    @Size(min = 3, max = 500, message = "Długość opisu powinna wynosić od 3 do 500 znaków")
    private String description;

    @Column(name = "link")
    private String link;

    @Column(name = "date_added")
    private LocalDate dateAdded = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Override
    public String toString() {
        return "InformationStore{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", dateAdded=" + dateAdded +
                // Omit `user` and `category` to avoid recursive calls
                '}';
    }
}
