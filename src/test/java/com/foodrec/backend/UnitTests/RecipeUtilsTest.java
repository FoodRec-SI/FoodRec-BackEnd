package com.foodrec.backend.UnitTests;

import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.Utils.RecipeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RecipeUtilsTest {

    @Mock
    private RecipeRepository recipeRepository;

    private RecipeUtils recipeUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeUtils = new RecipeUtils();
        recipeUtils.recipeRepository = recipeRepository;
    }

    @Test
    void testFieldValidatorWithNonNullFields() {
        // Create a test object with null fields
        TestObject testObject = new TestObject(null, 5);

        // Define the non-null fields
        ArrayList<String> nonNullFields = new ArrayList<>();
        nonNullFields.add("field1");
        nonNullFields.add("field2");

        // Test the field validator
        boolean isValid = recipeUtils.fieldValidator(testObject, nonNullFields, new ArrayList<>());

        assertFalse(isValid);
    }

    @Test
    void testFieldValidatorWithNonNegativeFields() {
        // Create a test object with negative value for a non-negative field
        TestObject testObject = new TestObject("value", -5);

        // Define the non-negative fields
        ArrayList<String> nonNegativeFields = new ArrayList<>();
        nonNegativeFields.add("field2");

        // Test the field validator
        boolean isValid = recipeUtils.fieldValidator(testObject, new ArrayList<>(), nonNegativeFields);

        assertFalse(isValid);
    }

    @Test
    void testFieldValidatorWithValidFields() {
        // Create a test object with valid fields
        TestObject testObject = new TestObject("value", 5);

        // Define the non-null and non-negative fields
        ArrayList<String> nonNullFields = new ArrayList<>();
        nonNullFields.add("field1");
        ArrayList<String> nonNegativeFields = new ArrayList<>();
        nonNegativeFields.add("field2");

        // Test the field validator
        boolean isValid = recipeUtils.fieldValidator(testObject, nonNullFields, nonNegativeFields);

        assertTrue(isValid);
    }


    @Test
    void testValidateRecipeIdWithValidId() {
        // Call the validateRecipeId method with a valid ID
        boolean isValid = recipeUtils.validateRecipeId("REC123456");

        assertTrue(isValid);
    }

    @Test
    void testValidateRecipeIdWithInvalidId() {
        // Call the validateRecipeId method with an invalid ID
        boolean isValid = recipeUtils.validateRecipeId("INVALID");

        assertFalse(isValid);
    }


    @Test
    void testValidateRecipeIdWithEmptyId() {
        // Call the validateRecipeId method with an empty ID
        boolean isValid = recipeUtils.validateRecipeId("");

        assertFalse(isValid);
    }

    @Test
    void testValidateRecipeIdWithInvalidLength() {
        // Call the validateRecipeId method with an ID of invalid length
        boolean isValid = recipeUtils.validateRecipeId("REC123");

        assertFalse(isValid);
    }

    // Helper class for testing
    private static class TestObject {
        private String field1;
        private int field2;

        public TestObject(String field1, int field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }
}

