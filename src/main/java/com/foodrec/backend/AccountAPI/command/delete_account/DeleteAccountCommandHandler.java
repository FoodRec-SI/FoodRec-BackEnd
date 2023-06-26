package com.foodrec.backend.AccountAPI.command.delete_account;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;
import com.foodrec.backend.AccountAPI.dto.DeleteAccountDTO;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Utils.ImageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
        ImageUtils imageUtils = new ImageUtils();
        DeleteAccountDTO deleteAccountDTO = command.getDeleteAccountDTO();
        Optional<Account> optionalAccount = accountRepository.findById(command.getUserId());
        if (optionalAccount.isEmpty()) {
            throw new NotFoundExceptionHandler("Invalid Account !");
        }
        Account account = optionalAccount.get();

        if (deleteAccountDTO.isDescriptionDeletion()) {
            account.setDescription(null);
        }

        if (deleteAccountDTO.isProfileImageDeletion()) {
            try {
                imageUtils.deleteImage(account.getProfileImageName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String defaultProfileImage = "https://storage.googleapis.com/foodrec-389515.appspot.com/profile-default.jpg";
            account.setProfileImageName(defaultProfileImage);
        }

        if (deleteAccountDTO.isBackgroundImageDeletion()) {
            try {
                imageUtils.deleteImage(account.getBackgroundImageName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String defaultBackgroundImage = "https://storage.googleapis.com/foodrec-389515.appspot.com/background-default.jpg";
            account.setBackgroundImageName(defaultBackgroundImage);
        }

        accountRepository.save(account);
        modelMapper.map(account, AccountDTO.class);
        return HttpStatus.OK;
    }
}
