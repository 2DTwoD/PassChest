package org.goznak.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "pass_table")
public class PassSlice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "sub_system_id")
    private int subSystemId;
    @Column(name = "soft_name")
    private String softName;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "last_change")
    private Date lastChange;
    @Column(name = "who_change")
    private String whoChange;
    @Column(name = "actual")
    private boolean actual;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_system_id", insertable=false, updatable=false)
    private SubSystem subSystem;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "who_change", insertable=false, updatable=false)
    private User user;
}
