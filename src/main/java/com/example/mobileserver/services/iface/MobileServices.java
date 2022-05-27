package com.example.mobileserver.services.iface;

import com.example.mobileserver.config.OperationNotImplementException;
import com.example.mobileserver.dto.SignInDto;
import com.example.mobileserver.dto.SignInResponse;

public interface MobileServices {
  SignInResponse signIn(SignInDto dto, String token) throws OperationNotImplementException;
}
