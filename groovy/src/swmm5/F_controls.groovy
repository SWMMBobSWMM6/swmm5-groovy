// Generated from EPA SWMM 5.2.4 (controls.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_controls {
    public static CPtr[] ObjectWords = new CPtr[] { L656, L657, L658, L659, L660, L661, L662, L663, L32, null };
    public static CPtr[] AttribWords = new CPtr[] { L664, L665, L314, L666, L667, L183, L668, L669, L670, L671, L672, L673, L674, L675, L676, L677, L21, L678, L679, L680, L681, null };
    public static CPtr[] RelOpWords = new CPtr[] { L31, L682, L683, L684, L685, L686, null };
    public static CPtr[] StatusWords = new CPtr[] { L687, L688, null };
    public static CPtr[] ConduitWords = new CPtr[] { L689, L690, null };
    public static CPtr[] SettingTypeWords = new CPtr[] { L691, L692, L693, null };
    public static CPtr IntensityWord = L694;
    public static TRule[] Rules = null;
    public static TActionList ActionList = null;
    public static int InputState = 0;
    public static int RuleCount = 0;
    public static double ControlValue = 0.0d;
    public static double SetPoint = 0.0d;
    public static double CurrentDate = 0.0d;
    public static double CurrentTime = 0.0d;
    public static int VariableCount = 0;
    public static int ExpressionCount = 0;
    public static int CurrentVariable = 0;
    public static int CurrentExpression = 0;
    public static TNamedVariable[] NamedVariable = null;
    public static TExpression[] Expression = null;

    // controls.c:226
    static void controls_init() {
        Rules = (null);
        NamedVariable = (null);
        Expression = (null);
        RuleCount = 0;
        VariableCount = 0;
        ExpressionCount = 0;
    }

    // controls.c:243
    static void controls_addToCount(CPtr s) {
        if ((F_input.match(s, L29) != 0)) {
            VariableCount++;
        } else if ((F_input.match(s, L30) != 0)) {
            ExpressionCount++;
        }
    }

    // controls.c:257
    static int controls_create(int n) {
        int r = 0;
        ActionList = (null);
        InputState = r_PRIORITY;
        RuleCount = n;
        if ((RuleCount > 0)) {
            Rules = R.fill(new TRule[(int) (RuleCount)], (() -> new TRule()));
            if ((Rules == null)) {
                return ERR_MEMORY;
            }
            L_f1: for (r = 0; (r < RuleCount); r++) {
                Rules[r].@ID = null;
                Rules[r].@firstPremise = (null);
                Rules[r].@lastPremise = (null);
                Rules[r].@thenActions = (null);
                Rules[r].@elseActions = (null);
                Rules[r].@priority = 0.0d;
            }
        }
        CurrentVariable = (-1);
        CurrentExpression = (-1);
        if ((VariableCount > 0)) {
            NamedVariable = R.fill(new TNamedVariable[(int) (VariableCount)], (() -> new TNamedVariable()));
            if ((NamedVariable == null)) {
                return ERR_MEMORY;
            }
        }
        if ((ExpressionCount > 0)) {
            Expression = R.fill(new TExpression[(int) (ExpressionCount)], (() -> new TExpression()));
            if ((Expression == null)) {
                return ERR_MEMORY;
            }
        }
        return 0;
    }

    // controls.c:302
    static void controls_delete() {
        int i = 0;
        L_f1: for (i = 0; (i < ExpressionCount); i++) {
            F_mathexpr.mathexpr_delete(Expression[i].expression);
            Expression[i].@expression = (null);
        }
        if ((Expression != null)) {
            R.free(Expression);
            Expression = (null);
        }
        if ((NamedVariable != null)) {
            R.free(NamedVariable);
            NamedVariable = (null);
        }
        if ((RuleCount == 0)) {
            return;
        }
        deleteActionList();
        deleteRules();
    }

    // controls.c:326
    static int controls_addVariable(CPtr[] tok, int nToks) {
        TVariable v1 = new TVariable();
        IBox k = new IBox(0);
        int err = 0;
        CurrentVariable++;
        if ((nToks < 5)) {
            return ERR_ITEMS;
        }
        if ((findExactMatch(tok[1], AttribWords) >= 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
        }
        if (!(F_input.match(tok[2], L31) != 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[2]);
        }
        if (((!(F_input.match(tok[3], L32) != 0)) && (nToks < 6))) {
            return ERR_ITEMS;
        }
        k.@v = 3;
        err = getPremiseVariable(tok, nToks, k, v1);
        if ((err > 0)) {
            return err;
        }
        k.@v = CurrentVariable;
        NamedVariable[k.v].@variable = v1.copy();
        Overrides.sstrncpy(NamedVariable[k.v].name, tok[1], 32);
        return 0;
    }

    // controls.c:357
    static int controls_addExpression(CPtr[] tok, int nToks) {
        int i = 0;
        int k = 0;
        CPtr s = R.buf(1025);
        MathExpr expr = null;
        CurrentExpression++;
        if ((nToks < 4)) {
            return ERR_ITEMS;
        }
        k = CurrentExpression;
        Expression[k].@expression = (null);
        Overrides.sstrncpy(Expression[k].name, tok[1], 32);
        Overrides.sstrncpy(s, tok[3], 1024);
        L_f1: for (i = 4; (i < nToks); i++) {
            Overrides.sstrcat(s, L33, 1024);
            Overrides.sstrcat(s, tok[i], 1024);
        }
        expr = F_mathexpr.mathexpr_create(s, F_controls::getVariableIndex);
        if ((expr == null)) {
            return F_error.error_setInpError(ERR_MATH_EXPR, L0);
        }
        Expression[k].@expression = expr;
        return 0;
    }

    // controls.c:393
    static int getVariableIndex(CPtr varName) {
        int i = 0;
        L_f1: for (i = 0; (i < VariableCount); i++) {
            if ((F_input.match(varName, NamedVariable[i].name) != 0)) {
                return i;
            }
        }
        return (-1);
    }

    // controls.c:410
    static double getNamedVariableValue(int varIndex) {
        return getVariableValue(NamedVariable[varIndex].variable);
    }

    // controls.c:422
    static int getExpressionIndex(CPtr exprName) {
        int i = 0;
        L_f1: for (i = 0; (i < ExpressionCount); i++) {
            if ((F_input.match(exprName, Expression[i].name) != 0)) {
                return i;
            }
        }
        return (-1);
    }

    // controls.c:439
    static int controls_addRuleClause(int r, int keyword, CPtr[] tok, int nToks) {
        int __sw1 = keyword;
        if (__sw1 == r_RULE) {
            if ((Rules[r].ID == null)) {
                Rules[r].@ID = F_project.project_findID(CONTROL, tok[1]);
            }
            InputState = r_RULE;
            if ((nToks > 2)) {
                return ERR_RULE;
            }
            return 0;
        } else if (__sw1 == r_IF) {
            if ((InputState != r_RULE)) {
                return ERR_RULE;
            }
            InputState = r_IF;
            return addPremise(r, r_AND, tok, nToks);
        } else if (__sw1 == r_AND) {
            if ((InputState == r_IF)) {
                return addPremise(r, r_AND, tok, nToks);
            } else if (((InputState == r_THEN) || (InputState == r_ELSE))) {
                return addAction(r, tok, nToks);
            } else {
                return ERR_RULE;
            }
        } else if (__sw1 == r_OR) {
            if ((InputState != r_IF)) {
                return ERR_RULE;
            }
            return addPremise(r, r_OR, tok, nToks);
        } else if (__sw1 == r_THEN) {
            if ((InputState != r_IF)) {
                return ERR_RULE;
            }
            InputState = r_THEN;
            return addAction(r, tok, nToks);
        } else if (__sw1 == r_ELSE) {
            if ((InputState != r_THEN)) {
                return ERR_RULE;
            }
            InputState = r_ELSE;
            return addAction(r, tok, nToks);
        } else if (__sw1 == r_PRIORITY) {
            if (((InputState != r_THEN) && (InputState != r_ELSE))) {
                return ERR_RULE;
            }
            InputState = r_PRIORITY;
            if (!(F_input.getDouble(tok[1], new DField<TRule>(Rules[r], (TRule __o) -> __o.priority, (TRule __o, double __x) -> { __o.@priority = __x })) != 0)) {
                return ERR_NUMBER;
            }
            if ((nToks > 2)) {
                return ERR_RULE;
            }
            return 0;
        }
        return 0;
    }

    // controls.c:495
    static int controls_evaluate(double currentTime, double elapsedTime, double tStep) {
        int r = 0;
        int result = 0;
        TPremise p = null;
        TAction a = null;
        CurrentDate = Math.floor(currentTime);
        CurrentTime = (currentTime - Math.floor(currentTime));
        F_swmm5.@ElapsedTime = elapsedTime;
        if ((RuleCount == 0)) {
            return 0;
        }
        clearActionList();
        L_f1: for (r = 0; (r < RuleCount); r++) {
            result = 1;
            p = Rules[r].firstPremise;
            L_w2: while ((p != null)) {
                if ((p.type == r_OR)) {
                    if ((result == 0)) {
                        result = evaluatePremise(p, tStep);
                    }
                } else {
                    if ((result == 0)) {
                        break L_w2;
                    }
                    result = evaluatePremise(p, tStep);
                }
                p = p.next;
            }
            if ((result == 1)) {
                a = Rules[r].thenActions;
            } else {
                a = Rules[r].elseActions;
            }
            L_w3: while ((a != null)) {
                updateActionValue(a, currentTime, tStep);
                updateActionList(a);
                a = a.next;
            }
        }
        if ((ActionList != null)) {
            return executeActionList(currentTime);
        } else {
            return 0;
        }
        throw new IllegalStateException('controls_evaluate: no return');
    }

    // controls.c:556
    static int addPremise(int r, int type, CPtr[] tok, int nToks) {
        int relation = 0;
        IBox n = new IBox(0);
        int err = 0;
        DBox value = new DBox((-10000000000.0d));
        TPremise p = null;
        TVariable v1 = new TVariable();
        TVariable v2 = new TVariable();
        int obj = 0;
        int exprIndex = 0;
        int varIndex = (-1);
        if ((nToks < 4)) {
            return ERR_ITEMS;
        }
        v1.@attribute = (-1);
        v1.@object = (-1);
        v1.@index = (-1);
        n.@v = 1;
        exprIndex = getExpressionIndex(tok[1]);
        if ((exprIndex < 0)) {
            varIndex = getVariableIndex(tok[n.v]);
            if ((varIndex >= 0)) {
                v1 = NamedVariable[varIndex].variable.copy();
            } else {
                err = getPremiseVariable(tok, nToks, n, v1);
                if ((err > 0)) {
                    return err;
                }
            }
        }
        n.@v = n.v + 1;
        if ((n.v >= nToks)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        relation = findExactMatch(tok[n.v], RelOpWords);
        if ((relation < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[n.v]);
        }
        v2.@attribute = (-1);
        v2.@object = (-1);
        v2.@index = (-1);
        n.@v = n.v + 1;
        if ((n.v >= nToks)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        varIndex = getVariableIndex(tok[n.v]);
        if ((varIndex >= 0)) {
            v2 = NamedVariable[varIndex].variable.copy();
        } else {
            obj = F_input.findmatch(tok[n.v], ObjectWords);
            if ((obj >= 0)) {
                err = getPremiseVariable(tok, nToks, n, v2);
                if ((err > 0)) {
                    return ERR_RULE;
                }
                if (((exprIndex < 0) && (v1.attribute != v2.attribute))) {
                    F_report.report_writeWarningMsg(L34, Rules[r].ID);
                }
            } else {
                err = getPremiseValue(tok[n.v], v1.attribute, value);
                if ((err > 0)) {
                    return err;
                }
            }
        }
        n.@v = n.v + 1;
        if (((n.v < nToks) && (F_input.findmatch(tok[n.v], F_keywords.RuleKeyWords) >= 0))) {
            return ERR_RULE;
        }
        p = new TPremise();
        if (!(p != null)) {
            return ERR_MEMORY;
        }
        p.@type = type;
        p.@exprIndex = exprIndex;
        p.@lhsVar = v1.copy();
        p.@rhsVar = v2.copy();
        p.@relation = relation;
        p.@value = value.v;
        p.@next = (null);
        if ((Rules[r].firstPremise == null)) {
            Rules[r].@firstPremise = p;
        } else {
            Rules[r].lastPremise.@next = p;
        }
        Rules[r].@lastPremise = p;
        return 0;
    }

    // controls.c:668
    static int getPremiseVariable(CPtr[] tok, int nToks, IRef k, TVariable v) {
        int n = k.get();
        int object = (-1);
        int index = (-1);
        int obj = 0;
        int attrib = 0;
        obj = F_input.findmatch(tok[n], ObjectWords);
        if ((obj < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[n]);
        }
        n++;
        if ((n >= nToks)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        int __sw1 = obj;
        if (__sw1 == r_GAGE) {
            index = F_project.project_findObject(GAGE, tok[n]);
            if ((index < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[n]);
            }
            object = r_GAGE;
        } else if (__sw1 == r_NODE) {
            index = F_project.project_findObject(NODE, tok[n]);
            if ((index < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[n]);
            }
            object = r_NODE;
        } else if (__sw1 == r_LINK || __sw1 == r_CONDUIT || __sw1 == r_PUMP || __sw1 == r_ORIFICE || __sw1 == r_WEIR || __sw1 == r_OUTLET) {
            index = F_project.project_findObject(LINK, tok[n]);
            if ((index < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[n]);
            }
            object = r_LINK;
        } else {
            n--;
        }
        n++;
        if ((n >= nToks)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        if ((object == r_GAGE)) {
            attrib = getGageAttrib(tok[n]);
        } else {
            attrib = F_input.findmatch(tok[n], AttribWords);
        }
        if ((attrib < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[n]);
        }
        if ((obj == r_GAGE)) {
        } else if ((obj == r_NODE)) {
            int __sw2 = attrib;
            if (__sw2 == r_DEPTH || __sw2 == r_MAXDEPTH || __sw2 == r_HEAD || __sw2 == r_VOLUME || __sw2 == r_INFLOW) {
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, tok[n]);
            }
        } else if ((((object == r_LINK) && (index >= 0)) && ((((attrib == r_TIMEOPEN) || (attrib == r_TIMECLOSED)))))) {
        } else if (((obj == r_LINK) || (obj == r_CONDUIT))) {
            int __sw3 = attrib;
            if (__sw3 == r_STATUS || __sw3 == r_DEPTH || __sw3 == r_FULLFLOW || __sw3 == r_FULLDEPTH || __sw3 == r_FLOW || __sw3 == r_LENGTH || __sw3 == r_SLOPE || __sw3 == r_VELOCITY) {
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, tok[n]);
            }
        } else if ((obj == r_PUMP)) {
            int __sw4 = attrib;
            if (__sw4 == r_FLOW || __sw4 == r_SETTING || __sw4 == r_STATUS) {
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, tok[n]);
            }
        } else if ((((obj == r_ORIFICE) || (obj == r_WEIR)) || (obj == r_OUTLET))) {
            int __sw5 = attrib;
            if (__sw5 == r_FLOW || __sw5 == r_SETTING) {
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, tok[n]);
            }
        } else {
            int __sw6 = attrib;
            if (__sw6 == r_TIME || __sw6 == r_DATE || __sw6 == r_CLOCKTIME || __sw6 == r_DAY || __sw6 == r_MONTH || __sw6 == r_DAYOFYEAR) {
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, tok[n]);
            }
        }
        v.@object = object;
        v.@index = index;
        v.@attribute = attrib;
        k.set(n);
        return 0;
    }

    // controls.c:797
    static int getGageAttrib(CPtr token) {
        int attrib = 0;
        if ((F_input.match(token, IntensityWord) != 0)) {
            return 0;
        }
        attrib = R.atoi(token);
        if (((attrib < 1) || (attrib > 48))) {
            return (-1);
        }
        return attrib;
    }

    // controls.c:824
    static int getPremiseValue(CPtr token, int attrib, DRef value) {
        CPtr strDate = R.buf(25);
        int __sw1 = attrib;
        if (__sw1 == r_STATUS) {
            value.set(F_input.findmatch(token, StatusWords));
            if ((value.get() < 0.0d)) {
                value.set(F_input.findmatch(token, ConduitWords));
            }
            if ((value.get() < 0.0d)) {
                return F_error.error_setInpError(ERR_KEYWORD, token);
            }
        } else if (__sw1 == r_TIME || __sw1 == r_CLOCKTIME || __sw1 == r_TIMEOPEN || __sw1 == r_TIMECLOSED) {
            if (!(F_datetime.datetime_strToTime(token, value) != 0)) {
                return F_error.error_setInpError(ERR_DATETIME, token);
            }
        } else if (__sw1 == r_DATE) {
            if (!(F_datetime.datetime_strToDate(token, value) != 0)) {
                return F_error.error_setInpError(ERR_DATETIME, token);
            }
        } else if (__sw1 == r_DAY) {
            if (!(F_input.getDouble(token, value) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, token);
            }
            if (((value.get() < 1.0d) || (R.dgt(value.get(), 7.0d)))) {
                return F_error.error_setInpError(ERR_DATETIME, token);
            }
        } else if (__sw1 == r_MONTH) {
            if (!(F_input.getDouble(token, value) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, token);
            }
            if (((value.get() < 1.0d) || (R.dgt(value.get(), 12.0d)))) {
                return F_error.error_setInpError(ERR_DATETIME, token);
            }
        } else if (__sw1 == r_DAYOFYEAR) {
            Overrides.sstrncpy(strDate, token, 6);
            Overrides.sstrcat(strDate, L35, 25);
            if ((F_datetime.datetime_strToDate(strDate, value) != 0)) {
                value.set(F_datetime.datetime_dayOfYear(value.get()));
            } else if ((((!(F_input.getDouble(token, value) != 0)) || (value.get() < 1)) || (R.dgt(value.get(), 365)))) {
                return F_error.error_setInpError(ERR_DATETIME, token);
            }
        } else {
            if (!(F_input.getDouble(token, value) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, token);
            }
        }
        return 0;
    }

    // controls.c:889
    static int addAction(int r, CPtr[] tok, int nToks) {
        int obj = 0;
        int link = 0;
        IBox attrib = new IBox(0);
        IBox curve = new IBox((-1));
        IBox tseries = new IBox((-1));
        int n = 0;
        int err = 0;
        double[] values = new double[] { 1.0d, 0.0d, 0.0d };
        TAction a = null;
        if ((nToks < 6)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        obj = F_input.findmatch(tok[1], ObjectWords);
        if (((((((obj != r_LINK) && (obj != r_CONDUIT)) && (obj != r_PUMP)) && (obj != r_ORIFICE)) && (obj != r_WEIR)) && (obj != r_OUTLET))) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
        }
        link = F_project.project_findObject(LINK, tok[2]);
        if ((link < 0)) {
            return F_error.error_setInpError(ERR_NAME, tok[2]);
        }
        int __sw1 = obj;
        if (__sw1 == r_CONDUIT) {
            if ((F_swmm5.Link[link].type != CONDUIT)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
        } else if (__sw1 == r_PUMP) {
            if ((F_swmm5.Link[link].type != PUMP)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
        } else if (__sw1 == r_ORIFICE) {
            if ((F_swmm5.Link[link].type != ORIFICE)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
        } else if (__sw1 == r_WEIR) {
            if ((F_swmm5.Link[link].type != WEIR)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
        } else if (__sw1 == r_OUTLET) {
            if ((F_swmm5.Link[link].type != OUTLET)) {
                return F_error.error_setInpError(ERR_NAME, tok[2]);
            }
        }
        attrib.@v = F_input.findmatch(tok[3], AttribWords);
        if ((attrib.v < 0)) {
            return F_error.error_setInpError(ERR_KEYWORD, tok[3]);
        }
        if ((obj == r_CONDUIT)) {
            if ((attrib.v == r_STATUS)) {
                values[0] = F_input.findmatch(tok[5], ConduitWords);
                if ((values[0] < 0.0d)) {
                    return F_error.error_setInpError(ERR_KEYWORD, tok[5]);
                }
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, tok[3]);
            }
        } else if ((obj == r_PUMP)) {
            if ((attrib.v == r_STATUS)) {
                values[0] = F_input.findmatch(tok[5], StatusWords);
                if ((values[0] < 0.0d)) {
                    return F_error.error_setInpError(ERR_KEYWORD, tok[5]);
                }
            } else if ((attrib.v == r_SETTING)) {
                err = setActionSetting(tok, nToks, curve, tseries, attrib, values);
                if ((err > 0)) {
                    return err;
                }
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, tok[3]);
            }
        } else if ((((obj == r_ORIFICE) || (obj == r_WEIR)) || (obj == r_OUTLET))) {
            if ((attrib.v == r_SETTING)) {
                err = setActionSetting(tok, nToks, curve, tseries, attrib, values);
                if ((err > 0)) {
                    return err;
                }
                if (((attrib.v == r_SETTING) && (((values[0] < 0.0d) || (R.dgt(values[0], 1.0d)))))) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[5]);
                }
            } else {
                return F_error.error_setInpError(ERR_KEYWORD, tok[3]);
            }
        } else {
            return F_error.error_setInpError(ERR_KEYWORD, tok[1]);
        }
        n = 6;
        if (((curve.v >= 0) || (tseries.v >= 0))) {
            n = 7;
        }
        if ((attrib.v == r_PID)) {
            n = 9;
        }
        if (((n < nToks) && (F_input.findmatch(tok[n], F_keywords.RuleKeyWords) >= 0))) {
            return ERR_RULE;
        }
        a = new TAction();
        if (!(a != null)) {
            return ERR_MEMORY;
        }
        a.@rule = r;
        a.@link = link;
        a.@attribute = attrib.v;
        a.@curve = curve.v;
        a.@tseries = tseries.v;
        a.@value = values[0];
        if ((attrib.v == r_PID)) {
            a.@kp = values[0];
            a.@ki = values[1];
            a.@kd = values[2];
            a.@e1 = 0.0d;
            a.@e2 = 0.0d;
        }
        if ((InputState == r_THEN)) {
            a.@next = Rules[r].thenActions;
            Rules[r].@thenActions = a;
        } else {
            a.@next = Rules[r].elseActions;
            Rules[r].@elseActions = a;
        }
        return 0;
    }

    // controls.c:1028
    static int setActionSetting(CPtr[] tok, int nToks, IRef curve, IRef tseries, IRef attrib, double[] values) {
        int k = 0;
        int m = 0;
        if ((nToks < 6)) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        k = F_input.findmatch(tok[5], SettingTypeWords);
        if (((k >= 0) && (nToks < 7))) {
            return F_error.error_setInpError(ERR_ITEMS, L0);
        }
        int __sw1 = k;
        if (__sw1 == r_CURVE) {
            m = F_project.project_findObject(CURVE, tok[6]);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[6]);
            }
            curve.set(m);
        } else if (__sw1 == r_TIMESERIES) {
            m = F_project.project_findObject(TSERIES, tok[6]);
            if ((m < 0)) {
                return F_error.error_setInpError(ERR_NAME, tok[6]);
            }
            tseries.set(m);
            F_swmm5.Tseries[m].@refersTo = CONTROL;
        } else if (__sw1 == r_PID) {
            if ((nToks < 9)) {
                return F_error.error_setInpError(ERR_ITEMS, L0);
            }
            L_f2: for (m = 6; (m <= 8); m++) {
                if (!(F_input.getDouble(tok[m], new DElem(values, (m - 6))) != 0)) {
                    return F_error.error_setInpError(ERR_NUMBER, tok[m]);
                }
            }
            attrib.set(r_PID);
        } else {
            if (!(F_input.getDouble(tok[5], new DElem(values, 0)) != 0)) {
                return F_error.error_setInpError(ERR_NUMBER, tok[5]);
            }
        }
        return 0;
    }

    // controls.c:1086
    static void updateActionValue(TAction a, double currentTime, double dt) {
        if ((a.curve >= 0)) {
            a.@value = F_table.table_lookup(F_swmm5.Curve[a.curve], ControlValue);
        } else if ((a.tseries >= 0)) {
            a.@value = F_table.table_tseriesLookup(F_swmm5.Tseries[a.tseries], currentTime, 1);
        } else if ((a.attribute == r_PID)) {
            a.@value = getPIDSetting(a, dt);
        }
    }

    // controls.c:1111
    static double getPIDSetting(TAction a, double dt) {
        double e0 = 0.0d;
        double setting = 0.0d;
        double p = 0.0d;
        double i = 0.0d;
        double d = 0.0d;
        double update = 0.0d;
        double tolerance = 0.0001d;
        dt *= 1440.0d;
        e0 = (SetPoint - ControlValue);
        if (R.dgt(Math.abs(e0), 1e-06d)) {
            if ((SetPoint != 0.0d)) {
                e0 = (e0 / SetPoint);
            } else {
                e0 = (e0 / ControlValue);
            }
        }
        if ((Math.abs((e0 - a.e1)) < tolerance)) {
            a.@e2 = 0.0d;
            a.@e1 = 0.0d;
        }
        p = ((e0 - a.e1));
        if ((a.ki == 0.0d)) {
            i = 0.0d;
        } else {
            i = ((e0 * dt) / a.ki);
        }
        d = ((a.kd * (((e0 - (2.0d * a.e1)) + a.e2))) / dt);
        update = (a.kp * (((p + i) + d)));
        if ((Math.abs(update) < tolerance)) {
            update = 0.0d;
        }
        setting = (F_swmm5.Link[a.link].targetSetting + update);
        a.@e2 = a.e1;
        a.@e1 = e0;
        if ((setting < 0.0d)) {
            setting = 0.0d;
        }
        if (((F_swmm5.Link[a.link].type != PUMP) && (R.dgt(setting, 1.0d)))) {
            setting = 1.0d;
        }
        return setting;
    }

    // controls.c:1168
    static void updateActionList(TAction a) {
        TActionList listItem = null;
        TAction a1 = null;
        double priority = Rules[a.rule].priority;
        listItem = ActionList;
        L_w1: while ((listItem != null)) {
            a1 = listItem.action;
            if (!(a1 != null)) {
                break L_w1;
            }
            if ((a1.link == a.link)) {
                if (R.dgt(priority, Rules[a1.rule].priority)) {
                    listItem.@action = a;
                }
                return;
            }
            listItem = listItem.next;
        }
        if (!(listItem != null)) {
            listItem = new TActionList();
            listItem.@next = ActionList;
            ActionList = listItem;
        }
        listItem.@action = a;
    }

    // controls.c:1206
    static int executeActionList(double currentTime) {
        TActionList listItem = null;
        TActionList nextItem = null;
        TAction a1 = null;
        int count = 0;
        listItem = ActionList;
        L_w1: while ((listItem != null)) {
            a1 = listItem.action;
            if (!(a1 != null)) {
                break L_w1;
            }
            if ((a1.link >= 0)) {
                if ((F_swmm5.Link[a1.link].targetSetting != a1.value)) {
                    F_swmm5.Link[a1.link].@targetSetting = a1.value;
                    if (((((F_swmm5.RptFlags.controls != 0) && (a1.curve < 0)) && (a1.tseries < 0)) && (a1.attribute != r_PID))) {
                        F_report.report_writeControlAction(currentTime, F_swmm5.Link[a1.link].ID, a1.value, Rules[a1.rule].ID);
                    }
                    count++;
                }
            }
            nextItem = listItem.next;
            listItem = nextItem;
        }
        return count;
    }

    // controls.c:1243
    static int evaluatePremise(TPremise p, double tStep) {
        double lhsValue = 0.0d;
        double rhsValue = 0.0d;
        int result = 0;
        if ((p.exprIndex >= 0)) {
            lhsValue = F_mathexpr.mathexpr_eval(Expression[p.exprIndex].expression, F_controls::getNamedVariableValue);
        } else {
            lhsValue = getVariableValue(p.lhsVar);
        }
        if ((p.value == (-10000000000.0d))) {
            rhsValue = getVariableValue(p.rhsVar);
        } else {
            rhsValue = p.value;
        }
        if (((lhsValue == (-10000000000.0d)) || (rhsValue == (-10000000000.0d)))) {
            return 0;
        }
        int __sw1 = p.lhsVar.attribute;
        if (__sw1 == r_TIME || __sw1 == r_CLOCKTIME) {
            return compareTimes(lhsValue, p.relation, rhsValue, (tStep / 2.0d));
        } else if (__sw1 == r_TIMEOPEN || __sw1 == r_TIMECLOSED) {
            result = compareTimes(lhsValue, p.relation, rhsValue, (tStep / 2.0d));
            ControlValue = (lhsValue * 24.0d);
            return result;
        } else {
            return compareValues(lhsValue, p.relation, rhsValue);
        }
        throw new IllegalStateException('evaluatePremise: no return');
    }

    // controls.c:1286
    static double getVariableValue(TVariable v) {
        int i = (-1);
        int j = (-1);
        if ((v.object == r_GAGE)) {
            return getRainValue(v);
        }
        if ((v.object == r_NODE)) {
            i = v.index;
        }
        if ((v.object == r_LINK)) {
            j = v.index;
        }
        int __sw1 = v.attribute;
        if (__sw1 == r_TIME) {
            return F_swmm5.ElapsedTime;
        } else if (__sw1 == r_DATE) {
            return CurrentDate;
        } else if (__sw1 == r_CLOCKTIME) {
            return CurrentTime;
        } else if (__sw1 == r_DAY) {
            return F_datetime.datetime_dayOfWeek(CurrentDate);
        } else if (__sw1 == r_MONTH) {
            return F_datetime.datetime_monthOfYear(CurrentDate);
        } else if (__sw1 == r_DAYOFYEAR) {
            return F_datetime.datetime_dayOfYear(CurrentDate);
        } else if (__sw1 == r_STATUS) {
            if (((j < 0) || (((F_swmm5.Link[j].type != CONDUIT) && (F_swmm5.Link[j].type != PUMP))))) {
                return (-10000000000.0d);
            } else {
                return F_swmm5.Link[j].setting;
            }
        } else if (__sw1 == r_SETTING) {
            if (((j < 0) || ((((F_swmm5.Link[j].type != PUMP) && (F_swmm5.Link[j].type != ORIFICE)) && (F_swmm5.Link[j].type != WEIR))))) {
                return (-10000000000.0d);
            } else {
                return F_swmm5.Link[j].setting;
            }
        } else if (__sw1 == r_FLOW) {
            if ((j < 0)) {
                return (-10000000000.0d);
            } else {
                return ((F_swmm5.Link[j].direction * F_swmm5.Link[j].newFlow) * F_swmm5.UCF(FLOW));
            }
        } else if (__sw1 == r_FULLFLOW || __sw1 == r_FULLDEPTH || __sw1 == r_VELOCITY || __sw1 == r_LENGTH || __sw1 == r_SLOPE) {
            if ((j < 0)) {
                return (-10000000000.0d);
            } else if ((F_swmm5.Link[j].type != CONDUIT)) {
                return (-10000000000.0d);
            }
            int __sw2 = v.attribute;
            if (__sw2 == r_FULLFLOW) {
                return (F_swmm5.Link[j].qFull * F_swmm5.UCF(FLOW));
            } else if (__sw2 == r_FULLDEPTH) {
                return (F_swmm5.Link[j].xsect.yFull * F_swmm5.UCF(LENGTH));
            } else if (__sw2 == r_VELOCITY) {
                return (F_link.link_getVelocity(j, F_swmm5.Link[j].newFlow, F_swmm5.Link[j].newDepth) * F_swmm5.UCF(LENGTH));
            } else if (__sw2 == r_LENGTH) {
                return (F_swmm5.Conduit[F_swmm5.Link[j].subIndex].length * F_swmm5.UCF(LENGTH));
            } else if (__sw2 == r_SLOPE) {
                return F_swmm5.Conduit[F_swmm5.Link[j].subIndex].slope;
            } else {
                return (-10000000000.0d);
            }
        } else if (__sw1 == r_DEPTH) {
            if ((j >= 0)) {
                return (F_swmm5.Link[j].newDepth * F_swmm5.UCF(LENGTH));
            } else if ((i >= 0)) {
                return (F_swmm5.Node_[i].newDepth * F_swmm5.UCF(LENGTH));
            } else {
                return (-10000000000.0d);
            }
        } else if (__sw1 == r_MAXDEPTH) {
            if ((i >= 0)) {
                return (F_swmm5.Node_[i].fullDepth * F_swmm5.UCF(LENGTH));
            } else {
                return (-10000000000.0d);
            }
        } else if (__sw1 == r_HEAD) {
            if ((i < 0)) {
                return (-10000000000.0d);
            }
            return (((F_swmm5.Node_[i].newDepth + F_swmm5.Node_[i].invertElev)) * F_swmm5.UCF(LENGTH));
        } else if (__sw1 == r_VOLUME) {
            if ((i < 0)) {
                return (-10000000000.0d);
            }
            return ((F_swmm5.Node_[i].newVolume * F_swmm5.UCF(VOLUME)));
        } else if (__sw1 == r_INFLOW) {
            if ((i < 0)) {
                return (-10000000000.0d);
            } else {
                return (F_swmm5.Node_[i].newLatFlow * F_swmm5.UCF(FLOW));
            }
        } else if (__sw1 == r_TIMEOPEN) {
            if ((j < 0)) {
                return (-10000000000.0d);
            }
            if ((F_swmm5.Link[j].setting <= 0.0d)) {
                return (-10000000000.0d);
            }
            return ((CurrentDate + CurrentTime) - F_swmm5.Link[j].timeLastSet);
        } else if (__sw1 == r_TIMECLOSED) {
            if ((j < 0)) {
                return (-10000000000.0d);
            }
            if (R.dgt(F_swmm5.Link[j].setting, 0.0d)) {
                return (-10000000000.0d);
            }
            return ((CurrentDate + CurrentTime) - F_swmm5.Link[j].timeLastSet);
        } else {
            return (-10000000000.0d);
        }
        throw new IllegalStateException('getVariableValue: no return');
    }

    // controls.c:1388
    static double getRainValue(TVariable v) {
        if ((v.index < 0)) {
            return (-10000000000.0d);
        } else if ((F_swmm5.Gage[v.index].isUsed == 0)) {
            return 0.0d;
        } else if ((v.attribute == 0)) {
            return F_swmm5.Gage[v.index].rainfall;
        } else {
            return F_gage.gage_getPastRain(v.index, v.attribute);
        }
        throw new IllegalStateException('getRainValue: no return');
    }

    // controls.c:1405
    static int compareTimes(double lhsValue, int relation, double rhsValue, double halfStep) {
        if ((relation == EQ)) {
            if (((R.dge(lhsValue, (rhsValue - halfStep))) && (lhsValue < (rhsValue + halfStep)))) {
                return 1;
            }
            return 0;
        } else if ((relation == NE)) {
            if (((lhsValue < (rhsValue - halfStep)) || (R.dge(lhsValue, (rhsValue + halfStep))))) {
                return 1;
            }
            return 0;
        } else {
            return compareValues(lhsValue, relation, rhsValue);
        }
        throw new IllegalStateException('compareTimes: no return');
    }

    // controls.c:1432
    static int compareValues(double lhsValue, int relation, double rhsValue) {
        SetPoint = rhsValue;
        ControlValue = lhsValue;
        int __sw1 = relation;
        if (__sw1 == EQ) {
            if ((lhsValue == rhsValue)) {
                return 1;
            }
        } else if (__sw1 == NE) {
            if ((lhsValue != rhsValue)) {
                return 1;
            }
        } else if (__sw1 == LT) {
            if ((lhsValue < rhsValue)) {
                return 1;
            }
        } else if (__sw1 == LE) {
            if ((lhsValue <= rhsValue)) {
                return 1;
            }
        } else if (__sw1 == GT) {
            if (R.dgt(lhsValue, rhsValue)) {
                return 1;
            }
        } else if (__sw1 == GE) {
            if (R.dge(lhsValue, rhsValue)) {
                return 1;
            }
        }
        return 0;
    }

    // controls.c:1455
    static void clearActionList() {
        TActionList listItem = null;
        listItem = ActionList;
        L_w1: while ((listItem != null)) {
            listItem.@action = (null);
            listItem = listItem.next;
        }
    }

    // controls.c:1473
    static void deleteActionList() {
        TActionList listItem = null;
        TActionList nextItem = null;
        listItem = ActionList;
        L_w1: while ((listItem != null)) {
            nextItem = listItem.next;
            R.free(listItem);
            listItem = nextItem;
        }
        ActionList = (null);
    }

    // controls.c:1494
    static void deleteRules() {
        TPremise p = null;
        TPremise pnext = null;
        TAction a = null;
        TAction anext = null;
        int r = 0;
        L_f1: for (r = 0; (r < RuleCount); r++) {
            p = Rules[r].firstPremise;
            L_w2: while ((p != null)) {
                pnext = p.next;
                R.free(p);
                p = pnext;
            }
            a = Rules[r].thenActions;
            L_w3: while ((a != null)) {
                anext = a.next;
                R.free(a);
                a = anext;
            }
            a = Rules[r].elseActions;
            L_w4: while ((a != null)) {
                anext = a.next;
                R.free(a);
                a = anext;
            }
        }
        if ((Rules != null)) {
            R.free(Rules);
            Rules = (null);
        }
        RuleCount = 0;
    }

    // controls.c:1536
    static int findExactMatch(CPtr s, CPtr[] keyword) {
        int i = 0;
        L_w1: while ((keyword[i] != null)) {
            if ((F_swmm5.strcomp(s, keyword[i]) != 0)) {
                return (i);
            }
            i++;
        }
        return ((-1));
    }

}
