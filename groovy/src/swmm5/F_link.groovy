// Generated from EPA SWMM 5.2.4 (link.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_link {
    public static double MIN_DELTA_Z = 0.001d;

    // link.c:137
    static int link_readParams(int j, int type, int k, CPtr[] tok, int ntoks) {
        int __sw1 = type;
        if (__sw1 == CONDUIT) {
            return conduit_readParams(j, k, tok, ntoks);
        } else if (__sw1 == PUMP) {
            return pump_readParams(j, k, tok, ntoks);
        } else if (__sw1 == ORIFICE) {
            return orifice_readParams(j, k, tok, ntoks);
        } else if (__sw1 == WEIR) {
            return weir_readParams(j, k, tok, ntoks);
        } else if (__sw1 == OUTLET) {
            return outlet_readParams(j, k, tok, ntoks);
        } else {
            return 0;
        }
        throw new IllegalStateException('link_readParams: no return');
    }

    // link.c:162
    static int link_readXsectParams(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int k = 0;
        double[] x = new double[4];
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(LINK, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        k = F_input.findmatch(tok[1], F_keywords.XsectTypeWords);
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
        }
        if ((F_swmm5.Link[j].type == CONDUIT)) {
            F_swmm5.Conduit[F_swmm5.Link[j].subIndex].@barrels = 1;
        }
        F_swmm5.Link[j].xsect.@culvertCode = 0;
        if ((k == IRREGULAR)) {
            i = F_project.project_findObject(TRANSECT, tok[2]);
            if ((i < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
            F_swmm5.Link[j].xsect.@type = k;
            F_swmm5.Link[j].xsect.@transect = i;
            return 0;
        } else if ((k == STREET_XSECT)) {
            i = F_project.project_findObject(STREET, tok[2]);
            if ((i < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
            F_swmm5.Link[j].xsect.@type = k;
            F_swmm5.Link[j].xsect.@transect = i;
            return 0;
        } else {
            if ((ntoks < 6)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            if ((k == CUSTOM)) {
                if (((!(F_input.getDouble(tok[2], new DElem(x, 0)) != 0)) || (x[0] <= 0.0d))) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[2]);
                }
                i = F_project.project_findObject(CURVE, tok[3]);
                if ((i < 0)) {
                    return F_error.error_setInpError(ERR_NAME, tok[3]);
                }
                F_swmm5.Link[j].xsect.@type = k;
                F_swmm5.Link[j].xsect.@transect = i;
                F_swmm5.Link[j].xsect.@yFull = (x[0] / F_swmm5.UCF(LENGTH));
            } else {
                L_f1: for (i = 2; (i <= 5); i++) {
                    if (!(F_input.getDouble(tok[i], new DElem(x, (i - 2))) != 0)) {
                        return F_error.error_setInpError(ERR_NUMBER, tok[i]);
                    }
                }
            }
            if (((F_swmm5.Link[j].type != CONDUIT) && (k == RECT_OPEN))) {
                x[2] = 0.0d;
                x[3] = 0.0d;
            }
            if (!(F_xsect.xsect_setParams(F_swmm5.Link[j].xsect, k, x, F_swmm5.UCF(LENGTH)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, L0);
            }
            if (((F_swmm5.Link[j].type == CONDUIT) && (ntoks >= 7))) {
                i = R.atoi(tok[6]);
                if ((i <= 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[6]);
                } else {
                    F_swmm5.Conduit[F_swmm5.Link[j].subIndex].@barrels = ((byte) (i));
                }
            }
            if (((F_swmm5.Link[j].type == CONDUIT) && (ntoks >= 8))) {
                i = R.atoi(tok[7]);
                if ((i < 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[7]);
                } else {
                    F_swmm5.Link[j].xsect.@culvertCode = i;
                }
            }
        }
        return 0;
    }

    // link.c:271
    static int link_readLossParams(CPtr[] tok, int ntoks) {
        int i = 0;
        int j = 0;
        int k = 0;
        double[] x = new double[3];
        DBox seepRate = new DBox(0.0d);
        if ((ntoks < 4)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        j = F_project.project_findObject(LINK, tok[0]);
        if ((j < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        L_f1: for (i = 1; (i <= 3); i++) {
            if (((!(F_input.getDouble(tok[i], new DElem(x, (i - 1))) != 0)) || (x[(i - 1)] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[i]);
            }
        }
        k = 0;
        if ((ntoks >= 5)) {
            k = F_input.findmatch(tok[4], F_keywords.NoYesWords);
            if ((k < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[4]);
            }
        }
        if ((ntoks >= 6)) {
            if (!(F_input.getDouble(tok[5], seepRate) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[5]);
            }
        }
        F_swmm5.Link[j].@cLossInlet = x[0];
        F_swmm5.Link[j].@cLossOutlet = x[1];
        F_swmm5.Link[j].@cLossAvg = x[2];
        F_swmm5.Link[j].@hasFlapGate = k;
        F_swmm5.Link[j].@seepRate = (seepRate.v / F_swmm5.UCF(RAINFALL));
        return 0;
    }

    // link.c:315
    static void link_setParams(int j, int type, int n1, int n2, int k, double[] x) {
        F_swmm5.Link[j].@node1 = n1;
        F_swmm5.Link[j].@node2 = n2;
        F_swmm5.Link[j].@type = type;
        F_swmm5.Link[j].@subIndex = k;
        F_swmm5.Link[j].@offset1 = 0.0d;
        F_swmm5.Link[j].@offset2 = 0.0d;
        F_swmm5.Link[j].@q0 = 0.0d;
        F_swmm5.Link[j].@qFull = 0.0d;
        F_swmm5.Link[j].@setting = 1.0d;
        F_swmm5.Link[j].@targetSetting = 1.0d;
        F_swmm5.Link[j].@hasFlapGate = 0;
        F_swmm5.Link[j].@qLimit = 0.0d;
        F_swmm5.Link[j].@direction = 1;
        int __sw1 = type;
        if (__sw1 == CONDUIT) {
            F_swmm5.Conduit[k].@length = (x[0] / F_swmm5.UCF(LENGTH));
            F_swmm5.Conduit[k].@modLength = F_swmm5.Conduit[k].length;
            F_swmm5.Conduit[k].@roughness = x[1];
            F_swmm5.Link[j].@offset1 = (x[2] / F_swmm5.UCF(LENGTH));
            F_swmm5.Link[j].@offset2 = (x[3] / F_swmm5.UCF(LENGTH));
            F_swmm5.Link[j].@q0 = (x[4] / F_swmm5.UCF(FLOW));
            F_swmm5.Link[j].@qLimit = (x[5] / F_swmm5.UCF(FLOW));
        } else if (__sw1 == PUMP) {
            F_swmm5.Pump[k].@pumpCurve = R.D2I(x[0]);
            F_swmm5.Link[j].@hasFlapGate = 0;
            F_swmm5.Pump[k].@initSetting = x[1];
            F_swmm5.Pump[k].@yOn = (x[2] / F_swmm5.UCF(LENGTH));
            F_swmm5.Pump[k].@yOff = (x[3] / F_swmm5.UCF(LENGTH));
            F_swmm5.Pump[k].@xMin = 0.0d;
            F_swmm5.Pump[k].@xMax = 0.0d;
        } else if (__sw1 == ORIFICE) {
            F_swmm5.Orifice[k].@type = R.D2I(x[0]);
            F_swmm5.Link[j].@offset1 = (x[1] / F_swmm5.UCF(LENGTH));
            F_swmm5.Link[j].@offset2 = F_swmm5.Link[j].offset1;
            F_swmm5.Orifice[k].@cDisch = x[2];
            F_swmm5.Link[j].@hasFlapGate = ((R.dgt(x[3], 0.0d)) ? 1 : 0);
            F_swmm5.Orifice[k].@orate = (x[4] * 3600.0d);
        } else if (__sw1 == WEIR) {
            F_swmm5.Weir[k].@type = R.D2I(x[0]);
            F_swmm5.Link[j].@offset1 = (x[1] / F_swmm5.UCF(LENGTH));
            F_swmm5.Link[j].@offset2 = F_swmm5.Link[j].offset1;
            F_swmm5.Weir[k].@cDisch1 = x[2];
            F_swmm5.Link[j].@hasFlapGate = ((R.dgt(x[3], 0.0d)) ? 1 : 0);
            F_swmm5.Weir[k].@endCon = x[4];
            F_swmm5.Weir[k].@cDisch2 = x[5];
            F_swmm5.Weir[k].@canSurcharge = R.D2I(x[6]);
            F_swmm5.Weir[k].@roadWidth = (x[7] / F_swmm5.UCF(LENGTH));
            F_swmm5.Weir[k].@roadSurface = R.D2I(x[8]);
            F_swmm5.Weir[k].@cdCurve = R.D2I(x[9]);
        } else if (__sw1 == OUTLET) {
            F_swmm5.Link[j].@offset1 = (x[0] / F_swmm5.UCF(LENGTH));
            F_swmm5.Link[j].@offset2 = F_swmm5.Link[j].offset1;
            F_swmm5.Outlet[k].@qCoeff = x[1];
            F_swmm5.Outlet[k].@qExpon = x[2];
            F_swmm5.Outlet[k].@qCurve = R.D2I(x[3]);
            F_swmm5.Link[j].@hasFlapGate = ((R.dgt(x[4], 0.0d)) ? 1 : 0);
            F_swmm5.Outlet[k].@curveType = R.D2I(x[5]);
            F_xsect.xsect_setParams(F_swmm5.Link[j].xsect, DUMMY, (null), 0.0d);
        }
    }

    // link.c:403
    static void link_validate(int j) {
        int n = 0;
        if ((F_swmm5.LinkOffsets == ELEV_OFFSET)) {
            link_convertOffsets(j);
        }
        int __sw1 = F_swmm5.Link[j].type;
        if (__sw1 == CONDUIT) {
            conduit_validate(j, F_swmm5.Link[j].subIndex);
        } else if (__sw1 == PUMP) {
            pump_validate(j, F_swmm5.Link[j].subIndex);
        } else if (__sw1 == ORIFICE) {
            orifice_validate(j, F_swmm5.Link[j].subIndex);
        } else if (__sw1 == WEIR) {
            weir_validate(j, F_swmm5.Link[j].subIndex);
        }
        int __sw2 = F_swmm5.Link[j].type;
        if (__sw2 == ORIFICE || __sw2 == WEIR || __sw2 == OUTLET) {
            if (((F_swmm5.Node_[F_swmm5.Link[j].node1].invertElev + F_swmm5.Link[j].offset1) < F_swmm5.Node_[F_swmm5.Link[j].node2].invertElev)) {
                if ((F_swmm5.RouteModel == DW)) {
                    F_swmm5.Link[j].@offset1 = (F_swmm5.Node_[F_swmm5.Link[j].node2].invertElev - F_swmm5.Node_[F_swmm5.Link[j].node1].invertElev);
                    F_report.report_writeWarningMsg(L305, F_swmm5.Link[j].ID);
                } else {
                    F_report.report_writeWarningMsg(L306, F_swmm5.Link[j].ID);
                }
            }
        }
        if (((F_swmm5.Link[j].type == PUMP) || (((F_swmm5.Link[j].type == ORIFICE) && (F_swmm5.Orifice[F_swmm5.Link[j].subIndex].type == BOTTOM_ORIFICE))))) {
            return;
        }
        n = F_swmm5.Link[j].node1;
        if (((F_swmm5.Node_[n].type != STORAGE) || (R.dgt(F_swmm5.Node_[n].surDepth, 0.0d)))) {
            F_swmm5.Node_[n].@fullDepth = (((R.dge((F_swmm5.Node_[n].fullDepth), ((F_swmm5.Link[j].offset1 + F_swmm5.Link[j].xsect.yFull)))) ? (F_swmm5.Node_[n].fullDepth) : ((F_swmm5.Link[j].offset1 + F_swmm5.Link[j].xsect.yFull))));
        }
        n = F_swmm5.Link[j].node2;
        if (((((F_swmm5.Node_[n].type != STORAGE) || (R.dgt(F_swmm5.Node_[n].surDepth, 0.0d)))) && (F_swmm5.Link[j].type == CONDUIT))) {
            F_swmm5.Node_[n].@fullDepth = (((R.dge((F_swmm5.Node_[n].fullDepth), ((F_swmm5.Link[j].offset2 + F_swmm5.Link[j].xsect.yFull)))) ? (F_swmm5.Node_[n].fullDepth) : ((F_swmm5.Link[j].offset2 + F_swmm5.Link[j].xsect.yFull))));
        }
    }

    // link.c:468
    static void link_convertOffsets(int j) {
        double elev = 0.0d;
        elev = F_swmm5.Node_[F_swmm5.Link[j].node1].invertElev;
        F_swmm5.Link[j].@offset1 = link_getOffsetHeight(j, F_swmm5.Link[j].offset1, elev);
        if ((F_swmm5.Link[j].type == CONDUIT)) {
            elev = F_swmm5.Node_[F_swmm5.Link[j].node2].invertElev;
            F_swmm5.Link[j].@offset2 = link_getOffsetHeight(j, F_swmm5.Link[j].offset2, elev);
        } else {
            F_swmm5.Link[j].@offset2 = F_swmm5.Link[j].offset1;
        }
    }

    // link.c:489
    static double link_getOffsetHeight(int j, double offset, double elev) {
        if (((offset <= (-10000000000.0d)) || (F_swmm5.Link[j].type == PUMP))) {
            return 0.0d;
        }
        offset -= elev;
        if (R.dge(offset, 0.0d)) {
            return offset;
        }
        if (R.dge(offset, (-MIN_DELTA_Z))) {
            return 0.0d;
        }
        F_report.report_writeWarningMsg(L307, F_swmm5.Link[j].ID);
        return 0.0d;
    }

    // link.c:508
    static void link_initState(int j) {
        int p = 0;
        F_swmm5.Link[j].@oldFlow = F_swmm5.Link[j].q0;
        F_swmm5.Link[j].@newFlow = F_swmm5.Link[j].q0;
        F_swmm5.Link[j].@oldDepth = 0.0d;
        F_swmm5.Link[j].@newDepth = 0.0d;
        F_swmm5.Link[j].@oldVolume = 0.0d;
        F_swmm5.Link[j].@newVolume = 0.0d;
        F_swmm5.Link[j].@setting = 1.0d;
        F_swmm5.Link[j].@targetSetting = 1.0d;
        F_swmm5.Link[j].@timeLastSet = F_swmm5.StartDate.v;
        F_swmm5.Link[j].@inletControl = 0;
        F_swmm5.Link[j].@normalFlow = 0;
        if ((F_swmm5.Link[j].type == CONDUIT)) {
            conduit_initState(j, F_swmm5.Link[j].subIndex);
        }
        if ((F_swmm5.Link[j].type == PUMP)) {
            pump_initState(j, F_swmm5.Link[j].subIndex);
        }
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            F_swmm5.Link[j].oldQual[p] = 0.0d;
            F_swmm5.Link[j].newQual[p] = 0.0d;
            F_swmm5.Link[j].totalLoad[p] = 0.0d;
        }
    }

    // link.c:543
    static double link_getInflow(int j) {
        if ((F_swmm5.Link[j].setting == 0)) {
            return 0.0d;
        }
        int __sw1 = F_swmm5.Link[j].type;
        if (__sw1 == CONDUIT) {
            return conduit_getInflow(j);
        } else if (__sw1 == PUMP) {
            return pump_getInflow(j);
        } else if (__sw1 == ORIFICE) {
            return orifice_getInflow(j);
        } else if (__sw1 == WEIR) {
            return weir_getInflow(j);
        } else if (__sw1 == OUTLET) {
            return outlet_getInflow(j);
        } else {
            return F_node.node_getOutflow(F_swmm5.Link[j].node1, j);
        }
        throw new IllegalStateException('link_getInflow: no return');
    }

    // link.c:564
    static void link_setOldHydState(int j) {
        int k = 0;
        F_swmm5.Link[j].@oldDepth = F_swmm5.Link[j].newDepth;
        F_swmm5.Link[j].@oldFlow = F_swmm5.Link[j].newFlow;
        F_swmm5.Link[j].@oldVolume = F_swmm5.Link[j].newVolume;
        if ((F_swmm5.Link[j].type == CONDUIT)) {
            k = F_swmm5.Link[j].subIndex;
            F_swmm5.Conduit[k].@q1Old = F_swmm5.Conduit[k].q1;
            F_swmm5.Conduit[k].@q2Old = F_swmm5.Conduit[k].q2;
        }
    }

    // link.c:587
    static void link_setOldQualState(int j) {
        int p = 0;
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            F_swmm5.Link[j].oldQual[p] = F_swmm5.Link[j].newQual[p];
            F_swmm5.Link[j].newQual[p] = 0.0d;
        }
    }

    // link.c:604
    static void link_setTargetSetting(int j) {
        int k = 0;
        int n1 = 0;
        if ((F_swmm5.Link[j].type == PUMP)) {
            k = F_swmm5.Link[j].subIndex;
            n1 = F_swmm5.Link[j].node1;
            F_swmm5.Link[j].@targetSetting = F_swmm5.Link[j].setting;
            if ((((R.dgt(F_swmm5.Pump[k].yOff, 0.0d)) && (R.dgt(F_swmm5.Link[j].setting, 0.0d))) && (F_swmm5.Node_[n1].newDepth < F_swmm5.Pump[k].yOff))) {
                F_swmm5.Link[j].@targetSetting = 0.0d;
            }
            if ((((R.dgt(F_swmm5.Pump[k].yOn, 0.0d)) && (F_swmm5.Link[j].setting == 0.0d)) && (R.dgt(F_swmm5.Node_[n1].newDepth, F_swmm5.Pump[k].yOn)))) {
                F_swmm5.Link[j].@targetSetting = 1.0d;
            }
        }
    }

    // link.c:628
    static void link_setSetting(int j, double tstep) {
        if ((F_swmm5.Link[j].type == ORIFICE)) {
            orifice_setSetting(j, tstep);
        } else if ((F_swmm5.Link[j].type == WEIR)) {
            weir_setSetting(j);
        } else {
            F_swmm5.Link[j].@setting = F_swmm5.Link[j].targetSetting;
        }
    }

    // link.c:643
    static int link_setFlapGate(int j, int n1, int n2, double q) {
        int n = (-1);
        if ((F_swmm5.Link[j].hasFlapGate != 0)) {
            if (((q * ((double) (F_swmm5.Link[j].direction))) < 0.0d)) {
                return 1;
            }
        }
        if ((q < 0.0d)) {
            n = n2;
        }
        if (R.dgt(q, 0.0d)) {
            n = n1;
        }
        if ((((n >= 0) && (F_swmm5.Node_[n].type == OUTFALL)) && (F_swmm5.Outfall[F_swmm5.Node_[n].subIndex].hasFlapGate != 0))) {
            return 1;
        }
        return 0;
    }

    // link.c:674
    static void link_getResults(int j, double f, float[] x) {
        int p = 0;
        double y = 0.0d;
        double q = 0.0d;
        double u = 0.0d;
        double v = 0.0d;
        double c = 0.0d;
        double f1 = (1.0d - f);
        y = ((f1 * F_swmm5.Link[j].oldDepth) + (f * F_swmm5.Link[j].newDepth));
        q = ((f1 * F_swmm5.Link[j].oldFlow) + (f * F_swmm5.Link[j].newFlow));
        v = ((f1 * F_swmm5.Link[j].oldVolume) + (f * F_swmm5.Link[j].newVolume));
        u = link_getVelocity(j, q, y);
        c = 0.0d;
        if ((F_swmm5.Link[j].type == CONDUIT)) {
            if ((F_swmm5.Link[j].xsect.type != DUMMY)) {
                c = (F_xsect.xsect_getAofY(F_swmm5.Link[j].xsect, y) / F_swmm5.Link[j].xsect.aFull);
            }
        } else {
            c = F_swmm5.Link[j].setting;
        }
        if (((F_swmm5.Link[j].type == PUMP) && ((F_swmm5.Link[j].oldFlow * F_swmm5.Link[j].newFlow) == 0.0d))) {
            if (R.dge(f, f1)) {
                q = F_swmm5.Link[j].newFlow;
            } else {
                q = F_swmm5.Link[j].oldFlow;
            }
        }
        y *= (F_swmm5.UCF(LENGTH));
        v *= (F_swmm5.UCF(VOLUME));
        q *= (F_swmm5.UCF(FLOW) * ((double) (F_swmm5.Link[j].direction)));
        u *= (F_swmm5.UCF(LENGTH) * ((double) (F_swmm5.Link[j].direction)));
        x[LINK_DEPTH] = ((float) (y));
        x[LINK_FLOW] = ((float) (q));
        x[LINK_VELOCITY] = ((float) (u));
        x[LINK_VOLUME] = ((float) (v));
        x[LINK_CAPACITY] = ((float) (c));
        if (!(F_swmm5.IgnoreQuality != 0)) {
            L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                c = ((f1 * F_swmm5.Link[j].oldQual[p]) + (f * F_swmm5.Link[j].newQual[p]));
                x[(LINK_QUAL + p)] = ((float) (c));
            }
        }
    }

    // link.c:728
    static void link_setOutfallDepth(int j) {
        int k = 0;
        int n = 0;
        double z = 0.0d;
        double q = 0.0d;
        double yCrit = 0.0d;
        double yNorm = 0.0d;
        if ((F_swmm5.Node_[F_swmm5.Link[j].node2].type == OUTFALL)) {
            n = F_swmm5.Link[j].node2;
            z = F_swmm5.Link[j].offset2;
        } else if ((F_swmm5.Node_[F_swmm5.Link[j].node1].type == OUTFALL)) {
            n = F_swmm5.Link[j].node1;
            z = F_swmm5.Link[j].offset1;
        } else {
            return;
        }
        if ((F_swmm5.Link[j].type == CONDUIT)) {
            k = F_swmm5.Link[j].subIndex;
            q = Math.abs((F_swmm5.Link[j].newFlow / F_swmm5.Conduit[k].barrels));
            yNorm = link_getYnorm(j, q);
            yCrit = link_getYcrit(j, q);
        }
        F_node.node_setOutletDepth(n, yNorm, yCrit, z);
    }

    // link.c:770
    static double link_getYcrit(int j, double q) {
        return F_xsect.xsect_getYcrit(F_swmm5.Link[j].xsect, q);
    }

    // link.c:783
    static double link_getYnorm(int j, double q) {
        int k = 0;
        double s = 0.0d;
        double a = 0.0d;
        double y = 0.0d;
        if ((F_swmm5.Link[j].type != CONDUIT)) {
            return 0.0d;
        }
        if ((F_swmm5.Link[j].xsect.type == DUMMY)) {
            return 0.0d;
        }
        q = Math.abs(q);
        k = F_swmm5.Link[j].subIndex;
        if (R.dgt(q, F_swmm5.Conduit[k].qMax)) {
            q = F_swmm5.Conduit[k].qMax;
        }
        if ((q <= 0.0d)) {
            return 0.0d;
        }
        s = (q / F_swmm5.Conduit[k].beta);
        a = F_xsect.xsect_getAofS(F_swmm5.Link[j].xsect, s);
        y = F_xsect.xsect_getYofA(F_swmm5.Link[j].xsect, a);
        return y;
    }

    // link.c:808
    static double link_getLength(int j) {
        if ((F_swmm5.Link[j].type == CONDUIT)) {
            return conduit_getLength(j);
        }
        return 0.0d;
    }

    // link.c:821
    static double link_getVelocity(int j, double flow, double depth) {
        double area = 0.0d;
        double veloc = 0.0d;
        int k = 0;
        if ((depth <= 0.01d)) {
            return 0.0d;
        }
        if ((F_swmm5.Link[j].type == CONDUIT)) {
            k = F_swmm5.Link[j].subIndex;
            flow = (flow / F_swmm5.Conduit[k].barrels);
            area = F_xsect.xsect_getAofY(F_swmm5.Link[j].xsect, depth);
            if (R.dgt(area, 0.0001d)) {
                veloc = (flow / area);
            }
        }
        return veloc;
    }

    // link.c:847
    static double link_getFroude(int j, double v, double y) {
        TXsect xsect = F_swmm5.Link[j].xsect;
        if ((F_swmm5.Link[j].type != CONDUIT)) {
            return 0.0d;
        }
        if ((y <= 0.0001d)) {
            return 0.0d;
        }
        if (((!(F_xsect.xsect_isOpen(xsect.type) != 0)) && ((xsect.yFull - y) <= 0.0001d))) {
            return 0.0d;
        }
        y = (F_xsect.xsect_getAofY(xsect, y) / F_xsect.xsect_getWofY(xsect, y));
        return (Math.abs(v) / Math.sqrt((32.2d * y)));
    }

    // link.c:875
    static double link_getPower(int j) {
        int n1 = F_swmm5.Link[j].node1;
        int n2 = F_swmm5.Link[j].node2;
        double dh = (((F_swmm5.Node_[n1].invertElev + F_swmm5.Node_[n1].newDepth)) - ((F_swmm5.Node_[n2].invertElev + F_swmm5.Node_[n2].newDepth)));
        double q = Math.abs(F_swmm5.Link[j].newFlow);
        return (((Math.abs(dh) * q) / 8.814d) * 0.7457d);
    }

    // link.c:893
    static double link_getLossRate(int j, int routeModel, double q, double tstep) {
        if ((F_swmm5.Link[j].type == CONDUIT)) {
            return conduit_getLossRate(j, routeModel, q, tstep);
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('link_getLossRate: no return');
    }

    // link.c:911
    static int link_getFullState(double a1, double a2, double aFull) {
        if (R.dge(a1, aFull)) {
            if (R.dge(a2, aFull)) {
                return ALL_FULL;
            } else {
                return UP_FULL;
            }
        }
        if (R.dge(a2, aFull)) {
            return DN_FULL;
        }
        return 0;
    }

    // link.c:933
    static int conduit_readParams(int j, int k, CPtr[] tok, int ntoks) {
        int n1 = 0;
        int n2 = 0;
        double[] x = new double[6];
        CPtr id = null;
        if ((ntoks < 7)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(LINK, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        n1 = F_project.project_findObject(NODE, tok[1]);
        if ((n1 < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[1]);
        }
        n2 = F_project.project_findObject(NODE, tok[2]);
        if ((n2 < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[2]);
        }
        if (!(F_input.getDouble(tok[3], new DElem(x, 0)) != 0)) {
            return F_error.error_setInpError(ERR_NUMBER, tok[3]);
        }
        if (!(F_input.getDouble(tok[4], new DElem(x, 1)) != 0)) {
            return F_error.error_setInpError(ERR_NUMBER, tok[4]);
        }
        if (((F_swmm5.LinkOffsets == ELEV_OFFSET) && (tok[5].g(0) == 42))) {
            x[2] = (-10000000000.0d);
        } else if (!(F_input.getDouble(tok[5], new DElem(x, 2)) != 0)) {
            return F_error.error_setInpError(ERR_NUMBER, tok[5]);
        }
        if (((F_swmm5.LinkOffsets == ELEV_OFFSET) && (tok[6].g(0) == 42))) {
            x[3] = (-10000000000.0d);
        } else if (!(F_input.getDouble(tok[6], new DElem(x, 3)) != 0)) {
            return F_error.error_setInpError(ERR_NUMBER, tok[6]);
        }
        x[4] = 0.0d;
        if ((ntoks >= 8)) {
            if (!(F_input.getDouble(tok[7], new DElem(x, 4)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[7]);
            }
        }
        x[5] = 0.0d;
        if ((ntoks >= 9)) {
            if (!(F_input.getDouble(tok[8], new DElem(x, 5)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[8]);
            }
        }
        F_swmm5.Link[j].@ID = id;
        link_setParams(j, CONDUIT, n1, n2, k, x);
        return 0;
    }

    // link.c:992
    static void conduit_validate(int j, int k) {
        double aa = 0.0d;
        double lengthFactor = 0.0d;
        double roughness = 0.0d;
        double slope = 0.0d;
        if (((F_swmm5.Link[j].xsect.type == DUMMY) && (F_swmm5.RouteModel == DW))) {
            if ((F_swmm5.Node_[F_swmm5.Link[j].node1].type == STORAGE)) {
                F_report.report_writeErrorMsg(ERR_DUMMY_LINK, F_swmm5.Node_[F_swmm5.Link[j].node1].ID);
                return;
            }
        }
        if ((F_swmm5.Link[j].xsect.type == CUSTOM)) {
            F_xsect.xsect_setCustomXsectParams(F_swmm5.Link[j].xsect);
        }
        if ((F_swmm5.Link[j].xsect.type == IRREGULAR)) {
            F_xsect.xsect_setIrregXsectParams(F_swmm5.Link[j].xsect);
            F_swmm5.Conduit[k].@roughness = F_swmm5.Transect[F_swmm5.Link[j].xsect.transect].roughness;
        }
        if ((F_swmm5.Link[j].xsect.type == STREET_XSECT)) {
            F_xsect.xsect_setStreetXsectParams(F_swmm5.Link[j].xsect);
            F_swmm5.Conduit[k].@roughness = F_swmm5.Street[F_swmm5.Link[j].xsect.transect].roughness;
        }
        if ((F_swmm5.Link[j].xsect.type == FORCE_MAIN)) {
            if ((F_swmm5.ForceMainEqn == D_W)) {
                F_swmm5.Link[j].xsect.@rBot = (F_swmm5.Link[j].xsect.rBot / (F_swmm5.UCF(RAINDEPTH)));
            }
            if ((F_swmm5.Link[j].xsect.rBot <= 0.0d)) {
                F_report.report_writeErrorMsg(ERR_XSECT, F_swmm5.Link[j].ID);
            }
        }
        if ((F_swmm5.Conduit[k].length <= 0.0d)) {
            F_report.report_writeErrorMsg(ERR_LENGTH, F_swmm5.Link[j].ID);
        }
        if ((F_swmm5.Conduit[k].roughness <= 0.0d)) {
            F_report.report_writeErrorMsg(ERR_ROUGHNESS, F_swmm5.Link[j].ID);
        }
        if ((F_swmm5.Conduit[k].barrels <= 0)) {
            F_report.report_writeErrorMsg(ERR_BARRELS, F_swmm5.Link[j].ID);
        }
        if ((F_swmm5.Link[j].xsect.type != DUMMY)) {
            if ((F_swmm5.Link[j].xsect.type < 0)) {
                F_report.report_writeErrorMsg(ERR_NO_XSECT, F_swmm5.Link[j].ID);
            } else if ((F_swmm5.Link[j].xsect.aFull <= 0.0d)) {
                F_report.report_writeErrorMsg(ERR_XSECT, F_swmm5.Link[j].ID);
            }
        }
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        if ((F_swmm5.Link[j].offset1 < 0.0d)) {
            F_report.report_writeWarningMsg(L307, F_swmm5.Link[j].ID);
            F_swmm5.Link[j].@offset1 = 0.0d;
        }
        if ((F_swmm5.Link[j].offset2 < 0.0d)) {
            F_report.report_writeWarningMsg(L307, F_swmm5.Link[j].ID);
            F_swmm5.Link[j].@offset2 = 0.0d;
        }
        if ((F_swmm5.Link[j].xsect.type == FILLED_CIRCULAR)) {
            F_swmm5.Link[j].@offset1 = (F_swmm5.Link[j].offset1 + F_swmm5.Link[j].xsect.yBot);
            F_swmm5.Link[j].@offset2 = (F_swmm5.Link[j].offset2 + F_swmm5.Link[j].xsect.yBot);
        }
        slope = conduit_getSlope(j);
        F_swmm5.Conduit[k].@slope = slope;
        if ((((F_swmm5.RouteModel == DW) && (slope < 0.0d)) && (F_swmm5.Link[j].xsect.type != DUMMY))) {
            conduit_reverse(j, k);
        }
        roughness = F_swmm5.Conduit[k].roughness;
        if (((F_swmm5.RouteModel == DW) && (F_swmm5.Link[j].xsect.type == FORCE_MAIN))) {
            roughness = F_forcmain.forcemain_getEquivN(j, k);
        }
        if ((F_swmm5.Link[j].xsect.type == IRREGULAR)) {
            lengthFactor = F_swmm5.Transect[F_swmm5.Link[j].xsect.transect].lengthFactor;
            roughness *= (Math.sqrt(lengthFactor));
        }
        lengthFactor = 1.0d;
        if ((((F_swmm5.RouteModel == DW) && (R.dgt(F_swmm5.LengtheningStep, 0.0d))) && (F_swmm5.Link[j].xsect.type != DUMMY))) {
            lengthFactor = conduit_getLengthFactor(j, k, roughness);
        }
        if ((lengthFactor != 1.0d)) {
            F_swmm5.Conduit[k].@modLength = (lengthFactor * conduit_getLength(j));
            slope = (slope / lengthFactor);
            roughness = (roughness / Math.sqrt(lengthFactor));
        }
        if (((F_swmm5.RouteModel == DW) && (F_swmm5.Link[j].xsect.type == FORCE_MAIN))) {
            F_swmm5.Link[j].xsect.@sBot = F_forcmain.forcemain_getRoughFactor(j, lengthFactor);
        }
        F_swmm5.Conduit[k].@roughFactor = (32.2d * ((((roughness / 1.486d)) * ((roughness / 1.486d)))));
        if ((F_swmm5.Link[j].xsect.type == DUMMY)) {
            F_swmm5.Conduit[k].@beta = 0.0d;
        } else {
            F_swmm5.Conduit[k].@beta = ((1.486d * Math.sqrt(Math.abs(slope))) / roughness);
        }
        F_swmm5.Link[j].@qFull = (F_swmm5.Link[j].xsect.sFull * F_swmm5.Conduit[k].beta);
        F_swmm5.Conduit[k].@qMax = (F_swmm5.Link[j].xsect.sMax * F_swmm5.Conduit[k].beta);
        aa = (((F_swmm5.Conduit[k].beta / Math.sqrt(32.2d)) * Glibm.pow(F_swmm5.Link[j].xsect.yFull, 0.1666667d)) * 0.3d);
        if (R.dge(aa, 1.0d)) {
            F_swmm5.Conduit[k].@superCritical = 1;
        } else {
            F_swmm5.Conduit[k].@superCritical = 0;
        }
        if ((((F_swmm5.Link[j].cLossInlet == 0.0d) && (F_swmm5.Link[j].cLossOutlet == 0.0d)) && (F_swmm5.Link[j].cLossAvg == 0.0d))) {
            F_swmm5.Conduit[k].@hasLosses = 0;
        } else {
            F_swmm5.Conduit[k].@hasLosses = 1;
        }
    }

    // link.c:1158
    static void conduit_reverse(int j, int k) {
        int i = 0;
        double z = 0.0d;
        double cLoss = 0.0d;
        i = F_swmm5.Link[j].node1;
        F_swmm5.Link[j].@node1 = F_swmm5.Link[j].node2;
        F_swmm5.Link[j].@node2 = i;
        z = F_swmm5.Link[j].offset1;
        F_swmm5.Link[j].@offset1 = F_swmm5.Link[j].offset2;
        F_swmm5.Link[j].@offset2 = z;
        cLoss = F_swmm5.Link[j].cLossInlet;
        F_swmm5.Link[j].@cLossInlet = F_swmm5.Link[j].cLossOutlet;
        F_swmm5.Link[j].@cLossOutlet = cLoss;
        F_swmm5.Conduit[k].@slope = (-F_swmm5.Conduit[k].slope);
        F_swmm5.Link[j].@direction = (F_swmm5.Link[j].direction * ((byte) ((-1))));
        F_swmm5.Link[j].@q0 = (-F_swmm5.Link[j].q0);
    }

    // link.c:1195
    static double conduit_getLength(int j) {
        int k = F_swmm5.Link[j].subIndex;
        int t = 0;
        if ((F_swmm5.Link[j].xsect.type != IRREGULAR)) {
            return F_swmm5.Conduit[k].length;
        }
        t = F_swmm5.Link[j].xsect.transect;
        if (((t < 0) || (t >= F_swmm5.Nobjects[TRANSECT]))) {
            return F_swmm5.Conduit[k].length;
        }
        return (F_swmm5.Conduit[k].length / F_swmm5.Transect[t].lengthFactor);
    }

    // link.c:1217
    static double conduit_getLengthFactor(int j, int k, double roughness) {
        double ratio = 0.0d;
        double yFull = 0.0d;
        double vFull = 0.0d;
        double tStep = 0.0d;
        yFull = F_swmm5.Link[j].xsect.yFull;
        if ((F_xsect.xsect_isOpen(F_swmm5.Link[j].xsect.type) != 0)) {
            yFull = (F_swmm5.Link[j].xsect.aFull / F_xsect.xsect_getWofY(F_swmm5.Link[j].xsect, yFull));
        }
        vFull = ((((1.486d / roughness) * F_swmm5.Link[j].xsect.sFull) * Math.sqrt(Math.abs(F_swmm5.Conduit[k].slope))) / F_swmm5.Link[j].xsect.aFull);
        if ((F_swmm5.LengtheningStep == 0.0d)) {
            tStep = F_swmm5.RouteStep;
        } else {
            tStep = (((((F_swmm5.RouteStep) <= (F_swmm5.LengtheningStep))) ? (F_swmm5.RouteStep) : (F_swmm5.LengtheningStep)));
        }
        ratio = ((((Math.sqrt((32.2d * yFull)) + vFull)) * tStep) / conduit_getLength(j));
        if (R.dgt(ratio, 1.0d)) {
            return ratio;
        } else {
            return 1.0d;
        }
        throw new IllegalStateException('conduit_getLengthFactor: no return');
    }

    // link.c:1258
    static double conduit_getSlope(int j) {
        double elev1 = 0.0d;
        double elev2 = 0.0d;
        double delta = 0.0d;
        double slope = 0.0d;
        double length = conduit_getLength(j);
        elev1 = (F_swmm5.Link[j].offset1 + F_swmm5.Node_[F_swmm5.Link[j].node1].invertElev);
        elev2 = (F_swmm5.Link[j].offset2 + F_swmm5.Node_[F_swmm5.Link[j].node2].invertElev);
        delta = Math.abs((elev1 - elev2));
        if ((delta < MIN_DELTA_Z)) {
            F_report.report_writeWarningMsg(L308, F_swmm5.Link[j].ID);
            delta = MIN_DELTA_Z;
        }
        if (R.dge(delta, length)) {
            F_report.report_writeWarningMsg(L309, F_swmm5.Link[j].ID);
            slope = (delta / length);
        } else {
            slope = (delta / Math.sqrt(((((length) * (length))) - (((delta) * (delta))))));
        }
        if (((R.dgt(F_swmm5.MinSlope.v, 0.0d)) && (slope < F_swmm5.MinSlope.v))) {
            F_report.report_writeWarningMsg(L310, F_swmm5.Link[j].ID);
            slope = F_swmm5.MinSlope.v;
            if (((F_swmm5.RouteModel == SF) || (F_swmm5.RouteModel == KW))) {
                return slope;
            }
        }
        if ((elev1 < elev2)) {
            slope = (-slope);
        }
        return slope;
    }

    // link.c:1304
    static void conduit_initState(int j, int k) {
        F_swmm5.Link[j].@newDepth = link_getYnorm(j, (F_swmm5.Link[j].q0 / F_swmm5.Conduit[k].barrels));
        F_swmm5.Link[j].@oldDepth = F_swmm5.Link[j].newDepth;
        F_swmm5.Conduit[k].@evapLossRate = 0.0d;
        F_swmm5.Conduit[k].@seepLossRate = 0.0d;
    }

    // link.c:1320
    static double conduit_getInflow(int j) {
        double qIn = F_node.node_getOutflow(F_swmm5.Link[j].node1, j);
        if (R.dgt(F_swmm5.Link[j].qLimit, 0.0d)) {
            qIn = (((((qIn) <= (F_swmm5.Link[j].qLimit))) ? (qIn) : (F_swmm5.Link[j].qLimit)));
        }
        return qIn;
    }

    // link.c:1334
    static double conduit_getLossRate(int j, int routeModel, double q, double tstep) {
        TXsect xsect = null;
        double depth = (0.5d * ((F_swmm5.Link[j].oldDepth + F_swmm5.Link[j].newDepth)));
        double length = 0.0d;
        double width = 0.0d;
        double topWidth = 0.0d;
        double evapLossRate = 0.0d;
        double seepLossRate = 0.0d;
        double totalLossRate = 0.0d;
        if (R.dgt(depth, 0.0001d)) {
            xsect = F_swmm5.Link[j].xsect;
            length = conduit_getLength(j);
            if (((F_xsect.xsect_isOpen(xsect.type) != 0) && (R.dgt(F_swmm5.Evap.rate, 0.0d)))) {
                topWidth = F_xsect.xsect_getWofY(xsect, depth);
                evapLossRate = ((topWidth * length) * F_swmm5.Evap.rate);
            }
            if (R.dgt(F_swmm5.Link[j].seepRate, 0.0d)) {
                if ((xsect.type == RECT_CLOSED)) {
                    width = xsect.wMax;
                } else {
                    if (R.dge(depth, xsect.ywMax)) {
                        depth = xsect.ywMax;
                    }
                    width = F_xsect.xsect_getWofY(xsect, depth);
                }
                seepLossRate = ((F_swmm5.Link[j].seepRate * width) * length);
                seepLossRate *= F_swmm5.Adjust.hydconFactor;
            }
            totalLossRate = (evapLossRate + seepLossRate);
            if ((routeModel == DW)) {
                q = (F_swmm5.Link[j].newVolume / tstep);
            } else {
                q = (((((q) < 0)) ? (-(q)) : (q)));
            }
            if (R.dgt(totalLossRate, q)) {
                evapLossRate = ((evapLossRate * q) / totalLossRate);
                seepLossRate = ((seepLossRate * q) / totalLossRate);
                totalLossRate = q;
            }
        }
        F_swmm5.Conduit[F_swmm5.Link[j].subIndex].@evapLossRate = evapLossRate;
        F_swmm5.Conduit[F_swmm5.Link[j].subIndex].@seepLossRate = seepLossRate;
        return totalLossRate;
    }

    // link.c:1406
    static int pump_readParams(int j, int k, CPtr[] tok, int ntoks) {
        int m = 0;
        int n1 = 0;
        int n2 = 0;
        double[] x = new double[4];
        CPtr id = null;
        if ((ntoks < 3)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(LINK, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        n1 = F_project.project_findObject(NODE, tok[1]);
        if ((n1 < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[1]);
        }
        n2 = F_project.project_findObject(NODE, tok[2]);
        if ((n2 < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[2]);
        }
        x[0] = (-1.0d);
        if ((ntoks >= 4)) {
            if (!(F_swmm5.strcomp(tok[3], L275) != 0)) {
                m = F_project.project_findObject(CURVE, tok[3]);
                if ((m < 0)) {
                    return F_error.error_setInpError(ERR_NAME, tok[3]);
                }
                x[0] = m;
            }
        }
        x[1] = 1.0d;
        if ((ntoks >= 5)) {
            m = F_input.findmatch(tok[4], F_keywords.OffOnWords);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[4]);
            }
            x[1] = m;
        }
        x[2] = 0.0d;
        if ((ntoks >= 6)) {
            if (((!(F_input.getDouble(tok[5], new DElem(x, 2)) != 0)) || (x[2] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[5]);
            }
        }
        x[3] = 0.0d;
        if ((ntoks >= 7)) {
            if (((!(F_input.getDouble(tok[6], new DElem(x, 3)) != 0)) || (x[3] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[6]);
            }
        }
        F_swmm5.Link[j].@ID = id;
        link_setParams(j, PUMP, n1, n2, k, x);
        return 0;
    }

    // link.c:1473
    static void pump_validate(int j, int k) {
        int m = 0;
        int n1 = 0;
        DBox x = new DBox(0.0d);
        DBox y = new DBox(0.0d);
        F_swmm5.Link[j].xsect.@yFull = 0.0d;
        m = F_swmm5.Pump[k].pumpCurve;
        if ((m < 0)) {
            F_swmm5.Pump[k].@type = IDEAL_PUMP;
        } else {
            if (((F_swmm5.Curve[m].curveType < PUMP1_CURVE) || (F_swmm5.Curve[m].curveType > PUMP5_CURVE))) {
                F_report.report_writeErrorMsg(ERR_NO_CURVE, F_swmm5.Link[j].ID);
            } else {
                F_swmm5.Pump[k].@type = (F_swmm5.Curve[m].curveType - PUMP1_CURVE);
                if ((F_table.table_getFirstEntry(F_swmm5.Curve[m], x, y) != 0)) {
                    F_swmm5.Link[j].@qFull = y.v;
                    F_swmm5.Pump[k].@xMin = x.v;
                    F_swmm5.Pump[k].@xMax = x.v;
                    L_w1: while ((F_table.table_getNextEntry(F_swmm5.Curve[m], x, y) != 0)) {
                        F_swmm5.Link[j].@qFull = (((R.dge((y.v), (F_swmm5.Link[j].qFull))) ? (y.v) : (F_swmm5.Link[j].qFull)));
                        F_swmm5.Pump[k].@xMax = x.v;
                    }
                }
                F_swmm5.Link[j].@qFull = (F_swmm5.Link[j].qFull / (F_swmm5.UCF(FLOW)));
            }
        }
        if (((R.dgt(F_swmm5.Pump[k].yOn, 0.0d)) && (F_swmm5.Pump[k].yOn <= F_swmm5.Pump[k].yOff))) {
            F_report.report_writeErrorMsg(ERR_PUMP_LIMITS, F_swmm5.Link[j].ID);
        }
        if ((F_swmm5.Pump[k].type == TYPE1_PUMP)) {
            n1 = F_swmm5.Link[j].node1;
            if ((F_swmm5.Node_[n1].type != STORAGE)) {
                F_swmm5.Node_[n1].@fullVolume = (((R.dge((F_swmm5.Node_[n1].fullVolume), ((F_swmm5.Pump[k].xMax / F_swmm5.UCF(VOLUME))))) ? (F_swmm5.Node_[n1].fullVolume) : ((F_swmm5.Pump[k].xMax / F_swmm5.UCF(VOLUME)))));
            }
        }
    }

    // link.c:1534
    static void pump_initState(int j, int k) {
        F_swmm5.Link[j].@setting = F_swmm5.Pump[k].initSetting;
        F_swmm5.Link[j].@targetSetting = F_swmm5.Pump[k].initSetting;
    }

    // link.c:1548
    static double pump_getInflow(int j) {
        int k = 0;
        int m = 0;
        int n1 = 0;
        int n2 = 0;
        double vol = 0.0d;
        double depth = 0.0d;
        double head = 0.0d;
        double qIn = 0.0d;
        double qIn1 = 0.0d;
        double dh = 0.001d;
        double s = 1.0d;
        k = F_swmm5.Link[j].subIndex;
        m = F_swmm5.Pump[k].pumpCurve;
        n1 = F_swmm5.Link[j].node1;
        n2 = F_swmm5.Link[j].node2;
        F_swmm5.Link[j].@flowClass = NO;
        F_swmm5.Link[j].@setting = F_swmm5.Link[j].targetSetting;
        if ((F_swmm5.Link[j].setting == 0.0d)) {
            return 0.0d;
        }
        if ((F_swmm5.Pump[k].type == IDEAL_PUMP)) {
            qIn = (F_swmm5.Node_[n1].inflow + F_swmm5.Node_[n1].overflow);
        } else {
            int __sw1 = F_swmm5.Curve[m].curveType;
            if (__sw1 == PUMP1_CURVE) {
                vol = (F_swmm5.Node_[n1].newVolume * F_swmm5.UCF(VOLUME));
                qIn = (F_table.table_intervalLookup(F_swmm5.Curve[m], vol) / F_swmm5.UCF(FLOW));
                if (((vol < F_swmm5.Pump[k].xMin) || (R.dgt(vol, F_swmm5.Pump[k].xMax)))) {
                    F_swmm5.Link[j].@flowClass = YES;
                }
            } else if (__sw1 == PUMP2_CURVE) {
                depth = (F_swmm5.Node_[n1].newDepth * F_swmm5.UCF(LENGTH));
                qIn = (F_table.table_intervalLookup(F_swmm5.Curve[m], depth) / F_swmm5.UCF(FLOW));
                if (((depth < F_swmm5.Pump[k].xMin) || (R.dgt(depth, F_swmm5.Pump[k].xMax)))) {
                    F_swmm5.Link[j].@flowClass = YES;
                }
            } else if (__sw1 == PUMP3_CURVE || __sw1 == PUMP5_CURVE) {
                if ((F_swmm5.Curve[m].curveType == PUMP5_CURVE)) {
                    s = F_swmm5.Link[j].setting;
                }
                head = ((((((F_swmm5.Node_[n2].newDepth + F_swmm5.Node_[n2].invertElev)) - ((F_swmm5.Node_[n1].newDepth + F_swmm5.Node_[n1].invertElev)))) / s) / s);
                head = ((((R.dge((head), (0.0d))) ? (head) : (0.0d))) * F_swmm5.UCF(LENGTH));
                qIn = (F_table.table_lookup(F_swmm5.Curve[m], head) / F_swmm5.UCF(FLOW));
                F_swmm5.Link[j].@dqdh = ((((-(F_table.table_getSlope(F_swmm5.Curve[m], head))) * F_swmm5.UCF(LENGTH)) / F_swmm5.UCF(FLOW)) / s);
                if (((head < F_swmm5.Pump[k].xMin) || (R.dgt(head, F_swmm5.Pump[k].xMax)))) {
                    F_swmm5.Link[j].@flowClass = YES;
                }
            } else if (__sw1 == PUMP4_CURVE) {
                depth = F_swmm5.Node_[n1].newDepth;
                qIn = (F_table.table_lookup(F_swmm5.Curve[m], (depth * F_swmm5.UCF(LENGTH))) / F_swmm5.UCF(FLOW));
                qIn1 = (F_table.table_lookup(F_swmm5.Curve[m], (((depth + dh)) * F_swmm5.UCF(LENGTH))) / F_swmm5.UCF(FLOW));
                F_swmm5.Link[j].@dqdh = (((qIn1 - qIn)) / dh);
                depth *= (F_swmm5.UCF(LENGTH));
                if ((depth < F_swmm5.Pump[k].xMin)) {
                    F_swmm5.Link[j].@flowClass = DN_DRY;
                }
                if (R.dgt(depth, F_swmm5.Pump[k].xMax)) {
                    F_swmm5.Link[j].@flowClass = UP_DRY;
                }
            } else {
                qIn = 0.0d;
            }
        }
        if ((qIn < 0.0d)) {
            qIn = 0.0d;
        }
        return (qIn * F_swmm5.Link[j].setting);
    }

    // link.c:1641
    static int orifice_readParams(int j, int k, CPtr[] tok, int ntoks) {
        int m = 0;
        int n1 = 0;
        int n2 = 0;
        double[] x = new double[5];
        CPtr id = null;
        if ((ntoks < 6)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(LINK, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        n1 = F_project.project_findObject(NODE, tok[1]);
        if ((n1 < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[1]);
        }
        n2 = F_project.project_findObject(NODE, tok[2]);
        if ((n2 < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[2]);
        }
        m = F_input.findmatch(tok[3], F_keywords.OrificeTypeWords);
        if ((m < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[3]);
        }
        x[0] = m;
        if (((F_swmm5.LinkOffsets == ELEV_OFFSET) && (tok[4].g(0) == 42))) {
            x[1] = (-10000000000.0d);
        } else if (!(F_input.getDouble(tok[4], new DElem(x, 1)) != 0)) {
            return F_error.error_setInpError(ERR_NUMBER, tok[4]);
        }
        if (((!(F_input.getDouble(tok[5], new DElem(x, 2)) != 0)) || (x[2] < 0.0d))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[5]);
        }
        x[3] = 0.0d;
        if ((ntoks >= 7)) {
            m = F_input.findmatch(tok[6], F_keywords.NoYesWords);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[6]);
            }
            x[3] = m;
        }
        x[4] = 0.0d;
        if ((ntoks >= 8)) {
            if (((!(F_input.getDouble(tok[7], new DElem(x, 4)) != 0)) || (x[4] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[7]);
            }
        }
        F_swmm5.Link[j].@ID = id;
        link_setParams(j, ORIFICE, n1, n2, k, x);
        return 0;
    }

    // link.c:1696
    static void orifice_validate(int j, int k) {
        int err = 0;
        if (((F_swmm5.Link[j].xsect.type != RECT_CLOSED) && (F_swmm5.Link[j].xsect.type != CIRCULAR))) {
            err = ERR_REGULATOR_SHAPE;
        }
        if ((err > 0)) {
            F_report.report_writeErrorMsg(err, F_swmm5.Link[j].ID);
            return;
        }
        if ((F_swmm5.Link[j].offset1 < 0.0d)) {
            F_swmm5.Link[j].@offset1 = 0.0d;
        }
        orifice_setSetting(j, 0.0d);
        F_swmm5.Orifice[k].@length = ((2.0d * F_swmm5.RouteStep) * Math.sqrt((32.2d * F_swmm5.Link[j].xsect.yFull)));
        F_swmm5.Orifice[k].@length = (((R.dge((200.0d), (F_swmm5.Orifice[k].length))) ? (200.0d) : (F_swmm5.Orifice[k].length)));
        F_swmm5.Orifice[k].@surfArea = 0.0d;
    }

    // link.c:1729
    static void orifice_setSetting(int j, double tstep) {
        int k = F_swmm5.Link[j].subIndex;
        double delta = 0.0d;
        double step = 0.0d;
        double h = 0.0d;
        double f = 0.0d;
        if (((F_swmm5.Orifice[k].orate == 0.0d) || (tstep == 0.0d))) {
            F_swmm5.Link[j].@setting = F_swmm5.Link[j].targetSetting;
        } else {
            delta = (F_swmm5.Link[j].targetSetting - F_swmm5.Link[j].setting);
            step = (tstep / F_swmm5.Orifice[k].orate);
            if (R.dge((step + 0.001d), Math.abs(delta))) {
                F_swmm5.Link[j].@setting = F_swmm5.Link[j].targetSetting;
            } else {
                F_swmm5.Link[j].@setting = (F_swmm5.Link[j].setting + ((((((delta) < 0)) ? ((-1)) : (1))) * step));
            }
        }
        h = (F_swmm5.Link[j].setting * F_swmm5.Link[j].xsect.yFull);
        f = (F_xsect.xsect_getAofY(F_swmm5.Link[j].xsect, h) * Math.sqrt((2.0d * 32.2d)));
        F_swmm5.Orifice[k].@cOrif = (F_swmm5.Orifice[k].cDisch * f);
        F_swmm5.Orifice[k].@cWeir = (orifice_getWeirCoeff(j, k, h) * f);
    }

    // link.c:1766
    static double orifice_getWeirCoeff(int j, int k, double h) {
        double w = 0.0d;
        double aOverL = 0.0d;
        if ((F_swmm5.Orifice[k].type == BOTTOM_ORIFICE)) {
            if ((F_swmm5.Link[j].xsect.type == CIRCULAR)) {
                aOverL = (h / 4.0d);
            } else {
                w = F_swmm5.Link[j].xsect.wMax;
                aOverL = (((h * w)) / ((2.0d * ((h + w)))));
            }
            h = ((F_swmm5.Orifice[k].cDisch / 0.414d) * aOverL);
            F_swmm5.Orifice[k].@hCrit = h;
        } else {
            F_swmm5.Orifice[k].@hCrit = h;
            h = (h / 2.0d);
        }
        return (F_swmm5.Orifice[k].cDisch * Math.sqrt(h));
    }

    // link.c:1812
    static double orifice_getInflow(int j) {
        int k = 0;
        int n1 = 0;
        int n2 = 0;
        double head = 0.0d;
        double h1 = 0.0d;
        double h2 = 0.0d;
        double y1 = 0.0d;
        double dir = 0.0d;
        double f = 0.0d;
        double hcrest = 0.0d;
        double hcrown = 0.0d;
        double hmidpt = 0.0d;
        double q = 0.0d;
        double ratio = 0.0d;
        n1 = F_swmm5.Link[j].node1;
        n2 = F_swmm5.Link[j].node2;
        k = F_swmm5.Link[j].subIndex;
        if ((F_swmm5.RouteModel == DW)) {
            h1 = (F_swmm5.Node_[n1].newDepth + F_swmm5.Node_[n1].invertElev);
            h2 = (F_swmm5.Node_[n2].newDepth + F_swmm5.Node_[n2].invertElev);
        } else {
            h1 = (F_swmm5.Node_[n1].newDepth + F_swmm5.Node_[n1].invertElev);
            h2 = F_swmm5.Node_[n1].invertElev;
        }
        dir = ((R.dge(h1, h2)) ? 1.0d : (-1.0d));
        y1 = F_swmm5.Node_[n1].newDepth;
        if ((dir < 0.0d)) {
            head = h1;
            h1 = h2;
            h2 = head;
            y1 = F_swmm5.Node_[n2].newDepth;
        }
        if ((F_swmm5.Orifice[k].type == BOTTOM_ORIFICE)) {
            hcrest = (F_swmm5.Node_[n1].invertElev + F_swmm5.Link[j].offset1);
            if ((h1 < hcrest)) {
                head = 0.0d;
            } else if (R.dgt(h2, hcrest)) {
                head = (h1 - h2);
            } else {
                head = (h1 - hcrest);
            }
            f = (head / F_swmm5.Orifice[k].hCrit);
            f = (((((f) <= (1.0d))) ? (f) : (1.0d)));
        } else {
            hcrest = (F_swmm5.Node_[n1].invertElev + F_swmm5.Link[j].offset1);
            hcrown = (hcrest + (F_swmm5.Link[j].xsect.yFull * F_swmm5.Link[j].setting));
            hmidpt = (((hcrest + hcrown)) / 2.0d);
            if (((h1 < hcrown) && (R.dgt(hcrown, hcrest)))) {
                f = (((h1 - hcrest)) / ((hcrown - hcrest)));
            } else {
                f = 1.0d;
            }
            if ((f < 1.0d)) {
                head = (h1 - hcrest);
            } else if ((h2 < hmidpt)) {
                head = (h1 - hmidpt);
            } else {
                head = (h1 - h2);
            }
        }
        if ((((head <= 0.0001d) || (y1 <= 0.0001d)) || (link_setFlapGate(j, n1, n2, dir) != 0))) {
            F_swmm5.Link[j].@newDepth = 0.0d;
            F_swmm5.Link[j].@flowClass = DRY;
            F_swmm5.Orifice[k].@surfArea = (0.0001d * F_swmm5.Orifice[k].length);
            F_swmm5.Link[j].@dqdh = 0.0d;
            return 0.0d;
        }
        F_swmm5.Link[j].@flowClass = SUBCRITICAL;
        if (R.dgt(hcrest, h2)) {
            if ((dir == 1.0d)) {
                F_swmm5.Link[j].@flowClass = DN_CRITICAL;
            } else {
                F_swmm5.Link[j].@flowClass = UP_CRITICAL;
            }
        }
        y1 = (F_swmm5.Link[j].xsect.yFull * F_swmm5.Link[j].setting);
        if ((F_swmm5.Orifice[k].type == SIDE_ORIFICE)) {
            F_swmm5.Link[j].@newDepth = (y1 * f);
            F_swmm5.Orifice[k].@surfArea = (F_xsect.xsect_getWofY(F_swmm5.Link[j].xsect, F_swmm5.Link[j].newDepth) * F_swmm5.Orifice[k].length);
        } else {
            F_swmm5.Link[j].@newDepth = y1;
            F_swmm5.Orifice[k].@surfArea = F_xsect.xsect_getAofY(F_swmm5.Link[j].xsect, y1);
        }
        q = (dir * orifice_getFlow(j, k, head, f, F_swmm5.Link[j].hasFlapGate));
        if (((f < 1.0d) && (R.dgt(h2, hcrest)))) {
            ratio = (((h2 - hcrest)) / ((h1 - hcrest)));
            q *= (Glibm.pow(((1.0d - Glibm.pow(ratio, 1.5d))), 0.385d));
        }
        return q;
    }

    // link.c:1938
    static double orifice_getFlow(int j, int k, double head, double f, int hasFlapGate) {
        double area = 0.0d;
        double q = 0.0d;
        double veloc = 0.0d;
        double hLoss = 0.0d;
        if (((head == 0.0d) || (f <= 0.0d))) {
            F_swmm5.Link[j].@dqdh = 0.0d;
            return 0.0d;
        } else if ((f < 1.0d)) {
            q = (F_swmm5.Orifice[k].cWeir * Glibm.pow(f, 1.5d));
            F_swmm5.Link[j].@dqdh = ((1.5d * q) / ((f * F_swmm5.Orifice[k].hCrit)));
        } else {
            q = (F_swmm5.Orifice[k].cOrif * Math.sqrt(head));
            F_swmm5.Link[j].@dqdh = (q / ((2.0d * head)));
        }
        if ((hasFlapGate != 0)) {
            area = F_xsect.xsect_getAofY(F_swmm5.Link[j].xsect, (F_swmm5.Link[j].setting * F_swmm5.Link[j].xsect.yFull));
            veloc = (q / area);
            hLoss = (((((4.0d / 32.2d)) * veloc) * veloc) * Glibm.exp((((-1.15d) * veloc) / Math.sqrt(head))));
            if ((f < 1.0d)) {
                f = (f - (hLoss / F_swmm5.Orifice[k].hCrit));
                if ((f < 0.0d)) {
                    f = 0.0d;
                }
            } else {
                head = (head - hLoss);
                if ((head < 0.0d)) {
                    head = 0.0d;
                }
            }
            q = orifice_getFlow(j, k, head, f, 0);
        }
        return q;
    }

    // link.c:2010
    static int weir_readParams(int j, int k, CPtr[] tok, int ntoks) {
        int m = 0;
        int n1 = 0;
        int n2 = 0;
        double[] x = new double[10];
        CPtr id = null;
        if ((ntoks < 6)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(LINK, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        n1 = F_project.project_findObject(NODE, tok[1]);
        if ((n1 < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[1]);
        }
        n2 = F_project.project_findObject(NODE, tok[2]);
        if ((n2 < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[2]);
        }
        m = F_input.findmatch(tok[3], F_keywords.WeirTypeWords);
        if ((m < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[3]);
        }
        x[0] = m;
        if (((F_swmm5.LinkOffsets == ELEV_OFFSET) && (tok[4].g(0) == 42))) {
            x[1] = (-10000000000.0d);
        } else if (!(F_input.getDouble(tok[4], new DElem(x, 1)) != 0)) {
            return F_error.error_setInpError(ERR_NUMBER, tok[4]);
        }
        if (((!(F_input.getDouble(tok[5], new DElem(x, 2)) != 0)) || (x[2] < 0.0d))) {
            return F_error.error_setInpError(ERR_NUMBER, tok[5]);
        }
        x[3] = 0.0d;
        x[4] = 0.0d;
        x[5] = 0.0d;
        x[6] = 1.0d;
        x[7] = 0.0d;
        x[8] = 0.0d;
        x[9] = (-1.0d);
        if (((ntoks >= 7) && (tok[6].g(0) != 42))) {
            m = F_input.findmatch(tok[6], F_keywords.NoYesWords);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[6]);
            }
            x[3] = m;
        }
        if (((ntoks >= 8) && (tok[7].g(0) != 42))) {
            if (((!(F_input.getDouble(tok[7], new DElem(x, 4)) != 0)) || (x[4] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[7]);
            }
        }
        if (((ntoks >= 9) && (tok[8].g(0) != 42))) {
            if (((!(F_input.getDouble(tok[8], new DElem(x, 5)) != 0)) || (x[5] < 0.0d))) {
                return F_error.error_setInpError(ERR_NUMBER, tok[8]);
            }
        }
        if (((ntoks >= 10) && (tok[9].g(0) != 42))) {
            m = F_input.findmatch(tok[9], F_keywords.NoYesWords);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[9]);
            }
            x[6] = m;
        }
        if ((((m = R.D2I(x[0]))) == ROADWAY_WEIR)) {
            if ((ntoks >= 11)) {
                if (((!(F_input.getDouble(tok[10], new DElem(x, 7)) != 0)) || (x[7] < 0.0d))) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[10]);
                }
            }
            if ((ntoks >= 12)) {
                if ((F_swmm5.strcomp(tok[11], L311) != 0)) {
                    x[8] = 1.0d;
                } else if ((F_swmm5.strcomp(tok[11], L312) != 0)) {
                    x[8] = 2.0d;
                }
            }
        }
        if (((ntoks >= 13) && (tok[12].g(0) != 42))) {
            m = F_project.project_findObject(CURVE, tok[12]);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[12]);
            }
            x[9] = m;
        }
        F_swmm5.Link[j].@ID = id;
        link_setParams(j, WEIR, n1, n2, k, x);
        return 0;
    }

    // link.c:2103
    static void weir_validate(int j, int k) {
        int err = 0;
        double q = 0.0d;
        DBox q1 = new DBox(0.0d);
        DBox q2 = new DBox(0.0d);
        double head = 0.0d;
        int __sw1 = F_swmm5.Weir[k].type;
        if (__sw1 == TRANSVERSE_WEIR || __sw1 == SIDEFLOW_WEIR || __sw1 == ROADWAY_WEIR) {
            if ((F_swmm5.Link[j].xsect.type != RECT_OPEN)) {
                err = ERR_REGULATOR_SHAPE;
            }
            F_swmm5.Weir[k].@slope = 0.0d;
        } else if (__sw1 == VNOTCH_WEIR) {
            if ((F_swmm5.Link[j].xsect.type != TRIANGULAR)) {
                err = ERR_REGULATOR_SHAPE;
            } else {
                F_swmm5.Weir[k].@slope = F_swmm5.Link[j].xsect.sBot;
            }
        } else if (__sw1 == TRAPEZOIDAL_WEIR) {
            if ((F_swmm5.Link[j].xsect.type != TRAPEZOIDAL)) {
                err = ERR_REGULATOR_SHAPE;
            } else {
                F_swmm5.Weir[k].@slope = F_swmm5.Link[j].xsect.sBot;
            }
        }
        if ((err > 0)) {
            F_report.report_writeErrorMsg(err, F_swmm5.Link[j].ID);
            return;
        }
        if ((F_swmm5.Link[j].offset1 < 0.0d)) {
            F_swmm5.Link[j].@offset1 = 0.0d;
        }
        F_swmm5.Weir[k].@length = ((2.0d * F_swmm5.RouteStep) * Math.sqrt((32.2d * F_swmm5.Link[j].xsect.yFull)));
        F_swmm5.Weir[k].@length = (((R.dge((200.0d), (F_swmm5.Weir[k].length))) ? (200.0d) : (F_swmm5.Weir[k].length)));
        F_swmm5.Weir[k].@surfArea = 0.0d;
        head = F_swmm5.Link[j].xsect.yFull;
        weir_getFlow(j, k, head, 1.0d, 0, q1, q2);
        q = (q1.v + q2.v);
        head = (head / 2.0d);
        F_swmm5.Weir[k].@cSurcharge = (q / Math.sqrt(head));
    }

    // link.c:2166
    static void weir_setSetting(int j) {
        int k = F_swmm5.Link[j].subIndex;
        double h = 0.0d;
        double q = 0.0d;
        DBox q1 = new DBox(0.0d);
        DBox q2 = new DBox(0.0d);
        F_swmm5.Link[j].@setting = F_swmm5.Link[j].targetSetting;
        if (!(F_swmm5.Weir[k].canSurcharge != 0)) {
            return;
        }
        if ((F_swmm5.Weir[k].type == ROADWAY_WEIR)) {
            return;
        }
        if ((F_swmm5.Link[j].setting == 0.0d)) {
            F_swmm5.Weir[k].@cSurcharge = 0.0d;
        } else {
            h = (F_swmm5.Link[j].setting * F_swmm5.Link[j].xsect.yFull);
            weir_getFlow(j, k, h, 1.0d, 0, q1, q2);
            q = (q1.v + q2.v);
            h = (h / 2.0d);
            F_swmm5.Weir[k].@cSurcharge = (q / Math.sqrt(h));
        }
    }

    // link.c:2198
    static double weir_getInflow(int j) {
        int n1 = 0;
        int n2 = 0;
        int k = 0;
        DBox q1 = new DBox(0.0d);
        DBox q2 = new DBox(0.0d);
        double head = 0.0d;
        double h1 = 0.0d;
        double h2 = 0.0d;
        double hcrest = 0.0d;
        double hcrown = 0.0d;
        double y = 0.0d;
        double dir = 0.0d;
        double ratio = 0.0d;
        double[] weirPower = new double[] { 1.5d, (5.0d / 3.0d), 2.5d, 1.5d };
        n1 = F_swmm5.Link[j].node1;
        n2 = F_swmm5.Link[j].node2;
        k = F_swmm5.Link[j].subIndex;
        if ((F_swmm5.RouteModel == DW)) {
            h1 = (F_swmm5.Node_[n1].newDepth + F_swmm5.Node_[n1].invertElev);
            h2 = (F_swmm5.Node_[n2].newDepth + F_swmm5.Node_[n2].invertElev);
        } else {
            h1 = (F_swmm5.Node_[n1].newDepth + F_swmm5.Node_[n1].invertElev);
            h2 = F_swmm5.Node_[n1].invertElev;
        }
        dir = ((R.dgt(h1, h2)) ? 1.0d : (-1.0d));
        if ((dir < 0.0d)) {
            head = h1;
            h1 = h2;
            h2 = head;
        }
        hcrest = (F_swmm5.Node_[n1].invertElev + F_swmm5.Link[j].offset1);
        hcrown = (hcrest + F_swmm5.Link[j].xsect.yFull);
        if ((F_swmm5.Weir[k].type == ROADWAY_WEIR)) {
            return F_roadway.roadway_getInflow(j, dir, hcrest, h1, h2);
        }
        hcrest += (((1.0d - F_swmm5.Link[j].setting)) * F_swmm5.Link[j].xsect.yFull);
        head = (h1 - hcrest);
        F_swmm5.Link[j].@dqdh = 0.0d;
        if ((((head <= 0.0001d) || (R.dge(hcrest, hcrown))) || (link_setFlapGate(j, n1, n2, dir) != 0))) {
            F_swmm5.Link[j].@newDepth = 0.0d;
            F_swmm5.Link[j].@flowClass = DRY;
            return 0.0d;
        }
        F_swmm5.Link[j].@flowClass = SUBCRITICAL;
        if (R.dgt(hcrest, h2)) {
            if ((dir == 1.0d)) {
                F_swmm5.Link[j].@flowClass = DN_CRITICAL;
            } else {
                F_swmm5.Link[j].@flowClass = UP_CRITICAL;
            }
        }
        y = (F_swmm5.Link[j].xsect.yFull - ((hcrown - (((((h1) <= (hcrown))) ? (h1) : (hcrown))))));
        F_swmm5.Weir[k].@surfArea = (F_xsect.xsect_getWofY(F_swmm5.Link[j].xsect, y) * F_swmm5.Weir[k].length);
        if (R.dge(h1, hcrown)) {
            if ((F_swmm5.Weir[k].canSurcharge != 0)) {
                y = (((hcrest + hcrown)) / 2.0d);
                if ((h2 < y)) {
                    head = (h1 - y);
                } else {
                    head = (h1 - h2);
                }
                y = (hcrown - hcrest);
                q1.@v = weir_getOrificeFlow(j, head, y, F_swmm5.Weir[k].cSurcharge);
                F_swmm5.Link[j].@newDepth = y;
                return (dir * q1.v);
            } else {
                head = (hcrown - hcrest);
            }
        }
        weir_getFlow(j, k, head, dir, F_swmm5.Link[j].hasFlapGate, q1, q2);
        if (R.dgt(h2, hcrest)) {
            ratio = (((h2 - hcrest)) / ((h1 - hcrest)));
            q1.@v = (q1.v * (Glibm.pow(((1.0d - Glibm.pow(ratio, weirPower[F_swmm5.Weir[k].type]))), 0.385d)));
            if (R.dgt(q2.v, 0.0d)) {
                q2.@v = (q2.v * (Glibm.pow(((1.0d - Glibm.pow(ratio, weirPower[VNOTCH_WEIR]))), 0.385d)));
            }
        }
        F_swmm5.Link[j].@newDepth = (((((((h1 - hcrest))) <= (F_swmm5.Link[j].xsect.yFull))) ? (((h1 - hcrest))) : (F_swmm5.Link[j].xsect.yFull)));
        return (dir * ((q1.v + q2.v)));
    }

    // link.c:2321
    static void weir_getFlow(int j, int k, double head, double dir, int hasFlapGate, DRef q1, DRef q2) {
        double length = 0.0d;
        double h = 0.0d;
        double y = 0.0d;
        double hLoss = 0.0d;
        double area = 0.0d;
        double veloc = 0.0d;
        int wType = 0;
        int cdCurve = F_swmm5.Weir[k].cdCurve;
        double cDisch1 = F_swmm5.Weir[k].cDisch1;
        q1.set(0.0d);
        q2.set(0.0d);
        F_swmm5.Link[j].@dqdh = 0.0d;
        if ((head <= 0.0d)) {
            return;
        }
        length = (F_swmm5.Link[j].xsect.wMax * F_swmm5.UCF(LENGTH));
        h = (head * F_swmm5.UCF(LENGTH));
        if ((cdCurve >= 0)) {
            cDisch1 = F_table.table_lookup(F_swmm5.Curve[cdCurve], h);
        }
        wType = F_swmm5.Weir[k].type;
        if (((wType == VNOTCH_WEIR) && (F_swmm5.Link[j].setting < 1.0d))) {
            wType = TRAPEZOIDAL_WEIR;
        }
        int __sw1 = wType;
        if (__sw1 == TRANSVERSE_WEIR) {
            length -= ((0.1d * F_swmm5.Weir[k].endCon) * h);
            length = (((R.dge((length), (0.0d))) ? (length) : (0.0d)));
            q1.set(((cDisch1 * length) * Glibm.pow(h, 1.5d)));
        } else if (__sw1 == SIDEFLOW_WEIR) {
            length -= ((0.1d * F_swmm5.Weir[k].endCon) * h);
            length = (((R.dge((length), (0.0d))) ? (length) : (0.0d)));
            if ((dir < 0.0d)) {
                q1.set(((cDisch1 * length) * Glibm.pow(h, 1.5d)));
            } else {
                q1.set(((cDisch1 * Glibm.pow(length, 0.83d)) * Glibm.pow(h, 1.67d)));
            }
        } else if (__sw1 == VNOTCH_WEIR) {
            q1.set(((cDisch1 * F_swmm5.Weir[k].slope) * Glibm.pow(h, 2.5d)));
        } else if (__sw1 == TRAPEZOIDAL_WEIR) {
            y = (((1.0d - F_swmm5.Link[j].setting)) * F_swmm5.Link[j].xsect.yFull);
            length = (F_xsect.xsect_getWofY(F_swmm5.Link[j].xsect, y) * F_swmm5.UCF(LENGTH));
            q1.set(((cDisch1 * length) * Glibm.pow(h, 1.5d)));
            q2.set(((F_swmm5.Weir[k].cDisch2 * F_swmm5.Weir[k].slope) * Glibm.pow(h, 2.5d)));
        }
        if ((F_swmm5.UnitSystem == SI)) {
            q1.set((q1.get() / 0.028317d));
            q2.set((q2.get() / 0.028317d));
        }
        if ((hasFlapGate != 0)) {
            area = weir_getOpenArea(j, head);
            if (R.dgt(area, 1e-06d)) {
                veloc = (((q1.get() + q2.get())) / area);
                hLoss = (((((4.0d / 32.2d)) * veloc) * veloc) * Glibm.exp((((-1.15d) * veloc) / Math.sqrt(head))));
                head = (head - hLoss);
                if ((head < 0.0d)) {
                    head = 0.0d;
                }
                weir_getFlow(j, k, head, dir, 0, q1, q2);
            }
        }
        F_swmm5.Link[j].@dqdh = weir_getdqdh(k, dir, head, q1.get(), q2.get());
    }

    // link.c:2432
    static double weir_getOrificeFlow(int j, double head, double y, double cOrif) {
        double a = 0.0d;
        double q = 0.0d;
        double v = 0.0d;
        double hloss = 0.0d;
        q = (cOrif * Math.sqrt(head));
        if ((F_swmm5.Link[j].hasFlapGate != 0)) {
            a = weir_getOpenArea(j, y);
            if (R.dgt(a, 0.0d)) {
                v = (q / a);
                hloss = (((((4.0d / 32.2d)) * v) * v) * Glibm.exp((((-1.15d) * v) / Math.sqrt(y))));
                head -= hloss;
                head = (((R.dge((head), (0.0d))) ? (head) : (0.0d)));
                q = (cOrif * Math.sqrt(head));
            }
        }
        if (R.dgt(head, 0.0d)) {
            F_swmm5.Link[j].@dqdh = (q / ((2.0d * head)));
        } else {
            F_swmm5.Link[j].@dqdh = 0.0d;
        }
        return q;
    }

    // link.c:2467
    static double weir_getOpenArea(int j, double y) {
        double z = 0.0d;
        double zy = 0.0d;
        z = (((1.0d - F_swmm5.Link[j].setting)) * F_swmm5.Link[j].xsect.yFull);
        zy = (z + y);
        zy = (((((zy) <= (F_swmm5.Link[j].xsect.yFull))) ? (zy) : (F_swmm5.Link[j].xsect.yFull)));
        return (F_xsect.xsect_getAofY(F_swmm5.Link[j].xsect, zy) - F_xsect.xsect_getAofY(F_swmm5.Link[j].xsect, z));
    }

    // link.c:2492
    static double weir_getdqdh(int k, double dir, double h, double q1, double q2) {
        double q1h = 0.0d;
        double q2h = 0.0d;
        if ((Math.abs(h) < 0.0001d)) {
            return 0.0d;
        }
        q1h = Math.abs((q1 / h));
        q2h = Math.abs((q2 / h));
        int __sw1 = F_swmm5.Weir[k].type;
        if (__sw1 == TRANSVERSE_WEIR) {
            return (1.5d * q1h);
        } else if (__sw1 == SIDEFLOW_WEIR) {
            if ((dir < 0.0d)) {
                return (1.5d * q1h);
            } else {
                return (1.67d * q1h);
            }
        } else if (__sw1 == VNOTCH_WEIR) {
            if ((q2h == 0.0d)) {
                return (2.5d * q1h);
            } else {
                return ((1.5d * q1h) + (2.5d * q2h));
            }
        } else if (__sw1 == TRAPEZOIDAL_WEIR) {
            return ((1.5d * q1h) + (2.5d * q2h));
        }
        return 0.0d;
    }

    // link.c:2524
    static int outlet_readParams(int j, int k, CPtr[] tok, int ntoks) {
        int i = 0;
        int m = 0;
        int n = 0;
        int n1 = 0;
        int n2 = 0;
        double[] x = new double[6];
        CPtr id = null;
        CPtr s = null;
        if ((ntoks < 6)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        id = F_project.project_findID(LINK, tok[0]);
        if ((id == null)) {
            return F_error.error_setInpError(ERR_NAME, tok[0]);
        }
        n1 = F_project.project_findObject(NODE, tok[1]);
        if ((n1 < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[1]);
        }
        n2 = F_project.project_findObject(NODE, tok[2]);
        if ((n2 < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[2]);
        }
        if (((F_swmm5.LinkOffsets == ELEV_OFFSET) && (tok[3].g(0) == 42))) {
            x[0] = (-10000000000.0d);
        } else {
            if (!(F_input.getDouble(tok[3], new DElem(x, 0)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[3]);
            }
            if (((F_swmm5.LinkOffsets == DEPTH_OFFSET) && (x[0] < 0.0d))) {
                x[0] = 0.0d;
            }
        }
        m = F_input.findmatch(tok[4], F_keywords.RelationWords);
        if ((m < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[4]);
        }
        x[1] = 0.0d;
        x[2] = 0.0d;
        x[3] = (-1.0d);
        x[4] = 0.0d;
        x[5] = NODE_DEPTH;
        s = R.strtok(tok[4], L313);
        s = R.strtok(null, L313);
        if ((F_swmm5.strcomp(s, L314) != 0)) {
            x[5] = NODE_HEAD;
        }
        if ((m == FUNCTIONAL)) {
            if ((ntoks < 7)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            if (!(F_input.getDouble(tok[5], new DElem(x, 1)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[5]);
            }
            if (!(F_input.getDouble(tok[6], new DElem(x, 2)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[6]);
            }
            n = 7;
        } else {
            i = F_project.project_findObject(CURVE, tok[5]);
            if ((i < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[5]);
            }
            x[3] = i;
            n = 6;
        }
        if ((ntoks > n)) {
            i = F_input.findmatch(tok[n], F_keywords.NoYesWords);
            if ((i < 0)) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[n]);
            }
            x[4] = i;
        }
        F_swmm5.Link[j].@ID = id;
        link_setParams(j, OUTLET, n1, n2, k, x);
        return 0;
    }

    // link.c:2608
    static double outlet_getInflow(int j) {
        int k = 0;
        int n1 = 0;
        int n2 = 0;
        double head = 0.0d;
        double hcrest = 0.0d;
        double h1 = 0.0d;
        double h2 = 0.0d;
        double y1 = 0.0d;
        double dir = 0.0d;
        n1 = F_swmm5.Link[j].node1;
        n2 = F_swmm5.Link[j].node2;
        k = F_swmm5.Link[j].subIndex;
        if ((F_swmm5.RouteModel == DW)) {
            h1 = (F_swmm5.Node_[n1].newDepth + F_swmm5.Node_[n1].invertElev);
            h2 = (F_swmm5.Node_[n2].newDepth + F_swmm5.Node_[n2].invertElev);
        } else {
            h1 = (F_swmm5.Node_[n1].newDepth + F_swmm5.Node_[n1].invertElev);
            h2 = F_swmm5.Node_[n1].invertElev;
        }
        dir = ((R.dge(h1, h2)) ? 1.0d : (-1.0d));
        y1 = F_swmm5.Node_[n1].newDepth;
        if ((dir < 0.0d)) {
            y1 = h1;
            h1 = h2;
            h2 = y1;
            y1 = F_swmm5.Node_[n2].newDepth;
        }
        hcrest = (F_swmm5.Node_[n1].invertElev + F_swmm5.Link[j].offset1);
        if (((F_swmm5.Outlet[k].curveType == NODE_HEAD) && (F_swmm5.RouteModel == DW))) {
            head = (h1 - (((R.dge((h2), (hcrest))) ? (h2) : (hcrest))));
        } else {
            head = (h1 - hcrest);
        }
        if ((((head <= 0.0001d) || (y1 <= 0.0001d)) || (link_setFlapGate(j, n1, n2, dir) != 0))) {
            F_swmm5.Link[j].@newDepth = 0.0d;
            F_swmm5.Link[j].@flowClass = DRY;
            return 0.0d;
        }
        F_swmm5.Link[j].@newDepth = head;
        F_swmm5.Link[j].@flowClass = SUBCRITICAL;
        return ((dir * F_swmm5.Link[j].setting) * outlet_getFlow(k, head));
    }

    // link.c:2672
    static double outlet_getFlow(int k, double head) {
        int m = 0;
        double h = 0.0d;
        h = (head * F_swmm5.UCF(LENGTH));
        m = F_swmm5.Outlet[k].qCurve;
        if ((m >= 0)) {
            return (F_table.table_lookup(F_swmm5.Curve[m], h) / F_swmm5.UCF(FLOW));
        } else {
            return ((F_swmm5.Outlet[k].qCoeff * Glibm.pow(h, F_swmm5.Outlet[k].qExpon)) / F_swmm5.UCF(FLOW));
        }
        throw new IllegalStateException('outlet_getFlow: no return');
    }

}
