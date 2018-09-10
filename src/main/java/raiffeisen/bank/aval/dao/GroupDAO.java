package raiffeisen.bank.aval.dao;

import raiffeisen.bank.aval.dto.Group;

import java.sql.SQLException;
import java.util.List;

public interface GroupDAO {

    List<Group> getAllGroups() throws SQLException;
}
