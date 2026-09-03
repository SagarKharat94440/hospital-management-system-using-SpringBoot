package com.codingshuttle.youtube.hospitalManagement.security;

import com.codingshuttle.youtube.hospitalManagement.entity.User;
import com.codingshuttle.youtube.hospitalManagement.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private  final UserRepository userRepository;
    private  final AuthUtil authUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final  String Header = request.getHeader("Authorization");
        if(Header== null || !Header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = Header.split("Bearer ")[1];
        String username= authUtil.getUserNameFromToken(token);

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            User user= userRepository.findByUsername(username).orElseThrow();
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);
    }
}
