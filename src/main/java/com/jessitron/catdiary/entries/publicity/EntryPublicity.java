package com.jessitron.catdiary.entries.publicity;

import com.jessitron.catdiary.entries.Entry;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "ENTRY_PUBLICITIES")
public class EntryPublicity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(optional = false)
  private final Entry entry;

  @Enumerated(EnumType.STRING)
  private final Publicity publicity;

  Date recorded;

  @PrePersist
  void recorded() {
    this.recorded = new Date();
  }

}
