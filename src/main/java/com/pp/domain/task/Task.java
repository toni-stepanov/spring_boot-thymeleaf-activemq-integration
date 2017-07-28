package com.pp.domain.task;

import com.pp.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by astepanov
 */
@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long number;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
