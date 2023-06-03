package com.foodrec.backend.Utils;

import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RecipeIdGenerator {
    private final RedisTemplate<String, String> redisTemplate;
    private String postid = null;
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeIdGenerator(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateNextPostId() {
        String postIdString;
        Long lastPostIdNumber;
        if (postid == null) {
            postid = RepositoryUtils.findLastById(Post.class, "postid");
            System.out.println(postid);
            if (postid == null || postid.isEmpty()) {
                postid = "POST000001";
                return postid;
            }
            postIdString = postid.substring(0, 4); //"POST"
            lastPostIdNumber = Long.parseLong(postid.substring(4)); //
            redisTemplate.opsForValue().set(postIdString, String.valueOf(lastPostIdNumber));
        }
        postIdString = postid.substring(0, 4);
        Long incrementedPostNumber = redisTemplate.opsForValue().increment(postIdString, 1); // Tăng giá trị postid trong Redis lên 1 đơn vị
        String newPostNumber = String.format("%06d", incrementedPostNumber);

        return postIdString + newPostNumber;
    }


    public String generateRecId(){
        String result = null;
        //Tìm xem có tồn tại 1 recipe trong Database không.
        Recipe latestRec = recipeRepository.findAll
                (Sort.by(Sort.Direction.DESC, "recipeid")).get(0);;
        if(latestRec == null){ //TH1: KO CÓ Recipe nào trong bảng Recipe
            result = "REC0001";
        }else{ //TH2: CÓ ÍT NHẤT 1 recipe trong bảng Recipe. -> Lấy id thằng recipe mới nhất + 1
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(latestRec.getRecipeid());
            String numberSection = "";
            if(m.find()) {
                numberSection = m.group(0);
            }
            int newId = Integer.parseInt(numberSection)+1;
            if(newId<10){
                result = "REC000"+newId;
            }else if(newId<100){
                result = "REC00"+newId;
            }else {
                result = "REC0"+newId;
            }

        }
        return result;
    }

}
