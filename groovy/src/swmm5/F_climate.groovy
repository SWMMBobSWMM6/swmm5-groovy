// Generated from EPA SWMM 5.2.4 (climate.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_climate {
    public static int MAXCLIMATEVARS = 4;
    public static int MAXDAYSPERMONTH = 32;
    public static CPtr[] ClimateVarWords = new CPtr[] { L22, L23, L7, L24, L25, null };
    public static CPtr[] TempUnitsWords = new CPtr[] { L653, L654, L655, null };
    public static double Tmin = 0.0d;
    public static double Tmax = 0.0d;
    public static double Trng = 0.0d;
    public static double Trng1 = 0.0d;
    public static double Tave = 0.0d;
    public static double Hrsr = 0.0d;
    public static double Hrss = 0.0d;
    public static double Hrday = 0.0d;
    public static double Dhrdy = 0.0d;
    public static double Dydif = 0.0d;
    public static double LastDay = 0.0d;
    public static TMovAve Tma = new TMovAve();
    public static DBox NextEvapDate = new DBox(0.0d);
    public static DBox NextEvapRate = new DBox(0.0d);
    public static int FileFormat = 0;
    public static IBox FileYear = new IBox(0);
    public static IBox FileMonth = new IBox(0);
    public static IBox FileDay = new IBox(0);
    public static int FileLastDay = 0;
    public static int FileElapsedDays = 0;
    public static double[] FileValue = new double[4];
    public static double[][] FileData = new double[4][32];
    public static CPtr FileLine = R.buf(1025);
    public static int[] FileFieldPos = new int[4];
    public static int FileDateFieldPos = 0;
    public static int FileWindType = 0;
    public static int FileTempUnits = 0;

    // climate.c:153
    static int climate_readParams(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int k = 0;
        double[] x = new double[6];
        DBox y = new DBox(0.0d);
        DBox aDate = new DBox(0.0d);
        CPtr fname = R.buf(260);
        k = F_input.findmatch(tok[0], F_keywords.TempKeyWords);
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[0]);
        }
        int __sw1 = k;
        if (__sw1 == 0) {
            if ((ntoks < 2)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            i = F_project.project_findObject(TSERIES, tok[1]);
            if ((i < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[1]);
            }
            F_swmm5.Temp.@dataSource = TSERIES_TEMP;
            F_swmm5.Temp.@tSeries = i;
            F_swmm5.Tseries[i].@refersTo = TSERIES_TEMP;
        } else if (__sw1 == 1) {
            if ((ntoks < 2)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            F_swmm5.Temp.@dataSource = FILE_TEMP;
            F_swmm5.Fclimate.@mode = USE_FILE;
            Overrides.sstrncpy(fname, tok[1], 259);
            Overrides.sstrncpy(F_swmm5.Fclimate.name, F_swmm5.addAbsolutePath(fname), 259);
            F_swmm5.Temp.@fileStartDate = (-693594);
            if ((ntoks > 2)) {
                if ((tok[2].g(0) != 42)) {
                    if (!(F_datetime.datetime_strToDate(tok[2], aDate) != 0)) {
                        return F_error.error_setInpError(ERR_DATETIME, tok[2]);
                    }
                    F_swmm5.Temp.@fileStartDate = aDate.v;
                }
            }
            FileTempUnits = DEG_F;
            if ((F_swmm5.UnitSystem == SI)) {
                FileTempUnits = DEG_C;
            }
            if ((ntoks > 3)) {
                i = F_input.findmatch(tok[3], TempUnitsWords);
                if ((i < 0)) {
                    return F_error.error_setInpError(ERR_KEYWORD, tok[3]);
                }
                FileTempUnits = i;
            }
        } else if (__sw1 == 2) {
            if ((F_swmm5.strcomp(tok[1], L1) != 0)) {
                F_swmm5.Wind.@type = FILE_WIND;
            } else {
                if ((ntoks < 14)) {
                    return F_error.error_setInpError(ERR_ITEMS, L0);
                }
                F_swmm5.Wind.@type = MONTHLY_WIND;
                L_f2: for (i = 0; (i < 12); i++) {
                    if (!(F_input.getDouble(tok[(i + 2)], y) != 0)) {
                        return F_error.error_setInpError(ERR_NUMBER, tok[(i + 2)]);
                    }
                    F_swmm5.Wind.aws[i] = y.v;
                }
            }
        } else if (__sw1 == 3) {
            if ((ntoks < 7)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            L_f3: for (i = 1; (i < 7); i++) {
                if (!(F_input.getDouble(tok[i], new DElem(x, (i - 1))) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[i]);
                }
            }
            if ((F_swmm5.UnitSystem == SI)) {
                x[0] = (((9.0d / 5.0d) * x[0]) + 32.0d);
            }
            F_swmm5.Snow.@snotmp = x[0];
            F_swmm5.Snow.@tipm = x[1];
            F_swmm5.Snow.@rnm = x[2];
            F_swmm5.Temp.@elev = (x[3] / F_swmm5.UCF(LENGTH));
            F_swmm5.Temp.@anglat = x[4];
            F_swmm5.Temp.@dtlong = (x[5] / 60.0d);
        } else if (__sw1 == 4) {
            if ((ntoks < 12)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            if ((F_input.match(tok[1], L2) != 0)) {
                i = 0;
            } else if ((F_input.match(tok[1], L3) != 0)) {
                i = 1;
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
            }
            L_f4: for (j = 0; (j < 10); j++) {
                if ((((!(F_input.getDouble(tok[(j + 2)], y) != 0)) || (y.v < 0.0d)) || (R.dgt(y.v, 1.0d)))) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[(j + 2)]);
                }
                F_swmm5.Snow.adc[i][j] = y.v;
            }
        }
        return 0;
    }

    // climate.c:285
    static int climate_readEvapParams(CPtr[] tok, int ntoks) {
        int i = 0;
        int k = 0;
        DBox x = new DBox(0.0d);
        k = F_input.findmatch(tok[0], F_keywords.EvapTypeWords);
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[0]);
        }
        if ((k == RECOVERY)) {
            if ((ntoks < 2)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            i = F_project.project_findObject(TIMEPATTERN, tok[1]);
            if ((i < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[1]);
            }
            F_swmm5.Evap.@recoveryPattern = i;
            return 0;
        }
        if ((k == DRYONLY)) {
            if ((ntoks < 2)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            if ((F_swmm5.strcomp(tok[1], L4) != 0)) {
                F_swmm5.Evap.@dryOnly = 0;
            } else if ((F_swmm5.strcomp(tok[1], L5) != 0)) {
                F_swmm5.Evap.@dryOnly = 1;
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
            }
            return 0;
        }
        F_swmm5.Evap.@type = k;
        if (((k != TEMPERATURE_EVAP) && (ntoks < 2))) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        int __sw1 = k;
        if (__sw1 == CONSTANT_EVAP) {
            if (!(F_input.getDouble(tok[1], x) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[1]);
            }
            L_f2: for (i = 0; (i < 12); i++) {
                F_swmm5.Evap.monthlyEvap[i] = x.v;
            }
        } else if (__sw1 == MONTHLY_EVAP) {
            if ((ntoks < 13)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            L_f3: for (i = 0; (i < 12); i++) {
                if (!(F_input.getDouble(tok[(i + 1)], new DElem(F_swmm5.Evap.monthlyEvap, i)) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[(i + 1)]);
                }
            }
        } else if (__sw1 == TIMESERIES_EVAP) {
            i = F_project.project_findObject(TSERIES, tok[1]);
            if ((i < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[1]);
            }
            F_swmm5.Evap.@tSeries = i;
            F_swmm5.Tseries[i].@refersTo = TIMESERIES_EVAP;
        } else if (__sw1 == FILE_EVAP) {
            if ((ntoks > 1)) {
                if ((ntoks < 13)) {
                    return F_error.error_setInpError(ERR_ITEMS, L0);
                }
                L_f4: for (i = 0; (i < 12); i++) {
                    if (!(F_input.getDouble(tok[(i + 1)], new DElem(F_swmm5.Evap.panCoeff, i)) != 0)) {
                        return F_error.error_setInpError(ERR_NUMBER, tok[(i + 1)]);
                    }
                }
            }
        }
        return 0;
    }

    // climate.c:377
    static int climate_readAdjustments(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        if ((ntoks == 1)) {
            return 0;
        }
        if ((F_input.match(tok[0], L6) != 0)) {
            if ((ntoks < 13)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            L_f1: for (i = 1; (i < 13); i++) {
                if (!(F_input.getDouble(tok[i], new DElem(F_swmm5.Adjust.temp, (i - 1))) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[i]);
                }
            }
            return 0;
        }
        if ((F_input.match(tok[0], L7) != 0)) {
            if ((ntoks < 13)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            L_f2: for (i = 1; (i < 13); i++) {
                if (!(F_input.getDouble(tok[i], new DElem(F_swmm5.Adjust.evap, (i - 1))) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[i]);
                }
            }
            return 0;
        }
        if ((F_input.match(tok[0], L8) != 0)) {
            if ((ntoks < 13)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            L_f3: for (i = 1; (i < 13); i++) {
                if (!(F_input.getDouble(tok[i], new DElem(F_swmm5.Adjust.rain, (i - 1))) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[i]);
                }
            }
            return 0;
        }
        if ((F_input.match(tok[0], L9) != 0)) {
            if ((ntoks < 13)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            L_f4: for (i = 1; (i < 13); i++) {
                if (!(F_input.getDouble(tok[i], new DElem(F_swmm5.Adjust.hydcon, (i - 1))) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[i]);
                }
                if ((F_swmm5.Adjust.hydcon[(i - 1)] <= 0.0d)) {
                    F_swmm5.Adjust.hydcon[(i - 1)] = 1.0d;
                }
            }
            return 0;
        }
        if ((F_input.match(tok[0], L10) != 0)) {
            if ((ntoks < 3)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            i = F_project.project_findObject(SUBCATCH, tok[1]);
            if ((i < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[1]);
            }
            j = F_project.project_findObject(TIMEPATTERN, tok[2]);
            if ((j < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
            F_swmm5.Subcatch[i].@nPervPattern = j;
            return 0;
        }
        if ((F_input.match(tok[0], L11) != 0)) {
            if ((ntoks < 3)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            i = F_project.project_findObject(SUBCATCH, tok[1]);
            if ((i < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[1]);
            }
            j = F_project.project_findObject(TIMEPATTERN, tok[2]);
            if ((j < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
            F_swmm5.Subcatch[i].@dStorePattern = j;
            return 0;
        }
        if ((F_input.match(tok[0], L12) != 0)) {
            if ((ntoks < 3)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            i = F_project.project_findObject(SUBCATCH, tok[1]);
            if ((i < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[1]);
            }
            j = F_project.project_findObject(TIMEPATTERN, tok[2]);
            if ((j < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
            F_swmm5.Subcatch[i].@infilPattern = j;
            return 0;
        }
        return F_error.error_setInpError(ERR_KEYWORD, tok[0]);
    }

    // climate.c:480
    static void climate_validate() {
        int i = 0;
        double a = 0.0d;
        double z = 0.0d;
        double pa = 0.0d;
        if ((((F_swmm5.Wind.type == FILE_WIND) || (F_swmm5.Evap.type == FILE_EVAP)) || (F_swmm5.Evap.type == TEMPERATURE_EVAP))) {
            if ((F_swmm5.Fclimate.mode == NO_FILE)) {
                F_report.report_writeErrorMsg(ERR_NO_CLIMATE_FILE, L0);
            }
        }
        if ((F_swmm5.Fclimate.mode == USE_FILE)) {
            climate_openFile();
        }
        if (((((F_swmm5.Snow.tipm < 0.0d) || (R.dgt(F_swmm5.Snow.tipm, 1.0d))) || (F_swmm5.Snow.rnm < 0.0d)) || (R.dgt(F_swmm5.Snow.rnm, 1.0d)))) {
            F_report.report_writeErrorMsg(ERR_SNOWMELT_PARAMS, L0);
        }
        a = F_swmm5.Temp.anglat;
        if (((a <= (-89.99d)) || (R.dge(a, 89.99d)))) {
            F_report.report_writeErrorMsg(ERR_SNOWMELT_PARAMS, L0);
        } else {
            F_swmm5.Temp.@tanAnglat = Glibm.tan(((a * 3.141592654d) / 180.0d));
        }
        z = (F_swmm5.Temp.elev / 1000.0d);
        if ((z <= 0.0d)) {
            pa = 29.9d;
        } else {
            pa = ((29.9d - (1.02d * z)) + (0.0032d * Glibm.pow(z, 2.4d)));
        }
        F_swmm5.Temp.@gamma = (0.000359d * pa);
        L_f1: for (i = 0; (i < 12); i++) {
            if ((F_swmm5.UnitSystem == SI)) {
                F_swmm5.Adjust.temp[i] *= (9.0d / 5.0d);
            }
            F_swmm5.Adjust.evap[i] = (F_swmm5.Adjust.evap[i] / (F_swmm5.UCF(EVAPRATE)));
        }
    }

    // climate.c:531
    static void climate_openFile() {
        int i = 0;
        IBox m = new IBox(0);
        IBox y = new IBox(0);
        if ((((F_swmm5.Fclimate.@file = R.fopen(F_swmm5.Fclimate.name, L13))) == null)) {
            F_report.report_writeErrorMsg(ERR_CLIMATE_FILE_OPEN, F_swmm5.Fclimate.name);
            return;
        }
        FileValue[TMIN] = F_swmm5.Temp.ta;
        FileValue[TMAX] = F_swmm5.Temp.ta;
        FileValue[EVAP] = 0.0d;
        FileValue[WIND] = 0.0d;
        FileFormat = getFileFormat();
        if ((FileFormat == UNKNOWN_FORMAT)) {
            F_report.report_writeErrorMsg(ERR_CLIMATE_FILE_READ, F_swmm5.Fclimate.name);
            return;
        }
        R.rewind(F_swmm5.Fclimate.file);
        Overrides.sstrncpy(FileLine, L0, 0);
        if ((F_swmm5.Temp.fileStartDate == (-693594))) {
            F_datetime.datetime_decodeDate(F_swmm5.StartDate.v, FileYear, FileMonth, FileDay);
        } else {
            F_datetime.datetime_decodeDate(F_swmm5.Temp.fileStartDate, FileYear, FileMonth, FileDay);
        }
        L_w1: while (!(R.feof(F_swmm5.Fclimate.file) != 0)) {
            Overrides.sstrncpy(FileLine, L0, 0);
            readFileLine(y, m);
            if (((y.v == FileYear.v) && (m.v == FileMonth.v))) {
                break L_w1;
            }
        }
        if ((R.feof(F_swmm5.Fclimate.file) != 0)) {
            F_report.report_writeErrorMsg(ERR_CLIMATE_END_OF_FILE, F_swmm5.Fclimate.name);
            return;
        }
        if (!(F_swmm5.ErrorCode != 0)) {
            FileElapsedDays = 0;
            FileLastDay = F_datetime.datetime_daysPerMonth(FileYear.v, FileMonth.v);
            readFileValues();
            L_f2: for (i = TMIN; (i <= WIND); i++) {
                if ((FileData[i][FileDay.v] == (-10000000000.0d))) {
                    continue L_f2;
                }
                FileValue[i] = FileData[i][FileDay.v];
            }
        }
    }

    // climate.c:598
    static void climate_initState() {
        LastDay = (-693594);
        F_swmm5.Temp.@tmax = (-10000000000.0d);
        F_swmm5.Snow.@removed = 0.0d;
        NextEvapDate.@v = F_swmm5.StartDate.v;
        NextEvapRate.@v = 0.0d;
        if (((F_swmm5.Evap.type == TIMESERIES_EVAP) && (F_swmm5.Evap.tSeries >= 0))) {
            F_table.table_getFirstEntry(F_swmm5.Tseries[F_swmm5.Evap.tSeries], NextEvapDate, NextEvapRate);
            if ((NextEvapDate.v < F_swmm5.StartDate.v)) {
                setNextEvapDate(F_swmm5.StartDate.v);
            }
            F_swmm5.Evap.@rate = (NextEvapRate.v / F_swmm5.UCF(EVAPRATE));
            setNextEvapDate(NextEvapDate.v);
        }
        if ((F_swmm5.Evap.type == TEMPERATURE_EVAP)) {
            Tma.@maxCount = (int) (R.ldiv(56L, 8L));
            Tma.@count = 0;
            Tma.@front = 0;
            Tma.@tAve = 0.0d;
            Tma.@tRng = 0.0d;
        }
    }

    // climate.c:641
    static void climate_setState(double theDate) {
        if ((F_swmm5.Fclimate.mode == USE_FILE)) {
            updateFileValues(theDate);
        }
        if ((F_swmm5.Temp.dataSource != NO_TEMP)) {
            setTemp(theDate);
        }
        setEvap(theDate);
        setWind(theDate);
        F_swmm5.Adjust.@rainFactor = F_swmm5.Adjust.rain[(F_datetime.datetime_monthOfYear(theDate) - 1)];
        F_swmm5.Adjust.@hydconFactor = F_swmm5.Adjust.hydcon[(F_datetime.datetime_monthOfYear(theDate) - 1)];
        setNextEvapDate(theDate);
    }

    // climate.c:659
    static double climate_getNextEvapDate() {
        return NextEvapDate.v;
    }

    // climate.c:671
    static void setNextEvapDate(double theDate) {
        IBox yr = new IBox(0);
        IBox mon = new IBox(0);
        IBox day = new IBox(0);
        int k = 0;
        DBox d = new DBox(0.0d);
        DBox e = new DBox(0.0d);
        if (R.dgt(NextEvapDate.v, theDate)) {
            return;
        }
        int __sw1 = F_swmm5.Evap.type;
        if (__sw1 == CONSTANT_EVAP) {
            NextEvapDate.@v = (theDate + 365.0d);
        } else if (__sw1 == MONTHLY_EVAP) {
            F_datetime.datetime_decodeDate(theDate, yr, mon, day);
            if ((mon.v == 12)) {
                mon.@v = 1;
                yr.@v = yr.v + 1;
            } else {
                mon.@v = mon.v + 1;
            }
            NextEvapDate.@v = F_datetime.datetime_encodeDate(yr.v, mon.v, 1);
        } else if (__sw1 == TIMESERIES_EVAP) {
            k = F_swmm5.Evap.tSeries;
            if ((k >= 0)) {
                NextEvapDate.@v = (theDate + 365.0d);
                L_w2: while (((F_table.table_getNextEntry(F_swmm5.Tseries[k], d, e) != 0) && (d.v <= F_swmm5.EndDateTime))) {
                    if (R.dge(d.v, theDate)) {
                        NextEvapDate.@v = d.v;
                        NextEvapRate.@v = e.v;
                        break L_w2;
                    }
                }
            }
        } else if (__sw1 == FILE_EVAP) {
            NextEvapDate.@v = (Math.floor(theDate) + 1.0d);
        } else {
            NextEvapDate.@v = (theDate + 365.0d);
        }
    }

    // climate.c:734
    static void updateFileValues(double theDate) {
        int i = 0;
        int deltaDays = 0;
        deltaDays = R.D2I(((Math.floor(theDate) - Math.floor(F_swmm5.StartDateTime))));
        if ((deltaDays > FileElapsedDays)) {
            FileElapsedDays++;
            FileDay.@v = FileDay.v + 1;
            if ((FileDay.v > FileLastDay)) {
                FileMonth.@v = FileMonth.v + 1;
                if ((FileMonth.v > 12)) {
                    FileMonth.@v = 1;
                    FileYear.@v = FileYear.v + 1;
                }
                readFileValues();
                FileDay.@v = 1;
                FileLastDay = F_datetime.datetime_daysPerMonth(FileYear.v, FileMonth.v);
            }
            L_f1: for (i = TMIN; (i <= WIND); i++) {
                if ((FileData[i][FileDay.v] == (-10000000000.0d))) {
                    continue L_f1;
                }
                FileValue[i] = FileData[i][FileDay.v];
            }
        }
    }

    // climate.c:782
    static void setTemp(double theDate) {
        int j = 0;
        int k = 0;
        int mon = 0;
        int day = 0;
        double theDay = 0.0d;
        double hour = 0.0d;
        double tmp = 0.0d;
        mon = F_datetime.datetime_monthOfYear(theDate);
        theDay = Math.floor(theDate);
        if (R.dgt(theDay, LastDay)) {
            day = F_datetime.datetime_dayOfYear(theDate);
            if ((F_swmm5.Temp.dataSource == FILE_TEMP)) {
                Tmin = (FileValue[TMIN] + F_swmm5.Adjust.temp[(mon - 1)]);
                Tmax = (FileValue[TMAX] + F_swmm5.Adjust.temp[(mon - 1)]);
                if (R.dgt(Tmin, Tmax)) {
                    tmp = Tmin;
                    Tmin = Tmax;
                    Tmax = tmp;
                }
                updateTempTimes(day);
                if ((F_swmm5.Evap.type == TEMPERATURE_EVAP)) {
                    updateTempMoveAve(Tmin, Tmax);
                    FileValue[EVAP] = getTempEvap(day, Tma.tAve, Tma.tRng);
                }
            }
            F_swmm5.Snow.@season = Glibm.sin((0.0172615d * ((day - 81.0d))));
            L_f1: for (j = 0; (j < F_swmm5.Nobjects[SNOWMELT]); j++) {
                F_snow.snow_setMeltCoeffs(j, F_swmm5.Snow.season);
            }
            LastDay = theDate;
        }
        if ((F_swmm5.Temp.dataSource == FILE_TEMP)) {
            hour = (((theDate - theDay)) * 24.0d);
            if ((hour < Hrsr)) {
                F_swmm5.Temp.@ta = (Tmin + ((Trng1 / 2.0d) * Glibm.sin(((3.141592654d / Dydif) * ((Hrsr - hour))))));
            } else if (((R.dge(hour, Hrsr)) && (hour <= Hrss))) {
                F_swmm5.Temp.@ta = (Tave + (Trng * Glibm.sin(((3.141592654d / Dhrdy) * ((Hrday - hour))))));
            } else {
                F_swmm5.Temp.@ta = (Tmax - (Trng * Glibm.sin(((3.141592654d / Dydif) * ((hour - Hrss))))));
            }
        }
        if ((F_swmm5.Temp.dataSource == TSERIES_TEMP)) {
            k = F_swmm5.Temp.tSeries;
            if ((k >= 0)) {
                F_swmm5.Temp.@ta = F_table.table_tseriesLookup(F_swmm5.Tseries[k], theDate, 1);
                if ((F_swmm5.UnitSystem == SI)) {
                    F_swmm5.Temp.@ta = ((((9.0d / 5.0d)) * F_swmm5.Temp.ta) + 32.0d);
                }
                F_swmm5.Temp.@ta = (F_swmm5.Temp.ta + (F_swmm5.Adjust.temp[(mon - 1)]));
            }
        }
        F_swmm5.Temp.@ea = (8.1175e6d * Glibm.exp(((-7701.544d) / ((F_swmm5.Temp.ta + 405.0265d)))));
    }

    // climate.c:872
    static void setEvap(double theDate) {
        int k = 0;
        int mon = F_datetime.datetime_monthOfYear(theDate);
        int __sw1 = F_swmm5.Evap.type;
        if (__sw1 == CONSTANT_EVAP) {
            F_swmm5.Evap.@rate = (F_swmm5.Evap.monthlyEvap[0] / F_swmm5.UCF(EVAPRATE));
        } else if (__sw1 == MONTHLY_EVAP) {
            F_swmm5.Evap.@rate = (F_swmm5.Evap.monthlyEvap[(mon - 1)] / F_swmm5.UCF(EVAPRATE));
        } else if (__sw1 == TIMESERIES_EVAP) {
            if (R.dge(theDate, NextEvapDate.v)) {
                F_swmm5.Evap.@rate = (NextEvapRate.v / F_swmm5.UCF(EVAPRATE));
            }
        } else if (__sw1 == FILE_EVAP) {
            F_swmm5.Evap.@rate = (FileValue[EVAP] / F_swmm5.UCF(EVAPRATE));
            F_swmm5.Evap.@rate = (F_swmm5.Evap.rate * (F_swmm5.Evap.panCoeff[(mon - 1)]));
        } else if (__sw1 == TEMPERATURE_EVAP) {
            F_swmm5.Evap.@rate = (FileValue[EVAP] / F_swmm5.UCF(EVAPRATE));
        } else {
            F_swmm5.Evap.@rate = 0.0d;
        }
        F_swmm5.Evap.@rate = (F_swmm5.Evap.rate + (F_swmm5.Adjust.evap[(mon - 1)]));
        F_swmm5.Evap.@recoveryFactor = 1.0d;
        k = F_swmm5.Evap.recoveryPattern;
        if (((k >= 0) && (F_swmm5.Pattern[k].type == MONTHLY_PATTERN))) {
            F_swmm5.Evap.@recoveryFactor = F_swmm5.Pattern[k].factor[(mon - 1)];
        }
    }

    // climate.c:923
    static void setWind(double theDate) {
        IBox yr = new IBox(0);
        IBox mon = new IBox(0);
        IBox day = new IBox(0);
        int __sw1 = F_swmm5.Wind.type;
        if (__sw1 == MONTHLY_WIND) {
            F_datetime.datetime_decodeDate(theDate, yr, mon, day);
            F_swmm5.Wind.@ws = (F_swmm5.Wind.aws[(mon.v - 1)] / F_swmm5.UCF(WINDSPEED));
        } else if (__sw1 == FILE_WIND) {
            F_swmm5.Wind.@ws = FileValue[WIND];
        } else {
            F_swmm5.Wind.@ws = 0.0d;
        }
    }

    // climate.c:949
    static void updateTempTimes(int day) {
        double decl = 0.0d;
        double hrang = 0.0d;
        double arg = 0.0d;
        decl = (0.40928d * Glibm.cos((0.017202d * ((172.0d - day)))));
        arg = ((-(Glibm.tan(decl))) * F_swmm5.Temp.tanAnglat);
        if ((arg <= (-1.0d))) {
            arg = 3.141592654d;
        } else if (R.dge(arg, 1.0d)) {
            arg = 0.0d;
        } else {
            arg = Glibm.acos(arg);
        }
        hrang = (3.8197d * arg);
        Hrsr = ((12.0d - hrang) + F_swmm5.Temp.dtlong);
        Hrss = (((12.0d + hrang) + F_swmm5.Temp.dtlong) - 3.0d);
        Dhrdy = (Hrsr - Hrss);
        Dydif = ((24.0d + Hrsr) - Hrss);
        Hrday = (((Hrsr + Hrss)) / 2.0d);
        Tave = (((Tmin + Tmax)) / 2.0d);
        Trng = (((Tmax - Tmin)) / 2.0d);
        if ((F_swmm5.Temp.tmax == (-10000000000.0d))) {
            Trng1 = (Tmax - Tmin);
        } else {
            Trng1 = (F_swmm5.Temp.tmax - Tmin);
        }
        F_swmm5.Temp.@tmax = Tmax;
    }

    // climate.c:981
    static double getTempEvap(int day, double tave, double trng) {
        double a = ((2.0d * 3.141592654d) / 365.0d);
        double ta = ((((tave - 32.0d)) * 5.0d) / 9.0d);
        double tr = ((trng * 5.0d) / 9.0d);
        double lamda = (2.50d - (0.002361d * ta));
        double dr = (1.0d + (0.033d * Glibm.cos((a * day))));
        double phi = (((F_swmm5.Temp.anglat * 2.0d) * 3.141592654d) / 360.0d);
        double del = (0.4093d * Glibm.sin((a * ((284.0d + ((double) (day)))))));
        double omega = Glibm.acos(((-(Glibm.tan(phi))) * Glibm.tan(del)));
        double ra = ((37.6d * dr) * ((((omega * Glibm.sin(phi)) * Glibm.sin(del)) + ((Glibm.cos(phi) * Glibm.cos(del)) * Glibm.sin(omega)))));
        double e = ((((0.0023d * ra) / lamda) * Math.sqrt(tr)) * ((ta + 17.8d)));
        if ((e < 0.0d)) {
            e = 0.0d;
        }
        if ((F_swmm5.UnitSystem == US)) {
            e = (e / 25.4d);
        }
        return e;
    }

    // climate.c:1010
    static int getFileFormat() {
        CPtr recdType = R.bufFrom(L0, 4);
        CPtr elemType = R.bufFrom(L0, 4);
        CPtr filler = R.bufFrom(L0, 5);
        CPtr staID = R.buf(80);
        CPtr s = R.buf(80);
        CPtr line = R.buf(1024);
        IBox y = new IBox(0);
        IBox m = new IBox(0);
        IBox d = new IBox(0);
        int n = 0;
        if (((R.fgets(line, (int) (1024), F_swmm5.Fclimate.file)) == null)) {
            return UNKNOWN_FORMAT;
        }
        Overrides.sstrncpy(recdType, line, 3);
        Overrides.sstrncpy(filler, line.add(23), 4);
        if (((R.strcmp(recdType, L14) == 0) && (R.strcmp(filler, L15) == 0))) {
            return TD3200;
        }
        if ((R.strlen(line) >= 233)) {
            Overrides.sstrncpy(elemType, line.add(13), 3);
            n = R.atoi(elemType);
            if ((((n == 1) || (n == 2)) || (n == 151))) {
                return DLY0204;
            }
        }
        n = R.sscanf(line, L16, staID, y, m, d, s);
        if ((n == 5)) {
            return USER_PREPARED;
        }
        if ((isGhcndFormat(line) != 0)) {
            return GHCND;
        }
        return UNKNOWN_FORMAT;
    }

    // climate.c:1055
    static void readFileLine(IRef y, IRef m) {
        L_w1: while ((R.strlen(FileLine) == 0)) {
            if (((R.fgets(FileLine, (int) (1024), F_swmm5.Fclimate.file)) == null)) {
                return;
            }
            if ((FileLine.g(0) == 10)) {
                FileLine.s(0, 0);
            }
        }
        int __sw1 = FileFormat;
        if (__sw1 == USER_PREPARED) {
            readUserFileLine(y, m);
        } else if (__sw1 == TD3200) {
            readTD3200FileLine(y, m);
        } else if (__sw1 == DLY0204) {
            readDLY0204FileLine(y, m);
        } else if (__sw1 == GHCND) {
            readGhcndFileLine(y, m);
        }
    }

    // climate.c:1082
    static void readUserFileLine(IRef y, IRef m) {
        int n = 0;
        CPtr staID = R.buf(80);
        n = R.sscanf(FileLine, L17, staID, y, m);
        if ((n < 3)) {
            F_report.report_writeErrorMsg(ERR_CLIMATE_FILE_READ, F_swmm5.Fclimate.name);
        }
    }

    // climate.c:1101
    static void readTD3200FileLine(IRef y, IRef m) {
        CPtr recdType = R.bufFrom(L0, 4);
        CPtr year = R.bufFrom(L0, 5);
        CPtr month = R.bufFrom(L0, 3);
        if ((R.strlen(FileLine) < 30)) {
            F_report.report_writeErrorMsg(ERR_CLIMATE_FILE_READ, F_swmm5.Fclimate.name);
            return;
        }
        Overrides.sstrncpy(recdType, FileLine, 3);
        if ((R.strcmp(recdType, L14) != 0)) {
            F_report.report_writeErrorMsg(ERR_CLIMATE_FILE_READ, F_swmm5.Fclimate.name);
            return;
        }
        Overrides.sstrncpy(year, FileLine.add(17), 4);
        Overrides.sstrncpy(month, FileLine.add(21), 2);
        y.set(R.atoi(year));
        m.set(R.atoi(month));
    }

    // climate.c:1137
    static void readDLY0204FileLine(IRef y, IRef m) {
        CPtr year = R.bufFrom(L0, 5);
        CPtr month = R.bufFrom(L0, 3);
        if ((R.strlen(FileLine) < 16)) {
            F_report.report_writeErrorMsg(ERR_CLIMATE_FILE_READ, F_swmm5.Fclimate.name);
            return;
        }
        Overrides.sstrncpy(year, FileLine.add(7), 4);
        Overrides.sstrncpy(month, FileLine.add(11), 2);
        y.set(R.atoi(year));
        m.set(R.atoi(month));
    }

    // climate.c:1164
    static void readFileValues() {
        int i = 0;
        int j = 0;
        IBox y = new IBox(0);
        IBox m = new IBox(0);
        L_f1: for (i = 0; (i < MAXCLIMATEVARS); i++) {
            L_f2: for (j = 0; (j < MAXDAYSPERMONTH); j++) {
                FileData[i][j] = (-10000000000.0d);
            }
        }
        L_w3: while (!(F_swmm5.ErrorCode != 0)) {
            if ((R.feof(F_swmm5.Fclimate.file) != 0)) {
                return;
            }
            readFileLine(y, m);
            if (((y.v > FileYear.v) || (m.v > FileMonth.v))) {
                return;
            }
            int __sw1 = FileFormat;
            if (__sw1 == USER_PREPARED) {
                parseUserFileLine();
            } else if (__sw1 == TD3200) {
                parseTD3200FileLine();
            } else if (__sw1 == DLY0204) {
                parseDLY0204FileLine();
            } else if (__sw1 == GHCND) {
                parseGhcndFileLine();
            }
            Overrides.sstrncpy(FileLine, L0, 0);
        }
    }

    // climate.c:1201
    static void parseUserFileLine() {
        int n = 0;
        IBox y = new IBox(0);
        IBox m = new IBox(0);
        IBox d = new IBox(0);
        CPtr staID = R.buf(80);
        CPtr s0 = R.bufFrom(L0, 80);
        CPtr s1 = R.bufFrom(L0, 80);
        CPtr s2 = R.bufFrom(L0, 80);
        CPtr s3 = R.bufFrom(L0, 80);
        double x = 0.0d;
        n = R.sscanf(FileLine, L18, staID, y, m, d, s0, s1, s2, s3);
        if ((n < 4)) {
            return;
        }
        if (((d.v < 1) || (d.v > 31))) {
            return;
        }
        if (((R.strlen(s0) > 0) && (s0.g(0) != 42))) {
            x = R.atof(s0);
            if ((F_swmm5.UnitSystem == SI)) {
                x = (((9.0d / 5.0d) * x) + 32.0d);
            }
            FileData[TMAX][d.v] = x;
        }
        if (((R.strlen(s1) > 0) && (s1.g(0) != 42))) {
            x = R.atof(s1);
            if ((F_swmm5.UnitSystem == SI)) {
                x = (((9.0d / 5.0d) * x) + 32.0d);
            }
            FileData[TMIN][d.v] = x;
        }
        if (((R.strlen(s2) > 0) && (s2.g(0) != 42))) {
            FileData[EVAP][d.v] = R.atof(s2);
        }
        if (((R.strlen(s3) > 0) && (s3.g(0) != 42))) {
            FileData[WIND][d.v] = R.atof(s3);
        }
    }

    // climate.c:1249
    static void parseTD3200FileLine() {
        int i = 0;
        CPtr param = R.bufFrom(L0, 5);
        Overrides.sstrncpy(param, FileLine.add(11), 4);
        L_f1: for (i = 0; (i < MAXCLIMATEVARS); i++) {
            if ((R.strcmp(param, ClimateVarWords[i]) == 0)) {
                setTD3200FileValues(i);
            }
        }
    }

    // climate.c:1271
    static void setTD3200FileValues(int i) {
        CPtr valCount = R.bufFrom(L0, 4);
        CPtr day = R.bufFrom(L0, 3);
        CPtr sign = R.bufFrom(L0, 2);
        CPtr value = R.bufFrom(L0, 6);
        CPtr flag2 = R.bufFrom(L0, 2);
        double x = 0.0d;
        int nValues = 0;
        int j = 0;
        int k = 0;
        int d = 0;
        Overrides.sstrncpy(valCount, FileLine.add(27), 3);
        nValues = R.atoi(valCount);
        if (((int) (R.strlen(FileLine)) >= ((12 * nValues) + 30))) {
            L_f1: for (j = 0; (j < nValues); j++) {
                k = (30 + (j * 12));
                Overrides.sstrncpy(day, FileLine.add(k), 2);
                Overrides.sstrncpy(sign, FileLine.add((k + 4)), 1);
                Overrides.sstrncpy(value, FileLine.add((k + 5)), 5);
                Overrides.sstrncpy(flag2, FileLine.add((k + 11)), 1);
                d = R.atoi(day);
                if (((((R.strcmp(value, L19) != 0) && (((flag2.g(0) == 48) || (flag2.g(0) == 49)))) && (d > 0)) && (d <= 31))) {
                    x = R.atof(value);
                    if ((sign.g(0) == 45)) {
                        x = (-x);
                    }
                    if ((i == EVAP)) {
                        x = (x / 100.0d);
                        if ((F_swmm5.UnitSystem == SI)) {
                            x *= 25.4d;
                        }
                    }
                    if ((i == WIND)) {
                        x = (x / 24.0d);
                    }
                    FileData[i][d] = x;
                }
            }
        }
    }

    // climate.c:1336
    static void parseDLY0204FileLine() {
        int j = 0;
        int k = 0;
        int p = 0;
        CPtr param = R.bufFrom(L0, 4);
        CPtr sign = R.bufFrom(L0, 2);
        CPtr value = R.bufFrom(L0, 6);
        CPtr code = R.bufFrom(L0, 2);
        double x = 0.0d;
        Overrides.sstrncpy(param, FileLine.add(13), 3);
        p = R.atoi(param);
        if ((p == 1)) {
            p = TMAX;
        } else if ((p == 2)) {
            p = TMIN;
        } else if ((p == 151)) {
            p = EVAP;
        } else {
            return;
        }
        if ((R.strlen(FileLine) < 233)) {
            return;
        }
        k = 16;
        L_f1: for (j = 1; (j <= 31); j++) {
            Overrides.sstrncpy(sign, FileLine.add(k), 1);
            Overrides.sstrncpy(value, FileLine.add((k + 1)), 5);
            Overrides.sstrncpy(code, FileLine.add((k + 6)), 1);
            k += 7;
            if (((R.strcmp(value, L19) != 0) && (R.strcmp(value, L20) != 0))) {
                int __sw1 = p;
                if (__sw1 == TMAX || __sw1 == TMIN) {
                    x = (R.atof(value) / 10.0d);
                    if ((sign.g(0) == 45)) {
                        x = (-x);
                    }
                    x = (((9.0d / 5.0d) * x) + 32.0d);
                } else if (__sw1 == EVAP) {
                    x = (R.atof(value) / 10.0d);
                    if ((F_swmm5.UnitSystem == US)) {
                        x = (x / 25.4d);
                    }
                } else {
                    return;
                }
                FileData[p][j] = x;
            }
        }
    }

    // climate.c:1401
    static int isGhcndFormat(CPtr line) {
        int i = 0;
        CPtr ptr = null;
        ptr = R.strstr(line, L21);
        if ((ptr == null)) {
            return 0;
        }
        FileDateFieldPos = (int) ((((long) (ptr.o - line.o))));
        L_f1: for (i = TMIN; (i <= WIND); i++) {
            FileFieldPos[i] = (-1);
        }
        ptr = R.strstr(line, L22);
        if ((ptr != null)) {
            FileFieldPos[TMIN] = (int) ((((long) (ptr.o - line.o))));
        }
        ptr = R.strstr(line, L23);
        if ((ptr != null)) {
            FileFieldPos[TMAX] = (int) ((((long) (ptr.o - line.o))));
        }
        ptr = R.strstr(line, L7);
        if ((ptr != null)) {
            FileFieldPos[EVAP] = (int) ((((long) (ptr.o - line.o))));
        }
        FileWindType = WDMV;
        ptr = R.strstr(line, L24);
        if ((ptr == null)) {
            FileWindType = AWND;
            ptr = R.strstr(line, L25);
        }
        if ((ptr != null)) {
            FileFieldPos[WIND] = (int) ((((long) (ptr.o - line.o))));
        }
        L_f2: for (i = TMIN; (i <= WIND); i++) {
            if ((FileFieldPos[i] >= 0)) {
                return 1;
            }
        }
        return 0;
    }

    // climate.c:1445
    static void readGhcndFileLine(IRef y, IRef m) {
        int n = R.sscanf(FileLine.add(FileDateFieldPos), L26, y, m);
        if ((n != 2)) {
            y.set((-99999));
            m.set((-99999));
        }
    }

    // climate.c:1463
    static void parseGhcndFileLine() {
        IBox y = new IBox(0);
        IBox m = new IBox(0);
        IBox d = new IBox(0);
        int n = 0;
        int i = 0;
        DBox v = new DBox(0.0d);
        n = R.sscanf(FileLine.add(FileDateFieldPos), L27, y, m, d);
        if ((n < 3)) {
            return;
        }
        if (((d.v < 1) || (d.v > 31))) {
            return;
        }
        L_f1: for (i = TMIN; (i <= WIND); i++) {
            if ((FileFieldPos[i] >= 0)) {
                if ((R.sscanf(FileLine.add(FileFieldPos[i]), L28, v) > 0)) {
                    if ((Math.abs(v.v) < 9999.0d)) {
                        FileData[i][d.v] = convertGhcndValue(i, v.v);
                    }
                }
            }
        }
    }

    // climate.c:1496
    static double convertGhcndValue(int var_, double v) {
        int __sw1 = var_;
        if (__sw1 == TMIN || __sw1 == TMAX) {
            int __sw2 = FileTempUnits;
            if (__sw2 == DEG_C10) {
                return ((((v / 10.0d) * 9.0d) / 5.0d) + 32.0d);
            } else if (__sw2 == DEG_C) {
                return (((v * 9.0d) / 5.0d) + 32.0d);
            } else {
                return v;
            }
        } else if (__sw1 == EVAP) {
            int __sw3 = FileTempUnits;
            if (__sw3 == DEG_C10) {
                v = (v / 10.0d);
                if ((F_swmm5.UnitSystem == US)) {
                    v = (v / 25.4d);
                }
                return v;
            } else if (__sw3 == DEG_C) {
                if ((F_swmm5.UnitSystem == US)) {
                    v = (v / 25.4d);
                }
                return v;
            } else {
                if ((F_swmm5.UnitSystem == SI)) {
                    v *= 25.4d;
                }
                return v;
            }
        } else if (__sw1 == WIND) {
            int __sw4 = FileTempUnits;
            if (__sw4 == DEG_C10) {
                if ((FileWindType == WDMV)) {
                    return ((v * 0.62137d) / 24.0d);
                } else {
                    return ((((v / 10.0d) / 1000.0d) * 0.62137d) * 3600.0d);
                }
            } else if (__sw4 == DEG_C) {
                if ((FileWindType == WDMV)) {
                    return ((v * 0.62137d) / 24.0d);
                } else {
                    return (((v / 1000.0d) * 0.62137d) * 3600.0d);
                }
            } else {
                if ((FileWindType == WDMV)) {
                    return (v / 24.0d);
                } else {
                    return v;
                }
            }
        } else {
            return v;
        }
        throw new IllegalStateException('convertGhcndValue: no return');
    }

    // climate.c:1569
    static void updateTempMoveAve(double tmin, double tmax) {
        double ta = 0.0d;
        double tr = 0.0d;
        int kount = Tma.count;
        double count = kount;
        ta = (((tmin + tmax)) / 2.0d);
        tr = Math.abs((tmax - tmin));
        if ((kount == Tma.maxCount)) {
            Tma.@tAve = (((((Tma.tAve * count) + ta) - Tma.ta[Tma.front])) / count);
            Tma.@tRng = (((((Tma.tRng * count) + tr) - Tma.tr[Tma.front])) / count);
            Tma.ta[Tma.front] = ta;
            Tma.tr[Tma.front] = tr;
            Tma.@front = Tma.front + 1;
            if ((Tma.front == count)) {
                Tma.@front = 0;
            }
        } else {
            Tma.@tAve = ((((Tma.tAve * count) + ta)) / ((count + 1)));
            Tma.@tRng = ((((Tma.tRng * count) + tr)) / ((count + 1)));
            Tma.ta[Tma.front] = ta;
            Tma.tr[Tma.front] = tr;
            Tma.@count = Tma.count + 1;
            Tma.@front = Tma.front + 1;
            if ((Tma.count == Tma.maxCount)) {
                Tma.@front = 0;
            }
        }
    }

}
