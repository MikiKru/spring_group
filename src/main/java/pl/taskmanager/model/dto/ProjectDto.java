package pl.taskmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    @NotBlank
    private String acronim;
    @NotBlank
    @Column(columnDefinition = "text")
    private String description;
    @NotBlank
    private LocalDate dateStart;
    @NotBlank
    private LocalDate dateStop;
}