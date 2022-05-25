package com.example.mobileserver.enities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "note_user")
public class NoteUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "userId")
  private String userId;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @Column(name = "create_time")
  private String createTime;

  @Column(name = "modify_time")
  private String modifyTime;

  @Column(name = "completed")
  private Boolean completed;

  @Column(name = "is_deleted")
  private Boolean isDeleted;

  @Column(name = "color")
  private String color;
}
