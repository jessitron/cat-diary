package com.jessitron.catdiary.entries;

import java.util.Date;

import javax.persistence.*;

import com.jessitron.catdiary.cats.Cat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name="ENTRIES")
public class Entry {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(optional = false)
  private final Cat cat;

  private final String title;
  private Date timestamp;
  private final String complaint;

  private final String imageUrl;

  @PrePersist
  void timestamp() {
    this.timestamp = new Date();
  }

}