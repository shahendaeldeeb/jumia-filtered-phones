package com.jumia.phones.service;

import com.jumia.phones.model.CountryValidator;
import com.jumia.phones.model.NormalizedPhone;
import com.jumia.phones.repository.CustomerRepository;
import com.jumia.phones.utils.PhoneUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhoneServiceTest {
    private List<CountryValidator> mockedValidators = new ArrayList<>();

    private final CountryValidator Cameroon = new CountryValidator("Cameroon", "+237", "\\(237\\)\\ ?[2368]\\d{7,8}$");
    private final CountryValidator Ethiopia = new CountryValidator("Ethiopia", "+251", "\\(251\\)\\ ?[1-59]\\d{8}$");
    private final CountryValidator Morocco = new CountryValidator("Morocco", "+212", "\\(212\\)\\ ?[5-9]\\d{8}$");
    private final CountryValidator Mozambique = new CountryValidator("Mozambique", "+258", "\\(258\\)\\ ?[28]\\d{7,8}$");
    private final CountryValidator Uganda = new CountryValidator("Uganda", "+256", "\\(256\\)\\ ?\\d{9}$");

    @Spy
    @InjectMocks
    public PhoneService phoneService;

    @Mock
    public CustomerRepository customerRepository;

    @Mock
    private PhoneUtil phoneUtil;

    List<String> mockedPhoneNumbers = new ArrayList<>();

    @Before
    public void setup() {
        this.setupValidator();
        mockedPhoneNumbers.add("(237) 697151594"); //Cameroon, valid
        when(customerRepository.getPhones()).thenReturn(this.mockedPhoneNumbers);
        when(phoneUtil.getValidators()).thenReturn(this.mockedValidators);
    }

    @Test
    public void testParsePhoneNumberWithoutFiltering() {
        List<NormalizedPhone> normalizedPhoneList = this.phoneService.getNormalizedPhoneList(null, null);
        assertEquals(1, normalizedPhoneList.size());
        assertNotNull(normalizedPhoneList.get(0).getCountryName());
        assertNotNull(normalizedPhoneList.get(0).getValid());
        assertNotNull(normalizedPhoneList.get(0).getCode());
        assertNotNull(normalizedPhoneList.get(0).getPhone());
    }

    @Test
    public void testParsePhoneNumberWithCountryNameFiltering() {
        mockedPhoneNumbers.add("(212) 6007989253");
        when(customerRepository.getPhones()).thenReturn(this.mockedPhoneNumbers);

        List<NormalizedPhone> normalizedPhoneList = this.phoneService.getNormalizedPhoneList("Cameroon", null);
        assertEquals(1, normalizedPhoneList.size());

        assertNotNull(normalizedPhoneList.get(0).getCountryName());
        assertEquals("Cameroon", normalizedPhoneList.get(0).getCountryName());
        assertNotNull(normalizedPhoneList.get(0).getValid());
        assertNotNull(normalizedPhoneList.get(0).getCode());
        assertNotNull(normalizedPhoneList.get(0).getPhone());
    }

    @Test
    public void testParsePhoneNumberWithValidFiltering() {
        mockedPhoneNumbers.add("(212) 6007989253"); //Morocco, invalid
        mockedPhoneNumbers.add("(212) 698054317"); //Morocco, valid
        mockedPhoneNumbers.add("(237) 6A0311634");// Cameroon, invalid

        when(customerRepository.getPhones()).thenReturn(this.mockedPhoneNumbers);

        List<NormalizedPhone> normalizedPhoneList = this.phoneService.getNormalizedPhoneList(null, "valid");
        assertEquals(2, normalizedPhoneList.size());
    }

    @Test
    public void testParsePhoneNumberWithValidAndCountryNameFiltering() {
        mockedPhoneNumbers.add("(212) 6007989253"); //Morocco, invalid
        mockedPhoneNumbers.add("(212) 698054317"); //Morocco, valid
        mockedPhoneNumbers.add("(237) 6A0311634");// Cameroon, invalid

        when(customerRepository.getPhones()).thenReturn(this.mockedPhoneNumbers);

        List<NormalizedPhone> normalizedPhoneList = this.phoneService.getNormalizedPhoneList("Morocco", "valid");
        assertEquals(1, normalizedPhoneList.size());
    }

    public void setupValidator() {
        this.mockedValidators.add(Cameroon);
        this.mockedValidators.add(Ethiopia);
        this.mockedValidators.add(Morocco);
        this.mockedValidators.add(Mozambique);
        this.mockedValidators.add(Uganda);
    }
}
