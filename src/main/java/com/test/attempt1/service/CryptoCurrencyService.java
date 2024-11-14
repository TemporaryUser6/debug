package com.test.attempt1.service;

import com.test.attempt1.CurrencyInfo;
import com.test.attempt1.domain.CryptoCurrency;
import com.test.attempt1.domain.CryptoCurrencyToShow;

import java.util.List;
import java.util.Map;

/*
 * service interface
 */

public interface CryptoCurrencyService {

    CryptoCurrency createCryptoCurrency(CryptoCurrency cryptoCurrency);

    CryptoCurrency getCryptoCurrency(long id);

    List<CryptoCurrencyToShow> getAllCryptoCurrencies();

    Map.Entry<String, CurrencyInfo> getCryptoCurrencyInfoByName(String name);

}
