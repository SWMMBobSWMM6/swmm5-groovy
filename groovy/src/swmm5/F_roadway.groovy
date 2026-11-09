// Generated from EPA SWMM 5.2.4 (roadway.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_roadway {
    public static int N_Cr_Low_Paved = 4;
    public static double[][] Cr_Low_Paved = new double[][] { new double[] { 0.0d, 2.85d }, new double[] { 0.2d, 2.95d }, new double[] { 0.7d, 3.03d }, new double[] { 4.0d, 3.05d } };
    public static int N_Cr_Low_Gravel = 8;
    public static double[][] Cr_Low_Gravel = new double[][] { new double[] { 0.0d, 2.5d }, new double[] { 0.5d, 2.7d }, new double[] { 1.0d, 2.8d }, new double[] { 1.5d, 2.9d }, new double[] { 2.0d, 2.98d }, new double[] { 2.5d, 3.02d }, new double[] { 3.0d, 3.03d }, new double[] { 4.0d, 3.05d } };
    public static int N_Cr_High_Paved = 2;
    public static double[][] Cr_High_Paved = new double[][] { new double[] { 0.15d, 3.05d }, new double[] { 0.25d, 3.10d } };
    public static int N_Cr_High_Gravel = 2;
    public static double[][] Cr_High_Gravel = new double[][] { new double[] { 0.15d, 2.95d }, new double[] { 0.30d, 3.10d } };
    public static int N_Kt_Paved = 9;
    public static double[][] Kt_Paved = new double[][] { new double[] { 0.8d, 1.0d }, new double[] { 0.85d, 0.98d }, new double[] { 0.90d, 0.92d }, new double[] { 0.93d, 0.85d }, new double[] { 0.95d, 0.80d }, new double[] { 0.97d, 0.70d }, new double[] { 0.98d, 0.60d }, new double[] { 0.99d, 0.50d }, new double[] { 1.00d, 0.40d } };
    public static int N_Kt_Gravel = 12;
    public static double[][] Kt_Gravel = new double[][] { new double[] { 0.75d, 1.00d }, new double[] { 0.80d, 0.985d }, new double[] { 0.83d, 0.97d }, new double[] { 0.86d, 0.93d }, new double[] { 0.89d, 0.90d }, new double[] { 0.90d, 0.87d }, new double[] { 0.92d, 0.80d }, new double[] { 0.94d, 0.70d }, new double[] { 0.96d, 0.60d }, new double[] { 0.98d, 0.50d }, new double[] { 0.99d, 0.40d }, new double[] { 1.00d, 0.24d } };

    // roadway.c:85
    static double roadway_getInflow(int j, double dir, double hRoad, double h1, double h2) {
        int k = 0;
        int roadSurf = 0;
        int useVariableCd = 0;
        double length = 0.0d;
        double roadWidth = 0.0d;
        double hWr = 0.0d;
        double ht = 0.0d;
        double cD = 0.0d;
        double q = 0.0d;
        double dqdh = 0.0d;
        if ((F_swmm5.Link[j].type != WEIR)) {
            return 0.0d;
        }
        k = F_swmm5.Link[j].subIndex;
        roadWidth = F_swmm5.Weir[k].roadWidth;
        roadSurf = F_swmm5.Weir[k].roadSurface;
        cD = F_swmm5.Weir[k].cDisch1;
        if ((F_swmm5.UnitSystem == SI)) {
            cD = (cD / 0.552d);
        }
        useVariableCd = 0;
        if (((R.dgt(roadWidth, 0.0d)) && (roadSurf >= 1))) {
            useVariableCd = 1;
        }
        hWr = (h1 - hRoad);
        ht = (h2 - hRoad);
        if (R.dgt(hWr, 0.0001d)) {
            if ((useVariableCd != 0)) {
                cD = getCd(hWr, ht, roadWidth, roadSurf);
            }
            length = F_swmm5.Link[j].xsect.wMax;
            q = ((cD * length) * Glibm.pow(hWr, 1.5d));
            dqdh = ((1.5d * q) / hWr);
        }
        F_swmm5.Link[j].@dqdh = dqdh;
        F_swmm5.Link[j].@newDepth = (((R.dge(((h1 - hRoad)), (0.0d))) ? ((h1 - hRoad)) : (0.0d)));
        F_swmm5.Link[j].@flowClass = SUBCRITICAL;
        if (R.dgt(hRoad, h2)) {
            if ((dir == 1.0d)) {
                F_swmm5.Link[j].@flowClass = DN_CRITICAL;
            } else {
                F_swmm5.Link[j].@flowClass = UP_CRITICAL;
            }
        }
        return (dir * q);
    }

    // roadway.c:146
    static double getCd(double hWr, double ht, double roadWidth, int roadSurf) {
        double kT = 1.0d;
        double hL = 0.0d;
        double htH = 0.0d;
        double cR = 0.0d;
        if ((hWr <= 0.0d)) {
            return 0.0d;
        }
        hL = (hWr / roadWidth);
        if ((hL <= 0.15d)) {
            if ((roadSurf == PAVED)) {
                cR = getY(hWr, Cr_Low_Paved, N_Cr_Low_Paved);
            } else {
                cR = getY(hWr, Cr_Low_Gravel, N_Cr_Low_Gravel);
            }
        } else {
            if ((roadSurf == PAVED)) {
                cR = getY(hL, Cr_High_Paved, N_Cr_High_Paved);
            } else {
                cR = getY(hL, Cr_High_Gravel, N_Cr_High_Gravel);
            }
        }
        if (R.dgt(ht, 0.0d)) {
            htH = (ht / hWr);
            if ((roadSurf == PAVED)) {
                kT = getY(htH, Kt_Paved, N_Kt_Paved);
            } else {
                kT = getY(htH, Kt_Gravel, N_Kt_Gravel);
            }
        }
        return (cR * kT);
    }

    // roadway.c:176
    static double getY(double x, double[][] table, int n) {
        int i = 0;
        double x1 = 0.0d;
        double y1 = 0.0d;
        double dx = 0.0d;
        double dy = 0.0d;
        if ((x <= table[0][0])) {
            return table[0][1];
        }
        if (R.dge(x, (table[(n - 1)])[0])) {
            return (table[(n - 1)])[1];
        }
        L_f1: for (i = 1; (i < n); i++) {
            if ((x <= table[i][0])) {
                x1 = (table[(i - 1)])[0];
                dx = (table[i][0] - x1);
                y1 = (table[(i - 1)])[1];
                dy = (table[i][1] - y1);
                return (y1 + ((((x - x1)) * dy) / dx));
            }
        }
        return (table[(n - 1)])[1];
    }

}
