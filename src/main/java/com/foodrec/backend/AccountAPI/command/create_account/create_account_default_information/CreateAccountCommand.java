package com.foodrec.backend.AccountAPI.command.create_account.create_account_default_information;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.CreateAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
public class CreateAccountCommand implements Command<ResponseEntity> {

    private final CreateAccountDTO createAccountDTO;

}
