package com.spx.config;

import com.spx.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by timofb on 12-Jul-16.
 */
@Configuration
@EnableWebSecurity
@PropertySource(Application.PROPERTIES_PATH)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**");
    }

    @Configuration
    @EnableOAuth2Sso
    @Order(1)
    public static class ExternalWebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(final HttpSecurity http) throws Exception {

            http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/resources/**").permitAll()
                    .antMatchers("/register/**").permitAll()
                    .antMatchers("/construct/**").permitAll()
                    .antMatchers("/rest/user/register/**").permitAll()
                    .antMatchers("/rest/user/formlogin/**").permitAll()
                    .anyRequest()
                    .authenticated().and().logout().logoutSuccessUrl("/").permitAll()
                    .and().exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
                    .and().csrf().csrfTokenRepository(csrfTokenRepository())
                    .and().addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);

        }


        private Filter csrfHeaderFilter() {
            return new OncePerRequestFilter() {
                @Override
                protected void doFilterInternal(HttpServletRequest request,
                                                HttpServletResponse response, FilterChain filterChain)
                        throws ServletException, IOException {
                    CsrfToken csrf = (CsrfToken) request
                            .getAttribute(CsrfToken.class.getName());
                    if (csrf != null) {
                        Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                        String token = csrf.getToken();
                        if (cookie == null
                                || token != null && !token.equals(cookie.getValue())) {
                            cookie = new Cookie("XSRF-TOKEN", token);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                    }
                    filterChain.doFilter(request, response);
                }
            };
        }

        private CsrfTokenRepository csrfTokenRepository() {
            HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
            repository.setHeaderName("X-XSRF-TOKEN");
            return repository;
        }
    }


    @Configuration
    @Order(2)
    @EnableGlobalMethodSecurity(securedEnabled = true)
    public static class FormWebSecurityConfig extends WebSecurityConfigurerAdapter{

        @Autowired
        private Environment env;



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
            http.csrf().disable();
            http.authorizeRequests()
                    .expressionHandler(webExpressionHandler())
                    .antMatchers("/").permitAll() // '/**' permit all requests, change it to '/'.
                    .antMatchers("/register/**").permitAll()
                    .antMatchers("/construct/**").permitAll()
                    .antMatchers("/rest/user/register/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/")
                    .defaultSuccessUrl("/dashboard")
                    .failureUrl("/login?auth=fail")
                    .permitAll()
                    .and().logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll()
                    .and().rememberMe().tokenValiditySeconds(Integer.MAX_VALUE);


        }

    }

}
