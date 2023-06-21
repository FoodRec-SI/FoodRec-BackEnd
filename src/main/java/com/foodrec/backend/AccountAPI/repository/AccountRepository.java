package com.foodrec.backend.AccountAPI.repository;

import com.foodrec.backend.AccountAPI.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
