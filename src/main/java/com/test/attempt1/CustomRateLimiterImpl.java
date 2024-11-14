package com.test.attempt1;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * THIS CODE IS NOT PRODUCTION READY!
 * IT'S ONLY AN IDEA!
 * DEBUG: to be done !!
 * IP ADDRESSES TABLE STORAGE&PROCESSING: to be done
 */
@Service
@Scope("singleton")
public class CustomRateLimiterImpl implements CustomRateLimiter {
    private static final double permitsPerSecond = Double.MAX_VALUE - 100;
    private static final RateLimiter rateLimiter = RateLimiter.create(permitsPerSecond);

    public void waitRateLimit(String remoteAddr) {
        rateLimiter.acquire();
    }
}
