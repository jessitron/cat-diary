package com.jessitron.catdiary.cats.lives;

import lombok.extern.slf4j.Slf4j;

/**
 * Represents the number of lives a cat has left.
 * Could be as high as 9, or as low as 0 (dead cat).
 */
@Slf4j
public class CurrentLives {
  public final int intValue;

  public CurrentLives(int intValue) {
    if (9 < intValue) {
      throw new IllegalArgumentException("You can't have more than nine lives");
    }
    if (intValue < 0) {
      log.warn("You're extra dead. Fewer than 0 current lives, using 0");
      this.intValue = 0;
    } else {
      this.intValue = intValue;
    }
  }

  public boolean isDead() {
    return this.intValue <= 0;
  }
}
