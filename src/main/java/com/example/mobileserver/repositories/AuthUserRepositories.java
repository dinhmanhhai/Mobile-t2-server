package com.example.mobileserver.repositories;

import com.example.mobileserver.entities.AuthUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepositories extends JpaRepository<AuthUser, Integer> {
  @Query("select a from AuthUser a where a.userId = ?1")
  AuthUser findById(String userId);
}
