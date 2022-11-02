package com.project.TravelAgency.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "means_of_transport")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String model;
    private String chargedPer;
    private int capacity;
    private double price;
    private Double latitude;
    private Double longitude;

//    @OneToOne(targetEntity = TransportType.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_transport_type"))
//    private Long transportType;

    @OneToMany(mappedBy = "transport")
    private Set<Image> images;

    @OneToMany(mappedBy = "transport")
    private Set<TransportAddOn> addOns;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "transportType", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TransportType transportType;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_transport_owner"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Long owner;

}
