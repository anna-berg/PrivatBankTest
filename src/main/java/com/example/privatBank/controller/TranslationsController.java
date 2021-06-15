package com.example.privatBank.controller;

import com.example.privatBank.RequestEntity.JoinRequestEntity;
import com.example.privatBank.RequestEntity.TranslationsRequestEntity;
import com.example.privatBank.entity.TranslationParts;
import com.example.privatBank.entity.Translations;
import com.example.privatBank.repository.TranslationPartsRepository;
import com.example.privatBank.repository.TranslationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;


@RestController
@RequestMapping("/api/v1/translations/")
public class TranslationsController {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TranslationsRepository translationsRepository;

    @Autowired
    TranslationPartsRepository translationPartsRepository;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Translations> getTranslation(@PathVariable("id") Integer id) {
        Translations translation = translationsRepository.getOne(id);

        if (translation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(translation, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Translations> createTranslation (@RequestBody TranslationsRequestEntity translationsRequestEntity){
        Translations translation = new Translations();

        translation.setDjId(translationsRequestEntity.getDjId());
        translation.setDuration(translationsRequestEntity.getDuration());
        translation.setTitle(translationsRequestEntity.getTitle());
        translationsRepository.save(translation);

        return new ResponseEntity<>(translation, HttpStatus.OK);

    }
    @Transactional
    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addTranslationPart(
        @RequestBody JoinRequestEntity joinRequestEntity,
        @PathVariable("id") Integer translationId
    ) {
        Translations translation = translationsRepository.getOne(translationId);
        TranslationParts translationPart = translationPartsRepository.getOne(joinRequestEntity.getTranslationPartId());

        if (translation == null || translationPart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        int totalDurationOfAllParts = 0;
        int totalDurationOfPaidParts = 0;

        for (TranslationParts part : translation.getTranslationParts()) {
            totalDurationOfAllParts+=part.getDuration();

            if (part.isPaidType()) {
                totalDurationOfPaidParts+=part.getDuration();
            }
        }

        if (translation.getDuration() < totalDurationOfAllParts+translationPart.getDuration()) {
            String message = String.format(
                "Total of translation parts duration must be less then translation duration (%d seconds)",
                translation.getDuration()
            );

            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        if (translationPart.isPaidType() &
            totalDurationOfPaidParts+translationPart.getDuration() > (translation.getDuration()/2)) {
            String message = String.format(
                    "Total duration of paid parts must be less then half of all translation duration (%d seconds)",
                    translation.getDuration()/2
            );
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        translation.addTranslationPart(translationPart);

        entityManager.persist(translation);
        entityManager.flush();

        return new ResponseEntity<>(translation, HttpStatus.OK);
    }

    @RequestMapping(value = "income/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Double TranslationIncome (int translationId){
        Translations translation = translationsRepository.getOne(translationId);

        int totalTranslationIncome = 0;
        for (TranslationParts part : translation.getTranslationParts()) {
            totalTranslationIncome += part.getCost();
        }
        double translationIncomeInEuro = (double) totalTranslationIncome/100;
        return translationIncomeInEuro;
    }
}
