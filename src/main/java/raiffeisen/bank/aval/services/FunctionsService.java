package raiffeisen.bank.aval.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raiffeisen.bank.aval.dao.FunctionDAO;
import raiffeisen.bank.aval.dao.impl.FunctionDAOImpl;
import raiffeisen.bank.aval.dto.Function;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;


@ManagedBean(name = "functionService")
@ApplicationScoped
public class FunctionsService {

    private FunctionDAO dao = new FunctionDAOImpl();
    private static final Logger logger = LoggerFactory.getLogger(FunctionsService.class);

    public List<Function> getFunctionsByGroup(Long groupId) {
        List<Function> list = null;
        try {
            list = dao.getFunctionsByGroup(groupId);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return list;
    }
}
