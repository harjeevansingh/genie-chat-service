package com.taskmanager.entities;

import com.taskmanager.constants.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author harjeevanSingh
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task extends BaseEntity {

    private String title;

    private String description;

    @Enumerated(jakarta.persistence.EnumType.STRING)
    private TaskStatus status;

    private boolean active;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

}

