// Generated from EPA SWMM 5.2.4 (datetime.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_datetime {
    public static CPtr[] MonthTxt = new CPtr[] { L695, L696, L697, L698, L699, L700, L701, L702, L703, L704, L705, L706, null };
    public static int[][] DaysPerMonth = new int[][] { new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }, new int[] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
    public static int DateDelta = 693594;
    public static double SecsPerDay = 86400.0d;
    public static int DateFormat = 0;

    // datetime.c:51
    static void divMod(int n, int d, IRef result, IRef remainder) {
        if ((d == 0)) {
            result.set(0);
            remainder.set(0);
        } else {
            result.set(R.idiv(n, d));
            remainder.set((n - (d * (result.get()))));
        }
    }

    // datetime.c:74
    static int isLeapYear(int year) {
        if (((((year % 4) == 0)) && (((((year % 100) != 0)) || (((year % 400) == 0)))))) {
            return 1;
        } else {
            return 0;
        }
        throw new IllegalStateException('isLeapYear: no return');
    }

    // datetime.c:89
    static int datetime_findMonth(CPtr month) {
        int i = 0;
        L_f1: for (i = 0; (i < 12); i++) {
            if ((((((((((month.g(0)) >= 97) && ((month.g(0)) <= 122))) ? (((month.g(0)) & (~32))) : (month.g(0)))) == MonthTxt[i].g(0)) && (((((((month.g(1)) >= 97) && ((month.g(1)) <= 122))) ? (((month.g(1)) & (~32))) : (month.g(1)))) == MonthTxt[i].g(1))) && (((((((month.g(2)) >= 97) && ((month.g(2)) <= 122))) ? (((month.g(2)) & (~32))) : (month.g(2)))) == MonthTxt[i].g(2)))) {
                return (i + 1);
            }
        }
        return 0;
    }

    // datetime.c:108
    static double datetime_encodeDate(int year, int month, int day) {
        int i = 0;
        int j = 0;
        i = isLeapYear(year);
        if ((((((((year >= 1)) && ((year <= 9999))) && ((month >= 1))) && ((month <= 12))) && ((day >= 1))) && ((day <= DaysPerMonth[i][(month - 1)])))) {
            L_f1: for (j = 0; (j < (month - 1)); j++) {
                day += DaysPerMonth[i][j];
            }
            i = (year - 1);
            i = ((((((i * 365) + R.idiv(i, 4)) - R.idiv(i, 100)) + R.idiv(i, 400)) + day) - DateDelta);
            return i;
        } else {
            return (-DateDelta);
        }
        throw new IllegalStateException('datetime_encodeDate: no return');
    }

    // datetime.c:136
    static double datetime_encodeTime(int hour, int minute, int second) {
        int s = 0;
        if (((((hour >= 0)) && ((minute >= 0))) && ((second >= 0)))) {
            s = ((((hour * 3600) + (minute * 60)) + second));
            return (((double) (s)) / SecsPerDay);
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('datetime_encodeTime: no return');
    }

    // datetime.c:158
    static void datetime_decodeDate(double date, IRef year, IRef month, IRef day) {
        int D1 = 0;
        int D4 = 0;
        int D100 = 0;
        int D400 = 0;
        int y = 0;
        int m = 0;
        IBox d = new IBox(0);
        IBox i = new IBox(0);
        int k = 0;
        int t = 0;
        D1 = 365;
        D4 = ((D1 * 4) + 1);
        D100 = ((D4 * 25) - 1);
        D400 = ((D100 * 4) + 1);
        t = (R.D2I((Math.floor(date))) + DateDelta);
        if ((t <= 0)) {
            year.set(0);
            month.set(1);
            day.set(1);
        } else {
            t--;
            y = 1;
            L_w1: while ((t >= D400)) {
                t -= D400;
                y += 400;
            }
            divMod(t, D100, i, d);
            if ((i.v == 4)) {
                i.@v = i.v - 1;
                d.@v = (d.v + D100);
            }
            y += (i.v * 100);
            divMod(d.v, D4, i, d);
            y += (i.v * 4);
            divMod(d.v, D1, i, d);
            if ((i.v == 4)) {
                i.@v = i.v - 1;
                d.@v = (d.v + D1);
            }
            y += i.v;
            k = isLeapYear(y);
            m = 1;
            L_f2: for (; ; ) {
                i.@v = DaysPerMonth[k][(m - 1)];
                if ((d.v < i.v)) {
                    break L_f2;
                }
                d.@v = (d.v - i.v);
                m++;
            }
            year.set(y);
            month.set(m);
            day.set((d.v + 1));
        }
    }

    // datetime.c:224
    static void datetime_decodeTime(double time, IRef h, IRef m, IRef s) {
        int secs = 0;
        IBox mins = new IBox(0);
        double fracDay = (((time - Math.floor(time))) * SecsPerDay);
        secs = R.D2I((Math.floor((fracDay + 0.5d))));
        if ((secs >= 86400)) {
            secs = 86399;
        }
        divMod(secs, 60, mins, s);
        divMod(mins.v, 60, h, m);
        if ((h.get() > 23)) {
            h.set(0);
        }
    }

    // datetime.c:245
    static void datetime_dateToStr(double date, CPtr s) {
        IBox y = new IBox(0);
        IBox m = new IBox(0);
        IBox d = new IBox(0);
        datetime_decodeDate(date, y, m, d);
        int __sw1 = DateFormat;
        if (__sw1 == 0) {
            R.snprintf(s, (int) (12), L36, y.v, MonthTxt[(m.v - 1)], d.v);
        } else if (__sw1 == 1) {
            R.snprintf(s, (int) (12), L37, m.v, d.v, y.v);
        } else {
            R.snprintf(s, (int) (12), L38, d.v, MonthTxt[(m.v - 1)], y.v);
        }
    }

    // datetime.c:270
    static void datetime_timeToStr(double time, CPtr s) {
        IBox hr = new IBox(0);
        IBox min = new IBox(0);
        IBox sec = new IBox(0);
        datetime_decodeTime(time, hr, min, sec);
        R.snprintf(s, (int) (9), L39, hr.v, min.v, sec.v);
    }

    // datetime.c:284
    static int datetime_strToDate(CPtr s, DRef d) {
        IBox yr = new IBox(0);
        IBox mon = new IBox(0);
        IBox day = new IBox(0);
        int n = 0;
        CPtr month = R.buf(4);
        IBox sep1 = new IBox(0);
        IBox sep2 = new IBox(0);
        d.set((-DateDelta));
        if (((R.strchr(s, 45) != null) || (R.strchr(s, 47) != null))) {
            int __sw1 = DateFormat;
            if (__sw1 == 0) {
                n = R.sscanf(s, L40, yr, sep1, mon, sep2, day);
                if ((n < 3)) {
                    mon.@v = 0;
                    n = R.sscanf(s, L41, yr, sep1, month, sep2, day);
                    if ((n < 3)) {
                        return 0;
                    }
                }
            } else if (__sw1 == 2) {
                n = R.sscanf(s, L40, day, sep1, mon, sep2, yr);
                if ((n < 3)) {
                    mon.@v = 0;
                    n = R.sscanf(s, L41, day, sep1, month, sep2, yr);
                    if ((n < 3)) {
                        return 0;
                    }
                }
            } else {
                n = R.sscanf(s, L40, mon, sep1, day, sep2, yr);
                if ((n < 3)) {
                    mon.@v = 0;
                    n = R.sscanf(s, L42, month, sep1, day, sep2, yr);
                    if ((n < 3)) {
                        return 0;
                    }
                }
            }
            if ((mon.v == 0)) {
                mon.@v = datetime_findMonth(month);
            }
            d.set(datetime_encodeDate(yr.v, mon.v, day.v));
        }
        if ((d.get() == (-DateDelta))) {
            return 0;
        } else {
            return 1;
        }
        throw new IllegalStateException('datetime_strToDate: no return');
    }

    // datetime.c:338
    static int datetime_strToTime(CPtr s, DRef t) {
        int n = 0;
        IBox hr = new IBox(0);
        IBox min = new IBox(0);
        IBox sec = new IBox(0);
        OBox<CPtr> endptr = new OBox<>(null);
        t.set(R.strtod(s, endptr));
        if ((endptr.v.g(0) == 0)) {
            t.set((t.get() / 24.0d));
            return 1;
        }
        t.set(0.0d);
        n = R.sscanf(s, L43, hr, min, sec);
        if ((n == 0)) {
            return 0;
        }
        t.set(datetime_encodeTime(hr.v, min.v, sec.v));
        if (((((hr.v >= 0)) && ((min.v >= 0))) && ((sec.v >= 0)))) {
            return 1;
        } else {
            return 0;
        }
        throw new IllegalStateException('datetime_strToTime: no return');
    }

    // datetime.c:369
    static void datetime_setDateFormat(int fmt) {
        if (((fmt >= 0) && (fmt <= 1))) {
            DateFormat = fmt;
        }
    }

    // datetime.c:381
    static double datetime_addSeconds(double date1, double seconds) {
        double d = Math.floor(date1);
        IBox h = new IBox(0);
        IBox m = new IBox(0);
        IBox s = new IBox(0);
        datetime_decodeTime(date1, h, m, s);
        return (d + ((((((3600.0d * h.v) + (60.0d * m.v)) + s.v) + seconds)) / SecsPerDay));
    }

    // datetime.c:397
    static double datetime_addDays(double date1, double date2) {
        double d1 = Math.floor(date1);
        double d2 = Math.floor(date2);
        IBox h1 = new IBox(0);
        IBox m1 = new IBox(0);
        IBox s1 = new IBox(0);
        IBox h2 = new IBox(0);
        IBox m2 = new IBox(0);
        IBox s2 = new IBox(0);
        datetime_decodeTime(date1, h1, m1, s1);
        datetime_decodeTime(date2, h2, m2, s2);
        return ((d1 + d2) + datetime_encodeTime((h1.v + h2.v), (m1.v + m2.v), (s1.v + s2.v)));
    }

    // datetime.c:416
    static long datetime_timeDiff(double date1, double date2) {
        double d1 = Math.floor(date1);
        double d2 = Math.floor(date2);
        IBox h = new IBox(0);
        IBox m = new IBox(0);
        IBox s = new IBox(0);
        long s1 = 0L;
        long s2 = 0L;
        long secs = 0L;
        datetime_decodeTime(date1, h, m, s);
        s1 = (((3600 * h.v) + (60 * m.v)) + s.v);
        datetime_decodeTime(date2, h, m, s);
        s2 = (((3600 * h.v) + (60 * m.v)) + s.v);
        secs = R.D2I((Math.floor(((((d1 - d2)) * SecsPerDay) + 0.5d))));
        secs += ((s1 - s2));
        return secs;
    }

    // datetime.c:439
    static int datetime_monthOfYear(double date) {
        IBox year = new IBox(0);
        IBox month = new IBox(0);
        IBox day = new IBox(0);
        datetime_decodeDate(date, year, month, day);
        return month.v;
    }

    // datetime.c:453
    static int datetime_dayOfYear(double date) {
        IBox year = new IBox(0);
        IBox month = new IBox(0);
        IBox day = new IBox(0);
        double startOfYear = 0.0d;
        datetime_decodeDate(date, year, month, day);
        startOfYear = datetime_encodeDate(year.v, 1, 1);
        return (R.D2I((Math.floor((date - startOfYear)))) + 1);
    }

    // datetime.c:469
    static int datetime_dayOfWeek(double date) {
        int t = (R.D2I((Math.floor(date))) + DateDelta);
        return (((t % 7)) + 1);
    }

    // datetime.c:482
    static int datetime_hourOfDay(double date) {
        IBox hour = new IBox(0);
        IBox min = new IBox(0);
        IBox sec = new IBox(0);
        datetime_decodeTime(date, hour, min, sec);
        return hour.v;
    }

    // datetime.c:496
    static int datetime_daysPerMonth(int year, int month) {
        if (((month < 1) || (month > 12))) {
            return 0;
        }
        return (DaysPerMonth[isLeapYear(year)])[(month - 1)];
    }

    // datetime.c:510
    static void datetime_getTimeStamp(int fmt, double aDate, int stampSize, CPtr timeStamp) {
        CPtr dateStr = R.buf(12);
        CPtr timeStr = R.buf(9);
        int oldDateFormat = DateFormat;
        if ((stampSize < 21)) {
            return;
        }
        datetime_setDateFormat(fmt);
        datetime_dateToStr(aDate, dateStr);
        DateFormat = oldDateFormat;
        datetime_timeToStr(aDate, timeStr);
        R.snprintf(timeStamp, (int) (stampSize), L44, dateStr, timeStr);
    }

}
