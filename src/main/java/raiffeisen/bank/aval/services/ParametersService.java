package raiffeisen.bank.aval.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raiffeisen.bank.aval.dao.ParameterDAO;
import raiffeisen.bank.aval.dao.impl.ParameterDAOImpl;
import raiffeisen.bank.aval.dto.Parameter;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "parametersService")
@ApplicationScoped
public class ParametersService {

    private ParameterDAO dao = new ParameterDAOImpl();
    private static final Logger logger = LoggerFactory.getLogger(FunctionsService.class);

    public List<Parameter> getParametersByFunction(Long funId) {
        List<Parameter> list = null;
        try {
            list = dao.getParametersByFunction(funId);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return list;
    }
}
