// Generated from EPA SWMM 5.2.4 (input.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_input {
    public static int MAXERRS = 100;
    public static CPtr[] Tok = new CPtr[40];
    public static int Ntokens = 0;
    public static int[] Mobjects = new int[18];
    public static int[] Mnodes = new int[5];
    public static int[] Mlinks = new int[5];
    public static int Mevents = 0;

    // input.c:69
    static int input_countObjects() {
        CPtr line = R.buf(1025);
        CPtr wLine = R.buf(1025);
        CPtr tok = null;
        int sect = (-1);
        int newsect = 0;
        int errcode = 0;
        int errsum = 0;
        int i = 0;
        long lineCount = 0;
        if ((F_swmm5.ErrorCode != 0)) {
            return F_swmm5.ErrorCode;
        }
        F_error.error_setInpError(0, L0);
        L_f1: for (i = 0; (i < MAX_OBJ_TYPES); i++) {
            F_swmm5.Nobjects[i] = 0;
        }
        L_f2: for (i = 0; (i < 5); i++) {
            F_swmm5.Nnodes[i] = 0;
        }
        L_f3: for (i = 0; (i < 5); i++) {
            F_swmm5.Nlinks[i] = 0;
        }
        F_controls.controls_init();
        L_w4: while (((R.fgets(line, (int) (1024), F_swmm5.Finp.file)) != null)) {
            lineCount++;
            Overrides.sstrncpy(wLine, line, 1024);
            tok = R.strtok(wLine, L184);
            if ((tok == null)) {
                continue L_w4;
            }
            if ((tok.g(0) == 59)) {
                continue L_w4;
            }
            if ((tok.g(0) == 91)) {
                newsect = findmatch(tok, F_keywords.SectWords);
                if ((newsect >= 0)) {
                    sect = newsect;
                    continue L_w4;
                } else {
                    sect = (-1);
                    errcode = ERR_KEYWORD;
                }
            }
            if ((sect == s_OPTION)) {
                errcode = readOption(line);
            } else if ((sect >= 0)) {
                errcode = addObject(sect, tok);
            }
            if ((errcode != 0)) {
                F_report.report_writeInputErrorMsg(errcode, sect, line, lineCount);
                errsum++;
                if ((errsum >= MAXERRS)) {
                    break L_w4;
                }
            }
        }
        if ((errsum > 0)) {
            F_swmm5.@ErrorCode = ERR_INPUT;
        }
        return F_swmm5.ErrorCode;
    }

    // input.c:141
    static int input_readData() {
        CPtr line = R.buf(1025);
        CPtr wLine = R.buf(1025);
        CPtr comment = null;
        int sect = 0;
        int newsect = 0;
        int inperr = 0;
        int errsum = 0;
        int lineLength = 0;
        int i = 0;
        long lineCount = 0;
        if ((F_swmm5.ErrorCode != 0)) {
            return F_swmm5.ErrorCode;
        }
        F_error.error_setInpError(0, L0);
        L_f1: for (i = 0; (i < MAX_OBJ_TYPES); i++) {
            Mobjects[i] = 0;
        }
        L_f2: for (i = 0; (i < 5); i++) {
            Mnodes[i] = 0;
        }
        L_f3: for (i = 0; (i < 5); i++) {
            Mlinks[i] = 0;
        }
        Mevents = 0;
        L_f4: for (i = 0; (i < F_swmm5.Nobjects[TSERIES]); i++) {
            F_swmm5.Tseries[i].@lastDate = (F_swmm5.StartDate.v + F_swmm5.StartTime.v);
        }
        sect = 0;
        errsum = 0;
        R.rewind(F_swmm5.Finp.file);
        L_w5: while (((R.fgets(line, (int) (1024), F_swmm5.Finp.file)) != null)) {
            lineCount++;
            Overrides.sstrncpy(wLine, line, 1024);
            Ntokens = getTokens(wLine);
            if ((Ntokens == 0)) {
                continue L_w5;
            }
            if ((Tok[0].g(0) == 59)) {
                continue L_w5;
            }
            lineLength = (int) (R.strlen(line));
            if ((lineLength >= 1024)) {
                comment = R.strchr(line, 59);
                if ((comment != null)) {
                    lineLength = (int) ((((long) (comment.o - line.o))));
                }
                if ((lineLength >= 1024)) {
                    inperr = ERR_LINE_LENGTH;
                    F_report.report_writeInputErrorMsg(inperr, sect, line, lineCount);
                    errsum++;
                }
            }
            if ((Tok[0].g(0) == 91)) {
                newsect = findmatch(Tok[0], F_keywords.SectWords);
                if ((newsect >= 0)) {
                    if ((sect == s_TRANSECT)) {
                        F_transect.transect_validate((F_swmm5.Nobjects[TRANSECT] - 1));
                    }
                    sect = newsect;
                    continue L_w5;
                } else {
                    inperr = F_error.error_setInpError(ERR_KEYWORD, Tok[0]);
                    F_report.report_writeInputErrorMsg(inperr, sect, line, lineCount);
                    errsum++;
                    break L_w5;
                }
            } else {
                inperr = parseLine(sect, line);
                if ((inperr > 0)) {
                    errsum++;
                    if ((errsum > MAXERRS)) {
                        F_report.report_writeLine(L201);
                    } else {
                        F_report.report_writeInputErrorMsg(inperr, sect, line, lineCount);
                    }
                }
            }
            if ((errsum > MAXERRS)) {
                break L_w5;
            }
        }
        if ((errsum > 0)) {
            F_swmm5.@ErrorCode = ERR_INPUT;
        }
        return F_swmm5.ErrorCode;
    }

    // input.c:251
    static int addObject(int objType, CPtr id) {
        int errcode = 0;
        int __sw1 = objType;
        if (__sw1 == s_RAINGAGE) {
            if (!(F_project.project_addObject(GAGE, id, F_swmm5.Nobjects[GAGE]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[GAGE]++;
        } else if (__sw1 == s_SUBCATCH) {
            if (!(F_project.project_addObject(SUBCATCH, id, F_swmm5.Nobjects[SUBCATCH]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[SUBCATCH]++;
        } else if (__sw1 == s_AQUIFER) {
            if (!(F_project.project_addObject(AQUIFER, id, F_swmm5.Nobjects[AQUIFER]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[AQUIFER]++;
        } else if (__sw1 == s_UNITHYD) {
            if ((F_project.project_findObject(UNITHYD, id) < 0)) {
                if (!(F_project.project_addObject(UNITHYD, id, F_swmm5.Nobjects[UNITHYD]) != 0)) {
                    errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
                }
                F_swmm5.Nobjects[UNITHYD]++;
            }
        } else if (__sw1 == s_SNOWMELT) {
            if ((F_project.project_findObject(SNOWMELT, id) < 0)) {
                if (!(F_project.project_addObject(SNOWMELT, id, F_swmm5.Nobjects[SNOWMELT]) != 0)) {
                    errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
                }
                F_swmm5.Nobjects[SNOWMELT]++;
            }
        } else if (__sw1 == s_JUNCTION) {
            if (!(F_project.project_addObject(NODE, id, F_swmm5.Nobjects[NODE]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[NODE]++;
            F_swmm5.Nnodes[JUNCTION]++;
        } else if (__sw1 == s_OUTFALL) {
            if (!(F_project.project_addObject(NODE, id, F_swmm5.Nobjects[NODE]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[NODE]++;
            F_swmm5.Nnodes[OUTFALL]++;
        } else if (__sw1 == s_STORAGE) {
            if (!(F_project.project_addObject(NODE, id, F_swmm5.Nobjects[NODE]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[NODE]++;
            F_swmm5.Nnodes[STORAGE]++;
        } else if (__sw1 == s_DIVIDER) {
            if (!(F_project.project_addObject(NODE, id, F_swmm5.Nobjects[NODE]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[NODE]++;
            F_swmm5.Nnodes[DIVIDER]++;
        } else if (__sw1 == s_CONDUIT) {
            if (!(F_project.project_addObject(LINK, id, F_swmm5.Nobjects[LINK]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[LINK]++;
            F_swmm5.Nlinks[CONDUIT]++;
        } else if (__sw1 == s_PUMP) {
            if (!(F_project.project_addObject(LINK, id, F_swmm5.Nobjects[LINK]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[LINK]++;
            F_swmm5.Nlinks[PUMP]++;
        } else if (__sw1 == s_ORIFICE) {
            if (!(F_project.project_addObject(LINK, id, F_swmm5.Nobjects[LINK]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[LINK]++;
            F_swmm5.Nlinks[ORIFICE]++;
        } else if (__sw1 == s_WEIR) {
            if (!(F_project.project_addObject(LINK, id, F_swmm5.Nobjects[LINK]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[LINK]++;
            F_swmm5.Nlinks[WEIR]++;
        } else if (__sw1 == s_OUTLET) {
            if (!(F_project.project_addObject(LINK, id, F_swmm5.Nobjects[LINK]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[LINK]++;
            F_swmm5.Nlinks[OUTLET]++;
        } else if (__sw1 == s_POLLUTANT) {
            if (!(F_project.project_addObject(POLLUT, id, F_swmm5.Nobjects[POLLUT]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[POLLUT]++;
        } else if (__sw1 == s_LANDUSE) {
            if (!(F_project.project_addObject(LANDUSE, id, F_swmm5.Nobjects[LANDUSE]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[LANDUSE]++;
        } else if (__sw1 == s_PATTERN) {
            if ((F_project.project_findObject(TIMEPATTERN, id) < 0)) {
                if (!(F_project.project_addObject(TIMEPATTERN, id, F_swmm5.Nobjects[TIMEPATTERN]) != 0)) {
                    errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
                }
                F_swmm5.Nobjects[TIMEPATTERN]++;
            }
        } else if (__sw1 == s_CURVE) {
            if ((F_project.project_findObject(CURVE, id) < 0)) {
                if (!(F_project.project_addObject(CURVE, id, F_swmm5.Nobjects[CURVE]) != 0)) {
                    errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
                }
                F_swmm5.Nobjects[CURVE]++;
                id = R.strtok(null, L184);
                if ((findmatch(id, F_keywords.CurveTypeWords) == SHAPE_CURVE)) {
                    F_swmm5.Nobjects[SHAPE]++;
                }
            }
        } else if (__sw1 == s_TIMESERIES) {
            if ((F_project.project_findObject(TSERIES, id) < 0)) {
                if (!(F_project.project_addObject(TSERIES, id, F_swmm5.Nobjects[TSERIES]) != 0)) {
                    errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
                }
                F_swmm5.Nobjects[TSERIES]++;
            }
        } else if (__sw1 == s_CONTROL) {
            if ((match(id, L202) != 0)) {
                F_swmm5.Nobjects[CONTROL]++;
            } else {
                F_controls.controls_addToCount(id);
            }
        } else if (__sw1 == s_TRANSECT) {
            if ((match(id, L203) != 0)) {
                id = R.strtok(null, L184);
                if ((id != null)) {
                    if (!(F_project.project_addObject(TRANSECT, id, F_swmm5.Nobjects[TRANSECT]) != 0)) {
                        errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
                    }
                    F_swmm5.Nobjects[TRANSECT]++;
                }
            }
        } else if (__sw1 == s_LID_CONTROL) {
            if ((F_project.project_findObject(LID, id) < 0)) {
                if (!(F_project.project_addObject(LID, id, F_swmm5.Nobjects[LID]) != 0)) {
                    errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
                }
                F_swmm5.Nobjects[LID]++;
            }
        } else if (__sw1 == s_EVENT) {
            F_swmm5.@NumEvents = F_swmm5.NumEvents + 1;
        } else if (__sw1 == s_STREET) {
            if (!(F_project.project_addObject(STREET, id, F_swmm5.Nobjects[STREET]) != 0)) {
                errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
            }
            F_swmm5.Nobjects[STREET]++;
        } else if (__sw1 == s_INLET) {
            if ((F_project.project_findObject(INLET, id) < 0)) {
                if (!(F_project.project_addObject(INLET, id, F_swmm5.Nobjects[INLET]) != 0)) {
                    errcode = F_error.error_setInpError(ERR_DUP_NAME, id);
                }
                F_swmm5.Nobjects[INLET]++;
            }
        }
        return errcode;
    }

    // input.c:464
    static int parseLine(int sect, CPtr line) {
        int j = 0;
        int err = 0;
        int __sw1 = sect;
        if (__sw1 == s_TITLE) {
            return readTitle(line);
        } else if (__sw1 == s_RAINGAGE) {
            j = Mobjects[GAGE];
            err = F_gage.gage_readParams(j, Tok, Ntokens);
            Mobjects[GAGE]++;
            return err;
        } else if (__sw1 == s_TEMP) {
            return F_climate.climate_readParams(Tok, Ntokens);
        } else if (__sw1 == s_EVAP) {
            return F_climate.climate_readEvapParams(Tok, Ntokens);
        } else if (__sw1 == s_ADJUST) {
            return F_climate.climate_readAdjustments(Tok, Ntokens);
        } else if (__sw1 == s_SUBCATCH) {
            j = Mobjects[SUBCATCH];
            err = F_subcatch.subcatch_readParams(j, Tok, Ntokens);
            Mobjects[SUBCATCH]++;
            return err;
        } else if (__sw1 == s_SUBAREA) {
            return F_subcatch.subcatch_readSubareaParams(Tok, Ntokens);
        } else if (__sw1 == s_INFIL) {
            return F_infil.infil_readParams(F_swmm5.InfilModel, Tok, Ntokens);
        } else if (__sw1 == s_AQUIFER) {
            j = Mobjects[AQUIFER];
            err = F_gwater.gwater_readAquiferParams(j, Tok, Ntokens);
            Mobjects[AQUIFER]++;
            return err;
        } else if (__sw1 == s_GROUNDWATER) {
            return F_gwater.gwater_readGroundwaterParams(Tok, Ntokens);
        } else if (__sw1 == s_GWF) {
            return F_gwater.gwater_readFlowExpression(Tok, Ntokens);
        } else if (__sw1 == s_SNOWMELT) {
            return F_snow.snow_readMeltParams(Tok, Ntokens);
        } else if (__sw1 == s_JUNCTION) {
            return readNode(JUNCTION);
        } else if (__sw1 == s_OUTFALL) {
            return readNode(OUTFALL);
        } else if (__sw1 == s_STORAGE) {
            return readNode(STORAGE);
        } else if (__sw1 == s_DIVIDER) {
            return readNode(DIVIDER);
        } else if (__sw1 == s_CONDUIT) {
            return readLink(CONDUIT);
        } else if (__sw1 == s_PUMP) {
            return readLink(PUMP);
        } else if (__sw1 == s_ORIFICE) {
            return readLink(ORIFICE);
        } else if (__sw1 == s_WEIR) {
            return readLink(WEIR);
        } else if (__sw1 == s_OUTLET) {
            return readLink(OUTLET);
        } else if (__sw1 == s_XSECTION) {
            return F_link.link_readXsectParams(Tok, Ntokens);
        } else if (__sw1 == s_TRANSECT) {
            return F_transect.transect_readParams(new IElem(Mobjects, TRANSECT), Tok, Ntokens);
        } else if (__sw1 == s_LOSSES) {
            return F_link.link_readLossParams(Tok, Ntokens);
        } else if (__sw1 == s_POLLUTANT) {
            j = Mobjects[POLLUT];
            err = F_landuse.landuse_readPollutParams(j, Tok, Ntokens);
            Mobjects[POLLUT]++;
            return err;
        } else if (__sw1 == s_LANDUSE) {
            j = Mobjects[LANDUSE];
            err = F_landuse.landuse_readParams(j, Tok, Ntokens);
            Mobjects[LANDUSE]++;
            return err;
        } else if (__sw1 == s_BUILDUP) {
            return F_landuse.landuse_readBuildupParams(Tok, Ntokens);
        } else if (__sw1 == s_WASHOFF) {
            return F_landuse.landuse_readWashoffParams(Tok, Ntokens);
        } else if (__sw1 == s_COVERAGE) {
            return F_subcatch.subcatch_readLanduseParams(Tok, Ntokens);
        } else if (__sw1 == s_INFLOW) {
            return F_inflow.inflow_readExtInflow(Tok, Ntokens);
        } else if (__sw1 == s_DWF) {
            return F_inflow.inflow_readDwfInflow(Tok, Ntokens);
        } else if (__sw1 == s_PATTERN) {
            return F_inflow.inflow_readDwfPattern(Tok, Ntokens);
        } else if (__sw1 == s_RDII) {
            return F_rdii.rdii_readRdiiInflow(Tok, Ntokens);
        } else if (__sw1 == s_UNITHYD) {
            return F_rdii.rdii_readUnitHydParams(Tok, Ntokens);
        } else if (__sw1 == s_LOADING) {
            return F_subcatch.subcatch_readInitBuildup(Tok, Ntokens);
        } else if (__sw1 == s_TREATMENT) {
            return F_treatmnt.treatmnt_readExpression(Tok, Ntokens);
        } else if (__sw1 == s_CURVE) {
            return F_table.table_readCurve(Tok, Ntokens);
        } else if (__sw1 == s_TIMESERIES) {
            return F_table.table_readTimeseries(Tok, Ntokens);
        } else if (__sw1 == s_CONTROL) {
            return readControl(Tok, Ntokens);
        } else if (__sw1 == s_REPORT) {
            return F_report.report_readOptions(Tok, Ntokens);
        } else if (__sw1 == s_FILE) {
            return F_iface.iface_readFileParams(Tok, Ntokens);
        } else if (__sw1 == s_LID_CONTROL) {
            return F_lid.lid_readProcParams(Tok, Ntokens);
        } else if (__sw1 == s_LID_USAGE) {
            return F_lid.lid_readGroupParams(Tok, Ntokens);
        } else if (__sw1 == s_EVENT) {
            return readEvent(Tok, Ntokens);
        } else if (__sw1 == s_STREET) {
            return F_street.street_readParams(Tok, Ntokens);
        } else if (__sw1 == s_INLET) {
            return F_inlet.inlet_readDesignParams(Tok, Ntokens);
        } else if (__sw1 == s_INLET_USAGE) {
            return F_inlet.inlet_readUsageParams(Tok, Ntokens);
        } else {
            return 0;
        }
        throw new IllegalStateException('parseLine: no return');
    }

    // input.c:637
    static int readControl(CPtr[] tok, int ntoks) {
        int index = 0;
        int keyword = 0;
        if ((ntoks < 2)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        if ((match(tok[0], L29) != 0)) {
            return F_controls.controls_addVariable(tok, ntoks);
        }
        if ((match(tok[0], L30) != 0)) {
            return F_controls.controls_addExpression(tok, ntoks);
        }
        keyword = findmatch(tok[0], F_keywords.RuleKeyWords);
        if ((keyword < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[0]);
        }
        if ((keyword == 0)) {
            if (!(F_project.project_addObject(CONTROL, tok[1], Mobjects[CONTROL]) != 0)) {
                return F_error.error_setInpError(ERR_DUP_NAME, Tok[1]);
            }
            Mobjects[CONTROL]++;
        }
        index = (Mobjects[CONTROL] - 1);
        if ((index < 0)) {
            return F_error.error_setInpError(ERR_RULE, L0);
        }
        return F_controls.controls_addRuleClause(index, keyword, Tok, Ntokens);
    }

    // input.c:680
    static int readOption(CPtr line) {
        Ntokens = getTokens(line);
        if ((Ntokens < 2)) {
            return 0;
        }
        return F_project.project_readOption(Tok[0], Tok[1]);
    }

    // input.c:694
    static int readTitle(CPtr line) {
        int i = 0;
        int n = 0;
        L_f1: for (i = 0; (i < 3); i++) {
            if ((R.strlen(F_swmm5.Title[i]) == 0)) {
                n = (int) (R.strlen(line));
                if ((line.g((n - 1)) == 10)) {
                    line.s((n - 1), 32);
                }
                Overrides.sstrncpy(F_swmm5.Title[i], line, 1024);
                break L_f1;
            }
        }
        return 0;
    }

    // input.c:721
    static int readNode(int type) {
        int j = Mobjects[NODE];
        int k = Mnodes[type];
        int err = F_node.node_readParams(j, type, k, Tok, Ntokens);
        Mobjects[NODE]++;
        Mnodes[type]++;
        return err;
    }

    // input.c:738
    static int readLink(int type) {
        int j = Mobjects[LINK];
        int k = Mlinks[type];
        int err = F_link.link_readParams(j, type, k, Tok, Ntokens);
        Mobjects[LINK]++;
        Mlinks[type]++;
        return err;
    }

    // input.c:755
    static int readEvent(CPtr[] tok, int ntoks) {
        double[] x = new double[4];
        if ((ntoks < 4)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        if (!(F_datetime.datetime_strToDate(tok[0], new DElem(x, 0)) != 0)) {
            return F_error.error_setInpError(ERR_DATETIME, tok[0]);
        }
        if (!(F_datetime.datetime_strToTime(tok[1], new DElem(x, 1)) != 0)) {
            return F_error.error_setInpError(ERR_DATETIME, tok[1]);
        }
        if (!(F_datetime.datetime_strToDate(tok[2], new DElem(x, 2)) != 0)) {
            return F_error.error_setInpError(ERR_DATETIME, tok[2]);
        }
        if (!(F_datetime.datetime_strToTime(tok[3], new DElem(x, 3)) != 0)) {
            return F_error.error_setInpError(ERR_DATETIME, tok[3]);
        }
        F_swmm5.Event[Mevents].@start = (x[0] + x[1]);
        F_swmm5.Event[Mevents].@end = (x[2] + x[3]);
        if (R.dge(F_swmm5.Event[Mevents].start, F_swmm5.Event[Mevents].end)) {
            return F_error.error_setInpError(ERR_DATETIME, L204);
        }
        Mevents++;
        return 0;
    }

    // input.c:779
    static int findmatch(CPtr s, CPtr[] keyword) {
        int i = 0;
        L_w1: while ((keyword[i] != null)) {
            if ((match(s, keyword[i]) != 0)) {
                return (i);
            }
            i++;
        }
        return ((-1));
    }

    // input.c:798
    static int match(CPtr str, CPtr substr) {
        int i = 0;
        int j = 0;
        int k = 0;
        if (!(substr.g(0) != 0)) {
            return (0);
        }
        L_f1: for (k = 0; (str.g(k) != 0); k++) {
            if ((str.g(k) != 32)) {
                break L_f1;
            }
        }
        L_f2: for (i = k, j = 0; (substr.g(j) != 0); i++, j++) {
            if (((!(str.g(i) != 0)) || (((((((str.g(i)) >= 97) && ((str.g(i)) <= 122))) ? (((str.g(i)) & (~32))) : (str.g(i)))) != ((((((substr.g(j)) >= 97) && ((substr.g(j)) <= 122))) ? (((substr.g(j)) & (~32))) : (substr.g(j))))))) {
                return (0);
            }
        }
        return (1);
    }

    // input.c:828
    static int getInt(CPtr s, IRef y) {
        DBox x = new DBox(0.0d);
        if ((getDouble(s, x) != 0)) {
            if ((x.v < 0.0d)) {
                x.@v = (x.v - 0.01d);
            } else {
                x.@v = (x.v + 0.01d);
            }
            y.set(R.D2I(x.v));
            return 1;
        }
        y.set(0);
        return 0;
    }

    // input.c:850
    static int getFloat(CPtr s, FRef y) {
        OBox<CPtr> endptr = new OBox<>(null);
        y.set(((float) (R.strtod(s, endptr))));
        if ((endptr.v.g(0) > 0)) {
            return (0);
        }
        return (1);
    }

    // input.c:866
    static int getDouble(CPtr s, DRef y) {
        OBox<CPtr> endptr = new OBox<>(null);
        y.set(R.strtod(s, endptr));
        if ((endptr.v.g(0) > 0)) {
            return (0);
        }
        return (1);
    }

    // input.c:882
    static int getTokens(CPtr s) {
        int len = 0;
        int n = 0;
        int m = 0;
        CPtr c = null;
        L_f1: for (n = 0; (n < 40); n++) {
            Tok[n] = null;
        }
        n = 0;
        c = R.strchr(s, 59);
        if ((c != null)) {
            c.s(0, 0);
        }
        len = (int) (R.strlen(s));
        L_w2: while (((len > 0) && (n < 40))) {
            m = (int) (R.strcspn(s, L184));
            if ((m == 0)) {
                s = s.add(1);
            } else {
                if ((s.g(0) == 34)) {
                    s = s.add(1);
                    len--;
                    m = (int) (R.strcspn(s, L205));
                }
                s.s(m, 0);
                Tok[n] = s;
                n++;
                s = s.add((m + 1));
            }
            len -= (m + 1);
        }
        return n;
    }

}
