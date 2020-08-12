package com.example.demo.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Account;

public interface AccountRepository extends JpaRepository<Account, String>{
	
	Account findByUserNameAndPassword(String userName, String password);

}
