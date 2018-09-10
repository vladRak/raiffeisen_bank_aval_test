package raiffeisen.bank.aval.managed_beans;

import lombok.Data;
import raiffeisen.bank.aval.dto.Function;
import raiffeisen.bank.aval.services.FunctionsService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "functionController")
@SessionScoped
@Data
public class FunctionController implements Serializable {

    private List<Function> functions;

    @ManagedProperty("#{functionService}")
    private FunctionsService service;

    public void getFunctionsByGroup() {
        Map<String, String> jsfParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Long group_id = Long.parseLong(jsfParams.get("group_id"));

        functions = service.getFunctionsByGroup(group_id);
    }
}
