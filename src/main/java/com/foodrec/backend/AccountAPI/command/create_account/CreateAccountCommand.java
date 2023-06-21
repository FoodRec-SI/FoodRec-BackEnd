package com.foodrec.backend.AccountAPI.command.create_account;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.CreateAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CreateAccountCommand implements Command<HttpStatus> {

    private final CreateAccountDTO createAccountDTO;

}
