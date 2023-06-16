package com.client.ws.rasmooplus.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.repository.jpa.UserCredentialsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userCredentialsRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("Error! UserCredentials: not found!"));
    }
    
}
