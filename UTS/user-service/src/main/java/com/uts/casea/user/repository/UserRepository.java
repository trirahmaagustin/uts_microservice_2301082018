package com.uts.casea.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uts.casea.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
