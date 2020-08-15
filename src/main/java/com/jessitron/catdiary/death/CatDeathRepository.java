package com.jessitron.catdiary.death;

import com.jessitron.catdiary.cats.Cat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CatDeathRepository extends CrudRepository<CatDeath, Long> {
  public List<CatDeath> findAllByCat(Cat cat);
}