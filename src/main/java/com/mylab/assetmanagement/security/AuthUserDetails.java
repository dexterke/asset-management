package com.mylab.assetmanagement.security;


import com.mylab.assetmanagement.entity.UserEntity;
import com.mylab.assetmanagement.entity.UserRoleEntity;
import com.mylab.assetmanagement.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class AuthUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;

    public AuthUserDetails(UserRepository userRepository, UserEntity user) {
        this.username = user.getUsername();
//        this.password = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(user.getPassword());
        List<UserRoleEntity> userRoles = userRepository.getRoles(user.getId());
        this.authorities = translate(userRoles);
    }

    private Collection<? extends GrantedAuthority> translate(List<UserRoleEntity> roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for ( UserRoleEntity role : roles) {
            String name = role.getRoleEntity().getName().toUpperCase();
            if (!name.startsWith("ROLE_")) {
                name = "ROLE_" + name;
            }
            grantedAuthorities.add(new SimpleGrantedAuthority(name));
        }
        return grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

