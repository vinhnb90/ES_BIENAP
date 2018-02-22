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

@Table(name = "TABLE_ANSWER_REPORT")
public class TABLE_ANSWER_REPORT {
    @PrimaryKey
    @AutoIncrement
    @Collumn(name = "ID_TABLE_ANSWER_REPORT", type = TYPE.INTEGER, other = "NOT NULL")
    private int ID_TABLE_ANSWER_REPORT;

    @Collumn(name = "ID_TABLE_QUESTION_REPORT", type = TYPE.INTEGER, other = "NOT NULL")
    private int ID_TABLE_QUESTION_REPORT;

    @Collumn(name = "TYPE_ELEMENT_REPORT", type = TYPE.TEXT, other = "NOT NULL")
    private String TYPE_ELEMENT_REPORT;

    @Collumn(name = "TITLE_ANSWER", type = TYPE.TEXT, other = "NOT NULL")
    private String TITLE_ANSWER;

    @Collumn(name = "MODE", type = TYPE.TEXT, other = "NOT NULL")
    private String MODE;


    @EnumNameCollumn()
    public enum table {
        ID_TABLE_ANSWER_REPORT,
        ID_TABLE_QUESTION_REPORT,
        TYPE_ELEMENT_REPORT,
        TITLE_ANSWER,
        MODE;

        public static String getName() {
            return "TABLE_ANSWER_REPORT";
        }
    }


    public TABLE_ANSWER_REPORT() {
    }

    public TABLE_ANSWER_REPORT(@Params(name = "ID_TABLE_ANSWER_REPORT") int ID_TABLE_ANSWER_REPORT,
                               @Params(name = "ID_TABLE_QUESTION_REPORT") int ID_TABLE_QUESTION_REPORT,
                               @Params(name = "TYPE_ELEMENT_REPORT") String TYPE_ELEMENT_REPORT,
                               @Params(name = "TITLE_ANSWER") String TITLE_ANSWER,
                               @Params(name = "MODE") String MODE) {
        this.ID_TABLE_ANSWER_REPORT = ID_TABLE_ANSWER_REPORT;
        this.ID_TABLE_QUESTION_REPORT = ID_TABLE_QUESTION_REPORT;
        this.TYPE_ELEMENT_REPORT = TYPE_ELEMENT_REPORT;
        this.TITLE_ANSWER = TITLE_ANSWER;
        this.MODE = MODE;
    }

    public int getID_TABLE_ANSWER_REPORT() {
        return ID_TABLE_ANSWER_REPORT;
    }

    public void setID_TABLE_ANSWER_REPORT(int ID_TABLE_ANSWER_REPORT) {
        this.ID_TABLE_ANSWER_REPORT = ID_TABLE_ANSWER_REPORT;
    }

    public int getID_TABLE_QUESTION_REPORT() {
        return ID_TABLE_QUESTION_REPORT;
    }

    public TABLE_ANSWER_REPORT setID_TABLE_QUESTION_REPORT(int ID_TABLE_QUESTION_REPORT) {
        this.ID_TABLE_QUESTION_REPORT = ID_TABLE_QUESTION_REPORT;
        return this;
    }

    public String getTYPE_ELEMENT_REPORT() {
        return TYPE_ELEMENT_REPORT;
    }

    public TABLE_ANSWER_REPORT setTYPE_ELEMENT_REPORT(String TYPE_ELEMENT_REPORT) {
        this.TYPE_ELEMENT_REPORT = TYPE_ELEMENT_REPORT;
        return this;
    }

    public String getTITLE_ANSWER() {
        return TITLE_ANSWER;
    }

    public void setTITLE_ANSWER(String TITLE_ANSWER) {
        this.TITLE_ANSWER = TITLE_ANSWER;
    }

    public String getMODE() {
        return MODE;
    }

    public void setMODE(String MODE) {
        this.MODE = MODE;
    }
}

