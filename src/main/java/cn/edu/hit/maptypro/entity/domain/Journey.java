package cn.edu.hit.maptypro.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "journey")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer", "user"})
public class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
}
