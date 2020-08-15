package com.jessitron.catdiary.death;

import com.jessitron.catdiary.cats.Cat;
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
@Table(name = "CAT_DEATHS")
public class CatDeath {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "CAT_ID")
  private final Cat cat;

  Date reportedAt;

  @PrePersist
  void reported() {
    this.reportedAt = new Date();
  }

}