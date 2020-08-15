package com.jessitron.catdiary.cats;

import javax.persistence.*;

import com.jessitron.catdiary.bank.CatBankInfo;
import com.jessitron.catdiary.cats.lives.CurrentLives;
import com.jessitron.catdiary.cats.lives.OriginalLives;
import com.jessitron.catdiary.death.CatDeath;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.util.Collections;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table(name="CATS")
public class Cat {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private final CatName catName;

  @OneToMany(mappedBy = "cat")
  private final List<CatDeath> deaths;

  @OneToOne(optional = true, mappedBy = "cat")
  private final CatBankInfo bankInfo;

  private final OriginalLives livesAtSignup;

  public Cat(CatName catName, OriginalLives lives) {
    this.catName = catName;
    this.livesAtSignup = lives;
    this.deaths = Collections.emptyList();
    this.bankInfo = null;
  }

  public CurrentLives getLives() {
    return this.livesAtSignup.decrementBy(this.deaths.size());
  }


}