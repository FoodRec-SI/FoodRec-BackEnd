package com.foodrec.backend.AccountAPI.command.update_account.update_account_basic_data;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;
import com.foodrec.backend.AccountAPI.dto.UpdateAccountDTO;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Utils.ImageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Component
public class UpdateAccountCommandHandler implements Command.Handler<UpdateAccountCommand, AccountDTO> {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;


    public UpdateAccountCommandHandler(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    private String updateImage(String existingImage, MultipartFile image, String folder, String userId) {
        ImageUtils imageUtils = new ImageUtils();
        try {
            imageUtils.delete(existingImage);
            return (String) imageUtils.upload(image, folder, userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AccountDTO handle(UpdateAccountCommand command) {

        UpdateAccountDTO updateAccountDTO = command.getUpdateAccountDTO();
        Optional<Account> optionalAccount = accountRepository.findById(command.getUserId());
        if (optionalAccount.isEmpty()){
            throw new NotFoundExceptionHandler("Invalid Account !");
        }
        Account account = optionalAccount.get();

        String updatedDescription = updateAccountDTO.getDescription() == null
                ? account.getDescription() : updateAccountDTO.getDescription();

        String updatedProfileImageName = updateAccountDTO.getProfileImage() == null
                ? account.getProfileImageName()
                : updateImage("profile-" + command.getUserId(), updateAccountDTO.getProfileImage(), "profile", command.getUserId());

        String updatedBackgroundImageName = updateAccountDTO.getBackgroundImage() == null
                ? account.getBackgroundImageName()
                : updateImage("background-" + command.getUserId(), updateAccountDTO.getBackgroundImage(), "background", command.getUserId());

        account.setDescription(updatedDescription);
        account.setProfileImageName(updatedProfileImageName);
        account.setBackgroundImageName(updatedBackgroundImageName);
        accountRepository.save(account);
        return modelMapper.map(account, AccountDTO.class);
    }
}
