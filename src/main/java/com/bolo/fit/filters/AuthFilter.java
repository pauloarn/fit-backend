package com.bolo.fit.filters;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.model.Users;
import com.bolo.fit.service.UserService;
import com.bolo.fit.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        validateToken(request);
        getUserLogged(request);
        filterChain.doFilter(request, response);
    }

    private void validateToken(HttpServletRequest request) {
        String token = recoverToken(request);
        if(Objects.nonNull(token)){
            try {
                String email = tokenUtils.validateToken(token);
                Users user = userService.findUserByEmail(email);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ApiErrorException e) {
                throw new RuntimeException(MessageEnum.INVALID_TOKEN.getMessage());
            }
        }
    }

    private String recoverToken(HttpServletRequest request){
        var authorization = request.getHeader("Authorization");
        if(Objects.isNull(authorization)) return null;
        return authorization.replace("Bearer ", "");
    }
    private void getUserLogged(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
    }
}
