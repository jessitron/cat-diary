package com.jessitron.catdiary.cats;

import lombok.Value;

@Value
public class CatName {

  public static final int RIDICULOUS_CAT_NAME_LENGTH = 200;
  public String stringValue;

  public CatName(String stringValue) {
    if (stringValue == null || stringValue.isEmpty()) {
      throw new IllegalArgumentException("Cat name cannot be null or empty");
    } else if (RIDICULOUS_CAT_NAME_LENGTH < stringValue.length()) {
      throw new IllegalArgumentException("Cat name may not exceed " + RIDICULOUS_CAT_NAME_LENGTH + " characters.");
    } else {
      this.stringValue = stringValue;
    }
  }

  public String toString() {
    return "Cat name starting with: " + stringValue.substring(0,3);
  }

  public String displayValue() {
    return this.stringValue;
  }
}