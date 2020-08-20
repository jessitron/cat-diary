package com.jessitron.catdiary.pictures;

import com.jessitron.catdiary.cats.CatName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CatPictureUrlConverter implements AttributeConverter<CatPictureUrl, String> {

  @Override
  public String convertToDatabaseColumn(CatPictureUrl domainPrimitive) {
    return domainPrimitive.stringValue();
  }

  @Override
  public CatPictureUrl convertToEntityAttribute(String dbData) {
    return CatPictureUrl.fromString(dbData);
  }
}
