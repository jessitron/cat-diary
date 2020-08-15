package com.jessitron.catdiary.catIdentities;


import lombok.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

@Value
public class EncodedPassword {

  public String stringValue;

  public EncodedPassword(PasswordEncoder encoder, PlainTextPassword plainTextPassword) {
    this.stringValue = encoder.encode(plainTextPassword.getValue());
  }

  public EncodedPassword(String alreadyEncodedPassword) {
    this.stringValue = alreadyEncodedPassword;
  }
}