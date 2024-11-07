package univ.rouen.gestionCategorie.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "categories")
@Data
@AllArgsConstructor
public class Categorie {

    @Id
    private String id;

    @Indexed(unique = true)
    private String nom;

    private LocalDate dateCreation;

    // parent
    @DBRef
    private Categorie parent;

    // enfants
    @DBRef
    private List<Categorie> enfants;

    private boolean estRacine;

    // constructeur personilisé pour la date de creation et aussi voir si il est parent ou enfant
    public Categorie(String nom, Categorie parent) {
        this.nom = nom;
        this.dateCreation = LocalDate.now();
        this.estRacine = (parent == null);
    }


    public void setParent(Categorie parent) {
        // empecher  qu'une catégorie soit enfant d'elle-même
        if (parent != null && parent.equals(this)) {
            throw new IllegalArgumentException("une catégorie ne peut pas être enfant d'elle-même.");
        }

        // empecher qu'une catégorie ait plus d'un parent
        if (this.parent != null && parent != null && !this.parent.equals(parent)) {
            throw new IllegalArgumentException("une catégorie ne peut pas avoir deux parents.");
        }

        this.parent = parent;
        this.estRacine = (parent == null);
    }

}
