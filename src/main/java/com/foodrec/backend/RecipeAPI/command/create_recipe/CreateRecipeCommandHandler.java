package com.foodrec.backend.RecipeAPI.command.create_recipe;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;

import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.entity.RecipeTag;
import com.foodrec.backend.RecipeAPI.entity.RecipeTagId;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.RecipeAPI.repository.RecipeTagRepository;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import com.foodrec.backend.Utils.IdGenerator;
import com.foodrec.backend.Utils.ImageUtils;
import com.foodrec.backend.Utils.RecipeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Component
public class CreateRecipeCommandHandler implements Command.Handler<CreateRecipeCommand, String> {

    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final RecipeUtils recipeUtils;
    private final TagRepository tagRepository;
    private final RecipeTagRepository recipeTagRepository;
    private boolean isDTOValid(CreateRecipeDTO createRecipeDTO, List<String> stringFields,
                               List<String> numericFields){
        boolean result = false;
        Field[] fields = createRecipeDTO.getClass().getDeclaredFields();
        try{
            if(createRecipeDTO.getTagIdSet().isEmpty()) return false;
            for(Field eachField:fields){
                eachField.setAccessible(true); //bắt buộc phải có. Nếu không thì nó sẽ không lấy giá trị của từng field được (v.d. giá trị của recipename là "Bánh xèo thơm ngon").
                String eachFieldName = eachField.getName();
                if(stringFields.contains(eachFieldName)){//nếu field đó là 1 string -> ko được trống
                    Object eachFieldValue = eachField.get(createRecipeDTO);
                    if(eachFieldValue==null || eachFieldValue.toString().length()==0){
                        return result;
                    }
                }
                else if(numericFields.contains(eachFieldName)){//nếu field đó là 1 số -> ko được bé hơn 0
                    Integer eachFieldValue = Integer.parseInt(eachField.get(createRecipeDTO).toString());
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
    public CreateRecipeCommandHandler(RecipeRepository recipeRepository,
                                      RecipeUtils recipeUtils,
                                      ModelMapper modelMapper,
                                      TagRepository tagRepository,
                                      RecipeTagRepository recipeTagRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeUtils = recipeUtils;
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
        this.recipeTagRepository = recipeTagRepository;
    }

    @Transactional
    @Override
    public String handle(CreateRecipeCommand createRecipeCommand) {
        ImageUtils imageUtils = new ImageUtils();
        CreateRecipeDTO createRecipeDTO = createRecipeCommand.getCreateRecipeDTO();
        List<String> stringFields = Arrays.asList(
                "recipeName","description",
                "instructions","imageFile","ingredientList","tagIdSet"
        );
        List<String> numericFields = Arrays.asList(
                "calories","duration"
        );
        boolean isValid = isDTOValid(createRecipeDTO,stringFields,numericFields);
        if (!isValid) {
            return "One of the followings might have occurred: " +
                    "I. String fields (recipeId,recipeName,description,instructions,imageFile,ingredientList)" +
                    " might be empty.   "+
                    "II. Numeric fields (calories,duration) might be less than or equal to 0.   " +
                    "III. The given recipe Tag Set might be empty or invalid.   " +
                    "Please try again.";
        }

        String recipeId = IdGenerator.generateNextId(Recipe.class, "recipeId");
        String imageUrl = (String) imageUtils.upload(createRecipeDTO.getImageFile(),
                "recipe", String.valueOf(UUID.randomUUID()));

        Recipe recipeEntity = modelMapper.map(createRecipeDTO, Recipe.class);
        recipeEntity.setRecipeId(recipeId);
        recipeEntity.setImage(imageUrl);
        recipeEntity.setPublicStatus(false);
        recipeEntity.setUserId(createRecipeCommand.getUserid());
        recipeEntity.setStatus(true);
        recipeEntity.setRecipeTags(new HashSet<>());
        Set<String> tagsIdSet = createRecipeDTO.getTagIdSet();
        Set<Tag> tags = tagRepository.getTagsByTagIdIn(tagsIdSet);
        if (tags.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found tag !");
        }
        List<RecipeTag> recipeTags = new ArrayList<>();
        for (Tag tag : tags) {
            RecipeTag recipeTag = new RecipeTag();
            RecipeTagId recipeTagId = new RecipeTagId(recipeEntity.getRecipeId(), tag.getTagId());
            recipeTag.setRecipeTagId(recipeTagId);
            recipeTag.setRecipe(recipeEntity);
            recipeTag.setTag(tag);
            recipeTags.add(recipeTag);
        }
        recipeEntity.getRecipeTags().addAll(recipeTags);
        recipeRepository.save(recipeEntity);
        recipeTagRepository.saveAll(recipeTags);
        return recipeId;
    }
}
