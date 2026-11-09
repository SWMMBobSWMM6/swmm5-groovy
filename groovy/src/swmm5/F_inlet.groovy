// Generated from EPA SWMM 5.2.4 (inlet.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_inlet {
    public static TInletDesign[] InletDesigns = null;
    public static int InletDesignCount = 0;
    public static int UsesInlets = 0;
    public static CPtr[] InletTypeWords = new CPtr[] { L718, L719, L0, L720, L721, L722, L723, null };
    public static CPtr[] GrateTypeWords = new CPtr[] { L724, L725, L726, L727, L728, L729, L730, L731, null };
    public static CPtr[] ThroatAngleWords = new CPtr[] { L732, L733, L734, null };
    public static CPtr[] PlacementTypeWords = new CPtr[] { L735, L736, L737, null };
    public static double[][] SplashCoeffs = new double[][] { new double[] { 2.22d, 4.03d, 0.65d, 0.06d }, new double[] { 0.74d, 2.44d, 0.27d, 0.02d }, new double[] { 1.76d, 3.12d, 0.45d, 0.03d }, new double[] { 0.30d, 4.85d, 1.31d, 0.15d }, new double[] { 0.99d, 2.64d, 0.36d, 0.03d }, new double[] { 0.51d, 2.34d, 0.2d, 0.01d }, new double[] { 0.28d, 2.28d, 0.18d, 0.01d } };
    public static double[] GrateOpeningRatios = new double[] { 0.90d, 0.80d, 0.60d, 0.35d, 0.17d, 0.34d, 0.80d, 1.00d };
    public static double Sx = 0.0d;
    public static double SL = 0.0d;
    public static double Sw = 0.0d;
    public static double a = 0.0d;
    public static double W = 0.0d;
    public static double T = 0.0d;
    public static double n = 0.0d;
    public static int Nsides = 0;
    public static double Tcrown = 0.0d;
    public static double Beta = 0.0d;
    public static double Qfactor = 0.0d;
    public static TXsect xsect = null;
    public static double[] InletFlow = null;
    public static TInlet FirstInlet = null;

    // inlet.c:257
    static int inlet_create(int numInlets) {
        int i = 0;
        InletDesigns = (null);
        InletFlow = (null);
        InletDesignCount = 0;
        UsesInlets = 0;
        FirstInlet = (null);
        InletDesigns = R.fill(new TInletDesign[(int) (numInlets)], (() -> new TInletDesign()));
        if ((InletDesigns == null)) {
            return ERR_MEMORY;
        }
        InletDesignCount = numInlets;
        InletFlow = new double[(int) (F_swmm5.Nobjects[NODE])];
        if ((InletFlow == null)) {
            return ERR_MEMORY;
        }
        L_f1: for (i = 0; (i < InletDesignCount); i++) {
            InletDesigns[i].@customCurve = (-1);
            InletDesigns[i].curbInlet.@length = 0.0d;
            InletDesigns[i].grateInlet.@length = 0.0d;
            InletDesigns[i].slottedInlet.@length = 0.0d;
            InletDesigns[i].@type = CUSTOM_INLET;
        }
        return 0;
    }

    // inlet.c:291
    static void inlet_delete() {
        TInlet inlet = FirstInlet;
        TInlet nextInlet = null;
        L_w1: while ((inlet != null)) {
            nextInlet = inlet.nextInlet;
            R.free(inlet);
            inlet = nextInlet;
        }
        FirstInlet = (null);
        if ((InletFlow != null)) {
            R.free(InletFlow);
            InletFlow = (null);
        }
        if ((InletDesigns != null)) {
            R.free(InletDesigns);
            InletDesigns = (null);
        }
    }

    // inlet.c:313
    static int inlet_readDesignParams(CPtr[] tok, int ntoks) {
        int i = 0;
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        i = F_project.project_findObject(INLET, tok[0]);
        if ((i < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        InletDesigns[i].@ID = F_project.project_findID(INLET, tok[0]);
        InletDesigns[i].@type = F_input.findmatch(tok[1], InletTypeWords);
        int __sw1 = InletDesigns[i].type;
        if (__sw1 == GRATE_INLET || __sw1 == DROP_GRATE_INLET) {
            return readGrateInletParams(i, tok, ntoks);
        } else if (__sw1 == CURB_INLET || __sw1 == DROP_CURB_INLET) {
            return readCurbInletParams(i, tok, ntoks);
        } else if (__sw1 == SLOTTED_INLET) {
            return readSlottedInletParams(i, tok, ntoks);
        } else if (__sw1 == CUSTOM_INLET) {
            return readCustomInletParams(i, tok, ntoks);
        } else {
            return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
        }
        throw new IllegalStateException('inlet_readDesignParams: no return');
    }

    // inlet.c:362
    static int inlet_readUsageParams(CPtr[] tok, int ntoks) {
        int linkIndex = 0;
        int designIndex = 0;
        int nodeIndex = 0;
        IBox numInlets = new IBox(1);
        int placement = AUTOMATIC;
        DBox flowLimit = new DBox(0.0d);
        DBox pctClogged = new DBox(0.0d);
        DBox aLocal = new DBox(0.0d);
        DBox wLocal = new DBox(0.0d);
        TInlet inlet = null;
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        linkIndex = F_project.project_findObject(LINK, tok[0]);
        if ((linkIndex < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        designIndex = F_project.project_findObject(INLET, tok[1]);
        if ((designIndex < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[1]);
        }
        nodeIndex = F_project.project_findObject(NODE, tok[2]);
        if ((nodeIndex < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[2]);
        }
        if ((ntoks > 3)) {
            if (((!(F_input.getInt(tok[3], numInlets) != 0)) || (numInlets.v < 1))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[3]);
            }
        }
        if ((ntoks > 4)) {
            if ((((!(F_input.getDouble(tok[4], pctClogged) != 0)) || (pctClogged.v < 0.0d)) || (R.dgt(pctClogged.v, 99.0d)))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[4]);
            }
        }
        if ((ntoks > 5)) {
            if (((!(F_input.getDouble(tok[5], flowLimit) != 0)) || (flowLimit.v < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[5]);
            }
        }
        if ((ntoks > 6)) {
            if (((!(F_input.getDouble(tok[6], aLocal) != 0)) || (aLocal.v < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[6]);
            }
        }
        if ((ntoks > 7)) {
            if (((!(F_input.getDouble(tok[7], wLocal) != 0)) || (wLocal.v < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[7]);
            }
        }
        if ((ntoks > 8)) {
            placement = F_input.findmatch(tok[8], PlacementTypeWords);
            if ((placement < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[8]);
            }
        }
        inlet = F_swmm5.Link[linkIndex].inlet;
        if ((inlet == null)) {
            inlet = new TInlet();
            if (!(inlet != null)) {
                return F_error.error_setInpError(ERR_MEMORY, L0);
            }
            F_swmm5.Link[linkIndex].@inlet = inlet;
            inlet.@nextInlet = FirstInlet;
            FirstInlet = inlet;
        }
        inlet.@linkIndex = linkIndex;
        inlet.@designIndex = designIndex;
        inlet.@nodeIndex = nodeIndex;
        inlet.@numInlets = numInlets.v;
        inlet.@placement = placement;
        inlet.@clogFactor = (1.0d - ((pctClogged.v / 100.0d)));
        inlet.@flowLimit = (flowLimit.v / F_swmm5.UCF(FLOW));
        inlet.@localDepress = (aLocal.v / F_swmm5.UCF(LENGTH));
        inlet.@localWidth = (wLocal.v / F_swmm5.UCF(LENGTH));
        inlet.@flowFactor = 0.0d;
        inlet.@backflowRatio = 0.0d;
        initInletStats(inlet);
        UsesInlets = 1;
        return 0;
    }

    // inlet.c:464
    static void inlet_validate() {
        int i = 0;
        int j = 0;
        int inletType = 0;
        int inletValid = 0;
        TInlet inlet = null;
        TInlet prevInlet = null;
        if (!(UsesInlets != 0)) {
            return;
        }
        prevInlet = FirstInlet;
        inlet = FirstInlet;
        L_w1: while ((inlet != null)) {
            inletValid = 0;
            i = inlet.linkIndex;
            xsect = F_swmm5.Link[i].xsect;
            inletType = InletDesigns[inlet.designIndex].type;
            if ((inletType == CUSTOM_INLET)) {
                j = InletDesigns[inlet.designIndex].customCurve;
                if ((j >= 0)) {
                    if (((F_swmm5.Curve[j].curveType == DIVERSION_CURVE) || (F_swmm5.Curve[j].curveType == RATING_CURVE))) {
                        inletValid = 1;
                    }
                }
            } else if (((((xsect.type == TRAPEZOIDAL) || (xsect.type == RECT_OPEN))) && (((inletType == DROP_GRATE_INLET) || (inletType == DROP_CURB_INLET))))) {
                inletValid = 1;
            } else if ((((xsect.type == STREET_XSECT) && (inletType != DROP_GRATE_INLET)) && (inletType != DROP_CURB_INLET))) {
                inletValid = 1;
            }
            if ((inletValid != 0)) {
                F_swmm5.Node_[F_swmm5.Link[i].node2].@inlet = BYPASS;
                F_swmm5.Node_[inlet.nodeIndex].@inlet = CAPTURE;
                inlet.@backflow = 0.0d;
                getConduitGeometry(inlet);
                inlet.@flowFactor = ((((0.56d / n)) * Glibm.pow(SL, 0.5d)) * Glibm.pow(Sx, 1.67d));
                prevInlet = inlet;
                inlet = inlet.nextInlet;
            } else {
                F_report.report_writeWarningMsg(L187, F_swmm5.Link[i].ID);
                if ((inlet === FirstInlet)) {
                    FirstInlet = inlet.nextInlet;
                    prevInlet = FirstInlet;
                    R.free(inlet);
                    inlet = FirstInlet;
                } else {
                    prevInlet.@nextInlet = inlet.nextInlet;
                    R.free(inlet);
                    inlet = prevInlet.nextInlet;
                }
                F_swmm5.Link[i].@inlet = (null);
            }
        }
        getBackflowRatios();
    }

    // inlet.c:555
    static void inlet_findCapturedFlows(double tStep) {
        int i = 0;
        int j = 0;
        int m = 0;
        int placement = 0;
        double q = 0.0d;
        TInlet inlet = null;
        if (!(UsesInlets != 0)) {
            return;
        }
        R.memset(InletFlow, 0, (F_swmm5.Nobjects[NODE] * 8L));
        if ((F_swmm5.RouteModel != DW)) {
            L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
                F_swmm5.Node_[j].@inflow = (((R.dge((0.0d), (F_swmm5.Node_[j].newLatFlow))) ? (0.0d) : (F_swmm5.Node_[j].newLatFlow)));
            }
            L_f2: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
                F_swmm5.Node_[F_swmm5.Link[i].node2].@inflow = (F_swmm5.Node_[F_swmm5.Link[i].node2].inflow + (((R.dge((0.0d), (F_swmm5.Link[i].newFlow))) ? (0.0d) : (F_swmm5.Link[i].newFlow))));
            }
        }
        L_f3: for (inlet = FirstInlet; (inlet != null); inlet = inlet.nextInlet) {
            i = inlet.linkIndex;
            j = F_swmm5.Link[i].node2;
            m = inlet.nodeIndex;
            placement = getInletPlacement(inlet, j);
            if ((InletDesigns[inlet.designIndex].type == CUSTOM_INLET)) {
                q = Math.abs(F_swmm5.Link[i].newFlow);
                inlet.@flowCapture = getCustomCapturedFlow(inlet, q, F_swmm5.Node_[j].newDepth);
            } else if ((placement == ON_GRADE)) {
                q = Math.abs(F_swmm5.Link[i].newFlow);
                inlet.@flowCapture = getOnGradeCapturedFlow(inlet, q, F_swmm5.Node_[j].newDepth);
            } else {
                q = F_swmm5.Node_[j].inflow;
                inlet.@flowCapture = getOnSagCapturedFlow(inlet, q, F_swmm5.Node_[j].newDepth);
            }
            if ((Math.abs(inlet.flowCapture) < 0.0001d)) {
                inlet.@flowCapture = 0.0d;
            }
            InletFlow[j] += inlet.flowCapture;
            inlet.@backflow = (F_swmm5.Node_[m].overflow * inlet.backflowRatio);
            if ((Math.abs(inlet.backflow) < 0.0001d)) {
                inlet.@backflow = 0.0d;
            }
        }
        L_f4: for (inlet = FirstInlet; (inlet != null); inlet = inlet.nextInlet) {
            i = inlet.linkIndex;
            j = F_swmm5.Link[i].node2;
            m = inlet.nodeIndex;
            if (((F_swmm5.RouteModel != DW) && (getInletPlacement(inlet, j) == ON_SAG))) {
                q = (F_swmm5.Node_[j].newVolume / tStep);
                q += (((R.dge((F_swmm5.Node_[j].inflow), (0.0d))) ? (F_swmm5.Node_[j].inflow) : (0.0d)));
                if (R.dgt(InletFlow[j], q)) {
                    inlet.@flowCapture = (inlet.flowCapture * (q / InletFlow[j]));
                }
            }
            F_swmm5.Node_[j].@newLatFlow = (F_swmm5.Node_[j].newLatFlow - ((inlet.flowCapture - inlet.backflow)));
            F_swmm5.Node_[m].@newLatFlow = (F_swmm5.Node_[m].newLatFlow + inlet.flowCapture);
            if (R.dgt(F_swmm5.getDateTime(F_swmm5.NewRoutingTime), F_swmm5.ReportStart)) {
                updateInletStats(inlet, Math.abs(F_swmm5.Link[i].newFlow));
            }
        }
    }

    // inlet.c:655
    static void inlet_adjustQualInflows() {
        int i = 0;
        int j = 0;
        int m = 0;
        int p = 0;
        double qNet = 0.0d;
        TInlet inlet = null;
        if (!(UsesInlets != 0)) {
            return;
        }
        if (((F_swmm5.IgnoreQuality != 0) || (F_swmm5.Nobjects[POLLUT] == 0))) {
            return;
        }
        L_f1: for (inlet = FirstInlet; (inlet != null); inlet = inlet.nextInlet) {
            i = inlet.linkIndex;
            j = F_swmm5.Link[i].node2;
            m = inlet.nodeIndex;
            qNet = (inlet.flowCapture - inlet.backflow);
            if (R.dgt(qNet, 0.0d)) {
                F_swmm5.Node_[m].@qualInflow = (F_swmm5.Node_[m].qualInflow + qNet);
                L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                    F_swmm5.Node_[m].newQual[p] += (qNet * F_swmm5.Node_[j].oldQual[p]);
                }
            } else {
                qNet = (-qNet);
                F_swmm5.Node_[j].@qualInflow = (F_swmm5.Node_[j].qualInflow + qNet);
                L_f3: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                    F_swmm5.Node_[j].newQual[p] += (qNet * F_swmm5.Node_[m].oldQual[p]);
                }
            }
        }
    }

    // inlet.c:706
    static void inlet_adjustQualOutflows() {
        int j = 0;
        int p = 0;
        double q = 0.0d;
        double w = 0.0d;
        TInlet inlet = null;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            if ((F_swmm5.Node_[j].inlet == CAPTURE)) {
                q = F_swmm5.Node_[j].overflow;
                if (R.dgt(q, 0.0d)) {
                    F_massbal.StepFlowTotals.@flooding = (F_massbal.StepFlowTotals.flooding - q);
                    if (!(F_swmm5.IgnoreQuality != 0)) {
                        L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                            w = (q * F_swmm5.Node_[j].newQual[p]);
                            F_massbal.StepQualTotals[p].@flooding = (F_massbal.StepQualTotals[p].flooding - w);
                        }
                    }
                }
            }
        }
        if (((!(F_swmm5.IgnoreQuality != 0)) && (F_swmm5.Nobjects[POLLUT] > 0))) {
            L_f3: for (inlet = FirstInlet; (inlet != null); inlet = inlet.nextInlet) {
                j = F_swmm5.Link[inlet.linkIndex].node2;
                q = (inlet.flowCapture - inlet.backflow);
                if (((R.dgt(q, 0.0d)) && (F_swmm5.Node_[j].newLatFlow < 0.0d))) {
                    L_f4: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                        w = (q * F_swmm5.Node_[j].newQual[p]);
                        F_massbal.StepQualTotals[p].@outflow = (F_massbal.StepQualTotals[p].outflow - w);
                    }
                }
            }
        }
    }

    // inlet.c:774
    static void inlet_writeStatsReport() {
        int j = 0;
        int header = 0;
        if ((F_swmm5.Nobjects[STREET] == 0)) {
            return;
        }
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            if ((F_swmm5.Link[j].xsect.type == STREET_XSECT)) {
                if (!(header != 0)) {
                    writeStreetStatsHeader();
                    header = 1;
                }
                writeStreetStats(j);
            }
        }
        F_report.report_writeLine(L0);
    }

    // inlet.c:801
    static double inlet_capturedFlow(int i) {
        if ((F_swmm5.Link[i].inlet != null)) {
            return F_swmm5.Link[i].inlet.flowCapture;
        }
        return 0.0d;
    }

    // inlet.c:814
    static int readGrateInletParams(int i, CPtr[] tok, int ntoks) {
        int grateType = 0;
        DBox width = new DBox(0.0d);
        DBox length = new DBox(0.0d);
        DBox areaRatio = new DBox(0.0d);
        DBox vSplash = new DBox(0.0d);
        if ((ntoks < 5)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        if (((!(F_input.getDouble(tok[2], length) != 0)) || (length.v <= 0.0d))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[2]);
        }
        if (((!(F_input.getDouble(tok[3], width) != 0)) || (width.v <= 0.0d))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[3]);
        }
        grateType = F_input.findmatch(tok[4], GrateTypeWords);
        if ((grateType < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[4]);
        }
        if ((grateType == GENERIC)) {
            if ((ntoks < 6)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            if ((((!(F_input.getDouble(tok[5], areaRatio) != 0)) || (areaRatio.v <= 0.0d)) || (R.dgt(areaRatio.v, 1.0d)))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[5]);
            }
            if ((ntoks > 6)) {
                if (((!(F_input.getDouble(tok[6], vSplash) != 0)) || (vSplash.v < 0.0d))) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[6]);
                }
            }
        }
        InletDesigns[i].grateInlet.@length = (length.v / F_swmm5.UCF(LENGTH));
        InletDesigns[i].grateInlet.@width = (width.v / F_swmm5.UCF(LENGTH));
        InletDesigns[i].grateInlet.@type = grateType;
        InletDesigns[i].grateInlet.@fracOpenArea = areaRatio.v;
        InletDesigns[i].grateInlet.@splashVeloc = (vSplash.v / F_swmm5.UCF(LENGTH));
        if (((InletDesigns[i].type == GRATE_INLET) && (R.dgt(InletDesigns[i].curbInlet.length, 0.0d)))) {
            InletDesigns[i].@type = COMBO_INLET;
        }
        return 0;
    }

    // inlet.c:868
    static int readCurbInletParams(int i, CPtr[] tok, int ntoks) {
        int throatAngle = 0;
        DBox height = new DBox(0.0d);
        DBox length = new DBox(0.0d);
        if ((ntoks < 4)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        if (((!(F_input.getDouble(tok[2], length) != 0)) || (length.v <= 0.0d))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[2]);
        }
        if (((!(F_input.getDouble(tok[3], height) != 0)) || (height.v <= 0.0d))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[3]);
        }
        throatAngle = VERTICAL_THROAT;
        if (((InletDesigns[i].type == CURB_INLET) && (ntoks > 4))) {
            throatAngle = F_input.findmatch(tok[4], ThroatAngleWords);
            if ((throatAngle < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[4]);
            }
        }
        InletDesigns[i].curbInlet.@length = (length.v / F_swmm5.UCF(LENGTH));
        InletDesigns[i].curbInlet.@height = (height.v / F_swmm5.UCF(LENGTH));
        InletDesigns[i].curbInlet.@throatAngle = throatAngle;
        if (((InletDesigns[i].type == CURB_INLET) && (R.dgt(InletDesigns[i].grateInlet.length, 0.0d)))) {
            InletDesigns[i].@type = COMBO_INLET;
        }
        return 0;
    }

    // inlet.c:911
    static int readSlottedInletParams(int i, CPtr[] tok, int ntoks) {
        DBox width = new DBox(0.0d);
        DBox length = new DBox(0.0d);
        if ((ntoks < 4)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        if (((!(F_input.getDouble(tok[2], length) != 0)) || (length.v <= 0.0d))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[2]);
        }
        if (((!(F_input.getDouble(tok[3], width) != 0)) || (width.v <= 0.0d))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[3]);
        }
        InletDesigns[i].slottedInlet.@length = (length.v / F_swmm5.UCF(LENGTH));
        InletDesigns[i].slottedInlet.@width = (width.v / F_swmm5.UCF(LENGTH));
        return 0;
    }

    // inlet.c:939
    static int readCustomInletParams(int i, CPtr[] tok, int ntoks) {
        int c = 0;
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        } else {
            c = F_project.project_findObject(CURVE, tok[2]);
            if ((c < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
        }
        InletDesigns[i].@customCurve = c;
        return 0;
    }

    // inlet.c:962
    static void initInletStats(TInlet inlet) {
        if ((inlet != null)) {
            inlet.@flowCapture = 0.0d;
            inlet.@backflow = 0.0d;
            inlet.stats.@flowPeriods = 0;
            inlet.stats.@capturePeriods = 0;
            inlet.stats.@backflowPeriods = 0;
            inlet.stats.@peakFlow = 0.0d;
            inlet.stats.@peakFlowCapture = 0;
            inlet.stats.@avgFlowCapture = 0;
            inlet.stats.@bypassFreq = 0;
        }
    }

    // inlet.c:985
    static void updateInletStats(TInlet inlet, double q) {
        double qCapture = inlet.flowCapture;
        double qBackflow = inlet.backflow;
        double qNet = (qCapture - qBackflow);
        double qBypass = (q - qNet);
        double fCapture = 0.0d;
        if (((q < 0.001d) && (qBackflow <= 0.0d))) {
            return;
        }
        inlet.stats.@flowPeriods = inlet.stats.flowPeriods + 1;
        if (R.dgt(qNet, 0.0d)) {
            inlet.stats.@capturePeriods = inlet.stats.capturePeriods + 1;
            fCapture = (qNet / q);
            fCapture = (((((fCapture) <= (1.0d))) ? (fCapture) : (1.0d)));
            inlet.stats.@avgFlowCapture = (inlet.stats.avgFlowCapture + fCapture);
            if (R.dgt(qBypass, 0.001d)) {
                inlet.stats.@bypassFreq = inlet.stats.bypassFreq + 1.0d;
            }
        } else {
            inlet.stats.@backflowPeriods = inlet.stats.backflowPeriods + 1;
        }
        if (R.dgt(q, inlet.stats.peakFlow)) {
            inlet.stats.@peakFlow = q;
            inlet.stats.@peakFlowCapture = (fCapture * 100.0d);
        }
    }

    // inlet.c:1026
    static void writeStreetStatsHeader() {
        F_report.report_writeLine(L0);
        F_report.report_writeLine(L188);
        F_report.report_writeLine(L189);
        F_report.report_writeLine(L188);
        F_report.report_writeLine(L0);
        R.fprintf(F_swmm5.Frpt.file, L190);
        if ((F_swmm5.UnitSystem == US)) {
            R.fprintf(F_swmm5.Frpt.file, L191, F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L192, F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        }
        R.fprintf(F_swmm5.Frpt.file, L193);
    }

    // inlet.c:1055
    static void writeStreetStats(int link) {
        int k = 0;
        int t = 0;
        int placement = 0;
        double maxSpread = 0.0d;
        double maxDepth = 0.0d;
        double maxFlow = 0.0d;
        double fp = 0.0d;
        double cp = 0.0d;
        double afc = 0.0d;
        double bpf = 0.0d;
        TInlet inlet = null;
        k = F_swmm5.Link[link].subIndex;
        t = F_swmm5.Link[link].xsect.transect;
        inlet = F_swmm5.Link[link].inlet;
        maxFlow = F_stats.LinkStats[link].maxFlow;
        maxDepth = F_stats.LinkStats[link].maxDepth;
        maxSpread = (F_xsect.xsect_getWofY(F_swmm5.Link[link].xsect, maxDepth) / F_swmm5.Street[t].sides);
        maxSpread = (((((maxSpread) <= (F_swmm5.Street[t].width))) ? (maxSpread) : (F_swmm5.Street[t].width)));
        R.fprintf(F_swmm5.Frpt.file, L194, F_swmm5.Link[link].ID);
        R.fprintf(F_swmm5.Frpt.file, L195, (maxFlow * F_swmm5.UCF(FLOW)));
        R.fprintf(F_swmm5.Frpt.file, L195, (maxSpread * F_swmm5.UCF(LENGTH)));
        R.fprintf(F_swmm5.Frpt.file, L195, (maxDepth * F_swmm5.UCF(LENGTH)));
        if ((inlet != null)) {
            R.fprintf(F_swmm5.Frpt.file, L196, InletDesigns[inlet.designIndex].ID);
            placement = getInletPlacement(inlet, F_swmm5.Link[inlet.linkIndex].node2);
            if ((placement == ON_GRADE)) {
                R.fprintf(F_swmm5.Frpt.file, L197);
            } else {
                R.fprintf(F_swmm5.Frpt.file, L198);
            }
            R.fprintf(F_swmm5.Frpt.file, L199, inlet.numInlets);
            fp = (inlet.stats.flowPeriods / 100.0d);
            if (R.dgt(fp, 0.0d)) {
                cp = (inlet.stats.capturePeriods / 100.0d);
                R.fprintf(F_swmm5.Frpt.file, L200, inlet.stats.peakFlowCapture);
                if (R.dgt(cp, 0.0d)) {
                    afc = (inlet.stats.avgFlowCapture / cp);
                    bpf = (inlet.stats.bypassFreq / cp);
                }
                R.fprintf(F_swmm5.Frpt.file, L200, afc);
                R.fprintf(F_swmm5.Frpt.file, L200, bpf);
                R.fprintf(F_swmm5.Frpt.file, L200, (inlet.stats.backflowPeriods / fp));
                R.fprintf(F_swmm5.Frpt.file, L200, ((((((maxFlow / F_swmm5.Street[t].sides)) * F_swmm5.UCF(FLOW)) * 0.01d) * inlet.stats.peakFlowCapture) / inlet.numInlets));
                R.fprintf(F_swmm5.Frpt.file, L200, (((maxFlow * F_swmm5.UCF(FLOW)) * 0.01d) * ((100.0d - inlet.stats.peakFlowCapture))));
            }
        }
    }

    // inlet.c:1129
    static int getInletPlacement(TInlet inlet, int j) {
        if ((inlet.placement == AUTOMATIC)) {
            if ((F_swmm5.Node_[j].degree > 0)) {
                return ON_GRADE;
            } else {
                return ON_SAG;
            }
        } else {
            return inlet.placement;
        }
        throw new IllegalStateException('getInletPlacement: no return');
    }

    // inlet.c:1147
    static void getConduitGeometry(TInlet inlet) {
        int linkIndex = inlet.linkIndex;
        int t = 0;
        int k = F_swmm5.Link[linkIndex].subIndex;
        SL = F_swmm5.Conduit[k].slope;
        Beta = F_swmm5.Conduit[k].beta;
        xsect = F_swmm5.Link[linkIndex].xsect;
        if ((xsect.type == STREET_XSECT)) {
            t = xsect.transect;
            Sx = F_swmm5.Street[t].slope;
            a = F_swmm5.Street[t].gutterDepression;
            W = F_swmm5.Street[t].gutterWidth;
            n = F_swmm5.Street[t].roughness;
            Nsides = F_swmm5.Street[t].sides;
            Tcrown = F_swmm5.Street[t].width;
            Qfactor = inlet.flowFactor;
            if (((inlet != null) && (R.dgt((inlet.localDepress * inlet.localWidth), 0)))) {
                a += inlet.localDepress;
                W = inlet.localWidth;
            }
            if (R.dgt((W * a), 0.0d)) {
                Sw = (Sx + (a / W));
            } else {
                Sw = Sx;
            }
        } else {
            a = 0.0d;
            W = 0.0d;
            n = F_swmm5.Conduit[k].roughness;
            Nsides = 1;
            Sx = 0.01d;
            Sw = Sx;
        }
    }

    // inlet.c:1200
    static double getFlowSpread(double Q) {
        int iter = 0;
        double f = 0.0d;
        double f1 = 0.0d;
        double Sr = 0.0d;
        double Ts1 = 0.0d;
        double Ts2 = 0.0d;
        double Tw = 0.0d;
        double Qs = 0.0d;
        double Eo = 0.0d;
        f = Qfactor;
        if ((a == 0.0d)) {
            Ts1 = Glibm.pow((Q / f), 0.375d);
        } else {
            f1 = (f * Glibm.pow((((a / W)) / Sx), 1.67d));
            Tw = Glibm.pow((Q / f1), 0.375d);
            if ((Tw <= W)) {
                Ts1 = Tw;
            } else {
                Sr = (((Sx + (a / W))) / Sx);
                iter = 1;
                Ts1 = (Glibm.pow((Q / f), 0.375d) - W);
                if ((Ts1 <= 0)) {
                    Ts1 = (Tw - W);
                }
                L_w1: while ((iter < 11)) {
                    Eo = getEo(Sr, Ts1, W);
                    Qs = (((1.0d - Eo)) * Q);
                    Ts2 = Glibm.pow((Qs / f), 0.375d);
                    if ((Math.abs((Ts2 - Ts1)) < 0.01d)) {
                        break L_w1;
                    }
                    Ts1 = Ts2;
                    iter++;
                }
                Ts1 = (Ts2 + W);
            }
        }
        return (((((Ts1) <= (Tcrown))) ? (Ts1) : (Tcrown)));
    }

    // inlet.c:1248
    static double getEo(double Sr, double Ts, double w) {
        double x = 0.0d;
        x = (Sr / ((Ts / w)));
        x = (Glibm.pow(((1.0d + x)), 2.67d) - 1.0d);
        x = (1.0d + (Sr / x));
        return (1.0d / x);
    }

    // inlet.c:1267
    static double getOnGradeCapturedFlow(TInlet inlet, double q, double d) {
        int i = 0;
        int linkIndex = 0;
        double qApproach = 0.0d;
        double qc = 0.0d;
        double qCaptured = 0.0d;
        double qBypassed = 0.0d;
        double qMax = 0.0d;
        if ((inlet.numInlets == 0)) {
            return 0.0d;
        }
        linkIndex = inlet.linkIndex;
        qApproach = q;
        if ((qApproach < 0.001d)) {
            return 0.0d;
        }
        getConduitGeometry(inlet);
        qApproach = (qApproach / Nsides);
        qBypassed = qApproach;
        qCaptured = 0.0d;
        qMax = 10000000000.0d;
        if (R.dgt(inlet.flowLimit, 0.0d)) {
            qMax = inlet.flowLimit;
        }
        L_f1: for (i = 1; (i <= inlet.numInlets); i++) {
            qc = (getOnGradeInletCapture(inlet.designIndex, qBypassed, d) * inlet.clogFactor);
            qc = (((((qc) <= (qMax))) ? (qc) : (qMax)));
            qc = (((((qc) <= (qBypassed))) ? (qc) : (qBypassed)));
            qCaptured += qc;
            qBypassed -= qc;
            if ((qBypassed < 0.001d)) {
                break L_f1;
            }
        }
        return (qCaptured *= Nsides);
    }

    // inlet.c:1325
    static double getOnGradeInletCapture(int i, double Q, double d) {
        double Q1 = Q;
        double Qc = 0.0d;
        double Lsweep = 0.0d;
        double Lcurb = 0.0d;
        double Lgrate = 0.0d;
        if ((InletDesigns[i].type == DROP_CURB_INLET)) {
            Qc = getOnSagInletCapture(i, d);
            return (((((Qc) <= (Q))) ? (Qc) : (Q)));
        }
        if ((InletDesigns[i].type == DROP_GRATE_INLET)) {
            Qc = getGrateInletCapture(i, Q);
            return (((((Qc) <= (Q))) ? (Qc) : (Q)));
        }
        T = getFlowSpread(Q);
        if ((InletDesigns[i].type == SLOTTED_INLET)) {
            Qc = getCurbInletCapture(Q, InletDesigns[i].slottedInlet.length);
            return (((((Qc) <= (Q))) ? (Qc) : (Q)));
        }
        Lcurb = InletDesigns[i].curbInlet.length;
        Lgrate = InletDesigns[i].grateInlet.length;
        if (R.dgt(Lcurb, 0.0d)) {
            Lsweep = (Lcurb - Lgrate);
            if (R.dgt(Lsweep, 0.0d)) {
                Qc = getCurbInletCapture(Q1, Lsweep);
                Q1 -= Qc;
            }
        }
        if (((R.dgt(Lgrate, 0.0d)) && (R.dgt(Q1, 0.0d)))) {
            if ((Q1 != Q)) {
                T = getFlowSpread(Q1);
            }
            Qc += (getGrateInletCapture(i, Q1));
        }
        return Qc;
    }

    // inlet.c:1387
    static double getGrateInletCapture(int i, double Q) {
        int grateType = 0;
        double Lg = 0.0d;
        double Wg = 0.0d;
        double A = 0.0d;
        double Y = 0.0d;
        double Eo = 0.0d;
        double V = 0.0d;
        double Vo = 0.0d;
        double Qo = Q;
        double Rf = 1.0d;
        double Rs = 0.0d;
        Lg = InletDesigns[i].grateInlet.length;
        Wg = InletDesigns[i].grateInlet.width;
        if (((xsect.type == TRAPEZOIDAL) || (xsect.type == RECT_OPEN))) {
            A = F_xsect.xsect_getAofS(xsect, (Q / Beta));
            Y = F_xsect.xsect_getYofA(xsect, A);
            T = F_xsect.xsect_getWofY(xsect, Y);
            Eo = (((Beta * Glibm.pow((Y * Wg), 1.67d)) / Glibm.pow((Wg + (2 * Y)), 0.67d)) / Q);
            if ((((R.dgt(Wg, (0.99d * xsect.yBot))) && (xsect.type == TRAPEZOIDAL)) && (R.dgt(xsect.sBot, 0.0d)))) {
                Wg = xsect.yBot;
                Sx = (1.0d / xsect.sBot);
            }
        } else if ((a == 0.0d)) {
            A = (((T * T) * Sx) / 2.0d);
            Eo = getGutterFlowRatio(Wg);
            if (R.dge(T, Tcrown)) {
                Qo = (Qfactor * Glibm.pow(Tcrown, 2.67d));
            }
        } else {
            if ((T <= W)) {
                A = (((T * T) * Sw) / 2.0d);
            } else {
                A = (((((T * T) * Sx) + (a * W))) / 2.0d);
            }
            Eo = getGutterFlowRatio(W);
            if ((Eo < 1.0d)) {
                if (R.dge(T, Tcrown)) {
                    Qo = ((Qfactor * Glibm.pow(Tcrown, 2.67d)) / ((1.0d - Eo)));
                }
                Eo = (Eo * getGutterAreaRatio(Wg, A));
            }
        }
        V = (Qo / A);
        grateType = InletDesigns[i].grateInlet.type;
        if (((grateType < 0) || (grateType == GENERIC))) {
            Vo = InletDesigns[i].grateInlet.splashVeloc;
        } else {
            Vo = getSplashOverVelocity(grateType, Lg);
        }
        if (R.dgt(V, Vo)) {
            Rf = (1.0d - (0.09d * ((V - Vo))));
        }
        if ((Eo < 1.0d)) {
            Rs = (1.0d / ((1.0d + ((((0.15d * Glibm.pow(V, 1.8d)) / Sx) / Glibm.pow(Lg, 2.3d))))));
        }
        return (Q * (((Rf * Eo) + (Rs * ((1.0d - Eo))))));
    }

    // inlet.c:1477
    static double getCurbInletCapture(double Q, double L) {
        double Se = Sx;
        double Lt = 0.0d;
        double Sr = 0.0d;
        double Eo = 0.0d;
        double E = 1.0d;
        if (R.dgt(a, 0.0d)) {
            Sr = (Sw / Sx);
            Eo = getEo(Sr, (T - W), W);
            Se = (Sx + (((a / W)) * Eo));
        }
        Lt = (((0.6d * Glibm.pow(Q, 0.42d)) * Glibm.pow(SL, 0.3d)) * Glibm.pow((1.0d / ((n * Se))), 0.6d));
        if ((L < Lt)) {
            E = (1.0d - ((L / Lt)));
            E = (1 - Glibm.pow(E, 1.8d));
        }
        E = (((((E) <= (1.0d))) ? (E) : (1.0d)));
        E = (((R.dge((E), (0.0d))) ? (E) : (0.0d)));
        return (E * Q);
    }

    // inlet.c:1518
    static double getGutterFlowRatio(double w) {
        if ((T <= w)) {
            return 1.0d;
        } else if (R.dgt(a, 0.0d)) {
            return getEo((Sw / Sx), (T - w), w);
        } else {
            return (1.0d - Glibm.pow(((1.0d - (w / T))), 2.67d));
        }
        throw new IllegalStateException('getGutterFlowRatio: no return');
    }

    // inlet.c:1535
    static double getGutterAreaRatio(double Wg, double A) {
        double As = 0.0d;
        double Ag = 0.0d;
        if (R.dge(Wg, W)) {
            return 1.0d;
        }
        if ((T <= Wg)) {
            return 1.0d;
        }
        if ((T <= W)) {
            return (Wg / T);
        }
        As = ((0.5d * (((((T - W))) * (((T - W)))))) * Sx);
        Ag = (Wg * (((((T * Sx)) + a) - (((Wg * Sw) / 2.0d)))));
        return (Ag / ((A - As)));
    }

    // inlet.c:1557
    static double getSplashOverVelocity(int grateType, double L) {
        return (((SplashCoeffs[grateType][0] + (SplashCoeffs[grateType][1] * L)) - ((SplashCoeffs[grateType][2] * L) * L)) + (((SplashCoeffs[grateType][3] * L) * L) * L));
    }

    // inlet.c:1574
    static double getOnSagCapturedFlow(TInlet inlet, double q, double d) {
        int linkIndex = 0;
        int designIndex = 0;
        int totalInlets = 0;
        double qCaptured = 0.0d;
        double qMax = 10000000000.0d;
        if ((inlet.numInlets == 0)) {
            return 0.0d;
        }
        totalInlets = (Nsides * inlet.numInlets);
        linkIndex = inlet.linkIndex;
        designIndex = inlet.designIndex;
        getConduitGeometry(inlet);
        if (R.dgt(inlet.flowLimit, 0.0d)) {
            qMax = inlet.flowLimit;
        }
        qCaptured = getOnSagInletCapture(designIndex, Math.abs(d));
        qCaptured *= inlet.clogFactor;
        qCaptured = (((((qCaptured) <= (qMax))) ? (qCaptured) : (qMax)));
        qCaptured *= ((double) (totalInlets));
        return qCaptured;
    }

    // inlet.c:1610
    static double getOnSagInletCapture(int i, double d) {
        double Lsweep = 0.0d;
        double Lcurb = 0.0d;
        double Lgrate = 0.0d;
        DBox Qsw = new DBox(0.0d);
        DBox Qso = new DBox(0.0d);
        DBox Qgw = new DBox(0.0d);
        DBox Qgo = new DBox(0.0d);
        DBox Qcw = new DBox(0.0d);
        DBox Qco = new DBox(0.0d);
        if (R.dgt(InletDesigns[i].slottedInlet.length, 0.0d)) {
            return getOnSagSlottedFlow(i, d);
        }
        Lgrate = InletDesigns[i].grateInlet.length;
        if (R.dgt(Lgrate, 0.0d)) {
            findOnSagGrateFlows(i, d, Qgw, Qgo);
        }
        Lcurb = InletDesigns[i].curbInlet.length;
        if (R.dgt(Lcurb, 0.0d)) {
            Lsweep = (Lcurb - Lgrate);
            if (R.dgt(Lsweep, 0.0d)) {
                findOnSagCurbFlows(i, d, Lsweep, Qsw, Qso);
            }
            if (R.dgt(Qgo.v, 0.0d)) {
                findOnSagCurbFlows(i, d, Lgrate, Qcw, Qco);
            }
        }
        return ((((Qgw.v + Qgo.v) + Qsw.v) + Qso.v) + Qco.v);
    }

    // inlet.c:1644
    static void findOnSagGrateFlows(int i, double d, DRef Qw, DRef Qo) {
        int grateType = InletDesigns[i].grateInlet.type;
        double Lg = InletDesigns[i].grateInlet.length;
        double Wg = InletDesigns[i].grateInlet.width;
        double P = 0.0d;
        double Ao = 0.0d;
        double di = 0.0d;
        if ((InletDesigns[i].type == DROP_GRATE_INLET)) {
            di = d;
            P = (2.0d * ((Lg + Wg)));
        } else {
            if ((d <= (Wg * Sw))) {
                Wg = (d / Sw);
            }
            di = (d - (((Wg / 2.0d)) * Sw));
            P = (Lg + (2.0d * Wg));
        }
        if ((grateType == GENERIC)) {
            Ao = ((Lg * Wg) * InletDesigns[i].grateInlet.fracOpenArea);
        } else {
            Ao = ((Lg * Wg) * GrateOpeningRatios[grateType]);
        }
        if ((d <= ((1.79d * Ao) / P))) {
            Qw.set(((3.0d * P) * Glibm.pow(di, 1.5d)));
        } else {
            Qo.set(((0.67d * Ao) * Math.sqrt(((2.0d * 32.16d) * di))));
        }
    }

    // inlet.c:1703
    static void findOnSagCurbFlows(int i, double d, double L, DRef Qw, DRef Qo) {
        int throatAngle = InletDesigns[i].curbInlet.throatAngle;
        double h = InletDesigns[i].curbInlet.height;
        double Qweir = 0.0d;
        double Qorif = 0.0d;
        double P = 0.0d;
        double dweir = 0.0d;
        double dorif = 0.0d;
        double r = 0.0d;
        if ((L <= 0.0d)) {
            return;
        }
        if ((InletDesigns[i].type == DROP_CURB_INLET)) {
            L = (L * 4.0d);
        }
        dorif = (1.4d * h);
        if (R.dgt(d, dorif)) {
            Qo.set(getCurbOrificeFlow(d, h, L, throatAngle));
            return;
        }
        if (((a == 0.0d) || (R.dgt(L, 12.0d)))) {
            dweir = h;
            if ((d < dweir)) {
                Qw.set(((3.0d * L) * Glibm.pow(d, 1.5d)));
                return;
            } else {
                Qweir = ((3.0d * L) * Glibm.pow(dweir, 1.5d));
            }
        } else {
            P = (L + (1.8d * W));
            dweir = (h + a);
            if ((d < dweir)) {
                Qw.set(((2.3d * P) * Glibm.pow(d, 1.5d)));
                return;
            } else {
                Qweir = ((2.3d * P) * Glibm.pow(dweir, 1.5d));
            }
        }
        Qorif = getCurbOrificeFlow(dorif, h, L, throatAngle);
        r = (((d - dweir)) / ((dorif - dweir)));
        Qw.set((((1.0d - r)) * Qweir));
        Qo.set((r * Qorif));
    }

    // inlet.c:1764
    static double getCurbOrificeFlow(double di, double h, double L, int throatAngle) {
        double d = di;
        if ((throatAngle == HORIZONTAL_THROAT)) {
            d = (di - (h / 2.0d));
        } else if ((throatAngle == INCLINED_THROAT)) {
            d = (di - (((h / 2.0d)) * 0.7071d));
        }
        return (((0.67d * h) * L) * Math.sqrt(((2.0d * 32.16d) * d)));
    }

    // inlet.c:1785
    static double getOnSagSlottedFlow(int i, double d) {
        double L = InletDesigns[i].slottedInlet.length;
        double w = InletDesigns[i].slottedInlet.width;
        if ((d <= (2.587d * w))) {
            return ((2.48d * L) * Glibm.pow(d, 1.5d));
        } else {
            return (((0.8d * L) * w) * Math.sqrt((64.32d * d)));
        }
        throw new IllegalStateException('getOnSagSlottedFlow: no return');
    }

    // inlet.c:1805
    static void getBackflowRatios() {
        TInlet inlet = null;
        double area = 0.0d;
        double f = 0.0d;
        int n = 0;
        TInletNode[] inletNodes = R.fill(new TInletNode[(int) (F_swmm5.Nobjects[NODE])], (() -> new TInletNode()));
        if ((inletNodes == null)) {
            return;
        }
        L_f1: for (inlet = FirstInlet; (inlet != null); inlet = inlet.nextInlet) {
            n = inlet.nodeIndex;
            inletNodes[n].@numInletLinks = inletNodes[n].numInletLinks + 1;
            area = getInletArea(inlet);
            if (R.dgt(area, 0.0d)) {
                inletNodes[n].@numStdInletLinks = inletNodes[n].numStdInletLinks + 1;
                inletNodes[n].@totalInletArea = (inletNodes[n].totalInletArea + area);
            } else {
                inletNodes[n].@numCustomInlets = (inletNodes[n].numCustomInlets + inlet.numInlets);
            }
        }
        L_f2: for (inlet = FirstInlet; (inlet != null); inlet = inlet.nextInlet) {
            n = inlet.nodeIndex;
            f = (((double) (inletNodes[n].numStdInletLinks)) / ((double) (inletNodes[n].numInletLinks)));
            area = getInletArea(inlet);
            if ((area == 0.0d)) {
                inlet.@backflowRatio = ((((double) (inlet.numInlets)) / ((double) (inletNodes[n].numCustomInlets))) * ((1.0d - f)));
            } else {
                inlet.@backflowRatio = ((area / inletNodes[n].totalInletArea) * f);
            }
        }
        R.free(inletNodes);
    }

    // inlet.c:1870
    static double getInletArea(TInlet inlet) {
        double area = 0.0d;
        double curbLength = 0.0d;
        int i = inlet.designIndex;
        int grateType = InletDesigns[i].grateInlet.type;
        if (R.dgt(InletDesigns[i].grateInlet.length, 0.0d)) {
            area = (InletDesigns[i].grateInlet.length * InletDesigns[i].grateInlet.width);
            if ((grateType == GENERIC)) {
                area *= InletDesigns[i].grateInlet.fracOpenArea;
            } else {
                area *= GrateOpeningRatios[grateType];
            }
        }
        curbLength = (InletDesigns[i].curbInlet.length - InletDesigns[i].grateInlet.length);
        if (R.dgt(curbLength, 0.0d)) {
            area += (curbLength * InletDesigns[i].curbInlet.height);
        }
        if (R.dgt(InletDesigns[i].slottedInlet.length, 0.0d)) {
            area = (InletDesigns[i].slottedInlet.length * InletDesigns[i].slottedInlet.width);
        }
        return ((area * inlet.numInlets) * inlet.clogFactor);
    }

    // inlet.c:1902
    static double getCustomCapturedFlow(TInlet inlet, double q, double d) {
        int i = inlet.designIndex;
        int j = 0;
        int sides = 1;
        int c = 0;
        double qApproach = 0.0d;
        double qBypassed = 0.0d;
        double qCaptured = 0.0d;
        double qIncrement = 0.0d;
        double qMax = 10000000000.0d;
        if ((inlet.numInlets == 0)) {
            return 0.0d;
        }
        qMax = 10000000000.0d;
        if (R.dgt(inlet.flowLimit, 0.0d)) {
            qMax = inlet.flowLimit;
        }
        xsect = F_swmm5.Link[inlet.linkIndex].xsect;
        if ((xsect.type == STREET_XSECT)) {
            sides = F_swmm5.Street[xsect.transect].sides;
        }
        qApproach = (q / sides);
        qBypassed = qApproach;
        qCaptured = 0.0d;
        c = InletDesigns[i].customCurve;
        if ((c >= 0)) {
            if ((F_swmm5.Curve[c].curveType == DIVERSION_CURVE)) {
                L_f1: for (j = 1; (j <= inlet.numInlets); j++) {
                    qIncrement = ((inlet.clogFactor * F_table.table_lookupEx(F_swmm5.Curve[c], (qBypassed * F_swmm5.UCF(FLOW)))) / F_swmm5.UCF(FLOW));
                    qIncrement = (((((qIncrement) <= (qMax))) ? (qIncrement) : (qMax)));
                    qIncrement = (((((qIncrement) <= (qBypassed))) ? (qIncrement) : (qBypassed)));
                    qCaptured += qIncrement;
                    qBypassed -= qIncrement;
                    if ((qBypassed < 0.001d)) {
                        break L_f1;
                    }
                }
            } else if ((F_swmm5.Curve[c].curveType == RATING_CURVE)) {
                qCaptured = (((inlet.numInlets * inlet.clogFactor) * F_table.table_lookupEx(F_swmm5.Curve[c], (d * F_swmm5.UCF(LENGTH)))) / F_swmm5.UCF(FLOW));
            }
            qCaptured *= sides;
        }
        return qCaptured;
    }

}
