package com.foodrec.backend.AccountAPI.query.get_account_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAccountByIdQuery implements Command<AccountDTO> {

    private final String userId;

}
