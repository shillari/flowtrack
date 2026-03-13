package com.flowtrack.entity.database;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="task_label")
public class TaskLabel {

  @EmbeddedId
  @AttributeOverrides({
    @AttributeOverride(name = "taskId", column = @Column(name = "task_id")),
    @AttributeOverride(name = "labelId", column = @Column(name = "label_id"))
  })
  private TaskLabelId id;

  @ManyToOne(fetch=FetchType.LAZY)
  @MapsId("taskId")
  private Task task;

  @ManyToOne(fetch=FetchType.LAZY)
  @MapsId("labelId")
  private Label label;
}
