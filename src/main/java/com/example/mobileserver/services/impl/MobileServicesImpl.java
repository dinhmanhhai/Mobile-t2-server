package com.example.mobileserver.services.impl;

import com.example.mobileserver.config.AuthServiceMessageCode;
import com.example.mobileserver.config.OperationNotImplementException;
import com.example.mobileserver.dto.SignInDto;
import com.example.mobileserver.dto.SignInResponse;
import com.example.mobileserver.entities.AuthUser;
import com.example.mobileserver.repositories.AuthUserRepositories;
import com.example.mobileserver.services.iface.MobileServices;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class MobileServicesImpl implements MobileServices {


  final AuthUserRepositories authUserRepositories;

  public MobileServicesImpl(
      AuthUserRepositories authUserRepositories) {
    this.authUserRepositories = authUserRepositories;
  }


  @Override
  public SignInResponse signIn(SignInDto dto) throws OperationNotImplementException {
    AuthUser user = this.validateUser(dto);
    AuthUser tmp = authUserRepositories.findById(user.getUserId());
    authUserRepositories.save(user);
    SignInResponse response = new SignInResponse();
    response.setId(user.getId());
    response.setToken(user.getUserId());
    return response;
  }

  private AuthUser validateUser(SignInDto dto) throws OperationNotImplementException {
    AuthUser user = new AuthUser();
    if (StringUtils.isBlank(dto.getUserId())) {
      throw new OperationNotImplementException("fail create user" +
          AuthServiceMessageCode.USER_CREATE_FAIL);
    }
    user.setUserId(dto.getUserId());

    if (StringUtils.isBlank(dto.getPhotoUrl())) {
      throw new OperationNotImplementException("fail create user" +
          AuthServiceMessageCode.USER_CREATE_FAIL);
    }
    user.setPhotoUrl(dto.getPhotoUrl());

    if (StringUtils.isBlank(dto.getDisplayName())) {
      throw new OperationNotImplementException("fail create user" +
          AuthServiceMessageCode.USER_CREATE_FAIL);
    }
    user.setDisplayName(dto.getDisplayName());

    if (StringUtils.isBlank(dto.getEmail())) {
      throw new OperationNotImplementException("fail create user" +
          AuthServiceMessageCode.USER_CREATE_FAIL);
    }
    user.setEmail(dto.getEmail());

    if (StringUtils.isNotBlank(dto.getServerAuthCode())) {
      user.setServerAuthCode(dto.getServerAuthCode());
    }

    return user;
  }
}


