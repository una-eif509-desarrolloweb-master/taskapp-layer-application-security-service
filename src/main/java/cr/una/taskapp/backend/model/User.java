/*
 * Universidad Nacional de Costa Rica  2020
 *
 * mike@guzmanalan.com
 */

package cr.una.taskapp.backend.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entity for the user
 */
@Entity
@Table( name = "users" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // Properties
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private Long idUser;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Date createDate;
    private boolean enabled;
    private boolean tokenExpired;

    // Relationship definition
    @OneToMany(mappedBy = "user")
    private List<Task> taskList;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private List<Role> roleList;
}
