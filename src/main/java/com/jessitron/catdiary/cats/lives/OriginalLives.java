package com.jessitron.catdiary.cats.lives;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class OriginalLives {

  public int intValue;

  public OriginalLives(int intValue) {
    if (9 < intValue) {
      log.warn("Cats are limited to 9 lives. Setting original lives to 9.");
      this.intValue = 9;
    } else if (intValue < 1) {
      log.warn("No dead cats. Setting original lives to 1");
      this.intValue = 1;
    } else {
      this.intValue = intValue;
    }
  }

  public CurrentLives decrementBy(int qty) {
    return new CurrentLives(this.intValue - qty);
  }
}