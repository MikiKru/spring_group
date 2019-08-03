package pl.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;
    @NotBlank
    private String title;
    @NotBlank
    @Column(columnDefinition = "text")
    private String message;
    private LocalDate dateStart = LocalDate.now();
    @Column(name = "task_interval")
    private Integer interval;
    @ManyToMany
    @JoinTable(
            name = "task_employee",
            joinColumns = @JoinColumn(name= "task_id"),
            inverseJoinColumns = @JoinColumn(name= "employee_id")
    )
    @JsonIgnore
    List<User> users = new ArrayList<>();
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "project_id")
    // pole o nazwie jak w mappedBy
    private Project project;

    public Task(@NotBlank String title, @NotBlank String message, @NotBlank LocalDate dateStart, @NotBlank Integer interval, Project project) {
        this.title = title;
        this.message = message;
        this.dateStart = dateStart;
        this.interval = interval;
        this.project = project;
    }
}
