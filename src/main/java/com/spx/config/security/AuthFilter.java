package com.spx.config.security;

import com.spx.dao.UserDao;
import com.spx.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;

/**
 * Created by Bogdan1 on 09.09.2016.
 */
@Deprecated
public class AuthFilter implements Filter {
        private UserDao userDao;

        public AuthFilter(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        public void destroy() {
            // Do nothing
        }

        @Override
        public void doFilter(ServletRequest req, ServletResponse res,
                             FilterChain chain) throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) req;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                try {
                    Principal principal = (Principal) authentication.getPrincipal();
                    if ((userDao.getUserByLogin(principal.getName(), false).size() == 0) &&
                            (userDao.getUserByLogin(principal.getName(), true).size() == 0)) {
                        UserEntity externalUser = new UserEntity();
                        UserEntity userEntity = new UserEntity();
                        userEntity.setLogin(principal.getName());
                        userEntity.setExternal(true);
                    }
                }
                catch (ClassCastException ex) {

                }
            }
            chain.doFilter(req, res);

        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {
            // Do nothing
        }


}
