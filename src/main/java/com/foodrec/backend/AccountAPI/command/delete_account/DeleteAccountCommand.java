package com.foodrec.backend.AccountAPI.command.delete_account;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.DeleteAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class DeleteAccountCommand implements Command<HttpStatus> {

    private final DeleteAccountDTO deleteAccountDTO;
    private final String userId;

}
