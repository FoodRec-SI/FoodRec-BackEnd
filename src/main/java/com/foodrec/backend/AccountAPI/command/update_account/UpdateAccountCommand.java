package com.foodrec.backend.AccountAPI.command.update_account;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;
import com.foodrec.backend.AccountAPI.dto.UpdateAccountDTO;

public class UpdateAccountCommand implements Command<AccountDTO> {

    private final UpdateAccountDTO updateAccountDTO;
    private final String userId;


    public UpdateAccountCommand(UpdateAccountDTO updateAccountDTO, String userId) {
        this.updateAccountDTO = updateAccountDTO;
        this.userId = userId;
    }

    public UpdateAccountDTO getUpdateAccountDTO() {
        return updateAccountDTO;
    }

    public String getUserId() {
        return userId;
    }
}
