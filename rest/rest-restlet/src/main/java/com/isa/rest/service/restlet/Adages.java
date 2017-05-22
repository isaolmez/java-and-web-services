package com.isa.rest.service.restlet;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Adages {
  private static AtomicInteger id;

  private static CopyOnWriteArrayList<Adage> adageStore;

  static {
    String[] aphorisms = {
      "What can be shown cannot be said.",
      "If a lion could talk, we could not understand him.",
      "Philosophy is a battle against the bewitchment of our intelligence by means of language.",
      "Ambition is the death of thought.",
      "The limits of my language mean the limits of my world."
    };

    adageStore = new CopyOnWriteArrayList<>();
    id = new AtomicInteger();
    for (String aphorism : aphorisms) {
      add(aphorism);
    }
  }

  public static String toPlain() {
    StringBuilder result = new StringBuilder();
    for (Adage adage : adageStore) {
      result.append(adage.toString()).append("\n");
    }

    return result.toString();
  }

  public static List<Adage> getList() {
    return adageStore;
  }

  public static Adage find(int id) {
    Adage adage = null;
    for (Adage temp : adageStore) {
      if (temp.getId() == id) {
        adage = temp;
        break;
      }
    }

    return adage;
  }

  public static void add(String words) {
    final int nextId = id.incrementAndGet();
    Adage adage = new Adage();
    adage.setWords(words);
    adage.setId(nextId);
    adageStore.add(adage);
  }
}
