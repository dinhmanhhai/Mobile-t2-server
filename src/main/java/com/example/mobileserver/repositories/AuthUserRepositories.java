package com.example.mobileserver.repositories;

import com.example.mobileserver.enities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthUserRepositories extends JpaRepository<AuthUser, Integer> {
  @Query("select a from AuthUser a where a.userId = ?1")
  AuthUser findById(String userId);
}
