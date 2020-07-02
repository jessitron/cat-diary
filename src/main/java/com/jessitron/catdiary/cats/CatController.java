package com.jessitron.catdiary.cats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cat")
@SessionAttributes({"catName", "username"})
public class CatController {

  private CatService catService;

  @Autowired
  public CatController(CatService catService) {
    this.catService = catService;
  }

  @GetMapping("/new")
  public String showNewCat(Model model) {
    CatRequest catRequest = new CatRequest();
    catRequest.setLives(9); // default to 9 lives
    model.addAttribute("catRequest", catRequest);
    return "registration";
  }

  @PostMapping("/new")
  public String acceptNewCat(@ModelAttribute("catRequest") CatRequest catRequest, Model model) {
    var newIdentity = catService.createDangit(new CatName(catRequest.catName), catRequest.password, catRequest.lives);
    model.addAttribute("username", newIdentity.getUsername());
    model.addAttribute("catName", catRequest.catName);
    return "redirect:/cat/welcome";
  }

  @GetMapping("/welcome")
  public String welcomeNewCat() {
    return "welcome";
  }

}