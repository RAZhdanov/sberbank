package ru.sberbank.sberbank.entity;

import lombok.Data;
import ru.sberbank.sberbank.util.AbstractGeneralParentEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(schema = "PUBLIC", name = "TBL_RAWTRANSACTIONS")
@Data
public class SberRawTransactionEntity extends AbstractGeneralParentEntity<SberRawTransactionEntity> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INCOME")
    private double income;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CASH")
    private double cash;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_OF_TRANSACTION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfTransaction;
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private SberAccountEntity accountId;

    public SberRawTransactionEntity() {
    }

    public SberRawTransactionEntity(Integer id) {
        this.id = id;
    }

    public SberRawTransactionEntity(double income, double cash, Date dateOfTransaction, SberAccountEntity accountId){
        this.income = income;
        this.cash = cash;
        this.dateOfTransaction = dateOfTransaction;
        this.accountId = accountId;
    }

    public SberRawTransactionEntity(Integer id, double income, double cash, Date dateOfTransaction) {
        this.id = id;
        this.income = income;
        this.cash = cash;
        this.dateOfTransaction = dateOfTransaction;
    }
}
