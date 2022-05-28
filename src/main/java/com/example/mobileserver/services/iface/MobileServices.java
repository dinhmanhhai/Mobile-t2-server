package com.example.mobileserver.services.iface;

import com.example.mobileserver.config.OperationNotImplementException;
import com.example.mobileserver.dto.NoteDto;
import com.example.mobileserver.dto.PostNoteDto;
import com.example.mobileserver.dto.SignInDto;
import com.example.mobileserver.dto.SignInResponse;
import com.example.mobileserver.entities.NoteUser;
import java.util.List;

public interface MobileServices {
  SignInResponse signIn(SignInDto dto, String token) throws OperationNotImplementException;

  SignInResponse postNote(NoteDto dto, String token) throws OperationNotImplementException;

  List<NoteUser> getNotes(String token) throws OperationNotImplementException;

  Integer editNote(Integer id, PostNoteDto dto, String token) throws OperationNotImplementException;

  String deleteNote(Integer id, String token) throws OperationNotImplementException;

  SignInResponse getUserInfo(String token) throws OperationNotImplementException;
}
