package org.goznak.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.goznak.services.PassSliceService;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "pass_table")
public class PassSlice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "sub_system_id", insertable=false, updatable=false)
    private int subSystemId;
    @Column(name = "soft_name")
    @Size(min = 1, max = 100, message = "Название программы должно содержать от 1 до 100 символов")
    private String softName;
    @Column(name = "login")
    @Size(min = 0, max = 50, message = "Логин должен содержать от 0 до 50 символов")
    private String login;
    @Column(name = "password")
    @Size(min = 0, max = 100, message = "Пароль должен содержать от 0 до 100 символов")
    private String password;
    @Column(name = "last_change")
    private Date lastChange;
    @Column(name = "who_change", insertable=false, updatable=false)
    private String whoChange;
    @Column(name = "actual")
    private boolean actual;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_system_id")
    private SubSystem subSystem;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "who_change")
    private User user;
    @Transient
    private PassSliceService passSliceService;
    @Override
    public boolean equals(Object o){
        if (getClass() != o.getClass()) {
            return false;
        }
        return ((PassSlice) o).getId() == id;
    }
    @Override
    public String toString(){
        return String.format("PassSlice(System name:%s, SubSystem name: %s, login: %s, password: %s)", subSystem.getSystem().getName(),
                subSystem.getName(), login, password);
    }
    public boolean softExist(boolean fullCheck){
        if(passSliceService == null){
            return false;
        }
        List<PassSlice> passSlices = passSliceService.findByName(softName);
        boolean condition;
        for(PassSlice passSlice: passSlices){
            condition = fullCheck? passSlice.getLogin().equals(login) && passSlice.getPassword().equals(password):
                    passSlice.getLogin().equals(login);
            if(condition){
                return true;
            }
        }
        return false;
    }
}
