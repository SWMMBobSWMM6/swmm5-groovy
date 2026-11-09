// Generated from EPA SWMM 5.2.4 (inflow.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_inflow {
    // inflow.c:41
    static int inflow_readExtInflow(CPtr[] tok, int ntoks) {
        int j = 0;
        int param = 0;
        int type = CONCEN_INFLOW;
        int tseries = (-1);
        int basePat = (-1);
        DBox cf = new DBox(1.0d);
        DBox sf = new DBox(1.0d);
        DBox baseline = new DBox(0.0d);
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(NODE, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        param = F_project.project_findObject(POLLUT, tok[1]);
        if ((param < 0)) {
            if ((F_input.match(tok[1], L183) != 0)) {
                param = (-1);
            } else {
                return F_error.error_setInpError(ERR_NAME, tok[1]);
            }
        }
        if ((R.strlen(tok[2]) > 0)) {
            tseries = F_project.project_findObject(TSERIES, tok[2]);
            if ((tseries < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
            F_swmm5.Tseries[tseries].@refersTo = EXTERNAL_INFLOW;
        }
        if ((param == (-1))) {
            type = FLOW_INFLOW;
            cf.@v = (1.0d / F_swmm5.UCF(FLOW));
        }
        if (((ntoks >= 4) && (param > (-1)))) {
            if ((F_input.match(tok[3], L185) != 0)) {
                type = CONCEN_INFLOW;
            } else if ((F_input.match(tok[3], L186) != 0)) {
                type = MASS_INFLOW;
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, tok[3]);
            }
            if (((ntoks >= 5) && (type == MASS_INFLOW))) {
                if (!(F_input.getDouble(tok[4], cf) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[4]);
                }
                if ((cf.v <= 0.0d)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[4]);
                }
            }
        }
        if ((ntoks >= 6)) {
            if (!(F_input.getDouble(tok[5], sf) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[5]);
            }
        }
        if ((ntoks >= 7)) {
            if (!(F_input.getDouble(tok[6], baseline) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[6]);
            }
        }
        if ((ntoks >= 8)) {
            basePat = F_project.project_findObject(TIMEPATTERN, tok[7]);
            if ((basePat < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[7]);
            }
        }
        if ((type == MASS_INFLOW)) {
            cf.@v = (cf.v / 28.317d);
        }
        return (inflow_setExtInflow(j, param, type, tseries, basePat, cf.v, baseline.v, sf.v));
    }

    // inflow.c:138
    static int inflow_setExtInflow(int j, int param, int type, int tseries, int basePat, double cf, double baseline, double sf) {
        TExtInflow inflow = null;
        inflow = F_swmm5.Node_[j].extInflow;
        L_w1: while ((inflow != null)) {
            if ((inflow.param == param)) {
                break L_w1;
            }
            inflow = inflow.next;
        }
        if ((inflow == null)) {
            inflow = new TExtInflow();
            if ((inflow == null)) {
                return F_error.error_setInpError(ERR_MEMORY, L0);
            }
            inflow.@next = F_swmm5.Node_[j].extInflow;
            F_swmm5.Node_[j].@extInflow = inflow;
        }
        inflow.@param = param;
        inflow.@type = type;
        inflow.@tSeries = tseries;
        inflow.@cFactor = cf;
        inflow.@sFactor = sf;
        inflow.@baseline = baseline;
        inflow.@basePat = basePat;
        return 0;
    }

    // inflow.c:187
    static void inflow_deleteExtInflows(int j) {
        TExtInflow inflow1 = null;
        TExtInflow inflow2 = null;
        inflow1 = F_swmm5.Node_[j].extInflow;
        L_w1: while ((inflow1 != null)) {
            inflow2 = inflow1.next;
            R.free(inflow1);
            inflow1 = inflow2;
        }
    }

    // inflow.c:207
    static double inflow_getExtInflow(TExtInflow inflow, double aDate) {
        int month = 0;
        int day = 0;
        int hour = 0;
        int p = inflow.basePat;
        int k = inflow.tSeries;
        double cf = inflow.cFactor;
        double sf = inflow.sFactor;
        double blv = inflow.baseline;
        double tsv = 0.0d;
        if ((p >= 0)) {
            month = (F_datetime.datetime_monthOfYear(aDate) - 1);
            day = (F_datetime.datetime_dayOfWeek(aDate) - 1);
            hour = F_datetime.datetime_hourOfDay(aDate);
            blv *= (getPatternFactor(p, month, day, hour));
        }
        if ((k >= 0)) {
            tsv = (F_table.table_tseriesLookup(F_swmm5.Tseries[k], aDate, 0) * sf);
        }
        return (cf * ((tsv + blv)));
    }

    // inflow.c:237
    static int inflow_readDwfInflow(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int[] pats = new int[4];
        DBox x = new DBox(0.0d);
        TDwfInflow inflow = null;
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(NODE, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        k = F_project.project_findObject(POLLUT, tok[1]);
        if ((k < 0)) {
            if ((F_input.match(tok[1], L183) != 0)) {
                k = (-1);
            } else {
                return F_error.error_setInpError(ERR_NAME, tok[1]);
            }
        }
        if (!(F_input.getDouble(tok[2], x) != 0)) {
            return F_error.error_setInpError(ERR_NUMBER, tok[2]);
        }
        if ((k == (-1))) {
            x.@v = (x.v / (F_swmm5.UCF(FLOW)));
        }
        L_f1: for (i = 0; (i < 4); i++) {
            pats[i] = (-1);
        }
        L_f2: for (i = 3; (i < 7); i++) {
            if ((i >= ntoks)) {
                break L_f2;
            }
            if ((R.strlen(tok[i]) == 0)) {
                continue L_f2;
            }
            m = F_project.project_findObject(TIMEPATTERN, tok[i]);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[i]);
            }
            pats[(i - 3)] = m;
        }
        inflow = F_swmm5.Node_[j].dwfInflow;
        L_w3: while ((inflow != null)) {
            if ((inflow.param == k)) {
                break L_w3;
            }
            inflow = inflow.next;
        }
        if ((inflow == null)) {
            inflow = new TDwfInflow();
            if ((inflow == null)) {
                return F_error.error_setInpError(ERR_MEMORY, L0);
            }
            inflow.@next = F_swmm5.Node_[j].dwfInflow;
            F_swmm5.Node_[j].@dwfInflow = inflow;
        }
        inflow.@param = k;
        inflow.@avgValue = x.v;
        L_f4: for (i = 0; (i < 4); i++) {
            inflow.patterns[i] = pats[i];
        }
        return 0;
    }

    // inflow.c:311
    static void inflow_deleteDwfInflows(int j) {
        TDwfInflow inflow1 = null;
        TDwfInflow inflow2 = null;
        inflow1 = F_swmm5.Node_[j].dwfInflow;
        L_w1: while ((inflow1 != null)) {
            inflow2 = inflow1.next;
            R.free(inflow1);
            inflow1 = inflow2;
        }
    }

    // inflow.c:331
    static void inflow_initDwfInflow(TDwfInflow inflow) {
        int i = 0;
        int p = 0;
        int[] tmpPattern = new int[4];
        L_f1: for (i = 0; (i < 4); i++) {
            tmpPattern[i] = (-1);
        }
        L_f2: for (i = 0; (i < 4); i++) {
            p = inflow.patterns[i];
            if ((p >= 0)) {
                tmpPattern[F_swmm5.Pattern[p].type] = p;
            }
        }
        L_f3: for (i = 0; (i < 4); i++) {
            inflow.patterns[i] = tmpPattern[i];
        }
    }

    // inflow.c:361
    static double inflow_getDwfInflow(TDwfInflow inflow, int month, int day, int hour) {
        int p1 = 0;
        int p2 = 0;
        double f = 1.0d;
        p1 = inflow.patterns[MONTHLY_PATTERN];
        if ((p1 >= 0)) {
            f *= (getPatternFactor(p1, month, day, hour));
        }
        p1 = inflow.patterns[DAILY_PATTERN];
        if ((p1 >= 0)) {
            f *= (getPatternFactor(p1, month, day, hour));
        }
        p1 = inflow.patterns[HOURLY_PATTERN];
        p2 = inflow.patterns[WEEKEND_PATTERN];
        if ((p2 >= 0)) {
            if (((day == 0) || (day == 6))) {
                f *= (getPatternFactor(p2, month, day, hour));
            } else if ((p1 >= 0)) {
                f *= (getPatternFactor(p1, month, day, hour));
            }
        } else if ((p1 >= 0)) {
            f *= (getPatternFactor(p1, month, day, hour));
        }
        return (f * inflow.avgValue);
    }

    // inflow.c:394
    static void inflow_initDwfPattern(int j) {
        int i = 0;
        L_f1: for (i = 0; (i < 24); i++) {
            F_swmm5.Pattern[j].factor[i] = 1.0d;
        }
        F_swmm5.Pattern[j].@count = 0;
        F_swmm5.Pattern[j].@type = (-1);
        F_swmm5.Pattern[j].@ID = null;
    }

    // inflow.c:410
    static int inflow_readDwfPattern(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int k = 0;
        int n = 1;
        if ((ntoks < 2)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(TIMEPATTERN, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        if ((F_swmm5.Pattern[j].ID == null)) {
            F_swmm5.Pattern[j].@ID = F_project.project_findID(TIMEPATTERN, tok[0]);
            k = F_input.findmatch(tok[1], F_keywords.PatternTypeWords);
            if ((k < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
            }
            F_swmm5.Pattern[j].@type = k;
            n = 2;
        }
        L_w1: while (((ntoks > n) && (F_swmm5.Pattern[j].count < 24))) {
            i = F_swmm5.Pattern[j].count;
            if (!(F_input.getDouble(tok[n], new DElem(F_swmm5.Pattern[j].factor, i)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[n]);
            }
            F_swmm5.Pattern[j].@count = F_swmm5.Pattern[j].count + 1;
            n++;
        }
        return 0;
    }

    // inflow.c:456
    static double getPatternFactor(int p, int month, int day, int hour) {
        int __sw1 = F_swmm5.Pattern[p].type;
        if (__sw1 == MONTHLY_PATTERN) {
            if (((month >= 0) && (month < 12))) {
                return F_swmm5.Pattern[p].factor[month];
            }
        } else if (__sw1 == DAILY_PATTERN) {
            if (((day >= 0) && (day < 7))) {
                return F_swmm5.Pattern[p].factor[day];
            }
        } else if (__sw1 == HOURLY_PATTERN) {
            if (((hour >= 0) && (hour < 24))) {
                return F_swmm5.Pattern[p].factor[hour];
            }
        } else if (__sw1 == WEEKEND_PATTERN) {
            if (((day == 0) || (day == 6))) {
                if (((hour >= 0) && (hour < 24))) {
                    return F_swmm5.Pattern[p].factor[hour];
                }
            }
        }
        return 1.0d;
    }

}
