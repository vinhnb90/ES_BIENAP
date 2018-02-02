package vn.com.esolutions.es_bienap;

/**
 * Created by VINH-PC on 2/2/2018.
 */

public class Common {
    public static String BUNDLE_MODE = "BUNDLE_MODE";

    public enum MODE{
        ADMIN("Quản lý"),
        EMP("Nhân viên");

        String content;

        MODE(String content) {
            this.content = content;
        }


        public static MODE findMODEbyContent(String content) {
            for (MODE mode : values()) {
                if (mode.content.equalsIgnoreCase(content))
                    return mode;
            }
            return null;
        }
    }
}
