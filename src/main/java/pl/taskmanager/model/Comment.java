package pl.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;
    @Type(type = "text")
    private String content;
    private String owner;
    private LocalDateTime date_added = LocalDateTime.now();

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "task_id")
    private Task task;
}
