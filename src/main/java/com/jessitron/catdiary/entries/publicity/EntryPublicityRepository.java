package com.jessitron.catdiary.entries.publicity;

import com.jessitron.catdiary.entries.deletion.EntryDeletion;
import org.springframework.data.repository.CrudRepository;

public interface EntryPublicityRepository extends CrudRepository<EntryDeletion, Long> {

}