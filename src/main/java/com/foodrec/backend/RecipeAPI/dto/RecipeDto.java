package com.foodrec.backend.RecipeAPI.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

/*Note: DTO cơ bản là 1 Model, chỉ khác ở chỗ như sau:
  - Điểm 1: Recipe chứa ĐẦY ĐỦ các thuộc tính + hàm, trong khi đó DTO chỉ chứa 1 SỐ CÁI thuộc tính.
   + Nguyên nhân: Do khi trả về client, mình KHÔNG NHẤT THIẾT PHẢI TRẢ VỀ TẤT CẢ THÔNG TIN.
   + (V.d. khi front-end yêu cầu cần lấy Recipe, thì nó ĐÂU NHẤT THIẾT cần cái RecipeID làm gì?
      Hoặc là, khi front-end yêu cầu lấy thông tin người dùng, thì NÓ ĐÂU NHẤT THIẾT CẦN BIẾT MẬT KHẨU làm gì?
  - Nên điểm khác thứ 2 là: DTO là dành cho cả Front + Backend, còn Model chỉ ở lì BACKEND.
* */
public class RecipeDto {
    public RecipeDto() {
    }

    private String recipeid;
    private String recipename;

    public String getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(String recipeid) {
        this.recipeid = recipeid;
    }

    private String description;

    private int calories;

    private int duration;

    public String getRecipename() {
        return recipename;
    }

    public void setRecipename(String recipename) {
        this.recipename = recipename;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private byte[] image;


}
