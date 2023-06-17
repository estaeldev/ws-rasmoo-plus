package com.client.ws.rasmooplus.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.integration.MailIntegration;
import com.client.ws.rasmooplus.model.jpa.UserCredentials;
import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.repository.jpa.UserCredentialsRepository;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.RecoveryCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, RecoveryCode {

    private final UserCredentialsRepository userCredentialsRepository;
    private final UserRecoveryCodeRepository userRecoveryCodeRepository;
    private final MailIntegration mailIntegration;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userCredentialsRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("Error! UserCredentials: not found!"));
    }


    @Override
    public Object sendRecoveryCode(String email) {
        String code = String.format("%06d", new Random().nextInt(10000));
        UserRecoveryCode userRecoveryCode;
        Optional<UserRecoveryCode> userRecoveryCodeOpt = this.userRecoveryCodeRepository.findByEmail(email);

        if(userRecoveryCodeOpt.isEmpty()) {

            Optional<UserCredentials> userCredentialsOpt = this.userCredentialsRepository.findByUsername(email);

            if(userCredentialsOpt.isEmpty()) {
                throw new NotFoundException("Usuario não encontrado!");
            }

            userRecoveryCode = UserRecoveryCode.builder()
                .email(userCredentialsOpt.get().getUsername())
                .build();

        } else {
            userRecoveryCode = userRecoveryCodeOpt.get();
        }

        userRecoveryCode.setCode(code);
        userRecoveryCode.setCreateDate(LocalDateTime.now());

        this.userRecoveryCodeRepository.save(userRecoveryCode);

        sendEmail(userRecoveryCode.getEmail(), "Codigo de recuperação de conta: " + code, "CODIGO DE ACESSO!");

        return null;

    }
    

    private void sendEmail(String mailTo, String message, String subject) {
        this.mailIntegration.send(mailTo, message, subject);
    }

}
