package com.devexperts.converter;

import com.devexperts.model.TransferRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class StringToTransferRequestConverter implements Converter<String, TransferRequest> {

    Logger logger = LoggerFactory.getLogger(StringToTransferRequestConverter.class);


    private ObjectMapper objectMapper;

    public StringToTransferRequestConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

    }

    @Override
    public Optional<TransferRequest> convert(String input) {
        try {
            return Optional.of(objectMapper.readValue(input, TransferRequest.class));
        } catch (JsonProcessingException e) {
            logger.info("message='Unable to parse TransferRequest' class=StringToBusinessSectorApiResponseConverter method=convert ", e);
            return Optional.empty();
        }
    }
}
