package org.goznak.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
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
    @OneToMany(mappedBy = "systemId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubSystem> subSystems;
    @Transient
    SystemService systemService;
    public boolean systemExist(){
        if(systemService == null){
            return false;
        }
        System system = systemService.findFirstByName(name);
        return system != null && system.getId() != id;
    }
    @Override
    public boolean equals(Object o){
        if (getClass() != o.getClass()) {
            return false;
        }
        return ((System) o).getId() == id;
    }
    @Override
    public String toString(){
        return String.format("System with name: %s", name);
    }
}
