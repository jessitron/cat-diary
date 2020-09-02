package com.jessitron.catdiary.entries;

import com.jessitron.catdiary.cats.Cat;
import org.springframework.lang.Nullable;

import lombok.Data;

@Data
public class EntryView {
  final Long id;
  final String title;
  final String complaint;
  final String catName;
  private final Cat cat;
  @Nullable
  final String imageUrl;
  final boolean showImage;
  final boolean currentlyPublic;

  public EntryView(Entry data) {
    this.id = data.getId();
    this.title = data.getTitle();
    this.complaint = data.getComplaint();
    this.imageUrl = data.getImageUrl().displayValue();
    this.catName = data.getCat().getCatName().displayValue();
    this.showImage = this.imageUrl != null && !this.imageUrl.isBlank();
    this.currentlyPublic = data.isPublic();
    this.cat = data.getCat();
  }

  public boolean isUpdatableBy(Cat loggedInCat) {
    return this.cat.equals(loggedInCat);
  }

}
