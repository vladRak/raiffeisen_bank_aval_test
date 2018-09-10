package raiffeisen.bank.aval.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raiffeisen.bank.aval.file_operations.Configs;
import raiffeisen.bank.aval.file_operations.ConfigsSingleton;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@ManagedBean(eager = true)
public class StackFlow {

    private static String script_location = "";
    private static String file_extension = ".sql";
    private static ProcessBuilder processBuilder = null;
    private static Configs configs = ConfigsSingleton.INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(ConfigsSingleton.class);

    @PostConstruct
    public void flow() {

        String path = System.getProperty("user.home") + "/aval/";

        logger.info("Path to scripts directory " + path);

        File file = new File(path);
        File[] list_files = file.listFiles(new FileFilter() {

            public boolean accept(File f) {
                if (f.getName().toLowerCase().endsWith(file_extension))
                    return true;
                return false;
            }
        });

        List<String> queries = new ArrayList<>();

        try {
            for (File f : list_files) {
                script_location = "@" + f.getAbsolutePath();//ORACLE
                logger.info("Execute script " + script_location);
                queries.add(script_location);
            }
        }catch (NullPointerException e){
            logger.error(e.toString());
        }

        queries.add("execute aval_pkg.create_and_fill();");

        if (sqlPlusExecutor(queries)) logger.info("---------------------- StackFlow INIT OK-----------------------");
        else logger.info("---------------------- StackFlow INIT FALSE-----------------------");
    }

    @PreDestroy
    public void clearDb() {
        List<String> queries = new ArrayList<>();
        queries.add("execute aval_pkg.drop_all_data();");
        queries.add("DROP PACKAGE aval_pkg;");

        if (sqlPlusExecutor(queries)) logger.info("----------------------DB CLEAR OK-----------------------");
        else logger.info("---------------------- DB CLEAR FALSE-----------------------");
    }


    private boolean sqlPlusExecutor(List<String> queries) {
        try {
            processBuilder = new ProcessBuilder(configs.getProperty("path.to.sqlplus"));

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

            bw.write(
                    "connect " +
                            configs.getProperty("db.user") + "/" +
                            configs.getProperty("db.password") + "@" +
                            configs.getProperty("db.url"));
            bw.newLine();
            bw.flush();

            for (String query : queries) {
                bw.write(query);
                bw.newLine();
                bw.flush();
            }

            bw.write("exit");
            bw.newLine();
            bw.flush();

            return true;

        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }
}

