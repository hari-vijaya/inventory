package com.codebasicz.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_authentication")
public class UserAuthentication implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "user_auth_seq")
    @Column(name = "id")
    private Integer id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "roles")
    private String roles;
    @Column(name = "password")
    private String password;
    @Column(name = "account_expiration")
    private Date accountExpiration;
    @Column(name = "is_account_locked")
    private Boolean isAccountLocked;
    @Column(name = "credentials_expiration")
    private Date credentialsExpiration;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_on")
    private Date createdOn;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_on")
    private Date updatedOn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountExpiration.after(new Date());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsExpiration.after(new Date());
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
