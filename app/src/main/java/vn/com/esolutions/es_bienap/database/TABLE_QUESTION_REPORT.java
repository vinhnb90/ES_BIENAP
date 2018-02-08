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

@Table(name = "TABLE_QUESTION_REPORT")
public class TABLE_QUESTION_REPORT {
    @PrimaryKey
    @AutoIncrement
    @Collumn(name = "ID_TABLE_QUESTION_REPORT", type = TYPE.INTEGER, other = "NOT NULL")
    private int ID_TABLE_QUESTION_REPORT;

    @Collumn(name = "ID_TABLE_REPORT", type = TYPE.INTEGER, other = "NOT NULL")
    private String ID_TABLE_REPORT;

    @Collumn(name = "ID_TABLE_ELEMENT_REPORT", type = TYPE.INTEGER, other = "NOT NULL")
    private String ID_TABLE_ELEMENT_REPORT;

    @Collumn(name = "QUESTION_NAME", type = TYPE.TEXT, other = "NOT NULL")
    private String QUESTION_NAME;

    @EnumNameCollumn()
    public enum table {
        ID_TABLE_QUESTION_REPORT,
        ID_TABLE_REPORT,
        ID_TABLE_ELEMENT_REPORT,
        QUESTION_NAME;

        public static String getName() {
            return "TABLE_QUESTION_REPORT";
        }
    }

    public TABLE_QUESTION_REPORT() {
    }

    public TABLE_QUESTION_REPORT(@Params(name = "ID_TABLE_QUESTION_REPORT") int ID_TABLE_QUESTION_REPORT,
                                 @Params(name = "ID_TABLE_REPORT") String ID_TABLE_REPORT,
                                 @Params(name = "ID_TABLE_ELEMENT_REPORT") String ID_TABLE_ELEMENT_REPORT,
                                 @Params(name = "QUESTION_NAME") String QUESTION_NAME) {
        this.ID_TABLE_QUESTION_REPORT = ID_TABLE_QUESTION_REPORT;
        this.ID_TABLE_REPORT = ID_TABLE_REPORT;
        this.ID_TABLE_ELEMENT_REPORT = ID_TABLE_ELEMENT_REPORT;
        this.QUESTION_NAME = QUESTION_NAME;
    }

    public int getID_TABLE_QUESTION_REPORT() {
        return ID_TABLE_QUESTION_REPORT;
    }

    public void setID_TABLE_QUESTION_REPORT(int ID_TABLE_QUESTION_REPORT) {
        this.ID_TABLE_QUESTION_REPORT = ID_TABLE_QUESTION_REPORT;
    }

    public String getID_TABLE_REPORT() {
        return ID_TABLE_REPORT;
    }

    public TABLE_QUESTION_REPORT setID_TABLE_REPORT(String ID_TABLE_REPORT) {
        this.ID_TABLE_REPORT = ID_TABLE_REPORT;
        return this;
    }

    public String getID_TABLE_ELEMENT_REPORT() {
        return ID_TABLE_ELEMENT_REPORT;
    }

    public TABLE_QUESTION_REPORT setID_TABLE_ELEMENT_REPORT(String ID_TABLE_ELEMENT_REPORT) {
        this.ID_TABLE_ELEMENT_REPORT = ID_TABLE_ELEMENT_REPORT;
        return this;
    }

    public String getQUESTION_NAME() {
        return QUESTION_NAME;
    }

    public void setQUESTION_NAME(String QUESTION_NAME) {
        this.QUESTION_NAME = QUESTION_NAME;
    }

}

