package com.jumia.phones.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NormalizedPhone {
    private String countryName;
    private String code;
    private String phone;
    private String valid;

    public NormalizedPhone(){}

    public NormalizedPhone(final String countryName, final String code, final String phone, final String valid) {
        this.countryName = countryName;
        this.code = code;
        this.phone = phone;
        this.valid = valid;
    }
}
