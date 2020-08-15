package com.jessitron.catdiary.bank;

import com.jessitron.catdiary.cats.Cat;
import org.springframework.data.repository.CrudRepository;

public interface CatBankInfoRepository extends CrudRepository<CatBankInfo, Long> {
  public CatBankInfo findByCat(Cat cat);
}