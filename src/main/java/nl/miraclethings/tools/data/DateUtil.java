package nl.miraclethings.tools.data;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by arjan on 13-4-16.
 */
public class DateUtil {


    public static Calendar dateToCalendar(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance;
    }

    public static Date getBaseDate(Date date, int dateType) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        if (dateType == Calendar.DATE) {
            instance.add(Calendar.HOUR_OF_DAY, -6);
            instance.set(Calendar.HOUR_OF_DAY, 0);
        }
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        return instance.getTime();
    }
}
