// Generated from EPA SWMM 5.2.4 (hotstart.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_hotstart {
    public static int fileVersion = 0;

    // hotstart.c:72
    static int hotstart_open() {
        if (!(openHotstartFile1() != 0)) {
            return 0;
        }
        if (!(openHotstartFile2() != 0)) {
            return 0;
        }
        return 1;
    }

    // hotstart.c:82
    static void hotstart_close() {
        if ((F_swmm5.Fhotstart2.file != null)) {
            saveRunoff();
            saveRouting();
            R.fclose(F_swmm5.Fhotstart2.file);
        }
    }

    // hotstart.c:94
    static int openHotstartFile1() {
        IBox nSubcatch = new IBox(0);
        IBox nNodes = new IBox(0);
        IBox nLinks = new IBox(0);
        IBox nPollut = new IBox(0);
        IBox nLandUses = new IBox(0);
        IBox flowUnits = new IBox(0);
        CPtr fStamp = R.bufFrom(L160, 15);
        CPtr fileStamp = R.bufFrom(L160, 15);
        CPtr fStampx = R.bufFrom(L161, 16);
        CPtr fileStamp2 = R.bufFrom(L162, 16);
        CPtr fileStamp3 = R.bufFrom(L163, 16);
        CPtr fileStamp4 = R.bufFrom(L164, 16);
        if ((F_swmm5.Fhotstart1.mode != USE_FILE)) {
            return 1;
        }
        if ((((F_swmm5.Fhotstart1.@file = R.fopen(F_swmm5.Fhotstart1.name, L165))) == null)) {
            F_report.report_writeErrorMsg(ERR_HOTSTART_FILE_OPEN, F_swmm5.Fhotstart1.name);
            return 0;
        }
        R.fread(fStampx, 0, (int) (R.strlen(fileStamp2)), F_swmm5.Fhotstart1.file);
        if ((R.strcmp(fStampx, fileStamp4) == 0)) {
            fileVersion = 4;
        } else if ((R.strcmp(fStampx, fileStamp3) == 0)) {
            fileVersion = 3;
        } else if ((R.strcmp(fStampx, fileStamp2) == 0)) {
            fileVersion = 2;
        } else {
            R.rewind(F_swmm5.Fhotstart1.file);
            R.fread(fStamp, 0, (int) (R.strlen(fileStamp)), F_swmm5.Fhotstart1.file);
            if ((R.strcmp(fStamp, fileStamp) != 0)) {
                F_report.report_writeErrorMsg(ERR_HOTSTART_FILE_FORMAT, L0);
                return 0;
            }
            fileVersion = 1;
        }
        nSubcatch.@v = (-1);
        nNodes.@v = (-1);
        nLinks.@v = (-1);
        nPollut.@v = (-1);
        nLandUses.@v = (-1);
        flowUnits.@v = (-1);
        if ((fileVersion >= 2)) {
            R.fread1(nSubcatch, 4, F_swmm5.Fhotstart1.file);
        } else {
            nSubcatch.@v = F_swmm5.Nobjects[SUBCATCH];
        }
        if ((fileVersion >= 3)) {
            R.fread1(nLandUses, 4, F_swmm5.Fhotstart1.file);
        } else {
            nLandUses.@v = F_swmm5.Nobjects[LANDUSE];
        }
        R.fread1(nNodes, 4, F_swmm5.Fhotstart1.file);
        R.fread1(nLinks, 4, F_swmm5.Fhotstart1.file);
        R.fread1(nPollut, 4, F_swmm5.Fhotstart1.file);
        R.fread1(flowUnits, 4, F_swmm5.Fhotstart1.file);
        if (((((((nSubcatch.v != F_swmm5.Nobjects[SUBCATCH]) || (nLandUses.v != F_swmm5.Nobjects[LANDUSE])) || (nNodes.v != F_swmm5.Nobjects[NODE])) || (nLinks.v != F_swmm5.Nobjects[LINK])) || (nPollut.v != F_swmm5.Nobjects[POLLUT])) || (flowUnits.v != F_swmm5.FlowUnits))) {
            F_report.report_writeErrorMsg(ERR_HOTSTART_FILE_FORMAT, L0);
            return 0;
        }
        if ((fileVersion >= 3)) {
            readRunoff();
        }
        readRouting();
        R.fclose(F_swmm5.Fhotstart1.file);
        if ((F_swmm5.ErrorCode != 0)) {
            return 0;
        } else {
            return 1;
        }
        throw new IllegalStateException('openHotstartFile1: no return');
    }

    // hotstart.c:180
    static int openHotstartFile2() {
        IBox nSubcatch = new IBox(0);
        IBox nLandUses = new IBox(0);
        IBox nNodes = new IBox(0);
        IBox nLinks = new IBox(0);
        IBox nPollut = new IBox(0);
        IBox flowUnits = new IBox(0);
        CPtr fileStamp = R.bufFrom(L164, 16);
        if ((F_swmm5.Fhotstart2.mode != SAVE_FILE)) {
            return 1;
        }
        if ((((F_swmm5.Fhotstart2.@file = R.fopen(F_swmm5.Fhotstart2.name, L166))) == null)) {
            F_report.report_writeErrorMsg(ERR_HOTSTART_FILE_OPEN, F_swmm5.Fhotstart2.name);
            return 0;
        }
        nSubcatch.@v = F_swmm5.Nobjects[SUBCATCH];
        nLandUses.@v = F_swmm5.Nobjects[LANDUSE];
        nNodes.@v = F_swmm5.Nobjects[NODE];
        nLinks.@v = F_swmm5.Nobjects[LINK];
        nPollut.@v = F_swmm5.Nobjects[POLLUT];
        flowUnits.@v = F_swmm5.FlowUnits;
        R.fwrite(fileStamp, 0, (int) (R.strlen(fileStamp)), F_swmm5.Fhotstart2.file);
        R.fwrite1(nSubcatch.v, 4, F_swmm5.Fhotstart2.file);
        R.fwrite1(nLandUses.v, 4, F_swmm5.Fhotstart2.file);
        R.fwrite1(nNodes.v, 4, F_swmm5.Fhotstart2.file);
        R.fwrite1(nLinks.v, 4, F_swmm5.Fhotstart2.file);
        R.fwrite1(nPollut.v, 4, F_swmm5.Fhotstart2.file);
        R.fwrite1(flowUnits.v, 4, F_swmm5.Fhotstart2.file);
        return 1;
    }

    // hotstart.c:222
    static void saveRouting() {
        int i = 0;
        int j = 0;
        float[] x = new float[3];
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            x[0] = ((float) (F_swmm5.Node_[i].newDepth));
            x[1] = ((float) (F_swmm5.Node_[i].newLatFlow));
            R.fwrite(x, 0, (int) (2), F_swmm5.Fhotstart2.file);
            if ((F_swmm5.Node_[i].type == STORAGE)) {
                j = F_swmm5.Node_[i].subIndex;
                x[0] = ((float) (F_swmm5.Storage[j].hrt));
                R.fwrite(x, 0, (int) (1), F_swmm5.Fhotstart2.file);
            }
            L_f2: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
                x[0] = ((float) (F_swmm5.Node_[i].newQual[j]));
                R.fwrite(x, 0, (int) (1), F_swmm5.Fhotstart2.file);
            }
        }
        L_f3: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            x[0] = ((float) (F_swmm5.Link[i].newFlow));
            x[1] = ((float) (F_swmm5.Link[i].newDepth));
            x[2] = ((float) (F_swmm5.Link[i].setting));
            R.fwrite(x, 0, (int) (3), F_swmm5.Fhotstart2.file);
            L_f4: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
                x[0] = ((float) (F_swmm5.Link[i].newQual[j]));
                R.fwrite(x, 0, (int) (1), F_swmm5.Fhotstart2.file);
            }
        }
    }

    // hotstart.c:267
    static void readRouting() {
        int i = 0;
        int j = 0;
        FBox x = new FBox(0.0f);
        double[] xgw = new double[4];
        CFile f = F_swmm5.Fhotstart1.file;
        if ((fileVersion == 2)) {
            xgw[2] = 0.0d;
            xgw[3] = (-10000000000.0d);
            L_f1: for (i = 0; (i < F_swmm5.Nobjects[SUBCATCH]); i++) {
                if (!(readFloat(x, f) != 0)) {
                    return;
                }
                xgw[0] = x.v;
                if (!(readFloat(x, f) != 0)) {
                    return;
                }
                xgw[1] = x.v;
                if ((F_swmm5.Subcatch[i].groundwater != null)) {
                    F_gwater.gwater_setState(i, xgw);
                }
            }
        }
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if (!(readFloat(x, f) != 0)) {
                return;
            }
            F_swmm5.Node_[i].@newDepth = x.v;
            if (!(readFloat(x, f) != 0)) {
                return;
            }
            F_swmm5.Node_[i].@newLatFlow = x.v;
            if (((fileVersion >= 4) && (F_swmm5.Node_[i].type == STORAGE))) {
                if (!(readFloat(x, f) != 0)) {
                    return;
                }
                j = F_swmm5.Node_[i].subIndex;
                F_swmm5.Storage[j].@hrt = x.v;
            }
            L_f3: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
                if (!(readFloat(x, f) != 0)) {
                    return;
                }
                F_swmm5.Node_[i].newQual[j] = x.v;
            }
            if ((fileVersion <= 2)) {
                L_f4: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
                    if (!(readFloat(x, f) != 0)) {
                        return;
                    }
                }
            }
        }
        L_f5: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if (!(readFloat(x, f) != 0)) {
                return;
            }
            F_swmm5.Link[i].@newFlow = x.v;
            if (!(readFloat(x, f) != 0)) {
                return;
            }
            F_swmm5.Link[i].@newDepth = x.v;
            if (!(readFloat(x, f) != 0)) {
                return;
            }
            F_swmm5.Link[i].@setting = x.v;
            F_swmm5.Link[i].@targetSetting = x.v;
            F_link.link_setTargetSetting(i);
            F_link.link_setSetting(i, 0.0d);
            L_f6: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
                if (!(readFloat(x, f) != 0)) {
                    return;
                }
                F_swmm5.Link[i].newQual[j] = x.v;
            }
        }
    }

    // hotstart.c:356
    static void saveRunoff() {
        int i = 0;
        int j = 0;
        int k = 0;
        double[] x = new double[6];
        CFile f = F_swmm5.Fhotstart2.file;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[SUBCATCH]); i++) {
            L_f2: for (j = 0; (j < 3); j++) {
                x[j] = F_swmm5.Subcatch[i].subArea[j].depth;
            }
            x[3] = F_swmm5.Subcatch[i].newRunoff;
            R.fwrite(x, 0, (int) (4), f);
            L_f3: for (j = 0; (j < 6); j++) {
                x[j] = 0.0d;
            }
            F_infil.infil_getState(i, x);
            R.fwrite(x, 0, (int) (6), f);
            if ((F_swmm5.Subcatch[i].groundwater != null)) {
                F_gwater.gwater_getState(i, x);
                R.fwrite(x, 0, (int) (4), f);
            }
            if ((F_swmm5.Subcatch[i].snowpack != null)) {
                L_f4: for (j = 0; (j < 3); j++) {
                    F_snow.snow_getState(i, j, x);
                    R.fwrite(x, 0, (int) (5), f);
                }
            }
            if ((F_swmm5.Nobjects[POLLUT] > 0)) {
                L_f5: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
                    x[0] = F_swmm5.Subcatch[i].newQual[j];
                    R.fwrite(x, 0, (int) (1), f);
                }
                L_f6: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
                    x[0] = F_swmm5.Subcatch[i].pondedQual[j];
                    R.fwrite(x, 0, (int) (1), f);
                }
                L_f7: for (k = 0; (k < F_swmm5.Nobjects[LANDUSE]); k++) {
                    L_f8: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
                        x[0] = F_swmm5.Subcatch[i].landFactor[k].buildup[j];
                        R.fwrite(x, 0, (int) (F_swmm5.Nobjects[POLLUT]), f);
                    }
                    x[0] = F_swmm5.Subcatch[i].landFactor[k].lastSwept;
                    R.fwrite(x, 0, (int) (1), f);
                }
            }
        }
    }

    // hotstart.c:430
    static void readRunoff() {
        int i = 0;
        int j = 0;
        int k = 0;
        double[] x = new double[6];
        CFile f = F_swmm5.Fhotstart1.file;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[SUBCATCH]); i++) {
            L_f2: for (j = 0; (j < 3); j++) {
                if (!(readDouble(new DField<TSubarea>(F_swmm5.Subcatch[i].subArea[j], (TSubarea __o) -> __o.depth, (TSubarea __o, double __x) -> { __o.@depth = __x }), f) != 0)) {
                    return;
                }
            }
            if (!(readDouble(new DField<TSubcatch>(F_swmm5.Subcatch[i], (TSubcatch __o) -> __o.newRunoff, (TSubcatch __o, double __x) -> { __o.@newRunoff = __x }), f) != 0)) {
                return;
            }
            L_f3: for (j = 0; (j < 6); j++) {
                if (!(readDouble(new DElem(x, j), f) != 0)) {
                    return;
                }
            }
            F_infil.infil_setState(i, x);
            if ((F_swmm5.Subcatch[i].groundwater != null)) {
                L_f4: for (j = 0; (j < 4); j++) {
                    if (!(readDouble(new DElem(x, j), f) != 0)) {
                        return;
                    }
                }
                F_gwater.gwater_setState(i, x);
            }
            if ((F_swmm5.Subcatch[i].snowpack != null)) {
                L_f5: for (j = 0; (j < 3); j++) {
                    L_f6: for (k = 0; (k < 5); k++) {
                        if (!(readDouble(new DElem(x, k), f) != 0)) {
                            return;
                        }
                    }
                    F_snow.snow_setState(i, j, x);
                }
            }
            if ((F_swmm5.Nobjects[POLLUT] > 0)) {
                L_f7: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
                    if (!(readDouble(new DElem(F_swmm5.Subcatch[i].newQual, j), f) != 0)) {
                        return;
                    }
                }
                L_f8: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
                    if (!(readDouble(new DElem(F_swmm5.Subcatch[i].pondedQual, j), f) != 0)) {
                        return;
                    }
                }
                L_f9: for (k = 0; (k < F_swmm5.Nobjects[LANDUSE]); k++) {
                    L_f10: for (j = 0; (j < F_swmm5.Nobjects[POLLUT]); j++) {
                        if (!(readDouble(new DElem(F_swmm5.Subcatch[i].landFactor[k].buildup, j), f) != 0)) {
                            return;
                        }
                    }
                    if (!(readDouble(new DField<TLandFactor>(F_swmm5.Subcatch[i].landFactor[k], (TLandFactor __o) -> __o.lastSwept, (TLandFactor __o, double __x) -> { __o.@lastSwept = __x }), f) != 0)) {
                        return;
                    }
                }
            }
        }
    }

    // hotstart.c:499
    static int readFloat(FRef x, CFile f) {
        R.fread1(x, 4, f);
        if (((x).get() != (x).get())) {
            F_report.report_writeErrorMsg(ERR_HOTSTART_FILE_READ, L0);
            (x).set((float) (0.0d));
            return 0;
        }
        return 1;
    }

    // hotstart.c:521
    static int readDouble(DRef x, CFile f) {
        if ((R.feof(f) != 0)) {
            (x).set(0.0d);
            F_report.report_writeErrorMsg(ERR_HOTSTART_FILE_READ, L0);
            return 0;
        }
        R.fread1(x, 8, f);
        if (((x).get() != (x).get())) {
            (x).set(0.0d);
            return 0;
        }
        return 1;
    }

}
