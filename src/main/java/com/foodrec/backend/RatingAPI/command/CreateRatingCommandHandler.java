package com.foodrec.backend.RatingAPI.command;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.RatingAPI.dto.CreateRatingDTO;
import com.foodrec.backend.RatingAPI.dto.RatingDTO;
import com.foodrec.backend.RatingAPI.entity.Rating;
import com.foodrec.backend.RatingAPI.repository.RatingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CreateRatingCommandHandler implements Command.Handler<CreateRatingCommand, RatingDTO> {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CreateRatingCommandHandler(RatingRepository ratingRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public RatingDTO handle(CreateRatingCommand command) {
        CreateRatingDTO createRatingDTO = command.getCreateRatingDTO();
        String userId = command.getUserId();
        String postId = createRatingDTO.getPostId();

        Rating rating = null;
        rating = modelMapper.map(createRatingDTO,Rating.class);
        rating.setUserId(userId);

        ratingRepository.save(rating);

        //Gets the full data of the created/update recipe.
        rating = ratingRepository.findRatingByUserIdAndPostId(userId, postId);
        RatingDTO ratingDTO = modelMapper.map(rating, RatingDTO.class);
        return ratingDTO;
    }
}
