package com.jessitron.catdiary.entries;

import org.springframework.lang.Nullable;

import lombok.Data;

@Data
public class EntryView {
  final Long id;
  final String title;
  final String complaint;
  final String catName;
  @Nullable
  final String imageUrl;
  final boolean showImage;
  final boolean currentlyPublic;

  public EntryView(Entry data) {
    this.id = data.getId();
    this.title = data.getTitle();
    this.complaint = data.getComplaint();
    this.imageUrl = data.getImageUrl();
    this.catName = data.getCat().getCatName().displayValue();
    this.showImage = this.imageUrl != null && !this.imageUrl.isBlank();
    this.currentlyPublic = data.isPublic();
  }

}
