package com.foodrec.backend.AccountAPI.command.account_tags;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Data
@AllArgsConstructor
public class AccountTagsCommand implements Command<HttpStatus> {

    private final Set<String> tagIds;
    private final String userId;

}
