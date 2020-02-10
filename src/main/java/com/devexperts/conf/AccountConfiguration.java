package com.devexperts.conf;

import com.devexperts.converter.Converter;
import com.devexperts.converter.StringToTransferRequestConverter;
import com.devexperts.model.TransferRequest;
import com.devexperts.validator.TransferRequestValidator;
import com.devexperts.validator.Validator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {

    @Bean(name = "com.devexperts.objectmapper")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean(name = "com.devexperts.converter.stringToTransferRequestConverter")
    public Converter<String, TransferRequest> stringToTransferRequestConverter(
            @Qualifier("com.devexperts.objectmapper") ObjectMapper objectMapper) {
        return new StringToTransferRequestConverter(objectMapper);
    }

    @Bean(name = "com.devexperts.validator.TransferRequestValidator")
    public Validator<TransferRequest> transferRequestValidator() {
        return new TransferRequestValidator();
    }


}
