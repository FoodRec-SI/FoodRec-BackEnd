package com.foodrec.backend.RecipeAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Set;

/*Note: DTO cơ bản là 1 Model, chỉ khác ở chỗ như sau:
  - Điểm 1: Recipe chứa ĐẦY ĐỦ các thuộc tính + hàm, trong khi đó DTO chỉ chứa 1 SỐ CÁI thuộc tính.
   + Nguyên nhân: Do khi trả về client, mình KHÔNG NHẤT THIẾT PHẢI TRẢ VỀ TẤT CẢ THÔNG TIN.
   + (V.d. khi front-end yêu cầu cần lấy Recipe, thì nó ĐÂU NHẤT THIẾT cần cái RecipeID làm gì?
      Hoặc là, khi front-end yêu cầu lấy thông tin người dùng, thì NÓ ĐÂU NHẤT THIẾT CẦN BIẾT MẬT KHẨU làm gì?
  - Nên điểm khác thứ 2 là: DTO là dành cho cả Front + Backend, còn Model chỉ ở lì BACKEND.
* */
//Nguyên nhân phải implement: giúp cho Utils.checkFieldEmpty nhận được nhiều LOẠI DTO khác nhau.
//Bản chất: Coi RecipeDTO là 1 bản hợp đồng. Bất kì ai tuân theo thì sẽ đợc qua cửa Utils.checkFieldEmpty.

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecipeDTO implements Serializable {
    private String recipeName;
    private String description;
    private int calories;
    private int duration;
    private String instructions;
    private MultipartFile image;
    private Set<String> tagsIdSet;
}
