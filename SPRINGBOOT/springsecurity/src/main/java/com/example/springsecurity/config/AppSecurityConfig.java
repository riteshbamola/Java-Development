package com.example.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {


    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        //imperative
//        Customizer<CsrfConfigurer<HttpSecurity>> custCsrf =  new Customizer<CsrfConfigurer<HttpSecurity>>(){
//            @Override
//            public void customize(CsrfConfigurer<HttpSecurity> configurer) {
//                    configurer.disable();
//            }
//        };
//        http.csrf(custCsrf);



        //lambda
        http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
//     http.formLogin(Customizer.withDefaults());





        return http.build();
    }

//    @Bean    //In memory user Credentials
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user1 = User
//                                .withDefaultPasswordEncoder()
//                                .username("ayush")
//                                .password("1234")
//                                .build();
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("naman")
//                .password("1234")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1,user2);
//    }

// Database Credentials

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

}