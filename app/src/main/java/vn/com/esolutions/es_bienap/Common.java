package vn.com.esolutions.es_bienap;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by VINH-PC on 2/2/2018.
 */

public class Common {
    public static final long TIME_DELAY_CLICK = 250;
    public static final long TIME_DELAY_CLICK_MORE_SHORT = 25;
    public static final long TIME_DELAY_CLICK_SHORT = 50;
    public static final long TIME_DELAY_CLICK_LONG = 1000;

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

    public static <T> List<T> cloneList(List<T> listcloneList) {
        ArrayList<T> newArrayList = (ArrayList<T>) ((ArrayList<T>) listcloneList).clone();

        List<T> cloneListResult = new ArrayList<>();
        cloneListResult.addAll(newArrayList);

        return newArrayList;
    }

    public enum STATUS {
        EDIT("Đang chỉnh sửa"),
        PUBLISH("Đã được phát hành"),
        DELETE("Tạm thời đang xóa");

        public String content;

        STATUS(String content) {
            this.content = content;
        }

        public static STATUS findSTATUS(String content) {
            for (STATUS status : values()) {
                if (status.content.equalsIgnoreCase(content))
                    return status;
            }
            return null;
        }
    }

    public enum TYPE_ELEMENT_REPORT {
        CHECKBOX("Ô lựa chọn"),
        TEXT("Ký tự"),
        IMAGE("Chụp ảnh"),
        UNIT("Đơn vị");

        public String content;

        TYPE_ELEMENT_REPORT(String content) {
            this.content = content;
        }

        public static TYPE_ELEMENT_REPORT findTYPE_ELEMENT_REPORT(String content) {
            for (TYPE_ELEMENT_REPORT typeElementReport : values()) {
                if (typeElementReport.content.equalsIgnoreCase(content))
                    return typeElementReport;
            }
            return null;
        }
    }


    //region Date time
    public enum DATE_TIME_TYPE {
        type1("HHmmss"),
        type2("yyyyMMdd"),
        type3("yyyyMMddHHmmss"),
        type4("yyyy-MM-dd'T'hh:mm:ssZ"),
        type5("MM/yyyy"),
        type6("dd/MM/yyyy"),
        type61("dd-MM-yyyy"),
        type7("dd/MM/yyyy HH:mm:ss"),
        type8("dd/MM/yyyy HH:mm"),
        //UI
        type9("dd/MM/yyyy HH'h'mm"),
        type10("MM/dd/yyyy HH:mm:ss a"),
        type11("yyyy-MM-dd HH:mm:ss"),
        type12("yyyyMMddHHmm"),
        //2017-11-23T22:18
        sqlite1("yyyy-MM-dd'T'HH:mm"),
        sqlite2("yyyy-MM-dd'T'HH:mm:ss"),

        typeEx("typeEx");

        public String content;

        DATE_TIME_TYPE(String content) {
            this.content = content;
        }

        public static DATE_TIME_TYPE findDATE_TIME_TYPE(String content) {
            for (DATE_TIME_TYPE dateTimeType : values()) {
                if (dateTimeType.content.equalsIgnoreCase(content))
                    return dateTimeType;
            }
            return null;
        }
    }

    public static String getDateTimeNow(DATE_TIME_TYPE formatDate) {
        SimpleDateFormat df = new SimpleDateFormat(formatDate.content);
        return df.format(Calendar.getInstance().getTime());
    }

    public static long convertDateToLong(String date, DATE_TIME_TYPE typeDefault) {
        SimpleDateFormat formatter = new SimpleDateFormat(typeDefault.content);
//        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date dateParse;
        try {
            dateParse = (Date) formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

        return dateParse.getTime();
    }

    public static String convertLongToDate(long time, DATE_TIME_TYPE format) {
        if (time < 0)
            return null;
        if (format == null)
            return null;

        SimpleDateFormat df2 = new SimpleDateFormat(format.content);
//        df2.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date(time);
        return df2.format(date);
    }

    public static String convertDateToDate(String time, DATE_TIME_TYPE typeDefault, DATE_TIME_TYPE typeConvert) {
        if (time == null || time.trim().isEmpty())
            return "";
        long longDate = Common.convertDateToLong(time, typeDefault);
        String dateByDefaultType = Common.convertLongToDate(longDate, typeConvert);
        return dateByDefaultType;
    }
    //endregion

    public static void runAnimationClickView(final View view, int idAnimation, long timeDelayAnim) {
        if (view == null)
            return;
        if (idAnimation <= 0)
            return;

        Animation animation = AnimationUtils.loadAnimation(view.getContext(), idAnimation);
        if (timeDelayAnim > 0)
            animation.setDuration(timeDelayAnim);

        view.startAnimation(animation);
    }

}
