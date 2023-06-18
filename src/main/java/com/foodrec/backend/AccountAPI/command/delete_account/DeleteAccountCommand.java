package com.foodrec.backend.AccountAPI.command.delete_account;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.DeleteAccountDTO;
import org.springframework.http.HttpStatus;

public class DeleteAccountCommand implements Command<HttpStatus> {

    private final DeleteAccountDTO deleteAccountDTO;
    private final String userId;

    public DeleteAccountCommand(DeleteAccountDTO deleteAccountDTO, String userId) {
        this.deleteAccountDTO = deleteAccountDTO;
        this.userId = userId;
    }

    public DeleteAccountDTO getDeleteAccountDTO() {
        return deleteAccountDTO;
    }

    public String getUserId() {
        return userId;
    }
}
