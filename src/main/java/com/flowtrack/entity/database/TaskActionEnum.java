package com.flowtrack.entity.database;

public enum TaskActionEnum {

  COMMENTTED("commented"),
  CHANGED_DESCRIPTION("changed description");

  public final String label;

  private TaskActionEnum(String label) {
    this.label = label;
  }

}
