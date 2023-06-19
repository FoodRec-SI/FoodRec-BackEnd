package com.foodrec.backend.AccountAPI.query.get_account_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetAccountByIdQueryHandler implements Command.Handler<GetAccountByIdQuery, AccountDTO> {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public GetAccountByIdQueryHandler(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountDTO handle(GetAccountByIdQuery command) {
        Optional<Account> optionalAccount = accountRepository.findById(command.getUserId());
        if (optionalAccount.isEmpty()){
            throw new NotFoundExceptionHandler("Invalid Account !");
        }
        Account account = optionalAccount.get();
        return  modelMapper.map(account, AccountDTO.class);
    }
}
