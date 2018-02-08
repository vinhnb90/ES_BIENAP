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

@Table(name = "TABLE_DEVICE")
public class TABLE_DEVICE implements Cloneable{
    @PrimaryKey
    @AutoIncrement
    @Collumn(name = "ID_TABLE_DEVICE", type = TYPE.INTEGER, other = "NOT NULL")
    private int ID_TABLE_DEVICE;

    @Collumn(name = "DEVICE_NAME", type = TYPE.TEXT, other = "NOT NULL")
    private String DEVICE_NAME;

    @Collumn(name = "DEVICE_CODE", type = TYPE.TEXT, other = "NOT NULL")
    private String DEVICE_CODE;

    @Collumn(name = "DEVICE_ADDRESS", type = TYPE.TEXT)
    private String DEVICE_ADDRESS;

    @Collumn(name = "DEVICE_DATE", type = TYPE.TEXT)
    private String DEVICE_DATE;

    @Collumn(name = "DEVICE_STATUS", type = TYPE.TEXT)
    private String DEVICE_STATUS;

    @Collumn(name = "MODE", type = TYPE.TEXT)
    private String MODE;

    @EnumNameCollumn()
    public enum table {
        ID_TABLE_DEVICE,
        DEVICE_NAME,
        DEVICE_CODE,
        DEVICE_ADDRESS,
        DEVICE_DATE,
        DEVICE_STATUS,
        MODE;

        public static String getName() {
            return "TABLE_DEVICE";
        }
    }

    public TABLE_DEVICE() {
    }

    public TABLE_DEVICE(@Params(name = "ID_TABLE_DEVICE") int ID_TABLE_DEVICE,
                        @Params(name = "DEVICE_NAME") String DEVICE_NAME,
                        @Params(name = "DEVICE_CODE") String DEVICE_CODE,
                        @Params(name = "DEVICE_ADDRESS") String DEVICE_ADDRESS,
                        @Params(name = "DEVICE_DATE") String DEVICE_DATE,
                        @Params(name = "DEVICE_STATUS") String DEVICE_STATUS,
                        @Params(name = "MODE") String MODE
    ) {
        this.ID_TABLE_DEVICE = ID_TABLE_DEVICE;
        this.DEVICE_NAME = DEVICE_NAME;
        this.DEVICE_CODE = DEVICE_CODE;
        this.DEVICE_ADDRESS = DEVICE_ADDRESS;
        this.DEVICE_DATE = DEVICE_DATE;
        this.DEVICE_STATUS = DEVICE_STATUS;
        this.MODE = MODE;
    }

    public int getID_TABLE_DEVICE() {
        return ID_TABLE_DEVICE;
    }

    public void setID_TABLE_DEVICE(int ID_TABLE_DEVICE) {
        this.ID_TABLE_DEVICE = ID_TABLE_DEVICE;
    }

    public String getDEVICE_NAME() {
        return DEVICE_NAME;
    }

    public void setDEVICE_NAME(String DEVICE_NAME) {
        this.DEVICE_NAME = DEVICE_NAME;
    }

    public String getDEVICE_CODE() {
        return DEVICE_CODE;
    }

    public void setDEVICE_CODE(String DEVICE_CODE) {
        this.DEVICE_CODE = DEVICE_CODE;
    }

    public String getDEVICE_ADDRESS() {
        return DEVICE_ADDRESS;
    }

    public void setDEVICE_ADDRESS(String DEVICE_ADDRESS) {
        this.DEVICE_ADDRESS = DEVICE_ADDRESS;
    }

    public String getDEVICE_DATE() {
        return DEVICE_DATE;
    }

    public void setDEVICE_DATE(String DEVICE_DATE) {
        this.DEVICE_DATE = DEVICE_DATE;
    }

    public String getDEVICE_STATUS() {
        return DEVICE_STATUS;
    }

    public void setDEVICE_STATUS(String DEVICE_STATUS) {
        this.DEVICE_STATUS = DEVICE_STATUS;
    }

    public String getMODE() {
        return MODE;
    }

    public void setMODE(String MODE) {
        this.MODE = MODE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

