package com.jessitron.catdiary.bank;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BankAccountConverter implements AttributeConverter<BankAccount, String> {
  @Override
  public String convertToDatabaseColumn(BankAccount attribute) {
    return attribute.accountNumber;
  }

  @Override
  public BankAccount convertToEntityAttribute(String dbData) {
    return new BankAccount(dbData);
  }
}
