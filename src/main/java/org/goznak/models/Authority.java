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
}
