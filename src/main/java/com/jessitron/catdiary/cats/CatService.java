package com.jessitron.catdiary.cats;

import com.jessitron.catdiary.catIdentities.CatIdentity;
import com.jessitron.catdiary.catIdentities.CatIdentityRepository;
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

  private final CatIdentityRepository catIdRepo;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public CatService(CatRepository catRepo, CatIdentityRepository catIdRepo, PasswordEncoder passwordEncoder) {
    this.catRepo = catRepo;
    this.catIdRepo = catIdRepo;
    this.passwordEncoder = passwordEncoder;
  }

  public CatIdentity save(CatName catName, String plainTextPassword, int lives) {
    var cat = catRepo.save(new Cat(catName, lives));
    return catIdRepo.save(new CatIdentity(catName.getName(), passwordEncoder.encode(plainTextPassword), cat));
  }

  public CatIdentity createDangit(CatName catName, String plainTextPassword, int lives) {
    log.info("I am here to create " + catName);
    var cat = catRepo.save(new Cat(catName, lives));
    var username = catName.getName();
    try {
      return catIdRepo.save(new CatIdentity(username, passwordEncoder.encode(plainTextPassword), cat));
    }
    catch (DataIntegrityViolationException e) {
      log.info("Sorry, ya don't get your preferred username");
      // keep going
    }
    for (int i = 1; i < 1000; i++) {
      username = catName.getName() + i;
      try {
        return catIdRepo.save(new CatIdentity(username, passwordEncoder.encode(plainTextPassword), cat));
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

  public CatCreationReport createIfNotExists(CatName catName, String plainTextPassword, int lives) {
    var foundCat = catIdRepo.findByUsername(catName.getName());
    if (foundCat != null) {
      return new CatCreationReport(false, foundCat);
    }
    var createdCat = save(catName, plainTextPassword, lives);
    return new CatCreationReport(true, createdCat);
  }
}
