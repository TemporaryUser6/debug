package com.test.attempt1.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.attempt1.Application;
import com.test.attempt1.domain.CryptoCurrency;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * basic tests
 * todo: provide more tests
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class CryptoCurrencyControllerTest {

    private static final String BASE_URL = "/api/v1/cryptos";

    private static final String RESOURCE_LOCATION_PATTERN =
            "http://localhost" + BASE_URL + "/[0-9]+";

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldCreateRetrieveDelete() throws Exception {
//  data is empty here
//        mvc.perform(get(BASE_URL + "/1")
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound());

//  CREATE
        final CryptoCurrency cryptoCurrency = mockCryptoCurrency("shouldCreateRetrieveDelete");
        final byte[] cryptoCurrencyAsJson = toJson(cryptoCurrency);
        MvcResult result = mvc.perform(post(BASE_URL)
            .content(cryptoCurrencyAsJson)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
            .andExpect(status().isCreated())
            .andReturn();
        String redirectedUrl = Objects.requireNonNull(result.getResponse().getRedirectedUrl());
        long id = getIdFromUrl(redirectedUrl);

// READ
        mvc.perform(get(BASE_URL + "/" + id)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is((int) id)))
            .andExpect(jsonPath("$.name", is(cryptoCurrency.getName())))
            .andExpect(jsonPath("$.timeStamp", is((int)cryptoCurrency.getTimeStamp())))
            .andExpect(jsonPath("$.price",     is((int)cryptoCurrency.getPrice())));
    }

    private long getIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Long.parseLong(parts[parts.length - 1]);
    }

    private CryptoCurrency mockCryptoCurrency(String prefix) {
        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setTimeStamp(12345678L);
        cryptoCurrency.setName(prefix + "_name");
        cryptoCurrency.setPrice(42000L);
        return cryptoCurrency;
    }

    private byte[] toJson(Object r) throws JsonProcessingException {
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsString(r).getBytes();
    }

    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return result -> {
            String redirectedUrl = Objects.requireNonNull(result.getResponse().getRedirectedUrl());
            Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
            assertTrue(pattern.matcher(redirectedUrl).find());
        };
    }
}
