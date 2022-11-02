package com.project.TravelAgency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "transport_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;

    @OneToMany(mappedBy = "transportType")
    private Set<Transport> transportMeans;
}
