package com.jessitron.catdiary.entries.deletion;

import com.jessitron.catdiary.cats.Cat;
import com.jessitron.catdiary.entries.Entry;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name="ENTRY_DELETIONS")
public class EntryDeletion {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(optional = false)
  private final Entry entry;

  Date deletedAt;

  @PrePersist
  void deleted() {
    this.deletedAt = new Date();
  }

}
