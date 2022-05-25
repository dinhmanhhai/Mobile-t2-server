package com.example.mobileserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {

  private String displayName;

  private String userId;

  private String email;

  private String photoUrl;

  private String serverAuthCode;
}
