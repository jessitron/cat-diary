package com.jessitron.catdiary.bank;

import com.jessitron.catdiary.cats.Cat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Data
public class CatBankInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(optional = false)
  private final Cat cat;

  private final BankAccount bankAccount;

}
