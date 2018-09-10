package raiffeisen.bank.aval.managed_beans;

import lombok.Data;
import raiffeisen.bank.aval.dto.Group;
import raiffeisen.bank.aval.services.GroupService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(eager = true, name = "groupsController")
@SessionScoped
@Data
public class GroupsController implements Serializable {

    private List<Group> groups;

    @PostConstruct
    public void init() {
        setGroups();
    }

    @ManagedProperty("#{groupService}")
    private GroupService service;

    public void setGroups() {
        groups = service.getGroups();
    }
}
