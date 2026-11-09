// Generated from EPA SWMM 5.2.4 (statsrpt.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_statsrpt {
    public static CPtr FlowFmt = R.buf(6);
    public static double Vcf = 0.0d;

    // statsrpt.c:84
    static void statsrpt_writeReport() {
        if (((F_swmm5.FlowUnits == MGD) || (F_swmm5.FlowUnits == CMS))) {
            Overrides.sstrncpy(FlowFmt, L534, 5);
        } else {
            Overrides.sstrncpy(FlowFmt, L535, 5);
        }
        if ((F_swmm5.UnitSystem == US)) {
            Vcf = (7.48d / 1.0e6d);
        } else {
            Vcf = (28.317d / 1.0e6d);
        }
        if ((F_swmm5.Nobjects[SUBCATCH] > 0)) {
            if ((((!(F_swmm5.IgnoreRainfall != 0)) || (((F_swmm5.Nobjects[SNOWMELT] > 0) && (!(F_swmm5.IgnoreSnowmelt != 0))))) || (((F_swmm5.Nobjects[AQUIFER] > 0) && (!(F_swmm5.IgnoreGwater != 0)))))) {
                writeSubcatchRunoff();
                F_lid.lid_writeWaterBalance();
                if (!(F_swmm5.IgnoreGwater != 0)) {
                    writeGroundwater();
                }
                if (((F_swmm5.Nobjects[POLLUT] > 0) && (!(F_swmm5.IgnoreQuality != 0)))) {
                    writeSubcatchLoads();
                }
            }
        }
        if (((F_swmm5.Nobjects[LINK] > 0) && (!(F_swmm5.IgnoreRouting != 0)))) {
            writeNodeDepths();
            writeNodeFlows();
            if ((F_swmm5.RouteModel == DW)) {
                writeNodeSurcharge();
            }
            writeNodeFlooding();
            writeStorageVolumes();
            writeOutfallLoads();
            F_inlet.inlet_writeStatsReport();
            writeLinkFlows();
            writeFlowClass();
            writeLinkSurcharge();
            writePumpFlows();
            if (((F_swmm5.Nobjects[POLLUT] > 0) && (!(F_swmm5.IgnoreQuality != 0)))) {
                writeLinkLoads();
            }
        }
    }

    // statsrpt.c:133
    static void writeSubcatchRunoff() {
        int j = 0;
        double a = 0.0d;
        double x = 0.0d;
        double r = 0.0d;
        if ((F_swmm5.Nobjects[SUBCATCH] == 0)) {
            return;
        }
        F_report.report_writeLine((L0));
        F_report.report_writeLine((L473));
        F_report.report_writeLine((L536));
        F_report.report_writeLine((L473));
        F_report.report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L537);
        if ((F_swmm5.UnitSystem == US)) {
            R.fprintf(F_swmm5.Frpt.file, L538, F_keywords.VolUnitsWords[F_swmm5.UnitSystem], F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L539, F_keywords.VolUnitsWords[F_swmm5.UnitSystem], F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        }
        R.fprintf(F_swmm5.Frpt.file, L540);
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            a = F_swmm5.Subcatch[j].area;
            if ((a == 0.0d)) {
                continue L_f1;
            }
            R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Subcatch[j].ID);
            x = (F_stats.SubcatchStats[j].precip * F_swmm5.UCF(RAINDEPTH));
            R.fprintf(F_swmm5.Frpt.file, L542, (x / a));
            x = (F_stats.SubcatchStats[j].runon * F_swmm5.UCF(RAINDEPTH));
            R.fprintf(F_swmm5.Frpt.file, L542, (x / a));
            x = (F_stats.SubcatchStats[j].evap * F_swmm5.UCF(RAINDEPTH));
            R.fprintf(F_swmm5.Frpt.file, L542, (x / a));
            x = (F_stats.SubcatchStats[j].infil * F_swmm5.UCF(RAINDEPTH));
            R.fprintf(F_swmm5.Frpt.file, L542, (x / a));
            x = (F_stats.SubcatchStats[j].impervRunoff * F_swmm5.UCF(RAINDEPTH));
            R.fprintf(F_swmm5.Frpt.file, L542, (x / a));
            x = (F_stats.SubcatchStats[j].pervRunoff * F_swmm5.UCF(RAINDEPTH));
            R.fprintf(F_swmm5.Frpt.file, L542, (x / a));
            x = (F_stats.SubcatchStats[j].runoff * F_swmm5.UCF(RAINDEPTH));
            R.fprintf(F_swmm5.Frpt.file, L542, (x / a));
            x = (F_stats.SubcatchStats[j].runoff * Vcf);
            R.fprintf(F_swmm5.Frpt.file, L543, x);
            x = (F_stats.SubcatchStats[j].maxFlow * F_swmm5.UCF(FLOW));
            R.fprintf(F_swmm5.Frpt.file, L544, x);
            r = (F_stats.SubcatchStats[j].precip + F_stats.SubcatchStats[j].runon);
            if (R.dgt(r, 0.0d)) {
                r = (F_stats.SubcatchStats[j].runoff / r);
            }
            R.fprintf(F_swmm5.Frpt.file, L545, r);
        }
        F_report.report_writeLine((L0));
    }

    // statsrpt.c:190
    static void writeGroundwater() {
        int i = 0;
        int j = 0;
        int count = 0;
        double totalSeconds = (F_swmm5.NewRunoffTime / 1000.0d);
        double[] x = new double[9];
        if ((F_swmm5.Nobjects[SUBCATCH] == 0)) {
            return;
        }
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            if ((F_swmm5.Subcatch[j].groundwater != null)) {
                count++;
            }
        }
        if ((count == 0)) {
            return;
        }
        F_report.report_writeLine((L0));
        F_report.report_writeLine((L188));
        F_report.report_writeLine((L546));
        F_report.report_writeLine((L188));
        F_report.report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L547);
        if ((F_swmm5.UnitSystem == US)) {
            R.fprintf(F_swmm5.Frpt.file, L548, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L549, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        }
        R.fprintf(F_swmm5.Frpt.file, L550);
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            if (((F_swmm5.Subcatch[j].area == 0.0d) || (F_swmm5.Subcatch[j].groundwater == null))) {
                continue L_f2;
            }
            R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Subcatch[j].ID);
            x[0] = (F_swmm5.Subcatch[j].groundwater.stats.infil * F_swmm5.UCF(RAINDEPTH));
            x[1] = (F_swmm5.Subcatch[j].groundwater.stats.evap * F_swmm5.UCF(RAINDEPTH));
            x[2] = (F_swmm5.Subcatch[j].groundwater.stats.deepFlow * F_swmm5.UCF(RAINDEPTH));
            x[3] = (F_swmm5.Subcatch[j].groundwater.stats.latFlow * F_swmm5.UCF(RAINDEPTH));
            x[4] = ((F_swmm5.Subcatch[j].groundwater.stats.maxFlow * F_swmm5.UCF(FLOW)) * F_swmm5.Subcatch[j].area);
            x[5] = (F_swmm5.Subcatch[j].groundwater.stats.avgUpperMoist / totalSeconds);
            x[6] = ((F_swmm5.Subcatch[j].groundwater.stats.avgWaterTable * F_swmm5.UCF(LENGTH)) / totalSeconds);
            x[7] = F_swmm5.Subcatch[j].groundwater.stats.finalUpperMoist;
            x[8] = (F_swmm5.Subcatch[j].groundwater.stats.finalWaterTable * F_swmm5.UCF(LENGTH));
            L_f3: for (i = 0; (i < 9); i++) {
                R.fprintf(F_swmm5.Frpt.file, L544, x[i]);
            }
        }
        F_report.report_writeLine((L0));
    }

    // statsrpt.c:245
    static void writeSubcatchLoads() {
        int i = 0;
        int j = 0;
        int p = 0;
        double x = 0.0d;
        double[] totals = null;
        CPtr units = R.buf(15);
        CPtr subcatchLine = R.bufFrom(L551, 21);
        CPtr pollutLine = R.bufFrom(L552, 15);
        totals = new double[(int) (F_swmm5.Nobjects[POLLUT])];
        if ((totals != null)) {
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L553));
            F_report.report_writeLine((L554));
            F_report.report_writeLine((L553));
            F_report.report_writeLine((L0));
            R.fprintf(F_swmm5.Frpt.file, L356, subcatchLine);
            L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                R.fprintf(F_swmm5.Frpt.file, L169, pollutLine);
            }
            R.fprintf(F_swmm5.Frpt.file, L555);
            L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                R.fprintf(F_swmm5.Frpt.file, L423, F_swmm5.Pollut[p].ID);
            }
            R.fprintf(F_swmm5.Frpt.file, L556);
            L_f3: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                i = F_swmm5.UnitSystem;
                if ((F_swmm5.Pollut[p].units == COUNT)) {
                    i = 2;
                }
                Overrides.sstrncpy(units, F_keywords.LoadUnitsWords[i], 14);
                R.fprintf(F_swmm5.Frpt.file, L423, units);
                totals[p] = 0.0d;
            }
            R.fprintf(F_swmm5.Frpt.file, L356, subcatchLine);
            L_f4: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                R.fprintf(F_swmm5.Frpt.file, L169, pollutLine);
            }
            L_f5: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
                R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Subcatch[j].ID);
                L_f6: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                    x = F_swmm5.Subcatch[j].totalLoad[p];
                    totals[p] += x;
                    if ((F_swmm5.Pollut[p].units == COUNT)) {
                        x = ((R.dgt((x), 0.0d) ? Glibm.log10((x)) : (x)));
                    }
                    R.fprintf(F_swmm5.Frpt.file, L427, x);
                }
            }
            R.fprintf(F_swmm5.Frpt.file, L356, subcatchLine);
            L_f7: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                R.fprintf(F_swmm5.Frpt.file, L169, pollutLine);
            }
            R.fprintf(F_swmm5.Frpt.file, L557);
            L_f8: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                x = totals[p];
                if ((F_swmm5.Pollut[p].units == COUNT)) {
                    x = ((R.dgt((x), 0.0d) ? Glibm.log10((x)) : (x)));
                }
                R.fprintf(F_swmm5.Frpt.file, L427, x);
            }
            R.free(totals);
            F_report.report_writeLine((L0));
        }
    }

    // statsrpt.c:310
    static void writeNodeDepths() {
        int j = 0;
        IBox days = new IBox(0);
        IBox hrs = new IBox(0);
        IBox mins = new IBox(0);
        if ((F_swmm5.Nobjects[LINK] == 0)) {
            return;
        }
        F_report.report_writeLine((L0));
        F_report.report_writeLine((L558));
        F_report.report_writeLine((L559));
        F_report.report_writeLine((L558));
        F_report.report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L560);
        if ((F_swmm5.UnitSystem == US)) {
            R.fprintf(F_swmm5.Frpt.file, L561);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L562);
        }
        R.fprintf(F_swmm5.Frpt.file, L563);
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Node_[j].ID);
            R.fprintf(F_swmm5.Frpt.file, L564, F_keywords.NodeTypeWords[F_swmm5.Node_[j].type]);
            F_swmm5.getElapsedTime(F_stats.NodeStats[j].maxDepthDate, days, hrs, mins);
            R.fprintf(F_swmm5.Frpt.file, L565, ((F_stats.NodeStats[j].avgDepth / F_swmm5.ReportStepCount) * F_swmm5.UCF(LENGTH)), (F_stats.NodeStats[j].maxDepth * F_swmm5.UCF(LENGTH)), (((F_stats.NodeStats[j].maxDepth + F_swmm5.Node_[j].invertElev)) * F_swmm5.UCF(LENGTH)), days.v, hrs.v, mins.v, F_stats.NodeStats[j].maxRptDepth);
        }
        F_report.report_writeLine((L0));
    }

    // statsrpt.c:353
    static void writeNodeFlows() {
        int j = 0;
        IBox days1 = new IBox(0);
        IBox hrs1 = new IBox(0);
        IBox mins1 = new IBox(0);
        F_report.report_writeLine((L0));
        F_report.report_writeLine((L188));
        F_report.report_writeLine((L566));
        F_report.report_writeLine((L188));
        F_report.report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L567, F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.VolUnitsWords[F_swmm5.UnitSystem], F_keywords.VolUnitsWords[F_swmm5.UnitSystem]);
        R.fprintf(F_swmm5.Frpt.file, L568);
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Node_[j].ID);
            R.fprintf(F_swmm5.Frpt.file, L569, F_keywords.NodeTypeWords[F_swmm5.Node_[j].type]);
            F_swmm5.getElapsedTime(F_stats.NodeStats[j].maxInflowDate, days1, hrs1, mins1);
            R.fprintf(F_swmm5.Frpt.file, FlowFmt, (F_stats.NodeStats[j].maxLatFlow * F_swmm5.UCF(FLOW)));
            R.fprintf(F_swmm5.Frpt.file, FlowFmt, (F_stats.NodeStats[j].maxInflow * F_swmm5.UCF(FLOW)));
            R.fprintf(F_swmm5.Frpt.file, L570, days1.v, hrs1.v, mins1.v);
            R.fprintf(F_swmm5.Frpt.file, L571, (F_stats.NodeStats[j].totLatFlow * Vcf));
            R.fprintf(F_swmm5.Frpt.file, L571, (F_massbal.NodeInflow[j] * Vcf));
            if ((Math.abs(F_massbal.NodeOutflow[j]) < 1.0d)) {
                R.fprintf(F_swmm5.Frpt.file, L572, ((((F_massbal.NodeInflow[j] - F_massbal.NodeOutflow[j])) * Vcf) * 1.0e6d), F_keywords.VolUnitsWords2[F_swmm5.UnitSystem]);
            } else {
                R.fprintf(F_swmm5.Frpt.file, L573, ((((F_massbal.NodeInflow[j] - F_massbal.NodeOutflow[j])) / F_massbal.NodeOutflow[j]) * 100.0d));
            }
        }
        F_report.report_writeLine((L0));
    }

    // statsrpt.c:403
    static void writeNodeSurcharge() {
        int j = 0;
        int n = 0;
        double t = 0.0d;
        double d1 = 0.0d;
        double d2 = 0.0d;
        F_report.report_writeLine((L0));
        F_report.report_writeLine((L574));
        F_report.report_writeLine((L575));
        F_report.report_writeLine((L574));
        F_report.report_writeLine((L0));
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            if ((F_swmm5.Node_[j].type == OUTFALL)) {
                continue L_f1;
            }
            if ((F_stats.NodeStats[j].timeSurcharged == 0.0d)) {
                continue L_f1;
            }
            t = (((R.dge((0.01d), (((F_stats.NodeStats[j].timeSurcharged / 3600.0d))))) ? (0.01d) : (((F_stats.NodeStats[j].timeSurcharged / 3600.0d)))));
            if ((n == 0)) {
                F_report.report_writeLine((L576));
                R.fprintf(F_swmm5.Frpt.file, L577);
                if ((F_swmm5.UnitSystem == US)) {
                    R.fprintf(F_swmm5.Frpt.file, L578);
                } else {
                    R.fprintf(F_swmm5.Frpt.file, L579);
                }
                R.fprintf(F_swmm5.Frpt.file, L580);
                n = 1;
            }
            R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Node_[j].ID);
            R.fprintf(F_swmm5.Frpt.file, L569, F_keywords.NodeTypeWords[F_swmm5.Node_[j].type]);
            d1 = ((F_stats.NodeStats[j].maxDepth + F_swmm5.Node_[j].invertElev) - F_swmm5.Node_[j].crownElev);
            if ((d1 < 0.0d)) {
                d1 = 0.0d;
            }
            d2 = (F_swmm5.Node_[j].fullDepth - F_stats.NodeStats[j].maxDepth);
            if ((d2 < 0.0d)) {
                d2 = 0.0d;
            }
            R.fprintf(F_swmm5.Frpt.file, L581, t, (d1 * F_swmm5.UCF(LENGTH)), (d2 * F_swmm5.UCF(LENGTH)));
        }
        if ((n == 0)) {
            F_report.report_writeLine((L582));
        }
        F_report.report_writeLine((L0));
    }

    // statsrpt.c:449
    static void writeNodeFlooding() {
        int j = 0;
        int n = 0;
        IBox days = new IBox(0);
        IBox hrs = new IBox(0);
        IBox mins = new IBox(0);
        double t = 0.0d;
        F_report.report_writeLine((L0));
        F_report.report_writeLine((L255));
        F_report.report_writeLine((L583));
        F_report.report_writeLine((L255));
        F_report.report_writeLine((L0));
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            if ((F_swmm5.Node_[j].type == OUTFALL)) {
                continue L_f1;
            }
            if ((F_stats.NodeStats[j].timeFlooded == 0.0d)) {
                continue L_f1;
            }
            t = (((R.dge((0.01d), (((F_stats.NodeStats[j].timeFlooded / 3600.0d))))) ? (0.01d) : (((F_stats.NodeStats[j].timeFlooded / 3600.0d)))));
            if ((n == 0)) {
                F_report.report_writeLine((L584));
                R.fprintf(F_swmm5.Frpt.file, L585);
                if ((F_swmm5.RouteModel == DW)) {
                    R.fprintf(F_swmm5.Frpt.file, L586);
                } else {
                    R.fprintf(F_swmm5.Frpt.file, L587);
                }
                R.fprintf(F_swmm5.Frpt.file, L588, F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.VolUnitsWords[F_swmm5.UnitSystem]);
                if ((F_swmm5.RouteModel == DW)) {
                    R.fprintf(F_swmm5.Frpt.file, L589, F_keywords.PondingUnitsWords[F_swmm5.UnitSystem]);
                } else if ((F_swmm5.UnitSystem == US)) {
                    R.fprintf(F_swmm5.Frpt.file, L590);
                } else {
                    R.fprintf(F_swmm5.Frpt.file, L591);
                }
                R.fprintf(F_swmm5.Frpt.file, L592);
                n = 1;
            }
            R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Node_[j].ID);
            R.fprintf(F_swmm5.Frpt.file, L593, t);
            R.fprintf(F_swmm5.Frpt.file, FlowFmt, (F_stats.NodeStats[j].maxOverflow * F_swmm5.UCF(FLOW)));
            F_swmm5.getElapsedTime(F_stats.NodeStats[j].maxOverflowDate, days, hrs, mins);
            R.fprintf(F_swmm5.Frpt.file, L594, days.v, hrs.v, mins.v);
            R.fprintf(F_swmm5.Frpt.file, L573, (F_stats.NodeStats[j].volFlooded * Vcf));
            if ((F_swmm5.RouteModel == DW)) {
                R.fprintf(F_swmm5.Frpt.file, L195, (((F_stats.NodeStats[j].maxDepth - F_swmm5.Node_[j].fullDepth)) * F_swmm5.UCF(LENGTH)));
            } else {
                R.fprintf(F_swmm5.Frpt.file, L195, ((F_stats.NodeStats[j].maxPondedVol / 1000.0d) * F_swmm5.UCF(VOLUME)));
            }
        }
        if ((n == 0)) {
            F_report.report_writeLine((L595));
        }
        F_report.report_writeLine((L0));
    }

    // statsrpt.c:508
    static void writeStorageVolumes() {
        int j = 0;
        int k = 0;
        IBox days = new IBox(0);
        IBox hrs = new IBox(0);
        IBox mins = new IBox(0);
        double avgVol = 0.0d;
        double maxVol = 0.0d;
        double pctAvgVol = 0.0d;
        double pctMaxVol = 0.0d;
        double pctEvapLoss = 0.0d;
        double pctSeepLoss = 0.0d;
        if ((F_swmm5.Nnodes[STORAGE] > 0)) {
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L574));
            F_report.report_writeLine((L596));
            F_report.report_writeLine((L574));
            F_report.report_writeLine((L0));
            R.fprintf(F_swmm5.Frpt.file, L597);
            if ((F_swmm5.UnitSystem == US)) {
                R.fprintf(F_swmm5.Frpt.file, L598);
            } else {
                R.fprintf(F_swmm5.Frpt.file, L599);
            }
            R.fprintf(F_swmm5.Frpt.file, L600, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
            R.fprintf(F_swmm5.Frpt.file, L601);
            L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
                if ((F_swmm5.Node_[j].type != STORAGE)) {
                    continue L_f1;
                }
                k = F_swmm5.Node_[j].subIndex;
                R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Node_[j].ID);
                avgVol = (F_stats.StorageStats[k].avgVol / ((double) (F_swmm5.ReportStepCount)));
                maxVol = F_stats.StorageStats[k].maxVol;
                pctMaxVol = 0.0d;
                pctAvgVol = 0.0d;
                if (R.dgt(F_swmm5.Node_[j].fullVolume, 0.0d)) {
                    pctAvgVol = ((avgVol / F_swmm5.Node_[j].fullVolume) * 100.0d);
                    pctMaxVol = ((maxVol / F_swmm5.Node_[j].fullVolume) * 100.0d);
                }
                pctEvapLoss = 0.0d;
                pctSeepLoss = 0.0d;
                if (R.dgt(F_massbal.NodeInflow[j], 0.0d)) {
                    pctEvapLoss = ((F_stats.StorageStats[k].evapLosses / F_massbal.NodeInflow[j]) * 100.0d);
                    pctSeepLoss = ((F_stats.StorageStats[k].exfilLosses / F_massbal.NodeInflow[j]) * 100.0d);
                }
                R.fprintf(F_swmm5.Frpt.file, L602, ((avgVol * F_swmm5.UCF(VOLUME)) / 1000.0d), pctAvgVol, pctEvapLoss, pctSeepLoss, ((maxVol * F_swmm5.UCF(VOLUME)) / 1000.0d), pctMaxVol);
                F_swmm5.getElapsedTime(F_stats.StorageStats[k].maxVolDate, days, hrs, mins);
                R.fprintf(F_swmm5.Frpt.file, L603, days.v, hrs.v, mins.v);
                R.fprintf(F_swmm5.Frpt.file, FlowFmt, (F_stats.StorageStats[k].maxFlow * F_swmm5.UCF(FLOW)));
            }
            F_report.report_writeLine((L0));
        }
    }

    // statsrpt.c:575
    static void writeOutfallLoads() {
        CPtr units = R.buf(15);
        int i = 0;
        int j = 0;
        int k = 0;
        int p = 0;
        double x = 0.0d;
        double outfallCount = 0.0d;
        double flowCount = 0.0d;
        double flowSum = 0.0d;
        double freqSum = 0.0d;
        double volSum = 0.0d;
        double[] totals = null;
        if ((F_swmm5.Nnodes[OUTFALL] > 0)) {
            totals = new double[(int) (F_swmm5.Nobjects[POLLUT])];
            L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                totals[p] = 0.0d;
            }
            flowSum = 0.0d;
            freqSum = 0.0d;
            volSum = 0.0d;
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L604));
            F_report.report_writeLine((L605));
            F_report.report_writeLine((L604));
            F_report.report_writeLine((L0));
            R.fprintf(F_swmm5.Frpt.file, L606);
            L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                R.fprintf(F_swmm5.Frpt.file, L552);
            }
            R.fprintf(F_swmm5.Frpt.file, L607);
            L_f3: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                R.fprintf(F_swmm5.Frpt.file, L608);
            }
            R.fprintf(F_swmm5.Frpt.file, L609);
            L_f4: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                R.fprintf(F_swmm5.Frpt.file, L423, F_swmm5.Pollut[p].ID);
            }
            R.fprintf(F_swmm5.Frpt.file, L610, F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.VolUnitsWords[F_swmm5.UnitSystem]);
            L_f5: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                i = F_swmm5.UnitSystem;
                if ((F_swmm5.Pollut[p].units == COUNT)) {
                    i = 2;
                }
                Overrides.sstrncpy(units, F_keywords.LoadUnitsWords[i], 14);
                R.fprintf(F_swmm5.Frpt.file, L423, units);
            }
            R.fprintf(F_swmm5.Frpt.file, L606);
            L_f6: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                R.fprintf(F_swmm5.Frpt.file, L552);
            }
            L_f7: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
                if ((F_swmm5.Node_[j].type != OUTFALL)) {
                    continue L_f7;
                }
                k = F_swmm5.Node_[j].subIndex;
                flowCount = F_stats.OutfallStats[k].totalPeriods;
                R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Node_[j].ID);
                x = ((100.0d * flowCount) / ((double) (F_swmm5.ReportStepCount)));
                R.fprintf(F_swmm5.Frpt.file, L611, x);
                freqSum += x;
                if (R.dgt(flowCount, 0)) {
                    x = ((F_stats.OutfallStats[k].avgFlow * F_swmm5.UCF(FLOW)) / flowCount);
                } else {
                    x = 0.0d;
                }
                flowSum += x;
                R.fprintf(F_swmm5.Frpt.file, L33);
                R.fprintf(F_swmm5.Frpt.file, FlowFmt, x);
                R.fprintf(F_swmm5.Frpt.file, L33);
                R.fprintf(F_swmm5.Frpt.file, FlowFmt, (F_stats.OutfallStats[k].maxFlow * F_swmm5.UCF(FLOW)));
                R.fprintf(F_swmm5.Frpt.file, L573, (F_massbal.NodeInflow[j] * Vcf));
                volSum += F_massbal.NodeInflow[j];
                L_f8: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                    x = ((F_stats.OutfallStats[k].totalLoad[p] * 28.317d) * F_swmm5.Pollut[p].mcf);
                    totals[p] += x;
                    if ((F_swmm5.Pollut[p].units == COUNT)) {
                        x = ((R.dgt((x), 0.0d) ? Glibm.log10((x)) : (x)));
                    }
                    R.fprintf(F_swmm5.Frpt.file, L427, x);
                }
            }
            outfallCount = F_swmm5.Nnodes[OUTFALL];
            R.fprintf(F_swmm5.Frpt.file, L606);
            L_f9: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                R.fprintf(F_swmm5.Frpt.file, L552);
            }
            R.fprintf(F_swmm5.Frpt.file, L612, (freqSum / outfallCount));
            R.fprintf(F_swmm5.Frpt.file, FlowFmt, flowSum);
            R.fprintf(F_swmm5.Frpt.file, L33);
            R.fprintf(F_swmm5.Frpt.file, FlowFmt, (F_stats.MaxOutfallFlow * F_swmm5.UCF(FLOW)));
            R.fprintf(F_swmm5.Frpt.file, L573, (volSum * Vcf));
            L_f10: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                x = totals[p];
                if ((F_swmm5.Pollut[p].units == COUNT)) {
                    x = ((R.dgt((x), 0.0d) ? Glibm.log10((x)) : (x)));
                }
                R.fprintf(F_swmm5.Frpt.file, L427, x);
            }
            F_report.report_writeLine((L0));
            R.free(totals);
        }
    }

    // statsrpt.c:691
    static void writeLinkFlows() {
        int j = 0;
        int k = 0;
        IBox days = new IBox(0);
        IBox hrs = new IBox(0);
        IBox mins = new IBox(0);
        double v = 0.0d;
        double fullDepth = 0.0d;
        if ((F_swmm5.Nobjects[LINK] == 0)) {
            return;
        }
        F_report.report_writeLine((L0));
        F_report.report_writeLine((L235));
        F_report.report_writeLine((L613));
        F_report.report_writeLine((L235));
        F_report.report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L614);
        if ((F_swmm5.UnitSystem == US)) {
            R.fprintf(F_swmm5.Frpt.file, L615, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L616, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        }
        R.fprintf(F_swmm5.Frpt.file, L617);
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            k = F_swmm5.Link[j].subIndex;
            R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Link[j].ID);
            if ((F_swmm5.Link[j].xsect.type == DUMMY)) {
                R.fprintf(F_swmm5.Frpt.file, L618);
            } else if ((F_swmm5.Link[j].xsect.type == IRREGULAR)) {
                R.fprintf(F_swmm5.Frpt.file, L619);
            } else {
                R.fprintf(F_swmm5.Frpt.file, L620, F_keywords.LinkTypeWords[F_swmm5.Link[j].type]);
            }
            F_swmm5.getElapsedTime(F_stats.LinkStats[j].maxFlowDate, days, hrs, mins);
            R.fprintf(F_swmm5.Frpt.file, FlowFmt, (F_stats.LinkStats[j].maxFlow * F_swmm5.UCF(FLOW)));
            R.fprintf(F_swmm5.Frpt.file, L570, days.v, hrs.v, mins.v);
            if (((F_swmm5.Link[j].type == PUMP) && (R.dgt(F_swmm5.Link[j].qFull, 0.0d)))) {
                R.fprintf(F_swmm5.Frpt.file, L621);
                R.fprintf(F_swmm5.Frpt.file, L622, (F_stats.LinkStats[j].maxFlow / F_swmm5.Link[j].qFull));
                continue L_f1;
            }
            if ((F_swmm5.Link[j].xsect.type == DUMMY)) {
                continue L_f1;
            }
            if ((F_swmm5.Link[j].type == OUTLET)) {
                continue L_f1;
            }
            if ((F_swmm5.Link[j].type == CONDUIT)) {
                v = (F_stats.LinkStats[j].maxVeloc * F_swmm5.UCF(LENGTH));
                if (R.dgt(v, 50.0d)) {
                    R.fprintf(F_swmm5.Frpt.file, L623);
                } else {
                    R.fprintf(F_swmm5.Frpt.file, L624, v);
                }
                R.fprintf(F_swmm5.Frpt.file, L622, ((F_stats.LinkStats[j].maxFlow / F_swmm5.Link[j].qFull) / ((double) (F_swmm5.Conduit[k].barrels))));
            } else {
                R.fprintf(F_swmm5.Frpt.file, L625);
            }
            fullDepth = F_swmm5.Link[j].xsect.yFull;
            if (((F_swmm5.Link[j].type == ORIFICE) && (F_swmm5.Orifice[k].type == BOTTOM_ORIFICE))) {
                fullDepth = 0.0d;
            }
            if (R.dgt(fullDepth, 0.0d)) {
                R.fprintf(F_swmm5.Frpt.file, L622, (F_stats.LinkStats[j].maxDepth / fullDepth));
            } else {
                R.fprintf(F_swmm5.Frpt.file, L626);
            }
        }
        F_report.report_writeLine((L0));
    }

    // statsrpt.c:778
    static void writeFlowClass() {
        int i = 0;
        int j = 0;
        int k = 0;
        double totalSeconds = F_stats.RoutingTimeSpan;
        if ((F_swmm5.RouteModel != DW)) {
            return;
        }
        F_report.report_writeLine((L0));
        F_report.report_writeLine((L473));
        F_report.report_writeLine((L627));
        F_report.report_writeLine((L473));
        F_report.report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L628);
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            if ((F_swmm5.Link[j].type != CONDUIT)) {
                continue L_f1;
            }
            if ((F_swmm5.Link[j].xsect.type == DUMMY)) {
                continue L_f1;
            }
            k = F_swmm5.Link[j].subIndex;
            R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Link[j].ID);
            R.fprintf(F_swmm5.Frpt.file, L629, (F_swmm5.Conduit[k].modLength / F_swmm5.Conduit[k].length));
            L_f2: for (i = 0; (i < MAX_FLOW_CLASSES); i++) {
                R.fprintf(F_swmm5.Frpt.file, L630, (F_stats.LinkStats[j].timeInFlowClass[i] = (F_stats.LinkStats[j].timeInFlowClass[i] / totalSeconds)));
            }
            R.fprintf(F_swmm5.Frpt.file, L630, (F_stats.LinkStats[j].timeNormalFlow / totalSeconds));
            R.fprintf(F_swmm5.Frpt.file, L630, (F_stats.LinkStats[j].timeInletControl / totalSeconds));
        }
        F_report.report_writeLine((L0));
    }

    // statsrpt.c:821
    static void writeLinkSurcharge() {
        int i = 0;
        int j = 0;
        int n = 0;
        double[] t = new double[5];
        F_report.report_writeLine((L0));
        F_report.report_writeLine((L470));
        F_report.report_writeLine((L631));
        F_report.report_writeLine((L470));
        F_report.report_writeLine((L0));
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            if (((F_swmm5.Link[j].type != CONDUIT) || (F_swmm5.Link[j].xsect.type == DUMMY))) {
                continue L_f1;
            }
            t[0] = (F_stats.LinkStats[j].timeSurcharged / 3600.0d);
            t[1] = (F_stats.LinkStats[j].timeFullUpstream / 3600.0d);
            t[2] = (F_stats.LinkStats[j].timeFullDnstream / 3600.0d);
            t[3] = (F_stats.LinkStats[j].timeFullFlow / 3600.0d);
            if (((((t[0] + t[1]) + t[2]) + t[3]) == 0.0d)) {
                continue L_f1;
            }
            t[4] = (F_stats.LinkStats[j].timeCapacityLimited / 3600.0d);
            L_f2: for (i = 0; (i < 5); i++) {
                t[i] = (((R.dge((0.01d), (t[i]))) ? (0.01d) : (t[i])));
            }
            if ((n == 0)) {
                R.fprintf(F_swmm5.Frpt.file, L632);
                n = 1;
            }
            R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Link[j].ID);
            R.fprintf(F_swmm5.Frpt.file, L633, t[0], t[1], t[2], t[3], t[4]);
        }
        if ((n == 0)) {
            F_report.report_writeLine((L634));
        }
        F_report.report_writeLine((L0));
    }

    // statsrpt.c:862
    static void writePumpFlows() {
        int j = 0;
        int k = 0;
        double avgFlow = 0.0d;
        double pctUtilized = 0.0d;
        double pctOffCurve1 = 0.0d;
        double pctOffCurve2 = 0.0d;
        double totalSeconds = F_stats.RoutingTimeSpan;
        if ((F_swmm5.Nlinks[PUMP] == 0)) {
            return;
        }
        F_report.report_writeLine((L0));
        F_report.report_writeLine((L221));
        F_report.report_writeLine((L635));
        F_report.report_writeLine((L221));
        F_report.report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L636, F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.VolUnitsWords[F_swmm5.UnitSystem]);
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            if ((F_swmm5.Link[j].type != PUMP)) {
                continue L_f1;
            }
            k = F_swmm5.Link[j].subIndex;
            R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Link[j].ID);
            pctUtilized = ((F_stats.PumpStats[k].utilized / totalSeconds) * 100.0d);
            avgFlow = F_stats.PumpStats[k].avgFlow;
            if ((F_stats.PumpStats[k].totalPeriods > 0)) {
                avgFlow = (avgFlow / F_stats.PumpStats[k].totalPeriods);
            }
            R.fprintf(F_swmm5.Frpt.file, L637, pctUtilized, F_stats.PumpStats[k].startUps, (F_stats.PumpStats[k].minFlow * F_swmm5.UCF(FLOW)), (avgFlow * F_swmm5.UCF(FLOW)), (F_stats.PumpStats[k].maxFlow * F_swmm5.UCF(FLOW)), (F_stats.PumpStats[k].volume * Vcf), F_stats.PumpStats[k].energy);
            pctOffCurve1 = F_stats.PumpStats[k].offCurveLow;
            pctOffCurve2 = F_stats.PumpStats[k].offCurveHigh;
            if (R.dgt(F_stats.PumpStats[k].utilized, 0.0d)) {
                pctOffCurve1 = ((pctOffCurve1 / F_stats.PumpStats[k].utilized) * 100.0d);
                pctOffCurve2 = ((pctOffCurve2 / F_stats.PumpStats[k].utilized) * 100.0d);
            }
            R.fprintf(F_swmm5.Frpt.file, L638, pctOffCurve1, pctOffCurve2);
        }
        F_report.report_writeLine((L0));
    }

    // statsrpt.c:916
    static void writeLinkLoads() {
        int i = 0;
        int j = 0;
        int p = 0;
        double x = 0.0d;
        CPtr units = R.buf(15);
        CPtr linkLine = R.bufFrom(L551, 21);
        CPtr pollutLine = R.bufFrom(L552, 15);
        F_report.report_writeLine((L0));
        F_report.report_writeLine((L473));
        F_report.report_writeLine((L639));
        F_report.report_writeLine((L473));
        F_report.report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L356, linkLine);
        L_f1: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            R.fprintf(F_swmm5.Frpt.file, L169, pollutLine);
        }
        R.fprintf(F_swmm5.Frpt.file, L555);
        L_f2: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            R.fprintf(F_swmm5.Frpt.file, L423, F_swmm5.Pollut[p].ID);
        }
        R.fprintf(F_swmm5.Frpt.file, L640);
        L_f3: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            i = F_swmm5.UnitSystem;
            if ((F_swmm5.Pollut[p].units == COUNT)) {
                i = 2;
            }
            Overrides.sstrncpy(units, F_keywords.LoadUnitsWords[i], 14);
            R.fprintf(F_swmm5.Frpt.file, L423, units);
        }
        R.fprintf(F_swmm5.Frpt.file, L356, linkLine);
        L_f4: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
            R.fprintf(F_swmm5.Frpt.file, L169, pollutLine);
        }
        L_f5: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            R.fprintf(F_swmm5.Frpt.file, L541, F_swmm5.Link[j].ID);
            L_f6: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                x = ((F_swmm5.Link[j].totalLoad[p] * 28.317d) * F_swmm5.Pollut[p].mcf);
                if ((F_swmm5.Pollut[p].units == COUNT)) {
                    x = ((R.dgt((x), 0.0d) ? Glibm.log10((x)) : (x)));
                }
                if ((x < 10000.0d)) {
                    R.fprintf(F_swmm5.Frpt.file, L427, x);
                } else {
                    R.fprintf(F_swmm5.Frpt.file, L641, x);
                }
            }
        }
        F_report.report_writeLine((L0));
    }

}
