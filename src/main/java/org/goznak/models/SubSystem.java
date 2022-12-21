package org.goznak.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "sub_systems")
public class SubSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "system_id")
    private int systemId;
    @OneToMany(mappedBy="subSystemId")
    private Set<PassSlice> slices;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_id", insertable=false, updatable=false)
    private System system;
}
