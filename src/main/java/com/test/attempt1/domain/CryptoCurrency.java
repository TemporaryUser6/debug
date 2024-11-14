package com.test.attempt1.domain;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * a simple domain entity doubling as a DTO
 */
@Entity
@Table(name = "cryptos")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CryptoCurrency {

    @Id
    @GeneratedValue()
    private long id;
    @Column(nullable = false)
    private String name;
    @Column()
    private Long timeStamp;
    @Column()
    private long price;

    public CryptoCurrency() {
    }

    public CryptoCurrency(String name, Long timeStamp, long price) {
        this.name = name;
        this.timeStamp = timeStamp;
        this.price = price;
    }

    public long getId() {
        return this.id;
    }

    // for tests ONLY
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CryptoCurrency {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timestamp='" + Long.toString(timeStamp) + '\'' +
                ", price=" + price +
                '}';
    }
}
