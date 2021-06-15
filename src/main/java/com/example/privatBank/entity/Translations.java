package com.example.privatBank.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "translations")
@Getter
@Setter
@ToString

public class Translations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "translation_id")
    private int translationId;

    @Column(name = "dj_id")
    private int djId;

    @Column(name = "duration")
    private int duration;

    @Column(name = "title")
    private String title;

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(
        name = "translation_to_part",
        joinColumns = @JoinColumn(name = "translation_id"),
        inverseJoinColumns = @JoinColumn(name = "translation_part_id"))
    private Set<TranslationParts> translationParts;

    public void addTranslationPart(TranslationParts translationPart) {
        translationParts.add(translationPart);
        translationPart.getTranslations().add(this);
    }

    public void removeTranslationPart(TranslationParts translationPart) {
        translationParts.remove(translationPart);
        translationPart.getTranslations().remove(this);
    }
}
