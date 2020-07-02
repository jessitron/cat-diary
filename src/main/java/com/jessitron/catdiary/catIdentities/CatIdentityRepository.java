package com.jessitron.catdiary.catIdentities;

import org.springframework.data.repository.CrudRepository;

public interface CatIdentityRepository extends CrudRepository<CatIdentity, Long> {
  CatIdentity findByUsername(String username);
}
