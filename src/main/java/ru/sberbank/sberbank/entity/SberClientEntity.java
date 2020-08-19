package ru.sberbank.sberbank.entity;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import ru.sberbank.sberbank.util.AbstractGeneralParentEntity;
import ru.sberbank.sberbank.util.GeneralEntity;

import javax.persistence.*;

@Entity
@Table(schema = "PUBLIC", name = "TBL_SBERCLIENT")
@Data
public class SberClientEntity extends AbstractGeneralParentEntity<SberClientEntity>{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private SberAccountEntity accountId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private SberUserEntity userId;

    public SberClientEntity() {
    }

    public SberClientEntity(Integer id) {
        this.id = id;
    }
}
