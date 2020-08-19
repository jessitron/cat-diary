package com.jessitron.catdiary.cats;

import com.jessitron.catdiary.bank.CatBankInfoRequest;
import com.jessitron.catdiary.catIdentities.PlainTextPassword;
import com.jessitron.catdiary.cats.lives.OriginalLives;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Slf4j
@Controller
@RequestMapping("/cat")
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
  public String acceptNewCat(@ModelAttribute("catRequest") CatRequest catRequest, RedirectAttributes redirectAttributes) {
    var newIdentity = catService.create(new CatName(catRequest.catName),
        new PlainTextPassword(catRequest.password),
        new OriginalLives(catRequest.lives));
    redirectAttributes.addFlashAttribute("catName", newIdentity.getCat().getCatName().displayValue());
    redirectAttributes.addFlashAttribute("username", newIdentity.getUsername());
    return "redirect:/cat/welcome";
  }

  @GetMapping("/welcome")
  public String welcomeNewCat(@ModelAttribute("catName") String catNameInput,
                              @ModelAttribute("username") String usernameInput,
                              Model model) {
    var catName = new CatName(catNameInput);
    model.addAttribute("catName", catName.displayValue());
    model.addAttribute("username", usernameInput);
    return "welcome";
  }

  @GetMapping("/profile")
  public String catProfile(@AuthenticationPrincipal User catIdentity, Model model) {
    var cat = catService.getCatFromUsername(catIdentity.getUsername());
//    log.error("How many deaths now? " + cat.getDeaths().size());
    model.addAttribute("catName", cat.getCatName().displayValue());
    model.addAttribute("lives", cat.getLives().intValue);
    model.addAttribute("bankAccount", cat.getBankInfo() != null ? cat.getBankInfo().getBankAccount().displayValue() : "");
    model.addAttribute("maskedPassword", "*******");
    model.addAttribute("bankRequest", new CatBankInfoRequest());
    return "profile";
  }

  @PostMapping("/death")
  public String reportDeath(@AuthenticationPrincipal User catIdentity, Model model) {
    Cat beforeCat = catService.getCatFromUsername(catIdentity.getUsername());
    Cat afterCat = catService.reportDeath(beforeCat);
    model.addAttribute("catName", beforeCat.getCatName().displayValue());
    if (afterCat.getLives().isDead()) {
      return "rip";
    } else {
      model.addAttribute("message", sorryForYourDeath());
      model.addAttribute("lives", afterCat.getLives().intValue);
      return "deathReported";
    }
  }

  private String sorryForYourDeath() {
    String[] condolences = {
        "Sorry to hear about your death.",
        "Bummer, dude.",
        "Yikes!",
        "You were yeeted into the sun?",
        "The cat is dead. Long live the cat!",
        "Every day lived on this side of the dirt is a good day.",
        "Wow, I can't even tell. You look great!",
        "Death is a new beginning.",
        "Without death, there could be no life.",
        "Happy deathday!",
        "Welcome back.",
        "Hope it didn't hurt too much.",
        "May the next life be a longer one."
    };
    var r = new Random();
    return condolences[r.nextInt(condolences.length)];
  }

}