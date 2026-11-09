// Generated from EPA SWMM 5.2.4 (table.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_table {
    // table.c:51
    static double table_interpolate(double x, double x1, double y1, double x2, double y2) {
        double dx = (x2 - x1);
        if ((Math.abs(dx) < 1.0e-20d)) {
            return (((y1 + y2)) / 2.0d);
        }
        return (y1 + ((((x - x1)) * ((y2 - y1))) / dx));
    }

    // table.c:67
    static int table_readCurve(CPtr[] tok, int ntoks) {
        int j = 0;
        int m = 0;
        int k = 0;
        int k1 = 1;
        DBox x = new DBox(0.0d);
        DBox y = new DBox(0.0d);
        if ((ntoks < 2)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(CURVE, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        if ((F_swmm5.Curve[j].ID == null)) {
            F_swmm5.Curve[j].@ID = F_project.project_findID(CURVE, tok[0]);
            m = F_input.findmatch(tok[1], F_keywords.CurveTypeWords);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
            }
            F_swmm5.Curve[j].@curveType = m;
            if ((ntoks == 2)) {
                return 0;
            }
            k1 = 2;
        }
        L_f1: for (k = k1; (k < ntoks); k = (k + 2)) {
            if (((k + 1) >= ntoks)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            if (!(F_input.getDouble(tok[k], x) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[k]);
            }
            if (!(F_input.getDouble(tok[(k + 1)], y) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[(k + 1)]);
            }
            table_addEntry(F_swmm5.Curve[j], x.v, y.v);
        }
        return 0;
    }

    // table.c:113
    static int table_readTimeseries(CPtr[] tok, int ntoks) {
        int j = 0;
        int k = 0;
        int state = 0;
        double x = 0.0d;
        DBox y = new DBox(0.0d);
        DBox d = new DBox(0.0d);
        DBox t = new DBox(0.0d);
        CPtr fname = R.buf(260);
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(TSERIES, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        if ((F_swmm5.Tseries[j].ID == null)) {
            F_swmm5.Tseries[j].@ID = F_project.project_findID(TSERIES, tok[0]);
        }
        if ((F_swmm5.strcomp(tok[1], L1) != 0)) {
            Overrides.sstrncpy(fname, tok[2], 259);
            Overrides.sstrncpy(F_swmm5.Tseries[j].file.name, F_swmm5.addAbsolutePath(fname), 259);
            F_swmm5.Tseries[j].file.@mode = USE_FILE;
            return 0;
        }
        x = 0.0d;
        k = 1;
        state = 1;
        L_w1: while ((k < ntoks)) {
            int __sw1 = state;
            if (__sw1 == 1) {
                if ((F_datetime.datetime_strToDate(tok[k], d) != 0)) {
                    F_swmm5.Tseries[j].@lastDate = d.v;
                    k++;
                }
                state = 2;
            } else if (__sw1 == 2) {
                if ((k >= ntoks)) {
                    return F_error.error_setInpError(ERR_ITEMS, L0);
                }
                if ((F_input.getDouble(tok[k], t) != 0)) {
                    t.@v = (t.v / 24.0d);
                } else if (!(F_datetime.datetime_strToTime(tok[k], t) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[k]);
                }
                x = (F_swmm5.Tseries[j].lastDate + t.v);
                k++;
                state = 3;
            } else if (__sw1 == 3) {
                if ((k >= ntoks)) {
                    return F_error.error_setInpError(ERR_ITEMS, L0);
                }
                if (!(F_input.getDouble(tok[k], y) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[k]);
                }
                table_addEntry(F_swmm5.Tseries[j], x, y.v);
                k++;
                state = 1;
            }
        }
        return 0;
    }

    // table.c:208
    static int table_addEntry(TTable table, double x, double y) {
        TTableEntry entry = null;
        entry = new TTableEntry();
        if (!(entry != null)) {
            return 0;
        }
        entry.@x = x;
        entry.@y = y;
        entry.@next = (null);
        if ((table.firstEntry == null)) {
            table.@firstEntry = entry;
        } else {
            table.lastEntry.@next = entry;
        }
        table.@lastEntry = entry;
        return 1;
    }

    // table.c:231
    static void table_deleteEntries(TTable table) {
        TTableEntry entry = null;
        TTableEntry nextEntry = null;
        entry = table.firstEntry;
        L_w1: while ((entry != null)) {
            nextEntry = entry.next;
            R.free(entry);
            entry = nextEntry;
        }
        table.@firstEntry = (null);
        table.@lastEntry = (null);
        table.@thisEntry = (null);
        if ((table.file.file != null)) {
            R.fclose(table.file.file);
            table.file.@file = (null);
        }
    }

    // table.c:260
    static void table_init(TTable table) {
        table.@ID = null;
        table.@refersTo = (-1);
        table.@firstEntry = (null);
        table.@lastEntry = (null);
        table.@thisEntry = table.firstEntry;
        table.@lastDate = 0.0d;
        table.@x1 = 0.0d;
        table.@x2 = 0.0d;
        table.@y1 = 0.0d;
        table.@y2 = 0.0d;
        table.@dxMin = 0.0d;
        table.file.@mode = NO_FILE;
        table.file.@file = (null);
        table.@curveType = (-1);
    }

    // table.c:285
    static int table_validate(TTable table) {
        int result = 0;
        DBox x1 = new DBox(0.0d);
        DBox x2 = new DBox(0.0d);
        DBox y1 = new DBox(0.0d);
        DBox y2 = new DBox(0.0d);
        double dx = 0.0d;
        double dxMin = 10000000000.0d;
        if ((table.file.mode == USE_FILE)) {
            table.file.@file = R.fopen(table.file.name, L13);
            if ((table.file.file == null)) {
                return ERR_TABLE_FILE_OPEN;
            }
        }
        result = table_getFirstEntry(table, x1, y1);
        if (((!(result != 0)) && (table.file.mode == USE_FILE))) {
            return ERR_TABLE_FILE_READ;
        }
        L_w1: while ((table_getNextEntry(table, x2, y2) != 0)) {
            dx = (x2.v - x1.v);
            if ((dx <= 0.0d)) {
                table.@x2 = x2.v;
                return ERR_CURVE_SEQUENCE;
            }
            dxMin = (((((dxMin) <= (dx))) ? (dxMin) : (dx)));
            x1.@v = x2.v;
        }
        table.@dxMin = dxMin;
        if (((table.file.mode == USE_FILE) && (!(R.feof(table.file.file) != 0)))) {
            return ERR_TABLE_FILE_READ;
        }
        return 0;
    }

    // table.c:331
    static int table_getFirstEntry(TTable table, DRef x, DRef y) {
        TTableEntry entry = null;
        x.set(0);
        y.set(0.0d);
        if ((table.file.mode == USE_FILE)) {
            if ((table.file.file == null)) {
                return 0;
            }
            R.rewind(table.file.file);
            return table_getNextFileEntry(table, x, y);
        }
        entry = table.firstEntry;
        if ((entry != null)) {
            x.set(entry.x);
            y.set(entry.y);
            table.@thisEntry = entry;
            return 1;
        } else {
            return 0;
        }
        throw new IllegalStateException('table_getFirstEntry: no return');
    }

    // table.c:366
    static int table_getNextEntry(TTable table, DRef x, DRef y) {
        TTableEntry entry = null;
        if ((table.file.mode == USE_FILE)) {
            return table_getNextFileEntry(table, x, y);
        }
        entry = table.thisEntry.next;
        if ((entry != null)) {
            x.set(entry.x);
            y.set(entry.y);
            table.@thisEntry = entry;
            return 1;
        } else {
            return 0;
        }
        throw new IllegalStateException('table_getNextEntry: no return');
    }

    // table.c:395
    static double table_lookup(TTable table, double x) {
        double x1 = 0.0d;
        double y1 = 0.0d;
        double x2 = 0.0d;
        double y2 = 0.0d;
        TTableEntry entry = null;
        entry = table.firstEntry;
        if ((entry == null)) {
            return 0.0d;
        }
        x1 = entry.x;
        y1 = entry.y;
        if ((x <= x1)) {
            return y1;
        }
        L_w1: while ((entry.next != null)) {
            entry = entry.next;
            x2 = entry.x;
            y2 = entry.y;
            if ((x <= x2)) {
                return table_interpolate(x, x1, y1, x2, y2);
            }
            x1 = x2;
            y1 = y2;
        }
        return y1;
    }

    // table.c:430
    static double table_getSlope(TTable table, double x) {
        double x1 = 0.0d;
        double y1 = 0.0d;
        double x2 = 0.0d;
        double y2 = 0.0d;
        double dx = 0.0d;
        TTableEntry entry = null;
        entry = table.firstEntry;
        if ((entry == null)) {
            return 0.0d;
        }
        x1 = entry.x;
        y1 = entry.y;
        x2 = x1;
        y2 = y1;
        L_w1: while ((entry.next != null)) {
            entry = entry.next;
            x2 = entry.x;
            y2 = entry.y;
            if ((x <= x2)) {
                break L_w1;
            }
            x1 = x2;
            y1 = y2;
        }
        dx = (x2 - x1);
        if ((dx == 0.0d)) {
            return 0.0d;
        }
        return (((y2 - y1)) / dx);
    }

    // table.c:464
    static double table_lookupEx(TTable table, double x) {
        double x1 = 0.0d;
        double y1 = 0.0d;
        double x2 = 0.0d;
        double y2 = 0.0d;
        double s = 0.0d;
        TTableEntry entry = null;
        entry = table.firstEntry;
        if ((entry == null)) {
            return 0.0d;
        }
        x1 = entry.x;
        y1 = entry.y;
        if ((x <= x1)) {
            if (R.dgt(x1, 0.0d)) {
                return ((x / x1) * y1);
            } else {
                return y1;
            }
        }
        L_w1: while ((entry.next != null)) {
            entry = entry.next;
            x2 = entry.x;
            y2 = entry.y;
            if ((x2 != x1)) {
                s = (((y2 - y1)) / ((x2 - x1)));
            }
            if ((x <= x2)) {
                return table_interpolate(x, x1, y1, x2, y2);
            }
            x1 = x2;
            y1 = y2;
        }
        if ((s < 0.0d)) {
            s = 0.0d;
        }
        return (y1 + (s * ((x - x1))));
    }

    // table.c:503
    static double table_intervalLookup(TTable table, double x) {
        TTableEntry entry = null;
        entry = table.firstEntry;
        if ((entry == null)) {
            return 0.0d;
        }
        if ((x < entry.x)) {
            return entry.y;
        }
        L_w1: while ((entry.next != null)) {
            entry = entry.next;
            if ((x < entry.x)) {
                return entry.y;
            }
        }
        return entry.y;
    }

    // table.c:527
    static double table_inverseLookup(TTable table, double y) {
        double x1 = 0.0d;
        double y1 = 0.0d;
        double x2 = 0.0d;
        double y2 = 0.0d;
        TTableEntry entry = null;
        entry = table.firstEntry;
        if ((entry == null)) {
            return 0.0d;
        }
        x1 = entry.x;
        y1 = entry.y;
        if ((y <= y1)) {
            return x1;
        }
        L_w1: while ((entry.next != null)) {
            entry = entry.next;
            x2 = entry.x;
            y2 = entry.y;
            if ((y <= y2)) {
                return table_interpolate(y, y1, x1, y2, x2);
            }
            x1 = x2;
            y1 = y2;
        }
        return x1;
    }

    // table.c:562
    static double table_getMaxY(TTable table, double x) {
        double ymax = 0.0d;
        TTableEntry entry = null;
        entry = table.firstEntry;
        ymax = entry.y;
        L_w1: while (((R.dgt(x, entry.x)) && (entry.next != null))) {
            entry = entry.next;
            if ((entry.y < ymax)) {
                return ymax;
            }
            ymax = entry.y;
        }
        return 0.0d;
    }

    // table.c:587
    static double table_getStorageVolume(TTable table, double x) {
        double a = 0.0d;
        double a1 = 0.0d;
        double x1 = 0.0d;
        double v = 0.0d;
        double dx = 0.0d;
        double dy = 0.0d;
        double s = 0.0d;
        TTableEntry entry = null;
        v = 0.0d;
        entry = table.firstEntry;
        if ((entry == null)) {
            return 0.0d;
        }
        x1 = entry.x;
        a1 = entry.y;
        if ((x <= x1)) {
            if ((x1 < 1.0e-6d)) {
                return 0.0d;
            }
            return (((((a1 / x1)) * x) * x) / 2.0d);
        }
        L_w1: while ((entry.next != null)) {
            entry = entry.next;
            if (R.dge(entry.x, x)) {
                a = table_interpolate(x, x1, a1, entry.x, entry.y);
                return (v + ((((a1 + a)) / 2.0d) * ((x - x1))));
            } else {
                dx = (entry.x - x1);
                dy = (entry.y - a1);
                v = (v + ((((a1 + entry.y)) / 2.0d) * dx));
                x1 = entry.x;
                a1 = entry.y;
            }
        }
        if (R.dgt(dx, 1.0e-6d)) {
            s = (dy / dx);
            a = (a1 + (s * ((x - x1))));
            if ((a < 0.0d)) {
                v = (v - (((a1 * a1) / s) / 2.0d));
            } else {
                v = (v + ((((a1 + a)) / 2.0d) * ((x - x1))));
            }
        }
        return v;
    }

    // table.c:651
    static double table_getStorageDepth(TTable table, double v) {
        double a1 = 0.0d;
        double a2 = 0.0d;
        double d1 = 0.0d;
        double d2 = 0.0d;
        double dd = 0.0d;
        double da = 0.0d;
        double v1 = 0.0d;
        double v2 = 0.0d;
        double s = 0.0d;
        TTableEntry entry = null;
        if ((v == 0.0d)) {
            return 0.0d;
        }
        entry = table.firstEntry;
        if ((entry == null)) {
            return 0.0d;
        }
        d1 = entry.x;
        a1 = entry.y;
        v1 = ((a1 * d1) / 2.0d);
        if ((v <= v1)) {
            if (R.dgt(a1, 0.0d)) {
                return Math.sqrt((((2.0d * v) * d1) / a1));
            } else {
                return 0.0d;
            }
        }
        L_w1: while ((entry.next != null)) {
            entry = entry.next;
            d2 = entry.x;
            a2 = entry.y;
            dd = (d2 - d1);
            da = (a2 - a1);
            v2 = (v1 + ((((a1 + a2)) / 2.0d) * dd));
            if ((v <= v2)) {
                if ((dd <= 0.0d)) {
                    return d1;
                }
                if ((da == 0.0d)) {
                    if ((Math.abs((v2 - v1)) < 1.0e-6d)) {
                        return d1;
                    } else {
                        return (d1 + ((dd * ((v - v1))) / ((v2 - v1))));
                    }
                }
                if ((da < 0.0d)) {
                    d1 = d2;
                    a1 = a2;
                    v1 = v2;
                }
                s = (da / dd);
                return (d1 + (((Math.sqrt(((a1 * a1) + ((2.0d * s) * ((v - v1))))) - a1)) / s));
            }
            d1 = d2;
            a1 = a2;
            v1 = v2;
        }
        if (((dd == 0.0d) || (da == 0.0d))) {
            if (R.dgt(a1, 0.0d)) {
                dd = (((v - v1)) / a1);
            } else {
                dd = 0.0d;
            }
        } else {
            s = (da / dd);
            dd = (((Math.sqrt(((a1 * a1) + ((2.0d * s) * ((v - v1))))) - a1)) / s);
            if ((dd < 0.0d)) {
                dd = 0.0d;
            }
        }
        return (d1 + dd);
    }

    // table.c:730
    static void table_tseriesInit(TTable table) {
        table_getFirstEntry(table, new DField<TTable>(table, (TTable __o) -> __o.x1, (TTable __o, double __x) -> { __o.@x1 = __x }), new DField<TTable>(table, (TTable __o) -> __o.y1, (TTable __o, double __x) -> { __o.@y1 = __x }));
        table.@x2 = table.x1;
        table.@y2 = table.y1;
        table_getNextEntry(table, new DField<TTable>(table, (TTable __o) -> __o.x2, (TTable __o, double __x) -> { __o.@x2 = __x }), new DField<TTable>(table, (TTable __o) -> __o.y2, (TTable __o, double __x) -> { __o.@y2 = __x }));
    }

    // table.c:745
    static double table_tseriesLookup(TTable table, double x, int extend) {
        if ((((table.x1 <= x) && (R.dge(table.x2, x))) && (table.x1 != table.x2))) {
            return table_interpolate(x, table.x1, table.y1, table.x2, table.y2);
        }
        if (((table.file.mode == USE_FILE) && (R.feof(table.file.file) != 0))) {
            if ((extend == 1)) {
                return table.y1;
            } else {
                return 0;
            }
        }
        if (((table.x1 == table.x2) || (x < table.x1))) {
            table_getFirstEntry(table, new DField<TTable>(table, (TTable __o) -> __o.x1, (TTable __o, double __x) -> { __o.@x1 = __x }), new DField<TTable>(table, (TTable __o) -> __o.y1, (TTable __o, double __x) -> { __o.@y1 = __x }));
            if ((x < table.x1)) {
                if ((extend == 1)) {
                    return table.y1;
                } else {
                    return 0;
                }
            }
        }
        table.@x1 = table.x2;
        table.@y1 = table.y2;
        L_w1: while ((table_getNextEntry(table, new DField<TTable>(table, (TTable __o) -> __o.x2, (TTable __o, double __x) -> { __o.@x2 = __x }), new DField<TTable>(table, (TTable __o) -> __o.y2, (TTable __o, double __x) -> { __o.@y2 = __x })) != 0)) {
            if ((x <= table.x2)) {
                return table_interpolate(x, table.x1, table.y1, table.x2, table.y2);
            }
            table.@x1 = table.x2;
            table.@y1 = table.y2;
        }
        if ((extend == 1)) {
            return table.y1;
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('table_tseriesLookup: no return');
    }

    // table.c:808
    static int table_getNextFileEntry(TTable table, DRef x, DRef y) {
        CPtr line = R.buf(1025);
        int code = 0;
        if ((table.file.file == null)) {
            return 0;
        }
        L_w1: while (((!(R.feof(table.file.file) != 0)) && ((R.fgets(line, (int) (1024), table.file.file)) != null))) {
            code = table_parseFileLine(line, table, x, y);
            if ((code < 0)) {
                continue L_w1;
            }
            return code;
        }
        return 0;
    }

    // table.c:833
    static int table_parseFileLine(CPtr line, TTable table, DRef x, DRef y) {
        int n = 0;
        CPtr s1 = R.buf(50);
        CPtr s2 = R.buf(50);
        CPtr s3 = R.buf(50);
        CPtr tStr = null;
        CPtr yStr = null;
        DBox yy = new DBox(0.0d);
        DBox d = new DBox(0.0d);
        DBox t = new DBox(0.0d);
        n = R.sscanf(line, L650, s1, s2, s3);
        tStr = R.strtok(line, L184);
        if (((tStr == null) || (tStr.g(0) == 59))) {
            return (-1);
        }
        if ((n == 2)) {
            d.@v = table.lastDate;
            tStr = s1;
            yStr = s2;
        } else if ((n == 3)) {
            if (!(F_datetime.datetime_strToDate(s1, d) != 0)) {
                return 0;
            }
            table.@lastDate = d.v;
            tStr = s2;
            yStr = s3;
        } else {
            return 0;
        }
        if ((F_input.getDouble(tStr, t) != 0)) {
            t.@v = (t.v / 24.0d);
        } else if (!(F_datetime.datetime_strToTime(tStr, t) != 0)) {
            return 0;
        }
        if (!(F_input.getDouble(yStr, yy) != 0)) {
            return 0;
        }
        x.set((d.v + t.v));
        y.set(yy.v);
        return 1;
    }

}
