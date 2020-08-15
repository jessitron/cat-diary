package com.jessitron.catdiary.bank;

import lombok.Value;

@Value
public class BankAccount {

  public String accountNumber;

  public String displayValue() {
    return "******" + accountNumber.substring(accountNumber.length() - 2);
  }

  public BankAccount(String accountNumber) {
    if (accountNumber == null || accountNumber.isBlank()) {
      throw new IllegalArgumentException("Bank account number may not be null or blank");
    }
    if (!accountNumber.matches("\\d+")) {
      throw new IllegalArgumentException("Bank account number must be all digits");
    }
    this.accountNumber = accountNumber;
  }
}