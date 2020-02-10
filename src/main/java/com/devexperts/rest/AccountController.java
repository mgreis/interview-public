package com.devexperts.rest;

import com.devexperts.account.Account;
import com.devexperts.converter.Converter;
import com.devexperts.model.TransferRequest;
import com.devexperts.service.ExtendedAccountService;
import com.devexperts.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/operations")
public class AccountController extends AbstractAccountController {

    @Autowired
    @Qualifier("com.devexperts.converter.stringToTransferRequestConverter")
    private Converter<String, TransferRequest> stringToTransferRequestConverter;

    @Autowired
    @Qualifier("com.devexperts.service.extendedAccountServiceImpl")
    private ExtendedAccountService extendedAccountService;

    @Autowired
    @Qualifier("com.devexperts.validator.TransferRequestValidator")
    private Validator<TransferRequest> transferRequestValidator;


    @PostMapping(value = "/transfer", produces = "application/json")
    public ResponseEntity<?> transfer(@RequestBody final String body) {
        Optional<TransferRequest> transferRequest = stringToTransferRequestConverter.convert(body);

        if (!transferRequest.isPresent()) {
            return new ResponseEntity<>("Unable to parse request", HttpStatus.BAD_REQUEST);
        }

        if (!transferRequestValidator.isValid(transferRequest.get())) {
            return new ResponseEntity<>("One of the parameters is not present or amout is invalid", HttpStatus.BAD_REQUEST);
        }

        Optional<Account> sourceAccount = extendedAccountService.getAccountExtended(transferRequest.get().getSourceId());
        if (!sourceAccount.isPresent()) {
            return new ResponseEntity<>("Source account not found", HttpStatus.NOT_FOUND);
        }

        Optional<Account> targetAccount = extendedAccountService.getAccountExtended(transferRequest.get().getTargetId());
        if (!targetAccount.isPresent()) {
            return new ResponseEntity<>("Target account not found", HttpStatus.NOT_FOUND);
        }

        if (transferRequest.get().getAmount() > sourceAccount.get().getBalance()) {
            return new ResponseEntity<>("Insufficient account balance", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (extendedAccountService.transferExtended(sourceAccount.get(),
                targetAccount.get(),
                transferRequest.get().getAmount())) {
            return new ResponseEntity<>("Successful transfer.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Something has gone wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    ResponseEntity<Void> transfer(long sourceId, long targetId, double amount) {
        return null;
    }
}
