package com.korea.MOVIEBOOK.member;

import jakarta.servlet.http.HttpSession;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/customerSupport/myQuestion").authenticated()
                        .requestMatchers("/customerSupport/question/questionForm").authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .formLogin(formLogin -> formLogin
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/"))
//

                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/member/login")  // OAuth2 로그인 페이지 설정
                        .defaultSuccessUrl("/")
                        .successHandler(savedRequestAwareAuthenticationSuccessHandler())
                        .failureHandler((request, response, exception) -> {
                            int a = 10;
                            exception.printStackTrace();
                            System.out.println("hihihi");
                        })      // 로그인 성공 후 리다이렉트 URL3
                        .failureHandler((request, response, exception) -> {
                            System.out.println("hihihihi");
                            exception.printStackTrace();
                        })
                        .defaultSuccessUrl("/"))
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
            Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.kakaoClientRegistration(), this.googleClientRegistration(), this.naverClientRegistration());
    }
    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setUseReferer(true);
        return handler;
    }


    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId("286082206804-488g2m1ncjkn87hq3g1c1m406crijhre.apps.googleusercontent.com")
                .clientSecret("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")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("https://moviebook.site/login/oauth2/code/google")
                .scope("profile", "email", "address", "phone")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("Google")
                .build();
    }

//    private ClientRegistration googleClientRegistration() {
//        return ClientRegistration.withRegistrationId("google")
//                .clientId("286082206804-jndj19gauu19aksqnscei5hf5jd2qkgt.apps.googleusercontent.com")
//                .clientSecret("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")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("https://moviebook.site/login/oauth2/code/google")
//                .scope("profile", "email", "address", "phone")
//                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
//                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
//                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
//                .userNameAttributeName(IdTokenClaimNames.SUB)
//                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
//                .clientName("Google")
//                .build();
//    }

    private ClientRegistration kakaoClientRegistration() {
        return ClientRegistration.withRegistrationId("kakao")
                .clientId("c85a61301f37545e9f8d0819abc77a8f")
                .redirectUri("https://moviebook.site/login/oauth2/code/kakao")
                .clientSecret("Ce4UFSlocsYjvNF0NaYKuzo4ZNQl7gDY")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile_nickname", "profile_image")
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .userNameAttributeName("id")
                .clientName("Kakao")
                .build();
    }

    private ClientRegistration naverClientRegistration() {
        return ClientRegistration.withRegistrationId("naver")
                .clientId("YbsKJH1dMFw8nSmVJKAV")
                .clientSecret("dqHpkKMGPB")
                .redirectUri("https://moviebook.site/login/oauth2/code/naver")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("nickname", "email", "username", "profile_image")
                .clientName("Naver")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .build();
    }
}