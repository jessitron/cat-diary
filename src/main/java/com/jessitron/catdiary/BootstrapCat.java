package com.jessitron.catdiary;

import com.jessitron.catdiary.cats.Cat;
import com.jessitron.catdiary.cats.CatName;
import com.jessitron.catdiary.cats.CatService;
import com.jessitron.catdiary.entries.Entry;
import com.jessitron.catdiary.entries.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapCat implements ApplicationRunner {

  private final CatService catService;

  private final EntryRepository entryRepo;

  @Autowired
  public BootstrapCat(CatService catService, EntryRepository entryRepo) {
    this.catService = catService;
    this.entryRepo = entryRepo;
  }

  public void run(ApplicationArguments args) {
    catService.createIfNotExists(new CatName("Bootsy"), "meow", 1);

    var pixieReport = catService.createIfNotExists(new CatName("Pixie"), "", 9);
    if (pixieReport.isCreated()) {
      var pixie = pixieReport.getCatIdentity().getCat();
      saveEntry(pixie, "I sit on them.", "https://raw.githubusercontent.com/jessitron/cat-diary/master/cat-pictures/pixie-on-computer.jpg", "Computers");
    }

    var pinchoReport = catService.createIfNotExists(new CatName("Pincho"), "smart", 8);
    if (pinchoReport.isCreated()) {
      var pincho = pinchoReport.getCatIdentity().getCat();
      saveEntry(pincho, "You cannot see my internal shape when I fold into a bean", "https://raw.githubusercontent.com/jessitron/cat-diary/master/cat-pictures/pincho_yoga.jpg", "I am a pincho bean");
      saveEntry(pincho, "My unicorn horn is heavy.", "https://raw.githubusercontent.com/jessitron/cat-diary/master/cat-pictures/pincho_unicorn.jpg", "I bear the burden of magic");
      saveEntry(pincho, "Violets are blue\r\nI am the most handsome cat\r\nand you think so too.", "https://raw.githubusercontent.com/jessitron/cat-diary/master/cat-pictures/pincho_strawhat.jpg", "Roses are red");
      saveEntry(pincho, "You can become trapped in merriment", "https://raw.githubusercontent.com/jessitron/cat-diary/master/cat-pictures/pincho_santa.jpg", "Beware Christmas");
      saveEntry(pincho, "I am dangerous, and fancy", "https://raw.githubusercontent.com/jessitron/cat-diary/master/cat-pictures/pincho_purrate_2.jpg", "Fear me");
    }
  }

  private void saveEntry(Cat cat, String complaint, String imageUrl, String title) {
    entryRepo.save(new Entry(cat, title, complaint, imageUrl));
  }
}

