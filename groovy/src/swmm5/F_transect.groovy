// Generated from EPA SWMM 5.2.4 (transect.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_transect {
    public static int Ntransects = 0;
    public static int Nstations = 0;
    public static double[] Station = new double[1501];
    public static double[] Elev = new double[1501];
    public static double Nleft = 0.0d;
    public static double Nright = 0.0d;
    public static double Nchannel = 0.0d;
    public static double Xleftbank = 0.0d;
    public static double Xrightbank = 0.0d;
    public static double Xfactor = 0.0d;
    public static double Yfactor = 0.0d;
    public static double Lfactor = 0.0d;

    // transect.c:70
    static int transect_create(int n) {
        Ntransects = n;
        if ((n == 0)) {
            return 0;
        }
        F_swmm5.@Transect = R.fill(new TTransect[(int) (Ntransects)], (() -> new TTransect()));
        if ((F_swmm5.Transect == null)) {
            return ERR_MEMORY;
        }
        Nchannel = 0.0d;
        Nleft = 0.0d;
        Nright = 0.0d;
        Nstations = 0;
        return 0;
    }

    // transect.c:90
    static void transect_delete() {
        if ((Ntransects == 0)) {
            return;
        }
        if ((F_swmm5.Transect != null)) {
            R.free(F_swmm5.Transect);
            F_swmm5.@Transect = (null);
        }
        Ntransects = 0;
    }

    // transect.c:104
    static int transect_readParams(IRef count, CPtr[] tok, int ntoks) {
        int i = 0;
        int k = 0;
        int index = count.get();
        int errcode = 0;
        double[] x = new double[10];
        CPtr id = null;
        k = F_input.findmatch(tok[0], F_keywords.TransectKeyWords);
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[0]);
        }
        int __sw1 = k;
        if (__sw1 == 0) {
            transect_validate((index - 1));
            if ((ntoks < 4)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            L_f2: for (i = 1; (i <= 3); i++) {
                if (!(F_input.getDouble(tok[i], new DElem(x, i)) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[i]);
                }
            }
            return setManning(x);
        } else if (__sw1 == 1) {
            if ((ntoks < 10)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            id = F_project.project_findID(TRANSECT, tok[1]);
            if ((id == null)) {
                return F_error.error_setInpError(ERR_NAME, tok[1]);
            }
            L_f3: for (i = 2; (i < 10); i++) {
                if (!(F_input.getDouble(tok[i], new DElem(x, i)) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[i]);
                }
            }
            count.set((index + 1));
            return setParams(index, id, x);
        } else if (__sw1 == 2) {
            if (((((ntoks - 1)) % 2) > 0)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            i = 1;
            L_w4: while ((i < ntoks)) {
                if (!(F_input.getDouble(tok[i], new DElem(x, 1)) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[i]);
                }
                if (!(F_input.getDouble(tok[(i + 1)], new DElem(x, 2)) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[(i + 1)]);
                }
                errcode = addStation(x[1], x[2]);
                if ((errcode != 0)) {
                    return errcode;
                }
                i += 2;
            }
            return 0;
        }
        return 0;
    }

    // transect.c:194
    static void transect_validate(int j) {
        int i = 0;
        double ymin = 0.0d;
        double ymax = 0.0d;
        double oldNchannel = Nchannel;
        if (((j < 0) || (j >= Ntransects))) {
            return;
        }
        if ((Nstations < 2)) {
            F_report.report_writeErrorMsg(ERR_TRANSECT_TOO_FEW, F_swmm5.Transect[j].ID);
            return;
        }
        if ((Nstations >= 1500)) {
            F_report.report_writeErrorMsg(ERR_TRANSECT_TOO_MANY, F_swmm5.Transect[j].ID);
            return;
        }
        if ((Nchannel <= 0.0d)) {
            F_report.report_writeErrorMsg(ERR_TRANSECT_MANNING, F_swmm5.Transect[j].ID);
            return;
        }
        if (R.dgt(Xleftbank, Xrightbank)) {
            F_report.report_writeErrorMsg(ERR_TRANSECT_OVERBANK, F_swmm5.Transect[j].ID);
            return;
        }
        Nchannel = (Nchannel * Math.sqrt(Lfactor));
        F_swmm5.Transect[j].@lengthFactor = Lfactor;
        ymax = Elev[1];
        ymin = Elev[1];
        L_f1: for (i = 2; (i <= Nstations); i++) {
            ymax = (((R.dge((Elev[i]), (ymax))) ? (Elev[i]) : (ymax)));
            ymin = (((((Elev[i]) <= (ymin))) ? (Elev[i]) : (ymin)));
        }
        if (R.dge(ymin, ymax)) {
            F_report.report_writeErrorMsg(ERR_TRANSECT_NO_DEPTH, F_swmm5.Transect[j].ID);
            return;
        }
        Station[0] = Station[1];
        Elev[0] = ymax;
        Nstations++;
        Station[Nstations] = Station[(Nstations - 1)];
        Elev[Nstations] = Elev[0];
        F_swmm5.Transect[j].@nTbl = 51;
        createTables(F_swmm5.Transect[j], ymin, ymax);
        F_swmm5.Transect[j].@roughness = oldNchannel;
    }

    // transect.c:264
    static void createTables(TTransect transect, double ymin, double ymax) {
        int i = 0;
        int nLast = 0;
        double dy = 0.0d;
        double y = 0.0d;
        transect.@yFull = (ymax - ymin);
        transect.@wMax = 0.0d;
        transect.areaTbl[0] = 0.0d;
        transect.hradTbl[0] = 0.0d;
        transect.widthTbl[0] = 0.0d;
        dy = (((ymax - ymin)) / ((((double) ((transect.nTbl))) - 1)));
        y = ymin;
        L_f1: for (i = 1; (i < transect.nTbl); i++) {
            y += dy;
            transect.areaTbl[i] = 0.0d;
            transect.hradTbl[i] = 0.0d;
            transect.widthTbl[i] = 0.0d;
            getGeometry(transect, i, y);
        }
        setMaxSectionFactor(transect);
        nLast = (transect.nTbl - 1);
        transect.@aFull = transect.areaTbl[nLast];
        transect.@rFull = transect.hradTbl[nLast];
        transect.@wMax = transect.widthTbl[nLast];
        L_f2: for (i = 1; (i <= nLast); i++) {
            transect.areaTbl[i] = (transect.areaTbl[i] / transect.aFull);
            transect.hradTbl[i] = (transect.hradTbl[i] / transect.rFull);
            transect.widthTbl[i] = (transect.widthTbl[i] / transect.wMax);
        }
        transect.widthTbl[0] = transect.widthTbl[1];
    }

    // transect.c:312
    static int setManning(double[] n) {
        int i = 0;
        L_f1: for (i = 1; (i <= 3); i++) {
            if ((n[i] < 0.0d)) {
                return ERR_NUMBER;
            }
        }
        if (R.dgt(n[1], 0.0d)) {
            Nleft = n[1];
        }
        if (R.dgt(n[2], 0.0d)) {
            Nright = n[2];
        }
        if (R.dgt(n[3], 0.0d)) {
            Nchannel = n[3];
        }
        if ((Nleft == 0.0d)) {
            Nleft = Nchannel;
        }
        if ((Nright == 0.0d)) {
            Nright = Nchannel;
        }
        return 0;
    }

    // transect.c:334
    static int setParams(int j, CPtr id, double[] x) {
        if (((j < 0) || (j >= Ntransects))) {
            return ERR_NUMBER;
        }
        F_swmm5.Transect[j].@ID = id;
        Xleftbank = (x[3] / F_swmm5.UCF(LENGTH));
        Xrightbank = (x[4] / F_swmm5.UCF(LENGTH));
        Lfactor = x[7];
        if ((Lfactor == 0.0d)) {
            Lfactor = 1.0d;
        }
        Xfactor = x[8];
        if ((Xfactor == 0.0d)) {
            Xfactor = 1.0d;
        }
        Xleftbank *= Xfactor;
        Xrightbank *= Xfactor;
        Yfactor = (x[9] / F_swmm5.UCF(LENGTH));
        Nstations = 0;
        return 0;
    }

    // transect.c:360
    static int addStation(double y, double x) {
        if ((Nstations < 0)) {
            return ERR_TRANSECT_UNKNOWN;
        }
        Nstations++;
        if ((Nstations >= 1500)) {
            return 0;
        }
        Station[Nstations] = ((x * Xfactor) / F_swmm5.UCF(LENGTH));
        Elev[Nstations] = (((y + Yfactor)) / F_swmm5.UCF(LENGTH));
        if (((Nstations > 1) && (Station[Nstations] < Station[(Nstations - 1)]))) {
            return ERR_TRANSECT_SEQUENCE;
        }
        return 0;
    }

    // transect.c:388
    static void getGeometry(TTransect transect, int i, double y) {
        int k = 0;
        double ylo = 0.0d;
        double yhi = 0.0d;
        DBox w = new DBox(0.0d);
        DBox wp = new DBox(0.0d);
        double wpSum = 0.0d;
        DBox a = new DBox(0.0d);
        double aSum = 0.0d;
        double q = 0.0d;
        double qSum = 0.0d;
        int findFlow = 0;
        wpSum = 0.0d;
        aSum = 0.0d;
        qSum = 0.0d;
        L_f1: for (k = 1; (k <= Nstations); k++) {
            if (R.dge(Elev[(k - 1)], Elev[k])) {
                yhi = Elev[(k - 1)];
                ylo = Elev[k];
            } else {
                yhi = Elev[k];
                ylo = Elev[(k - 1)];
            }
            if (R.dge(ylo, y)) {
                continue L_f1;
            }
            getSliceGeom(k, y, ylo, yhi, w, a, wp);
            wpSum += wp.v;
            aSum += a.v;
            transect.areaTbl[i] += a.v;
            transect.widthTbl[i] += w.v;
            if (R.dge(Elev[k], y)) {
                findFlow = 1;
            } else {
                findFlow = 0;
            }
            q = getFlow(k, aSum, wpSum, findFlow);
            if (R.dgt(q, 0.0d)) {
                qSum += q;
                aSum = 0.0d;
                wpSum = 0.0d;
            }
        }
        aSum = transect.areaTbl[i];
        if ((aSum == 0.0d)) {
            transect.hradTbl[i] = transect.hradTbl[(i - 1)];
        } else {
            transect.hradTbl[i] = Glibm.pow((((qSum * Nchannel) / 1.49d) / aSum), 1.5d);
        }
    }

    // transect.c:468
    static void getSliceGeom(int k, double y, double ylo, double yhi, DRef w, DRef a, DRef wp) {
        double width = 0.0d;
        double ratio = 0.0d;
        width = Math.abs((Station[k] - Station[(k - 1)]));
        w.set(width);
        wp.set(Math.sqrt(((width * width) + (((yhi - ylo)) * ((yhi - ylo))))));
        a.set(0.0d);
        if (R.dgt(y, yhi)) {
            a.set(((width * ((((y - yhi)) + ((y - ylo))))) / 2.0d));
        } else if (R.dgt(yhi, ylo)) {
            ratio = (((y - ylo)) / ((yhi - ylo)));
            a.set(((((width * ((yhi - ylo))) / 2.0d) * ratio) * ratio));
            w.set((w.get() * ratio));
            wp.set((wp.get() * ratio));
        }
    }

    // transect.c:519
    static double getFlow(int k, double a, double wp, int findFlow) {
        double n = 0.0d;
        if ((findFlow == 0)) {
            if ((k == (Nstations - 1))) {
                findFlow = 1;
            } else if ((Station[k] == Xleftbank)) {
                if (((Nleft != Nchannel) && (Station[k] != Station[(k - 1)]))) {
                    findFlow = 1;
                }
            } else if ((Station[k] == Xrightbank)) {
                if (((Nright != Nchannel) && (Station[k] != Station[(k + 1)]))) {
                    findFlow = 1;
                }
            }
        }
        if ((findFlow != 0)) {
            n = Nchannel;
            if ((Station[(k - 1)] < Xleftbank)) {
                n = Nleft;
            }
            if (R.dgt(Station[k], Xrightbank)) {
                n = Nright;
            }
            return (((1.486d / n) * a) * Glibm.pow((a / wp), (2.0d / 3.0d)));
        }
        return 0.0d;
    }

    // transect.c:570
    static void setMaxSectionFactor(TTransect transect) {
        int i = 0;
        double sf = 0.0d;
        transect.@aMax = 0.0d;
        transect.@sMax = 0.0d;
        L_f1: for (i = 1; (i < transect.nTbl); i++) {
            sf = (transect.areaTbl[i] * Glibm.pow(transect.hradTbl[i], (2.0d / 3.0d)));
            if (R.dgt(sf, transect.sMax)) {
                transect.@sMax = sf;
                transect.@aMax = transect.areaTbl[i];
            }
        }
    }

    // transect.c:596
    static void transect_createStreetTransect(TStreet street) {
        double ymin = 0.0d;
        double ymax = 0.0d;
        double y1 = 0.0d;
        double y3 = 0.0d;
        double y4 = 0.0d;
        double w1 = 0.0d;
        double w2 = 0.0d;
        double w3 = 0.0d;
        double w4 = 0.0d;
        ymin = 0.0d;
        w1 = street.backWidth;
        w2 = street.gutterWidth;
        w3 = street.width;
        w4 = (w3 - w2);
        y3 = (street.gutterDepression + (street.slope * w2));
        y1 = (street.curbHeight + street.gutterDepression);
        ymax = ((street.backSlope * street.backWidth) + y1);
        y4 = (y3 + (street.slope * w4));
        ymax = (((R.dge((ymax), (y4))) ? (ymax) : (y4)));
        Station[0] = 0.0d;
        Elev[0] = ymax;
        Station[1] = w1;
        Elev[1] = y1;
        Station[2] = w1;
        Elev[2] = 0.0d;
        Station[3] = (w1 + w2);
        Elev[3] = y3;
        Station[4] = (w1 + w3);
        Elev[4] = y4;
        if ((street.sides == 1)) {
            Station[5] = Station[4];
            Elev[5] = ymax;
            Nstations = 5;
            street.transect.@nTbl = 51;
        } else {
            Station[5] = (Station[4] + w4);
            Elev[5] = y3;
            Station[6] = (Station[5] + w2);
            Elev[6] = 0.0d;
            Station[7] = Station[6];
            Elev[7] = y1;
            Station[8] = (Station[7] + w1);
            Elev[8] = ymax;
            Nstations = 8;
            street.transect.@nTbl = 51;
        }
        Nchannel = street.roughness;
        if ((street.backWidth == 0.0d)) {
            Nleft = Nchannel;
            Nright = Nchannel;
            Xleftbank = Station[0];
            Xrightbank = Station[Nstations];
        } else {
            Nleft = street.backRoughness;
            Nright = Nleft;
            Xleftbank = Station[1];
            if ((street.sides == 2)) {
                Xrightbank = Station[(Nstations - 1)];
            } else {
                Xrightbank = Station[Nstations];
            }
        }
        createTables(street.transect, ymin, ymax);
        street.transect.@roughness = street.roughness;
    }

}
