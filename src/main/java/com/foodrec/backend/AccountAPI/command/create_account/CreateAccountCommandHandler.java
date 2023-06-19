package com.foodrec.backend.AccountAPI.command.create_account;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.CreateAccountDTO;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateAccountCommandHandler implements Command.Handler<CreateAccountCommand, HttpStatus> {

    private final AccountRepository accountRepository;

    public CreateAccountCommandHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public HttpStatus handle(CreateAccountCommand command) {
        CreateAccountDTO createAccountDTO = command.getCreateAccountDTO();
        Optional<Account> optionalAccount = accountRepository.findById(createAccountDTO.getUserId());
        if (optionalAccount.isPresent()){
            return HttpStatus.OK;
        }
        Account account = new Account();
        account.setUserId(createAccountDTO.getUserId());
        account.setName(createAccountDTO.getUserName());
        account.setProfileImageName(createAccountDTO.getProfileImage());
        account.setBackgroundImageName(createAccountDTO.getBackgroundImage());
        accountRepository.save(account);
        return HttpStatus.OK;
    }
}
