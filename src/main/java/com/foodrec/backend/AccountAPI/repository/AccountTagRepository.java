package com.foodrec.backend.AccountAPI.repository;

import com.foodrec.backend.AccountAPI.entity.AccountTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTagRepository extends JpaRepository<AccountTag, String> {

    void deleteAccountTagsByAccount_UserId(String userId);



}
