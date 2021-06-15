package com.example.privatBank.controller;

import com.example.privatBank.RequestEntity.TranslationPartsRequestEntity;
import com.example.privatBank.entity.TranslationParts;
import com.example.privatBank.repository.TranslationPartsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/translation-parts/")
public class TranslationPartsController {

    @Autowired
    TranslationPartsRepository translationPartsRepository;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TranslationParts> getPartById(@PathVariable("id") Integer id) {
        TranslationParts part = translationPartsRepository.getOne(id);

        if (part == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(part, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TranslationParts> createPart (@RequestBody TranslationPartsRequestEntity translationPartsRequestEntity){
        TranslationParts translationParts = new TranslationParts();

            translationParts.setType(translationPartsRequestEntity.getType());
            translationParts.setAttribute(translationPartsRequestEntity.getAttribute());
            translationParts.setDuration(translationPartsRequestEntity.getDuration());
            translationPartsRepository.save(translationParts);

        return new ResponseEntity<>(translationParts, HttpStatus.OK);

    }
}
