package com.jessitron.catdiary.cats;

import lombok.Value;

@Value
public class CatName {

  String name;

  public String toString() {
    return name;
  }
}