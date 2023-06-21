package com.foodrec.backend.AccountAPI.command.delete_account.delete_account_information;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;
import com.foodrec.backend.AccountAPI.dto.DeleteAccountDTO;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteAccountCommandHandler implements Command.Handler<DeleteAccountCommand, HttpStatus> {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public DeleteAccountCommandHandler(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public HttpStatus handle(DeleteAccountCommand command) {

        DeleteAccountDTO deleteAccountDTO = command.getDeleteAccountDTO();
        Optional<Account> optionalAccount = accountRepository.findById(command.getUserId());
        if (optionalAccount.isEmpty()){
            throw new NotFoundExceptionHandler("Invalid Account !");
        }
        Account account = optionalAccount.get();

        if (deleteAccountDTO.isDescriptionDeletion()){
            account.setDescription(null);
        }

        if (deleteAccountDTO.isProfileImageDeletion()){
            String deletedProfileImage = "https://storage.googleapis.com/foodrec-389515.appspot.com/profile-default.jpg";
            account.setProfileImageName(deletedProfileImage);
        }

        if (deleteAccountDTO.isBackgroundImageDeletion()){
            String deletedBackgroundImage = "https://storage.googleapis.com/foodrec-389515.appspot.com/background-default.jpg";
            account.setBackgroundImageName(deletedBackgroundImage);
        }

        accountRepository.save(account);
        modelMapper.map(account, AccountDTO.class);
        return HttpStatus.OK;
    }
}
