// Generated from EPA SWMM 5.2.4 (output.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_output {
    public static LBox IDStartPos = new LBox(0L);
    public static LBox InputStartPos = new LBox(0L);
    public static LBox OutputStartPos = new LBox(0L);
    public static long BytesPerPeriod = 0L;
    public static int NumSubcatchVars = 0;
    public static int NumNodeVars = 0;
    public static int NumLinkVars = 0;
    public static int NumSubcatch = 0;
    public static int NumNodes = 0;
    public static int NumLinks = 0;
    public static int NumPolluts = 0;
    public static float[] SysResults = new float[15];
    public static TAvgResults[] AvgLinkResults = null;
    public static TAvgResults[] AvgNodeResults = null;
    public static int output_Nsteps = 0;
    public static float[] SubcatchResults = null;
    public static float[] NodeResults = null;
    public static float[] LinkResults = null;

    // output.c:121
    static int output_open() {
        int j = 0;
        int m = 0;
        IBox k = new IBox(0);
        float x = 0.0f;
        DBox z = new DBox(0.0d);
        long numResults = 0L;
        output_openOutFile();
        if ((F_swmm5.ErrorCode != 0)) {
            return F_swmm5.ErrorCode;
        }
        if ((F_swmm5.IgnoreQuality != 0)) {
            NumPolluts = 0;
        } else {
            NumPolluts = F_swmm5.Nobjects[POLLUT];
        }
        NumSubcatchVars = ((9 - 1) + NumPolluts);
        NumNodeVars = ((7 - 1) + NumPolluts);
        NumLinkVars = ((6 - 1) + NumPolluts);
        NumSubcatch = 0;
        NumNodes = 0;
        NumLinks = 0;
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            if ((F_swmm5.Subcatch[j].rptFlag != 0)) {
                NumSubcatch++;
            }
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            if ((F_swmm5.Node_[j].rptFlag != 0)) {
                NumNodes++;
            }
        }
        L_f3: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            if ((F_swmm5.Link[j].rptFlag != 0)) {
                NumLinks++;
            }
        }
        numResults = (((((NumSubcatch * NumSubcatchVars)) + ((NumNodes * NumNodeVars))) + ((NumLinks * NumLinkVars))) + 15);
        BytesPerPeriod = (8L + ((numResults * 4L)));
        F_swmm5.@Nperiods = 0;
        SubcatchResults = (null);
        NodeResults = (null);
        LinkResults = (null);
        SubcatchResults = new float[(int) (NumSubcatchVars)];
        NodeResults = new float[(int) (NumNodeVars)];
        LinkResults = new float[(int) (NumLinkVars)];
        if ((((!(SubcatchResults != null)) || (!(NodeResults != null))) || (!(LinkResults != null)))) {
            F_report.report_writeErrorMsg(ERR_MEMORY, L0);
            return F_swmm5.ErrorCode;
        }
        AvgNodeResults = (null);
        AvgLinkResults = (null);
        if (((F_swmm5.RptFlags.averages != 0) && (!(output_openAvgResults() != 0)))) {
            F_report.report_writeErrorMsg(ERR_MEMORY, L0);
            return F_swmm5.ErrorCode;
        }
        R.fseek(F_swmm5.Fout.file, 0, 0);
        k.@v = 516114522;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = 52004;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = F_swmm5.FlowUnits;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = NumSubcatch;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = NumNodes;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = NumLinks;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = NumPolluts;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        IDStartPos.@v = R.ftell(F_swmm5.Fout.file);
        L_f4: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            if ((F_swmm5.Subcatch[j].rptFlag != 0)) {
                output_saveID(F_swmm5.Subcatch[j].ID, F_swmm5.Fout.file);
            }
        }
        L_f5: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            if ((F_swmm5.Node_[j].rptFlag != 0)) {
                output_saveID(F_swmm5.Node_[j].ID, F_swmm5.Fout.file);
            }
        }
        L_f6: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            if ((F_swmm5.Link[j].rptFlag != 0)) {
                output_saveID(F_swmm5.Link[j].ID, F_swmm5.Fout.file);
            }
        }
        L_f7: for (j = 0; (j < NumPolluts); j++) {
            output_saveID(F_swmm5.Pollut[j].ID, F_swmm5.Fout.file);
        }
        L_f8: for (j = 0; (j < NumPolluts); j++) {
            k.@v = F_swmm5.Pollut[j].units;
            R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        }
        InputStartPos.@v = R.ftell(F_swmm5.Fout.file);
        k.@v = 1;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = INPUT_AREA;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        L_f9: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            if (!(F_swmm5.Subcatch[j].rptFlag != 0)) {
                continue L_f9;
            }
            SubcatchResults[0] = ((float) (((F_swmm5.Subcatch[j].area * F_swmm5.UCF(LANDAREA)))));
            R.fwrite(SubcatchResults, 0, (int) (1), F_swmm5.Fout.file);
        }
        k.@v = 3;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = INPUT_TYPE_CODE;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = INPUT_INVERT;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = INPUT_MAX_DEPTH;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        L_f10: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            if (!(F_swmm5.Node_[j].rptFlag != 0)) {
                continue L_f10;
            }
            k.@v = F_swmm5.Node_[j].type;
            NodeResults[0] = ((float) (((F_swmm5.Node_[j].invertElev * F_swmm5.UCF(LENGTH)))));
            NodeResults[1] = ((float) (((F_swmm5.Node_[j].fullDepth * F_swmm5.UCF(LENGTH)))));
            R.fwrite1(k.v, 4, F_swmm5.Fout.file);
            R.fwrite(NodeResults, 0, (int) (2), F_swmm5.Fout.file);
        }
        k.@v = 5;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = INPUT_TYPE_CODE;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = INPUT_OFFSET;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = INPUT_OFFSET;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = INPUT_MAX_DEPTH;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = INPUT_LENGTH;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        L_f11: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            if (!(F_swmm5.Link[j].rptFlag != 0)) {
                continue L_f11;
            }
            k.@v = F_swmm5.Link[j].type;
            if ((k.v == PUMP)) {
                L_f12: for (m = 0; (m < 4); m++) {
                    LinkResults[m] = 0.0f;
                }
            } else {
                LinkResults[0] = ((float) (((F_swmm5.Link[j].offset1 * F_swmm5.UCF(LENGTH)))));
                LinkResults[1] = ((float) (((F_swmm5.Link[j].offset2 * F_swmm5.UCF(LENGTH)))));
                if ((F_swmm5.Link[j].direction < 0)) {
                    x = LinkResults[0];
                    LinkResults[0] = LinkResults[1];
                    LinkResults[1] = x;
                }
                if ((k.v == OUTLET)) {
                    LinkResults[2] = 0.0f;
                } else {
                    LinkResults[2] = ((float) (((F_swmm5.Link[j].xsect.yFull * F_swmm5.UCF(LENGTH)))));
                }
                if ((k.v == CONDUIT)) {
                    m = F_swmm5.Link[j].subIndex;
                    LinkResults[3] = ((float) (((F_swmm5.Conduit[m].length * F_swmm5.UCF(LENGTH)))));
                } else {
                    LinkResults[3] = 0.0f;
                }
            }
            R.fwrite1(k.v, 4, F_swmm5.Fout.file);
            R.fwrite(LinkResults, 0, (int) (4), F_swmm5.Fout.file);
        }
        k.@v = NumSubcatchVars;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = SUBCATCH_RAINFALL;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = SUBCATCH_SNOWDEPTH;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = SUBCATCH_EVAP;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = SUBCATCH_INFIL;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = SUBCATCH_RUNOFF;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = SUBCATCH_GW_FLOW;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = SUBCATCH_GW_ELEV;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = SUBCATCH_SOIL_MOIST;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        L_f13: for (j = 0; (j < NumPolluts); j++) {
            k.@v = (SUBCATCH_WASHOFF + j);
            R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        }
        k.@v = NumNodeVars;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = NODE_DEPTH;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = NODE_HEAD;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = NODE_VOLUME;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = NODE_LATFLOW;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = NODE_INFLOW;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = NODE_OVERFLOW;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        L_f14: for (j = 0; (j < NumPolluts); j++) {
            k.@v = (NODE_QUAL + j);
            R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        }
        k.@v = NumLinkVars;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = LINK_FLOW;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = LINK_DEPTH;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = LINK_VELOCITY;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = LINK_VOLUME;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = LINK_CAPACITY;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        L_f15: for (j = 0; (j < NumPolluts); j++) {
            k.@v = (LINK_QUAL + j);
            R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        }
        k.@v = 15;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        L_f16: for (k.@v = 0; (k.v < 15); k.@v = k.v + 1) {
            R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        }
        z.@v = (((double) (F_swmm5.ReportStep)) / 86400.0d);
        if (R.dgt((F_swmm5.StartDateTime + z.v), F_swmm5.ReportStart)) {
            z.@v = F_swmm5.StartDateTime;
        } else {
            z.@v = (Math.floor((((F_swmm5.ReportStart - F_swmm5.StartDateTime)) / z.v)) - 1.0d);
            z.@v = (F_swmm5.StartDateTime + ((z.v * ((double) (F_swmm5.ReportStep))) / 86400.0d));
        }
        R.fwrite1(z.v, 8, F_swmm5.Fout.file);
        k.@v = F_swmm5.ReportStep;
        if ((R.fwrite1(k.v, 4, F_swmm5.Fout.file) < 1)) {
            F_report.report_writeErrorMsg(ERR_OUT_WRITE, L0);
            return F_swmm5.ErrorCode;
        }
        OutputStartPos.@v = R.ftell(F_swmm5.Fout.file);
        return F_swmm5.ErrorCode;
    }

    // output.c:427
    static void output_openOutFile() {
        if ((F_swmm5.Fout.file != null)) {
            R.fclose(F_swmm5.Fout.file);
        } else if ((R.strlen(F_swmm5.Fout.name) != 0)) {
            F_swmm5.Fout.@mode = SAVE_FILE;
        } else {
            F_swmm5.Fout.@mode = SCRATCH_FILE;
            Overrides.getTempFileName(F_swmm5.Fout.name);
        }
        if ((((F_swmm5.Fout.@file = R.fopen(F_swmm5.Fout.name, L166))) == null)) {
            F_swmm5.writecon(L318);
            F_swmm5.@ErrorCode = ERR_OUT_FILE;
        }
    }

    // output.c:457
    static void output_saveResults(double reportTime) {
        int i = 0;
        double reportDate = F_swmm5.getDateTime(reportTime);
        DBox date = new DBox(0.0d);
        if ((reportDate < F_swmm5.ReportStart)) {
            return;
        }
        L_f1: for (i = 0; (i < 15); i++) {
            SysResults[i] = 0.0f;
        }
        date.@v = reportDate;
        R.fwrite1(date.v, 8, F_swmm5.Fout.file);
        if ((F_swmm5.Nobjects[SUBCATCH] > 0)) {
            output_saveSubcatchResults(reportTime, F_swmm5.Fout.file);
        }
        if ((F_swmm5.RptFlags.averages != 0)) {
            output_saveAvgResults(F_swmm5.Fout.file);
        } else {
            if ((F_swmm5.Nobjects[NODE] > 0)) {
                output_saveNodeResults(reportTime, F_swmm5.Fout.file);
            }
            if ((F_swmm5.Nobjects[LINK] > 0)) {
                output_saveLinkResults(reportTime, F_swmm5.Fout.file);
            }
        }
        SysResults[SYS_FLOODING] = ((float) (((F_massbal.StepFlowTotals.flooding * F_swmm5.UCF(FLOW)))));
        SysResults[SYS_OUTFLOW] = ((float) (((F_massbal.StepFlowTotals.outflow * F_swmm5.UCF(FLOW)))));
        SysResults[SYS_DWFLOW] = ((float) (((F_massbal.StepFlowTotals.dwInflow * F_swmm5.UCF(FLOW)))));
        SysResults[SYS_GWFLOW] = ((float) (((F_massbal.StepFlowTotals.gwInflow * F_swmm5.UCF(FLOW)))));
        SysResults[SYS_IIFLOW] = ((float) (((F_massbal.StepFlowTotals.iiInflow * F_swmm5.UCF(FLOW)))));
        SysResults[SYS_EXFLOW] = ((float) (((F_massbal.StepFlowTotals.exInflow * F_swmm5.UCF(FLOW)))));
        SysResults[SYS_INFLOW] = ((float) (((float) (((float) (((float) (SysResults[SYS_RUNOFF] + SysResults[SYS_DWFLOW])) + SysResults[SYS_GWFLOW])) + SysResults[SYS_IIFLOW])) + SysResults[SYS_EXFLOW]));
        R.fwrite(SysResults, 0, (int) (15), F_swmm5.Fout.file);
        if (((F_swmm5.Foutflows.mode == SAVE_FILE) && (!(F_swmm5.IgnoreRouting != 0)))) {
            F_iface.iface_saveOutletResults(reportDate, F_swmm5.Foutflows.file);
        }
        F_swmm5.@Nperiods = F_swmm5.Nperiods + 1L;
    }

    // output.c:515
    static void output_end() {
        IBox k = new IBox(0);
        R.fwrite1(IDStartPos.v, 4, F_swmm5.Fout.file);
        R.fwrite1(InputStartPos.v, 4, F_swmm5.Fout.file);
        R.fwrite1(OutputStartPos.v, 4, F_swmm5.Fout.file);
        k.@v = (int) (F_swmm5.Nperiods);
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = F_swmm5.ErrorCode;
        R.fwrite1(k.v, 4, F_swmm5.Fout.file);
        k.@v = 516114522;
        if ((R.fwrite1(k.v, 4, F_swmm5.Fout.file) < 1)) {
            F_report.report_writeErrorMsg(ERR_OUT_WRITE, L0);
        }
    }

    // output.c:539
    static void output_close() {
        if ((SubcatchResults != null)) {
            R.free(SubcatchResults);
            SubcatchResults = (null);
        }
        if ((NodeResults != null)) {
            R.free(NodeResults);
            NodeResults = (null);
        }
        if ((LinkResults != null)) {
            R.free(LinkResults);
            LinkResults = (null);
        }
        output_closeAvgResults();
    }

    // output.c:554
    static void output_saveID(CPtr id, CFile file) {
        IBox n = new IBox((int) (R.strlen(id)));
        R.fwrite1(n.v, 4, file);
        R.fwrite(id, 0, (int) (n.v), file);
    }

    // output.c:569
    static void output_saveSubcatchResults(double reportTime, CFile file) {
        int j = 0;
        double f = 0.0d;
        double area = 0.0d;
        float totalArea = 0.0f;
        double reportDate = F_swmm5.getDateTime(reportTime);
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[GAGE]); j++) {
            F_gage.gage_setReportRainfall(j, reportDate);
        }
        f = (((reportTime - F_swmm5.OldRunoffTime)) / ((F_swmm5.NewRunoffTime - F_swmm5.OldRunoffTime)));
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            F_subcatch.subcatch_getResults(j, f, SubcatchResults);
            if ((F_swmm5.Subcatch[j].rptFlag != 0)) {
                R.fwrite(SubcatchResults, 0, (int) (NumSubcatchVars), file);
            }
            area = (F_swmm5.Subcatch[j].area * F_swmm5.UCF(LANDAREA));
            totalArea = ((float) (totalArea + ((float) (area))));
            SysResults[SYS_RAINFALL] = ((float) (SysResults[SYS_RAINFALL] + ((float) (((SubcatchResults[SUBCATCH_RAINFALL] * area))))));
            SysResults[SYS_SNOWDEPTH] = ((float) (SysResults[SYS_SNOWDEPTH] + ((float) (((SubcatchResults[SUBCATCH_SNOWDEPTH] * area))))));
            SysResults[SYS_EVAP] = ((float) (SysResults[SYS_EVAP] + ((float) (((SubcatchResults[SUBCATCH_EVAP] * area))))));
            if ((F_swmm5.Subcatch[j].groundwater != null)) {
                SysResults[SYS_EVAP] = ((float) (SysResults[SYS_EVAP] + ((float) ((((F_swmm5.Subcatch[j].groundwater.evapLoss * F_swmm5.UCF(EVAPRATE)) * area))))));
            }
            SysResults[SYS_INFIL] = ((float) (SysResults[SYS_INFIL] + ((float) (((SubcatchResults[SUBCATCH_INFIL] * area))))));
            SysResults[SYS_RUNOFF] = ((float) (SysResults[SYS_RUNOFF] + SubcatchResults[SUBCATCH_RUNOFF]));
        }
        if (R.dgt(totalArea, 0.0d)) {
            SysResults[SYS_EVAP] = ((float) (SysResults[SYS_EVAP] / totalArea));
            SysResults[SYS_RAINFALL] = ((float) (SysResults[SYS_RAINFALL] / totalArea));
            SysResults[SYS_SNOWDEPTH] = ((float) (SysResults[SYS_SNOWDEPTH] / totalArea));
            SysResults[SYS_INFIL] = ((float) (SysResults[SYS_INFIL] / totalArea));
        }
        if ((F_swmm5.UnitSystem == SI)) {
            f = (((5.0d / 9.0d)) * ((F_swmm5.Temp.ta - 32.0d)));
        } else {
            f = F_swmm5.Temp.ta;
        }
        SysResults[SYS_TEMPERATURE] = ((float) (f));
        f = (F_swmm5.Evap.rate * F_swmm5.UCF(EVAPRATE));
        SysResults[SYS_PET] = ((float) (f));
    }

    // output.c:636
    static void output_saveNodeResults(double reportTime, CFile file) {
        int j = 0;
        double f = (((reportTime - F_swmm5.OldRoutingTime)) / ((F_swmm5.NewRoutingTime - F_swmm5.OldRoutingTime)));
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            F_node.node_getResults(j, f, NodeResults);
            if ((F_swmm5.Node_[j].rptFlag != 0)) {
                R.fwrite(NodeResults, 0, (int) (NumNodeVars), file);
            }
            F_stats.stats_updateMaxNodeDepth(j, NodeResults[NODE_DEPTH]);
            SysResults[SYS_STORAGE] = ((float) (SysResults[SYS_STORAGE] + NodeResults[NODE_VOLUME]));
        }
    }

    // output.c:666
    static void output_saveLinkResults(double reportTime, CFile file) {
        int j = 0;
        double f = 0.0d;
        double z = 0.0d;
        f = (((reportTime - F_swmm5.OldRoutingTime)) / ((F_swmm5.NewRoutingTime - F_swmm5.OldRoutingTime)));
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            if ((F_swmm5.Link[j].rptFlag != 0)) {
                F_link.link_getResults(j, f, LinkResults);
                R.fwrite(LinkResults, 0, (int) (NumLinkVars), file);
            }
            z = ((((((1.0d - f)) * F_swmm5.Link[j].oldVolume) + (f * F_swmm5.Link[j].newVolume))) * F_swmm5.UCF(VOLUME));
            SysResults[SYS_STORAGE] = ((float) (SysResults[SYS_STORAGE] + ((float) (z))));
        }
    }

    // output.c:699
    static void output_readDateTime(long period, DRef days) {
        long p = period;
        long bytePos = (OutputStartPos.v + (((p - 1)) * BytesPerPeriod));
        R.fseek(F_swmm5.Fout.file, bytePos, 0);
        days.set((-693594));
        R.fread1(days, 8, F_swmm5.Fout.file);
    }

    // output.c:716
    static void output_readSubcatchResults(long period, int index) {
        long offset = (index * NumSubcatchVars);
        long p = period;
        long bytePos = (((OutputStartPos.v + (((p - 1)) * BytesPerPeriod)) + 8L) + (offset * 4L));
        R.fseek(F_swmm5.Fout.file, bytePos, 0);
        R.fread(SubcatchResults, 0, (int) (NumSubcatchVars), F_swmm5.Fout.file);
    }

    // output.c:735
    static void output_readNodeResults(long period, int index) {
        long offset = ((NumSubcatch * NumSubcatchVars) + (index * NumNodeVars));
        long p = period;
        long bytePos = (((OutputStartPos.v + (((p - 1)) * BytesPerPeriod)) + 8L) + (offset * 4L));
        R.fseek(F_swmm5.Fout.file, bytePos, 0);
        R.fread(NodeResults, 0, (int) (NumNodeVars), F_swmm5.Fout.file);
    }

    // output.c:753
    static void output_readLinkResults(long period, int index) {
        long offset = ((((NumSubcatch * NumSubcatchVars) + (NumNodes * NumNodeVars)) + (index * NumLinkVars)));
        long p = period;
        long bytePos = (((OutputStartPos.v + (((p - 1)) * BytesPerPeriod)) + 8L) + (offset * 4L));
        R.fseek(F_swmm5.Fout.file, bytePos, 0);
        R.fread(LinkResults, 0, (int) (NumLinkVars), F_swmm5.Fout.file);
        R.fread(SysResults, 0, (int) (15), F_swmm5.Fout.file);
    }

    // output.c:774
    static int output_openAvgResults() {
        int i = 0;
        AvgNodeResults = R.fill(new TAvgResults[(int) (NumNodes)], (() -> new TAvgResults()));
        if ((AvgNodeResults == null)) {
            return 0;
        }
        L_f1: for (i = 0; (i < NumNodes); i++) {
            AvgNodeResults[i].@xAvg = (null);
        }
        AvgLinkResults = R.fill(new TAvgResults[(int) (NumLinks)], (() -> new TAvgResults()));
        if ((AvgLinkResults == null)) {
            output_closeAvgResults();
            return 0;
        }
        L_f2: for (i = 0; (i < NumLinks); i++) {
            AvgLinkResults[i].@xAvg = (null);
        }
        L_f3: for (i = 0; (i < NumNodes); i++) {
            AvgNodeResults[i].@xAvg = new float[(int) (NumNodeVars)];
            if ((AvgNodeResults[i].xAvg == null)) {
                output_closeAvgResults();
                return 0;
            }
        }
        L_f4: for (i = 0; (i < NumLinks); i++) {
            AvgLinkResults[i].@xAvg = new float[(int) (NumLinkVars)];
            if ((AvgLinkResults[i].xAvg == null)) {
                output_closeAvgResults();
                return 0;
            }
        }
        return 1;
    }

    // output.c:820
    static void output_closeAvgResults() {
        int i = 0;
        if ((AvgNodeResults != null)) {
            L_f1: for (i = 0; (i < NumNodes); i++) {
                if ((AvgNodeResults[i].xAvg != null)) {
                    R.free(AvgNodeResults[i].xAvg);
                    AvgNodeResults[i].@xAvg = (null);
                }
            }
            if ((AvgNodeResults != null)) {
                R.free(AvgNodeResults);
                AvgNodeResults = (null);
            }
        }
        if ((AvgLinkResults != null)) {
            L_f2: for (i = 0; (i < NumLinks); i++) {
                if ((AvgLinkResults[i].xAvg != null)) {
                    R.free(AvgLinkResults[i].xAvg);
                    AvgLinkResults[i].@xAvg = (null);
                }
            }
            if ((AvgLinkResults != null)) {
                R.free(AvgLinkResults);
                AvgLinkResults = (null);
            }
        }
    }

    // output.c:839
    static void output_initAvgResults() {
        int i = 0;
        int j = 0;
        output_Nsteps = 0;
        L_f1: for (i = 0; (i < NumNodes); i++) {
            L_f2: for (j = 0; (j < NumNodeVars); j++) {
                AvgNodeResults[i].xAvg[j] = (float) (0.0d);
            }
        }
        L_f3: for (i = 0; (i < NumLinks); i++) {
            L_f4: for (j = 0; (j < NumLinkVars); j++) {
                AvgLinkResults[i].xAvg[j] = (float) (0.0d);
            }
        }
    }

    // output.c:857
    static void output_updateAvgResults() {
        int i = 0;
        int j = 0;
        int k = 0;
        int sign = 0;
        k = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if (!(F_swmm5.Node_[i].rptFlag != 0)) {
                continue L_f1;
            }
            F_node.node_getResults(i, 1.0d, NodeResults);
            L_f2: for (j = 0; (j < NumNodeVars); j++) {
                AvgNodeResults[k].xAvg[j] = ((float) (AvgNodeResults[k].xAvg[j] + NodeResults[j]));
            }
            k++;
        }
        k = 0;
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if (!(F_swmm5.Link[i].rptFlag != 0)) {
                continue L_f3;
            }
            F_link.link_getResults(i, 1.0d, LinkResults);
            sign = (((((LinkResults[LINK_FLOW]) < (float) (0))) ? ((-1)) : (1)));
            L_f4: for (j = 0; (j < NumLinkVars); j++) {
                if ((j == LINK_CAPACITY)) {
                    if ((F_swmm5.Link[i].type == CONDUIT)) {
                        AvgLinkResults[k].xAvg[j] = ((float) (AvgLinkResults[k].xAvg[j] + LinkResults[j]));
                    } else {
                        AvgLinkResults[k].xAvg[j] = ((float) (LinkResults[j] * (float) (((output_Nsteps + 1)))));
                    }
                } else {
                    AvgLinkResults[k].xAvg[j] = ((float) (AvgLinkResults[k].xAvg[j] + LinkResults[j]));
                }
            }
            k++;
        }
        output_Nsteps++;
    }

    // output.c:911
    static void output_saveAvgResults(CFile file) {
        int i = 0;
        int j = 0;
        L_f1: for (i = 0; (i < NumNodes); i++) {
            L_f2: for (j = 0; (j < NumNodeVars); j++) {
                NodeResults[j] = ((float) (AvgNodeResults[i].xAvg[j] / (float) (output_Nsteps)));
            }
            R.fwrite(NodeResults, 0, (int) (NumNodeVars), file);
        }
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            F_stats.stats_updateMaxNodeDepth(i, (F_swmm5.Node_[i].newDepth * F_swmm5.UCF(LENGTH)));
            SysResults[SYS_STORAGE] = ((float) (SysResults[SYS_STORAGE] + ((float) (((F_swmm5.Node_[i].newVolume * F_swmm5.UCF(VOLUME)))))));
        }
        L_f4: for (i = 0; (i < NumLinks); i++) {
            L_f5: for (j = 0; (j < NumLinkVars); j++) {
                LinkResults[j] = ((float) (AvgLinkResults[i].xAvg[j] / (float) (output_Nsteps)));
            }
            R.fwrite(LinkResults, 0, (int) (NumLinkVars), file);
        }
        L_f6: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            SysResults[SYS_STORAGE] = ((float) (SysResults[SYS_STORAGE] + ((float) (((F_swmm5.Link[i].newVolume * F_swmm5.UCF(VOLUME)))))));
        }
        output_initAvgResults();
    }

}
