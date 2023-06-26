package com.foodrec.backend.AccountAPI.command.update_account;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;
import com.foodrec.backend.AccountAPI.dto.UpdateAccountDTO;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Utils.ImageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UpdateAccountCommandHandler implements Command.Handler<UpdateAccountCommand, AccountDTO> {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public UpdateAccountCommandHandler(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountDTO handle(UpdateAccountCommand command) {

        ImageUtils imageUtils = new ImageUtils();
        UpdateAccountDTO updateAccountDTO = command.getUpdateAccountDTO();
        Optional<Account> optionalAccount = accountRepository.findById(command.getUserId());
        if (optionalAccount.isEmpty()) {
            throw new NotFoundExceptionHandler("Invalid Account !");
        }
        Account account = optionalAccount.get();

        String updatedDescription = updateAccountDTO.getDescription() == null
                ? account.getDescription() : updateAccountDTO.getDescription();

        String updatedProfileImageName = updateAccountDTO.getProfileImage() == null
                ? account.getProfileImageName()
                : imageUtils.updateImage(account.getProfileImageName(), updateAccountDTO.getProfileImage(),
                "profile", String.valueOf(UUID.randomUUID()));

        String updatedBackgroundImageName = updateAccountDTO.getBackgroundImage() == null
                ? account.getBackgroundImageName()
                : imageUtils.updateImage(account.getBackgroundImageName(), updateAccountDTO.getBackgroundImage(),
                "background", String.valueOf(UUID.randomUUID()));

        account.setDescription(updatedDescription);
        account.setProfileImageName(updatedProfileImageName);
        account.setBackgroundImageName(updatedBackgroundImageName);
        accountRepository.save(account);
        return modelMapper.map(account, AccountDTO.class);
    }
}
