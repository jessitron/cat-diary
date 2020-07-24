package com.jessitron.catdiary.cats;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CatNameConverter implements AttributeConverter<CatName, String> {
  @Override
  public String convertToDatabaseColumn(CatName domainPrimitive) {
    return domainPrimitive.getName();
  }

  @Override
  public CatName convertToEntityAttribute(String dbData) {
    return new CatName(dbData);
  }
}
