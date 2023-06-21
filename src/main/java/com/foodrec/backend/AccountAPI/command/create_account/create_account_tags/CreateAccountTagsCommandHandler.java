package com.foodrec.backend.AccountAPI.command.create_account.create_account_tags;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class CreateAccountTagsCommandHandler implements Command.Handler<CreateAccountTagsCommand, HttpStatus> {

    private final AccountRepository accountRepository;
    private final TagRepository tagRepository;

    public CreateAccountTagsCommandHandler(AccountRepository accountRepository, TagRepository tagRepository) {
        this.accountRepository = accountRepository;
        this.tagRepository = tagRepository;
    }
    @Transactional
    @Override
    public HttpStatus handle(CreateAccountTagsCommand command) {
        Optional<Account> optionalAccount = accountRepository.findById(command.getUserId());
        if (optionalAccount.isEmpty()){
            throw new NotFoundExceptionHandler("Invalid Account !");
        }
        Account account = optionalAccount.get();
        account.setAccountTags(tagRepository.getTagsByTagIdIn(command.getTagIds()));
        accountRepository.save(account);
        return HttpStatus.OK;
    }
}
