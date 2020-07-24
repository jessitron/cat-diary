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

  private final CatName catName;
  private final Integer lives;

  public Cat(CatName catName, Integer lives) {
    this.catName = catName;
    this.lives = lives;
  }

}