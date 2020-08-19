package ru.sberbank.sberbank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.sberbank.sberbank.util.AbstractGeneralParentEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(schema = "PUBLIC", name = "TBL_USERS")
@Data
public class SberUserEntity extends AbstractGeneralParentEntity<SberUserEntity> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "EMAIL")
    private String email;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<SberClientEntity> tblSberclientCollection;

    public SberUserEntity() {
    }

    public SberUserEntity(Integer id) {
        this.id = id;
    }

    public SberUserEntity(Integer id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
