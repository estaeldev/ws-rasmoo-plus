package com.client.ws.rasmooplus.dto.wsraspay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    
    private String cpf;
    private String email;	
    private String firstName;
    private String id;
    private String lastName;

}
