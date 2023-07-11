package com.foodrec.backend.LikeAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.LikeAPI.command.add_like.AddLikeCommand;
import com.foodrec.backend.LikeAPI.command.remove_like.RemoveLikeCommand;
import com.foodrec.backend.LikeAPI.dto.DeleteLikeDTO;
import com.foodrec.backend.LikeAPI.dto.LikeDTO;
import com.foodrec.backend.LikeAPI.dto.NewLikeDTO;
import com.foodrec.backend.Utils.GetCurrentUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "LikeAPI")
@RestController
public class LikeCommandController {
    final Pipeline pipeline;

    public LikeCommandController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(
            description = "Allows the signed-in user to Like a post.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/like", method = RequestMethod.POST)
    public ResponseEntity addLike(@RequestBody NewLikeDTO newLikedUserDTO){
        ResponseEntity result = null;
        Authentication authentication = null;
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            AddLikeCommand command = new AddLikeCommand(newLikedUserDTO.getPostId(), userId);
            LikeDTO likeDTO = pipeline.send(command);
            if (likeDTO != null) {
                result = new ResponseEntity(likeDTO, HttpStatus.OK);
            }
        }catch (InvalidDataExceptionHandler e){
            result = new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }catch(Exception e){
            result = new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
        return result;
    }

    @Operation(
            description = "Allows the signed-in user to Like a post.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/like", method = RequestMethod.DELETE)
    public ResponseEntity removeLike(@RequestBody DeleteLikeDTO deleteLikeDTO){
        ResponseEntity result = null;
        Authentication authentication = null;
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            RemoveLikeCommand command = new RemoveLikeCommand(userId, deleteLikeDTO.getPostId());
            Boolean isRemoved = pipeline.send(command);
            if (isRemoved) {
                result = new ResponseEntity("Successfully removed like for post with Id "+deleteLikeDTO.getPostId()
                        , HttpStatus.OK);
            }
        }catch(Exception e){
            e.getMessage();
        }
        return result;
    }
}
