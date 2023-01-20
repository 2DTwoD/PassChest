package org.goznak.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.goznak.services.PassSliceService;
import org.goznak.utils.CipherUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "pass_table")
public class PassSlice implements Comparable<PassSlice> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "sub_system_id", insertable=false, updatable=false)
    private int subSystemId;
    @Column(name = "soft_name")
    @Size(min = 1, max = 100, message = "Название программы должно содержать от 1 до 100 символов")
    private String softName;
    @Column(name = "login")
    @Size(min = 1, max = 50, message = "Логин должен содержать от 1 до 50 символов")
    private String login;
    @Column(name = "password")
    @Size(max = 200, message = "Пароль должен содержать от 0 до 200 символов")
    private String password;
    @Column(name = "last_change")
    private Date lastChange;
    @Column(name = "who_change", insertable=false, updatable=false)
    private String whoChange;
    @Column(name = "actual")
    private boolean actual;
    @Column(name = "role")
    @Size(min = 1, max = 100, message = "Роль пользователя должна содержать от 1 до 50 символов")
    private String role;
    @Column(name = "credentials_id", insertable=false, updatable=false)
    private Integer credentialsId;
    @Column(name = "comment")
    @Size(max = 100, message = "Комментарий не должен содержать больше 100 символов")
    private String comment;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_system_id")
    private SubSystem subSystem;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "who_change")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credentials_id")
    private CredentialsIds credentialsIds;
    @Transient
    private PassSliceService passSliceService;
    @Transient
    CipherUtil cipherUtil;
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
    public boolean softExist(){
        if(passSliceService == null){
            return false;
        }
        List<PassSlice> passSlices = passSliceService.findBySubSystemAndSoftName(subSystem, softName);
        for(PassSlice passSlice: passSlices){
            if(passSlice.getId() != id && passSlice.isActual() && passSlice.getLogin().equals(login)){
                return true;
            }
        }
        return false;
    }
    public boolean noChange() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if(passSliceService == null){
            return false;
        }
        PassSlice passSlice = passSliceService.findByCredentialsIdsAndLogin(credentialsIds, login);
        return passSlice != null && passSlice.getLogin().equals(login) && cipherUtil.decryptPass(passSlice.getPassword()).equals(password) &&
                passSlice.getRole().equals(role) && passSlice.getComment().equals(comment);
    }

    @Override
    public int compareTo(PassSlice passSlice) {
        return (subSystem.getSystem().getName() + subSystem.getName() + softName + login)
                .compareTo(passSlice.getSubSystem().getSystem().getName() + passSlice.getSubSystem().getName() +
                        passSlice.getSoftName() + passSlice.getLogin());
    }
}
