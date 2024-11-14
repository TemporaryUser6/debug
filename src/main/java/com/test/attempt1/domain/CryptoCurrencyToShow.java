package com.test.attempt1.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * a simple domain entity doubling as a DTO
 */
@Entity
@Table(name = "cryptos")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CryptoCurrencyToShow {

    // it's not recommended to make SimpleDateFormat static due to theoretically possible
    // problems with multthreading. Let it be here just for temporary debug
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    @Id
    @GeneratedValue()
    private long id;
    @Column(nullable = false)
    private String name;
    @Column()
    private long timeStamp;
    @Column()
    private long price;

    @Column
    private String priceWithDecimals;

    @Column
    private String readableDate;

    public CryptoCurrencyToShow() {
    }

    public CryptoCurrencyToShow(String name, Long timeStamp, long price) {
        this.name = name;
        this.timeStamp = timeStamp;
        this.price = price;
        priceWithDecimals = Long.toString(price/10000L) + "." + Long.toString(price % 10000L);

        Timestamp stamp = new Timestamp(timeStamp);
        Date date = new Date(stamp.getTime());
        readableDate = dateFormat.format(date);
        //readableDate = date.toString();

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

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPriceWithDecimals() {
        return priceWithDecimals;
    }
    public String getReadableDate() {
        return readableDate;
    }

    @Override
    public String toString() {
        return "CryptoCurrency {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timestamp='" + timeStamp + '\'' +
                ", price=" + price +
                '}';
    }
}
