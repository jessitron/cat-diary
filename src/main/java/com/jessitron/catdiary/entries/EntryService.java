package com.jessitron.catdiary.entries;

import com.jessitron.catdiary.cats.Cat;
import com.jessitron.catdiary.entries.deletion.EntryDeletion;
import com.jessitron.catdiary.entries.deletion.EntryDeletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

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

  public Stream<Entry> findAllByCat(Cat cat) {
    return entryRepo.findAllByCat(cat).stream().filter(entry -> !entry.hasBeenDeleted());
  }

  public Stream<Entry> findAll() {
    return entryRepo.findAll().stream().filter(entry -> !entry.hasBeenDeleted());
  }

  public EntryDeletion delete(Entry entry) {
    return entryDeletionRepo.save(new EntryDeletion(entry));
  }
}
