package com.jessitron.catdiary.catIdentities;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatIdentityController {

  @GetMapping("/login")
  public String whyIsThisSoHard() {
    return "login";
  }
}
