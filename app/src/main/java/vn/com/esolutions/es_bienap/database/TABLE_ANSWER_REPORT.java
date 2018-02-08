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
    private String ID_TABLE_QUESTION_REPORT;

    @Collumn(name = "ID_TABLE_DEVICE", type = TYPE.INTEGER, other = "NOT NULL")
    private String ID_TABLE_DEVICE;

    @Collumn(name = "CONTENT_ANSWER", type = TYPE.TEXT, other = "NOT NULL")
    private String CONTENT_ANSWER;

    @EnumNameCollumn()
    public enum table {
        ID_TABLE_ANSWER_REPORT,
        ID_TABLE_QUESTION_REPORT,
        ID_TABLE_DEVICE,
        CONTENT_ANSWER;

        public static String getName() {
            return "TABLE_ANSWER_REPORT";
        }
    }


    public TABLE_ANSWER_REPORT() {
    }

    public TABLE_ANSWER_REPORT(@Params(name = "ID_TABLE_ANSWER_REPORT") int ID_TABLE_ANSWER_REPORT,
                               @Params(name = "ID_TABLE_QUESTION_REPORT") String ID_TABLE_QUESTION_REPORT,
                               @Params(name = "ID_TABLE_DEVICE") String ID_TABLE_DEVICE,
                               @Params(name = "CONTENT_ANSWER") String CONTENT_ANSWER) {
        this.ID_TABLE_ANSWER_REPORT = ID_TABLE_ANSWER_REPORT;
        this.ID_TABLE_QUESTION_REPORT = ID_TABLE_QUESTION_REPORT;
        this.ID_TABLE_DEVICE = ID_TABLE_DEVICE;
        this.CONTENT_ANSWER = CONTENT_ANSWER;
    }

    public int getID_TABLE_ANSWER_REPORT() {
        return ID_TABLE_ANSWER_REPORT;
    }

    public void setID_TABLE_ANSWER_REPORT(int ID_TABLE_ANSWER_REPORT) {
        this.ID_TABLE_ANSWER_REPORT = ID_TABLE_ANSWER_REPORT;
    }

    public String getID_TABLE_QUESTION_REPORT() {
        return ID_TABLE_QUESTION_REPORT;
    }

    public TABLE_ANSWER_REPORT setID_TABLE_QUESTION_REPORT(String ID_TABLE_QUESTION_REPORT) {
        this.ID_TABLE_QUESTION_REPORT = ID_TABLE_QUESTION_REPORT;
        return this;
    }

    public String getID_TABLE_DEVICE() {
        return ID_TABLE_DEVICE;
    }

    public TABLE_ANSWER_REPORT setID_TABLE_DEVICE(String ID_TABLE_DEVICE) {
        this.ID_TABLE_DEVICE = ID_TABLE_DEVICE;
        return this;
    }

    public String getCONTENT_ANSWER() {
        return CONTENT_ANSWER;
    }

    public void setCONTENT_ANSWER(String CONTENT_ANSWER) {
        this.CONTENT_ANSWER = CONTENT_ANSWER;
    }

}

