package com.runcoding.auth.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class CustomTokenEnhancer implements TokenEnhancer {

    /**
     * jwt 中自定义扩展信息
    * {"alg":"HS256","typ":"JWT"}
     * {
     *   "scope": [
     *     "foo",
     *     "openid",
     *     "read",
     *     "write"
     *   ],
     *   "organization": "fooClientIdPasswordVpTI",
     *   "exp": 1553196047,
     *   "authorities": [
     *     "ROLE_ANONYMOUS",
     *     "ROLE_USER"
     *   ],
     *   "jti": "7984d250-a238-47c1-a463-ffa0f0e88336",
     *   "client_id": "fooClientIdPassword",
     *   "info": "[running info]"
     * }
    * */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        // additionalInfo.put("organization", authentication.getName() + randomAlphabetic(4));
        additionalInfo.put("info","[running info]");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
