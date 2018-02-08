package vn.com.esolutions.es_bienap.database;


import esolutions.com.esdatabaselib.baseSqlite.anonation.AutoIncrement;
import esolutions.com.esdatabaselib.baseSqlite.anonation.Collumn;
import esolutions.com.esdatabaselib.baseSqlite.anonation.EnumNameCollumn;
import esolutions.com.esdatabaselib.baseSqlite.anonation.Params;
import esolutions.com.esdatabaselib.baseSqlite.anonation.PrimaryKey;
import esolutions.com.esdatabaselib.baseSqlite.anonation.TYPE;
import esolutions.com.esdatabaselib.baseSqlite.anonation.Table;

/**
 * Created by VinhNB on 10/10/2017.
 */

@Table(name = "TABLE_REPORT")
public class TABLE_REPORT {
    @PrimaryKey
    @AutoIncrement
    @Collumn(name = "ID_TABLE_REPORT", type = TYPE.INTEGER, other = "NOT NULL")
    private int ID_TABLE_REPORT;

    @Collumn(name = "REPORT_NAME", type = TYPE.TEXT, other = "NOT NULL")
    private String REPORT_NAME;

    @EnumNameCollumn()
    public enum table {
        ID_TABLE_REPORT,
        REPORT_NAME;

        public static String getName() {
            return "TABLE_REPORT";
        }
    }

    public TABLE_REPORT() {
    }

    public TABLE_REPORT(@Params(name = "ID_TABLE_REPORT") int ID_TABLE_REPORT, @Params(name = "REPORT_NAME") String REPORT_NAME) {
        this.ID_TABLE_REPORT = ID_TABLE_REPORT;
        this.REPORT_NAME = REPORT_NAME;
    }

    public int getID_TABLE_REPORT() {
        return ID_TABLE_REPORT;
    }

    public void setID_TABLE_REPORT(int ID_TABLE_REPORT) {
        this.ID_TABLE_REPORT = ID_TABLE_REPORT;
    }

    public String getREPORT_NAME() {
        return REPORT_NAME;
    }

    public void setREPORT_NAME(String REPORT_NAME) {
        this.REPORT_NAME = REPORT_NAME;
    }

}

