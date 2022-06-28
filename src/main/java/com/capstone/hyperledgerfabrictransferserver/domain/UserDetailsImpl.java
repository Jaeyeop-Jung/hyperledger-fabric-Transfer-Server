package com.capstone.hyperledgerfabrictransferserver.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailsImpl implements UserDetails {

    private Long id;

    private Long studentId;

    private String password;

    private UserRole userRole;

    private String name;

    private UserDetailsImpl(Long id, Long studentId, String password, UserRole userRole, String name) {
        this.id = id;
        this.studentId = studentId;
        this.password = password;
        this.userRole = userRole;
        this.name = name;
    }

    public static UserDetailsImpl of(Long id, Long studentId, String password, UserRole userRole, String name){
        return new UserDetailsImpl(id, studentId, password, userRole, name);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return getUserRole().toString();
            }
        });

        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
