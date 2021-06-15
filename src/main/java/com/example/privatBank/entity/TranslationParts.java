package com.example.privatBank.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "translation_parts")
@Getter
@Setter
@ToString
public class TranslationParts {
    private static String TYPE_SONG = "SONG";
    private static String TYPE_ADVERTISE = "ADVERTISE";
    private static String TYPE_INTERVIEW = "INTERVIEW";

    private static Set<String> paidTypes= new HashSet<String>();
    static {
        paidTypes.add(TYPE_ADVERTISE);
        paidTypes.add(TYPE_INTERVIEW);
    }

    /**
     * Price in cent per sec
     */
    private static HashMap <String, Integer> typeToPriceMap = new HashMap<>();

    static {
        typeToPriceMap.put(TranslationParts.TYPE_ADVERTISE, 500);
        typeToPriceMap.put(TranslationParts.TYPE_INTERVIEW, 50);
        typeToPriceMap.put(TranslationParts.TYPE_SONG, 0);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "translation_part_id")
    private int translationPartId;

    @Column(name = "type")
    private String type;

    @Column(name = "attribute")
    @Convert(converter = HashMapConverter.class)
    private HashMap<String, Object> attribute;

    @Column(name = "duration")
    private Integer duration;

    @ManyToMany(mappedBy = "translationParts")
    private Set<Translations> translations;

    public boolean isPaidType() {
        return paidTypes.contains(this.type);
    }

    public Integer getCost(){
        return this.duration * typeToPriceMap.get(this.type);
    }
}

