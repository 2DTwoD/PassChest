package org.goznak.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.goznak.services.SubSystemService;
import org.goznak.services.SystemService;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "sub_systems")
public class SubSystem implements Comparable<SubSystem> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(min = 1, max = 50, message = "Имя подсистемы должно содержать от 1 до 50 символов")
    private String name;
    @Column(name = "system_id", insertable = false, updatable = false)
    private int systemId;
    @OneToMany(mappedBy="subSystemId")
    private Set<PassSlice> slices;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "system_id")
    private System system;
    @Transient
    SubSystemService subSystemService;
    @Transient
    SystemService systemService;
    @Transient
    String systemName;
    public boolean subSystemExist(){
        if(subSystemService == null){
            return false;
        }
        boolean result = false;
        List<SubSystem> subSystems = subSystemService.findByName(name);
        for(SubSystem subSystem: subSystems){
            if(subSystem.getSystem().getName().equals(systemName) && subSystem.getId() != id){
                result = true;
                break;
            }
        }
        return result;
    }
    public boolean systemNotExist(){
        return subSystemService != null && systemService.findByName(systemName).isEmpty();
    }
    public void updateSystemName(){
        if(system != null) {
            systemName = system.getName();
        }
    }
    @Override
    public boolean equals(Object o){
        if (getClass() != o.getClass()) {
            return false;
        }
        return ((SubSystem) o).getId() == id;
    }
    @Override
    public String toString(){
        return String.format("Subsystem with system name: %s, name: %s",system.getName(), name);
    }
    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(SubSystem subSystem) {
        return (system.getName() + name).compareTo(subSystem.getSystem().getName() + subSystem.getName());
    }
}
