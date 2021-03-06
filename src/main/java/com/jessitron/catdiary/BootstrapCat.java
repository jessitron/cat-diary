package com.jessitron.catdiary;

import com.jessitron.catdiary.bank.BankAccount;
import com.jessitron.catdiary.bank.CatBankInfo;
import com.jessitron.catdiary.bank.CatBankInfoRepository;
import com.jessitron.catdiary.catIdentities.PlainTextPassword;
import com.jessitron.catdiary.cats.Cat;
import com.jessitron.catdiary.cats.CatName;
import com.jessitron.catdiary.cats.CatService;
import com.jessitron.catdiary.cats.lives.OriginalLives;
import com.jessitron.catdiary.entries.Entry;
import com.jessitron.catdiary.entries.EntryRepository;
import com.jessitron.catdiary.entries.EntryService;
import com.jessitron.catdiary.pictures.CatPictureUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class BootstrapCat implements ApplicationRunner {

  private final CatService catService;

  private final EntryService entryService;

  private final CatBankInfoRepository bankRepo;

  @Autowired
  public BootstrapCat(CatService catService, EntryService entryService, CatBankInfoRepository bankRepo) {
    this.catService = catService;
    this.entryService = entryService;
    this.bankRepo = bankRepo;
  }

  public void run(ApplicationArguments args) {
    catService.createIfNotExists(new CatName("Bootsy"), new PlainTextPassword("meow"), new OriginalLives(1));

    var pixieReport = catService.createIfNotExists(new CatName("Pixie"),
        new PlainTextPassword(""),
        new OriginalLives(2));
    bankRepo.save(new CatBankInfo(pixieReport.getCatIdentity().getCat(), new BankAccount("10000008")));
    if (pixieReport.isCreated()) {
      var pixie = pixieReport.getCatIdentity().getCat();
      saveEntry(pixie, "I sit on them.",
          "https://raw.githubusercontent.com/jessitron/cat-diary/main/cat-pictures/pixie-on-computer.jpg", "Computers");
    }

    var pinchoReport = catService.createIfNotExists(new CatName("Pincho"), new PlainTextPassword("smart"), new OriginalLives(8));
    if (pinchoReport.isCreated()) {
      var pincho = pinchoReport.getCatIdentity().getCat();
      saveEntry(pincho, "You cannot see my internal shape when I fold into a bean",
          "https://raw.githubusercontent.com/jessitron/cat-diary/main/cat-pictures/pincho_yoga.jpg",
          "I am a pincho bean");
      saveEntry(pincho, "My unicorn horn is <b>heavy</b>.",
          "https://raw.githubusercontent.com/jessitron/cat-diary/main/cat-pictures/pincho_unicorn.jpg",
          "I bear the burden of <b>magic</b>");
      saveDeletedEntry(pincho, "Violets are <span style='color: blue'>blue</span>\r\nI am the most handsome cat\r\nand you think so too.",
          "https://raw.githubusercontent.com/jessitron/cat-diary/main/cat-pictures/pincho_strawhat.jpg",
          "Roses are red");
      savePublicEntry(pincho,
          "Beware Christmas",
          "https://raw.githubusercontent.com/jessitron/cat-diary/main/cat-pictures/pincho_santa.jpg",
          "You can become trapped in <span style='color: red'>merriment</span>"
      );
      saveEntry(pincho, "I am dangerous, and <i>fancy</i>",
          "https://raw.githubusercontent.com/jessitron/cat-diary/main/cat-pictures/pincho_purrate_2.jpg",
          "Fear me");
    }
  }

  private void saveEntry(Cat cat, String complaint, String imageUrl, String title) {
    entryService.save(cat, title, CatPictureUrl.fromString(imageUrl), complaint);
  }

  private void saveDeletedEntry(Cat cat, String title, String imageUrl, String complaint) {
    var e = entryService.save(cat, title, CatPictureUrl.fromString(imageUrl), complaint);
    entryService.delete(e);
  }

  private void savePublicEntry(Cat cat, String title, String imageUrl, String complaint) {
    var e = entryService.save(cat, title, CatPictureUrl.fromString(imageUrl), complaint);
    entryService.makePublic(e);
  }
}

