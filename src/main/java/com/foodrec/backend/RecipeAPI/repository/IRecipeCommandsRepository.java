package com.foodrec.backend.RecipeAPI.repository;

import com.foodrec.backend.RecipeAPI.model.Recipe;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface IRecipeCommandsRepository extends JpaRepository<Recipe,Long> {

    @Transactional //1 là thêm được thì thêm, 2 là không thêm được thì CÚT (Database sẽ nguyên vẹn).

    @Modifying // báo hiệu đây là 1 câu lệnh thay đổi Database.
              // BẮT BUỘC PHẢI CÓ với những lệnh (thêm/xóa/sửa)


    /* Cú pháp lấy từng thuộc tính trong object Recipe, để thêm vào câu Query
     *       :#{#recipe.tên_thuộc_tính}                */
    @Query(value="INSERT INTO recipe(recipeid,recipename,description,calories," +
                                    "userid,duration,image,hidden) " +
                "VALUES (:#{#newRec.recipeid},:#{#newRec.recipename},:#{#newRec.description}," +
                ":#{#newRec.calories},:#{#newRec.userid},:#{#newRec.duration},:#{#newRec.image}" +
                ",:#{#newRec.hidden})"
                ,nativeQuery = true)
    void insertRecipe(@Param("newRec") Recipe newRec);

    @Transactional
    @Modifying
    @Query(value="UPDATE recipe SET hidden = true WHERE recipeid = :recipeid",nativeQuery = true)
    void deleteRecipeById(@Param("recipeid") String recipeid);

    @Transactional
    @Modifying
    @Query(value="UPDATE recipe " +
            "SET recipename = :#{#rec.recipename},description = :#{#rec.description}," +
            "calories = :#{#rec.calories},userid = :#{#rec.userid}, " +
            "duration = :#{#rec.duration},image = :#{#rec.image},hidden = :#{#rec.hidden} " +
            "WHERE recipeid = :#{#rec.recipeid}",nativeQuery = true)
    void updateRecipeDetailsById(@Param("rec") Recipe rec);
}
