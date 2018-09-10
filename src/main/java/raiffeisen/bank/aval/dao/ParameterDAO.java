package raiffeisen.bank.aval.dao;

import raiffeisen.bank.aval.dto.Parameter;

import java.util.List;

public interface ParameterDAO {

    List<Parameter> getParametersByFunction(Long funId);
}
