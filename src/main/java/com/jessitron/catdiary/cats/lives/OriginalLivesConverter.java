package com.jessitron.catdiary.cats.lives;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OriginalLivesConverter implements AttributeConverter<OriginalLives, Integer> {

  @Override
  public Integer convertToDatabaseColumn(OriginalLives domainPrimitive) {
    return domainPrimitive.intValue;
  }

  @Override
  public OriginalLives convertToEntityAttribute(Integer dbData) {
    return new OriginalLives(dbData);
  }
}
