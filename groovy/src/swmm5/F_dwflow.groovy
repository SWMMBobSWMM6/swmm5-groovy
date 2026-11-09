// Generated from EPA SWMM 5.2.4 (dwflow.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_dwflow {
    public static double MAXVELOCITY = 50.0d;

    // dwflow.c:57
    static void dwflow_findConduitFlow(int j, int steps, double omega, double dt) {
        int k = 0;
        int n1 = 0;
        int n2 = 0;
        double z1 = 0.0d;
        double z2 = 0.0d;
        DBox h1 = new DBox(0.0d);
        DBox h2 = new DBox(0.0d);
        DBox y1 = new DBox(0.0d);
        DBox y2 = new DBox(0.0d);
        double a1 = 0.0d;
        double a2 = 0.0d;
        double r1 = 0.0d;
        double yMid = 0.0d;
        double rMid = 0.0d;
        double aMid = 0.0d;
        double aWtd = 0.0d;
        double rWtd = 0.0d;
        double qLast = 0.0d;
        double qOld = 0.0d;
        double aOld = 0.0d;
        double v = 0.0d;
        double rho = 0.0d;
        double sigma = 0.0d;
        double length = 0.0d;
        double wSlot = 0.0d;
        double dq1 = 0.0d;
        double dq2 = 0.0d;
        double dq3 = 0.0d;
        double dq4 = 0.0d;
        double dq5 = 0.0d;
        double dq6 = 0.0d;
        double denom = 0.0d;
        double q = 0.0d;
        double barrels = 0.0d;
        TXsect xsect = F_swmm5.Link[j].xsect;
        int isFull = 0;
        int isClosed = 0;
        if ((F_swmm5.Link[j].setting == 0)) {
            isClosed = 1;
        }
        k = F_swmm5.Link[j].subIndex;
        barrels = F_swmm5.Conduit[k].barrels;
        qOld = (F_swmm5.Link[j].oldFlow / barrels);
        qLast = F_swmm5.Conduit[k].q1;
        F_swmm5.Conduit[k].@evapLossRate = 0.0d;
        F_swmm5.Conduit[k].@seepLossRate = 0.0d;
        n1 = F_swmm5.Link[j].node1;
        n2 = F_swmm5.Link[j].node2;
        z1 = (F_swmm5.Node_[n1].invertElev + F_swmm5.Link[j].offset1);
        z2 = (F_swmm5.Node_[n2].invertElev + F_swmm5.Link[j].offset2);
        h1.@v = (F_swmm5.Node_[n1].newDepth + F_swmm5.Node_[n1].invertElev);
        h2.@v = (F_swmm5.Node_[n2].newDepth + F_swmm5.Node_[n2].invertElev);
        h1.@v = (((R.dge((h1.v), (z1))) ? (h1.v) : (z1)));
        h2.@v = (((R.dge((h2.v), (z2))) ? (h2.v) : (z2)));
        y1.@v = (h1.v - z1);
        y2.@v = (h2.v - z2);
        y1.@v = (((R.dge((y1.v), (0.0001d))) ? (y1.v) : (0.0001d)));
        y2.@v = (((R.dge((y2.v), (0.0001d))) ? (y2.v) : (0.0001d)));
        if ((F_swmm5.SurchargeMethod != SLOT)) {
            y1.@v = (((((y1.v) <= (xsect.yFull))) ? (y1.v) : (xsect.yFull)));
            y2.@v = (((((y2.v) <= (xsect.yFull))) ? (y2.v) : (xsect.yFull)));
        }
        aOld = F_swmm5.Conduit[k].a2;
        aOld = (((R.dge((aOld), (0.0001d))) ? (aOld) : (0.0001d)));
        length = F_swmm5.Conduit[k].modLength;
        findSurfArea(j, qLast, length, h1, h2, y1, y2);
        wSlot = getSlotWidth(xsect, y1.v);
        a1 = dwflow_getArea(xsect, y1.v, wSlot);
        r1 = getHydRad(xsect, y1.v);
        wSlot = getSlotWidth(xsect, y2.v);
        a2 = dwflow_getArea(xsect, y2.v, wSlot);
        yMid = (0.5d * ((y1.v + y2.v)));
        wSlot = getSlotWidth(xsect, yMid);
        aMid = dwflow_getArea(xsect, yMid, wSlot);
        rMid = getHydRad(xsect, yMid);
        if (((R.dge(y1.v, xsect.yFull)) && (R.dge(y2.v, xsect.yFull)))) {
            isFull = 1;
        }
        if ((((((F_swmm5.Link[j].flowClass == DRY) || (F_swmm5.Link[j].flowClass == UP_DRY)) || (F_swmm5.Link[j].flowClass == DN_DRY)) || (isClosed != 0)) || (aMid <= 0.0001d))) {
            F_swmm5.Conduit[k].@a1 = (0.5d * ((a1 + a2)));
            F_swmm5.Conduit[k].@q1 = 0.0d;
            F_swmm5.Conduit[k].@q2 = 0.0d;
            F_swmm5.Link[j].@dqdh = ((((32.2d * dt) * aMid) / length) * barrels);
            F_swmm5.Link[j].@froude = 0.0d;
            F_swmm5.Link[j].@newDepth = (((((yMid) <= (F_swmm5.Link[j].xsect.yFull))) ? (yMid) : (F_swmm5.Link[j].xsect.yFull)));
            F_swmm5.Link[j].@newVolume = ((F_swmm5.Conduit[k].a1 * F_link.link_getLength(j)) * barrels);
            F_swmm5.Link[j].@newFlow = 0.0d;
            return;
        }
        v = (qLast / aMid);
        if (R.dgt(Math.abs(v), MAXVELOCITY)) {
            v = (MAXVELOCITY * (((((qLast) < 0)) ? ((-1)) : (1))));
        }
        F_swmm5.Link[j].@froude = F_link.link_getFroude(j, v, yMid);
        if (((F_swmm5.Link[j].flowClass == SUBCRITICAL) && (R.dgt(F_swmm5.Link[j].froude, 1.0d)))) {
            F_swmm5.Link[j].@flowClass = SUPCRITICAL;
        }
        if ((F_swmm5.Link[j].froude <= 0.5d)) {
            sigma = 1.0d;
        } else if (R.dge(F_swmm5.Link[j].froude, 1.0d)) {
            sigma = 0.0d;
        } else {
            sigma = (2.0d * ((1.0d - F_swmm5.Link[j].froude)));
        }
        rho = 1.0d;
        if ((((!(isFull != 0)) && (R.dgt(qLast, 0.0d))) && (R.dge(h1.v, h2.v)))) {
            rho = sigma;
        }
        aWtd = (a1 + (((aMid - a1)) * rho));
        rWtd = (r1 + (((rMid - r1)) * rho));
        if ((F_swmm5.InertDamping == NO_DAMPING)) {
            sigma = 1.0d;
        } else if ((F_swmm5.InertDamping == FULL_DAMPING)) {
            sigma = 0.0d;
        }
        if (((isFull != 0) && (!(F_xsect.xsect_isOpen(xsect.type) != 0)))) {
            sigma = 0.0d;
        }
        if (((xsect.type == FORCE_MAIN) && (isFull != 0))) {
            dq1 = (dt * F_forcmain.forcemain_getFricSlope(j, Math.abs(v), rMid));
        } else {
            dq1 = (((dt * F_swmm5.Conduit[k].roughFactor) / Glibm.pow(rWtd, 1.33333d)) * Math.abs(v));
        }
        dq2 = ((((dt * 32.2d) * aWtd) * ((h2.v - h1.v))) / length);
        dq3 = 0.0d;
        dq4 = 0.0d;
        if (R.dgt(sigma, 0.0d)) {
            dq3 = (((2.0d * v) * ((aMid - aOld))) * sigma);
            dq4 = (((((dt * v) * v) * ((a2 - a1))) / length) * sigma);
        }
        dq5 = 0.0d;
        if ((F_swmm5.Conduit[k].hasLosses != 0)) {
            dq5 = (((findLocalLosses(j, a1, a2, aMid, qLast) / 2.0d) / length) * dt);
        }
        dq6 = ((((F_link.link_getLossRate(j, DW, qLast, dt) * 2.5d) * dt) * v) / F_link.link_getLength(j));
        denom = ((1.0d + dq1) + dq5);
        q = ((((((qOld - dq2) + dq3) + dq4) + dq6)) / denom);
        F_swmm5.Link[j].@dqdh = ((((((1.0d / denom) * 32.2d) * dt) * aWtd) / length) * barrels);
        F_swmm5.Link[j].@inletControl = 0;
        F_swmm5.Link[j].@normalFlow = 0;
        if (R.dgt(q, 0.0d)) {
            if (((xsect.culvertCode > 0) && (!(isFull != 0)))) {
                q = F_culvert.culvert_getInflow(j, q, h1.v);
            } else if ((((F_swmm5.NormalFlowLtd != NEITHER) && (y1.v < F_swmm5.Link[j].xsect.yFull)) && (((F_swmm5.Link[j].flowClass == SUBCRITICAL) || (F_swmm5.Link[j].flowClass == SUPCRITICAL))))) {
                q = checkNormalFlow(j, q, y1.v, y2.v, a1, r1);
            }
        }
        if ((steps > 0)) {
            q = ((((1.0d - omega)) * qLast) + (omega * q));
            if (((q * qLast) < 0.0d)) {
                q = (0.001d * (((((q) < 0)) ? ((-1)) : (1))));
            }
        }
        if (R.dgt(F_swmm5.Link[j].qLimit, 0.0d)) {
            if (R.dgt(Math.abs(q), F_swmm5.Link[j].qLimit)) {
                q = ((((((q) < 0)) ? ((-1)) : (1))) * F_swmm5.Link[j].qLimit);
            }
        }
        if ((F_link.link_setFlapGate(j, n1, n2, q) != 0)) {
            q = 0.0d;
        }
        if (((R.dgt(q, 0.0001d)) && (F_swmm5.Node_[n1].newDepth <= 0.0001d))) {
            q = 0.0001d;
        }
        if (((q < (-0.0001d)) && (F_swmm5.Node_[n2].newDepth <= 0.0001d))) {
            q = (-0.0001d);
        }
        F_swmm5.Conduit[k].@a1 = aMid;
        F_swmm5.Conduit[k].@q1 = q;
        F_swmm5.Conduit[k].@q2 = q;
        F_swmm5.Link[j].@newDepth = (((((yMid) <= (xsect.yFull))) ? (yMid) : (xsect.yFull)));
        aMid = (((a1 + a2)) / 2.0d);
        F_swmm5.Conduit[k].@fullState = F_link.link_getFullState(a1, a2, xsect.aFull);
        F_swmm5.Link[j].@newVolume = ((aMid * F_link.link_getLength(j)) * barrels);
        F_swmm5.Link[j].@newFlow = (q * barrels);
    }

    // dwflow.c:297
    static int getFlowClass(int j, double q, double h1, double h2, double y1, double y2, DRef yC, DRef yN, DRef fasnh) {
        int n1 = 0;
        int n2 = 0;
        int flowClass = 0;
        double ycMin = 0.0d;
        double ycMax = 0.0d;
        double z1 = 0.0d;
        double z2 = 0.0d;
        n1 = F_swmm5.Link[j].node1;
        n2 = F_swmm5.Link[j].node2;
        z1 = F_swmm5.Link[j].offset1;
        z2 = F_swmm5.Link[j].offset2;
        if ((F_swmm5.Node_[n1].type == OUTFALL)) {
            z1 = (((R.dge((0.0d), (((z1 - F_swmm5.Node_[n1].newDepth))))) ? (0.0d) : (((z1 - F_swmm5.Node_[n1].newDepth)))));
        }
        if ((F_swmm5.Node_[n2].type == OUTFALL)) {
            z2 = (((R.dge((0.0d), (((z2 - F_swmm5.Node_[n2].newDepth))))) ? (0.0d) : (((z2 - F_swmm5.Node_[n2].newDepth)))));
        }
        flowClass = SUBCRITICAL;
        fasnh.set(1.0d);
        if (((R.dgt(y1, 0.0001d)) && (R.dgt(y2, 0.0001d)))) {
            if ((q < 0.0d)) {
                if (R.dgt(z1, 0.0d)) {
                    yN.set(F_link.link_getYnorm(j, Math.abs(q)));
                    yC.set(F_link.link_getYcrit(j, Math.abs(q)));
                    ycMin = (((((yN.get()) <= (yC.get()))) ? (yN.get()) : (yC.get())));
                    if ((y1 < ycMin)) {
                        flowClass = UP_CRITICAL;
                    }
                }
            } else {
                if (R.dgt(z2, 0.0d)) {
                    yN.set(F_link.link_getYnorm(j, Math.abs(q)));
                    yC.set(F_link.link_getYcrit(j, Math.abs(q)));
                    ycMin = (((((yN.get()) <= (yC.get()))) ? (yN.get()) : (yC.get())));
                    ycMax = (((R.dge((yN.get()), (yC.get()))) ? (yN.get()) : (yC.get())));
                    if ((y2 < ycMin)) {
                        flowClass = DN_CRITICAL;
                    } else if ((y2 < ycMax)) {
                        if (((ycMax - ycMin) < 0.0001d)) {
                            fasnh.set(0.0d);
                        } else {
                            fasnh.set((((ycMax - y2)) / ((ycMax - ycMin))));
                        }
                    }
                }
            }
        } else if (((y1 <= 0.0001d) && (y2 <= 0.0001d))) {
            flowClass = DRY;
        } else if (R.dgt(y2, 0.0001d)) {
            if ((h2 < (F_swmm5.Node_[n1].invertElev + F_swmm5.Link[j].offset1))) {
                flowClass = UP_DRY;
            } else if (R.dgt(z1, 0.0d)) {
                yN.set(F_link.link_getYnorm(j, Math.abs(q)));
                yC.set(F_link.link_getYcrit(j, Math.abs(q)));
                flowClass = UP_CRITICAL;
            }
        } else {
            if ((h1 < (F_swmm5.Node_[n2].invertElev + F_swmm5.Link[j].offset2))) {
                flowClass = DN_DRY;
            } else if (R.dgt(z2, 0.0d)) {
                yN.set(F_link.link_getYnorm(j, Math.abs(q)));
                yC.set(F_link.link_getYcrit(j, Math.abs(q)));
                flowClass = DN_CRITICAL;
            }
        }
        return flowClass;
    }

    // dwflow.c:417
    static void findSurfArea(int j, double q, double length, DRef h1, DRef h2, DRef y1, DRef y2) {
        int n1 = 0;
        int n2 = 0;
        double flowDepth1 = 0.0d;
        double flowDepth2 = 0.0d;
        double flowDepthMid = 0.0d;
        double width1 = 0.0d;
        double width2 = 0.0d;
        double widthMid = 0.0d;
        double surfArea1 = 0.0d;
        double surfArea2 = 0.0d;
        DBox criticalDepth = new DBox(0.0d);
        DBox normalDepth = new DBox(0.0d);
        double fullDepth = 0.0d;
        DBox fasnh = new DBox(1.0d);
        TXsect xsect = F_swmm5.Link[j].xsect;
        n1 = F_swmm5.Link[j].node1;
        n2 = F_swmm5.Link[j].node2;
        flowDepth1 = y1.get();
        flowDepth2 = y2.get();
        normalDepth.@v = (((flowDepth1 + flowDepth2)) / 2.0d);
        criticalDepth.@v = normalDepth.v;
        fullDepth = xsect.yFull;
        if (((R.dge(flowDepth1, fullDepth)) && (R.dge(flowDepth2, fullDepth)))) {
            F_swmm5.Link[j].@flowClass = SUBCRITICAL;
        } else {
            F_swmm5.Link[j].@flowClass = getFlowClass(j, q, h1.get(), h2.get(), y1.get(), y2.get(), criticalDepth, normalDepth, fasnh);
        }
        int __sw1 = F_swmm5.Link[j].flowClass;
        if (__sw1 == SUBCRITICAL) {
            flowDepthMid = (0.5d * ((flowDepth1 + flowDepth2)));
            if ((flowDepthMid < 0.0001d)) {
                flowDepthMid = 0.0001d;
            }
            width1 = dwflow_getWidth(xsect, flowDepth1);
            width2 = dwflow_getWidth(xsect, flowDepth2);
            widthMid = dwflow_getWidth(xsect, flowDepthMid);
            surfArea1 = ((((width1 + widthMid)) * length) / 4.0d);
            surfArea2 = (((((widthMid + width2)) * length) / 4.0d) * fasnh.v);
        } else if (__sw1 == UP_CRITICAL) {
            flowDepth1 = criticalDepth.v;
            if ((normalDepth.v < criticalDepth.v)) {
                flowDepth1 = normalDepth.v;
            }
            flowDepth1 = (((R.dge((flowDepth1), (0.0001d))) ? (flowDepth1) : (0.0001d)));
            h1.set(((F_swmm5.Node_[n1].invertElev + F_swmm5.Link[j].offset1) + flowDepth1));
            flowDepthMid = (0.5d * ((flowDepth1 + flowDepth2)));
            if ((flowDepthMid < 0.0001d)) {
                flowDepthMid = 0.0001d;
            }
            width2 = dwflow_getWidth(xsect, flowDepth2);
            widthMid = dwflow_getWidth(xsect, flowDepthMid);
            surfArea2 = ((((widthMid + width2)) * length) * 0.5d);
        } else if (__sw1 == DN_CRITICAL) {
            flowDepth2 = criticalDepth.v;
            if ((normalDepth.v < criticalDepth.v)) {
                flowDepth2 = normalDepth.v;
            }
            flowDepth2 = (((R.dge((flowDepth2), (0.0001d))) ? (flowDepth2) : (0.0001d)));
            h2.set(((F_swmm5.Node_[n2].invertElev + F_swmm5.Link[j].offset2) + flowDepth2));
            width1 = dwflow_getWidth(xsect, flowDepth1);
            flowDepthMid = (0.5d * ((flowDepth1 + flowDepth2)));
            if ((flowDepthMid < 0.0001d)) {
                flowDepthMid = 0.0001d;
            }
            widthMid = dwflow_getWidth(xsect, flowDepthMid);
            surfArea1 = ((((width1 + widthMid)) * length) * 0.5d);
        } else if (__sw1 == UP_DRY) {
            flowDepth1 = 0.0001d;
            flowDepthMid = (0.5d * ((flowDepth1 + flowDepth2)));
            if ((flowDepthMid < 0.0001d)) {
                flowDepthMid = 0.0001d;
            }
            width1 = dwflow_getWidth(xsect, flowDepth1);
            width2 = dwflow_getWidth(xsect, flowDepth2);
            widthMid = dwflow_getWidth(xsect, flowDepthMid);
            surfArea2 = ((((widthMid + width2)) * length) / 4.0d);
            if ((F_swmm5.Link[j].offset1 <= 0.0d)) {
                surfArea1 = ((((width1 + widthMid)) * length) / 4.0d);
            }
        } else if (__sw1 == DN_DRY) {
            flowDepth2 = 0.0001d;
            flowDepthMid = (0.5d * ((flowDepth1 + flowDepth2)));
            if ((flowDepthMid < 0.0001d)) {
                flowDepthMid = 0.0001d;
            }
            width1 = dwflow_getWidth(xsect, flowDepth1);
            width2 = dwflow_getWidth(xsect, flowDepth2);
            widthMid = dwflow_getWidth(xsect, flowDepthMid);
            surfArea1 = ((((widthMid + width1)) * length) / 4.0d);
            if ((F_swmm5.Link[j].offset2 <= 0.0d)) {
                surfArea2 = ((((width2 + widthMid)) * length) / 4.0d);
            }
        } else if (__sw1 == DRY) {
            surfArea1 = ((0.0001d * length) / 2.0d);
            surfArea2 = surfArea1;
        }
        F_swmm5.Link[j].@surfArea1 = surfArea1;
        F_swmm5.Link[j].@surfArea2 = surfArea2;
        y1.set(flowDepth1);
        y2.set(flowDepth2);
    }

    // dwflow.c:554
    static double findLocalLosses(int j, double a1, double a2, double aMid, double q) {
        double losses = 0.0d;
        q = Math.abs(q);
        if (R.dgt(a1, 0.0001d)) {
            losses += (F_swmm5.Link[j].cLossInlet * ((q / a1)));
        }
        if (R.dgt(a2, 0.0001d)) {
            losses += (F_swmm5.Link[j].cLossOutlet * ((q / a2)));
        }
        if (R.dgt(aMid, 0.0001d)) {
            losses += (F_swmm5.Link[j].cLossAvg * ((q / aMid)));
        }
        return losses;
    }

    // dwflow.c:575
    static double getSlotWidth(TXsect xsect, double y) {
        double yNorm = (y / xsect.yFull);
        if ((((F_swmm5.SurchargeMethod != SLOT) || (F_xsect.xsect_isOpen(xsect.type) != 0)) || (yNorm < F_swmm5.CrownCutoff))) {
            return 0.0d;
        }
        if (R.dgt(yNorm, 1.78d)) {
            return (0.01d * xsect.wMax);
        }
        return ((xsect.wMax * 0.5423d) * Glibm.exp((-(Glibm.pow(yNorm, 2.4d)))));
    }

    // dwflow.c:592
    static double dwflow_getWidth(TXsect xsect, double y) {
        double wSlot = getSlotWidth(xsect, y);
        if (R.dgt(wSlot, 0.0d)) {
            return wSlot;
        }
        if (((R.dge((y / xsect.yFull), F_swmm5.CrownCutoff)) && (!(F_xsect.xsect_isOpen(xsect.type) != 0)))) {
            y = (F_swmm5.CrownCutoff * xsect.yFull);
        }
        return F_xsect.xsect_getWofY(xsect, y);
    }

    // dwflow.c:609
    static double dwflow_getArea(TXsect xsect, double y, double wSlot) {
        if (R.dge(y, xsect.yFull)) {
            return (xsect.aFull + (((y - xsect.yFull)) * wSlot));
        }
        return F_xsect.xsect_getAofY(xsect, y);
    }

    // dwflow.c:623
    static double getHydRad(TXsect xsect, double y) {
        if (R.dge(y, xsect.yFull)) {
            return xsect.rFull;
        }
        return F_xsect.xsect_getRofY(xsect, y);
    }

    // dwflow.c:637
    static double checkNormalFlow(int j, double q, double y1, double y2, double a1, double r1) {
        int check = 0;
        int k = F_swmm5.Link[j].subIndex;
        int n1 = F_swmm5.Link[j].node1;
        int n2 = F_swmm5.Link[j].node2;
        int hasOutfall = ((((F_swmm5.Node_[n1].type == OUTFALL) || (F_swmm5.Node_[n2].type == OUTFALL))) ? 1 : 0);
        double qNorm = 0.0d;
        double f1 = 0.0d;
        if ((((F_swmm5.NormalFlowLtd == SLOPE) || (F_swmm5.NormalFlowLtd == BOTH)) || (hasOutfall != 0))) {
            if ((y1 < y2)) {
                check = 1;
            }
        }
        if ((((!(check != 0)) && (((F_swmm5.NormalFlowLtd == FROUDE) || (F_swmm5.NormalFlowLtd == BOTH)))) && (!(hasOutfall != 0)))) {
            if (((R.dgt(y1, 0.0001d)) && (R.dgt(y2, 0.0001d)))) {
                f1 = F_link.link_getFroude(j, (q / a1), y1);
                if (R.dge(f1, 1.0d)) {
                    check = 1;
                }
            }
        }
        if ((check != 0)) {
            qNorm = ((F_swmm5.Conduit[k].beta * a1) * Glibm.pow(r1, (2.0d / 3.0d)));
            if ((qNorm < q)) {
                F_swmm5.Link[j].@normalFlow = 1;
                return qNorm;
            }
        }
        return q;
    }

}
