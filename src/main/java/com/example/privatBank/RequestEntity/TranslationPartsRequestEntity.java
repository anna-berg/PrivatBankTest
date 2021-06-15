package com.example.privatBank.RequestEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@Setter
@ToString
public class TranslationPartsRequestEntity {
    private String type;
    private HashMap<String, Object> attribute;
    private Integer duration;
}
