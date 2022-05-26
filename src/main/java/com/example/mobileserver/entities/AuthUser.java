package com.example.mobileserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "base_user_info")
public class AuthUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "display_name")
  private String displayName;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "email")
  private String email;

  @Column(name = "photo_url")
  private String photoUrl;

  @Column(name = "server_auth_code")
  private String serverAuthCode;
}
