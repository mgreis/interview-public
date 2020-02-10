package com.devexperts.validator;

import com.devexperts.model.TransferRequest;

public class TransferRequestValidator implements Validator<TransferRequest> {
    @Override
    public boolean isValid(TransferRequest input) {
        return input.getAmount() > 0;
    }
}
