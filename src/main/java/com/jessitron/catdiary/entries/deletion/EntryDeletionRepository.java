package com.jessitron.catdiary.entries.deletion;

import java.util.List;

import com.jessitron.catdiary.cats.Cat;
import org.springframework.data.repository.CrudRepository;

public interface EntryDeletionRepository extends CrudRepository<EntryDeletion, Long> {

}