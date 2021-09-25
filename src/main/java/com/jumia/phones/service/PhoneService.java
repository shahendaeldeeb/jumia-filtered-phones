package com.jumia.phones.service;

import com.jumia.phones.model.CountryValidator;
import com.jumia.phones.model.NormalizedPhone;
import com.jumia.phones.repository.CustomerRepository;
import com.jumia.phones.utils.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PhoneService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PhoneUtil phoneUtil;

    /**
     * This method get list of phones from db then normalize each one.
     *
     * @param countryName
     * @param state
     * @return
     */
    public List<NormalizedPhone> getNormalizedPhoneList(final String countryName,
                                                        final String state) {
        List<String> phones = customerRepository.getPhones();
        List<NormalizedPhone> normalizedPhoneList = this.normalize(phones);
        return this.filter(countryName, state, normalizedPhoneList);

    }

    /**
     * This method return normalized phone numbers.
     *
     * @param phones
     * @return
     */
    private List<NormalizedPhone> normalize(final List<String> phones) {
        List<NormalizedPhone> normalizedPhoneList = new ArrayList<>();
        for (String phone : phones) {
            NormalizedPhone normalizedPhone = this.normalizedPhone(phone);
            normalizedPhoneList.add(normalizedPhone);
        }
        return normalizedPhoneList;
    }

    /**
     * This method validate that the phone number country code found,
     * Then validate phone number according to country regexp,
     * then map result to normalized phone number.
     *
     * @param phone
     * @return
     */
    protected NormalizedPhone normalizedPhone(final String phone) {
        NormalizedPhone normalizedPhone = new NormalizedPhone();

        for (CountryValidator countryValidator : phoneUtil.getValidators()) {

            boolean countryCodeMatches = validateCountryCodeMatcher(countryValidator, phone);

            if (countryCodeMatches) {
                boolean isValid = Pattern.matches(countryValidator.getRegexp(), phone);

                normalizedPhone = new NormalizedPhone(
                        countryValidator.getName(),
                        countryValidator.getCode(),
                        phone,
                        isValid ? "valid" : "invalid");

                return normalizedPhone;
            }
        }
        return normalizedPhone;
    }

    /**
     * This method validate if phone number country code matches any predefined country codes.
     *
     * @param countryValidator
     * @param phone
     * @return
     */
    public boolean validateCountryCodeMatcher(final CountryValidator countryValidator, final String phone) {
        String countryCodeRegexp = countryValidator.getRegexp().substring(0, 10);
        String[] phoneStr = phone.split(" ");
        return Pattern.matches(countryCodeRegexp, phoneStr[0]);
    }

    /**
     * This method filter list of normalized phone number either by country name or validity or both.
     *
     * @param countryName
     * @param state
     * @param normalizedPhoneList
     * @return
     */
    private List<NormalizedPhone> filter(final String countryName, final String state, List<NormalizedPhone> normalizedPhoneList) {
        if (countryName != null || state != null) {

            normalizedPhoneList = normalizedPhoneList
                    .stream()
                    .filter((np) -> {
                        if (countryName != null & countryName != "") {
                            return np.getCountryName().equals(countryName);
                        } else {
                            return true;
                        }
                    })
                    .filter((np) -> {
                        if (state != null && state != "") {
                            return np.getValid().equals(state);
                        } else {
                            return true;
                        }
                    })
                    .collect(Collectors.toList());
        }
        return normalizedPhoneList;
    }

}
