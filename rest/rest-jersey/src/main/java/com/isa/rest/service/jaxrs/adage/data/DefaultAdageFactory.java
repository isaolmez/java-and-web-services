package com.isa.rest.service.jaxrs.adage.data;

import java.util.Random;

public class DefaultAdageFactory implements AdageFactory {
  private final String[] aphorisms = {
    "For Test",
    "What can be shown cannot be said.",
    "If a lion could talk, we could not understand him.",
    "Philosophy is a battle against the bewitchment of our intelligence by means of language.",
    "Ambition is the death of thought.",
    "The limits of my language mean the limits of my world."
  };

  private final Random random = new Random();

  @Override
  public Adage randomAdage() {
    Adage adage = new Adage();
    adage.setWords(aphorisms[random.nextInt(aphorisms.length)]);
    return adage;
  }

  @Override
  public Adage sameAdage() {
    Adage adage = new Adage();
    adage.setWords(aphorisms[0]);
    return adage;
  }
}
