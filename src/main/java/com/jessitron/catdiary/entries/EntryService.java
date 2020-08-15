package com.jessitron.catdiary.entries;

import com.jessitron.catdiary.cats.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class EntryService {

  private final EntryRepository entryRepo;

  @Autowired
  public EntryService(EntryRepository entryRepository) {
    this.entryRepo = entryRepository;
  }

  public Entry save(Cat cat, String complaint, String imageUrl, String title) {
    return entryRepo.save(new Entry(cat, complaint, imageUrl, title));
  }

  public List<Entry> findAllByCat(Cat cat) {
    return entryRepo.findAllByCat(cat);
  }

  public List<Entry> findAll() {
    return entryRepo.findAll();
  }
}
