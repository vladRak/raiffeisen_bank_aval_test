package raiffeisen.bank.aval.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raiffeisen.bank.aval.dao.GroupDAO;
import raiffeisen.bank.aval.dao.impl.GroupDAOImpl;
import raiffeisen.bank.aval.dto.Group;
import raiffeisen.bank.aval.jdbc.ProcedureCaller;
import raiffeisen.bank.aval.jdbc.StackFlow;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.sql.SQLException;
import java.util.List;

@ManagedBean(name = "groupService")
@ApplicationScoped
public class GroupService {

    private GroupDAO dao = new GroupDAOImpl();
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @PostConstruct
    public void init() {
        getGroups();
    }

    public List<Group> getGroups() {
        List<Group> list = null;
        try {
            list = dao.getAllGroups();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return list;
    }
}
