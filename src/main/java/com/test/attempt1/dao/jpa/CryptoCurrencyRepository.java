package com.test.attempt1.dao.jpa;

import com.test.attempt1.domain.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * delegate CRUD operations to the storage
 */
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {

    // CryptoCurrency findCryptoCurrencyByName(String city);
    // Optional<CryptoCurrency> findById(Long id);
    // CryptoCurrency findOne(Long id);

}
