package raiffeisen.bank.aval.managed_beans;

import lombok.Data;
import raiffeisen.bank.aval.dto.Parameter;
import raiffeisen.bank.aval.services.ParametersService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "parameterController")
@RequestScoped
@Data
public class ParameterController implements Serializable {

    private List<Parameter> params;

    @ManagedProperty("#{parametersService}")
    private ParametersService service;

    public void getParametersByFunction() {
        Map<String, String> jsfParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Long function_id = Long.parseLong(jsfParams.get("function_id"));

        params = service.getParametersByFunction(function_id);
    }
}
