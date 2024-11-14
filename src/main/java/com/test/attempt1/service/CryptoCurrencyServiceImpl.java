package com.test.attempt1.service;

import com.test.attempt1.CsvReader;
import com.test.attempt1.CurrencyInfo;
import com.test.attempt1.dao.jpa.CryptoCurrencyRepository;
import com.test.attempt1.domain.CryptoCurrency;
import com.test.attempt1.domain.CryptoCurrencyToShow;
import com.test.attempt1.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * service implementation
 */
@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {
    private static final Logger log = LoggerFactory.getLogger(CryptoCurrencyServiceImpl.class);

    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    HashMap<String, CurrencyInfo> currencyMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("------------------------- start read csv's");
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("prices/*.csv");
            for (Resource resource : resources) {
                log.info("reading csv: " + resource.getFilename());
                List<CryptoCurrency> crCrrList = CsvReader.csvToList(resource.getInputStream());
                cryptoCurrencyRepository.saveAll(crCrrList);

                Map.Entry<String,CurrencyInfo> currencyInfoEntry = CurrencyInfo.calcCurrencyInfo(crCrrList);
                currencyMap.put(currencyInfoEntry.getKey(), currencyInfoEntry.getValue());
                log.info("currency:" + currencyInfoEntry.getKey() );
                log.info("min:" + currencyInfoEntry.getValue().min );
                log.info("max:" + currencyInfoEntry.getValue().max );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("====================== csv read done.");
    }


    public CryptoCurrency createCryptoCurrency(CryptoCurrency cryptoCurrency) {
        return cryptoCurrencyRepository.save(cryptoCurrency);
    }

    public CryptoCurrency getCryptoCurrency(long id) {
        return cryptoCurrencyRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<CryptoCurrencyToShow> getAllCryptoCurrencies() {
        log.info("=========================== processed");
        List<CryptoCurrency> ccl = cryptoCurrencyRepository.findAll();
        List<CryptoCurrencyToShow> res = new ArrayList<>();
        for(CryptoCurrency cc : ccl) {
            res.add(new CryptoCurrencyToShow(cc.getName(),cc.getTimeStamp(),cc.getPrice()));
        }

        return res;
        //return cryptoCurrencyRepository.findAll();
    }

    public Map.Entry<String, CurrencyInfo> getCryptoCurrencyInfoByName(String name) {
        CurrencyInfo currencyInfo = currencyMap.get(name);
        if (currencyInfo != null) {
            return new AbstractMap.SimpleEntry<>(name, currencyInfo);
        } else {
            return null;
        }

    }
}
