package com.example.mobileserver.controller;

import com.example.mobileserver.config.BaseMethodResponse;
import com.example.mobileserver.config.Constants;
import com.example.mobileserver.config.GetMethodResponse;
import com.example.mobileserver.config.OperationNotImplementException;
import com.example.mobileserver.dto.NoteDto;
import com.example.mobileserver.dto.PostNoteDto;
import com.example.mobileserver.dto.SignInDto;
import com.example.mobileserver.dto.SignInResponse;
import com.example.mobileserver.entities.AuthUser;
import com.example.mobileserver.entities.NoteUser;
import com.example.mobileserver.services.impl.MobileServicesImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MobileController {

  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
      .getLogger(MobileController.class);

  final MobileServicesImpl mobileServices;

  public MobileController(MobileServicesImpl mobileServices) {
    this.mobileServices = mobileServices;
  }

  @PostMapping(path = "/auth/sign-in")
  public ResponseEntity<?> signupCTV(HttpServletRequest request,
      @RequestBody SignInDto dto) {
    try {
      SignInResponse signInResponse = mobileServices.signIn(dto, request.getHeader("token"));
      return new ResponseEntity<>(
          GetMethodResponse.builder().status(true).data(signInResponse)
              .message(Constants.SUCCESS_MSG)
              .errorCode(HttpStatus.OK.name().toLowerCase()).httpCode(HttpStatus.OK.value()).build()
          , HttpStatus.OK);
    } catch (OperationNotImplementException e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(
          BaseMethodResponse.builder().status(false).message(e.getMessage())
              .errorCode(e.getMessageCode()).httpCode(HttpStatus.BAD_REQUEST.value()).build()
          , HttpStatus.OK);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(
          BaseMethodResponse.builder().status(false).message(Constants.INTERNAL_SERVER_ERROR)
              .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.name().toLowerCase())
              .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build()
          , HttpStatus.OK);
    }
  }

  @GetMapping(path = "/auth/user")
  public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
    try {
      AuthUser data = mobileServices.getUserInfo(request.getHeader("token"));
      return new ResponseEntity<>(
          GetMethodResponse.builder().status(true).data(data)
              .message(Constants.SUCCESS_MSG)
              .errorCode(HttpStatus.OK.name().toLowerCase()).httpCode(HttpStatus.OK.value()).build()
          , HttpStatus.OK);
    } catch (OperationNotImplementException e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(
          BaseMethodResponse.builder().status(false).message(e.getMessage())
              .errorCode(e.getMessageCode()).httpCode(HttpStatus.BAD_REQUEST.value()).build()
          , HttpStatus.OK);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(
          BaseMethodResponse.builder().status(false).message(Constants.INTERNAL_SERVER_ERROR)
              .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.name().toLowerCase())
              .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build()
          , HttpStatus.OK);
    }
  }

  @PostMapping(path = "/post-note")
  public ResponseEntity<?> postNote(HttpServletRequest request,
      @RequestBody NoteDto dto) {
    try {
      SignInResponse signInResponse = mobileServices.postNote(dto, request.getHeader("token"));
      return new ResponseEntity<>(
          GetMethodResponse.builder().status(true).data(signInResponse)
              .message(Constants.SUCCESS_MSG)
              .errorCode(HttpStatus.OK.name().toLowerCase()).httpCode(HttpStatus.OK.value()).build()
          , HttpStatus.OK);
    } catch (OperationNotImplementException e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(
          BaseMethodResponse.builder().status(false).message(e.getMessage())
              .errorCode(e.getMessageCode()).httpCode(HttpStatus.BAD_REQUEST.value()).build()
          , HttpStatus.OK);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(
          BaseMethodResponse.builder().status(false).message(Constants.INTERNAL_SERVER_ERROR)
              .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.name().toLowerCase())
              .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build()
          , HttpStatus.OK);
    }
  }

  @GetMapping(path = "/notes")
  public ResponseEntity<?> getNotes(HttpServletRequest request) {
    try {
      List<NoteUser> data = mobileServices.getNotes(request.getHeader("token"));
      return new ResponseEntity<>(
          GetMethodResponse.builder().status(true).data(data)
              .message(Constants.SUCCESS_MSG)
              .errorCode(HttpStatus.OK.name().toLowerCase()).httpCode(HttpStatus.OK.value()).build()
          , HttpStatus.OK);
    } catch (OperationNotImplementException e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(
          BaseMethodResponse.builder().status(false).message(e.getMessage())
              .errorCode(e.getMessageCode()).httpCode(HttpStatus.BAD_REQUEST.value()).build()
          , HttpStatus.OK);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(
          BaseMethodResponse.builder().status(false).message(Constants.INTERNAL_SERVER_ERROR)
              .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.name().toLowerCase())
              .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build()
          , HttpStatus.OK);
    }
  }

  @PostMapping(path = "/edit-note/{id}")
  public ResponseEntity<?> editNote(HttpServletRequest request, @PathVariable Integer id, @RequestBody PostNoteDto dto) {
    try {
      Integer data = mobileServices.editNote(id, dto, request.getHeader("token"));
      return new ResponseEntity<>(
          GetMethodResponse.builder().status(true).data(data)
              .message(Constants.SUCCESS_MSG)
              .errorCode(HttpStatus.OK.name().toLowerCase()).httpCode(HttpStatus.OK.value()).build()
          , HttpStatus.OK);
    } catch (OperationNotImplementException e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(
          BaseMethodResponse.builder().status(false).message(e.getMessage())
              .errorCode(e.getMessageCode()).httpCode(HttpStatus.BAD_REQUEST.value()).build()
          , HttpStatus.OK);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(
          BaseMethodResponse.builder().status(false).message(Constants.INTERNAL_SERVER_ERROR)
              .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.name().toLowerCase())
              .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build()
          , HttpStatus.OK);
    }
  }

  @PostMapping(path = "/delete/{id}")
  public ResponseEntity<?> deleteNote(HttpServletRequest request, @PathVariable Integer id) {
    try {
      String data = mobileServices.deleteNote(id, request.getHeader("token"));
      return new ResponseEntity<>(
          GetMethodResponse.builder().status(true).data(data)
              .message(Constants.SUCCESS_MSG)
              .errorCode(HttpStatus.OK.name().toLowerCase()).httpCode(HttpStatus.OK.value()).build()
          , HttpStatus.OK);
    } catch (OperationNotImplementException e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(
          BaseMethodResponse.builder().status(false).message(e.getMessage())
              .errorCode(e.getMessageCode()).httpCode(HttpStatus.BAD_REQUEST.value()).build()
          , HttpStatus.OK);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(
          BaseMethodResponse.builder().status(false).message(Constants.INTERNAL_SERVER_ERROR)
              .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.name().toLowerCase())
              .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build()
          , HttpStatus.OK);
    }
  }
}
