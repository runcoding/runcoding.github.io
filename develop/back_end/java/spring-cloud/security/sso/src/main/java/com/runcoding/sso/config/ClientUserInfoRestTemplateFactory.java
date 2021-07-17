package com.runcoding.sso.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.state.DefaultStateKeyGenerator;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.RequestEnhancer;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author runcoding
 * @date 2019-04-01
 * @desc:  org.springframework.boot.autoconfigure.security.oauth2.resource.DefaultUserInfoRestTemplateFactory
 */
public class ClientUserInfoRestTemplateFactory   implements UserInfoRestTemplateFactory {

    private static final AuthorizationCodeResourceDetails DEFAULT_RESOURCE_DETAILS;

    static {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setClientId("<N/A>");
        details.setUserAuthorizationUri("Not a URI because there is no client");
        details.setAccessTokenUri("Not a URI because there is no client");
        DEFAULT_RESOURCE_DETAILS = details;
    }

    private final List<UserInfoRestTemplateCustomizer> customizers;

    private final OAuth2ProtectedResourceDetails details;

    private final OAuth2ClientContext oauth2ClientContext;

    private OAuth2RestTemplate oauth2RestTemplate;


    public ClientUserInfoRestTemplateFactory(ObjectProvider<List<UserInfoRestTemplateCustomizer>> customizers,
                                             ObjectProvider<OAuth2ProtectedResourceDetails> details,
                                             ObjectProvider<OAuth2ClientContext> oauth2ClientContext) {
        this.customizers = customizers.getIfAvailable();
        this.details = details.getIfAvailable();
        this.oauth2ClientContext = oauth2ClientContext.getIfAvailable();
    }

    @Override
    public OAuth2RestTemplate getUserInfoRestTemplate() {
        if (this.oauth2RestTemplate == null) {
            this.oauth2RestTemplate = createOAuth2RestTemplate(
                    this.details == null ? DEFAULT_RESOURCE_DETAILS : this.details);
            this.oauth2RestTemplate.getInterceptors().add(new AcceptJsonRequestInterceptor());

            AuthorizationCodeAccessTokenProvider accessTokenProvider = new AuthorizationCodeAccessTokenProvider();
            accessTokenProvider.setTokenRequestEnhancer(new AcceptJsonRequestEnhancer());
            accessTokenProvider.setStateKeyGenerator(new ClientStateKeyGenerator());

            this.oauth2RestTemplate.setAccessTokenProvider(accessTokenProvider);
            if (!CollectionUtils.isEmpty(this.customizers)) {
                AnnotationAwareOrderComparator.sort(this.customizers);
                for (UserInfoRestTemplateCustomizer customizer : this.customizers) {
                    customizer.customize(this.oauth2RestTemplate);
                }
            }
        }
        return this.oauth2RestTemplate;
    }

    /***
     * 自定义生成generateKey
     */
    class ClientStateKeyGenerator extends DefaultStateKeyGenerator {

        @Override
        public String generateKey(OAuth2ProtectedResourceDetails resource) {
            System.out.println("generateKey="+resource.getClientId());
            super.generateKey(resource);
            return  resource.getClientId();
        }
    }



    private OAuth2RestTemplate createOAuth2RestTemplate(
            OAuth2ProtectedResourceDetails details) {
        if (this.oauth2ClientContext == null) {
            return new OAuth2RestTemplate(details);
        }
        return new OAuth2RestTemplate(details, this.oauth2ClientContext);
    }

    static class AcceptJsonRequestEnhancer implements RequestEnhancer {

        @Override
        public void enhance(AccessTokenRequest request,
                            OAuth2ProtectedResourceDetails resource,
                            MultiValueMap<String, String> form, HttpHeaders headers) {
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        }

    }

    static class AcceptJsonRequestInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            return execution.execute(request, body);
        }

    }
}
