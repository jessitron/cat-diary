package com.jessitron.catdiary.entries;

import com.jessitron.catdiary.cats.Cat;
import com.jessitron.catdiary.entries.deletion.EntryDeletion;
import com.jessitron.catdiary.entries.deletion.EntryDeletionRepository;
import com.jessitron.catdiary.entries.publicity.EntryPublicity;
import com.jessitron.catdiary.entries.publicity.EntryPublicityRepository;
import com.jessitron.catdiary.entries.publicity.Publicity;
import com.jessitron.catdiary.pictures.CatPictureUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class EntryService {

  private final EntryRepository entryRepo;
  private final EntryDeletionRepository entryDeletionRepo;
  private final EntryPublicityRepository entryPublicityRepo;

  @Autowired
  public EntryService(EntryRepository entryRepository, EntryDeletionRepository entryDeletionRepo, EntryPublicityRepository entryPublicityRepo) {
    this.entryRepo = entryRepository;
    this.entryDeletionRepo = entryDeletionRepo;
    this.entryPublicityRepo = entryPublicityRepo;
  }

  public Entry save(Cat cat, String title, CatPictureUrl imageUrl, String complaint) {
    return entryRepo.save(new Entry(cat, title, complaint, imageUrl));
  }

  public Stream<Entry> findAllByCat(Cat cat) {
    return entryRepo.findAllByCat(cat).stream().filter(entry -> !entry.hasBeenDeleted());
  }

  public Stream<Entry> findPublicEntries() {
    return findAll().filter(Entry::isPublic);
  }

  public Stream<Entry> findAll() {
    return entryRepo.findAll().stream().filter(entry -> !entry.hasBeenDeleted());
  }

  public EntryDeletion delete(Entry entry) {
    return entryDeletionRepo.save(new EntryDeletion(entry));
  }

  public Entry findById(long entryId) {
    return entryRepo.findById(entryId).orElse(null);
  }

  public Stream<Entry> findEntriesViewableBy(Cat cat) {
    return entryRepo.findAll().stream().filter(entry -> !entry.hasBeenDeleted()).filter(entry ->
        entry.isFromCat(cat) || entry.isPublic());
  }

  public EntryPublicity makePublic(Entry entry) {
    return entryPublicityRepo.save(new EntryPublicity(entry, Publicity.PUBLIC));
  }

  public EntryPublicity makePrivate(Entry entry) {
    return entryPublicityRepo.save(new EntryPublicity(entry, Publicity.PRIVATE));
  }
}
