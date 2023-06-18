package com.foodrec.backend.AccountAPI.query.get_account_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;

public class GetAccountByIdQuery implements Command<AccountDTO> {

    private String userId;

    public GetAccountByIdQuery() {
    }

    public GetAccountByIdQuery(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
