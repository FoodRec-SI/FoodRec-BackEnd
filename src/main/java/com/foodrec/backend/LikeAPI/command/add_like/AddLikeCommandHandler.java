package com.foodrec.backend.LikeAPI.command.add_like;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.LikeAPI.dto.LikeDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AddLikeCommandHandler implements Command.Handler<AddLikeCommand, LikeDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PostRepository postRepository;
    public AddLikeCommandHandler(ModelMapper modelMapper,
                                 PostRepository postRepository){
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }
    @Override
    public LikeDTO handle(AddLikeCommand command) {
        Set<Account> accountSet = null;
            Account account = accountRepository.findById(command.getUserId()).get();
        Post post = postRepository.findById(command.getPostId()).get();

        accountSet = post.getAccounts();
        accountSet.add(account);
        post.setAccounts(accountSet);
        postRepository.save(post);
        //Query once again to make sure that the like is added.
        //If yes, then map the LikedUser Entity to the LikedUserDTO, then return the
        //like details to the front end.

        return null;
    }
}
