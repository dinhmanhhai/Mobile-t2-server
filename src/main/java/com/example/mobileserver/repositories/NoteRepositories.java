package com.example.mobileserver.repositories;

import com.example.mobileserver.entities.NoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepositories extends JpaRepository<NoteUser, Integer> {

}
