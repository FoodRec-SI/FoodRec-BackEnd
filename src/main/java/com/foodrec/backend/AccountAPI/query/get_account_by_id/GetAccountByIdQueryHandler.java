package com.foodrec.backend.AccountAPI.query.get_account_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.dto.AccountDTO;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class GetAccountByIdQueryHandler implements Command.Handler<GetAccountByIdQuery, AccountDTO> {

    private final AccountRepository accountRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public GetAccountByIdQueryHandler(AccountRepository accountRepository, TagRepository tagRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountDTO handle(GetAccountByIdQuery command) {
        Optional<Account> optionalAccount = accountRepository.findById(command.getUserId());
        if (optionalAccount.isEmpty()) {
            throw new NotFoundExceptionHandler("Invalid Account !");
        }
        Account account = optionalAccount.get();
        Collection<TagDTO> tagDTOCollection = tagRepository.getTagsByAccountTags_Account(account)
                .stream().map(tag -> modelMapper.map(tag, TagDTO.class)).toList();
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        accountDTO.setTagsCollection(tagDTOCollection);
        return accountDTO;
    }
}
