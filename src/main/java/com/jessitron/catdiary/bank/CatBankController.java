package com.jessitron.catdiary.bank;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bank")
public class CatBankController {

  private CatBankInfoRepository bankRepo;

  @PostMapping
  public String updateBankInfo(@ModelAttribute("bankRequest") CatBankInfoRequest bankRequest,
                               @AuthenticationPrincipal User catIdentity) { // do I need that model bit?
    // delete the old one and make a new one? probably should make a service.
    return "redirect:/cat/profile?error=sorry, update not implemented";
  }
}
