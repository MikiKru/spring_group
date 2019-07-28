package pl.taskmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
@Data
@AllArgsConstructor
public class TaskDto {
    @NotBlank
    private String title;
    @NotBlank
    @Column(columnDefinition = "text")
    private String message;
    @NotBlank
    private LocalDate dateStart = LocalDate.now();
    @NotBlank
    @Column(name = "task_interval")
    private Integer interval;
}
