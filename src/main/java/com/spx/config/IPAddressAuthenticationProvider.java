package com.spx.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;



@Component
@PropertySource(Application.PROPERTIES_PATH)
public class IPAddressAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = Logger.getLogger(IPAddressAuthenticationProvider.class);

    @Autowired
    Environment env;

    @Override
    public Authentication authenticate(final Authentication authentication) {
        final String serviceHostIPAddress = env.getProperty(Application.PROPERTY_SERVICE_HOST_URL);

        final WebAuthenticationDetails authDetails = (WebAuthenticationDetails) authentication.getDetails();
        final String userIPAddress = authDetails.getRemoteAddress();

        LOGGER.info("Auth ip = " + userIPAddress);

        boolean isAuthByIP = false;
        if (serviceHostIPAddress.contains(userIPAddress)) {
            isAuthByIP = true;
        }

        if (isAuthByIP) {
            LOGGER.info("Auth success, match with " + serviceHostIPAddress);

            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("ServiceHost",
                    "ServiceHostSecretPassword");

            token.setDetails(authentication.getDetails());

            return token;
        }

        return null;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
