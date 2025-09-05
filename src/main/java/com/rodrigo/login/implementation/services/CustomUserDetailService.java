package com.rodrigo.login.implementation.services;

import com.rodrigo.login.common.exception.custom.InvalidLoginException;
import com.rodrigo.login.implementation.entity.User;
import com.rodrigo.login.implementation.model.CustomUserDetail;
import com.rodrigo.login.implementation.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository repository;
    private final MessageService messageService;

    public CustomUserDetailService(UserRepository repository, MessageService messageService) {
        this.repository = repository;
        this.messageService = messageService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByLogin(username)
                .orElseThrow(() -> new InvalidLoginException(
                        messageService.getMessage("user.not.found")
                ));

        return new CustomUserDetail(user);
    }
}
