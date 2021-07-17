package com.runcoding.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author runcoding
 * @date 2019-03-14
 * @desc:
 */
@RestController
@SessionAttributes("authorizationRequest")
public class AuthorizationController extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/confirm_access").setViewName("authorize");
    }

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ConsumerTokenServices tokenServices;

   /** @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody */
    public void revokeToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer")) {
            String tokenId = authorization.substring("Bearer".length() + 1);
            tokenServices.revokeToken(tokenId);
            }
        }

    @RequestMapping(method = RequestMethod.GET, value = "/tokens")
    @ResponseBody
    public List<String> getTokens(@RequestParam(value = "clientId",defaultValue = "fooClientIdPassword") String  clientId) {
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId(clientId);
        return Optional.ofNullable(tokens).orElse(Collections.emptyList()).stream().map(OAuth2AccessToken::getValue).collect(Collectors.toList());
    }

    @PostMapping("/introspect")
    @ResponseBody
    public Map<String, Object> introspect(@RequestParam("token") String token) {
        OAuth2AccessToken accessToken = this.tokenStore.readAccessToken(token);
        Map<String, Object> attributes = new HashMap<>();
        if (accessToken == null || accessToken.isExpired()) {
            attributes.put("active", false);
            return attributes;
        }

        OAuth2Authentication authentication = this.tokenStore.readAuthentication(token);

        attributes.put("active", true);
        attributes.put("exp", accessToken.getExpiration().getTime());
        attributes.put("scope", accessToken.getScope().stream().collect(Collectors.joining(" ")));
        attributes.put("sub", authentication.getName());

        return attributes;
    }

    @GetMapping("/user/me")
    public Principal user(Principal principal) {
        return principal;
    }
}
