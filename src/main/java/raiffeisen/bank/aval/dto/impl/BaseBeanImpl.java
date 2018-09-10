package raiffeisen.bank.aval.dto.impl;

import lombok.Data;
import raiffeisen.bank.aval.dto.BaseBean;

import java.io.Serializable;
import java.sql.Date;

@Data
public abstract class BaseBeanImpl implements BaseBean, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String descr;
    private Date ctime;

    public BaseBeanImpl() {
    }

    public BaseBeanImpl(String name, String descr, Date ctime) {
        this.name = name;
        this.descr = descr;
        this.ctime = ctime;
    }

    public BaseBeanImpl(Long id, String name, String descr, Date ctime) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.ctime = ctime;
    }
}
