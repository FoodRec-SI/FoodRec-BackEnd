package com.foodrec.backend.MealAPI.command.create_meal;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.MealAPI.dto.CreateMealDTO;
import com.foodrec.backend.MealAPI.dto.MealDTO;

public class CreateMealCommand implements Command<MealDTO> {
    private final CreateMealDTO createMealDTO;
    private String userId;

    public CreateMealCommand(CreateMealDTO createMealDTO, String userId) {
        this.createMealDTO = createMealDTO;
        this.userId = userId;
    }

    public CreateMealDTO getCreateMealDTO() {
        return createMealDTO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
