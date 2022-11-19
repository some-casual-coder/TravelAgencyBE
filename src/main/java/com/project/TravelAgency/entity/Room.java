package com.project.TravelAgency.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private int capacity;
    private double pricePerDay;

    @OneToMany(mappedBy = "room")
    private Set<Image> images;

    @OneToOne(targetEntity = Hotel.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_room_hotel"))
    private Hotel hotel;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "room_amenities",
            joinColumns = {
                    @JoinColumn(name = "roomId")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "amenityId")
            }
    )
    private Set<Amenity> amenities;
}
