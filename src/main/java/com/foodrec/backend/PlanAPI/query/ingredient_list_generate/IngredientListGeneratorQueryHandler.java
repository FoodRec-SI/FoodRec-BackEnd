package com.foodrec.backend.PlanAPI.query.ingredient_list_generate;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.PlanAPI.dto.IngredientListRequestDTO;
import com.foodrec.backend.PlanAPI.dto.IngredientListResponseDTO;
import com.foodrec.backend.PlanAPI.entity.Plan;
import com.foodrec.backend.PlanAPI.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class IngredientListGeneratorQueryHandler implements Command.Handler<IngredientListGeneratorQuery, String> {
    private final PlanRepository planRepository;
    @Qualifier("openaiConfig")
    private final RestTemplate restTemplate;
    @Value("${OPENAI_API_MODEL}")
    private String model;
    @Value("${OPENAI_API_URL}")
    private String apiUrl;

    public IngredientListGeneratorQueryHandler(PlanRepository planRepository, RestTemplate restTemplate) {
        this.planRepository = planRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    @Override
    public String handle(IngredientListGeneratorQuery query) {
        if (query.getPlanId().isBlank() || query.getPlanId() == null) {
            throw new InvalidDataExceptionHandler("Plan ID cannot be null or blank.");
        }
        Optional<Plan> optionalPlan = planRepository.getPlanByPlanId(query.getPlanId());
        if (optionalPlan.isEmpty()) {
            throw new NotFoundExceptionHandler("Plan not found.");
        }
        if (!optionalPlan.get().getUserId().equals(query.getUserId())) {
            throw new UnauthorizedExceptionHandler("You are not authorized to access this plan.");
        }
        String prompt = "Create me a shopping list based on these ingredients and return array. " +
                "Do not reply anything else beside the json and do not put it in your code block just plain text. " +
                "Count any duplicate items into one. Do not line break: " + optionalPlan.get().getIngredientList();

        IngredientListRequestDTO ingredientListRequestDTO = new IngredientListRequestDTO(model, prompt);
        IngredientListResponseDTO ingredientListResponseDTO = restTemplate.postForObject(apiUrl, ingredientListRequestDTO, IngredientListResponseDTO.class);
        if (ingredientListResponseDTO == null || ingredientListResponseDTO.getChoices().isEmpty()) {
            return "No response.";
        }
        return ingredientListResponseDTO.getChoices().get(0).getMessage().getContent();
    }
}
