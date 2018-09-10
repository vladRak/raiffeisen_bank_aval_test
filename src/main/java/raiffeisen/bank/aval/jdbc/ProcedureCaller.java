package raiffeisen.bank.aval.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ProcedureCaller {

    private static CallableStatement cstmt;
    private static Connection connObj;
    private static final Logger logger = LoggerFactory.getLogger(ProcedureCaller.class);

    public static void callProcedure(String procedureName) {
        String SQL = "{call " + procedureName + "}";
        connObj = ConnectionPool.INSTANCE.getConnection();
        try {
            cstmt = connObj.prepareCall(SQL);
            cstmt.execute();
        } catch (SQLException e) {
           logger.error(e.toString());
        }finally {
            try {
                if (cstmt != null) {
                    cstmt.close();
                }
                if (connObj != null) {
                    connObj.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.toString());
            }
        }
    }
}
