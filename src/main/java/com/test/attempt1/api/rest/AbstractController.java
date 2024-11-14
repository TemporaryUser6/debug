package com.test.attempt1.api.rest;

import com.test.attempt1.domain.RestErrorInfo;
import com.test.attempt1.exception.CustomException;
import com.test.attempt1.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

/**
 * common REST API controller elements
 */

public abstract class AbstractController implements ApplicationEventPublisherAware {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String DATA_STORE_ERROR_MSG = "Error: handleDataStoreException.";
    private static final String RESOURCE_NOT_FOUND_MSG = "Error: handleResourceNotFoundException";
    protected ApplicationEventPublisher eventPublisher;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public RestErrorInfo handleDataStoreException(CustomException ex, WebRequest request, HttpServletResponse response) {
        logger.info("--- handleDataStoreException : " + ex.getMessage());
        return new RestErrorInfo(ex, DATA_STORE_ERROR_MSG);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public RestErrorInfo handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request, HttpServletResponse response) {
        logger.info("--- handleResourceNotFoundException:" + ex.getMessage());
        return new RestErrorInfo(ex, RESOURCE_NOT_FOUND_MSG);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    //todo: try use @ExceptionHandler HandlerExceptionResolver SimpleMappingExceptionResolver etc.
    public <T> void checkResourceFound(final T resource) {
        if (null == resource) {
            throw new ResourceNotFoundException("resource not found");
        }
    }

}