package com.foodrec.backend.PostAPI.utils;

import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class PostIdGenerator {
    private final RedisTemplate<String, String> redisTemplate;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    public PostIdGenerator(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    private String postid = null;

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
}
