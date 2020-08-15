package com.jessitron.catdiary.entries;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.jessitron.catdiary.cats.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping("/entries")
public class EntryController {

  private final EntryRepository entryRepo;

  private final CatService catService;

  @Autowired
  public EntryController(EntryRepository entryRepo, CatService catService) {
    this.entryRepo = entryRepo;
    this.catService = catService;
  }

  @ModelAttribute
  public void featureFlags(Model model, @Value("${feature.publicEntries:false}") boolean showPublicEntries) {
    model.addAttribute("showPublicEntries", showPublicEntries);
  }

  @GetMapping
  public String entries(Model model, @AuthenticationPrincipal User catIdentity, HttpServletResponse response) {
    var cat = catService.getCatFromUsername(catIdentity.getUsername());
    List<EntryView> entries = entryRepo.findAllByCat(cat)
        .stream().map(EntryView::new)
        .sorted(Comparator.comparingLong(EntryView::getId).reversed())
        .collect(Collectors.toList());
    model.addAttribute("entries", entries);
    String message = "You are a great cat, " + cat.getCatName();
    model.addAttribute("message", message);
    model.addAttribute("newEntry", new EntryRequest());
    model.addAttribute("catSound", imagineCatSound());

    response.addCookie(new Cookie("catSound", imagineCatSound())); // gratuitous cookies
    response.addCookie(new Cookie("myCatName", cat.getCatName().stringValue));
    return "entries";
  }

  @GetMapping("/public")
  public String publicEntries(Model model,  @Value("${feature.publicEntries:false}") boolean showPublicEntries) {
    if(!showPublicEntries) {
      throw new FeatureTurnedOffException();
    }
    List<EntryView> entries = entryRepo.findAll()
        .stream().map(EntryView::new)
        .sorted(Comparator.comparingLong(EntryView::getId).reversed())
        .collect(Collectors.toList());
    model.addAttribute("entries", entries);

    return "public-entries";
  }

  private String imagineCatSound() {
    Random rand = new Random();
    return "M" + "m".repeat(rand.nextInt(2)) + //
        "r".repeat(rand.nextInt(4) + 1) + //
        "o".repeat(rand.nextInt(3) + 1) + //
        "w".repeat(rand.nextInt(2) + 1) + //
        "r".repeat(rand.nextInt(2) + 1) + //
        "!".repeat(rand.nextInt(3));
  }

  @PostMapping
  public String acceptEntry(EntryRequest newEntry, @AuthenticationPrincipal User catIdentity) {
    log.info("Got a new diary entry.");
    var cat = catService.getCatFromUsername(catIdentity.getUsername());
    entryRepo.save(new Entry(cat, newEntry.getTitle(), newEntry.getComplaint(), newEntry.getImageUrl()));
    return "redirect:/entries";
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "you see nooooothing")
  public static class FeatureTurnedOffException extends RuntimeException {
  }

}