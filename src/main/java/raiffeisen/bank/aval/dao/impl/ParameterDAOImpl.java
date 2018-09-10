package raiffeisen.bank.aval.dao.impl;

import raiffeisen.bank.aval.dao.ParameterDAO;
import raiffeisen.bank.aval.dto.Parameter;
import raiffeisen.bank.aval.dto.impl.ParameterImpl;
import raiffeisen.bank.aval.jdbc.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParameterDAOImpl implements ParameterDAO {
    @Override
    public List<Parameter> getParametersByFunction(Long funId) {

        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        ResultSet rsObj = null;
        List<Parameter> parameters = null;
        try {
            connObj = ConnectionPool.INSTANCE.getConnection();
            connObj.setAutoCommit(false);
            pstmtObj = connObj.prepareStatement("SELECT * FROM FUN_PARAM WHERE ID_FUN = ?");
            pstmtObj.setLong(1, funId);
            parameters = new ArrayList<>();

            rsObj = pstmtObj.executeQuery();
            while (rsObj.next()) {
                parameters.add(
                        extractParameterFromResultSet(rsObj)
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

            return parameters;
        }
    }

    private Parameter extractParameterFromResultSet(ResultSet rs) throws SQLException {

        Parameter parameter = new ParameterImpl();

        parameter.setId(rs.getLong("ID"));
        parameter.setIdFun(rs.getLong("ID_FUN"));
        parameter.setName(rs.getString("NAME"));
        parameter.setDescr(rs.getString("DESCR"));
        parameter.setCtime(rs.getDate("CTIME"));

        return parameter;
    }
}
