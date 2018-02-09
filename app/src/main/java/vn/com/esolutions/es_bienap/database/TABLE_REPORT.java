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
public class TABLE_REPORT implements Cloneable{
    @PrimaryKey
    @AutoIncrement
    @Collumn(name = "ID_TABLE_REPORT", type = TYPE.INTEGER, other = "NOT NULL")
    private int ID_TABLE_REPORT;

    @Collumn(name = "REPORT_NAME", type = TYPE.TEXT, other = "NOT NULL")
    private String REPORT_NAME;

    @Collumn(name = "MODE", type = TYPE.TEXT, other = "NOT NULL")
    private String MODE;

    @Collumn(name = "REPORT_DATE", type = TYPE.TEXT, other = "NOT NULL")
    private String REPORT_DATE;

    @Collumn(name = "REPORT_STATUS", type = TYPE.TEXT, other = "NOT NULL")
    private String REPORT_STATUS;


    @EnumNameCollumn()
    public enum table {
        ID_TABLE_REPORT,
        REPORT_NAME,
        MODE,
        REPORT_DATE,
        REPORT_STATUS;

        public static String getName() {
            return "TABLE_REPORT";
        }
    }

    public TABLE_REPORT() {
    }

    public TABLE_REPORT(@Params(name = "ID_TABLE_REPORT") int ID_TABLE_REPORT,
                        @Params(name = "REPORT_NAME") String REPORT_NAME,
                        @Params(name = "MODE") String MODE,
                        @Params(name = "REPORT_DATE") String REPORT_DATE,
                        @Params(name = "REPORT_STATUS") String REPORT_STATUS
    ) {
        this.ID_TABLE_REPORT = ID_TABLE_REPORT;
        this.REPORT_NAME = REPORT_NAME;
        this.MODE = MODE;
        this.REPORT_DATE = REPORT_DATE;
        this.REPORT_STATUS = REPORT_STATUS;
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


    public String getMODE() {
        return MODE;
    }

    public TABLE_REPORT setMODE(String MODE) {
        this.MODE = MODE;
        return this;
    }

    public String getREPORT_DATE() {
        return REPORT_DATE;
    }

    public TABLE_REPORT setREPORT_DATE(String REPORT_DATE) {
        this.REPORT_DATE = REPORT_DATE;
        return this;
    }

    public String getREPORT_STATUS() {
        return REPORT_STATUS;
    }

    public TABLE_REPORT setREPORT_STATUS(String REPORT_STATUS) {
        this.REPORT_STATUS = REPORT_STATUS;
        return this;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

