package com.jessitron.catdiary.catIdentities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EncodedPasswordConverter implements AttributeConverter<EncodedPassword, String> {

  @Override
  public String convertToDatabaseColumn(EncodedPassword domainPrimitive) {
    return domainPrimitive.stringValue;
  }

  @Override
  public EncodedPassword convertToEntityAttribute(String dbData) {
    return new EncodedPassword(dbData);
  }
}