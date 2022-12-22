package org.goznak.models;

import jakarta.persistence.*;
import lombok.Data;
import org.goznak.dao.SubSystemDAO;
import org.goznak.services.SubSystemService;
import org.goznak.services.SystemService;

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
    @Transient
    SubSystemService subSystemService;
    public boolean subSystemExist(){
        return subSystemService != null && subSystemService.findByName(name).size() > 0;
    }
}
