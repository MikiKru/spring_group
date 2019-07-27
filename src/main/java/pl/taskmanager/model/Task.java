package pl.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @NotBlank
    private LocalDate dateStart = LocalDate.now();
    @NotBlank
    @Column(name = "task_interval")
    private Integer interval;
    @ManyToMany
    @JoinTable(
            name = "task_employee",
            joinColumns = @JoinColumn(name= "task_id"),
            inverseJoinColumns = @JoinColumn(name= "employee_id")
    )
    List<User> users = new ArrayList<>();
    @ManyToOne()
    @JoinColumn(name = "project_id")
    // pole o nazwie jak w mappedBy
    private Project project;

}
