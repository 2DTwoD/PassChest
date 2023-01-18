package org.goznak.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "credentials_ids")
public class CredentialsIds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
}

