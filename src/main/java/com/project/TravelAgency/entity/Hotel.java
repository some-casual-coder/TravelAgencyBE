package com.project.TravelAgency.entity;

import lombok.*;

import javax.persistence.*;

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

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_hotel_owner"))
    private Long added_by;

}
