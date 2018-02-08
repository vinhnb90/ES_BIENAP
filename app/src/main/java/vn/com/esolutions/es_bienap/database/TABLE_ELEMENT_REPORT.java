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

@Table(name = "TABLE_ELEMENT_REPORT")
public class TABLE_ELEMENT_REPORT {
    @PrimaryKey
    @AutoIncrement
    @Collumn(name = "ID_TABLE_ELEMENT_REPORT", type = TYPE.INTEGER, other = "NOT NULL")
    private int ID_TABLE_ELEMENT_REPORT;

    @Collumn(name = "ELEMENT_NAME", type = TYPE.TEXT, other = "NOT NULL")
    private String ELEMENT_NAME;

    @EnumNameCollumn()
    public enum table {
        ID_TABLE_ELEMENT_REPORT,
        ELEMENT_NAME;

        public static String getName() {
            return "TABLE_ELEMENT_REPORT";
        }
    }

    public TABLE_ELEMENT_REPORT() {
    }

    public TABLE_ELEMENT_REPORT(@Params(name = "ID_TABLE_ELEMENT_REPORT") int ID_TABLE_ELEMENT_REPORT, @Params(name = "ELEMENT_NAME") String ELEMENT_NAME) {
        this.ID_TABLE_ELEMENT_REPORT = ID_TABLE_ELEMENT_REPORT;
        this.ELEMENT_NAME = ELEMENT_NAME;
    }

    public int getID_TABLE_ELEMENT_REPORT() {
        return ID_TABLE_ELEMENT_REPORT;
    }

    public void setID_TABLE_ELEMENT_REPORT(int ID_TABLE_ELEMENT_REPORT) {
        this.ID_TABLE_ELEMENT_REPORT = ID_TABLE_ELEMENT_REPORT;
    }

    public String getELEMENT_NAME() {
        return ELEMENT_NAME;
    }

    public void setELEMENT_NAME(String ELEMENT_NAME) {
        this.ELEMENT_NAME = ELEMENT_NAME;
    }

}

