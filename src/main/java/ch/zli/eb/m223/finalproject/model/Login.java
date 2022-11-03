package ch.zli.eb.m223.finalproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(readOnly = true)
    private Long id;

    @Column(nullable = false)
    private String hashedPassword;

    @OneToOne(optional = false, mappedBy = "login", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private User user;
    
}
