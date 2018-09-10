package raiffeisen.bank.aval.dto.impl;

import lombok.Data;
import raiffeisen.bank.aval.dto.Group;

import java.io.Serializable;
import java.sql.Date;


@Data
public class GroupImpl extends BaseBeanImpl implements Group, Serializable {

    private static final long serialVersionUID = 1L;

    public GroupImpl() {
    }

    public GroupImpl(String name, String descr, Date ctime) {
        super(name, descr, ctime);
    }

    public GroupImpl(Long id, String name, String descr, Date ctime) {
        super(id, name, descr, ctime);
    }
}
