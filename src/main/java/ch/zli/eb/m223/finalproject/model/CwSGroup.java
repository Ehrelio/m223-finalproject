package ch.zli.eb.m223.finalproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.*;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Entity
public class CwSGroup {
    @Id
    @GeneratedValue
    @Schema(readOnly = true)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "group")
    @JsonIgnoreProperties("category")
    @Fetch(FetchMode.JOIN)
    private List<CwSUser> members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CwSUser> getMembers() {
        return members;
    }

    public void setMembers(List<CwSUser> members) {
        this.members = members;
    }

    
}
