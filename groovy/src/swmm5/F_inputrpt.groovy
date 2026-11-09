// Generated from EPA SWMM 5.2.4 (inputrpt.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_inputrpt {
    // inputrpt.c:27
    static void inputrpt_writeInput() {
        int m = 0;
        int i = 0;
        int k = 0;
        int lidCount = 0;
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        F_report.report_writeLine((L0));
        F_report.report_writeLine((L206));
        F_report.report_writeLine((L207));
        F_report.report_writeLine((L206));
        R.fprintf(F_swmm5.Frpt.file, L208, F_swmm5.Nobjects[GAGE]);
        R.fprintf(F_swmm5.Frpt.file, L209, F_swmm5.Nobjects[SUBCATCH]);
        R.fprintf(F_swmm5.Frpt.file, L210, F_swmm5.Nobjects[NODE]);
        R.fprintf(F_swmm5.Frpt.file, L211, F_swmm5.Nobjects[LINK]);
        R.fprintf(F_swmm5.Frpt.file, L212, F_swmm5.Nobjects[POLLUT]);
        R.fprintf(F_swmm5.Frpt.file, L213, F_swmm5.Nobjects[LANDUSE]);
        if ((F_swmm5.Nobjects[POLLUT] > 0)) {
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L214));
            F_report.report_writeLine((L215));
            F_report.report_writeLine((L214));
            R.fprintf(F_swmm5.Frpt.file, L216);
            R.fprintf(F_swmm5.Frpt.file, L217);
            R.fprintf(F_swmm5.Frpt.file, L218);
            L_f1: for (i = 0; (i < F_swmm5.Nobjects[POLLUT]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L219, F_swmm5.Pollut[i].ID, F_keywords.QualUnitsWords[F_swmm5.Pollut[i].units], F_swmm5.Pollut[i].pptConcen, F_swmm5.Pollut[i].gwConcen, (F_swmm5.Pollut[i].kDecay * 86400));
                if ((F_swmm5.Pollut[i].coPollut >= 0)) {
                    R.fprintf(F_swmm5.Frpt.file, L220, F_swmm5.Pollut[F_swmm5.Pollut[i].coPollut].ID, F_swmm5.Pollut[i].coFraction);
                }
            }
        }
        if ((F_swmm5.Nobjects[LANDUSE] > 0)) {
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L221));
            F_report.report_writeLine((L222));
            F_report.report_writeLine((L221));
            R.fprintf(F_swmm5.Frpt.file, L223);
            R.fprintf(F_swmm5.Frpt.file, L224);
            R.fprintf(F_swmm5.Frpt.file, L225);
            L_f2: for (i = 0; (i < F_swmm5.Nobjects[LANDUSE]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L226, F_swmm5.Landuse[i].ID, F_swmm5.Landuse[i].sweepInterval, F_swmm5.Landuse[i].sweepRemoval, F_swmm5.Landuse[i].sweepDays0);
            }
        }
        if ((F_swmm5.Nobjects[GAGE] > 0)) {
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L227));
            F_report.report_writeLine((L228));
            F_report.report_writeLine((L227));
            R.fprintf(F_swmm5.Frpt.file, L229);
            R.fprintf(F_swmm5.Frpt.file, L230);
            R.fprintf(F_swmm5.Frpt.file, L231);
            L_f3: for (i = 0; (i < F_swmm5.Nobjects[GAGE]); i++) {
                if ((F_swmm5.Gage[i].tSeries >= 0)) {
                    R.fprintf(F_swmm5.Frpt.file, L232, F_swmm5.Gage[i].ID, F_swmm5.Tseries[F_swmm5.Gage[i].tSeries].ID);
                    R.fprintf(F_swmm5.Frpt.file, L233, F_keywords.RainTypeWords[F_swmm5.Gage[i].rainType], R.idiv((F_swmm5.Gage[i].rainInterval), 60));
                } else {
                    R.fprintf(F_swmm5.Frpt.file, L234, F_swmm5.Gage[i].ID, F_swmm5.Gage[i].fname);
                }
            }
        }
        if ((F_swmm5.Nobjects[SUBCATCH] > 0)) {
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L235));
            F_report.report_writeLine((L236));
            F_report.report_writeLine((L235));
            R.fprintf(F_swmm5.Frpt.file, L237);
            R.fprintf(F_swmm5.Frpt.file, L238);
            L_f4: for (i = 0; (i < F_swmm5.Nobjects[SUBCATCH]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L239, F_swmm5.Subcatch[i].ID, (F_swmm5.Subcatch[i].area * F_swmm5.UCF(LANDAREA)), (F_swmm5.Subcatch[i].width * F_swmm5.UCF(LENGTH)), (F_swmm5.Subcatch[i].fracImperv * 100.0d), (F_swmm5.Subcatch[i].slope * 100.0d), F_swmm5.Gage[F_swmm5.Subcatch[i].gage].ID);
                if ((F_swmm5.Subcatch[i].outNode >= 0)) {
                    R.fprintf(F_swmm5.Frpt.file, L240, F_swmm5.Node_[F_swmm5.Subcatch[i].outNode].ID);
                } else if ((F_swmm5.Subcatch[i].outSubcatch >= 0)) {
                    R.fprintf(F_swmm5.Frpt.file, L240, F_swmm5.Subcatch[F_swmm5.Subcatch[i].outSubcatch].ID);
                }
                if ((F_swmm5.Subcatch[i].lidArea != 0.0d)) {
                    lidCount++;
                }
            }
        }
        if ((lidCount > 0)) {
            F_lid.lid_writeSummary();
        }
        if ((F_swmm5.Nobjects[NODE] > 0)) {
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L241));
            F_report.report_writeLine((L242));
            F_report.report_writeLine((L241));
            R.fprintf(F_swmm5.Frpt.file, L243);
            R.fprintf(F_swmm5.Frpt.file, L244);
            R.fprintf(F_swmm5.Frpt.file, L245);
            L_f5: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L246, F_swmm5.Node_[i].ID, F_keywords.NodeTypeWords[(F_swmm5.Node_[i].type - JUNCTION)], (F_swmm5.Node_[i].invertElev * F_swmm5.UCF(LENGTH)), (F_swmm5.Node_[i].fullDepth * F_swmm5.UCF(LENGTH)), ((F_swmm5.Node_[i].pondedArea * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LENGTH)));
                if ((((F_swmm5.Node_[i].extInflow != null) || (F_swmm5.Node_[i].dwfInflow != null)) || (F_swmm5.Node_[i].rdiiInflow != null))) {
                    R.fprintf(F_swmm5.Frpt.file, L247);
                }
            }
        }
        if ((F_swmm5.Nobjects[LINK] > 0)) {
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L241));
            F_report.report_writeLine((L248));
            F_report.report_writeLine((L241));
            R.fprintf(F_swmm5.Frpt.file, L249);
            R.fprintf(F_swmm5.Frpt.file, L250);
            L_f6: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
                if ((F_swmm5.Link[i].direction == 1)) {
                    R.fprintf(F_swmm5.Frpt.file, L251, F_swmm5.Link[i].ID, F_swmm5.Node_[F_swmm5.Link[i].node1].ID, F_swmm5.Node_[F_swmm5.Link[i].node2].ID);
                } else {
                    R.fprintf(F_swmm5.Frpt.file, L251, F_swmm5.Link[i].ID, F_swmm5.Node_[F_swmm5.Link[i].node2].ID, F_swmm5.Node_[F_swmm5.Link[i].node1].ID);
                }
                if ((F_swmm5.Link[i].type == PUMP)) {
                    k = F_swmm5.Link[i].subIndex;
                    R.fprintf(F_swmm5.Frpt.file, L252, F_keywords.PumpTypeWords[F_swmm5.Pump[k].type]);
                } else {
                    R.fprintf(F_swmm5.Frpt.file, L253, F_keywords.LinkTypeWords[(F_swmm5.Link[i].type - CONDUIT)]);
                }
                if ((F_swmm5.Link[i].type == CONDUIT)) {
                    k = F_swmm5.Link[i].subIndex;
                    R.fprintf(F_swmm5.Frpt.file, L254, (F_swmm5.Conduit[k].length * F_swmm5.UCF(LENGTH)), ((F_swmm5.Conduit[k].slope * 100.0d) * F_swmm5.Link[i].direction), F_swmm5.Conduit[k].roughness);
                }
            }
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L255));
            F_report.report_writeLine((L256));
            F_report.report_writeLine((L255));
            R.fprintf(F_swmm5.Frpt.file, L257);
            R.fprintf(F_swmm5.Frpt.file, L258);
            R.fprintf(F_swmm5.Frpt.file, L259);
            L_f7: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
                if ((F_swmm5.Link[i].type == CONDUIT)) {
                    k = F_swmm5.Link[i].subIndex;
                    R.fprintf(F_swmm5.Frpt.file, L260, F_swmm5.Link[i].ID);
                    if ((F_swmm5.Link[i].xsect.type == CUSTOM)) {
                        R.fprintf(F_swmm5.Frpt.file, L261, F_swmm5.Curve[F_swmm5.Link[i].xsect.transect].ID);
                    } else if ((F_swmm5.Link[i].xsect.type == IRREGULAR)) {
                        R.fprintf(F_swmm5.Frpt.file, L261, F_swmm5.Transect[F_swmm5.Link[i].xsect.transect].ID);
                    } else if ((F_swmm5.Link[i].xsect.type == STREET_XSECT)) {
                        R.fprintf(F_swmm5.Frpt.file, L261, F_swmm5.Street[F_swmm5.Link[i].xsect.transect].ID);
                    } else {
                        R.fprintf(F_swmm5.Frpt.file, L261, F_keywords.XsectTypeWords[F_swmm5.Link[i].xsect.type]);
                    }
                    R.fprintf(F_swmm5.Frpt.file, L262, (F_swmm5.Link[i].xsect.yFull * F_swmm5.UCF(LENGTH)), ((F_swmm5.Link[i].xsect.aFull * F_swmm5.UCF(LENGTH)) * F_swmm5.UCF(LENGTH)), (F_swmm5.Link[i].xsect.rFull * F_swmm5.UCF(LENGTH)), (F_swmm5.Link[i].xsect.wMax * F_swmm5.UCF(LENGTH)), F_swmm5.Conduit[k].barrels, (F_swmm5.Link[i].qFull * F_swmm5.UCF(FLOW)));
                }
            }
        }
        if ((F_swmm5.Nobjects[SHAPE] > 0)) {
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L206));
            F_report.report_writeLine((L263));
            F_report.report_writeLine((L206));
            L_f8: for (i = 0; (i < F_swmm5.Nobjects[SHAPE]); i++) {
                k = F_swmm5.Shape[i].curve;
                R.fprintf(F_swmm5.Frpt.file, L264, F_swmm5.Curve[k].ID);
                R.fprintf(F_swmm5.Frpt.file, L265);
                L_f9: for (m = 1; (m < 51); m++) {
                    if (((m % 5) == 1)) {
                        R.fprintf(F_swmm5.Frpt.file, L266);
                    }
                    R.fprintf(F_swmm5.Frpt.file, L267, F_swmm5.Shape[i].areaTbl[m]);
                }
                R.fprintf(F_swmm5.Frpt.file, L268);
                L_f10: for (m = 1; (m < 51); m++) {
                    if (((m % 5) == 1)) {
                        R.fprintf(F_swmm5.Frpt.file, L266);
                    }
                    R.fprintf(F_swmm5.Frpt.file, L267, F_swmm5.Shape[i].hradTbl[m]);
                }
                R.fprintf(F_swmm5.Frpt.file, L269);
                L_f11: for (m = 1; (m < 51); m++) {
                    if (((m % 5) == 1)) {
                        R.fprintf(F_swmm5.Frpt.file, L266);
                    }
                    R.fprintf(F_swmm5.Frpt.file, L267, F_swmm5.Shape[i].widthTbl[m]);
                }
            }
        }
        if ((F_swmm5.Nobjects[TRANSECT] > 0)) {
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L227));
            F_report.report_writeLine((L270));
            F_report.report_writeLine((L227));
            L_f12: for (i = 0; (i < F_swmm5.Nobjects[TRANSECT]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L271, F_swmm5.Transect[i].ID);
                R.fprintf(F_swmm5.Frpt.file, L265);
                L_f13: for (m = 1; (m < 51); m++) {
                    if (((m % 5) == 1)) {
                        R.fprintf(F_swmm5.Frpt.file, L266);
                    }
                    R.fprintf(F_swmm5.Frpt.file, L267, F_swmm5.Transect[i].areaTbl[m]);
                }
                R.fprintf(F_swmm5.Frpt.file, L268);
                L_f14: for (m = 1; (m < 51); m++) {
                    if (((m % 5) == 1)) {
                        R.fprintf(F_swmm5.Frpt.file, L266);
                    }
                    R.fprintf(F_swmm5.Frpt.file, L267, F_swmm5.Transect[i].hradTbl[m]);
                }
                R.fprintf(F_swmm5.Frpt.file, L269);
                L_f15: for (m = 1; (m < 51); m++) {
                    if (((m % 5) == 1)) {
                        R.fprintf(F_swmm5.Frpt.file, L266);
                    }
                    R.fprintf(F_swmm5.Frpt.file, L267, F_swmm5.Transect[i].widthTbl[m]);
                }
            }
        }
        if ((F_swmm5.Nobjects[STREET] > 0)) {
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L0));
            F_report.report_writeLine((L272));
            F_report.report_writeLine((L273));
            F_report.report_writeLine((L272));
            L_f16: for (i = 0; (i < F_swmm5.Nobjects[STREET]); i++) {
                R.fprintf(F_swmm5.Frpt.file, L274, F_swmm5.Street[i].ID);
                R.fprintf(F_swmm5.Frpt.file, L265);
                L_f17: for (m = 1; (m < F_swmm5.Street[i].transect.nTbl); m++) {
                    if (((m % 5) == 1)) {
                        R.fprintf(F_swmm5.Frpt.file, L266);
                    }
                    R.fprintf(F_swmm5.Frpt.file, L267, F_swmm5.Street[i].transect.areaTbl[m]);
                }
                R.fprintf(F_swmm5.Frpt.file, L268);
                L_f18: for (m = 1; (m < F_swmm5.Street[i].transect.nTbl); m++) {
                    if (((m % 5) == 1)) {
                        R.fprintf(F_swmm5.Frpt.file, L266);
                    }
                    R.fprintf(F_swmm5.Frpt.file, L267, F_swmm5.Street[i].transect.hradTbl[m]);
                }
                R.fprintf(F_swmm5.Frpt.file, L269);
                L_f19: for (m = 1; (m < F_swmm5.Street[i].transect.nTbl); m++) {
                    if (((m % 5) == 1)) {
                        R.fprintf(F_swmm5.Frpt.file, L266);
                    }
                    R.fprintf(F_swmm5.Frpt.file, L267, F_swmm5.Street[i].transect.widthTbl[m]);
                }
            }
        }
        F_report.report_writeLine((L0));
    }

}
