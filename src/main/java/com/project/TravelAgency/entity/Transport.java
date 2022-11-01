package com.project.TravelAgency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "means_of_transport")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String model;
    private String charged_per;
    private int capacity;
    private double price;

    @OneToOne(targetEntity = TransportType.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_transport_type"))
    private Long transport_type;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_transport_owner"))
    private Long owner;

}
