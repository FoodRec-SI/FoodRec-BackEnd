package com.foodrec.backend.service;

import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;
import com.foodrec.backend.RecipeAPI.service.RecipeQueryService;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.containers.PostgreSQLContainer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RecipeQueryServiceTest {
    @Autowired
    private RecipeQueryService recipeQueryService;


    /*đánh dấu rằng đoạn code này sẽ chạy trong 1 Container của Docker,
    * và sẽ được nhiều thằng dùng cùng 1 lúc (do có thuộc tính @Container).
    * */
    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:alpine")
            ;




    @BeforeAll //hàm này PHẢI CHẠY TRƯỚC TẤT CẢ các test bên dưới.
    public static void setUp(){ //Thiết lập 1 số cài đặt ban đầu cho PostgresContainer.
        postgreSQLContainer.setWaitStrategy(new LogMessageWaitStrategy());
        postgreSQLContainer.withEnv("POSTGRES_DB","Foodrec");
        postgreSQLContainer.withEnv("POSTGRES_USERNAME","postgres");
        postgreSQLContainer.withEnv("POSTGRES_PASSWORD","12345");
        postgreSQLContainer.withExposedPorts(5432);
        postgreSQLContainer.withReuse(true);
        //tạo 1 PostgreSQl Database trong Container, và chạy Container đó trong Docker.
        postgreSQLContainer.start();

    }

    @DynamicPropertySource //chưa rõ thằng này làm gì.
    public static void overrideProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username",postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password",postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name",postgreSQLContainer::getDriverClassName);
    }

    //Viết test ở dưới đây
    @Test
    public void testFindRecipeById(){
        RUDRecipeDTO actuals = recipeQueryService.findRecipeByRecipeid("3");
    }
    @AfterAll //Dừng chạy database Postgres trong RAM(Container/Docker).
    public static void tearDown(){
        postgreSQLContainer.stop();
    }
}