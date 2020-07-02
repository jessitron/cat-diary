package com.jessitron.catdiary.cats;

import lombok.Data;

@Data
public class CatRequest {

  String catName;
  Integer lives;
  String password;

}