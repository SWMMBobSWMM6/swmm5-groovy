// Generated from EPA SWMM 5.2.4 (shape.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_shape {
    public static double Atotal = 0.0d;
    public static double Ptotal = 0.0d;

    // shape.c:42
    static int shape_validate(TShape shape, TTable curve) {
        if (!(computeShapeTables(shape, curve) != 0)) {
            return 0;
        }
        if (!(normalizeShapeTables(shape) != 0)) {
            return 0;
        }
        return 1;
    }

    // shape.c:64
    static int computeShapeTables(TShape shape, TTable curve) {
        int i = 0;
        int n = 0;
        double dy = 0.0d;
        double y = 0.0d;
        DBox y1 = new DBox(0.0d);
        DBox y2 = new DBox(0.0d);
        double w = 0.0d;
        DBox w1 = new DBox(0.0d);
        DBox w2 = new DBox(0.0d);
        double yLast = 0.0d;
        double wLast = 0.0d;
        DBox wMax = new DBox(0.0d);
        if (!(F_table.table_getFirstEntry(curve, y1, w1) != 0)) {
            return 0;
        }
        if ((((y1.v < 0.0d) || (R.dge(y1.v, 1.0d))) || (w1.v < 0.0d))) {
            return 0;
        }
        wMax.@v = w1.v;
        if ((y1.v != 0.0d)) {
            y2.@v = y1.v;
            w2.@v = w1.v;
            y1.@v = 0.0d;
            w1.@v = 0.0d;
        } else {
            if (!(F_table.table_getNextEntry(curve, y2, w2) != 0)) {
                return 0;
            }
            if (((y2.v < y1.v) || (w2.v < 0.0d))) {
                return 0;
            }
            if (R.dgt(y2.v, 1.0d)) {
                y2.@v = 1.0d;
            }
            if (R.dgt(w2.v, wMax.v)) {
                wMax.@v = w2.v;
            }
        }
        shape.@nTbl = 51;
        n = (shape.nTbl - 1);
        dy = (1.0d / ((double) ((n))));
        shape.areaTbl[0] = 0.0d;
        shape.hradTbl[0] = 0.0d;
        shape.widthTbl[0] = w1.v;
        Ptotal = w1.v;
        Atotal = 0.0d;
        y = 0.0d;
        w = w1.v;
        L_f1: for (i = 1; (i <= n); i++) {
            yLast = y;
            wLast = w;
            y = (y + dy);
            if ((Math.abs((y - 1.0d)) < 1e-06d)) {
                y = 1.0d;
            }
            if (R.dgt(y, y2.v)) {
                if (!(getNextInterval(curve, y, yLast, wLast, y1, y2, w1, w2, wMax) != 0)) {
                    return 0;
                }
                yLast = y1.v;
                wLast = w1.v;
            }
            w = shape_getWidth(y, y1.v, y2.v, w1.v, w2.v);
            Atotal += (shape_getArea(y, w, yLast, wLast));
            Ptotal += (getPerim(y, w, yLast, wLast));
            if ((y == 1.0d)) {
                Ptotal += w2.v;
            }
            shape.widthTbl[i] = w;
            shape.areaTbl[i] = Atotal;
            if (R.dgt(Ptotal, 0.0d)) {
                shape.hradTbl[i] = (Atotal / Ptotal);
            } else {
                shape.hradTbl[i] = 0.0d;
            }
        }
        shape.@aFull = shape.areaTbl[n];
        shape.@rFull = shape.hradTbl[n];
        shape.@wMax = wMax.v;
        getSmax(shape);
        return 1;
    }

    // shape.c:189
    static void getSmax(TShape shape) {
        int i = 0;
        int n = (shape.nTbl - 1);
        double sf = 0.0d;
        shape.@sMax = 0.0d;
        shape.@aMax = 0.0d;
        L_f1: for (i = 1; (i <= n); i++) {
            sf = (shape.areaTbl[i] * Glibm.pow(shape.hradTbl[i], (2.0d / 3.0d)));
            if (R.dgt(sf, shape.sMax)) {
                shape.@sMax = sf;
                shape.@aMax = shape.areaTbl[i];
            }
        }
    }

    // shape.c:216
    static int normalizeShapeTables(TShape shape) {
        int i = 0;
        int n = (shape.nTbl - 1);
        double aFull = shape.aFull;
        double rFull = shape.rFull;
        double wMax = shape.wMax;
        if ((((aFull == 0.0d) || (rFull == 0.0d)) || (wMax == 0.0d))) {
            return 0;
        }
        L_f1: for (i = 0; (i <= n); i++) {
            shape.areaTbl[i] = (shape.areaTbl[i] / aFull);
            shape.hradTbl[i] = (shape.hradTbl[i] / rFull);
            shape.widthTbl[i] = (shape.widthTbl[i] / wMax);
        }
        return 1;
    }

    // shape.c:246
    static int getNextInterval(TTable curve, double y, double yLast, double wLast, DRef y1, DRef y2, DRef w1, DRef w2, DRef wMax) {
        L_w1: while (R.dgt(y, y2.get())) {
            if (R.dgt(y2.get(), yLast)) {
                Atotal += (shape_getArea(y2.get(), w2.get(), yLast, wLast));
                Ptotal += (getPerim(y2.get(), w2.get(), yLast, wLast));
                yLast = y2.get();
                wLast = w2.get();
            }
            y1.set(y2.get());
            w1.set(w2.get());
            if (!(F_table.table_getNextEntry(curve, y2, w2) != 0)) {
                y2.set(1.0d);
                return 1;
            }
            if (R.dgt(w2.get(), wMax.get())) {
                wMax.set(w2.get());
            }
            if (((y2.get() < y1.get()) || (w2.get() < 0.0d))) {
                return 0;
            }
            if (R.dgt(y2.get(), 1.0d)) {
                y2.set(1.0d);
            }
        }
        return 1;
    }

    // shape.c:307
    static double shape_getWidth(double y, double y1, double y2, double w1, double w2) {
        if ((y2 == y1)) {
            return w2;
        }
        return (w1 + ((((y - y1)) / ((y2 - y1))) * ((w2 - w1))));
    }

    // shape.c:328
    static double shape_getArea(double y, double w, double y1, double w1) {
        double wMin = 0.0d;
        double wMax = 0.0d;
        if (R.dgt(w, w1)) {
            wMin = w1;
            wMax = w;
        } else {
            wMin = w;
            wMax = w1;
        }
        return (((wMin + (((wMax - wMin)) / 2.0d))) * ((y - y1)));
    }

    // shape.c:353
    static double getPerim(double y, double w, double y1, double w1) {
        double dy = (y - y1);
        double dw = (Math.abs((w - w1)) / 2.0d);
        return (2.0d * Math.sqrt(((dy * dy) + (dw * dw))));
    }

}
