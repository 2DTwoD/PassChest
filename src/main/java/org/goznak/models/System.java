package org.goznak.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.goznak.dao.SystemDAO;
import org.goznak.services.SystemService;

import java.util.Set;

@Data
@Entity
@Table(name = "systems")
public class System {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(min = 1, max = 50, message = "Имя системы должно содержать от 1 до 50 символов")
    private String name;
    @OneToMany(mappedBy="systemId")
    private Set<SubSystem> subSystems;
    @Transient
    SystemService systemService;
    public boolean systemExist(){
        return systemService != null && systemService.findByName(name).size() > 0;
    }
    @Override
    public boolean equals(Object o){
        if (getClass() != o.getClass()) {
            return false;
        }
        return ((System) o).getId() == getId();
    }
}
