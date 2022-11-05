package com.project.TravelAgency.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "transportType")
    private Set<Transport> transportMeans;
}
