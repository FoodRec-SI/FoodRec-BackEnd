package com.foodrec.backend.RatingAPI.command.create_rating;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.RatingAPI.dto.CreateRatingDTO;
import com.foodrec.backend.RatingAPI.dto.RatingDTO;
import com.foodrec.backend.RatingAPI.entity.Rating;
import com.foodrec.backend.RatingAPI.entity.RatingCompositeKey;
import com.foodrec.backend.RatingAPI.repository.RatingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Component
public class CreateRatingCommandHandler implements Command.Handler<CreateRatingCommand, RatingDTO> {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AccountRepository accountRepository;

    public CreateRatingCommandHandler(RatingRepository ratingRepository,
                                      ModelMapper modelMapper,
                                      PostRepository postRepository,
                                      AccountRepository accountRepository) {
        this.modelMapper = modelMapper;
        this.ratingRepository = ratingRepository;
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public RatingDTO handle(CreateRatingCommand command) {
        //Step 0: Creates a new Rating entity with the rating Details from the Command
        CreateRatingDTO createRatingDTO = command.getCreateRatingDTO();
        String userId = command.getUserId();
        String postId = createRatingDTO.getPostId();

        /*Gets the Liked Account and Post (Entity) to fill in
         the Account and Post fields of the Rating.*/
        Account likedAccount = accountRepository.findById(userId).get();
        Post likedPost = postRepository.findById(postId).get();

        Rating rating = new Rating();
        rating.setId(new RatingCompositeKey(userId, postId));
        rating.setScore(createRatingDTO.getScore());
        rating.setPost(likedPost);
        rating.setAccount(likedAccount);




        /*Step 2: Saves the newest Rating into the Join Table (Rating).*/
        ratingRepository.save(rating);
        /*Step 3: Calculates and updates the average rating
        of the given Post.*/
        int totalScore = 0;
        int count = 0;

        List<Rating> ratings = ratingRepository
                .getRatingsByPost_PostId(postId);
        for(Rating eachRating:ratings){
            totalScore+=eachRating.getScore();
            count++;
        }

        double avgScore = (double)totalScore/count;
        BigDecimal bd = new BigDecimal(Double.toString(avgScore));
        bd = bd.setScale(1, RoundingMode.UP);
        avgScore = bd.doubleValue();
        likedPost.setAverageScore(avgScore);
        postRepository.save(likedPost);


        //Gets the full data of the created/update rating in the DTO format.
        rating = ratingRepository.findRatingByAccount_UserIdAndPost_PostId(userId, postId);
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setPostId(rating.getId().getPostId());
        ratingDTO.setUserId(rating.getId().getUserId());
        ratingDTO.setScore(rating.getScore());
        return ratingDTO;
    }
}
