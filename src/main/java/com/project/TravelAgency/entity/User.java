package com.project.TravelAgency.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private boolean verified;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
    joinColumns = {
            @JoinColumn(name = "user_id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "role_id")
    }
    )
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Hotel> hotels;
}
