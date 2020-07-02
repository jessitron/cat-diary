package com.jessitron.catdiary.cats;

import org.springframework.data.repository.CrudRepository;

public interface CatRepository extends CrudRepository<Cat, Long> {

  public Cat findByCatName(String catName);

}