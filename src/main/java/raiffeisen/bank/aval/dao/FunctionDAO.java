package raiffeisen.bank.aval.dao;

import raiffeisen.bank.aval.dto.Function;

import java.util.List;

public interface FunctionDAO {

    List<Function> getFunctionsByGroup(Long groupId);
}
