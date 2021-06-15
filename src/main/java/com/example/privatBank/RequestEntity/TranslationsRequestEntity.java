package com.example.privatBank.RequestEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Setter
@Getter
@ToString

public class TranslationsRequestEntity {

    private int id;
    private int djId;
    private int duration;
    private String title;

}
