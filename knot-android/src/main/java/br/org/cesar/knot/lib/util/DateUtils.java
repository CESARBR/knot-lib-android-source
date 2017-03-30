/*
 * Copyright (c) 2017, CESAR.
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *
 *
 */

package br.org.cesar.knot.lib.util;

import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import br.org.cesar.knot.lib.model.KnotQueryDateData;

public class DateUtils {

    private static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static int FIX_MONTH_ANDROID_ERROR = 1;

    /**
     * Convert  knotQueryDateData in a compatible date
     *
     * @param knotQueryDateData the query
     * @return compatible date to do the query
     */
    public static String getTimeStamp(KnotQueryDateData knotQueryDateData) {
        Calendar calendar = GregorianCalendar.getInstance();


        calendar.set(Calendar.YEAR, knotQueryDateData.getYear());
        calendar.set(Calendar.MONTH, knotQueryDateData.getMonth() - FIX_MONTH_ANDROID_ERROR);
        calendar.set(Calendar.DAY_OF_MONTH, knotQueryDateData.getDay());
        calendar.set(Calendar.HOUR_OF_DAY, knotQueryDateData.getHour());
        calendar.set(Calendar.MINUTE, knotQueryDateData.getMinute());
        calendar.set(Calendar.SECOND, knotQueryDateData.getSecond());
        calendar.set(Calendar.MILLISECOND, knotQueryDateData.getMillisecond());

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date currenTimeZone = (Date) calendar.getTime();
        String time = sdf.format(currenTimeZone);

        return time;
    }

    /**
     * Convert string in KnotQueryDateData object
     *
     * @param timeStamp the time in String
     * @return KnotQueryDateData object
     * @throws ParseException
     */
    public static KnotQueryDateData getKnotQueryDateData(String timeStamp) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        Date date = sdf.parse(timeStamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        KnotQueryDateData knotQueryDateData = new KnotQueryDateData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + FIX_MONTH_ANDROID_ERROR, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));

        return knotQueryDateData;
    }

    /**
     * Get the current time of system and converts string in KnotQueryDateData object
     *
     * @return KnotQueryDateData object
     * @throws ParseException
     */
    public static KnotQueryDateData getCurrentKnotQueryDateData() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        String currentDateTime = sdf.format(new Date());

        Date date = sdf.parse(currentDateTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        KnotQueryDateData knotQueryDateData = new KnotQueryDateData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + FIX_MONTH_ANDROID_ERROR, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));

        return knotQueryDateData;
    }
}
