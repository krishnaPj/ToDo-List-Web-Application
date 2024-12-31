package net.entities;

import java.util.Date;

import javax.persistence.*;
import lombok.*;

/**
 * Entity representing a user in the system.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String surname;

    @Column(unique = true, nullable = false, length = 256)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created;

    /**
     * Convenience constructor for creating a new user instance.
     *
     * @param name the first name of the user
     * @param surname the last name of the user
     * @param email the user's email (must be unique)
     * @param password the user's password (hashed)
     * @param userStatus the user's status (e.g., CONFIRMED, NOT_CONFIRMED)
     * @param created the timestamp when the user was created
     */
    public User(String name, String surname, String email, String password, UserStatus userStatus, Date created) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.userStatus = userStatus;
        this.created = created;
    }
}