// Generated from EPA SWMM 5.2.4 (gage.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_gage {
    public static double OneSecond = 1.1574074e-5d;

    // gage.c:58
    static int gage_readParams(int j, CPtr[] tok, int ntoks) {
        int k = 0;
        int err = 0;
        CPtr id = null;
        CPtr fname = R.buf(260);
        CPtr staID = R.buf(1025);
        double[] x = new double[7];
        if ((ntoks < 2)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(GAGE, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        x[0] = (-1.0d);
        x[1] = 1.0d;
        x[2] = 3600.0d;
        x[3] = 1.0d;
        x[4] = (-693594);
        x[5] = (-693594);
        x[6] = 0.0d;
        fname.s(0, 0);
        staID.s(0, 0);
        if ((ntoks < 5)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        k = F_input.findmatch(tok[4], F_keywords.GageDataWords);
        if ((k == RAIN_TSERIES)) {
            err = readGageSeriesFormat(tok, ntoks, x);
        } else if ((k == RAIN_FILE)) {
            if ((ntoks < 8)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            Overrides.sstrncpy(fname, tok[5], 259);
            Overrides.sstrncpy(staID, tok[6], 1024);
            err = readGageFileFormat(tok, ntoks, x);
        } else {
            return F_error.error_setInpError(ERR_KEYWORD, tok[4]);
        }
        if ((err > 0)) {
            return err;
        }
        F_swmm5.Gage[j].@ID = id;
        F_swmm5.Gage[j].@tSeries = R.D2I(x[0]);
        F_swmm5.Gage[j].@rainType = R.D2I(x[1]);
        F_swmm5.Gage[j].@rainInterval = R.D2I(x[2]);
        F_swmm5.Gage[j].@snowFactor = x[3];
        F_swmm5.Gage[j].@rainUnits = R.D2I(x[6]);
        if ((F_swmm5.Gage[j].tSeries >= 0)) {
            F_swmm5.Gage[j].@dataSource = RAIN_TSERIES;
        } else {
            F_swmm5.Gage[j].@dataSource = RAIN_FILE;
        }
        if ((F_swmm5.Gage[j].dataSource == RAIN_FILE)) {
            Overrides.sstrncpy(F_swmm5.Gage[j].fname, F_swmm5.addAbsolutePath(fname), 259);
            Overrides.sstrncpy(F_swmm5.Gage[j].staID, staID, 1024);
            F_swmm5.Gage[j].@startFileDate = x[4];
            F_swmm5.Gage[j].@endFileDate = x[5];
        }
        F_swmm5.Gage[j].@unitsFactor = 1.0d;
        F_swmm5.Gage[j].@coGage = (-1);
        F_swmm5.Gage[j].@isUsed = 0;
        return 0;
    }

    // gage.c:133
    static int readGageSeriesFormat(CPtr[] tok, int ntoks, double[] x) {
        int m = 0;
        int ts = 0;
        DBox aTime = new DBox(0.0d);
        if ((ntoks < 6)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        m = F_input.findmatch(tok[1], F_keywords.RainTypeWords);
        if ((m < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
        }
        x[1] = ((double) (m));
        if ((F_input.getDouble(tok[2], new DElem(x, 2)) != 0)) {
            x[2] = Math.floor(((x[2] * 3600) + 0.5d));
        } else if ((F_datetime.datetime_strToTime(tok[2], aTime) != 0)) {
            x[2] = Math.floor(((aTime.v * 86400) + 0.5d));
        } else {
            return F_error.error_setInpError(ERR_DATETIME, tok[2]);
        }
        if ((x[2] <= 0.0d)) {
            return F_error.error_setInpError(ERR_DATETIME, tok[2]);
        }
        if (!(F_input.getDouble(tok[3], new DElem(x, 3)) != 0)) {
            return F_error.error_setInpError(ERR_DATETIME, tok[3]);
        }
        ts = F_project.project_findObject(TSERIES, tok[5]);
        if ((ts < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[5]);
        }
        x[0] = ((double) (ts));
        Overrides.sstrncpy(tok[2], L0, 0);
        return 0;
    }

    // gage.c:168
    static int readGageFileFormat(CPtr[] tok, int ntoks, double[] x) {
        int m = 0;
        int u = 0;
        DBox aDate = new DBox(0.0d);
        DBox aTime = new DBox(0.0d);
        m = F_input.findmatch(tok[1], F_keywords.RainTypeWords);
        if ((m < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
        }
        x[1] = ((double) (m));
        if ((F_input.getDouble(tok[2], new DElem(x, 2)) != 0)) {
            x[2] *= 3600;
        } else if ((F_datetime.datetime_strToTime(tok[2], aTime) != 0)) {
            x[2] = Math.floor(((aTime.v * 86400) + 0.5d));
        } else {
            return F_error.error_setInpError(ERR_DATETIME, tok[2]);
        }
        if ((x[2] <= 0.0d)) {
            return F_error.error_setInpError(ERR_DATETIME, tok[2]);
        }
        if (!(F_input.getDouble(tok[3], new DElem(x, 3)) != 0)) {
            return F_error.error_setInpError(ERR_NUMBER, tok[3]);
        }
        u = F_input.findmatch(tok[7], F_keywords.RainUnitsWords);
        if ((u < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[7]);
        }
        x[6] = ((double) (u));
        if (((ntoks > 8) && (tok[8].g(0) != 42))) {
            if (!(F_datetime.datetime_strToDate(tok[8], aDate) != 0)) {
                return F_error.error_setInpError(ERR_DATETIME, tok[8]);
            }
            x[4] = ((float) (aDate.v));
        }
        return 0;
    }

    // gage.c:209
    static void gage_validate(int j) {
        int i = 0;
        int k = 0;
        int gageInterval = 0;
        if ((F_swmm5.Gage[j].dataSource == RAIN_TSERIES)) {
            if (!(F_swmm5.Gage[j].isUsed != 0)) {
                return;
            }
            k = F_swmm5.Gage[j].tSeries;
            L_f1: for (i = 0; (i < j); i++) {
                if ((((F_swmm5.Gage[i].dataSource == RAIN_TSERIES) && (F_swmm5.Gage[i].tSeries == k)) && (F_swmm5.Gage[i].isUsed != 0))) {
                    F_swmm5.Gage[j].@coGage = i;
                    if ((F_swmm5.Gage[j].rainType != F_swmm5.Gage[i].rainType)) {
                        F_report.report_writeErrorMsg(ERR_RAIN_GAGE_FORMAT, F_swmm5.Gage[j].ID);
                    }
                    return;
                }
            }
            if ((F_swmm5.Tseries[k].refersTo >= 0)) {
                F_report.report_writeErrorMsg(ERR_RAIN_GAGE_TSERIES, F_swmm5.Gage[j].ID);
            }
            gageInterval = R.D2I((Math.floor(((F_swmm5.Tseries[k].dxMin * 86400) + 0.5d))));
            if (((gageInterval > 0) && (F_swmm5.Gage[j].rainInterval > gageInterval))) {
                F_report.report_writeErrorMsg(ERR_RAIN_GAGE_INTERVAL, F_swmm5.Gage[j].ID);
            }
            if ((F_swmm5.Gage[j].rainInterval < gageInterval)) {
                F_report.report_writeWarningMsg(L156, F_swmm5.Gage[j].ID);
            }
            if ((F_swmm5.Gage[j].rainInterval < F_swmm5.WetStep)) {
                F_report.report_writeWarningMsg(L157, F_swmm5.Gage[j].ID);
                F_swmm5.@WetStep = F_swmm5.Gage[j].rainInterval;
            }
        }
    }

    // gage.c:270
    static void gage_initState(int j) {
        F_swmm5.Gage[j].@rainfall = 0.0d;
        F_swmm5.Gage[j].@apiRainfall = (-10000000000.0d);
        F_swmm5.Gage[j].@reportRainfall = 0.0d;
        if ((F_swmm5.IgnoreRainfall != 0)) {
            return;
        }
        if ((F_swmm5.Gage[j].dataSource == RAIN_FILE)) {
            F_swmm5.Gage[j].@currentFilePos = F_swmm5.Gage[j].startFilePos;
            if ((F_swmm5.UnitSystem == SI)) {
                F_swmm5.Gage[j].@unitsFactor = 25.4d;
            }
        }
        if ((getFirstRainfall(j) != 0)) {
            F_swmm5.Gage[j].@endDate = F_datetime.datetime_addSeconds(F_swmm5.Gage[j].startDate, F_swmm5.Gage[j].rainInterval);
            if (R.dgt(F_swmm5.Gage[j].startDate, F_swmm5.StartDateTime)) {
                F_swmm5.Gage[j].@nextDate = F_swmm5.Gage[j].startDate;
                F_swmm5.Gage[j].@nextRainfall = F_swmm5.Gage[j].rainfall;
                F_swmm5.Gage[j].@startDate = F_swmm5.StartDateTime;
                F_swmm5.Gage[j].@endDate = F_swmm5.Gage[j].nextDate;
                F_swmm5.Gage[j].@rainfall = 0.0d;
            } else if (!(getNextRainfall(j) != 0)) {
                F_swmm5.Gage[j].@nextDate = (-693594);
            }
        } else {
            F_swmm5.Gage[j].@startDate = (-693594);
        }
        initPastRain(j);
    }

    // gage.c:323
    static void gage_setState(int j, double t) {
        if ((F_swmm5.Gage[j].isUsed == 0)) {
            return;
        }
        if ((F_swmm5.IgnoreRainfall != 0)) {
            F_swmm5.Gage[j].@rainfall = 0.0d;
            return;
        }
        if ((F_swmm5.Gage[j].coGage >= 0)) {
            F_swmm5.Gage[j].@rainfall = F_swmm5.Gage[F_swmm5.Gage[j].coGage].rainfall;
            return;
        }
        if ((F_swmm5.Gage[j].apiRainfall != (-10000000000.0d))) {
            F_swmm5.Gage[j].@rainfall = F_swmm5.Gage[j].apiRainfall;
            return;
        }
        t += OneSecond;
        L_f1: for (; ; ) {
            if ((F_swmm5.Gage[j].startDate == (-693594))) {
                F_swmm5.Gage[j].@rainfall = 0.0d;
                return;
            }
            if ((t < F_swmm5.Gage[j].startDate)) {
                F_swmm5.Gage[j].@rainfall = 0.0d;
                return;
            }
            if ((t < F_swmm5.Gage[j].endDate)) {
                return;
            }
            if ((F_swmm5.Gage[j].nextDate == (-693594))) {
                F_swmm5.Gage[j].@rainfall = 0.0d;
                return;
            }
            if ((t < F_swmm5.Gage[j].nextDate)) {
                F_swmm5.Gage[j].@rainfall = 0.0d;
                return;
            }
            F_swmm5.Gage[j].@startDate = F_swmm5.Gage[j].nextDate;
            F_swmm5.Gage[j].@endDate = F_datetime.datetime_addSeconds(F_swmm5.Gage[j].startDate, F_swmm5.Gage[j].rainInterval);
            F_swmm5.Gage[j].@rainfall = F_swmm5.Gage[j].nextRainfall;
            if (!(getNextRainfall(j) != 0)) {
                F_swmm5.Gage[j].@nextDate = (-693594);
            }
        }
    }

    // gage.c:406
    static void initPastRain(int j) {
        int i = 0;
        L_f1: for (i = 0; (i <= 48); i++) {
            F_swmm5.Gage[j].pastRain[i] = 0.0d;
        }
        F_swmm5.Gage[j].@pastInterval = 0;
    }

    // gage.c:417
    static void gage_updatePastRain(int j, int tStep) {
        int i = 0;
        int t = 0;
        double r = 0.0d;
        r = (F_swmm5.Gage[j].rainfall / 3600.0d);
        L_w1: while ((tStep > 0)) {
            t = (3600 - F_swmm5.Gage[j].pastInterval);
            if ((tStep > t)) {
                F_swmm5.Gage[j].pastRain[0] += (t * r);
                L_f2: for (i = 48; (i > 0); i--) {
                    F_swmm5.Gage[j].pastRain[i] = F_swmm5.Gage[j].pastRain[(i - 1)];
                }
                F_swmm5.Gage[j].@pastInterval = 0;
                F_swmm5.Gage[j].pastRain[0] = 0.0d;
                tStep -= t;
            } else {
                F_swmm5.Gage[j].pastRain[0] += (tStep * r);
                F_swmm5.Gage[j].@pastInterval = (F_swmm5.Gage[j].pastInterval + tStep);
                tStep = 0;
            }
        }
    }

    // gage.c:468
    static double gage_getPastRain(int j, int n) {
        int i = 0;
        double result = 0.0d;
        if (((n < 1) || (n > 48))) {
            return 0.0d;
        }
        L_f1: for (i = 1; (i <= n); i++) {
            result += F_swmm5.Gage[j].pastRain[i];
        }
        return result;
    }

    // gage.c:486
    static double gage_getNextRainDate(int j, double aDate) {
        if ((F_swmm5.Gage[j].isUsed == 0)) {
            return aDate;
        }
        aDate += OneSecond;
        if ((aDate < F_swmm5.Gage[j].startDate)) {
            return F_swmm5.Gage[j].startDate;
        }
        if ((aDate < F_swmm5.Gage[j].endDate)) {
            return F_swmm5.Gage[j].endDate;
        }
        return F_swmm5.Gage[j].nextDate;
    }

    // gage.c:503
    static double gage_getPrecip(int j, DRef rainfall, DRef snowfall) {
        rainfall.set(0.0d);
        snowfall.set(0.0d);
        if (((!(F_swmm5.IgnoreSnowmelt != 0)) && (F_swmm5.Temp.ta <= F_swmm5.Snow.snotmp))) {
            snowfall.set(((F_swmm5.Gage[j].rainfall * F_swmm5.Gage[j].snowFactor) / F_swmm5.UCF(RAINFALL)));
        } else {
            rainfall.set((F_swmm5.Gage[j].rainfall / F_swmm5.UCF(RAINFALL)));
        }
        return ((rainfall.get()) + (snowfall.get()));
    }

    // gage.c:524
    static void gage_setReportRainfall(int j, double reportDate) {
        double result = 0.0d;
        if ((F_swmm5.Gage[j].coGage >= 0)) {
            F_swmm5.Gage[j].@reportRainfall = F_swmm5.Gage[F_swmm5.Gage[j].coGage].reportRainfall;
            return;
        }
        if ((F_swmm5.Gage[j].apiRainfall != (-10000000000.0d))) {
            F_swmm5.Gage[j].@reportRainfall = F_swmm5.Gage[j].apiRainfall;
            return;
        }
        reportDate += OneSecond;
        if ((reportDate < F_swmm5.Gage[j].endDate)) {
            result = F_swmm5.Gage[j].rainfall;
        } else if ((reportDate < F_swmm5.Gage[j].nextDate)) {
            result = 0.0d;
        } else {
            result = F_swmm5.Gage[j].nextRainfall;
        }
        F_swmm5.Gage[j].@reportRainfall = result;
    }

    // gage.c:567
    static int getFirstRainfall(int j) {
        int k = 0;
        FBox vFirst = new FBox(0.0f);
        DBox rFirst = new DBox(0.0d);
        F_swmm5.Gage[j].@startDate = (-693594);
        F_swmm5.Gage[j].@rainfall = 0.0d;
        F_swmm5.Gage[j].@rainAccum = 0;
        if ((F_swmm5.Gage[j].dataSource == RAIN_FILE)) {
            if (((F_swmm5.Frain.file != null) && (F_swmm5.Gage[j].endFilePos > F_swmm5.Gage[j].startFilePos))) {
                R.fseek(F_swmm5.Frain.file, F_swmm5.Gage[j].startFilePos, 0);
                R.fread1(new DField<TGage>(F_swmm5.Gage[j], (TGage __o) -> __o.startDate, (TGage __o, double __x) -> { __o.@startDate = __x }), 8, F_swmm5.Frain.file);
                R.fread1(vFirst, 4, F_swmm5.Frain.file);
                F_swmm5.Gage[j].@currentFilePos = R.ftell(F_swmm5.Frain.file);
                F_swmm5.Gage[j].@rainfall = convertRainfall(j, vFirst.v);
                return 1;
            }
            return 0;
        } else {
            k = F_swmm5.Gage[j].tSeries;
            if ((k >= 0)) {
                if ((F_table.table_getFirstEntry(F_swmm5.Tseries[k], new DField<TGage>(F_swmm5.Gage[j], (TGage __o) -> __o.startDate, (TGage __o, double __x) -> { __o.@startDate = __x }), rFirst) != 0)) {
                    F_swmm5.Gage[j].@rainfall = convertRainfall(j, rFirst.v);
                    return 1;
                }
            }
            return 0;
        }
        throw new IllegalStateException('getFirstRainfall: no return');
    }

    // gage.c:624
    static int getNextRainfall(int j) {
        int k = 0;
        FBox vNext = new FBox(0.0f);
        DBox rNext = new DBox(0.0d);
        F_swmm5.Gage[j].@nextRainfall = 0.0d;
        L_d1: do {
            if ((F_swmm5.Gage[j].dataSource == RAIN_FILE)) {
                if (((F_swmm5.Frain.file != null) && (F_swmm5.Gage[j].currentFilePos < F_swmm5.Gage[j].endFilePos))) {
                    R.fseek(F_swmm5.Frain.file, F_swmm5.Gage[j].currentFilePos, 0);
                    R.fread1(new DField<TGage>(F_swmm5.Gage[j], (TGage __o) -> __o.nextDate, (TGage __o, double __x) -> { __o.@nextDate = __x }), 8, F_swmm5.Frain.file);
                    R.fread1(vNext, 4, F_swmm5.Frain.file);
                    F_swmm5.Gage[j].@currentFilePos = R.ftell(F_swmm5.Frain.file);
                    rNext.@v = convertRainfall(j, vNext.v);
                } else {
                    return 0;
                }
            } else {
                k = F_swmm5.Gage[j].tSeries;
                if ((k >= 0)) {
                    if (!(F_table.table_getNextEntry(F_swmm5.Tseries[k], new DField<TGage>(F_swmm5.Gage[j], (TGage __o) -> __o.nextDate, (TGage __o, double __x) -> { __o.@nextDate = __x }), rNext) != 0)) {
                        return 0;
                    }
                    rNext.@v = convertRainfall(j, rNext.v);
                } else {
                    return 0;
                }
            }
        } while ((rNext.v == 0.0d));
        F_swmm5.Gage[j].@nextRainfall = rNext.v;
        return 1;
    }

    // gage.c:674
    static double convertRainfall(int j, double r) {
        double r1 = 0.0d;
        int __sw1 = F_swmm5.Gage[j].rainType;
        if (__sw1 == RAINFALL_INTENSITY) {
            r1 = r;
        } else if (__sw1 == RAINFALL_VOLUME) {
            r1 = ((r / F_swmm5.Gage[j].rainInterval) * 3600.0d);
        } else if (__sw1 == CUMULATIVE_RAINFALL) {
            if ((r < F_swmm5.Gage[j].rainAccum)) {
                r1 = ((r / F_swmm5.Gage[j].rainInterval) * 3600.0d);
            } else {
                r1 = ((((r - F_swmm5.Gage[j].rainAccum)) / F_swmm5.Gage[j].rainInterval) * 3600.0d);
            }
            F_swmm5.Gage[j].@rainAccum = r;
        } else {
            r1 = r;
        }
        return ((r1 * F_swmm5.Gage[j].unitsFactor) * F_swmm5.Adjust.rainFactor);
    }

}
