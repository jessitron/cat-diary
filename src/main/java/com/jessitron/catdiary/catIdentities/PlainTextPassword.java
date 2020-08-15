package com.jessitron.catdiary.catIdentities;

import java.util.concurrent.atomic.AtomicReference;

public class PlainTextPassword {

  private final AtomicReference<String> readOnceValue;

  public PlainTextPassword(String value) {
    this.readOnceValue = new AtomicReference<>(value);
  }

  public String getValue() {
    var result = this.readOnceValue.getAndSet(null);
    if (result == null) {
      throw new IllegalStateException("Value must only be accessed once");
    }
    return result;
  }

  public String toString() {
    return "<do not print the plain text password>";
  }

  // you can also add methods to prevent any serialization.

}