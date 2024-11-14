package com.test.attempt1;

/**
 *  todo: query rate limiter
 */
public interface CustomRateLimiter {
    /**
     * @param remoteAddr IP address of remote side
     */
    public void waitRateLimit(String remoteAddr);
}
