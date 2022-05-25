package com.example.mobileserver.services.impl;

import com.example.mobileserver.config.OperationNotImplementException;
import com.example.mobileserver.dto.SignInDto;
import com.example.mobileserver.dto.SignInResponse;
//import com.example.mobileserver.repositories.AuthUserRepositories;
import com.example.mobileserver.repositories.AuthUserRepositories;
import com.example.mobileserver.services.iface.MobileServices;
import org.springframework.stereotype.Service;

@Service
public class MobileServicesImpl implements MobileServices {


  AuthUserRepositories authUserRepositories;



  @Override
  public SignInResponse signIn(SignInDto dto) throws OperationNotImplementException {
    return null;
  }
}
