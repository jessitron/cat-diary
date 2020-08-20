package com.jessitron.catdiary.entries;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.*;

import com.jessitron.catdiary.cats.Cat;

import com.jessitron.catdiary.entries.deletion.EntryDeletion;
import com.jessitron.catdiary.entries.publicity.Publicity;
import com.jessitron.catdiary.entries.publicity.EntryPublicity;
import com.jessitron.catdiary.pictures.CatPictureUrl;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "ENTRIES")
public class Entry {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(optional = false)
  private final Cat cat;

  @OneToMany(mappedBy = "entry")
  private Collection<EntryDeletion> deletions = Collections.emptyList();


  @OneToMany(mappedBy = "entry")
  private Collection<EntryPublicity> publicities = Collections.emptyList();

  private final String title;
  private Date timestamp;
  private final String complaint;
  private final CatPictureUrl imageUrl;


  @PrePersist
  void timestamp() {
    this.timestamp = new Date();
  }

  public boolean hasBeenDeleted() {
    return !this.deletions.isEmpty();
  }

  public boolean isPublic() {
    return Publicity.PUBLIC == this.publicities.stream()
        .sorted(Comparator.comparingLong(EntryPublicity::getId).reversed())
        .findFirst().map(EntryPublicity::getPublicity)
        .orElse(Publicity.PRIVATE);
  }

  public boolean isFromCat(Cat whoIsLooking) {
    return whoIsLooking.getId().equals(cat.getId());
  }
}