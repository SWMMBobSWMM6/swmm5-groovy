// Generated from EPA SWMM 5.2.4 (snow.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_snow {
    // snow.c:68
    static int snow_readMeltParams(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int n = 0;
        double[] x = new double[7];
        if ((ntoks < 8)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(SNOWMELT, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        if ((F_swmm5.Snowmelt[j].ID == null)) {
            F_swmm5.Snowmelt[j].@ID = F_project.project_findID(SNOWMELT, tok[0]);
        }
        k = F_input.findmatch(tok[1], F_keywords.SnowmeltWords);
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
        }
        n = 7;
        if ((k == SNOW_REMOVAL)) {
            n = 6;
        }
        if ((ntoks < (n + 2))) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        L_f1: for (i = 0; (i < 7); i++) {
            x[i] = 0.0d;
        }
        L_f2: for (i = 0; (i < n); i++) {
            if (!(F_input.getDouble(tok[(i + 2)], new DElem(x, i)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[(i + 2)]);
            }
        }
        if ((k == SNOW_REMOVAL)) {
            x[6] = (-1.0d);
            if ((ntoks >= 9)) {
                m = F_project.project_findObject(SUBCATCH, tok[8]);
                if ((m < 0)) {
                    return F_error.error_setInpError(ERR_NAME, tok[8]);
                }
                x[6] = m;
            }
        }
        setMeltParams(j, k, x);
        return 0;
    }

    // snow.c:126
    static int snow_createSnowpack(int j, int k) {
        TSnowpack snowpack = null;
        snowpack = new TSnowpack();
        if (!(snowpack != null)) {
            return 0;
        }
        F_swmm5.Subcatch[j].@snowpack = snowpack;
        snowpack.@snowmeltIndex = k;
        return 1;
    }

    // snow.c:144
    static void snow_initSnowpack(int j) {
        int i = 0;
        int k = 0;
        double f = 0.0d;
        double snowDepth = 0.0d;
        TSnowpack snowpack = null;
        snowpack = F_swmm5.Subcatch[j].snowpack;
        if ((snowpack == null)) {
            return;
        }
        k = F_swmm5.Subcatch[j].snowpack.snowmeltIndex;
        f = F_swmm5.Snowmelt[k].snn;
        snowpack.fArea[SNOW_PLOWABLE] = (f * F_swmm5.Subcatch[j].fracImperv);
        snowpack.fArea[SNOW_IMPERV] = (((1.0d - f)) * F_swmm5.Subcatch[j].fracImperv);
        snowpack.fArea[SNOW_PERV] = (1.0d - F_swmm5.Subcatch[j].fracImperv);
        L_f1: for (i = SNOW_PLOWABLE; (i <= SNOW_PERV); i++) {
            if (R.dgt(snowpack.fArea[i], 0.0d)) {
                snowpack.wsnow[i] = F_swmm5.Snowmelt[k].wsnow[i];
                snowpack.fw[i] = F_swmm5.Snowmelt[k].fwnow[i];
            } else {
                snowpack.wsnow[i] = 0.0d;
                snowpack.fw[i] = 0.0d;
            }
            snowpack.coldc[i] = 0.0d;
            snowpack.ati[i] = F_swmm5.Snowmelt[k].tbase[i];
            snowpack.awe[i] = 1.0d;
            snowDepth += (snowpack.wsnow[i] * snowpack.fArea[i]);
        }
        F_swmm5.Subcatch[j].@newSnowDepth = snowDepth;
    }

    // snow.c:193
    static void snow_initSnowmelt(int j) {
        int i = 0;
        int k = 0;
        L_f1: for (i = 0; (i < 3); i++) {
            F_swmm5.Snowmelt[j].@snn = 0.0d;
            F_swmm5.Snowmelt[j].si[i] = 0.0d;
            F_swmm5.Snowmelt[j].dhmin[i] = 0.0d;
            F_swmm5.Snowmelt[j].dhmax[i] = 0.0d;
            F_swmm5.Snowmelt[j].tbase[i] = 0.0d;
            F_swmm5.Snowmelt[j].fwfrac[i] = 0.0d;
            F_swmm5.Snowmelt[j].wsnow[i] = 0.0d;
            F_swmm5.Snowmelt[j].fwnow[i] = 0.0d;
            F_swmm5.Snowmelt[j].@weplow = 1.0e6d;
            L_f2: for (k = 0; (k < 5); k++) {
                F_swmm5.Snowmelt[j].sfrac[k] = 0.0d;
            }
            F_swmm5.Snowmelt[j].@toSubcatch = (-1);
        }
    }

    // snow.c:219
    static void snow_validateSnowmelt(int j) {
        int k = 0;
        int err = 0;
        double sum = 0.0d;
        L_f1: for (k = SNOW_PLOWABLE; (k <= SNOW_PERV); k++) {
            if (R.dgt(F_swmm5.Snowmelt[j].dhmin[k], F_swmm5.Snowmelt[j].dhmax[k])) {
                err = 1;
            }
            if (((F_swmm5.Snowmelt[j].fwfrac[k] < 0.0d) || (R.dgt(F_swmm5.Snowmelt[j].fwfrac[k], 1.0d)))) {
                err = 1;
            }
        }
        if (((F_swmm5.Snowmelt[j].snn < 0.0d) || (R.dgt(F_swmm5.Snowmelt[j].snn, 1.0d)))) {
            err = 1;
        }
        L_f2: for (k = 0; (k < 5); k++) {
            sum += F_swmm5.Snowmelt[j].sfrac[k];
        }
        if (R.dgt(sum, 1.01d)) {
            err = 1;
        }
        if ((err != 0)) {
            F_report.report_writeErrorMsg(ERR_SNOWPACK_PARAMS, F_swmm5.Snowmelt[j].ID);
        }
    }

    // snow.c:251
    static void snow_getState(int i, int j, double[] x) {
        TSnowpack snowpack = F_swmm5.Subcatch[i].snowpack;
        if ((snowpack == null)) {
            return;
        }
        x[0] = snowpack.wsnow[j];
        x[1] = snowpack.fw[j];
        x[2] = snowpack.coldc[j];
        x[3] = snowpack.ati[j];
        x[4] = snowpack.awe[j];
    }

    // snow.c:270
    static void snow_setState(int i, int j, double[] x) {
        TSnowpack snowpack = F_swmm5.Subcatch[i].snowpack;
        if ((snowpack == null)) {
            return;
        }
        snowpack.wsnow[j] = x[0];
        snowpack.fw[j] = x[1];
        snowpack.coldc[j] = x[2];
        snowpack.ati[j] = x[3];
        snowpack.awe[j] = x[4];
    }

    // snow.c:290
    static void setMeltParams(int j, int k, double[] x) {
        int i = 0;
        if (((k >= SNOW_PLOWABLE) && (k <= SNOW_PERV))) {
            F_swmm5.Snowmelt[j].dhmin[k] = ((x[0] * F_swmm5.UCF(TEMPERATURE)) / F_swmm5.UCF(RAINFALL));
            F_swmm5.Snowmelt[j].dhmax[k] = ((x[1] * F_swmm5.UCF(TEMPERATURE)) / F_swmm5.UCF(RAINFALL));
            F_swmm5.Snowmelt[j].tbase[k] = x[2];
            if ((F_swmm5.UnitSystem == SI)) {
                F_swmm5.Snowmelt[j].tbase[k] = ((((9.0d / 5.0d)) * F_swmm5.Snowmelt[j].tbase[k]) + 32.0d);
            }
            F_swmm5.Snowmelt[j].fwfrac[k] = x[3];
            F_swmm5.Snowmelt[j].wsnow[k] = (x[4] / F_swmm5.UCF(RAINDEPTH));
            x[5] = (((((x[5]) <= (((x[3] * x[4]))))) ? (x[5]) : (((x[3] * x[4])))));
            F_swmm5.Snowmelt[j].fwnow[k] = (x[5] / F_swmm5.UCF(RAINDEPTH));
            if ((k == SNOW_PLOWABLE)) {
                F_swmm5.Snowmelt[j].@snn = x[6];
            } else {
                F_swmm5.Snowmelt[j].si[k] = (x[6] / F_swmm5.UCF(RAINDEPTH));
            }
        } else if ((k == SNOW_REMOVAL)) {
            F_swmm5.Snowmelt[j].@weplow = (x[0] / F_swmm5.UCF(RAINDEPTH));
            L_f1: for (i = 0; (i <= 4); i++) {
                F_swmm5.Snowmelt[j].sfrac[i] = x[(i + 1)];
            }
            if (R.dge(x[6], 0.0d)) {
                F_swmm5.Snowmelt[j].@toSubcatch = R.D2I(((x[6] + 0.01d)));
            } else {
                F_swmm5.Snowmelt[j].@toSubcatch = (-1);
            }
        }
    }

    // snow.c:341
    static void snow_setMeltCoeffs(int j, double s) {
        int k = 0;
        L_f1: for (k = SNOW_PLOWABLE; (k <= SNOW_PERV); k++) {
            F_swmm5.Snowmelt[j].dhm[k] = (0.5d * (((F_swmm5.Snowmelt[j].dhmax[k] * ((1.0d + s))) + (F_swmm5.Snowmelt[j].dhmin[k] * ((1.0d - s))))));
        }
    }

    // snow.c:360
    static void snow_plowSnow(int j, double tStep) {
        int i = 0;
        int k = 0;
        int m = 0;
        DBox rainfall = new DBox(0.0d);
        DBox snowfall = new DBox(0.0d);
        double exc = 0.0d;
        double f = 0.0d;
        double sfracTotal = 0.0d;
        TSnowpack snowpack = null;
        snowpack = F_swmm5.Subcatch[j].snowpack;
        if (!(snowpack != null)) {
            return;
        }
        F_gage.gage_getPrecip(F_swmm5.Subcatch[j].gage, rainfall, snowfall);
        L_f1: for (i = SNOW_PLOWABLE; (i <= SNOW_PERV); i++) {
            if (R.dgt(snowpack.fArea[i], 0.0d)) {
                snowpack.wsnow[i] += (snowfall.v * tStep);
                snowpack.imelt[i] = 0.0d;
            }
        }
        if (R.dgt(snowpack.fArea[SNOW_PLOWABLE], 0.0d)) {
            k = snowpack.snowmeltIndex;
            if (R.dge(snowpack.wsnow[SNOW_PLOWABLE], F_swmm5.Snowmelt[k].weplow)) {
                exc = snowpack.wsnow[SNOW_PLOWABLE];
                f = (snowpack.fArea[SNOW_PLOWABLE] * ((F_swmm5.Subcatch[j].area - F_swmm5.Subcatch[j].lidArea)));
                F_swmm5.Snow.@removed = (F_swmm5.Snow.removed + ((F_swmm5.Snowmelt[k].sfrac[0] * exc) * f));
                sfracTotal = F_swmm5.Snowmelt[k].sfrac[0];
                if (R.dgt(snowpack.fArea[SNOW_IMPERV], 0.0d)) {
                    f = (snowpack.fArea[SNOW_PLOWABLE] / snowpack.fArea[SNOW_IMPERV]);
                    snowpack.wsnow[SNOW_IMPERV] += ((F_swmm5.Snowmelt[k].sfrac[1] * exc) * f);
                    sfracTotal += F_swmm5.Snowmelt[k].sfrac[1];
                }
                if (R.dgt(snowpack.fArea[SNOW_PERV], 0.0d)) {
                    f = (snowpack.fArea[SNOW_PLOWABLE] / snowpack.fArea[SNOW_PERV]);
                    snowpack.wsnow[SNOW_PERV] += ((F_swmm5.Snowmelt[k].sfrac[2] * exc) * f);
                    sfracTotal += F_swmm5.Snowmelt[k].sfrac[2];
                }
                snowpack.imelt[SNOW_PLOWABLE] = ((F_swmm5.Snowmelt[k].sfrac[3] * exc) / tStep);
                sfracTotal += F_swmm5.Snowmelt[k].sfrac[3];
                if (R.dgt(F_swmm5.Snowmelt[k].sfrac[4], 0.0d)) {
                    m = F_swmm5.Snowmelt[k].toSubcatch;
                    if ((F_swmm5.Subcatch[m].snowpack != null)) {
                        f = F_swmm5.Subcatch[m].snowpack.fArea[SNOW_PERV];
                    } else {
                        f = 0.0d;
                    }
                    if (R.dgt(f, 0.0d)) {
                        f = (snowpack.fArea[SNOW_PLOWABLE] / f);
                        F_swmm5.Subcatch[m].snowpack.wsnow[SNOW_PERV] += ((F_swmm5.Snowmelt[k].sfrac[4] * exc) * f);
                        sfracTotal += F_swmm5.Snowmelt[k].sfrac[4];
                    }
                }
                sfracTotal = (((((sfracTotal) <= (1.0d))) ? (sfracTotal) : (1.0d)));
                snowpack.wsnow[SNOW_PLOWABLE] = (exc * ((1.0d - sfracTotal)));
            }
        }
    }

    // snow.c:458
    static double snow_getSnowMelt(int j, double rainfall, double snowfall, double tStep, double[] netPrecip) {
        int i = 0;
        double rmelt = 0.0d;
        double smelt = 0.0d;
        double asc = 0.0d;
        double snowDepth = 0.0d;
        double impervPrecip = 0.0d;
        TSnowpack snowpack = null;
        snowpack = F_swmm5.Subcatch[j].snowpack;
        rmelt = getRainmelt(rainfall);
        L_f1: for (i = SNOW_PLOWABLE; (i <= SNOW_PERV); i++) {
            if ((snowpack.wsnow[i] <= (0.001d / 12.0d))) {
                asc = 0.0d;
                smelt = 0.0d;
                snowpack.imelt[i] += (((snowpack.wsnow[i] + snowpack.fw[i])) / tStep);
                snowpack.wsnow[i] = 0.0d;
                snowpack.fw[i] = 0.0d;
                snowpack.coldc[i] = 0.0d;
            } else {
                asc = getArealDepletion(snowpack, i, snowfall, tStep);
                smelt = meltSnowpack(snowpack, i, rmelt, asc, snowfall, tStep);
                smelt = routeSnowmelt(snowpack, i, smelt, asc, rainfall, tStep);
            }
            netPrecip[i] = ((smelt + snowpack.imelt[i]) + (rainfall * ((1.0d - asc))));
            snowDepth += (snowpack.wsnow[i] * snowpack.fArea[i]);
        }
        if (R.dgt(F_swmm5.Subcatch[j].fracImperv, 0.0d)) {
            impervPrecip = ((((netPrecip[SNOW_PLOWABLE] * snowpack.fArea[SNOW_PLOWABLE]) + (netPrecip[SNOW_IMPERV] * snowpack.fArea[SNOW_IMPERV]))) / F_swmm5.Subcatch[j].fracImperv);
            netPrecip[IMPERV0] = impervPrecip;
            netPrecip[IMPERV1] = impervPrecip;
        }
        return snowDepth;
    }

    // snow.c:532
    static double snow_getSnowCover(int j) {
        int i = 0;
        double snowCover = 0.0d;
        TSnowpack snowpack = null;
        snowpack = F_swmm5.Subcatch[j].snowpack;
        if (!(snowpack != null)) {
            return 0.0d;
        }
        L_f1: for (i = SNOW_PLOWABLE; (i <= SNOW_PERV); i++) {
            snowCover += (((snowpack.wsnow[i] + snowpack.fw[i])) * snowpack.fArea[i]);
        }
        return (snowCover * ((F_swmm5.Subcatch[j].area - F_swmm5.Subcatch[j].lidArea)));
    }

    // snow.c:555
    static double getArealDepletion(TSnowpack snowpack, int i, double snowfall, double tStep) {
        int k = 0;
        double asc = 0.0d;
        double si = 0.0d;
        double awesi = 0.0d;
        double awe = 0.0d;
        double sba = 0.0d;
        double sbws = 0.0d;
        if ((i == SNOW_PLOWABLE)) {
            return 1.0d;
        }
        k = snowpack.snowmeltIndex;
        si = F_swmm5.Snowmelt[k].si[i];
        if (((si == 0.0d) || (R.dge(snowpack.wsnow[i], si)))) {
            snowpack.awe[i] = 1.0d;
            return 1.0d;
        }
        if ((snowpack.wsnow[i] == 0.0d)) {
            snowpack.awe[i] = 1.0d;
            return 0.0d;
        }
        if (R.dgt(snowfall, 0.0d)) {
            awe = (((snowpack.wsnow[i] - (snowfall * tStep))) / si);
            awe = (((R.dge((awe), (0.0d))) ? (awe) : (0.0d)));
            sba = getArealSnowCover(i, awe);
            sbws = (awe + ((((0.75d * snowfall) * tStep)) / si));
            sbws = (((((sbws) <= (1.0d))) ? (sbws) : (1.0d)));
            snowpack.awe[i] = awe;
            snowpack.sba[i] = sba;
            snowpack.sbws[i] = sbws;
            return 1.0d;
        } else {
            awe = snowpack.awe[i];
            sba = snowpack.sba[i];
            sbws = snowpack.sbws[i];
            awesi = (snowpack.wsnow[i] / si);
            if ((awesi < snowpack.awe[i])) {
                snowpack.awe[i] = 1.0d;
                asc = getArealSnowCover(i, awesi);
            } else if (R.dge(awesi, snowpack.sbws[i])) {
                asc = 1.0d;
            } else {
                asc = (sba + ((((1.0d - sba)) / ((sbws - awe))) * ((awesi - awe))));
            }
            return asc;
        }
        throw new IllegalStateException('getArealDepletion: no return');
    }

    // snow.c:636
    static double getArealSnowCover(int i, double awesi) {
        int k = 0;
        int m = 0;
        double asc1 = 0.0d;
        double asc2 = 0.0d;
        if ((i == SNOW_IMPERV)) {
            k = 0;
        } else if ((i == SNOW_PERV)) {
            k = 1;
        } else {
            return 1.0d;
        }
        if ((awesi <= 0.0d)) {
            return 0.0d;
        }
        if (R.dge(awesi, 0.9999d)) {
            return 1.0d;
        }
        m = R.D2I((((awesi * 10.0d) + 0.00001d)));
        asc1 = F_swmm5.Snow.adc[k][m];
        if ((m >= 9)) {
            asc2 = 1.0d;
        } else {
            asc2 = F_swmm5.Snow.adc[k][(m + 1)];
        }
        return (asc1 + ((((asc2 - asc1)) / 0.1d) * ((awesi - (0.1d * ((float) (m)))))));
    }

    // snow.c:674
    static double meltSnowpack(TSnowpack snowpack, int i, double rmelt, double asc, double snowfall, double tStep) {
        int k = 0;
        double smelt = 0.0d;
        double ccFactor = 0.0d;
        k = snowpack.snowmeltIndex;
        if (R.dgt(rmelt, 0.0d)) {
            smelt = rmelt;
        } else if (R.dge(F_swmm5.Temp.ta, F_swmm5.Snowmelt[k].tbase[i])) {
            smelt = (F_swmm5.Snowmelt[k].dhm[i] * ((F_swmm5.Temp.ta - F_swmm5.Snowmelt[k].tbase[i])));
        } else {
            updateColdContent(snowpack, i, asc, snowfall, tStep);
            return 0.0d;
        }
        smelt *= asc;
        ccFactor = ((tStep * F_swmm5.Snow.rnm) * asc);
        smelt = reduceColdContent(snowpack, i, smelt, ccFactor);
        snowpack.ati[i] = F_swmm5.Snowmelt[k].tbase[i];
        return smelt;
    }

    // snow.c:720
    static double getRainmelt(double rainfall) {
        double uadj = 0.0d;
        double t1 = 0.0d;
        double t2 = 0.0d;
        double t3 = 0.0d;
        double smelt = 0.0d;
        rainfall = (rainfall * 43200.0d);
        if (R.dgt(rainfall, 0.02d)) {
            uadj = (0.006d * F_swmm5.Wind.ws);
            t1 = (F_swmm5.Temp.ta - 32.0d);
            t2 = ((7.5d * F_swmm5.Temp.gamma) * uadj);
            t3 = ((8.5d * uadj) * ((F_swmm5.Temp.ea - 0.18d)));
            smelt = ((t1 * (((0.001167d + t2) + (0.007d * rainfall)))) + t3);
            return (smelt / 43200.0d);
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('getRainmelt: no return');
    }

    // snow.c:746
    static void updateColdContent(TSnowpack snowpack, int i, double asc, double snowfall, double tStep) {
        int k = 0;
        double ati = 0.0d;
        double cc = 0.0d;
        double ccMax = 0.0d;
        double tipm = 0.0d;
        ati = snowpack.ati[i];
        cc = snowpack.coldc[i];
        if (R.dgt((snowfall * 43200.0d), 0.02d)) {
            ati = F_swmm5.Temp.ta;
        } else {
            tipm = (1.0d - Glibm.pow((1.0d - F_swmm5.Snow.tipm), (tStep / ((6.0d * 3600.0d)))));
            ati += (tipm * ((F_swmm5.Temp.ta - ati)));
        }
        k = snowpack.snowmeltIndex;
        ati = (((((ati) <= (F_swmm5.Snowmelt[k].tbase[i]))) ? (ati) : (F_swmm5.Snowmelt[k].tbase[i])));
        cc += ((((F_swmm5.Snow.rnm * F_swmm5.Snowmelt[k].dhm[i]) * ((ati - F_swmm5.Temp.ta))) * tStep) * asc);
        cc = (((R.dge((cc), (0.0d))) ? (cc) : (0.0d)));
        ccMax = (((snowpack.wsnow[i] * 0.007d) / 12.0d) * ((F_swmm5.Snowmelt[k].tbase[i] - ati)));
        cc = (((((cc) <= (ccMax))) ? (cc) : (ccMax)));
        snowpack.coldc[i] = cc;
        snowpack.ati[i] = ati;
    }

    // snow.c:799
    static double reduceColdContent(TSnowpack snowpack, int i, double smelt, double ccFactor) {
        double cc = 0.0d;
        cc = snowpack.coldc[i];
        if (R.dgt((smelt * ccFactor), cc)) {
            smelt -= (cc / ccFactor);
            cc = 0.0d;
        } else {
            cc -= (smelt * ccFactor);
            smelt = 0.0d;
        }
        snowpack.coldc[i] = cc;
        return smelt;
    }

    // snow.c:828
    static double routeSnowmelt(TSnowpack snowpack, int i, double smelt, double asc, double rainfall, double tStep) {
        int k = 0;
        double vmelt = 0.0d;
        k = snowpack.snowmeltIndex;
        vmelt = (smelt * tStep);
        vmelt = (((((vmelt) <= (snowpack.wsnow[i]))) ? (vmelt) : (snowpack.wsnow[i])));
        snowpack.wsnow[i] -= vmelt;
        snowpack.fw[i] += (vmelt + ((rainfall * tStep) * asc));
        vmelt = (snowpack.fw[i] - (F_swmm5.Snowmelt[k].fwfrac[i] * snowpack.wsnow[i]));
        vmelt = (((R.dge((vmelt), (0.0d))) ? (vmelt) : (0.0d)));
        snowpack.fw[i] -= vmelt;
        return (vmelt / tStep);
    }

}
