package com.codebasicz.inventory.security;

import com.codebasicz.inventory.entity.UserAuthentication;
import com.codebasicz.inventory.repository.UserAuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsManager implements UserDetailsManager {

    private final UserAuthenticationRepository userAuthenticationRepository;

    @Override
    public void createUser(UserDetails user) {
        userAuthenticationRepository.save((UserAuthentication) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        userAuthenticationRepository.save((UserAuthentication) user);
    }

    @Override
    public void deleteUser(String username) {
        UserAuthentication userDetails = userAuthenticationRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No User found for username -> " + username));
        userAuthenticationRepository.delete(userDetails);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        UserAuthentication userDetails = userAuthenticationRepository.findByPassword(oldPassword)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid password "));
        userDetails.setPassword(newPassword);
        userAuthenticationRepository.save(userDetails);
    }

    @Override
    public boolean userExists(String username) {
        return userAuthenticationRepository.findByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthenticationRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("No user found with username = " + username));
    }
}
