package com.foodrec.backend.AccountAPI.command.update_account;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;
import com.foodrec.backend.AccountAPI.dto.UpdateAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateAccountCommand implements Command<AccountDTO> {

    private final UpdateAccountDTO updateAccountDTO;
    private final String userId;

}
