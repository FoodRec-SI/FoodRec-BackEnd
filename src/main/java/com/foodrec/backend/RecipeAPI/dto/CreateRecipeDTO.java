package com.foodrec.backend.RecipeAPI.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/*Note: DTO cơ bản là 1 Model, chỉ khác ở chỗ như sau:
  - Điểm 1: Recipe chứa ĐẦY ĐỦ các thuộc tính + hàm, trong khi đó DTO chỉ chứa 1 SỐ CÁI thuộc tính.
   + Nguyên nhân: Do khi trả về client, mình KHÔNG NHẤT THIẾT PHẢI TRẢ VỀ TẤT CẢ THÔNG TIN.
   + (V.d. khi front-end yêu cầu cần lấy Recipe, thì nó ĐÂU NHẤT THIẾT cần cái RecipeID làm gì?
      Hoặc là, khi front-end yêu cầu lấy thông tin người dùng, thì NÓ ĐÂU NHẤT THIẾT CẦN BIẾT MẬT KHẨU làm gì?
  - Nên điểm khác thứ 2 là: DTO là dành cho cả Front + Backend, còn Model chỉ ở lì BACKEND.
* */
//Nguyên nhân phải implement: giúp cho Utils.checkFieldEmpty nhận được nhiều LOẠI DTO khác nhau.
//Bản chất: Coi RecipeDTO là 1 bản hợp đồng. Bất kì ai tuân theo thì sẽ đợc qua cửa Utils.checkFieldEmpty.
public class CreateRecipeDTO implements Serializable {
    public CreateRecipeDTO() { //DTO cho việc tạo 1 công thức mới (KO BẮT ng dùng nhập Id công thức)
    }

    private String recipeName;

    private String description;

    private int calories;

    private int duration;
    private String instructions;

    private MultipartFile image; /*receives the image as a FILE FROM THE FRONT-END.
                                Then that image is converted into the URL String (Firebase)
                                at the CreateRecipeCommandHandler layer.*/

    private List<String> tagIdList;
    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<String> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<String> tagIdList) {
        this.tagIdList = tagIdList;
    }
}
