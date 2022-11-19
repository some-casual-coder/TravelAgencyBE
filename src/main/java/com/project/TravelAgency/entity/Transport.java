package com.project.TravelAgency.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int capacity;
    private double price;
   private String town;

//    @OneToOne(targetEntity = TransportType.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_transport_type"))
//    private Long transportType;

    @JsonIgnore
    @OneToMany(mappedBy = "transport")
    private Set<Image> images;

    @OneToMany(mappedBy = "transport")
    private Set<TransportAddOn> addOns;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "transportType", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TransportType transportType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner = new User();

}
