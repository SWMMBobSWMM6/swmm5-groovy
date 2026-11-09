// Generated from EPA SWMM 5.2.4 (report.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_report {
    public static LBox SysTime = new LBox(0L);

    // report.c:93
    static int report_readOptions(CPtr[] tok, int ntoks) {
        int k = 0;
        int j = 0;
        int m = 0;
        int t = 0;
        if ((ntoks < 2)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        k = ((byte) (F_input.findmatch(tok[0], F_keywords.ReportWords)));
        if ((k < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[0]);
        }
        if (((k < 2) || (k > 4))) {
            m = F_input.findmatch(tok[1], F_keywords.NoYesWords);
            if (((m < NO) || (m > YES))) {
                return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
            }
            int __sw1 = k;
            if (__sw1 == 0) {
                F_swmm5.RptFlags.@disabled = m;
                return 0;
            } else if (__sw1 == 1) {
                F_swmm5.RptFlags.@input = m;
                return 0;
            } else if (__sw1 == 5) {
                F_swmm5.RptFlags.@continuity = m;
                return 0;
            } else if (__sw1 == 6) {
                F_swmm5.RptFlags.@flowStats = m;
                return 0;
            } else if (__sw1 == 7) {
                F_swmm5.RptFlags.@controls = m;
                return 0;
            } else if (__sw1 == 8) {
                F_swmm5.RptFlags.@averages = m;
                return 0;
            } else if (__sw1 == 9) {
                return 0;
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
            }
        } else {
            int __sw2 = k;
            if (__sw2 == 2) {
                m = SUBCATCH;
            } else if (__sw2 == 3) {
                m = NODE;
            } else if (__sw2 == 4) {
                m = LINK;
            }
        }
        if ((F_swmm5.strcomp(tok[1], L355) != 0)) {
            k = NONE;
        } else if ((F_swmm5.strcomp(tok[1], L351) != 0)) {
            k = ALL;
        } else {
            k = SOME;
            L_f3: for (t = 1; (t < ntoks); t++) {
                j = F_project.project_findObject(m, tok[t]);
                if ((j < 0)) {
                    return F_error.error_setInpError(ERR_NAME, tok[t]);
                }
                int __sw3 = m;
                if (__sw3 == SUBCATCH) {
                    F_swmm5.Subcatch[j].@rptFlag = 1;
                } else if (__sw3 == NODE) {
                    F_swmm5.Node_[j].@rptFlag = 1;
                } else if (__sw3 == LINK) {
                    F_swmm5.Link[j].@rptFlag = 1;
                }
            }
        }
        int __sw4 = m;
        if (__sw4 == SUBCATCH) {
            F_swmm5.RptFlags.@subcatchments = k;
        } else if (__sw4 == NODE) {
            F_swmm5.RptFlags.@nodes = k;
        } else if (__sw4 == LINK) {
            F_swmm5.RptFlags.@links = k;
        }
        return 0;
    }

    // report.c:171
    static void report_writeLine(CPtr line) {
        if ((F_swmm5.Frpt.file != null)) {
            R.fprintf(F_swmm5.Frpt.file, L356, line);
        }
    }

    // report.c:183
    static void report_writeSysTime() {
        CPtr theTime = R.buf(9);
        double elapsedTime = 0.0d;
        LBox endTime = new LBox(0L);
        if ((F_swmm5.Frpt.file != null)) {
            R.fprintf(F_swmm5.Frpt.file, L357, R.ctime(SysTime.v));
            endTime.@v = R.time();
            R.fprintf(F_swmm5.Frpt.file, L358, R.ctime(endTime.v));
            elapsedTime = ((double) (endTime.v - SysTime.v));
            R.fprintf(F_swmm5.Frpt.file, L359);
            if ((elapsedTime < 1.0d)) {
                R.fprintf(F_swmm5.Frpt.file, L360);
            } else {
                elapsedTime = (elapsedTime / 86400);
                if (R.dge(elapsedTime, 1.0d)) {
                    R.fprintf(F_swmm5.Frpt.file, L361, R.D2I(Math.floor(elapsedTime)));
                    elapsedTime -= (Math.floor(elapsedTime));
                }
                F_datetime.datetime_timeToStr(elapsedTime, theTime);
                R.fprintf(F_swmm5.Frpt.file, L169, theTime);
            }
        }
    }

    // report.c:220
    static void report_writeLogo() {
        R.fprintf(F_swmm5.Frpt.file, L362);
        R.fprintf(F_swmm5.Frpt.file, L363);
        R.fprintf(F_swmm5.Frpt.file, L276);
        SysTime.@v = R.time();
    }

    // report.c:235
    static void report_writeTitle() {
        int i = 0;
        int lineCount = 0;
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        L_f1: for (i = 0; (i < 3); i++) {
            if ((R.strlen(F_swmm5.Title[i]) > 0)) {
                report_writeLine((F_swmm5.Title[i]));
                lineCount++;
            }
        }
    }

    // report.c:254
    static void report_writeOptions() {
        CPtr str = R.buf(80);
        report_writeLine((L0));
        report_writeLine((L227));
        report_writeLine((L364));
        report_writeLine((L227));
        R.fprintf(F_swmm5.Frpt.file, L365, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        R.fprintf(F_swmm5.Frpt.file, L366);
        R.fprintf(F_swmm5.Frpt.file, L367);
        if (((F_swmm5.IgnoreRainfall != 0) || (F_swmm5.Nobjects[GAGE] == 0))) {
            R.fprintf(F_swmm5.Frpt.file, L4);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L5);
        }
        R.fprintf(F_swmm5.Frpt.file, L368);
        if (((F_swmm5.IgnoreRDII != 0) || (F_swmm5.Nobjects[UNITHYD] == 0))) {
            R.fprintf(F_swmm5.Frpt.file, L4);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L5);
        }
        R.fprintf(F_swmm5.Frpt.file, L369);
        if (((F_swmm5.IgnoreSnowmelt != 0) || (F_swmm5.Nobjects[SNOWMELT] == 0))) {
            R.fprintf(F_swmm5.Frpt.file, L4);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L5);
        }
        R.fprintf(F_swmm5.Frpt.file, L370);
        if (((F_swmm5.IgnoreGwater != 0) || (F_swmm5.Nobjects[AQUIFER] == 0))) {
            R.fprintf(F_swmm5.Frpt.file, L4);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L5);
        }
        R.fprintf(F_swmm5.Frpt.file, L371);
        if (((F_swmm5.IgnoreRouting != 0) || (F_swmm5.Nobjects[LINK] == 0))) {
            R.fprintf(F_swmm5.Frpt.file, L4);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L5);
            R.fprintf(F_swmm5.Frpt.file, L372);
            if ((F_swmm5.AllowPonding != 0)) {
                R.fprintf(F_swmm5.Frpt.file, L5);
            } else {
                R.fprintf(F_swmm5.Frpt.file, L4);
            }
        }
        R.fprintf(F_swmm5.Frpt.file, L373);
        if (((F_swmm5.IgnoreQuality != 0) || (F_swmm5.Nobjects[POLLUT] == 0))) {
            R.fprintf(F_swmm5.Frpt.file, L4);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L5);
        }
        if ((F_swmm5.Nobjects[SUBCATCH] > 0)) {
            R.fprintf(F_swmm5.Frpt.file, L374, F_keywords.InfilModelWords[F_swmm5.InfilModel]);
        }
        if ((F_swmm5.Nobjects[LINK] > 0)) {
            R.fprintf(F_swmm5.Frpt.file, L375, F_keywords.RouteModelWords[F_swmm5.RouteModel]);
        }
        if ((F_swmm5.RouteModel == DW)) {
            R.fprintf(F_swmm5.Frpt.file, L376, F_keywords.SurchargeWords[F_swmm5.SurchargeMethod]);
        }
        F_datetime.datetime_dateToStr(F_swmm5.StartDate.v, str);
        R.fprintf(F_swmm5.Frpt.file, L377, str);
        F_datetime.datetime_timeToStr(F_swmm5.StartTime.v, str);
        R.fprintf(F_swmm5.Frpt.file, L378, str);
        F_datetime.datetime_dateToStr(F_swmm5.EndDate.v, str);
        R.fprintf(F_swmm5.Frpt.file, L379, str);
        F_datetime.datetime_timeToStr(F_swmm5.EndTime.v, str);
        R.fprintf(F_swmm5.Frpt.file, L378, str);
        R.fprintf(F_swmm5.Frpt.file, L380, F_swmm5.StartDryDays);
        F_datetime.datetime_timeToStr(F_datetime.datetime_encodeTime(0, 0, F_swmm5.ReportStep), str);
        R.fprintf(F_swmm5.Frpt.file, L381, str);
        if ((F_swmm5.Nobjects[SUBCATCH] > 0)) {
            F_datetime.datetime_timeToStr(F_datetime.datetime_encodeTime(0, 0, F_swmm5.WetStep), str);
            R.fprintf(F_swmm5.Frpt.file, L382, str);
            F_datetime.datetime_timeToStr(F_datetime.datetime_encodeTime(0, 0, F_swmm5.DryStep), str);
            R.fprintf(F_swmm5.Frpt.file, L383, str);
        }
        if ((F_swmm5.Nobjects[LINK] > 0)) {
            R.fprintf(F_swmm5.Frpt.file, L384, F_swmm5.RouteStep);
            if ((F_swmm5.RouteModel == DW)) {
                R.fprintf(F_swmm5.Frpt.file, L385);
                if (R.dgt(F_swmm5.CourantFactor.v, 0.0d)) {
                    R.fprintf(F_swmm5.Frpt.file, L5);
                } else {
                    R.fprintf(F_swmm5.Frpt.file, L4);
                }
                R.fprintf(F_swmm5.Frpt.file, L386, F_swmm5.MaxTrials);
                R.fprintf(F_swmm5.Frpt.file, L387, F_swmm5.NumThreads);
                R.fprintf(F_swmm5.Frpt.file, L388, (F_swmm5.HeadTol.v * F_swmm5.UCF(LENGTH)));
                if ((F_swmm5.UnitSystem == US)) {
                    R.fprintf(F_swmm5.Frpt.file, L389);
                } else {
                    R.fprintf(F_swmm5.Frpt.file, L390);
                }
            }
        }
        report_writeLine((L0));
    }

    // report.c:355
    static void report_writeRainStats(int i, TRainStats r) {
        CPtr date1 = R.bufFrom(L391, 12);
        CPtr date2 = R.bufFrom(L391, 12);
        if ((F_swmm5.RptFlags.disabled != 0)) {
            return;
        }
        if ((i < 0)) {
            report_writeLine((L0));
            report_writeLine((L255));
            report_writeLine((L392));
            report_writeLine((L255));
            R.fprintf(F_swmm5.Frpt.file, L393);
            R.fprintf(F_swmm5.Frpt.file, L394);
            R.fprintf(F_swmm5.Frpt.file, L245);
        } else {
            if ((r.startDate != (-693594))) {
                F_datetime.datetime_dateToStr(r.startDate, date1);
            }
            if ((r.endDate != (-693594))) {
                F_datetime.datetime_dateToStr(r.endDate, date2);
            }
            R.fprintf(F_swmm5.Frpt.file, L395, F_swmm5.Gage[i].staID, date1, date2, R.idiv(F_swmm5.Gage[i].rainInterval, 60), r.periodsRain, r.periodsMissing, r.periodsMalfunc);
        }
        report_writeLine((L0));
    }

    // report.c:396
    static void report_writeRdiiStats(double rainVol, double rdiiVol) {
        double ratio = 0.0d;
        double ucf1 = 0.0d;
        double ucf2 = 0.0d;
        if ((F_swmm5.RptFlags.disabled != 0)) {
            return;
        }
        ucf1 = (F_swmm5.UCF(LENGTH) * F_swmm5.UCF(LANDAREA));
        if ((F_swmm5.UnitSystem == US)) {
            ucf2 = (0.64632d / 86400);
        } else {
            ucf2 = (2.4466d / 86400);
        }
        report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L396);
        if ((F_swmm5.UnitSystem == US)) {
            R.fprintf(F_swmm5.Frpt.file, L397);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L398);
        }
        R.fprintf(F_swmm5.Frpt.file, L399);
        R.fprintf(F_swmm5.Frpt.file, L400, (rainVol * ucf1), (rainVol * ucf2));
        R.fprintf(F_swmm5.Frpt.file, L401, (rdiiVol * ucf1), (rdiiVol * ucf2));
        if ((rainVol == 0.0d)) {
            ratio = 0.0d;
        } else {
            ratio = (rdiiVol / rainVol);
        }
        R.fprintf(F_swmm5.Frpt.file, L402, ratio);
        report_writeLine((L0));
    }

    // report.c:441
    static void report_writeControlActionsHeading() {
        report_writeLine((L0));
        report_writeLine((L255));
        report_writeLine((L403));
        report_writeLine((L255));
        R.fprintf(F_swmm5.Frpt.file, L276);
    }

    // report.c:452
    static void report_writeControlAction(double aDate, CPtr linkID, double value, CPtr ruleID) {
        CPtr theDate = R.buf(12);
        CPtr theTime = R.buf(9);
        if ((F_swmm5.RptFlags.disabled != 0)) {
            return;
        }
        F_datetime.datetime_dateToStr(aDate, theDate);
        F_datetime.datetime_timeToStr(aDate, theTime);
        R.fprintf(F_swmm5.Frpt.file, L404, theDate, theTime, linkID, value, ruleID);
    }

    // report.c:480
    static void report_writeRunoffError(TRunoffTotals totals, double totalArea) {
        if ((F_swmm5.Frunoff.mode == USE_FILE)) {
            report_writeLine((L0));
            R.fprintf(F_swmm5.Frpt.file, L405, F_swmm5.Frunoff.name);
            report_writeLine((L0));
            return;
        }
        if ((totalArea == 0.0d)) {
            return;
        }
        report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L406);
        if ((F_swmm5.UnitSystem == US)) {
            R.fprintf(F_swmm5.Frpt.file, L407);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L408);
        }
        R.fprintf(F_swmm5.Frpt.file, L409);
        if (R.dgt(totals.initStorage, 0.0d)) {
            R.fprintf(F_swmm5.Frpt.file, L410, ((totals.initStorage * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.initStorage / totalArea) * F_swmm5.UCF(RAINDEPTH)));
        }
        if ((F_swmm5.Nobjects[SNOWMELT] > 0)) {
            R.fprintf(F_swmm5.Frpt.file, L411, ((totals.initSnowCover * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.initSnowCover / totalArea) * F_swmm5.UCF(RAINDEPTH)));
        }
        R.fprintf(F_swmm5.Frpt.file, L412, ((totals.rainfall * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.rainfall / totalArea) * F_swmm5.UCF(RAINDEPTH)));
        if (R.dgt(totals.runon, 0.0d)) {
            R.fprintf(F_swmm5.Frpt.file, L413, ((totals.runon * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.runon / totalArea) * F_swmm5.UCF(RAINDEPTH)));
        }
        R.fprintf(F_swmm5.Frpt.file, L414, ((totals.evap * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.evap / totalArea) * F_swmm5.UCF(RAINDEPTH)));
        R.fprintf(F_swmm5.Frpt.file, L415, ((totals.infil * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.infil / totalArea) * F_swmm5.UCF(RAINDEPTH)));
        R.fprintf(F_swmm5.Frpt.file, L416, ((totals.runoff * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.runoff / totalArea) * F_swmm5.UCF(RAINDEPTH)));
        if (R.dgt(totals.drains, 0.0d)) {
            R.fprintf(F_swmm5.Frpt.file, L417, ((totals.drains * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.drains / totalArea) * F_swmm5.UCF(RAINDEPTH)));
        }
        if ((F_swmm5.Nobjects[SNOWMELT] > 0)) {
            R.fprintf(F_swmm5.Frpt.file, L418, ((totals.snowRemoved * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.snowRemoved / totalArea) * F_swmm5.UCF(RAINDEPTH)));
            R.fprintf(F_swmm5.Frpt.file, L419, ((totals.finalSnowCover * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.finalSnowCover / totalArea) * F_swmm5.UCF(RAINDEPTH)));
        }
        R.fprintf(F_swmm5.Frpt.file, L420, ((totals.finalStorage * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.finalStorage / totalArea) * F_swmm5.UCF(RAINDEPTH)));
        R.fprintf(F_swmm5.Frpt.file, L421, totals.pctError);
        report_writeLine((L0));
    }

    // report.c:577
    static void report_writeLoadingError(TLoadingTotals[] totals) {
        int p1 = 0;
        int p2 = 0;
        p1 = 1;
        p2 = (((((5) <= (F_swmm5.Nobjects[POLLUT]))) ? (5) : (F_swmm5.Nobjects[POLLUT])));
        L_w1: while ((p1 <= F_swmm5.Nobjects[POLLUT])) {
            report_LoadingErrors((p1 - 1), (p2 - 1), totals);
            p1 = (p2 + 1);
            p2 = (p1 + 4);
            p2 = (((((p2) <= (F_swmm5.Nobjects[POLLUT]))) ? (p2) : (F_swmm5.Nobjects[POLLUT])));
        }
    }

    // report.c:599
    static void report_LoadingErrors(int p1, int p2, TLoadingTotals[] totals) {
        int i = 0;
        int p = 0;
        double cf = 1.0d;
        CPtr units = R.buf(15);
        report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L422);
        L_f1: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L423, F_swmm5.Pollut[p].ID);
        }
        R.fprintf(F_swmm5.Frpt.file, L424);
        L_f2: for (p = p1; (p <= p2); p++) {
            i = F_swmm5.UnitSystem;
            if ((F_swmm5.Pollut[p].units == COUNT)) {
                i = 2;
            }
            Overrides.sstrncpy(units, F_keywords.LoadUnitsWords[i], 14);
            R.fprintf(F_swmm5.Frpt.file, L423, units);
        }
        R.fprintf(F_swmm5.Frpt.file, L422);
        L_f3: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L425);
        }
        R.fprintf(F_swmm5.Frpt.file, L426);
        L_f4: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, (totals[p].initLoad * cf));
        }
        R.fprintf(F_swmm5.Frpt.file, L428);
        L_f5: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, (totals[p].buildup * cf));
        }
        R.fprintf(F_swmm5.Frpt.file, L429);
        L_f6: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, (totals[p].deposition * cf));
        }
        R.fprintf(F_swmm5.Frpt.file, L430);
        L_f7: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, (totals[p].sweeping * cf));
        }
        R.fprintf(F_swmm5.Frpt.file, L431);
        L_f8: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, (totals[p].infil * cf));
        }
        R.fprintf(F_swmm5.Frpt.file, L432);
        L_f9: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, (totals[p].bmpRemoval * cf));
        }
        R.fprintf(F_swmm5.Frpt.file, L433);
        L_f10: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, (totals[p].runoff * cf));
        }
        R.fprintf(F_swmm5.Frpt.file, L434);
        L_f11: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, (totals[p].finalLoad * cf));
        }
        R.fprintf(F_swmm5.Frpt.file, L435);
        L_f12: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, totals[p].pctError);
        }
        report_writeLine((L0));
    }

    // report.c:685
    static void report_writeGwaterError(TGwaterTotals totals, double gwArea) {
        report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L406);
        if ((F_swmm5.UnitSystem == US)) {
            R.fprintf(F_swmm5.Frpt.file, L436);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L437);
        }
        R.fprintf(F_swmm5.Frpt.file, L409);
        R.fprintf(F_swmm5.Frpt.file, L438, ((totals.initStorage * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.initStorage / gwArea) * F_swmm5.UCF(RAINDEPTH)));
        R.fprintf(F_swmm5.Frpt.file, L439, ((totals.infil * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.infil / gwArea) * F_swmm5.UCF(RAINDEPTH)));
        R.fprintf(F_swmm5.Frpt.file, L440, ((totals.upperEvap * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.upperEvap / gwArea) * F_swmm5.UCF(RAINDEPTH)));
        R.fprintf(F_swmm5.Frpt.file, L441, ((totals.lowerEvap * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.lowerEvap / gwArea) * F_swmm5.UCF(RAINDEPTH)));
        R.fprintf(F_swmm5.Frpt.file, L442, ((totals.lowerPerc * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.lowerPerc / gwArea) * F_swmm5.UCF(RAINDEPTH)));
        R.fprintf(F_swmm5.Frpt.file, L443, ((totals.gwater * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.gwater / gwArea) * F_swmm5.UCF(RAINDEPTH)));
        R.fprintf(F_swmm5.Frpt.file, L420, ((totals.finalStorage * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LANDAREA)), ((totals.finalStorage / gwArea) * F_swmm5.UCF(RAINDEPTH)));
        R.fprintf(F_swmm5.Frpt.file, L421, totals.pctError);
        report_writeLine((L0));
    }

    // report.c:737
    static void report_writeFlowError(TRoutingTotals totals) {
        double ucf1 = 0.0d;
        double ucf2 = 0.0d;
        ucf1 = (F_swmm5.UCF(LENGTH) * F_swmm5.UCF(LANDAREA));
        if ((F_swmm5.UnitSystem == US)) {
            ucf2 = (0.64632d / 86400);
        } else {
            ucf2 = (2.4466d / 86400);
        }
        report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L444);
        if ((F_swmm5.UnitSystem == US)) {
            R.fprintf(F_swmm5.Frpt.file, L445);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L446);
        }
        R.fprintf(F_swmm5.Frpt.file, L447);
        R.fprintf(F_swmm5.Frpt.file, L448, (totals.dwInflow * ucf1), (totals.dwInflow * ucf2));
        R.fprintf(F_swmm5.Frpt.file, L449, (totals.wwInflow * ucf1), (totals.wwInflow * ucf2));
        R.fprintf(F_swmm5.Frpt.file, L450, (totals.gwInflow * ucf1), (totals.gwInflow * ucf2));
        R.fprintf(F_swmm5.Frpt.file, L451, (totals.iiInflow * ucf1), (totals.iiInflow * ucf2));
        R.fprintf(F_swmm5.Frpt.file, L452, (totals.exInflow * ucf1), (totals.exInflow * ucf2));
        R.fprintf(F_swmm5.Frpt.file, L453, (totals.outflow * ucf1), (totals.outflow * ucf2));
        R.fprintf(F_swmm5.Frpt.file, L454, (totals.flooding * ucf1), (totals.flooding * ucf2));
        R.fprintf(F_swmm5.Frpt.file, L414, (totals.evapLoss * ucf1), (totals.evapLoss * ucf2));
        R.fprintf(F_swmm5.Frpt.file, L455, (totals.seepLoss * ucf1), (totals.seepLoss * ucf2));
        R.fprintf(F_swmm5.Frpt.file, L456, (totals.initStorage * ucf1), (totals.initStorage * ucf2));
        R.fprintf(F_swmm5.Frpt.file, L457, (totals.finalStorage * ucf1), (totals.finalStorage * ucf2));
        R.fprintf(F_swmm5.Frpt.file, L421, totals.pctError);
        report_writeLine((L0));
    }

    // report.c:800
    static void report_writeQualError(TRoutingTotals[] QualTotals) {
        int p1 = 0;
        int p2 = 0;
        p1 = 1;
        p2 = (((((5) <= (F_swmm5.Nobjects[POLLUT]))) ? (5) : (F_swmm5.Nobjects[POLLUT])));
        L_w1: while ((p1 <= F_swmm5.Nobjects[POLLUT])) {
            report_QualErrors((p1 - 1), (p2 - 1), QualTotals);
            p1 = (p2 + 1);
            p2 = (p1 + 4);
            p2 = (((((p2) <= (F_swmm5.Nobjects[POLLUT]))) ? (p2) : (F_swmm5.Nobjects[POLLUT])));
        }
    }

    // report.c:821
    static void report_QualErrors(int p1, int p2, TRoutingTotals[] QualTotals) {
        int i = 0;
        int p = 0;
        CPtr units = R.buf(15);
        report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L422);
        L_f1: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L423, F_swmm5.Pollut[p].ID);
        }
        R.fprintf(F_swmm5.Frpt.file, L458);
        L_f2: for (p = p1; (p <= p2); p++) {
            i = F_swmm5.UnitSystem;
            if ((F_swmm5.Pollut[p].units == COUNT)) {
                i = 2;
            }
            Overrides.sstrncpy(units, F_keywords.LoadUnitsWords[i], 14);
            R.fprintf(F_swmm5.Frpt.file, L423, units);
        }
        R.fprintf(F_swmm5.Frpt.file, L422);
        L_f3: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L425);
        }
        R.fprintf(F_swmm5.Frpt.file, L459);
        L_f4: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, QualTotals[p].dwInflow);
        }
        R.fprintf(F_swmm5.Frpt.file, L460);
        L_f5: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, QualTotals[p].wwInflow);
        }
        R.fprintf(F_swmm5.Frpt.file, L461);
        L_f6: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, QualTotals[p].gwInflow);
        }
        R.fprintf(F_swmm5.Frpt.file, L462);
        L_f7: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, QualTotals[p].iiInflow);
        }
        R.fprintf(F_swmm5.Frpt.file, L463);
        L_f8: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, QualTotals[p].exInflow);
        }
        R.fprintf(F_swmm5.Frpt.file, L464);
        L_f9: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, QualTotals[p].outflow);
        }
        R.fprintf(F_swmm5.Frpt.file, L465);
        L_f10: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, QualTotals[p].flooding);
        }
        R.fprintf(F_swmm5.Frpt.file, L466);
        L_f11: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, QualTotals[p].seepLoss);
        }
        R.fprintf(F_swmm5.Frpt.file, L467);
        L_f12: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, QualTotals[p].reacted);
        }
        R.fprintf(F_swmm5.Frpt.file, L468);
        L_f13: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, QualTotals[p].initStorage);
        }
        R.fprintf(F_swmm5.Frpt.file, L469);
        L_f14: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, QualTotals[p].finalStorage);
        }
        R.fprintf(F_swmm5.Frpt.file, L435);
        L_f15: for (p = p1; (p <= p2); p++) {
            R.fprintf(F_swmm5.Frpt.file, L427, QualTotals[p].pctError);
        }
        report_writeLine((L0));
    }

    // report.c:923
    static void report_writeMaxStats(TMaxStats[] maxMassBalErrs, TMaxStats[] maxCourantCrit, int nMaxStats) {
        int i = 0;
        int j = 0;
        int k = 0;
        if (((F_swmm5.RouteModel != DW) || (F_swmm5.Nobjects[LINK] == 0))) {
            return;
        }
        if ((nMaxStats <= 0)) {
            return;
        }
        if ((maxMassBalErrs[0].index >= 0)) {
            report_writeLine((L0));
            report_writeLine((L470));
            report_writeLine((L471));
            report_writeLine((L470));
            L_f1: for (i = 0; (i < nMaxStats); i++) {
                j = maxMassBalErrs[i].index;
                if ((j < 0)) {
                    continue L_f1;
                }
                R.fprintf(F_swmm5.Frpt.file, L472, F_swmm5.Node_[j].ID, maxMassBalErrs[i].value);
            }
            report_writeLine((L0));
        }
        if ((F_swmm5.CourantFactor.v == 0.0d)) {
            return;
        }
        report_writeLine((L0));
        report_writeLine((L473));
        report_writeLine((L474));
        report_writeLine((L473));
        k = 0;
        L_f2: for (i = 0; (i < nMaxStats); i++) {
            j = maxCourantCrit[i].index;
            if ((j < 0)) {
                continue L_f2;
            }
            k++;
            if ((maxCourantCrit[i].objType == NODE)) {
                R.fprintf(F_swmm5.Frpt.file, L475, F_swmm5.Node_[j].ID);
            } else {
                R.fprintf(F_swmm5.Frpt.file, L476, F_swmm5.Link[j].ID);
            }
            R.fprintf(F_swmm5.Frpt.file, L477, maxCourantCrit[i].value);
        }
        if ((k == 0)) {
            R.fprintf(F_swmm5.Frpt.file, L478);
        }
        report_writeLine((L0));
    }

    // report.c:977
    static void report_writeMaxFlowTurns(TMaxStats[] flowTurns, int nMaxStats) {
        int i = 0;
        int j = 0;
        if ((F_swmm5.Nobjects[LINK] == 0)) {
            return;
        }
        report_writeLine((L0));
        report_writeLine((L479));
        report_writeLine((L480));
        report_writeLine((L479));
        if (((nMaxStats <= 0) || (flowTurns[0].index <= 0))) {
            R.fprintf(F_swmm5.Frpt.file, L481);
        } else {
            L_f1: for (i = 0; (i < nMaxStats); i++) {
                j = flowTurns[i].index;
                if ((j < 0)) {
                    continue L_f1;
                }
                R.fprintf(F_swmm5.Frpt.file, L482, F_swmm5.Link[j].ID, flowTurns[i].value);
            }
        }
        report_writeLine((L0));
    }

    // report.c:1011
    static void report_writeNonconvergedStats(TMaxStats[] maxNonconverged, int nMaxStats) {
        int i = 0;
        int j = 0;
        if (((F_swmm5.Nobjects[NODE] == 0) || (F_swmm5.RouteModel != DW))) {
            return;
        }
        report_writeLine((L0));
        report_writeLine((L483));
        report_writeLine((L484));
        report_writeLine((L483));
        if ((((nMaxStats <= 0) || (maxNonconverged[0].index <= 0)) || (maxNonconverged[0].value < 0.00005d))) {
            R.fprintf(F_swmm5.Frpt.file, L485);
        } else {
            L_f1: for (i = 0; (i < nMaxStats); i++) {
                j = maxNonconverged[i].index;
                if (((j < 0) || (maxNonconverged[i].value <= 0.0d))) {
                    continue L_f1;
                }
                R.fprintf(F_swmm5.Frpt.file, L472, F_swmm5.Node_[j].ID, (100.0d * maxNonconverged[i].value));
            }
        }
        report_writeLine((L0));
    }

    // report.c:1037
    static void report_writeTimeStepStats(TTimeStepStats timeStepStats) {
        double timeStepCount = timeStepStats.timeStepCount;
        double totalRoutingTime = 0.0d;
        double fSteadyState = 0.0d;
        if (((F_swmm5.Nobjects[LINK] == 0) || (timeStepStats.timeStepCount == 0.0d))) {
            return;
        }
        totalRoutingTime = (timeStepStats.steadyStateTime + timeStepStats.routingTime);
        if (R.dgt(totalRoutingTime, 0.0d)) {
            fSteadyState = ((100.0d * timeStepStats.steadyStateTime) / totalRoutingTime);
        }
        report_writeLine((L0));
        report_writeLine((L470));
        report_writeLine((L486));
        report_writeLine((L470));
        R.fprintf(F_swmm5.Frpt.file, L487, timeStepStats.minTimeStep);
        R.fprintf(F_swmm5.Frpt.file, L488, (timeStepStats.routingTime / timeStepCount));
        R.fprintf(F_swmm5.Frpt.file, L489, timeStepStats.maxTimeStep);
        R.fprintf(F_swmm5.Frpt.file, L490, (((((fSteadyState) <= (100.0d))) ? (fSteadyState) : (100.0d))));
        R.fprintf(F_swmm5.Frpt.file, L491, (timeStepStats.trialsCount / timeStepCount));
        R.fprintf(F_swmm5.Frpt.file, L492, ((100.0d * ((double) (F_swmm5.NonConvergeCount))) / timeStepCount));
        if (((F_swmm5.RouteModel == DW) && (R.dgt(F_swmm5.CourantFactor.v, 0.0d)))) {
            report_RouteStepFreq(timeStepStats);
        }
        report_writeLine((L0));
    }

    // report.c:1085
    static void report_RouteStepFreq(TTimeStepStats timeStepStats) {
        double totalSteps = 0.0d;
        int i = 0;
        L_f1: for (i = 1; (i < 6); i++) {
            totalSteps += timeStepStats.timeStepCounts[i];
        }
        if ((totalSteps == 0)) {
            return;
        }
        R.fprintf(F_swmm5.Frpt.file, L493);
        L_f2: for (i = 1; (i < 6); i++) {
            R.fprintf(F_swmm5.Frpt.file, L494, timeStepStats.timeStepIntervals[(i - 1)], timeStepStats.timeStepIntervals[i], ((100.0d * ((double) ((timeStepStats.timeStepCounts[i])))) / totalSteps));
        }
    }

    // report.c:1113
    static void report_writeReport() {
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        if ((F_swmm5.Nperiods == 0)) {
            return;
        }
        if (((F_swmm5.RptFlags.subcatchments != NONE) && ((((F_swmm5.IgnoreRainfall == 0) || (F_swmm5.IgnoreSnowmelt == 0)) || (F_swmm5.IgnoreGwater == 0))))) {
            report_Subcatchments();
        }
        if (((F_swmm5.IgnoreRouting == 1) && (F_swmm5.IgnoreQuality == 1))) {
            return;
        }
        if ((F_swmm5.RptFlags.nodes != NONE)) {
            report_Nodes();
        }
        if ((F_swmm5.RptFlags.links != NONE)) {
            report_Links();
        }
    }

    // report.c:1135
    static void report_Subcatchments() {
        int j = 0;
        int p = 0;
        int k = 0;
        int period = 0;
        DBox days = new DBox(0.0d);
        CPtr theDate = R.buf(12);
        CPtr theTime = R.buf(9);
        int hasSnowmelt = ((((F_swmm5.Nobjects[SNOWMELT] > 0) && (!(F_swmm5.IgnoreSnowmelt != 0)))) ? 1 : 0);
        int hasGwater = ((((F_swmm5.Nobjects[AQUIFER] > 0) && (!(F_swmm5.IgnoreGwater != 0)))) ? 1 : 0);
        int hasQuality = ((((F_swmm5.Nobjects[POLLUT] > 0) && (!(F_swmm5.IgnoreQuality != 0)))) ? 1 : 0);
        if ((F_swmm5.Nobjects[SUBCATCH] == 0)) {
            return;
        }
        report_writeLine((L0));
        report_writeLine((L479));
        report_writeLine((L495));
        report_writeLine((L479));
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[SUBCATCH]); j++) {
            k = (F_swmm5.Subcatch[j].rptFlag - 1);
            if ((k >= 0)) {
                report_SubcatchHeader(F_swmm5.Subcatch[j].ID);
                L_f2: for (period = 1; (period <= F_swmm5.Nperiods); period++) {
                    F_output.output_readDateTime(period, days);
                    F_datetime.datetime_dateToStr(days.v, theDate);
                    F_datetime.datetime_timeToStr(days.v, theTime);
                    F_output.output_readSubcatchResults(period, k);
                    R.fprintf(F_swmm5.Frpt.file, L496, theDate, theTime, F_output.SubcatchResults[SUBCATCH_RAINFALL], ((F_output.SubcatchResults[SUBCATCH_EVAP] / 24.0d) + F_output.SubcatchResults[SUBCATCH_INFIL]), F_output.SubcatchResults[SUBCATCH_RUNOFF]);
                    if ((hasSnowmelt != 0)) {
                        R.fprintf(F_swmm5.Frpt.file, L497, F_output.SubcatchResults[SUBCATCH_SNOWDEPTH]);
                    }
                    if ((hasGwater != 0)) {
                        R.fprintf(F_swmm5.Frpt.file, L498, F_output.SubcatchResults[SUBCATCH_GW_ELEV], F_output.SubcatchResults[SUBCATCH_GW_FLOW]);
                    }
                    if ((hasQuality != 0)) {
                        L_f3: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                            R.fprintf(F_swmm5.Frpt.file, L499, F_output.SubcatchResults[(SUBCATCH_WASHOFF + p)]);
                        }
                    }
                }
                report_writeLine((L0));
            }
        }
    }

    // report.c:1192
    static void report_SubcatchHeader(CPtr id) {
        int i = 0;
        int hasSnowmelt = ((((F_swmm5.Nobjects[SNOWMELT] > 0) && (!(F_swmm5.IgnoreSnowmelt != 0)))) ? 1 : 0);
        int hasGwater = ((((F_swmm5.Nobjects[AQUIFER] > 0) && (!(F_swmm5.IgnoreGwater != 0)))) ? 1 : 0);
        int hasQuality = ((((F_swmm5.Nobjects[POLLUT] > 0) && (!(F_swmm5.IgnoreQuality != 0)))) ? 1 : 0);
        report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L500, id);
        report_writeLine((L501));
        if ((hasSnowmelt > 0)) {
            R.fprintf(F_swmm5.Frpt.file, L502);
        }
        if ((hasGwater != 0)) {
            R.fprintf(F_swmm5.Frpt.file, L503);
            R.fprintf(F_swmm5.Frpt.file, L503);
        }
        if ((hasQuality != 0)) {
            L_f1: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L503);
            }
        }
        R.fprintf(F_swmm5.Frpt.file, L504);
        if ((hasSnowmelt != 0)) {
            R.fprintf(F_swmm5.Frpt.file, L505);
        }
        if ((hasGwater != 0)) {
            R.fprintf(F_swmm5.Frpt.file, L506);
        }
        if ((hasQuality != 0)) {
            L_f2: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L507, F_swmm5.Pollut[i].ID);
            }
        }
        if ((F_swmm5.UnitSystem == US)) {
            R.fprintf(F_swmm5.Frpt.file, L508, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L509, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        }
        if ((hasSnowmelt != 0)) {
            if ((F_swmm5.UnitSystem == US)) {
                R.fprintf(F_swmm5.Frpt.file, L510);
            } else {
                R.fprintf(F_swmm5.Frpt.file, L511);
            }
        }
        if ((hasGwater != 0)) {
            if ((F_swmm5.UnitSystem == US)) {
                R.fprintf(F_swmm5.Frpt.file, L512, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
            } else {
                R.fprintf(F_swmm5.Frpt.file, L513, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
            }
        }
        if ((hasQuality != 0)) {
            L_f3: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L507, F_keywords.QualUnitsWords[F_swmm5.Pollut[i].units]);
            }
        }
        report_writeLine((L501));
        if ((hasSnowmelt != 0)) {
            R.fprintf(F_swmm5.Frpt.file, L502);
        }
        if ((hasGwater != 0)) {
            R.fprintf(F_swmm5.Frpt.file, L503);
            R.fprintf(F_swmm5.Frpt.file, L503);
        }
        if ((hasQuality != 0)) {
            L_f4: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L503);
            }
        }
    }

    // report.c:1261
    static void report_Nodes() {
        int j = 0;
        int p = 0;
        int k = 0;
        int period = 0;
        DBox days = new DBox(0.0d);
        CPtr theDate = R.buf(12);
        CPtr theTime = R.buf(9);
        if ((F_swmm5.Nobjects[NODE] == 0)) {
            return;
        }
        report_writeLine((L0));
        report_writeLine((L514));
        report_writeLine((L515));
        report_writeLine((L514));
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[NODE]); j++) {
            k = (F_swmm5.Node_[j].rptFlag - 1);
            if ((k >= 0)) {
                report_NodeHeader(F_swmm5.Node_[j].ID);
                L_f2: for (period = 1; (period <= F_swmm5.Nperiods); period++) {
                    F_output.output_readDateTime(period, days);
                    F_datetime.datetime_dateToStr(days.v, theDate);
                    F_datetime.datetime_timeToStr(days.v, theTime);
                    F_output.output_readNodeResults(period, k);
                    R.fprintf(F_swmm5.Frpt.file, L516, theDate, theTime, F_output.NodeResults[NODE_INFLOW], F_output.NodeResults[NODE_OVERFLOW], F_output.NodeResults[NODE_DEPTH], F_output.NodeResults[NODE_HEAD]);
                    if (!(F_swmm5.IgnoreQuality != 0)) {
                        L_f3: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                            R.fprintf(F_swmm5.Frpt.file, L195, F_output.NodeResults[(NODE_QUAL + p)]);
                        }
                    }
                }
                report_writeLine((L0));
            }
        }
    }

    // report.c:1305
    static void report_NodeHeader(CPtr id) {
        int i = 0;
        CPtr lengthUnits = R.buf(9);
        report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L517, id);
        report_writeLine((L518));
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
            R.fprintf(F_swmm5.Frpt.file, L503);
        }
        R.fprintf(F_swmm5.Frpt.file, L519);
        if (!(F_swmm5.IgnoreQuality != 0)) {
            L_f2: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L507, F_swmm5.Pollut[i].ID);
            }
        }
        if ((F_swmm5.UnitSystem == US)) {
            Overrides.sstrncpy(lengthUnits, L520, 8);
        } else {
            Overrides.sstrncpy(lengthUnits, L521, 8);
        }
        R.fprintf(F_swmm5.Frpt.file, L522, F_keywords.FlowUnitWords[F_swmm5.FlowUnits], F_keywords.FlowUnitWords[F_swmm5.FlowUnits], lengthUnits, lengthUnits);
        if (!(F_swmm5.IgnoreQuality != 0)) {
            L_f3: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L507, F_keywords.QualUnitsWords[F_swmm5.Pollut[i].units]);
            }
        }
        report_writeLine((L518));
        if (!(F_swmm5.IgnoreQuality != 0)) {
            L_f4: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L503);
            }
        }
    }

    // report.c:1339
    static void report_Links() {
        int j = 0;
        int p = 0;
        int k = 0;
        int period = 0;
        DBox days = new DBox(0.0d);
        CPtr theDate = R.buf(12);
        CPtr theTime = R.buf(9);
        if ((F_swmm5.Nobjects[LINK] == 0)) {
            return;
        }
        report_writeLine((L0));
        report_writeLine((L514));
        report_writeLine((L523));
        report_writeLine((L514));
        L_f1: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            k = (F_swmm5.Link[j].rptFlag - 1);
            if ((k >= 0)) {
                report_LinkHeader(F_swmm5.Link[j].ID);
                L_f2: for (period = 1; (period <= F_swmm5.Nperiods); period++) {
                    F_output.output_readDateTime(period, days);
                    F_datetime.datetime_dateToStr(days.v, theDate);
                    F_datetime.datetime_timeToStr(days.v, theTime);
                    F_output.output_readLinkResults(period, k);
                    R.fprintf(F_swmm5.Frpt.file, L516, theDate, theTime, F_output.LinkResults[LINK_FLOW], F_output.LinkResults[LINK_VELOCITY], F_output.LinkResults[LINK_DEPTH], F_output.LinkResults[LINK_CAPACITY]);
                    if (!(F_swmm5.IgnoreQuality != 0)) {
                        L_f3: for (p = 0; (p < F_swmm5.Nobjects[POLLUT]); p++) {
                            R.fprintf(F_swmm5.Frpt.file, L195, F_output.LinkResults[(LINK_QUAL + p)]);
                        }
                    }
                }
                report_writeLine((L0));
            }
        }
    }

    // report.c:1383
    static void report_LinkHeader(CPtr id) {
        int i = 0;
        report_writeLine((L0));
        R.fprintf(F_swmm5.Frpt.file, L524, id);
        report_writeLine((L518));
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
            R.fprintf(F_swmm5.Frpt.file, L503);
        }
        R.fprintf(F_swmm5.Frpt.file, L525);
        if (!(F_swmm5.IgnoreQuality != 0)) {
            L_f2: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L507, F_swmm5.Pollut[i].ID);
            }
        }
        if ((F_swmm5.UnitSystem == US)) {
            R.fprintf(F_swmm5.Frpt.file, L526, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        } else {
            R.fprintf(F_swmm5.Frpt.file, L527, F_keywords.FlowUnitWords[F_swmm5.FlowUnits]);
        }
        if (!(F_swmm5.IgnoreQuality != 0)) {
            L_f3: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L528, F_keywords.QualUnitsWords[F_swmm5.Pollut[i].units]);
            }
        }
        report_writeLine((L518));
        if (!(F_swmm5.IgnoreQuality != 0)) {
            L_f4: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L503);
            }
        }
    }

    // report.c:1422
    static void report_writeErrorMsg(int code, CPtr s) {
        if ((F_swmm5.Frpt.file != null)) {
            report_writeLine((L0));
            R.fprintf(F_swmm5.Frpt.file, F_error.error_getMsg(code, F_swmm5.Msg), s);
        }
        F_swmm5.@ErrorCode = code;
        if (((F_swmm5.ErrorCode <= ERR_INPUT) || (F_swmm5.ErrorCode >= ERR_FILE_NAME))) {
            R.snprintf(F_swmm5.ErrorMsg, (int) (1024), F_error.error_getMsg(F_swmm5.ErrorCode, F_swmm5.Msg), s);
        }
    }

    // report.c:1446
    static void report_writeErrorCode() {
        if ((F_swmm5.Frpt.file != null)) {
            if ((((((F_swmm5.ErrorCode >= ERR_MEMORY) && (F_swmm5.ErrorCode <= ERR_TIMESTEP))) || (((F_swmm5.ErrorCode >= ERR_FILE_NAME) && (F_swmm5.ErrorCode <= ERR_OUT_FILE)))) || ((F_swmm5.ErrorCode == ERR_SYSTEM)))) {
                R.fprintf(F_swmm5.Frpt.file, L169, F_error.error_getMsg(F_swmm5.ErrorCode, F_swmm5.Msg));
            }
        }
    }

    // report.c:1464
    static void report_writeInputErrorMsg(int k, int sect, CPtr line, long lineCount) {
        if ((F_swmm5.Frpt.file != null)) {
            report_writeErrorMsg(k, F_error.ErrString);
            if ((sect < 0)) {
                R.fprintf(F_swmm5.Frpt.file, L529, lineCount);
            } else {
                R.fprintf(F_swmm5.Frpt.file, L530, lineCount, F_keywords.SectWords[sect]);
            }
            R.fprintf(F_swmm5.Frpt.file, L356, line);
        }
    }

    // report.c:1485
    static void report_writeWarningMsg(CPtr msg, CPtr id) {
        R.fprintf(F_swmm5.Frpt.file, L531, msg, id);
        F_swmm5.@Warnings = F_swmm5.Warnings + 1;
    }

    // report.c:1499
    static void report_writeTseriesErrorMsg(int code, TTable tseries) {
        CPtr theDate = R.buf(20);
        CPtr theTime = R.buf(20);
        double x = 0.0d;
        if ((code == ERR_CURVE_SEQUENCE)) {
            x = tseries.x2;
            F_datetime.datetime_dateToStr(x, theDate);
            F_datetime.datetime_timeToStr(x, theTime);
            report_writeErrorMsg(ERR_TIMESERIES_SEQUENCE, tseries.ID);
            R.fprintf(F_swmm5.Frpt.file, L532, theDate, theTime);
        } else {
            report_writeErrorMsg(code, tseries.ID);
        }
    }

}
