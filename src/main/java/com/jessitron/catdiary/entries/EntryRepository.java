package com.jessitron.catdiary.entries;

import java.util.List;

import com.jessitron.catdiary.cats.Cat;
import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<Entry, Long> {

  public List<Entry> findAllByCat(Cat cat);

  public List<Entry> findAll();

}