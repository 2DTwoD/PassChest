package org.goznak.models;

import jakarta.persistence.*;
import lombok.Data;
import org.goznak.utils.Roles;

@Data
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    String username;
    @Enumerated(EnumType.STRING)
    Roles authority;
    @Override
    public boolean equals(Object o){
        if (getClass() != o.getClass()) {
            return false;
        }
        return ((Authority) o).getUsername().equals(username);
    }
    @Override
    public String toString(){
        return String.format("Username: %s, authority: %s",username, authority.toString());
    }
}
