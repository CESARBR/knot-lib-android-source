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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.org.cesar.knot.lib.model.KnotQueryDateData;

public class DateUtils {

    private static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static int FIX_MONTH_ANDROID_ERROR = 1;

    /**
     * Convert  knotQueryDateData in a compatible date
     * @param knotQueryDateData the query
     * @return compatible date to do the query
     */
    public static String getTimeStamp(KnotQueryDateData knotQueryDateData){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, knotQueryDateData.getYear());
        calendar.set(Calendar.MONTH, knotQueryDateData.getMonth()- FIX_MONTH_ANDROID_ERROR);
        calendar.set(Calendar.DAY_OF_MONTH, knotQueryDateData.getDay());
        calendar.set(Calendar.HOUR, knotQueryDateData.getHour());
        calendar.set(Calendar.MINUTE, knotQueryDateData.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date currenTimeZone = (Date) calendar.getTime();
        String time = sdf.format(currenTimeZone);

        return time;
    }
}
