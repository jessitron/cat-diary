package com.jessitron.catdiary.pictures;

import lombok.Value;
import org.apache.logging.log4j.util.Strings;
import org.springframework.lang.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

@Value
public class CatPictureUrl {

  public static final String DEFAULT_CAT_PICTURE_LOCATION = "https://raw.githubusercontent.com/jessitron/cat-diary/main/cat-pictures/pixie-on-computer.jpg";

  @FunctionalInterface
  public static interface CatPictureGenerator {
    public URL catPicture();
  }

  public URL urlValue;

  private CatPictureUrl(URL value) {
    this.urlValue = value;
  }

  public String displayValue() {
    return urlValue.toString();
  }

  public String stringValue() {
    return urlValue.toString();
  }

  public static CatPictureUrl mightBeEmpty(@Nullable String userSuppliedUrl, CatPictureGenerator urlGenerator) {
    if (!Strings.isBlank(userSuppliedUrl)) {
      try {
        return new CatPictureUrl(new URL(userSuppliedUrl));
      } catch (MalformedURLException e) {
        throw new IllegalArgumentException("Malformed URL for the cat picture");
      }
    }
    var generatedUrl = urlGenerator.catPicture();
    if (generatedUrl != null) {
      return new CatPictureUrl(generatedUrl);
    }

    return DEFAULT;
  }

  public static CatPictureUrl fromString(String urlString) {
    URL url;
    try {
      url = new URL(urlString);
    } catch (MalformedURLException e) {
      //log.error("Invalid URL retrieved. Using default");
      return DEFAULT;
    }

    return new CatPictureUrl(url);

  }

  public static CatPictureUrl DEFAULT;

  static { // static initializer, oooh
    try {
      DEFAULT = new CatPictureUrl(new URL(DEFAULT_CAT_PICTURE_LOCATION));
    } catch (MalformedURLException e) {
      throw new RuntimeException("Your default cat picture location is not a valid URL!! Fail everywhere!! " + DEFAULT_CAT_PICTURE_LOCATION);
    }
  }


}