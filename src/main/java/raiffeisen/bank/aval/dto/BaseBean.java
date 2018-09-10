package raiffeisen.bank.aval.dto;

import java.sql.Date;

public interface BaseBean {
    Long getId();

    String getName();

    String getDescr();

    Date getCtime();

    void setId(Long id);

    void setName(String name);

    void setDescr(String descr);

    void setCtime(Date date);
}
