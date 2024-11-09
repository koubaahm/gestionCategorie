package univ.rouen.gestionCategorie.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nom;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @ManyToOne
    @JoinColumn(name = "parent_id") // Clé étrangère vers la table parent
    private Categorie parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Categorie> enfants;

    @Column(name = "est_racine")
    private boolean estRacine;

    // Constructeur par défaut
    public Categorie() {}

    // Constructeur personnalisé
    public Categorie(String nom, Categorie parent) {
        this.nom = nom;
        this.dateCreation = LocalDate.now();
        this.estRacine = (parent == null);
    }

    // Setter pour parent avec vérifications
    public void setParent(Categorie parent) {
        if (parent != null && parent.equals(this)) {
            throw new IllegalArgumentException("une catégorie ne peut pas être enfant d'elle-même.");
        }
        if (this.parent != null && parent != null && !this.parent.equals(parent)) {
            throw new IllegalArgumentException("une catégorie ne peut pas avoir deux parents.");
        }
        this.parent = parent;
        this.estRacine = (parent == null);
    }
}
