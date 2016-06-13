package com.spx.logging;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.SecureRandom;


public class LoggerUtils {


    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private static long getRandomLong() {
        final byte[] bytes = new byte[8];
        SECURE_RANDOM.nextBytes(bytes);
        return new BigInteger(bytes).longValue();
    }

    public static long logException(final Logger logger, final Exception exception) {
        final long randomLong = getRandomLong();
        logger.error("[" + randomLong + "] " + ExceptionUtils.getStackTrace(exception));
        return randomLong;
    }
}
