package com.example.mobileserver.repositories;

import com.example.mobileserver.entities.NoteUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepositories extends JpaRepository<NoteUser, Integer> {

  @Query("select a from NoteUser a where a.userId = ?1 and a.isDeleted = false")
  List<NoteUser> findByUserId(String userId);

  NoteUser findOneById(Integer id);
}
