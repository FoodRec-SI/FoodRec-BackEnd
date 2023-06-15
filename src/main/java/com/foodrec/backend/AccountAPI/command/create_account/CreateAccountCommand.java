package com.foodrec.backend.AccountAPI.command.create_account;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.CreateAccountDTO;
import org.springframework.http.HttpStatus;

public class CreateAccountCommand implements Command<HttpStatus> {

    private final CreateAccountDTO createAccountDTO;

    public CreateAccountCommand(CreateAccountDTO createAccountDTO) {
        this.createAccountDTO = createAccountDTO;
    }

    public CreateAccountDTO getCreateAccountDTO() {
        return createAccountDTO;
    }
}
