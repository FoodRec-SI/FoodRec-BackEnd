package com.foodrec.backend.RecipeAPI.command.update_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.RecipeAPI.dto.SimpleRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.UpdateRecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.entity.RecipeTag;
import com.foodrec.backend.RecipeAPI.entity.RecipeTagId;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.RecipeAPI.repository.RecipeTagRepository;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import com.foodrec.backend.Utils.ImageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UpdateRecipeCommandHandler implements Command.Handler<UpdateRecipeCommand, SimpleRecipeDTO> {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;
    private final RecipeTagRepository recipeTagRepository;

    public UpdateRecipeCommandHandler(RecipeRepository recipeRepository,
                                      ModelMapper modelMapper,
                                      TagRepository tagRepository,
                                      RecipeTagRepository recipeTagRepository) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
        this.recipeTagRepository = recipeTagRepository;
    }
    private boolean isDTOValid(UpdateRecipeDTO updateRecipeDTO, List<String> stringFields,
                               List<String> numericFields){
        boolean result = false;
        Field[] fields = updateRecipeDTO.getClass().getDeclaredFields();
        try{
            if(updateRecipeDTO.getTagIdSet().isEmpty()) return false;
            for(Field eachField:fields){
                eachField.setAccessible(true); //bắt buộc phải có. Nếu không thì nó sẽ không lấy giá trị của từng field được (v.d. giá trị của recipename là "Bánh xèo thơm ngon").
                String eachFieldName = eachField.getName();
                if(stringFields.contains(eachFieldName)){//nếu field đó là 1 string -> ko được trống
                    Object eachFieldValue = eachField.get(updateRecipeDTO);
                    if(eachFieldValue==null || eachFieldValue.toString().length()==0){
                        return result;
                    }
                }
                else if(numericFields.contains(eachFieldName)){//nếu field đó là 1 số -> ko được bé hơn 0
                    Integer eachFieldValue = Integer.parseInt(eachField.get(updateRecipeDTO).toString());
                    if(eachFieldValue<=0){
                        return result;
                    }
                }
            }
            result = true;
        }catch(IllegalAccessException e){
            return result;
        }catch ( NumberFormatException e){
            return result;
        }
        return result;
    }
    @Transactional
    @Override

    public SimpleRecipeDTO handle(UpdateRecipeCommand command) {
        ImageUtils imageUtils = new ImageUtils();
        List<String> stringFields = Arrays.asList(
            "recipeId","recipeName","description",
                "instructions","ingredientList","tagIdSet"
        );
        List<String> numericFields = Arrays.asList(
                "calories","duration"
        );
        UpdateRecipeDTO updateRecipeDTO = command.getUpdateRecipeDTO();
        if(!isDTOValid(updateRecipeDTO,stringFields,numericFields)){
            return new SimpleRecipeDTO("null", "One of the followings might have occurred: " +
                    "I. String fields (recipeId,recipeName,description,instructions,ingredientList)" +
                    " might be empty.   "+
                    "II. Numeric fields (calories,duration) might be less than or equal to 0.   " +
                    "III. The given recipe Tag Set might be empty or invalid.   " +
                    "Please try again.");
        }

        Optional<Recipe> recipeOptional = recipeRepository.findById(updateRecipeDTO.getRecipeId());
        if (recipeOptional.isEmpty() || !recipeOptional.get().isStatus()) {
            return new SimpleRecipeDTO("null", "The given RecipeId is non-existent!");
        }
        Recipe recipe = recipeOptional.get();
        if (!command.getUserId().equals(recipe.getUserId())) {
            return new SimpleRecipeDTO("null", "The logged-in user is NOT authorized " +
                    "to modify this recipe. Please log out and try again.");
        }

//        String imageUrl = null;
//        if(updateRecipeDTO.getImageUrl()==null
//                || updateRecipeDTO .getImageUrl().length()==0){
//            imageUrl = (String) imageUtils.upload(updateRecipeDTO.getImageFile(),
//                    "recipe", String.valueOf(UUID.randomUUID()));
//        }else{
//            imageUrl = updateRecipeDTO.getImageUrl();
//        }

        String imageUrl = updateRecipeDTO.getImageFile()==null
                ? updateRecipeDTO.getImageUrl()
                : imageUtils.updateImage(recipe.getImage(), updateRecipeDTO.getImageFile(),
                "profile", String.valueOf(UUID.randomUUID()));


        /*IMPORTANT: Updates for the Set<RecipeTag> MUST BE DONE on both the entity (Recipe)
        * and the Join Table (Recipe_Tag)*/

        Set<String> tagIdSet = updateRecipeDTO.getTagIdSet();
        Set<Tag> tags = tagRepository.getTagsByTagIdIn(tagIdSet);
        recipeTagRepository.deleteRecipeTagByRecipe_RecipeId(recipe.getRecipeId());
        Recipe finalRecipe = recipe;
        Set<RecipeTag> recipeTags = tags.stream()
                .map(tag -> new RecipeTag(new RecipeTagId(finalRecipe.getRecipeId(), tag.getTagId()), finalRecipe, tag))
                .collect(Collectors.toSet());

        recipeTagRepository.saveAll(recipeTags);
        recipe = modelMapper.map(updateRecipeDTO, Recipe.class);
        recipe.setUserId(command.getUserId());
        recipe.setStatus(true);
        recipe.setImage(imageUrl);
        recipe.setRecipeTags(recipeTags);
        recipeRepository.save(recipe);

        SimpleRecipeDTO simpleRecipeDTO = new SimpleRecipeDTO(recipe.getRecipeId(),"Successfully updated " +
                "details for the given Recipe.");
        return simpleRecipeDTO;
    }
}

