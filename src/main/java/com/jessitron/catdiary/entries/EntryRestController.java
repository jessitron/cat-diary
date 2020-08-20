package com.jessitron.catdiary.entries;

import com.jessitron.catdiary.cats.CatService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/entries")
public class EntryRestController {

  private final CatService catService;
  private final EntryRepository entryService;

  public EntryRestController(CatService catService, EntryRepository entryService) {
    this.catService = catService;
    this.entryService = entryService;
  }

  @GetMapping("/mine")
  public Collection<Entry> allEntriesForCat(@AuthenticationPrincipal User catIdentity) {
    var cat = catService.getCatFromUsername(catIdentity.getUsername());
    // I was hoping this would return too much info about the cat
    // but it goes too far! and infinitely recurses on Cat<->BankAccount
    return entryService.findAllByCat(cat);
  }
}
