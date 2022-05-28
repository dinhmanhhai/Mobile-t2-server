package com.example.mobileserver.services.impl;

import com.example.mobileserver.config.AuthServiceMessageCode;
import com.example.mobileserver.config.OperationNotImplementException;
import com.example.mobileserver.dto.NoteDto;
import com.example.mobileserver.dto.PostNoteDto;
import com.example.mobileserver.dto.SignInDto;
import com.example.mobileserver.dto.SignInResponse;
import com.example.mobileserver.entities.AuthUser;
import com.example.mobileserver.entities.NoteUser;
import com.example.mobileserver.repositories.AuthUserRepositories;
import com.example.mobileserver.repositories.NoteRepositories;
import com.example.mobileserver.services.iface.MobileServices;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MobileServicesImpl implements MobileServices {

  final AuthUserRepositories authUserRepositories;

  final NoteRepositories noteRepositories;

  private final RedisTemplate redisTemplate;

  SimpleDateFormat DD_MM_YYYY = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

  public MobileServicesImpl(
      AuthUserRepositories authUserRepositories,
      NoteRepositories noteRepositories,
      RedisTemplate redisTemplate) {
    this.authUserRepositories = authUserRepositories;
    this.noteRepositories = noteRepositories;
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
    UUID uuid = UUID.randomUUID();

    // extend token
    extendRedisKey(String.valueOf(uuid), dto.getUserId());

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

      response.setId(tmp.getId());
      response.setToken(String.valueOf(uuid));
      return response;
    }
    authUserRepositories.save(user);
    response.setId(user.getId());
    response.setToken(String.valueOf(uuid));
    return response;
  }

  @Override
  public SignInResponse postNote(NoteDto dto, String token) throws OperationNotImplementException {
    String v = getRedisKeyValue(token);

    if(v == null){
      throw new OperationNotImplementException("user not found" +
          AuthServiceMessageCode.USER_NOT_FOUND);
    }
    AuthUser tmp = authUserRepositories.findById(v);

    if(tmp == null){
      throw new OperationNotImplementException("user not found" +
          AuthServiceMessageCode.USER_NOT_FOUND);
    }

    // extend token
    extendRedisKey(token, tmp.getUserId());

    Date date = new Date();

    NoteUser note = new NoteUser();
    note.setCreateTime(DD_MM_YYYY.format(date));
    note.setModifyTime(DD_MM_YYYY.format(date));
    note.setUserId(tmp.getUserId());
    note.setIsDeleted(false);
    note.setContent(dto.getContent());
    note.setTitle(dto.getTitle());
    note.setCompleted(false);
    noteRepositories.save(note);
    return null;
  }

  @Override
  public List<NoteUser> getNotes(String token) throws OperationNotImplementException {
    extendRedisKey(token, getRedisKeyValue(token));
    String v = getRedisKeyValue(token);
    List<NoteUser> list = new ArrayList<>();
    list = noteRepositories.findByUserId(v);
    return list;
  }

  @Override
  public Integer editNote(Integer id, PostNoteDto dto,
      String token) throws OperationNotImplementException {
    extendRedisKey(token, getRedisKeyValue(token));
    NoteUser note = noteRepositories.findOneById(id);
    Date date = new Date();

    note.setContent(dto.getContent());
    note.setTitle(dto.getTitle());
    note.setModifyTime(DD_MM_YYYY.format(date));
    noteRepositories.save(note);
    return note.getId();
  }

  @Override
  public String deleteNote(Integer id, String token) throws OperationNotImplementException {
    extendRedisKey(token, getRedisKeyValue(token));
    Optional<NoteUser> tmp =  noteRepositories.findById(id);
    if(tmp.isEmpty()){
      throw new OperationNotImplementException("Note does not exist" + AuthServiceMessageCode.NOTE_NOT_FOUND);
    }
    tmp.get().setIsDeleted(true);
    noteRepositories.save(tmp.get());
    return "Note deleted";
  }

  @Override
  public AuthUser getUserInfo(String token) throws OperationNotImplementException {
    String v = getRedisKeyValue(token);
    if(v == null){
      throw new OperationNotImplementException("User not found" +
          AuthServiceMessageCode.USER_NOT_FOUND);
    }
    extendRedisKey(token, v);
    AuthUser data = authUserRepositories.findById(v);
    return data;
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


