/*
 * Copyright (c) 2018.
 */

package weatherapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import weatherapp.security.filter.JWTAuthenticationFilter;
import weatherapp.security.filter.JWTAuthorizationFilter;
import weatherapp.services.UserDetailsServiceImpl;

import static weatherapp.security.SecurityConstants.SIGN_UP_URL;

/**
 * @author sulekha
 * njit_cs_673_Weather_Web_App, 2018
 */
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers("/secured-endpoints/**").authenticated()
                .antMatchers("/users/delete-user/**").authenticated()
                .antMatchers("/login/**").permitAll()
//                .anyRequest().authenticated()
//                .antMatchers("/radar/**").permitAll()
//                .antMatchers("/temp_maps/**").permitAll()
//                .antMatchers("/userprofile/**").permitAll()
//                .antMatchers("/locations-photos/**").permitAll()
//                .antMatchers("/10day/**").permitAll()
//                .antMatchers("/5day/**").permitAll()
//                .antMatchers("/historical/**").permitAll()
//                .antMatchers("/hourly/**").permitAll()
//                .antMatchers("/now/**").permitAll()
//                .antMatchers("/realtimeWeatherUpdate/**").permitAll()
                .antMatchers("/**").permitAll()
//                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
