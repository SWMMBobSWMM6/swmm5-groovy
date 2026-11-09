// Generated from EPA SWMM 5.2.4 (culvert.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_culvert {
    public static int MAX_CULVERT_CODE = 57;
    public static double[][] Params = new double[][] { new double[] { 0.0d, 0.0d, 0.0d, 0.0d, 0.00d }, new double[] { 1.0d, 0.0098d, 2.00d, 0.0398d, 0.67d }, new double[] { 1.0d, 0.0018d, 2.00d, 0.0292d, 0.74d }, new double[] { 1.0d, 0.0045d, 2.00d, 0.0317d, 0.69d }, new double[] { 1.0d, 0.0078d, 2.00d, 0.0379d, 0.69d }, new double[] { 1.0d, 0.0210d, 1.33d, 0.0463d, 0.75d }, new double[] { 1.0d, 0.0340d, 1.50d, 0.0553d, 0.54d }, new double[] { 1.0d, 0.0018d, 2.50d, 0.0300d, 0.74d }, new double[] { 1.0d, 0.0018d, 2.50d, 0.0243d, 0.83d }, new double[] { 1.0d, 0.026d, 1.0d, 0.0347d, 0.81d }, new double[] { 1.0d, 0.061d, 0.75d, 0.0400d, 0.80d }, new double[] { 1.0d, 0.061d, 0.75d, 0.0423d, 0.82d }, new double[] { 2.0d, 0.510d, 0.667d, 0.0309d, 0.80d }, new double[] { 2.0d, 0.486d, 0.667d, 0.0249d, 0.83d }, new double[] { 2.0d, 0.515d, 0.667d, 0.0375d, 0.79d }, new double[] { 2.0d, 0.495d, 0.667d, 0.0314d, 0.82d }, new double[] { 2.0d, 0.486d, 0.667d, 0.0252d, 0.865d }, new double[] { 2.0d, 0.545d, 0.667d, 0.04505d, 0.73d }, new double[] { 2.0d, 0.533d, 0.667d, 0.0425d, 0.705d }, new double[] { 2.0d, 0.522d, 0.667d, 0.0402d, 0.68d }, new double[] { 2.0d, 0.498d, 0.667d, 0.0327d, 0.75d }, new double[] { 2.0d, 0.497d, 0.667d, 0.0339d, 0.803d }, new double[] { 2.0d, 0.493d, 0.667d, 0.0361d, 0.806d }, new double[] { 2.0d, 0.495d, 0.667d, 0.0386d, 0.71d }, new double[] { 2.0d, 0.497d, 0.667d, 0.0302d, 0.835d }, new double[] { 2.0d, 0.495d, 0.667d, 0.0252d, 0.881d }, new double[] { 2.0d, 0.493d, 0.667d, 0.0227d, 0.887d }, new double[] { 1.0d, 0.0083d, 2.00d, 0.0379d, 0.69d }, new double[] { 1.0d, 0.0145d, 1.75d, 0.0419d, 0.64d }, new double[] { 1.0d, 0.0340d, 1.50d, 0.0496d, 0.57d }, new double[] { 1.0d, 0.0100d, 2.00d, 0.0398d, 0.67d }, new double[] { 1.0d, 0.0018d, 2.50d, 0.0292d, 0.74d }, new double[] { 1.0d, 0.0045d, 2.00d, 0.0317d, 0.69d }, new double[] { 1.0d, 0.0100d, 2.00d, 0.0398d, 0.67d }, new double[] { 1.0d, 0.0018d, 2.50d, 0.0292d, 0.74d }, new double[] { 1.0d, 0.0095d, 2.00d, 0.0317d, 0.69d }, new double[] { 1.0d, 0.0083d, 2.00d, 0.0379d, 0.69d }, new double[] { 1.0d, 0.0300d, 1.00d, 0.0463d, 0.75d }, new double[] { 1.0d, 0.0340d, 1.50d, 0.0496d, 0.57d }, new double[] { 1.0d, 0.0300d, 1.50d, 0.0496d, 0.57d }, new double[] { 1.0d, 0.0088d, 2.00d, 0.0368d, 0.68d }, new double[] { 1.0d, 0.0030d, 2.00d, 0.0269d, 0.77d }, new double[] { 1.0d, 0.0300d, 1.50d, 0.0496d, 0.57d }, new double[] { 1.0d, 0.0088d, 2.00d, 0.0368d, 0.68d }, new double[] { 1.0d, 0.0030d, 2.00d, 0.0269d, 0.77d }, new double[] { 1.0d, 0.0083d, 2.00d, 0.0379d, 0.69d }, new double[] { 1.0d, 0.0300d, 1.00d, 0.0473d, 0.75d }, new double[] { 1.0d, 0.0340d, 1.50d, 0.0496d, 0.57d }, new double[] { 2.0d, 0.534d, 0.555d, 0.0196d, 0.90d }, new double[] { 2.0d, 0.519d, 0.640d, 0.0210d, 0.90d }, new double[] { 2.0d, 0.536d, 0.622d, 0.0368d, 0.83d }, new double[] { 2.0d, 0.5035d, 0.719d, 0.0478d, 0.80d }, new double[] { 2.0d, 0.547d, 0.800d, 0.0598d, 0.75d }, new double[] { 2.0d, 0.475d, 0.667d, 0.0179d, 0.97d }, new double[] { 2.0d, 0.560d, 0.667d, 0.0446d, 0.85d }, new double[] { 2.0d, 0.560d, 0.667d, 0.0378d, 0.87d }, new double[] { 2.0d, 0.500d, 0.667d, 0.0446d, 0.65d }, new double[] { 2.0d, 0.500d, 0.667d, 0.0378d, 0.71d } };

    // culvert.c:174
    static double culvert_getInflow(int j, double q0, double h) {
        int code = 0;
        int k = 0;
        int condition = 0;
        double y = 0.0d;
        double y1 = 0.0d;
        double y2 = 0.0d;
        double q = 0.0d;
        TCulvert culvert = new TCulvert();
        if ((F_swmm5.Link[j].type != CONDUIT)) {
            return q0;
        }
        culvert.@xsect = F_swmm5.Link[j].xsect;
        code = culvert.xsect.culvertCode;
        if (((code <= 0) || (code > MAX_CULVERT_CODE))) {
            return q0;
        }
        k = F_swmm5.Link[j].subIndex;
        culvert.@yFull = culvert.xsect.yFull;
        culvert.@ad = (culvert.xsect.aFull * Math.sqrt(culvert.yFull));
        int __sw1 = code;
        if (__sw1 == 5 || __sw1 == 37 || __sw1 == 46) {
            culvert.@scf = ((-7.0d) * F_swmm5.Conduit[k].slope);
        } else {
            culvert.@scf = (0.5d * F_swmm5.Conduit[k].slope);
        }
        y = (h - ((F_swmm5.Node_[F_swmm5.Link[j].node1].invertElev + F_swmm5.Link[j].offset1)));
        y2 = (culvert.yFull * ((((16.0d * Params[code][C]) + Params[code][Y]) - culvert.scf)));
        if (R.dge(y, y2)) {
            q = getSubmergedFlow(code, y, culvert);
            condition = 2;
        } else {
            y1 = (0.95d * culvert.yFull);
            if ((y <= y1)) {
                q = getUnsubmergedFlow(code, y, culvert);
                condition = 1;
            } else {
                q = getTransitionFlow(code, y, y1, y2, culvert);
                condition = 0;
            }
        }
        if ((q < q0)) {
            F_swmm5.Link[j].@inletControl = 1;
            F_swmm5.Link[j].@dqdh = culvert.dQdH;
            return q;
        } else {
            return q0;
        }
        throw new IllegalStateException('culvert_getInflow: no return');
    }

    // culvert.c:257
    static double getUnsubmergedFlow(int code, double h, TCulvert culvert) {
        double arg = 0.0d;
        double q = 0.0d;
        culvert.@kk = Params[code][K];
        culvert.@mm = Params[code][M];
        arg = ((h / culvert.yFull) / culvert.kk);
        if ((Params[code][FORM] == 1.0d)) {
            q = getForm1Flow(h, culvert);
        } else {
            q = (culvert.ad * Glibm.pow(arg, (1.0d / culvert.mm)));
        }
        culvert.@dQdH = ((q / h) / culvert.mm);
        return q;
    }

    // culvert.c:288
    static double getSubmergedFlow(int code, double h, TCulvert culvert) {
        double cc = Params[code][C];
        double yy = Params[code][Y];
        double arg = (((((h / culvert.yFull) - yy) + culvert.scf)) / cc);
        double q = 0.0d;
        if ((arg <= 0.0d)) {
            culvert.@dQdH = 0.0d;
            return 10000000000.0d;
        }
        q = (Math.sqrt(arg) * culvert.ad);
        culvert.@dQdH = ((((0.5d * q) / arg) / culvert.yFull) / cc);
        return q;
    }

    // culvert.c:316
    static double getTransitionFlow(int code, double h, double h1, double h2, TCulvert culvert) {
        double q1 = getUnsubmergedFlow(code, h1, culvert);
        double q2 = getSubmergedFlow(code, h2, culvert);
        double q = (q1 + ((((q2 - q1)) * ((h - h1))) / ((h2 - h1))));
        culvert.@dQdH = (((q2 - q1)) / ((h2 - h1)));
        return q;
    }

    // culvert.c:339
    static double getForm1Flow(double h, TCulvert culvert) {
        culvert.@hPlus = ((h / culvert.yFull) + culvert.scf);
        F_findroot.findroot_Ridder((0.01d * h), h, 0.001d, F_culvert::form1Eqn, culvert);
        return culvert.qc;
    }

    // culvert.c:363
    static double form1Eqn(double yc, Object p) {
        double ac = 0.0d;
        double wc = 0.0d;
        double yh = 0.0d;
        TCulvert culvert = ((TCulvert) p);
        ac = F_xsect.xsect_getAofY(culvert.xsect, yc);
        wc = F_xsect.xsect_getWofY(culvert.xsect, yc);
        yh = (ac / wc);
        culvert.@qc = (ac * Math.sqrt((32.2d * yh)));
        return (((culvert.hPlus - (yc / culvert.yFull)) - ((yh / 2.0d) / culvert.yFull)) - (culvert.kk * Glibm.pow((culvert.qc / culvert.ad), culvert.mm)));
    }

}
