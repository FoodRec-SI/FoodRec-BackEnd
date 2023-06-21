package com.foodrec.backend.AccountAPI.command.create_account.create_account_tags;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Collection;

@Data
@AllArgsConstructor
public class CreateAccountTagsCommand implements Command<HttpStatus> {

    private final Collection<String> tagIds;
    private final String userId;

}
