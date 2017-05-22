package com.isa.rest.service.restlet;

public class Adage {
  private int id;

  private String words;

  private int wordCount;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getWords() {
    return words;
  }

  public void setWords(String words) {
    this.words = words;
    this.wordCount = words.trim().split("\\s+").length;
  }

  public int getWordCount() {
    return wordCount;
  }

  public void setWordCount(int wordCount) {
    this.wordCount = wordCount;
  }

  @Override
  public String toString() {
    return String.format("%2d: ", id) + words + " -- " + wordCount + " words";
  }
}
