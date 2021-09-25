package com.jumia.phones.utils;

import com.jumia.phones.model.CountryValidator;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class PhoneUtil {

    private List<CountryValidator> validators = new ArrayList<>();

    private final CountryValidator Cameroon = new CountryValidator("Cameroon", "+237", "\\(237\\)\\ ?[2368]\\d{7,8}$");
    private final CountryValidator Ethiopia = new CountryValidator("Ethiopia", "+251", "\\(251\\)\\ ?[1-59]\\d{8}$");
    private final CountryValidator Morocco = new CountryValidator("Morocco", "+212", "\\(212\\)\\ ?[5-9]\\d{8}$");
    private final CountryValidator Mozambique = new CountryValidator("Mozambique", "+258", "\\(258\\)\\ ?[28]\\d{7,8}$");
    private final CountryValidator Uganda = new CountryValidator("Uganda", "+256", "\\(256\\)\\ ?\\d{9}$");

    public PhoneUtil() {
        this.validators.add(Cameroon);
        this.validators.add(Ethiopia);
        this.validators.add(Morocco);
        this.validators.add(Mozambique);
        this.validators.add(Uganda);
    }

}
