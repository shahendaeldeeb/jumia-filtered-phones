package com.jumia.phones.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryValidator {
    private String name;
    private String code;
    private String regexp;

    public CountryValidator(String name, String code, String regexp) {
        this.name = name;
        this.code = code;
        this.regexp = regexp;
    }

}
