package raiffeisen.bank.aval.dto.impl;

import lombok.Data;
import raiffeisen.bank.aval.dto.Function;

import java.io.Serializable;
import java.sql.Date;

@Data
public class FunctionImpl extends BaseBeanImpl implements Function, Serializable {

    private static final long serialVersionUID = 1L;

    private Long idGroup;

    public FunctionImpl() {

    }

    public FunctionImpl(String name, String descr, Date ctime, Long idGroup) {
        super(name, descr, ctime);
        this.idGroup = idGroup;
    }

    public FunctionImpl(Long id, String name, String descr, Date ctime, Long idGroup) {
        super(id, name, descr, ctime);
        this.idGroup = idGroup;
    }
}
