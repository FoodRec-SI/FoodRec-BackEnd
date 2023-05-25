package com.foodrec.backend.RecipeAPI.repository;

import com.foodrec.backend.RecipeAPI.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IRecipeQueriesRepository extends JpaRepository<Recipe,Long>{
    /*Quy tắc đặt tên hàm tìm dữ liệu nếu dùng JpaRepository để CRUD dữ liệu:
       ### find<thực_thể>by/get<thực_thể>by/delete<thực_thể>by... ###

    * NOTE: NẾU CÓ CHỮ @Query ở đầu hàm, và có chữ nativeQuery = true ở cuối,
    * thì quy tắc trên coi như VỨT.
    * (cơ bản là đặt tên hàm qq j cũng được)
    * Nguồn: https://viblo.asia/p/spring-boot-12-spring-jpa-method-atquery-Qbq5Q4nGlD8*/
    @Query(value="SELECT * FROM recipe WHERE hidden = false",nativeQuery = true)
    List<Recipe> findAllRecipes();

    /*Param("recipeid"): tìm xem trong câu query có chỗ nào ghi là :recipeId không.
    Nếu có thì thay chỗ đó thành object (món đồ) được truyền từ Controller bên ngoài vào, đó là String recipeid )*/

    @Query(value="SELECT * FROM recipe WHERE recipeid = :recipeid AND hidden = false",nativeQuery = true)
    Recipe findRecipeByRecipeid(@Param("recipeid") String recipeid);

}
