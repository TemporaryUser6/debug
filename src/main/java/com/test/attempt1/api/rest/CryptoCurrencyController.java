package com.test.attempt1.api.rest;

import com.test.attempt1.CurrencyInfo;
import com.test.attempt1.CustomRateLimiter;
import com.test.attempt1.domain.CryptoCurrency;
import com.test.attempt1.domain.CryptoCurrencyInfoToShow;
import com.test.attempt1.domain.CryptoCurrencyToShow;
import com.test.attempt1.exception.CustomException;
import com.test.attempt1.service.CryptoCurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/*
 * endpoints entry point
 */

@RestController
@RequestMapping(value = "/api/v1/cryptos")
@Tag(name = "CryptoCurrency")
public class CryptoCurrencyController extends AbstractController {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @Autowired
    private CustomRateLimiter rateLimiter;

    @PostMapping(value = "",
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a CryptoCurrency", description = "return: URL of created entity: see Location header")
    public void createCryptoCurrency(@RequestBody CryptoCurrency cryptoCurrency,
                                     HttpServletRequest request, HttpServletResponse response) {
        rateLimiter.waitRateLimit(request.getRemoteAddr());
        CryptoCurrency createdCryptoCurrency = cryptoCurrencyService.createCryptoCurrency(cryptoCurrency);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdCryptoCurrency.getId()).toString());
    }


// todo: paging ?
//
//            @Parameter(description = "page number", required = true)
//            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUM) Integer page,
//            @Parameter(description = "page size", required = false)
//            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
    @GetMapping(value = "", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get list of all Crypto Currencies", description = "Get list of all Crypto Currencies")
    @ResponseBody
    public List<CryptoCurrencyToShow> getAllCryptoCurrencies(
                                                       HttpServletRequest request, HttpServletResponse response) {
        rateLimiter.waitRateLimit(request.getRemoteAddr());
        return cryptoCurrencyService.getAllCryptoCurrencies();
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get one CryptoCurrency", description = "Crypto Currency id")
    @ResponseBody
    public CryptoCurrency getCryptoCurrency(@Parameter(description = "id of Crypto Currency", required = true)
                          @PathVariable("id") Long id,
                                   HttpServletRequest request, HttpServletResponse response) throws CustomException {

        rateLimiter.waitRateLimit(request.getRemoteAddr());
        CryptoCurrency cryptoCurrency = cryptoCurrencyService.getCryptoCurrency(id);
        checkResourceFound(cryptoCurrency);
        return cryptoCurrency;
    }

    @GetMapping(value = "/byname/{cryptoName}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get CryptoCurrency by name", description = "Crypto Currency by name")
    @ResponseBody
    public CryptoCurrencyInfoToShow getCryptoCurrencyInfoByName(@Parameter(description = "name of Crypto Currency", required = true)
                                            @PathVariable("cryptoName") String cryptoName,
                                                                HttpServletRequest request, HttpServletResponse response) throws CustomException {

        rateLimiter.waitRateLimit(request.getRemoteAddr());
        Map.Entry<String, CurrencyInfo> cryptoCurrencyInfoByName = cryptoCurrencyService.getCryptoCurrencyInfoByName(cryptoName);
        checkResourceFound(cryptoCurrencyInfoByName);
        return new CryptoCurrencyInfoToShow(cryptoCurrencyInfoByName.getKey(),
                cryptoCurrencyInfoByName.getValue().min,
                cryptoCurrencyInfoByName.getValue().max,
                cryptoCurrencyInfoByName.getValue().oldest,
                cryptoCurrencyInfoByName.getValue().newest);
    }
}
