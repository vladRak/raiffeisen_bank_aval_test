package raiffeisen.bank.aval.dto.impl;

import lombok.Data;
import raiffeisen.bank.aval.dto.Parameter;

import java.io.Serializable;
import java.sql.Date;

@Data
public class ParameterImpl extends BaseBeanImpl implements Parameter, Serializable {

    private static final long serialVersionUID = 1L;

    private Long idFun;

    public ParameterImpl() {
    }

    public ParameterImpl(String name, String descr, Date ctime, Long idFun) {
        super(name, descr, ctime);
        this.idFun = idFun;
    }

    public ParameterImpl(Long id, String name, String descr, Date ctime, Long idFun) {
        super(id, name, descr, ctime);
        this.idFun = idFun;
    }
}
