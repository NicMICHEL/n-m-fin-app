package com.pcs.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES;

/**
 * This class allows to customize the configuration to secure the application.
 * The @Configuration annotation allows to declare beans through @Bean annotated methods.
 * The @EnableWebSecurity annotation ensures that the Spring web application knows how to import
 * the custom Spring Security configuration.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * Injects an instance of CustomUserDetailService as a class attribute.
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * @return an instance of BCryptPassword which allows passwords to be encoded.
     * The @Bean annotation allows to load the object resulting from the passwordEncoder method into the Spring context.
     * So Spring Security will be able to use it.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return an instance of HttpSessionEventPublisher which allows the Spring Security registry to be notified
     * when the session is created, when the session id is changed, when the session is destroyed.
     * The @Bean annotation allows to load the object resulting from the httpSessionEventPublisher method
     * into the Spring context. So Spring Security will be able to use it.
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * Checks user username and password entered by the user with credentials stored in database.
     * Checks if the user has the necessary permissions to access protected resources.
     *
     * @param http                  gives web-based security configuration for specific http requests.
     * @param bCryptPasswordEncoder which allows the password entered by the user to be encoded
     *                              and thus to check the concordance with the encoded password recorded in the database.
     * @return an AuthenticationManager instance which allows to know if the user is authenticated
     * and to retrieve his credentials, his rights and his principal.
     * throws AuthenticationException - if authentication fails.
     * The @Bean annotation allows to load the object resulting from the authenticationManager method
     * into the Spring context. So Spring Security will be able to use it.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    /**
     * Ensures that all HTTP requests that enter the web application are secure.
     * Each filter provides a security configuration.
     * authorizeHttpRequests()  specifies the required role depending on the endpoint.
     * exceptionHandling()  specifies the access denied URL.
     * formLogin()  customs login form.
     * logout()  configures logout functionality.
     * sessionManagement()  configures session based authentication.
     * maximumSessions()  determines the maximum number of concurrent sessions for the same user.
     * maxSessionsPreventsLogin(true) prevents a user from authenticating
     * when the SessionManagementConfigurer.maximumSessions(int) has been reached.
     * sessionFixation() configures what happens to an existing session when the user tries to authenticate again.
     * When newSession is set, a clean session will be created without any of the attributes
     * from the old session being copied over.
     * sessionCreationPolicy() controls when the session gets created. When IF_REQUIRED is set,
     * a session will be created only if required.
     * The @Bean annotation allows to load the object resulting from the filterChain method into the Spring context.
     * So Spring Security will be able to use it.
     *
     * @param http gives web-based security configuration for specific http requests.
     * @return a SecurityFilterChain instance used to determine which security Filter
     * should be invoked for the current request.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((requests) -> requests.requestMatchers
                        (PathRequest.toStaticResources().atCommonLocations()).permitAll())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/bidList/*").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/curvePoint/*").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/rating/*").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/ruleName/*").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/trade/*").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/user/*").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.accessDeniedPage("/accessDeniedPage"))
                .formLogin(form -> form
                        .loginPage("/showLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .defaultSuccessUrl("/bidList/list", true)
                        .permitAll())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutSuccessUrl("/login").permitAll()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(COOKIES))))
                .sessionManagement((session) -> {
                    session.maximumSessions(1).maxSessionsPreventsLogin(true);
                    session.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession);
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                })
                .build();
    }

}
