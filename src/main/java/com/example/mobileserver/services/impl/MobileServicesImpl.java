package com.example.mobileserver.services.impl;

import com.example.mobileserver.config.AuthServiceMessageCode;
import com.example.mobileserver.config.OperationNotImplementException;
import com.example.mobileserver.dto.SignInDto;
import com.example.mobileserver.dto.SignInResponse;
import com.example.mobileserver.entities.AuthUser;
import com.example.mobileserver.repositories.AuthUserRepositories;
import com.example.mobileserver.services.iface.MobileServices;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MobileServicesImpl implements MobileServices {


  final AuthUserRepositories authUserRepositories;

  private final RedisTemplate redisTemplate;

  public MobileServicesImpl(
      AuthUserRepositories authUserRepositories,
      RedisTemplate redisTemplate) {
    this.authUserRepositories = authUserRepositories;
    this.redisTemplate = redisTemplate;
  }

  public void extendRedisKey(String k, String v) {
    redisTemplate.opsForValue().set(k, v, 60, TimeUnit.SECONDS);
  }

  public String getRedisKeyValue(String k) {
    Object result = redisTemplate.opsForValue().get(k);
    if (result != null) {
      return (String) result;
    }
    return null;
  }

  @Override
  public SignInResponse signIn(SignInDto dto, String token) throws OperationNotImplementException {
    SignInResponse response = new SignInResponse();

    // exten token
    String existTokenValue = getRedisKeyValue(token);
    if(existTokenValue != null){
      extendRedisKey(token, dto.getUserId());
    }

    // validate dto
    AuthUser user = this.validateUser(dto);
    AuthUser tmp = authUserRepositories.findById(user.getUserId());

    if (tmp != null) {
      tmp.setEmail(dto.getEmail());
      tmp.setDisplayName(dto.getDisplayName());
      tmp.setPhotoUrl(dto.getPhotoUrl());
      tmp.setServerAuthCode(dto.getServerAuthCode());
      tmp.setUserId(dto.getUserId());
      authUserRepositories.save(tmp);

      //
//      System.out.println(
//          "Value of key: " + redisTemplate.opsForValue().get(String.valueOf(user.getUserId())));
      response.setId(tmp.getId());
      response.setToken(String.valueOf(token));
      return response;
    }
    authUserRepositories.save(user);
    response.setId(user.getId());
    response.setToken(String.valueOf(token));
    redisTemplate.opsForValue().set(user.getUserId(), String.valueOf(token));
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


