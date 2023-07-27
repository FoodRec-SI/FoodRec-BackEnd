package com.foodrec.backend.PlanAPI.dto;

import java.util.ArrayList;
import java.util.List;

public class IngredientListRequestDTO {
    private String model;
    private List<Message> messages;

    public IngredientListRequestDTO(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessage(List<Message> messages) {
        this.messages = messages;
    }
}
