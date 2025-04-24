package org.example.cs489project.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cs489project.model.user.User;

import java.time.LocalDate;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate date;

    public Post(String content, User user, LocalDate date) {
        this.content = content;
        this.user = user;
        this.date = date;
    }
}
