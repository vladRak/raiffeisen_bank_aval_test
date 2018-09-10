package raiffeisen.bank.aval.dao.impl;

import raiffeisen.bank.aval.dao.FunctionDAO;
import raiffeisen.bank.aval.dto.Function;
import raiffeisen.bank.aval.dto.impl.FunctionImpl;
import raiffeisen.bank.aval.jdbc.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FunctionDAOImpl implements FunctionDAO {

    @Override
    public List<Function> getFunctionsByGroup(Long groupId) {

        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        ResultSet rsObj = null;
        List<Function> functions = null;
        try {
            connObj = ConnectionPool.INSTANCE.getConnection();
            connObj.setAutoCommit(false);
            pstmtObj = connObj.prepareStatement("SELECT * FROM FUNCTIONS WHERE ID_GROUP = ?");
            pstmtObj.setLong(1, groupId);
            functions = new ArrayList<>();

            rsObj = pstmtObj.executeQuery();
            while (rsObj.next()) {
                functions.add(
                        extractFunctionFromResultSet(rsObj)
                );
            }

            connObj.commit();
            connObj.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
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

            return functions;
        }
    }

    private Function extractFunctionFromResultSet(ResultSet rs) throws SQLException {

        Function function = new FunctionImpl();

        function.setId(rs.getLong("ID"));
        function.setIdGroup(rs.getLong("ID_GROUP"));
        function.setName(rs.getString("NAME"));
        function.setDescr(rs.getString("DESCR"));
        function.setCtime(rs.getDate("CTIME"));

        return function;
    }
}
