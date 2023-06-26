package com.foodrec.backend.AccountAPI.command.account_tags;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.entity.AccountTag;
import com.foodrec.backend.AccountAPI.entity.AccountTagId;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.AccountAPI.repository.AccountTagRepository;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class AccountTagsCommandHandler implements Command.Handler<AccountTagsCommand, HttpStatus> {

    private final AccountRepository accountRepository;
    private final TagRepository tagRepository;
    private final AccountTagRepository accountTagRepository;

    public AccountTagsCommandHandler(AccountRepository accountRepository, TagRepository tagRepository, AccountTagRepository accountTagRepository) {
        this.accountRepository = accountRepository;
        this.tagRepository = tagRepository;
        this.accountTagRepository = accountTagRepository;
    }

    @Transactional
    @Override
    public HttpStatus handle(AccountTagsCommand command) {
        Optional<Account> optionalAccount = accountRepository.findById(command.getUserId());
        if (optionalAccount.isEmpty()) {
            throw new NotFoundExceptionHandler("Invalid Account !");
        }
        Account account = optionalAccount.get();

        Set<Tag> tags = tagRepository.getTagsByTagIdIn(command.getTagIds());
        accountTagRepository.deleteAccountTagsByAccount_UserId(command.getUserId());
        Account finalAccount = account;
        List<AccountTag> accountTags = tags.stream()
                .map(tag -> new AccountTag(new AccountTagId(finalAccount.getUserId(),
                        tag.getTagId()), finalAccount, tag))
                .toList();
        accountTagRepository.saveAll(accountTags);
        accountRepository.save(account);

        return HttpStatus.OK;
    }
}
