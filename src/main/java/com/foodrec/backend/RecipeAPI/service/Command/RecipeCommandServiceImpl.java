package com.foodrec.backend.RecipeAPI.service.Command;

import com.foodrec.backend.RecipeAPI.dto.RecipeDto;
import com.foodrec.backend.RecipeAPI.model.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import com.foodrec.backend.RecipeAPI.service.Query.RecipeQueryService;
import com.foodrec.backend.RecipeAPI.service.ValidationServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Arrays;

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
public class RecipeCommandServiceImpl implements RecipeCommandService{
    @Autowired
    private RecipeRepository _recipeRepository;
    @Autowired
    private ModelMapper _modelMapper;
    @Autowired
    private RecipeQueryService _recipeQueryService;
    private ArrayList<String> excludedFields = new ArrayList<>
            (Arrays.asList("description"));
    public boolean insertRecipe(RecipeDto rec){
        try{
            //B1: Kiểm tra xem các thuộc tính trong công thức (v.d. tên,...) có bị null không.
            boolean containsAnyEmptyField = ValidationServices.
                    containsAnyEmptyField(rec,excludedFields);
            if (containsAnyEmptyField==true){
                return false;
            }

            //B2: Nếu Ok thì map từ Dto sang Entity và thêm vào database.
            Recipe recEntity = _modelMapper.map(rec,Recipe.class);
            recEntity.setUserid("1");
            _recipeRepository.insertRecipe(recEntity);


            //B3: Kiểm tra xem công thức đã được add chưa, bằng cách tìm lại chính công thức đó.
            RecipeDto isAddedRec = _recipeQueryService.findRecipeByRecipeid(rec.getRecipeid());
            if(isAddedRec==null){
                return false;
            }

        }catch(IllegalAccessException e){

        }

        return true;
    }
    public boolean updateRecipeDetailsById(RecipeDto rec) {
        try{
            //B1: Kiểm tra xem các thuộc tính trong công thức (v.d. tên,...) có bị null không.
            boolean containsAnyEmptyField = ValidationServices.
                    containsAnyEmptyField(rec,excludedFields);
            if (containsAnyEmptyField==true){
                return false;
            }

            //B2: Nếu Ok thì map từ Dto sang Entity và update vào database
            //Đồng thời bổ sung thêm cái userId vào recipe entity, do bên Dto KHÔNG CÓ.
            Recipe recEntity = _modelMapper.map(rec,Recipe.class);
            recEntity.setUserid("1");
            _recipeRepository.updateRecipeDetails(recEntity);


            //B3: Kiểm tra xem công thức đã được add chưa, bằng cách tìm lại chính công thức đó.
            RecipeDto targetRec = _recipeQueryService.findRecipeByRecipeid(rec.getRecipeid());
            if(targetRec==null){
                return false;
            }

        }catch(IllegalAccessException e){

        }

        return true;
    }
    public boolean deleteRecipeById(String recid){
        _recipeRepository.deleteRecipeById(recid);
        RecipeDto targetRec = _recipeQueryService.findRecipeByRecipeid(recid);
        if(targetRec!=null){
            return false;
        }
        return true;
    }
}
