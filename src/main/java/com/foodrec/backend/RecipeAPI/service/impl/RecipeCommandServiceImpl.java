package com.foodrec.backend.RecipeAPI.service.impl;

import com.foodrec.backend.RecipeAPI.dto.NewRecipeDTO;
import com.foodrec.backend.RecipeAPI.dto.RUDRecipeDTO;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.RecipeAPI.service.RecipeCommandService;
import com.foodrec.backend.RecipeAPI.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/*Lưu ý điểm khác giữa Repository và Service, VÀ Controller:
    - Repository: Là 1 KHO CHỨA (a.k.a 1 cái Database),
            CHỈ BAO GỒM thêm, xóa, sửa dữ liệu, đọc dữ liệu
            (v.d. lấy Recipe theo id, ngày, tháng năm...)

    - Service: Là 1 cái file/class LẤY DỮ LIỆU từ Repository
                (bằng hàm CRUD của Repository (v.d. getAll, getById...),
                sau đó LÀM THÊM 1 SỐ VIỆC KHÁC (nếu có)
                (v.d. kiểm tra xem dữ liệu đầu vào từ Controller có bị NGU KHÔNG,
                Map dữ liệu tìm được với cái Dto...)

    - Controller: Chỉ NHẬN YÊU CẦU bên Front-end (v.d. React), và TRẢ KẾT QUẢ
                (từ Service cho Front-end)

* */
/*giúp cho Autowired biết RecipeCommandServiceImpl là 1 service, khi thằng Controller
* được Front-end gọi, thì 1 object/hiện thân/vật thể là RecipeCommandServiceImpl
* sẽ được tạo ra.
* */
@Service
public class RecipeCommandServiceImpl implements RecipeCommandService {
    @Autowired
    private RecipeRepository _recipeRepository;
    @Autowired
    private ModelMapper _modelMapper;
    @Autowired
    private Utils utils;

    private ArrayList<String> excludedFields = new ArrayList<>
            (Arrays.asList("description"));
    public boolean insertRecipe(NewRecipeDTO newRecipeDTO){
        try{
            //B1: Kiểm tra xem các thuộc tính trong công thức (v.d. tên,...) có bị null không.
            boolean containsAnyEmptyField = utils.
                    containsAnyEmptyField(newRecipeDTO,excludedFields);
            if (containsAnyEmptyField==true){
                return false;
            }

            //B2: Nếu Ok thì map từ Dto sang Entity và thêm vào database.
            Recipe recEntity = _modelMapper.map(newRecipeDTO,Recipe.class);

            recEntity.setRecipeid(utils.generateRecId());
            recEntity.setUserid("U0001");
            recEntity.setHidden(false);
            _recipeRepository.save(recEntity);


            //B3: Kiểm tra xem công thức đã được add chưa, bằng cách tìm lại chính công thức đó.
            Optional<Recipe> isAddedRec = _recipeRepository.findById(recEntity.getRecipeid());
            if(isAddedRec.get()==null){
                return false;
            }

        }catch(IllegalAccessException e){

        }

        return true;
    }
    public boolean updateRecipeDetailsById(RUDRecipeDTO RUDRecipeDTO) {
        try{
            //B1: Kiểm tra xem các thuộc tính trong công thức (v.d. tên,...) có bị null không.
            boolean containsAnyEmptyField = utils.
                    containsAnyEmptyField(RUDRecipeDTO,excludedFields);
            if (containsAnyEmptyField==true){
                return false;
            }

            //B2: Nếu Ok thì map từ Dto sang Entity và update vào database
            //Đồng thời bổ sung thêm cái userId vào recipe entity, do bên Dto KHÔNG CÓ.
            Recipe recEntity = _modelMapper.map(RUDRecipeDTO,Recipe.class);
            recEntity.setUserid("U0001"); //Tạm thời gài tạm userId = 1, do hiện tại chưa có cookies.
            recEntity.setHidden(false);
            _recipeRepository.save(recEntity);


            //B3: Kiểm tra xem công thức đã được add chưa, bằng cách tìm lại chính công thức đó.
            Optional<Recipe> targetRec = _recipeRepository.findById(recEntity.getRecipeid());
            if(targetRec==null){
                return false;
            }

        }catch(IllegalAccessException e){

        }

        return true;
    }
    public boolean updateRecipeStatusById(String recipeId){
        _recipeRepository.updateRecipeHiddenById(recipeId);
        Optional<Recipe> targetRec = _recipeRepository.findById(recipeId);
        if(targetRec.get().isHidden()==false){
            return false;
        }
        return true;
    }
}
