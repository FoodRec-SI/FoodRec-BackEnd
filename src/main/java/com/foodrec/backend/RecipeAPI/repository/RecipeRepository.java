package com.foodrec.backend.RecipeAPI.repository;

import com.foodrec.backend.RecipeAPI.entity.Recipe;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,String> {


//    @Transactional //1 là thêm được thì thêm, 2 là không thêm được thì CÚT (Database sẽ nguyên vẹn).
//
//    @Modifying // báo hiệu đây là 1 câu lệnh thay đổi Database.
//              // BẮT BUỘC PHẢI CÓ với những lệnh (thêm/xóa/sửa)
//
//
//    /* Cú pháp lấy từng thuộc tính trong object Recipe, để thêm vào câu Query
//     *       :#{#recipe.tên_thuộc_tính}                */
//    @Query(value="INSERT INTO recipe(recipeid,recipename,description,calories," +
//                                    "userid,duration,image,hidden) " +
//                "VALUES (:#{#newRec.recipeid},:#{#newRec.recipename},:#{#newRec.description}," +
//                ":#{#newRec.calories},:#{#newRec.userid},:#{#newRec.duration},:#{#newRec.image}" +
//                ",:#{#newRec.hidden})"
//                ,nativeQuery = true)
//    void insertRecipe(@Param("newRec") Recipe newRec);
//
    @Transactional
    @Modifying
    @Query(value="UPDATE recipe SET status = false WHERE recipeid = :recipeid",nativeQuery = true)
    void updateRecipeStatusById(@Param("recipeid") String recipeid);


//    /*Quy tắc đặt tên hàm tìm dữ liệu nếu dùng JpaRepository để CRUD dữ liệu:
//      ### find<thực_thể>by/get<thực_thể>by/delete<thực_thể>by... ###
//
//   * NOTE: NẾU CÓ CHỮ @Query ở đầu hàm, và có chữ nativeQuery = true ở cuối,
//   * thì quy tắc trên coi như VỨT.
//   * (cơ bản là đặt tên hàm qq j cũng được)
//   * Nguồn: https://viblo.asia/p/spring-boot-12-spring-jpa-method-atquery-Qbq5Q4nGlD8*/
    @Query(value="SELECT * FROM recipe WHERE status = true",nativeQuery = true)
    List<Recipe> findAllRecipes(Pageable pageable);
//
//    /*Param("recipeid"): tìm xem trong câu query có chỗ nào ghi là :recipeId không.
//    Nếu có thì thay chỗ đó thành object (món đồ) được truyền từ Controller bên ngoài vào, đó là String recipeid )*/
//
    @Query(value="SELECT * FROM recipe WHERE recipeid = :recipeid AND status = true",nativeQuery = true)
    Recipe findRecipeByRecipeId(@Param("recipeid") String recipeid);
}
