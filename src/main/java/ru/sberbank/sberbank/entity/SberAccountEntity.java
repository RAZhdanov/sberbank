package ru.sberbank.sberbank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.sberbank.sberbank.util.AbstractGeneralParentEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(schema = "PUBLIC", name = "TBL_SBERACCOUNT")
@Data
public class SberAccountEntity extends AbstractGeneralParentEntity<SberAccountEntity> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ACCOUNT_NAME")
    private String accountName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CASH")
    private double cash;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    @JsonIgnore
    private Collection<SberRawTransactionEntity> tblRawtransactionsCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    @JsonIgnore
    private Collection<SberClientEntity> tblSberclientCollection;

    public SberAccountEntity() {
    }

    public SberAccountEntity(Integer id) {
        this.id = id;
    }

    public SberAccountEntity(Integer id, String accountName, double cash) {
        this.id = id;
        this.accountName = accountName;
        this.cash = cash;
    }
}
