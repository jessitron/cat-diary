package com.jessitron.catdiary.catIdentities;

import javax.persistence.*;

import com.jessitron.catdiary.cats.Cat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table(name="CAT_IDENTITIES")
public class CatIdentity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique=true)
  private final String username;
  private final String password;

  @OneToOne
  private final Cat cat;

  public CatIdentity(Cat cat, String encodedPassword) {
    this.username = cat.getCatName().getName();
    this.cat = cat;
    this.password = encodedPassword;
  }

}