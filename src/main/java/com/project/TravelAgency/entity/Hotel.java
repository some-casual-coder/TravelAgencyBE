package com.project.TravelAgency.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "hotels")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String town;
    private Double latitude;
    private Double longitude;
    private String imageUrl;
    private double rating;

//    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner",referencedColumnName = "id",nullable = false, foreignKey = @ForeignKey(name = "fk_hotel_owner"))
//    private Long owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;


    @OneToMany(mappedBy = "hotel")
    private Set<Image> images;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "hotel_amenities",
            joinColumns = {
                    @JoinColumn(name = "hotelId")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "amenityId")
            }
    )
    private Set<Amenity> amenities;

}
