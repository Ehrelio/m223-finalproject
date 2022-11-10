package ch.zli.eb.m223.finalproject.model;

import javax.persistence.*;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.List; 
@NamedQueries({
    @NamedQuery(name = "CwSUser.findByEmail", query = "SELECT u FROM CwSUser u WHERE u.email = :email")
  })
@Entity
public class CwSUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(readOnly = true)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private boolean admin;

    @ManyToOne(optional = true)
    @Fetch(FetchMode.JOIN)
    private CwSGroup group;

    @OneToMany(mappedBy = "csWUser")
    @JsonIgnoreProperties("csWUser")
    @Fetch(FetchMode.JOIN)
    private List<Booking> bookings;

    public CwSUser(String name, String firstname, LocalDate birthdate, String email, String hashedPassword,
    boolean admin, CwSGroup group, List<Booking> bookings) {
        this.name = name;
        this.firstname = firstname;
        this.birthdate = birthdate;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.admin = admin;
        this.group = group;
        this.bookings = bookings;
    }

    public CwSUser(){}
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public CwSGroup getGroup() {
        return group;
    }

    public void setGroup(CwSGroup group) {
        this.group = group;
    }

    public Long getId() {
        return id;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<Booking> getBookings() {
        return bookings;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    
}
