package com.project.TravelAgency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "amenities")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String Title;
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private Room room;

}
