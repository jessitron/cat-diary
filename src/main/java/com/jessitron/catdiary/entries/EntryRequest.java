package com.jessitron.catdiary.entries;

import lombok.Data;

@Data
public class EntryRequest {

  private String title;
  private String complaint;
  private String imageUrl;

}