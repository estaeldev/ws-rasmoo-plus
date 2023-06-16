package com.client.ws.rasmooplus.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.client.ws.rasmooplus.dto.PaymentProcessDto;
import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDto;
import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.exception.BusinessException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.integration.MailIntegration;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;
import com.client.ws.rasmooplus.mapper.UserCredentialsMapper;
import com.client.ws.rasmooplus.mapper.UserPaymentInfoMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CreditCardMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CustomerMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.OrderMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.PaymentMapper;
import com.client.ws.rasmooplus.model.jpa.SubscriptionType;
import com.client.ws.rasmooplus.model.jpa.User;
import com.client.ws.rasmooplus.model.jpa.UserCredentials;
import com.client.ws.rasmooplus.model.jpa.UserPaymentInfo;
import com.client.ws.rasmooplus.repository.jpa.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.repository.jpa.UserCredentialsRepository;
import com.client.ws.rasmooplus.repository.jpa.UserPaymentInfoRepository;
import com.client.ws.rasmooplus.repository.jpa.UserRepository;
import com.client.ws.rasmooplus.service.PaymentInfoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Value("${webservices.rasplus.default.password}")
    private String defaultPassword;

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final UserPaymentInfoRepository userPaymentInfoRepository;
    private final SubscriptionTypeRepository subscriptionTypeRepository;

    private final WsRaspayIntegration wsRaspayIntegration;
    private final MailIntegration mailIntegration;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Boolean process(PaymentProcessDto dto) {
        Optional<User> userOptional = this.userRepository.findById(dto.getUserPaymentInfoDto().getUserId());

        return userOptional.map(user -> {
            if(Objects.nonNull(user.getSubscriptionType())) {
                throw new BusinessException("Pagamento não pode ser processado pois usuário já possui assinatura.");
            }

            CustomerDto customerDto = this.wsRaspayIntegration.createCustomer(CustomerMapper.build(user));
            OrderDto orderDto = this.wsRaspayIntegration.createOrder(OrderMapper.build(customerDto.getId(), dto));
            
            CreditCardDto creditCardDto = CreditCardMapper.build(dto.getUserPaymentInfoDto(), user.getCpf());
            Boolean processPayment = this.wsRaspayIntegration.processPayment(PaymentMapper.build(creditCardDto, orderDto));

            if(Boolean.TRUE.equals(processPayment)) {
                UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(), user);
                this.userPaymentInfoRepository.save(userPaymentInfo);

                Optional<SubscriptionType> subscriptionTypeOpt = this.subscriptionTypeRepository.findByProductKey(dto.getProductKey());
                if(subscriptionTypeOpt.isEmpty()) {
                    throw new NotFoundException("SubscriptionType not found!");
                }
                
                user.setSubscriptionType(subscriptionTypeOpt.get());
                this.userRepository.save(user);

                UserCredentials userCredentials = UserCredentialsMapper.build(user, passwordEncoder, defaultPassword);
                this.userCredentialsRepository.save(userCredentials);
                
                this.mailIntegration.send(user.getEmail(), 
                    "Usuario: " + user.getEmail() + " - Senha: " + defaultPassword, "ACESSO LIBERADO!");

                return Boolean.TRUE;
            }

            return Boolean.FALSE;
            
            
        }).orElseThrow(() -> new NotFoundException("Error! User: usuário não encontrado"));

    }
    
}
