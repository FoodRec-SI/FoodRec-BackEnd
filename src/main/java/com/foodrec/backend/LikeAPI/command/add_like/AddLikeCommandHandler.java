package com.foodrec.backend.LikeAPI.command.add_like;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.LikeAPI.dto.LikeDTO;
import com.foodrec.backend.LikeAPI.entity.Likes;
import com.foodrec.backend.LikeAPI.entity.LikesCompositeKey;
import com.foodrec.backend.LikeAPI.repository.LikesRepository;
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
    @Autowired
    private LikesRepository likesRepository;
    public AddLikeCommandHandler(ModelMapper modelMapper,
                                 PostRepository postRepository,
                                 LikesRepository likesRepository){
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.likesRepository = likesRepository;
    }
    @Override
    public LikeDTO handle(AddLikeCommand command)
    throws InvalidDataExceptionHandler {
        /*Step 0: Creates a new likes entity with the new details from the command.*/
        String userId = command.getUserId();
        String postId = command.getPostId();
        Likes newLike = new Likes();
        LikesCompositeKey likesCompositeKey =
                new LikesCompositeKey(command.getUserId(), command.getPostId());
        newLike.setId(likesCompositeKey);

        /*Step 1: Gets the Liked Account and Post (Entity) to fill in
         the Account and Post fields of the Like entity.
         This is a MUST. If not, an error of "Missing Id will be thrown."*/
        Account likedAccount = accountRepository.findById(userId).get();
        Post likedPost = postRepository.findById(postId).get();

        //Only allows users to give likes to Posts with Status 2.
        if (likedPost.getStatus()!=2)
            throw new InvalidDataExceptionHandler("This Post is already deleted!");
        newLike.setPost(likedPost);
        newLike.setAccount(likedAccount);

        /*Step 2: Saves the new Like Entity into the Join Table (Likes) */
        likesRepository.save(newLike);

        //Query once again to make sure that the like is added.
        newLike = likesRepository.findById(likesCompositeKey).get();
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setPostId(newLike.getId().getPostId());
        likeDTO.setUserId(newLike.getId().getUserId());
        return likeDTO;
    }
}
