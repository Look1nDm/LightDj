package com.example.lightdj.web.security.expression;

import com.example.lightdj.domain.user.Role;
import com.example.lightdj.domain.user.User;
import com.example.lightdj.service.UserService;
import com.example.lightdj.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;

    public boolean canAccessUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return hasAnyRole(authentication, Role.USER);
    }
    public boolean canAccessOperator(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return hasAnyRole(authentication, Role.OPERATOR);
    }
    public boolean canAccessAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return hasAnyRole(authentication, Role.ADMIN);
    }
    public  boolean canAccessOperatorOrAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return hasAnyRole(authentication, Role.ADMIN, Role.OPERATOR);
    }
    private boolean hasAnyRole(Authentication authentication, Role...roles){
        for(Role role: roles){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            if (authentication.getAuthorities().contains(authority)){
                return true;
            }
        } return false;
    }
}
