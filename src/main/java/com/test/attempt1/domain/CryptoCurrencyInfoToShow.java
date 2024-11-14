package com.test.attempt1.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;

/*
 * a simple domain entity doubling as a DTO
 */
@Entity
@Table(name = "cryptos")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CryptoCurrencyInfoToShow {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    // not used, just to let the things work
    @Id
    @GeneratedValue()
    private long id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String minPriceWithDecimals;

    @Column
    private String maxPriceWithDecimals;

    @Column
    private String oldestPriceWithDecimals;

    @Column
    private String newestPriceWithDecimals;

    public CryptoCurrencyInfoToShow() {
    }

    public CryptoCurrencyInfoToShow(String name, long minPrice, long maxPrice, long oldestPrice, long newestPrice) {
        this.name = name;
        this.minPriceWithDecimals = longToStringWithDecimals(minPrice);
        this.maxPriceWithDecimals = longToStringWithDecimals(maxPrice);
        this.oldestPriceWithDecimals = longToStringWithDecimals(oldestPrice);
        this.newestPriceWithDecimals = longToStringWithDecimals(newestPrice);
    }

    private String longToStringWithDecimals(long value) {
        return Long.toString(value/10000L) + "." + Long.toString(value % 10000L);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinPrice() {
        return minPriceWithDecimals;
    }
    public String getMaxPrice() {
        return maxPriceWithDecimals;
    }
    public String getOldestPrice() {
        return oldestPriceWithDecimals;
    }
    public String getnewestPrice() {
        return newestPriceWithDecimals;
    }

    @Override
    public String toString() {
        return "CryptoCurrencyInfo {}";
    }
}
