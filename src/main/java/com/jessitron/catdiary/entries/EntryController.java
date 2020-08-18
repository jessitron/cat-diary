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

  private final EntryService entryService;

  private final CatService catService;

  @Autowired
  public EntryController(EntryService entryService, CatService catService) {
    this.entryService = entryService;
    this.catService = catService;
  }

  @ModelAttribute
  public void featureFlags(Model model, @Value("${feature.publicEntries:false}") boolean showPublicEntries) {
    model.addAttribute("showPublicEntries", showPublicEntries);
  }

  @GetMapping
  public String entries(Model model, @AuthenticationPrincipal User catIdentity, HttpServletResponse response) {
    var cat = catService.getCatFromUsername(catIdentity.getUsername());
    List<EntryView> entries = entryService.findAllByCat(cat)
        .map(EntryView::new)
        .sorted(Comparator.comparingLong(EntryView::getId).reversed())
        .collect(Collectors.toList());
    model.addAttribute("entries", entries);
    String message = "You are a great cat, " + cat.getCatName().displayValue();
    model.addAttribute("message", message);
    model.addAttribute("newEntry", new EntryRequest());
    model.addAttribute("catSound", imagineCatSound());

    response.addCookie(new Cookie("catSound", imagineCatSound())); // gratuitous cookies
    response.addCookie(new Cookie("myCatName", cat.getCatName().stringValue));
    return "entries";
  }

  @GetMapping("/public")
  public String publicEntries(Model model, @Value("${feature.publicEntries:false}") boolean showPublicEntries) {
    if (!showPublicEntries) {
      throw new FeatureTurnedOffException();
    }
    List<EntryView> entries = entryService.findPublicEntries()
        .map(EntryView::new)
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
    entryService.save(cat, newEntry.getTitle(), newEntry.getComplaint(), newEntry.getImageUrl());
    return "redirect:/entries";
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "you see nooooothing")
  public static class FeatureTurnedOffException extends RuntimeException {
  }

  /**
   * what is wrong with this method?
   *
   * @param deleteEntry which one?
   */
  @PostMapping("/delete")
  public String deleteEntry(@RequestParam("entryId") long id) {
    var entry = entryService.findById(id);
    if (entry == null) {
      throw new MissingEntryException();
    }
    entryService.delete(entry);
    return "redirect:/entries"; // Can I give them an 'oops?'
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entry Not Found")
  public static class MissingEntryException extends RuntimeException {
  }

  @GetMapping("/make-public/{id}")
  public String makeEntryPublic(@PathVariable long id, @AuthenticationPrincipal User catIdentity) {
    var entry = entryService.findById(id);
    if (entry == null) {
      throw new MissingEntryException();
    }
    var cat = catService.getCatFromUsername(catIdentity.getUsername());
    if (!entry.isFromCat(cat)) {
      throw new NotSoFastException();
    }
    entryService.makePublic(entry);
    return "redirect:/entries"; // Can I give them an 'oops?'
  }

  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  public static class NotSoFastException extends RuntimeException {
  }

}