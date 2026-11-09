// Generated from EPA SWMM 5.2.4 (rain.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_rain {
    public static TRainStats RainStats = new TRainStats();
    public static int Condition = 0;
    public static int TimeOffset = 0;
    public static int DataOffset = 0;
    public static int ValueOffset = 0;
    public static int RainType = 0;
    public static int Interval = 0;
    public static double UnitsFactor = 0.0d;
    public static float RainAccum = 0.0f;
    public static CPtr StationID = null;
    public static double AccumStartDate = 0.0d;
    public static double PreviousDate = 0.0d;
    public static int GageIndex = 0;
    public static int hasStationName = 0;

    // rain.c:118
    static void rain_open() {
        int i = 0;
        int count = 0;
        count = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[GAGE]); i++) {
            if ((F_swmm5.Gage[i].dataSource == RAIN_FILE)) {
                count++;
            }
        }
        F_swmm5.Frain.@file = (null);
        if ((count == 0)) {
            F_swmm5.Frain.@mode = NO_FILE;
        } else {
            int __sw1 = F_swmm5.Frain.mode;
            if (__sw1 == SCRATCH_FILE) {
                Overrides.getTempFileName(F_swmm5.Frain.name);
                if ((((F_swmm5.Frain.@file = R.fopen(F_swmm5.Frain.name, L166))) == null)) {
                    F_report.report_writeErrorMsg(ERR_RAIN_FILE_SCRATCH, L0);
                    return;
                }
            } else if (__sw1 == USE_FILE) {
                if ((((F_swmm5.Frain.@file = R.fopen(F_swmm5.Frain.name, L165))) == null)) {
                    F_report.report_writeErrorMsg(ERR_RAIN_FILE_OPEN, F_swmm5.Frain.name);
                    return;
                }
            } else if (__sw1 == SAVE_FILE) {
                if ((((F_swmm5.Frain.@file = R.fopen(F_swmm5.Frain.name, L166))) == null)) {
                    F_report.report_writeErrorMsg(ERR_RAIN_FILE_OPEN, F_swmm5.Frain.name);
                    return;
                }
            }
        }
        if (((F_swmm5.Frain.mode == SCRATCH_FILE) || (F_swmm5.Frain.mode == SAVE_FILE))) {
            createRainFile(count);
        }
        if ((F_swmm5.Frain.mode != NO_FILE)) {
            initRainFile();
        }
        F_rdii.rdii_openRdii();
    }

    // rain.c:184
    static void rain_close() {
        if ((F_swmm5.Frain.file != null)) {
            R.fclose(F_swmm5.Frain.file);
            if ((F_swmm5.Frain.mode == SCRATCH_FILE)) {
                R.remove(F_swmm5.Frain.name);
            }
        }
        F_swmm5.Frain.@file = (null);
        F_rdii.rdii_closeRdii();
    }

    // rain.c:202
    static void createRainFile(int count) {
        int i = 0;
        int k = 0;
        IBox kount = new IBox(count);
        int filePos1 = 0;
        IBox filePos2 = new IBox(0);
        IBox filePos3 = new IBox(0);
        IBox interval = new IBox(0);
        IBox dummy = new IBox((-1));
        CPtr staID = R.buf(1025);
        CPtr fileStamp = R.bufFrom(L327, 11);
        if (((F_swmm5.ErrorCode != 0) || (!(F_swmm5.Frain.file != null)))) {
            return;
        }
        R.fwrite(fileStamp, 0, (int) (R.strlen(fileStamp)), F_swmm5.Frain.file);
        R.fwrite1(kount.v, 4, F_swmm5.Frain.file);
        filePos1 = (int) (R.ftell(F_swmm5.Frain.file));
        if ((count > 0)) {
            F_report.report_writeRainStats((-1), RainStats);
        }
        R.strcpy(staID, L33);
        L_f1: for (i = 0; (i < count); i++) {
            R.fwrite(staID, 0, (int) ((1024 + 1)), F_swmm5.Frain.file);
            L_f2: for (k = 1; (k <= 3); k++) {
                R.fwrite1(dummy.v, 4, F_swmm5.Frain.file);
            }
        }
        filePos2.@v = (int) (R.ftell(F_swmm5.Frain.file));
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[GAGE]); i++) {
            if (((F_swmm5.ErrorCode != 0) || (F_swmm5.Gage[i].dataSource != RAIN_FILE))) {
                continue L_f3;
            }
            if ((rainFileConflict(i) != 0)) {
                break L_f3;
            }
            R.fseek(F_swmm5.Frain.file, filePos2.v, 0);
            if ((addGageToRainFile(i) != 0)) {
                filePos3.@v = (int) (R.ftell(F_swmm5.Frain.file));
                R.fseek(F_swmm5.Frain.file, filePos1, 0);
                Overrides.sstrncpy(staID, F_swmm5.Gage[i].staID, 1024);
                interval.@v = Interval;
                R.fwrite(staID, 0, (int) ((1024 + 1)), F_swmm5.Frain.file);
                R.fwrite1(interval.v, 4, F_swmm5.Frain.file);
                R.fwrite1(filePos2.v, 4, F_swmm5.Frain.file);
                R.fwrite1(filePos3.v, 4, F_swmm5.Frain.file);
                filePos1 = (int) (R.ftell(F_swmm5.Frain.file));
                filePos2.@v = filePos3.v;
                F_report.report_writeRainStats(i, RainStats);
            }
        }
        if ((F_swmm5.ErrorCode != 0)) {
            R.fclose(F_swmm5.Frain.file);
            F_swmm5.Frain.@file = (null);
            R.remove(F_swmm5.Frain.name);
        }
    }

    // rain.c:278
    static int rainFileConflict(int i) {
        int j = 0;
        CPtr staID = F_swmm5.Gage[i].staID;
        CPtr fname = F_swmm5.Gage[i].fname;
        L_f1: for (j = 0; (j < i); j++) {
            if (((F_swmm5.strcomp(F_swmm5.Gage[j].staID, staID) != 0) && (!(F_swmm5.strcomp(F_swmm5.Gage[j].fname, fname) != 0)))) {
                F_report.report_writeErrorMsg(ERR_RAIN_FILE_CONFLICT, F_swmm5.Gage[i].ID);
                return 1;
            }
        }
        return 0;
    }

    // rain.c:302
    static int addGageToRainFile(int i) {
        CFile f = null;
        int fileFormat = 0;
        IBox hdrLines = new IBox(0);
        StationID = null;
        if ((((f = R.fopen(F_swmm5.Gage[i].fname, L13))) == null)) {
            F_report.report_writeErrorMsg(ERR_RAIN_FILE_DATA, F_swmm5.Gage[i].fname);
        } else {
            fileFormat = findFileFormat(f, i, hdrLines);
            if ((fileFormat == UNKNOWN_FORMAT)) {
                F_report.report_writeErrorMsg(ERR_RAIN_FILE_FORMAT, F_swmm5.Gage[i].fname);
            } else {
                GageIndex = i;
                readFile(f, fileFormat, hdrLines.v, F_swmm5.Gage[i].startFileDate, F_swmm5.Gage[i].endFileDate);
            }
            R.fclose(f);
        }
        if ((F_swmm5.ErrorCode != 0)) {
            return 0;
        } else {
            return 1;
        }
        throw new IllegalStateException('addGageToRainFile: no return');
    }

    // rain.c:341
    static void initRainFile() {
        CPtr fileStamp = R.bufFrom(L327, 11);
        CPtr fStamp = R.bufFrom(L327, 11);
        int i = 0;
        IBox kount = new IBox(0);
        long filePos = 0L;
        if (((F_swmm5.ErrorCode != 0) || (!(F_swmm5.Frain.file != null)))) {
            return;
        }
        R.rewind(F_swmm5.Frain.file);
        R.fread(fStamp, 0, (int) (R.strlen(fileStamp)), F_swmm5.Frain.file);
        if ((R.strcmp(fStamp, fileStamp) != 0)) {
            F_report.report_writeErrorMsg(ERR_RAIN_IFACE_FORMAT, L0);
            return;
        }
        R.fread1(kount, 4, F_swmm5.Frain.file);
        filePos = R.ftell(F_swmm5.Frain.file);
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[GAGE]); i++) {
            if (((F_swmm5.ErrorCode != 0) || (F_swmm5.Gage[i].dataSource != RAIN_FILE))) {
                continue L_f1;
            }
            R.fseek(F_swmm5.Frain.file, filePos, 0);
            if (((!(findGageInFile(i, kount.v) != 0)) || (F_swmm5.Gage[i].startFilePos == F_swmm5.Gage[i].endFilePos))) {
                F_report.report_writeErrorMsg(ERR_RAIN_FILE_GAGE, F_swmm5.Gage[i].ID);
            }
        }
    }

    // rain.c:385
    static int findGageInFile(int i, int kount) {
        int k = 0;
        IBox interval = new IBox(0);
        IBox filePos1 = new IBox(0);
        IBox filePos2 = new IBox(0);
        CPtr staID = R.bufFrom(L0, 1025);
        L_f1: for (k = 1; (k <= kount); k++) {
            R.fread(staID, 0, (int) ((1024 + 1)), F_swmm5.Frain.file);
            R.fread1(interval, 4, F_swmm5.Frain.file);
            R.fread1(filePos1, 4, F_swmm5.Frain.file);
            R.fread1(filePos2, 4, F_swmm5.Frain.file);
            if ((R.strcmp(staID, F_swmm5.Gage[i].staID) == 0)) {
                F_swmm5.Gage[i].@rainType = RAINFALL_VOLUME;
                F_swmm5.Gage[i].@rainInterval = interval.v;
                F_swmm5.Gage[i].@startFilePos = filePos1.v;
                F_swmm5.Gage[i].@endFilePos = filePos2.v;
                F_swmm5.Gage[i].@currentFilePos = F_swmm5.Gage[i].startFilePos;
                return 1;
            }
        }
        return 0;
    }

    // rain.c:420
    static int findFileFormat(CFile f, int i, IRef hdrLines) {
        int fileFormat = 0;
        int lineCount = 0;
        int maxCount = 5;
        int n = 0;
        IBox div = new IBox(0);
        LBox sn2 = new LBox(0L);
        CPtr recdType = R.buf(4);
        CPtr elemType = R.buf(5);
        CPtr coopID = R.bufFrom(L0, 6);
        CPtr line = R.buf(1024);
        IBox year = new IBox(0);
        IBox month = new IBox(0);
        IBox day = new IBox(0);
        IBox hour = new IBox(0);
        IBox minute = new IBox(0);
        IBox elem = new IBox(0);
        FBox x = new FBox(0.0f);
        fileFormat = UNKNOWN_FORMAT;
        hasStationName = 0;
        UnitsFactor = 1.0d;
        Interval = 0;
        hdrLines.set(0);
        L_f1: for (lineCount = 1; (lineCount <= maxCount); lineCount++) {
            if (((R.fgets(line, (int) (1024), f)) == null)) {
                return fileFormat;
            }
            n = R.sscanf(line, L328, sn2, div, elemType);
            if ((n == 3)) {
                Interval = getNWSInterval(elemType);
                TimeOffset = Interval;
                if ((Interval > 0)) {
                    fileFormat = NWS_SPACE_DELIMITED;
                    break L_f1;
                }
            }
            n = R.sscanf(line.add(37), L329, div, elemType, recdType, year);
            if ((n == 4)) {
                Interval = getNWSInterval(elemType);
                TimeOffset = Interval;
                if ((Interval > 0)) {
                    fileFormat = NWS_SPACE_DELIMITED;
                    hasStationName = 1;
                    break L_f1;
                }
            }
            n = R.sscanf(line, L330, sn2, div, elemType);
            if ((n == 3)) {
                Interval = getNWSInterval(elemType);
                TimeOffset = Interval;
                if ((Interval > 0)) {
                    fileFormat = NWS_COMMA_DELIMITED;
                    break L_f1;
                }
            }
            n = R.sscanf(line.add(37), L331, div, elemType, recdType, year);
            if ((n == 4)) {
                Interval = getNWSInterval(elemType);
                TimeOffset = Interval;
                if ((Interval > 0)) {
                    fileFormat = NWS_COMMA_DELIMITED;
                    hasStationName = 1;
                    break L_f1;
                }
            }
            n = R.sscanf(line, L332, recdType, sn2, div, elemType);
            if ((n == 4)) {
                Interval = getNWSInterval(elemType);
                TimeOffset = Interval;
                if ((Interval > 0)) {
                    fileFormat = NWS_TAPE;
                    break L_f1;
                }
            }
            n = R.sscanf(line, L333, coopID, sn2);
            if (((n == 2) && (R.strcmp(coopID, L334) == 0))) {
                fileFormat = findNWSOnlineFormat(f, line);
                break L_f1;
            }
            n = R.sscanf(line, L335, sn2, year, month, day, elem);
            if ((n == 5)) {
                if (((elem.v == 123) && (R.strlen(line) >= 185))) {
                    fileFormat = AES_HLY;
                    Interval = 3600;
                    TimeOffset = Interval;
                    UnitsFactor = (1.0d / 25.4d);
                    break L_f1;
                }
            }
            n = R.sscanf(line, L336, sn2, year, month, day, elem);
            if ((n == 5)) {
                if (((elem.v == 159) && (R.strlen(line) >= 691))) {
                    fileFormat = CMC_FIF;
                    Interval = 900;
                } else if (((elem.v == 123) && (R.strlen(line) >= 186))) {
                    fileFormat = CMC_HLY;
                    Interval = 3600;
                }
                if (((fileFormat == CMC_FIF) || (fileFormat == CMC_HLY))) {
                    TimeOffset = Interval;
                    UnitsFactor = (1.0d / 25.4d);
                    break L_f1;
                }
            }
            if ((parseStdLine(line, year, month, day, hour, minute, x) != 0)) {
                fileFormat = STD_SPACE_DELIMITED;
                RainType = F_swmm5.Gage[i].rainType;
                Interval = F_swmm5.Gage[i].rainInterval;
                if ((F_swmm5.Gage[i].rainUnits == SI)) {
                    UnitsFactor = (1.0d / 25.4d);
                }
                TimeOffset = 0;
                StationID = F_swmm5.Gage[i].staID;
                break L_f1;
            }
            hdrLines.set(hdrLines.get() + 1);
        }
        if ((fileFormat != UNKNOWN_FORMAT)) {
            F_swmm5.Gage[i].@rainInterval = Interval;
        }
        return fileFormat;
    }

    // rain.c:584
    static int findNWSOnlineFormat(CFile f, CPtr line) {
        int n = 0;
        int fileFormat = UNKNOWN_FORMAT;
        CPtr str = null;
        R.rewind(f);
        R.fgets(line, (int) (1024), f);
        if ((((str = R.strstr(line, L337))) != null)) {
            Interval = 3600;
            TimeOffset = Interval;
            ValueOffset = (int) ((((long) (str.o - line.o))));
            fileFormat = NWS_ONLINE_60;
        } else if ((((str = R.strstr(line, L338))) != null)) {
            Interval = 900;
            TimeOffset = Interval;
            ValueOffset = (int) ((((long) (str.o - line.o))));
            fileFormat = NWS_ONLINE_15;
        } else {
            return UNKNOWN_FORMAT;
        }
        L_f1: for (n = 1; (n <= 5); n++) {
            if (((R.fgets(line, (int) (1024), f)) == null)) {
                return UNKNOWN_FORMAT;
            }
            if (((R.strstr(line, L334)) == null)) {
                continue L_f1;
            }
            str = R.strrchr(line, 58);
            if ((str == null)) {
                return UNKNOWN_FORMAT;
            }
            n = (int) ((((long) (str.o - line.o))));
            DataOffset = (n - 11);
            return fileFormat;
        }
        return UNKNOWN_FORMAT;
    }

    // rain.c:641
    static int getNWSInterval(CPtr elemType) {
        if ((R.strcmp(elemType, L337) == 0)) {
            return 3600;
        } else if ((R.strcmp(elemType, L338) == 0)) {
            return 900;
        } else if ((R.strcmp(elemType, L339) == 0)) {
            return 900;
        } else {
            return 0;
        }
        throw new IllegalStateException('getNWSInterval: no return');
    }

    // rain.c:656
    static void readFile(CFile f, int fileFormat, int hdrLines, double day1, double day2) {
        CPtr line = R.buf(1024);
        int i = 0;
        int n = 0;
        R.rewind(f);
        RainStats.@startDate = (-693594);
        RainStats.@endDate = (-693594);
        RainStats.@periodsRain = 0;
        RainStats.@periodsMissing = 0;
        RainStats.@periodsMalfunc = 0;
        RainAccum = (float) (0.0d);
        AccumStartDate = (-693594);
        PreviousDate = (-693594);
        L_f1: for (i = 1; (i <= hdrLines); i++) {
            if (((R.fgets(line, (int) (1024), f)) == null)) {
                return;
            }
        }
        L_w2: while (((R.fgets(line, (int) (1024), f)) != null)) {
            int __sw1 = fileFormat;
            if (__sw1 == STD_SPACE_DELIMITED) {
                n = readStdLine(line, day1, day2);
            } else if (__sw1 == NWS_TAPE || __sw1 == NWS_SPACE_DELIMITED || __sw1 == NWS_COMMA_DELIMITED || __sw1 == NWS_ONLINE_60 || __sw1 == NWS_ONLINE_15) {
                n = readNWSLine(line, fileFormat, day1, day2);
            } else if (__sw1 == AES_HLY || __sw1 == CMC_FIF || __sw1 == CMC_HLY) {
                n = readCMCLine(line, fileFormat, day1, day2);
            } else {
                n = (-1);
            }
            if ((n < 0)) {
                break L_w2;
            }
        }
    }

    // rain.c:717
    static int readNWSLine(CPtr line, int fileFormat, double day1, double day2) {
        IBox flag1 = new IBox(0);
        IBox flag2 = new IBox(0);
        int isMissing = 0;
        double date1 = 0.0d;
        long result = 1;
        int k = 0;
        IBox y = new IBox(0);
        IBox m = new IBox(0);
        IBox d = new IBox(0);
        IBox n = new IBox(0);
        IBox hour = new IBox(0);
        IBox minute = new IBox(0);
        LBox v = new LBox(0L);
        float x = 0.0f;
        int lineLength = ((int) (R.strlen(line)) - 1);
        int nameLength = 0;
        int __sw1 = fileFormat;
        if (__sw1 == NWS_TAPE) {
            if ((lineLength <= 30)) {
                return 0;
            }
            if ((R.sscanf(line.add(17), L340, y, m, d, n) < 4)) {
                return 0;
            }
            k = 30;
        } else if (__sw1 == NWS_SPACE_DELIMITED) {
            if ((hasStationName != 0)) {
                nameLength = 31;
            }
            if ((lineLength <= (28 + nameLength))) {
                return 0;
            }
            k = (18 + nameLength);
            if ((R.sscanf(line.add(k), L341, y, m, d) < 3)) {
                return 0;
            }
            k = (k + 10);
        } else if (__sw1 == NWS_COMMA_DELIMITED) {
            if ((lineLength <= 28)) {
                return 0;
            }
            if ((R.sscanf(line.add(18), L342, y, m, d) < 3)) {
                return 0;
            }
            k = 28;
        } else if (__sw1 == NWS_ONLINE_60 || __sw1 == NWS_ONLINE_15) {
            if ((lineLength <= (DataOffset + 23))) {
                return 0;
            }
            if ((R.sscanf(line.add(DataOffset), L27, y, m, d) < 3)) {
                return 0;
            }
            k = (DataOffset + 8);
        } else {
            return 0;
        }
        date1 = F_datetime.datetime_encodeDate(y.v, m.v, d.v);
        if (((day1 != (-693594)) && (date1 < day1))) {
            return 0;
        }
        if (((day2 != (-693594)) && (R.dgt(date1, day2)))) {
            return (-1);
        }
        L_w2: while ((k < lineLength)) {
            flag1.@v = 0;
            flag2.@v = 0;
            v.@v = 99999;
            hour.@v = 25;
            minute.@v = 0;
            int __sw2 = fileFormat;
            if (__sw2 == NWS_TAPE) {
                n.@v = R.sscanf(line.add(k), L343, hour, minute, v, flag1, flag2);
                k += 12;
            } else if (__sw2 == NWS_SPACE_DELIMITED) {
                n.@v = R.sscanf(line.add(k), L344, hour, minute, v, flag1, flag2);
                k += 16;
            } else if (__sw2 == NWS_COMMA_DELIMITED) {
                n.@v = R.sscanf(line.add(k), L345, hour, minute, v, flag1, flag2);
                k += 16;
            } else if (__sw2 == NWS_ONLINE_60 || __sw2 == NWS_ONLINE_15) {
                n.@v = R.sscanf(line.add(k), L346, hour, minute);
                CPtr __c3 = R.buf(2); __c3.s(0, flag1.v);
                n.@v = (n.v + (readNwsOnlineValue(line.add(ValueOffset), v, __c3)));
                flag1.@v = __c3.g(0);
                if ((hour.v == 0)) {
                    hour.@v = 24;
                    date1 -= 1.0d;
                }
                k += lineLength;
            } else {
                n.@v = 0;
            }
            if (((n.v < 3) || (hour.v >= 25))) {
                break L_w2;
            }
            setCondition(flag1.v);
            if ((((Condition == DELETED_PERIOD) || (Condition == MISSING_PERIOD)) || (flag1.v == 77))) {
                isMissing = 1;
            } else if ((v.v >= 9999)) {
                isMissing = 1;
            } else {
                isMissing = 0;
            }
            if ((flag1.v == 97)) {
                AccumStartDate = (date1 + F_datetime.datetime_encodeTime(hour.v, minute.v, 0));
            } else if ((flag1.v == 65)) {
                saveAccumRainfall(date1, hour.v, minute.v, v.v);
            } else {
                x = ((float) (((float) (v.v)) / 100.0f));
                if (((R.dgt(x, (float) (0))) || (isMissing != 0))) {
                    saveRainfall(date1, hour.v, minute.v, x, isMissing);
                }
            }
            if ((((flag1.v == 65) || (flag1.v == 125)) || (flag1.v == 93))) {
                Condition = 0;
            }
        }
        return (int) (result);
    }

    // rain.c:862
    static int readNwsOnlineValue(CPtr s, LRef v, CPtr flag) {
        int n = 0;
        FBox x = new FBox(99.98999786376953f);
        if ((R.strchr(s, 46) != null)) {
            n = R.sscanf(s, L347, x, flag);
            v.set(R.D2L((((float) (((float) (100.0f * x.v)) + 0.5f)))));
        } else {
            n = R.sscanf(s, L348, v, flag);
        }
        return n;
    }

    // rain.c:891
    static void setCondition(int flag) {
        int __sw1 = flag;
        if (__sw1 == 97 || __sw1 == 65) {
            Condition = ACCUMULATED_PERIOD;
        } else if (__sw1 == 123 || __sw1 == 125) {
            Condition = DELETED_PERIOD;
        } else if (__sw1 == 91 || __sw1 == 93) {
            Condition = MISSING_PERIOD;
        } else {
            Condition = NO_CONDITION;
        }
    }

    // rain.c:914
    static int readCMCLine(CPtr line, int fileFormat, double day1, double day2) {
        IBox flag = new IBox(0);
        int isMissing = 0;
        double date1 = 0.0d;
        LBox sn = new LBox(0L);
        LBox v = new LBox(0L);
        int col = 0;
        int j = 0;
        int jMax = 0;
        IBox elem = new IBox(0);
        IBox y = new IBox(0);
        IBox m = new IBox(0);
        IBox d = new IBox(0);
        int hour = 0;
        int minute = 0;
        float x = 0.0f;
        if ((fileFormat == AES_HLY)) {
            if ((R.sscanf(line, L335, sn, y, m, d, elem) < 5)) {
                return 0;
            }
            if ((y.v < 100)) {
                y.@v = (y.v + 2000);
            } else {
                y.@v = (y.v + 1000);
            }
            col = 17;
        } else {
            if ((R.sscanf(line, L336, sn, y, m, d, elem) < 5)) {
                return 0;
            }
            col = 18;
        }
        date1 = F_datetime.datetime_encodeDate(y.v, m.v, d.v);
        if (((day1 != (-693594)) && (date1 < day1))) {
            return 0;
        }
        if (((day2 != (-693594)) && (R.dgt(date1, day2)))) {
            return (-1);
        }
        if (((fileFormat == AES_HLY) && (elem.v != 123))) {
            return 0;
        } else if (((fileFormat == CMC_FIF) && (elem.v != 159))) {
            return 0;
        } else if (((fileFormat == CMC_HLY) && (elem.v != 123))) {
            return 0;
        }
        hour = 0;
        minute = 0;
        jMax = 24;
        if ((fileFormat == CMC_FIF)) {
            jMax = 96;
        }
        L_f1: for (j = 1; (j <= jMax); j++) {
            if ((R.sscanf(line.add(col), L349, v, flag) < 2)) {
                return 0;
            }
            col += 7;
            if ((v.v == (-99999))) {
                isMissing = 1;
            } else {
                isMissing = 0;
            }
            x = ((float) ((((((double) (v.v)) / 10.0d) / 25.4d))));
            if (((R.dgt(x, (float) (0))) || (isMissing != 0))) {
                saveRainfall(date1, hour, minute, x, isMissing);
            }
            if ((fileFormat == CMC_FIF)) {
                minute += 15;
                if ((minute == 60)) {
                    minute = 0;
                    hour++;
                }
            } else {
                hour++;
            }
        }
        return 1;
    }

    // rain.c:994
    static int readStdLine(CPtr line, double day1, double day2) {
        double date1 = 0.0d;
        double date2 = 0.0d;
        IBox year = new IBox(0);
        IBox month = new IBox(0);
        IBox day = new IBox(0);
        IBox hour = new IBox(0);
        IBox minute = new IBox(0);
        FBox x = new FBox(0.0f);
        if (!(parseStdLine(line, year, month, day, hour, minute, x) != 0)) {
            return 0;
        }
        date1 = F_datetime.datetime_encodeDate(year.v, month.v, day.v);
        if (((day1 != (-693594)) && (date1 < day1))) {
            return 0;
        }
        if (((day2 != (-693594)) && (R.dgt(date1, day2)))) {
            return (-1);
        }
        date2 = (date1 + F_datetime.datetime_encodeTime(hour.v, minute.v, 0));
        if ((date2 <= PreviousDate)) {
            F_report.report_writeErrorMsg(ERR_RAIN_FILE_SEQUENCE, F_swmm5.Gage[GageIndex].fname);
            F_report.report_writeLine(line);
            return (-1);
        }
        PreviousDate = date2;
        int __sw1 = RainType;
        if (__sw1 == RAINFALL_INTENSITY) {
            x.@v = ((float) (((float) (x.v * (float) (Interval))) / 3600.0f));
        } else if (__sw1 == CUMULATIVE_RAINFALL) {
            if (R.dge(x.v, RainAccum)) {
                x.@v = ((float) (x.v - RainAccum));
                RainAccum = ((float) (RainAccum + x.v));
            } else {
                RainAccum = x.v;
            }
        }
        x.@v = ((float) (x.v * ((float) (UnitsFactor))));
        saveRainfall(date1, hour.v, minute.v, x.v, 0);
        return 1;
    }

    // rain.c:1052
    static int parseStdLine(CPtr line, IRef year, IRef month, IRef day, IRef hour, IRef minute, FRef value) {
        int n = 0;
        CPtr token = R.buf(1024);
        n = R.sscanf(line, L350, token, year, month, day, hour, minute, value);
        if ((n < 7)) {
            return 0;
        }
        if (((StationID != null) && (!(F_swmm5.strcomp(token, StationID) != 0)))) {
            return 0;
        }
        return 1;
    }

    // rain.c:1078
    static void saveAccumRainfall(double date1, int hour, int minute, long v) {
        DBox date2 = new DBox(0.0d);
        int n = 0;
        int j = 0;
        FBox x = new FBox(0.0f);
        if ((AccumStartDate == (-693594))) {
            return;
        }
        date2.@v = (date1 + F_datetime.datetime_encodeTime(hour, minute, 0));
        n = (int) (((R.ldiv(F_datetime.datetime_timeDiff(date2.v, AccumStartDate), Interval)) + 1));
        if ((v == 99999)) {
            RainStats.@periodsMissing = (RainStats.periodsMissing + n);
            return;
        }
        RainStats.@periodsRain = (RainStats.periodsRain + n);
        x.@v = ((float) (((float) (((float) (v)) / ((float) (n)))) / 100.0f));
        if (R.dgt(x.v, 0.0f)) {
            date2.@v = F_datetime.datetime_addSeconds(AccumStartDate, (-TimeOffset));
            if ((RainStats.startDate == (-693594))) {
                RainStats.@startDate = date2.v;
            }
            L_f1: for (j = 0; (j < n); j++) {
                R.fwrite1(date2.v, 8, F_swmm5.Frain.file);
                R.fwrite1(x.v, 4, F_swmm5.Frain.file);
                date2.@v = F_datetime.datetime_addSeconds(date2.v, Interval);
                RainStats.@endDate = date2.v;
            }
        }
        AccumStartDate = (-693594);
    }

    // rain.c:1133
    static void saveRainfall(double date1, int hour, int minute, float __x, int isMissing) {
        FBox x = new FBox(__x);
        DBox date2 = new DBox(0.0d);
        int seconds = 0;
        if ((isMissing != 0)) {
            RainStats.@periodsMissing = RainStats.periodsMissing + 1L;
        } else {
            RainStats.@periodsRain = RainStats.periodsRain + 1L;
        }
        if (!(isMissing != 0)) {
            seconds = (((3600 * hour) + (60 * minute)) - TimeOffset);
            date2.@v = F_datetime.datetime_addSeconds(date1, seconds);
            R.fwrite1(date2.v, 8, F_swmm5.Frain.file);
            R.fwrite1(x.v, 4, F_swmm5.Frain.file);
            if ((RainStats.startDate == (-693594))) {
                RainStats.@startDate = date2.v;
            }
            RainStats.@endDate = date2.v;
        }
    }

}
