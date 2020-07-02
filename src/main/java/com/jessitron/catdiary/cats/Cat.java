package com.jessitron.catdiary.cats;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table(name="CATS")
public class Cat {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  //@Embedded
  private final String catName;
  private final Integer lives;

  public Cat(CatName catName, Integer lives) {
    this.catName = catName.getName();
    this.lives = lives;
  }

  public CatName getCatName() {
    return new CatName(catName);
  }
}