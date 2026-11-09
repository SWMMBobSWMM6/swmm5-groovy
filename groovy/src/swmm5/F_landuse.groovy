// Generated from EPA SWMM 5.2.4 (landuse.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_landuse {
    // landuse.c:51
    static int landuse_readParams(int j, CPtr[] tok, int ntoks) {
        CPtr id = null;
        if ((ntoks < 1)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(LANDUSE, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        F_swmm5.Landuse[j].@ID = id;
        if ((ntoks > 1)) {
            if ((ntoks < 4)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            if (!(F_input.getDouble(tok[1], new DField<TLanduse>(F_swmm5.Landuse[j], (TLanduse __o) -> __o.sweepInterval, (TLanduse __o, double __x) -> { __o.@sweepInterval = __x })) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[1]);
            }
            if (!(F_input.getDouble(tok[2], new DField<TLanduse>(F_swmm5.Landuse[j], (TLanduse __o) -> __o.sweepRemoval, (TLanduse __o, double __x) -> { __o.@sweepRemoval = __x })) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[2]);
            }
            if (!(F_input.getDouble(tok[3], new DField<TLanduse>(F_swmm5.Landuse[j], (TLanduse __o) -> __o.sweepDays0, (TLanduse __o, double __x) -> { __o.@sweepDays0 = __x })) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[3]);
            }
        } else {
            F_swmm5.Landuse[j].@sweepInterval = 0.0d;
            F_swmm5.Landuse[j].@sweepRemoval = 0.0d;
            F_swmm5.Landuse[j].@sweepDays0 = 0.0d;
        }
        if (((F_swmm5.Landuse[j].sweepRemoval < 0.0d) || (R.dgt(F_swmm5.Landuse[j].sweepRemoval, 1.0d)))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[2]);
        }
        return 0;
    }

    // landuse.c:92
    static int landuse_readPollutParams(int j, CPtr[] tok, int ntoks) {
        int i = 0;
        int k = 0;
        int coPollut = 0;
        int snowFlag = 0;
        double[] x = new double[4];
        DBox coFrac = new DBox(0.0d);
        DBox cDWF = new DBox(0.0d);
        DBox cInit = new DBox(0.0d);
        CPtr id = null;
        if ((ntoks < 6)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(POLLUT, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        k = F_input.findmatch(tok[1], F_keywords.QualUnitsWords);
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
        }
        L_f1: for (i = 2; (i <= 4); i++) {
            if (((!(F_input.getDouble(tok[i], new DElem(x, (i - 2))) != 0)) || (x[(i - 2)] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[i]);
            }
        }
        if (!(F_input.getDouble(tok[5], new DElem(x, 3)) != 0)) {
            return F_error.error_setInpError(ERR_NUMBER, tok[5]);
        }
        snowFlag = 0;
        coPollut = (-1);
        coFrac.@v = 0.0d;
        cDWF.@v = 0.0d;
        cInit.@v = 0.0d;
        if ((ntoks >= 7)) {
            snowFlag = F_input.findmatch(tok[6], F_keywords.NoYesWords);
            if ((snowFlag < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[6]);
            }
        }
        if ((ntoks >= 9)) {
            if (!(F_swmm5.strcomp(tok[7], L275) != 0)) {
                coPollut = F_project.project_findObject(POLLUT, tok[7]);
                if ((coPollut < 0)) {
                    return F_error.error_setInpError(ERR_NAME, tok[7]);
                }
                if (((!(F_input.getDouble(tok[8], coFrac) != 0)) || (coFrac.v < 0.0d))) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[8]);
                }
            }
        }
        if ((ntoks >= 10)) {
            if (((!(F_input.getDouble(tok[9], cDWF) != 0)) || (cDWF.v < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[9]);
            }
        }
        if ((ntoks >= 11)) {
            if (((!(F_input.getDouble(tok[10], cInit) != 0)) || (cInit.v < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[9]);
            }
        }
        F_swmm5.Pollut[j].@ID = id;
        F_swmm5.Pollut[j].@units = k;
        if ((F_swmm5.Pollut[j].units == MG)) {
            F_swmm5.Pollut[j].@mcf = F_swmm5.UCF(MASS);
        } else if ((F_swmm5.Pollut[j].units == UG)) {
            F_swmm5.Pollut[j].@mcf = (F_swmm5.UCF(MASS) / 1000.0d);
        } else {
            F_swmm5.Pollut[j].@mcf = 1.0d;
        }
        F_swmm5.Pollut[j].@pptConcen = x[0];
        F_swmm5.Pollut[j].@gwConcen = x[1];
        F_swmm5.Pollut[j].@rdiiConcen = x[2];
        F_swmm5.Pollut[j].@kDecay = (x[3] / 86400);
        F_swmm5.Pollut[j].@snowOnly = snowFlag;
        F_swmm5.Pollut[j].@coPollut = coPollut;
        F_swmm5.Pollut[j].@coFraction = coFrac.v;
        F_swmm5.Pollut[j].@dwfConcen = cDWF.v;
        F_swmm5.Pollut[j].@initConcen = cInit.v;
        return 0;
    }

    // landuse.c:190
    static int landuse_readBuildupParams(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int k = 0;
        int n = 0;
        int p = 0;
        double[] c = new double[] { 0, 0, 0 };
        double tmax = 0.0d;
        if ((ntoks < 3)) {
            return 0;
        }
        j = F_project.project_findObject(LANDUSE, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        p = F_project.project_findObject(POLLUT, tok[1]);
        if ((p < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[1]);
        }
        k = F_input.findmatch(tok[2], F_keywords.BuildupTypeWords);
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[2]);
        }
        F_swmm5.Landuse[j].buildupFunc[p].@funcType = k;
        if ((k > NO_BUILDUP)) {
            if ((ntoks < 7)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            if ((k != EXTERNAL_BUILDUP)) {
                L_f1: for (i = 0; (i < 3); i++) {
                    if (((!(F_input.getDouble(tok[(i + 3)], new DElem(c, i)) != 0)) || (c[i] < 0.0d))) {
                        return F_error.error_setInpError(ERR_NUMBER, tok[(i + 3)]);
                    }
                }
            }
            n = F_input.findmatch(tok[6], F_keywords.NormalizerWords);
            if ((n < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[6]);
            }
            F_swmm5.Landuse[j].buildupFunc[p].@normalizer = n;
        }
        int __sw1 = F_swmm5.Landuse[j].buildupFunc[p].funcType;
        if (__sw1 == POWER_BUILDUP) {
            if (((R.dgt(c[2], 0.0d)) && (((c[2] < 0.01d) || (R.dgt(c[2], 10.0d)))))) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[5]);
            }
            if (((c[1] * c[2]) == 0.0d)) {
                tmax = 0.0d;
            } else if (R.dgt((Glibm.log10(c[0]) / c[2]), 3.5d)) {
                tmax = 3650.0d;
            } else {
                tmax = Glibm.pow((c[0] / c[1]), (1.0d / c[2]));
            }
        } else if (__sw1 == EXPON_BUILDUP) {
            if ((c[1] == 0.0d)) {
                tmax = 0.0d;
            } else {
                tmax = ((-(Glibm.log(0.001d))) / c[1]);
            }
        } else if (__sw1 == SATUR_BUILDUP) {
            tmax = (1000.0d * c[2]);
        } else if (__sw1 == EXTERNAL_BUILDUP) {
            if (((!(F_input.getDouble(tok[3], new DElem(c, 0)) != 0)) || (c[0] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[3]);
            }
            if (((!(F_input.getDouble(tok[4], new DElem(c, 1)) != 0)) || (c[1] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[3]);
            }
            n = F_project.project_findObject(TSERIES, tok[5]);
            if ((n < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[4]);
            }
            F_swmm5.Tseries[n].@refersTo = EXTERNAL_BUILDUP;
            c[2] = n;
            tmax = 0.0d;
        } else {
            tmax = 0.0d;
        }
        F_swmm5.Landuse[j].buildupFunc[p].coeff[0] = c[0];
        F_swmm5.Landuse[j].buildupFunc[p].coeff[1] = c[1];
        F_swmm5.Landuse[j].buildupFunc[p].coeff[2] = c[2];
        F_swmm5.Landuse[j].buildupFunc[p].@maxDays = tmax;
        return 0;
    }

    // landuse.c:281
    static int landuse_readWashoffParams(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int p = 0;
        int func = 0;
        double[] x = new double[4];
        if ((ntoks < 3)) {
            return 0;
        }
        L_f1: for (i = 0; (i < 4); i++) {
            x[i] = 0.0d;
        }
        func = NO_WASHOFF;
        j = F_project.project_findObject(LANDUSE, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        p = F_project.project_findObject(POLLUT, tok[1]);
        if ((p < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[1]);
        }
        if ((ntoks > 2)) {
            func = F_input.findmatch(tok[2], F_keywords.WashoffTypeWords);
            if ((func < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[2]);
            }
            if ((func != NO_WASHOFF)) {
                if ((ntoks < 5)) {
                    return F_error.error_setInpError(ERR_ITEMS, L0);
                }
                if (!(F_input.getDouble(tok[3], new DElem(x, 0)) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[3]);
                }
                if (!(F_input.getDouble(tok[4], new DElem(x, 1)) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[4]);
                }
                if ((ntoks >= 6)) {
                    if (!(F_input.getDouble(tok[5], new DElem(x, 2)) != 0)) {
                        return F_error.error_setInpError(ERR_NUMBER, tok[5]);
                    }
                }
                if ((ntoks >= 7)) {
                    if (!(F_input.getDouble(tok[6], new DElem(x, 3)) != 0)) {
                        return F_error.error_setInpError(ERR_NUMBER, tok[6]);
                    }
                }
            }
        }
        if ((x[0] < 0.0d)) {
            return F_error.error_setInpError(ERR_NUMBER, tok[3]);
        }
        if (((x[1] < (-10.0d)) || (R.dgt(x[1], 10.0d)))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[4]);
        }
        if (((x[2] < 0.0d) || (R.dgt(x[2], 100.0d)))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[5]);
        }
        if (((x[3] < 0.0d) || (R.dgt(x[3], 100.0d)))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[6]);
        }
        if ((func == EXPON_WASHOFF)) {
            x[0] = (x[0] / 3600.0d);
        }
        if ((func == RATING_WASHOFF)) {
            x[0] *= (Glibm.pow(F_swmm5.UCF(FLOW), x[1]));
        }
        if ((func == EMC_WASHOFF)) {
            x[0] *= 28.317d;
        }
        F_swmm5.Landuse[j].washoffFunc[p].@funcType = func;
        F_swmm5.Landuse[j].washoffFunc[p].@coeff = x[0];
        F_swmm5.Landuse[j].washoffFunc[p].@expon = x[1];
        F_swmm5.Landuse[j].washoffFunc[p].@sweepEffic = (x[2] / 100.0d);
        F_swmm5.Landuse[j].washoffFunc[p].@bmpEffic = (x[3] / 100.0d);
        return 0;
    }

    // landuse.c:355
    static void landuse_getInitBuildup(TLandFactor[] landFactor, double[] initBuildup, double area, double curb) {
        int i = 0;
        int p = 0;
        double startDrySeconds = 0.0d;
        double f = 0.0d;
        double fArea = 0.0d;
        double fCurb = 0.0d;
        double buildup = 0.0d;
        startDrySeconds = (F_swmm5.StartDryDays * 86400);
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LANDUSE]); i++) {
            landFactor[i].@lastSwept = (F_swmm5.StartDateTime - F_swmm5.Landuse[i].sweepDays0);
            f = landFactor[i].fraction;
            fArea = ((f * area) * F_swmm5.UCF(LANDAREA));
            fCurb = (f * curb);
            L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                buildup = 0.0d;
                if (R.dgt(initBuildup[p], 0.0d)) {
                    buildup = (initBuildup[p] * fArea);
                } else {
                    buildup = landuse_getBuildup(i, p, fArea, fCurb, buildup, startDrySeconds);
                }
                landFactor[i].buildup[p] = buildup;
            }
        }
    }

    // landuse.c:411
    static double landuse_getBuildup(int i, int p, double area, double curb, double buildup, double tStep) {
        int n = 0;
        double days = 0.0d;
        double perUnit = 0.0d;
        if (((F_swmm5.Landuse[i].buildupFunc[p].funcType == NO_BUILDUP) || (tStep == 0.0d))) {
            return buildup;
        }
        n = F_swmm5.Landuse[i].buildupFunc[p].normalizer;
        perUnit = 1.0d;
        if ((n == PER_AREA)) {
            perUnit = area;
        }
        if ((n == PER_CURB)) {
            perUnit = curb;
        }
        if ((perUnit == 0.0d)) {
            return 0.0d;
        }
        if ((F_swmm5.Landuse[i].buildupFunc[p].funcType == EXTERNAL_BUILDUP)) {
            return (landuse_getExternalBuildup(i, p, (buildup / perUnit), tStep) * perUnit);
        }
        days = landuse_getBuildupDays(i, p, (buildup / perUnit));
        days += (tStep / 86400);
        return (landuse_getBuildupMass(i, p, days) * perUnit);
    }

    // landuse.c:458
    static double landuse_getBuildupDays(int i, int p, double buildup) {
        double c0 = F_swmm5.Landuse[i].buildupFunc[p].coeff[0];
        double c1 = F_swmm5.Landuse[i].buildupFunc[p].coeff[1];
        double c2 = F_swmm5.Landuse[i].buildupFunc[p].coeff[2];
        if ((buildup == 0.0d)) {
            return 0.0d;
        }
        if (R.dge(buildup, c0)) {
            return F_swmm5.Landuse[i].buildupFunc[p].maxDays;
        }
        int __sw1 = F_swmm5.Landuse[i].buildupFunc[p].funcType;
        if (__sw1 == POWER_BUILDUP) {
            if (((c1 * c2) == 0.0d)) {
                return 0.0d;
            } else {
                return Glibm.pow(((buildup / c1)), ((1.0d / c2)));
            }
        } else if (__sw1 == EXPON_BUILDUP) {
            if (((c0 * c1) == 0.0d)) {
                return 0.0d;
            } else {
                return ((-(Glibm.log((1.0d - (buildup / c0))))) / c1);
            }
        } else if (__sw1 == SATUR_BUILDUP) {
            if ((c0 == 0.0d)) {
                return 0.0d;
            } else {
                return ((buildup * c2) / ((c0 - buildup)));
            }
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('landuse_getBuildupDays: no return');
    }

    // landuse.c:494
    static double landuse_getBuildupMass(int i, int p, double days) {
        double b = 0.0d;
        double c0 = F_swmm5.Landuse[i].buildupFunc[p].coeff[0];
        double c1 = F_swmm5.Landuse[i].buildupFunc[p].coeff[1];
        double c2 = F_swmm5.Landuse[i].buildupFunc[p].coeff[2];
        if ((days == 0.0d)) {
            return 0.0d;
        }
        if (R.dge(days, F_swmm5.Landuse[i].buildupFunc[p].maxDays)) {
            return c0;
        }
        int __sw1 = F_swmm5.Landuse[i].buildupFunc[p].funcType;
        if (__sw1 == POWER_BUILDUP) {
            b = (c1 * Glibm.pow(days, c2));
            if (R.dgt(b, c0)) {
                b = c0;
            }
        } else if (__sw1 == EXPON_BUILDUP) {
            b = (c0 * ((1.0d - Glibm.exp(((-days) * c1)))));
        } else if (__sw1 == SATUR_BUILDUP) {
            b = ((days * c0) / ((c2 + days)));
        } else {
            b = 0.0d;
        }
        return b;
    }

    // landuse.c:532
    static double landuse_getAvgBmpEffic(int j, int p) {
        int i = 0;
        double r = 0.0d;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LANDUSE]); i++) {
            r += (F_swmm5.Subcatch[j].landFactor[i].fraction * F_swmm5.Landuse[i].washoffFunc[p].bmpEffic);
        }
        return r;
    }

    // landuse.c:553
    static double landuse_getWashoffLoad(int i, int p, double area, TLandFactor[] landFactor, double runoff, double vOutflow) {
        double landuseArea = 0.0d;
        double buildup = 0.0d;
        double washoffQual = 0.0d;
        double washoffLoad = 0.0d;
        double bmpRemoval = 0.0d;
        buildup = landFactor[i].buildup[p];
        landuseArea = (landFactor[i].fraction * area);
        washoffQual = landuse_getWashoffQual(i, p, buildup, runoff, landuseArea);
        washoffLoad = ((((washoffQual * vOutflow) * landuseArea) / area) * F_swmm5.Pollut[p].mcf);
        if (((F_swmm5.Landuse[i].buildupFunc[p].funcType != NO_BUILDUP) || (R.dgt(buildup, washoffLoad)))) {
            washoffLoad = (((((washoffLoad) <= (buildup))) ? (washoffLoad) : (buildup)));
            buildup -= washoffLoad;
            landFactor[i].buildup[p] = buildup;
        } else {
            F_massbal.massbal_updateLoadingTotals(BUILDUP_LOAD, p, washoffLoad);
            landFactor[i].buildup[p] = 0.0d;
        }
        bmpRemoval = (F_swmm5.Landuse[i].washoffFunc[p].bmpEffic * washoffLoad);
        if (R.dgt(bmpRemoval, 0.0d)) {
            F_massbal.massbal_updateLoadingTotals(BMP_REMOVAL_LOAD, p, bmpRemoval);
            washoffLoad -= bmpRemoval;
        }
        return (washoffLoad / F_swmm5.Pollut[p].mcf);
    }

    // landuse.c:612
    static double landuse_getWashoffQual(int i, int p, double buildup, double runoff, double area) {
        double cWashoff = 0.0d;
        double coeff = F_swmm5.Landuse[i].washoffFunc[p].coeff;
        double expon = F_swmm5.Landuse[i].washoffFunc[p].expon;
        int func = F_swmm5.Landuse[i].washoffFunc[p].funcType;
        if (((func == NO_WASHOFF) || (runoff == 0.0d))) {
            return 0.0d;
        }
        if (((F_swmm5.Landuse[i].buildupFunc[p].funcType != NO_BUILDUP) && (buildup == 0.0d))) {
            return 0.0d;
        }
        if ((func == EXPON_WASHOFF)) {
            cWashoff = (((coeff * Glibm.pow((runoff * F_swmm5.UCF(RAINFALL)), expon)) * buildup) / F_swmm5.Pollut[p].mcf);
            cWashoff = (cWashoff / (runoff * area));
        } else if ((func == RATING_WASHOFF)) {
            cWashoff = (coeff * Glibm.pow((runoff * area), (expon - 1.0d)));
        } else if ((func == EMC_WASHOFF)) {
            cWashoff = coeff;
        }
        return cWashoff;
    }

    // landuse.c:665
    static double landuse_getCoPollutLoad(int p, double[] washoff) {
        int k = 0;
        double w = 0.0d;
        k = F_swmm5.Pollut[p].coPollut;
        if ((k >= 0)) {
            w = (F_swmm5.Pollut[p].coFraction * washoff[k]);
            F_massbal.massbal_updateLoadingTotals(BUILDUP_LOAD, p, (w * F_swmm5.Pollut[p].mcf));
            return w;
        }
        return 0.0d;
    }

    // landuse.c:693
    static double landuse_getExternalBuildup(int i, int p, double buildup, double tStep) {
        double maxBuildup = F_swmm5.Landuse[i].buildupFunc[p].coeff[0];
        double sf = F_swmm5.Landuse[i].buildupFunc[p].coeff[1];
        int ts = R.D2I(Math.floor(F_swmm5.Landuse[i].buildupFunc[p].coeff[2]));
        double rate = 0.0d;
        if ((F_swmm5.NewRunoffTime == 0.0d)) {
            return 0.0d;
        }
        if ((ts >= 0)) {
            rate = (sf * F_table.table_tseriesLookup(F_swmm5.Tseries[ts], F_swmm5.getDateTime(F_swmm5.NewRunoffTime), 0));
        }
        buildup = (buildup + ((rate * tStep) / 86400));
        buildup = (((((buildup) <= (maxBuildup))) ? (buildup) : (maxBuildup)));
        return buildup;
    }

}
