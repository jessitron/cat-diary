package com.jessitron.catdiary.cats;

import com.jessitron.catdiary.catIdentities.CatIdentity;
import com.jessitron.catdiary.catIdentities.CatIdentityRepository;
import com.jessitron.catdiary.catIdentities.EncodedPassword;
import com.jessitron.catdiary.catIdentities.PlainTextPassword;
import com.jessitron.catdiary.cats.lives.OriginalLives;
import com.jessitron.catdiary.death.CatDeath;
import com.jessitron.catdiary.death.CatDeathRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CatService {

  private final CatRepository catRepo;

  private final CatDeathRepository catDeathRepository;

  private final CatIdentityRepository catIdRepo;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public CatService(CatRepository catRepo, CatDeathRepository catDeathRepository, CatIdentityRepository catIdRepo, PasswordEncoder passwordEncoder) {
    this.catRepo = catRepo;
    this.catDeathRepository = catDeathRepository;
    this.catIdRepo = catIdRepo;
    this.passwordEncoder = passwordEncoder;
  }

  public CatIdentity save(CatName catName,
                          PlainTextPassword plainTextPassword,
                          OriginalLives lives) {
    log.info("Saving cat: " + catName);
    var cat = catRepo.save(new Cat(catName, lives));
    return catIdRepo.save(new CatIdentity(catName.stringValue, new EncodedPassword(passwordEncoder, plainTextPassword), cat));
  }

  public CatIdentity create(CatName catName, PlainTextPassword password, OriginalLives lives) {
    log.info("I am here to create " + catName);
    var cat = catRepo.save(new Cat(catName, lives));
    var username = catName.stringValue;
    EncodedPassword encodedPassword = new EncodedPassword(passwordEncoder, password);
    try {
      return catIdRepo.save(new CatIdentity(username, encodedPassword, cat));
    }
    catch (DataIntegrityViolationException e) {
      log.info("Sorry, ya don't get your preferred username");
      // keep going
    }
    for (int i = 1; i < 1000; i++) {
      username = catName.stringValue + i;
      try {
        return catIdRepo.save(new CatIdentity(username, encodedPassword, cat));
      }
      catch (DataIntegrityViolationException e) {
        // keep going
      }
    }
    throw new RuntimeException("There are too many cats with that name");
  }

  public Cat getCatFromUsername(String username) {
    return catIdRepo.findByUsername(username).getCat();
  }

  @Data
  public class CatCreationReport {
    final boolean created;
    final CatIdentity catIdentity;
  }

  public CatCreationReport createIfNotExists(CatName catName,
                                             PlainTextPassword plainTextPassword,
                                             OriginalLives lives) {
    var foundCat = catIdRepo.findByUsername(catName.stringValue);
    if (foundCat != null) {
      return new CatCreationReport(false, foundCat);
    }
    var createdCat = save(catName, plainTextPassword, lives);
    return new CatCreationReport(true, createdCat);
  }

  public Cat reportDeath(Cat cat) {
    log.error("CAT DIED");
    catDeathRepository.save(new CatDeath(cat));
    return cat;
  }
}
