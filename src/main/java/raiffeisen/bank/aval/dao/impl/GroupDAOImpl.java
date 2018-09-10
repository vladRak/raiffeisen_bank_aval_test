package raiffeisen.bank.aval.dao.impl;

import raiffeisen.bank.aval.dao.GroupDAO;
import raiffeisen.bank.aval.dto.Group;
import raiffeisen.bank.aval.dto.impl.GroupImpl;
import raiffeisen.bank.aval.jdbc.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAOImpl implements GroupDAO {

    @Override
    public List<Group> getAllGroups() {
        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        ResultSet rsObj = null;
        List<Group> groups = null;
        try {
            connObj = ConnectionPool.INSTANCE.getConnection();
            pstmtObj = connObj.prepareStatement("SELECT * FROM GROUP_FUNCTION");
            rsObj = pstmtObj.executeQuery();
            groups = new ArrayList<>();
            while (rsObj.next()) {
                groups.add(
                        extractGroupFromResultSet(rsObj)
                );
            }
        } finally {
            try {
                if (rsObj != null) {
                    rsObj.close();
                }
                if (pstmtObj != null) {
                    pstmtObj.close();
                }
                if (connObj != null) {
                    connObj.close();
                }
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
            }
            return groups;
        }
    }

    private Group extractGroupFromResultSet(ResultSet rs) throws SQLException {

        Group group = new GroupImpl();

        group.setId(rs.getLong("ID"));
        group.setName(rs.getString("NAME"));
        group.setDescr(rs.getString("DESCR"));
        group.setCtime(rs.getDate("CTIME"));

        return group;
    }
}
