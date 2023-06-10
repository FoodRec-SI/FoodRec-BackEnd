package com.foodrec.backend;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.RecipeAPI.command.create_recipe.CreateRecipeCommand;
import com.foodrec.backend.RecipeAPI.command.delete_recipe.DeleteRecipeCommand;
import com.foodrec.backend.RecipeAPI.command.update_recipe.UpdateRecipeCommand;
import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.UpdateRecipeDTO;
import com.foodrec.backend.RecipeAPI.query.get_all.GetAllRecipesQuery;
import com.foodrec.backend.RecipeAPI.query.get_recipe_by_id.GetRecipeByIdQuery;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeAPITests {

    @Autowired
    private Pipeline pipeline;
    @Container
    public static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:11-alpine")
                    .withDatabaseName("foodrec-db")
                    .withUsername("postgres")
                    .withPassword("12345");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
    @Test
    @Order(1)
    public void testGetRecipeById() {
        GetRecipeByIdQuery getRecipeByIdQuery =
                new GetRecipeByIdQuery("REC000001");
        RecipeDTO response = null;


        response = pipeline.send(getRecipeByIdQuery);
        assertEquals("REC000001",response.getRecipeId());
        assertEquals("bánh xèo",response.getRecipeName());
        assertEquals("1 loại bánh ngon vcl",response.getDescription());
        assertEquals(50,response.getCalories());
        assertEquals(120,response.getDuration());
    }

    @Test
    @Order(2)
    public void testGetAll() {
        GetAllRecipesQuery getAllRecipesQuery = new GetAllRecipesQuery("0","3");
        Page<RecipeDTO> readRecipeDTOArrayList = null;
        readRecipeDTOArrayList = pipeline.send(getAllRecipesQuery);
        assertTrue(readRecipeDTOArrayList.getSize()==3);
    }
    @Test
    @Order(3)
    public void testAddRecipe(){
        CreateRecipeDTO createRecipeDTO = new CreateRecipeDTO();
        createRecipeDTO.setRecipeName("Bánh Xèo Lmao");
        createRecipeDTO.setCalories(30);
        createRecipeDTO.setDescription("bruh bruh haahah");
        createRecipeDTO.setDuration(23);
        createRecipeDTO.setImage(new byte[]{});

        CreateRecipeCommand createRecipeCommand = new CreateRecipeCommand(createRecipeDTO);
        RecipeDTO result = pipeline.send(createRecipeCommand);
        assertEquals(createRecipeDTO.getRecipeName(),result.getRecipeName());
        assertEquals(createRecipeDTO.getCalories(),result.getCalories());
        assertEquals(createRecipeDTO.getDescription(),result.getDescription());
        assertEquals(createRecipeDTO.getDuration(),result.getDuration());

    }
    @Test
    @Order(4)
    public void testUpdateRecipe(){
        UpdateRecipeDTO updateRecipeDTO = new UpdateRecipeDTO();
        updateRecipeDTO.setRecipeId("REC000001");
        updateRecipeDTO.setRecipeName("Bánh Xèo Lmao");
        updateRecipeDTO.setCalories(30);
        updateRecipeDTO.setDescription("bruh bruh haahah");
        updateRecipeDTO.setDuration(23);
        updateRecipeDTO.setImage(new byte[]{});

        UpdateRecipeCommand updateRecipeCommand = new UpdateRecipeCommand(updateRecipeDTO);
        RecipeDTO result = pipeline.send(updateRecipeCommand);
        assertEquals(updateRecipeDTO.getRecipeName(),result.getRecipeName());
        assertEquals(updateRecipeDTO.getCalories(),result.getCalories());
        assertEquals(updateRecipeDTO.getDescription(),result.getDescription());
        assertEquals(updateRecipeDTO.getDuration(),result.getDuration());
    }
    @Test
    @Order(5)
    public void testDeleteRecipe(){
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand("REC000006");
        boolean isDeleted = pipeline.send(deleteRecipeCommand);
        assertTrue(isDeleted);
    }
}