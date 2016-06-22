package com.spx.config;

import com.spx.config.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@PropertySource(Application.PROPERTIES_PATH)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    private static RoleHierarchyImpl roleHierarchy() {
        final RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("" +
                        "ROLE_SYSTEM_ADMIN > ROLE_ADMIN" +
                        "ROLE_ADMIN > ROLE_ENGINEER" +
                        "ROLE_ENGINEER > ROLE_USER"
        );
        return roleHierarchy;
    }

    private static SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        final DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy());
        return handler;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        //final String hostIp = env.getProperty(Application.PROPERTY_SERVICE_HOST_IP);


        http.csrf().disable();
        http.authorizeRequests()
                .expressionHandler(webExpressionHandler())
                .antMatchers("/").permitAll() // '/**' permit all requests, change it to '/'.
                .antMatchers("/rest/service/**").permitAll() //TODO: check if it service machine trying to access
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/test")
                .failureUrl("/login?auth=fail")
                .permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?auth=logout").permitAll()
                .and().rememberMe().tokenValiditySeconds(Integer.MAX_VALUE);
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {

        web.ignoring()
                .antMatchers("/resources/**");

    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
        
/*        auth.inMemoryAuthentication().withUser("timofb").password("timofb").roles("SYSTEM_ADMIN");
        auth.inMemoryAuthentication().withUser("normal").password("normal").roles("USER");
        auth.inMemoryAuthentication().withUser("engineer").password("engineer").roles("ENGINEER");*/
    }

}