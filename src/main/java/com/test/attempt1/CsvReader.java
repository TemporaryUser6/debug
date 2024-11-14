package com.test.attempt1;

import com.test.attempt1.domain.CryptoCurrency;
import com.test.attempt1.exception.CustomException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvReader {

    private CsvReader(){}

    public static List<CryptoCurrency> csvToList(InputStream is) {
        try (BufferedReader bReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(bReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()))
        {
            List<CryptoCurrency> cryptoCurrencyList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                CryptoCurrency cryptoCurrency = new CryptoCurrency();
                cryptoCurrency.setName(csvRecord.get("symbol"));


                String priceStr = csvRecord.get("price");
                String[] splitPrice = priceStr.split("\\.");
                long price = 0;

                // I don't have a time to optimize this, 16 hrs for whole task is too small timeframe

                if (2 == splitPrice.length) {
                    if ( 1 == splitPrice[1].length()) {
                        price = Long.parseLong(splitPrice[0]) * 10000 + Long.parseLong(splitPrice[1].trim())*1000;
                    } else if ( 2 == splitPrice[1].length()) {
                        price = Long.parseLong(splitPrice[0]) * 10000 + Long.parseLong(splitPrice[1].trim()) *100;
                    } else if ( 3 == splitPrice[1].length()) {
                        price = Long.parseLong(splitPrice[0]) * 10000 + Long.parseLong(splitPrice[1].trim()) *10;
                    } else if ( 4 == splitPrice[1].length()) {
                        price = Long.parseLong(splitPrice[0]) * 10000 + Long.parseLong(splitPrice[1].trim()) ;
                    }
                } else {
                    price = Long.parseLong(splitPrice[0]) * 10000;
                }
                cryptoCurrency.setPrice(price);
                cryptoCurrency.setTimeStamp(Long.parseLong(csvRecord.get("Timestamp")));
                cryptoCurrencyList.add(cryptoCurrency);
            }
            return cryptoCurrencyList;
        } catch (IOException e) {
            throw new CustomException("csv parsing failed", e);
        }
    }
}
