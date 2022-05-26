package com.example.mobileserver.config;

public class OperationNotImplementException extends Exception {

  protected String messageCode;

  public OperationNotImplementException(String message) {
    super(message);
  }

  public String getMessageCode() {
    return this.messageCode;
  }

  public void setMessageCode(String messageCode) {
    this.messageCode = messageCode;
  }
}
