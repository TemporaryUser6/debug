package com.test.attempt1;

import com.test.attempt1.domain.CryptoCurrency;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class CurrencyInfo {
        public final long oldest;
        public final long newest;
        public final long min;
        public final long max;
        CurrencyInfo(long oldest, long newest, long min, long max) {
                this.oldest = oldest;
                this.newest = newest;
                this.min = min;
                this.max = max;
        }

        public static Map.Entry<String,CurrencyInfo> calcCurrencyInfo(List<CryptoCurrency> cryptoCurrencyList) {
                long oldestPrice = 0;
                long oldestTimestamp = 0;
                long newestPrice = 0;
                long newestTimestamp = 0;
                long minPrice = 0;
                long maxPrice = 0;
                boolean initDone = false;
                String currencyName ="null";
                for (CryptoCurrency cryptoCurrency : cryptoCurrencyList) {
                        if (!initDone) {
                                initDone = true;
                                maxPrice = minPrice = oldestPrice = newestPrice = cryptoCurrency.getPrice();
                                oldestTimestamp = newestTimestamp = cryptoCurrency.getTimeStamp();
                                currencyName = cryptoCurrency.getName();
                        }
                        if (cryptoCurrency.getTimeStamp() < oldestTimestamp) {
                                oldestTimestamp = cryptoCurrency.getTimeStamp();
                                oldestPrice = cryptoCurrency.getPrice();
                        }
                        if (cryptoCurrency.getTimeStamp() > newestTimestamp) {
                                newestTimestamp = cryptoCurrency.getTimeStamp();
                                newestPrice = cryptoCurrency.getPrice();
                        }
                        if (cryptoCurrency.getPrice() > maxPrice) {
                                maxPrice = cryptoCurrency.getPrice();
                        }
                        if(cryptoCurrency.getPrice() < minPrice) {
                                minPrice = cryptoCurrency.getPrice();
                        }
                }

                return new AbstractMap.SimpleEntry<>(currencyName,new CurrencyInfo(oldestPrice, newestPrice, minPrice, maxPrice));
        }
}
