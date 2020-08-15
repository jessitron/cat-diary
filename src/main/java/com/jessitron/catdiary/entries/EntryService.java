package com.jessitron.catdiary.entries;

import com.jessitron.catdiary.cats.Cat;
import com.jessitron.catdiary.entries.deletion.EntryDeletion;
import com.jessitron.catdiary.entries.deletion.EntryDeletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryService {

  private final EntryRepository entryRepo;
  private final EntryDeletionRepository entryDeletionRepo;

  @Autowired
  public EntryService(EntryRepository entryRepository, EntryDeletionRepository entryDeletionRepo) {
    this.entryRepo = entryRepository;
    this.entryDeletionRepo = entryDeletionRepo;
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

  public EntryDeletion delete(Entry entry) {
    return entryDeletionRepo.save(new EntryDeletion(entry));
  }
}
