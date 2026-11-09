// Generated from EPA SWMM 5.2.4 (mathexpr.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_mathexpr {
    public static int Err = 0;
    public static int Bc = 0;
    public static int PrevLex = 0;
    public static int CurLex = 0;
    public static int Len = 0;
    public static int Pos = 0;
    public static CPtr S = null;
    public static CPtr Token = R.buf(255);
    public static int Ivar = 0;
    public static double Fvalue = 0.0d;
    public static CPtr[] MathFunc = new CPtr[] { L1004, L1005, L1006, L1007, L1008, L1009, L1010, L1011, L739, L1012, L1013, L1014, L1015, L1016, L1017, L1018, L1019, L1020, L1021, null };
    public static FP3 mathexpr_getVariableIndex = null;

    // mathexpr.c:109
    static int sametext(CPtr s1, CPtr s2) {
        int i = 0;
        L_f1: for (i = 0; (R.toupper(s1.g(i)) == R.toupper(s2.g(i))); i++) {
            if (((!(s1.g((i + 1)) != 0)) && (!(s2.g((i + 1)) != 0)))) {
                return (1);
            }
        }
        return (0);
    }

    // mathexpr.c:130
    static int isDigit(int c) {
        if (((c >= 49) && (c <= 57))) {
            return 1;
        }
        if ((c == 48)) {
            return 1;
        }
        return 0;
    }

    // mathexpr.c:139
    static int isLetter(int c) {
        if (((c >= 97) && (c <= 122))) {
            return 1;
        }
        if (((c >= 65) && (c <= 90))) {
            return 1;
        }
        if ((c == 95)) {
            return 1;
        }
        return 0;
    }

    // mathexpr.c:149
    static void getToken() {
        CPtr c = R.bufFrom(L33, 2);
        Token.s(0, 0);
        L_w1: while (((Pos <= Len) && (((isLetter(S.g(Pos)) != 0) || (isDigit(S.g(Pos)) != 0))))) {
            c.s(0, S.g(Pos));
            R.strcat(Token, c);
            Pos++;
        }
        Pos--;
    }

    // mathexpr.c:165
    static int getMathFunc() {
        int i = 0;
        L_w1: while ((MathFunc[i] != null)) {
            if ((sametext(MathFunc[i], Token) != 0)) {
                return (i + 10);
            }
            i++;
        }
        return (0);
    }

    // mathexpr.c:178
    static int getVariable() {
        if (!(mathexpr_getVariableIndex != null)) {
            return 0;
        }
        Ivar = mathexpr_getVariableIndex.call(Token);
        if ((Ivar >= 0)) {
            return 8;
        }
        return 0;
    }

    // mathexpr.c:188
    static double getNumber() {
        CPtr c = R.bufFrom(L33, 2);
        CPtr sNumber = R.buf(255);
        int errflag = 0;
        int decimalCount = 0;
        sNumber.s(0, 0);
        L_w1: while (((Pos < Len) && (isDigit(S.g(Pos)) != 0))) {
            c.s(0, S.g(Pos));
            R.strcat(sNumber, c);
            Pos++;
        }
        if ((Pos < Len)) {
            if ((S.g(Pos) == 46)) {
                decimalCount++;
                if ((decimalCount > 1)) {
                    Err = 1;
                }
                R.strcat(sNumber, L315);
                Pos++;
                L_w2: while (((Pos < Len) && (isDigit(S.g(Pos)) != 0))) {
                    c.s(0, S.g(Pos));
                    R.strcat(sNumber, c);
                    Pos++;
                }
            }
            if (((Pos < Len) && (((S.g(Pos) == 101) || (S.g(Pos) == 69))))) {
                R.strcat(sNumber, L316);
                Pos++;
                if ((Pos >= Len)) {
                    errflag = 1;
                } else {
                    if (((S.g(Pos) == 45) || (S.g(Pos) == 43))) {
                        c.s(0, S.g(Pos));
                        R.strcat(sNumber, c);
                        Pos++;
                    }
                    if (((Pos >= Len) || (!(isDigit(S.g(Pos)) != 0)))) {
                        errflag = 1;
                    } else {
                        L_w3: while (((Pos < Len) && (isDigit(S.g(Pos)) != 0))) {
                            c.s(0, S.g(Pos));
                            R.strcat(sNumber, c);
                            Pos++;
                        }
                    }
                }
            }
        }
        Pos--;
        if ((errflag != 0)) {
            return 0;
        } else {
            return R.atof(sNumber);
        }
        throw new IllegalStateException('getNumber: no return');
    }

    // mathexpr.c:252
    static int getOperand() {
        int code = 0;
        int __sw1 = S.g(Pos);
        if (__sw1 == 40) {
            code = 1;
        } else if (__sw1 == 41) {
            code = 2;
        } else if (__sw1 == 43) {
            code = 3;
        } else if (__sw1 == 45) {
            code = 4;
            if ((((Pos < (Len - 1)) && (isDigit(S.g((Pos + 1))) != 0)) && (((CurLex <= 6) || (CurLex == 31))))) {
                Pos++;
                Fvalue = (-(getNumber()));
                code = 7;
            }
        } else if (__sw1 == 42) {
            code = 5;
        } else if (__sw1 == 47) {
            code = 6;
        } else if (__sw1 == 94) {
            code = 31;
        } else {
            code = 0;
        }
        return code;
    }

    // mathexpr.c:280
    static int getLex() {
        int n = 0;
        L_w1: while (((Pos < Len) && (S.g(Pos) == 32))) {
            Pos++;
        }
        if ((Pos >= Len)) {
            return 0;
        }
        n = getOperand();
        if ((n == 0)) {
            if ((isLetter(S.g(Pos)) != 0)) {
                getToken();
                n = getMathFunc();
                if ((n == 0)) {
                    n = getVariable();
                }
            } else if (((S.g(Pos) == 46) || (isDigit(S.g(Pos)) != 0))) {
                n = 7;
                Fvalue = getNumber();
            }
        }
        Pos++;
        PrevLex = CurLex;
        CurLex = n;
        return n;
    }

    // mathexpr.c:314
    static ExprTree newNode() {
        ExprTree node = null;
        node = new ExprTree();
        if (!(node != null)) {
            Err = 2;
        } else {
            node.@opcode = 0;
            node.@ivar = (-1);
            node.@fvalue = 0.0d;
            node.@left = (null);
            node.@right = (null);
        }
        return node;
    }

    // mathexpr.c:332
    static ExprTree getSingleOp(IRef lex) {
        int opcode = 0;
        ExprTree left = null;
        ExprTree node = null;
        if ((lex.get() == 1)) {
            Bc++;
            left = getTree();
        } else {
            if ((((lex.get() < 7) || (lex.get() == 9)) || (lex.get() > 30))) {
                Err = 1;
                return null;
            }
            opcode = lex.get();
            if (((lex.get() == 7) || (lex.get() == 8))) {
                left = newNode();
                left.@opcode = opcode;
                if ((lex.get() == 7)) {
                    left.@fvalue = Fvalue;
                }
                if ((lex.get() == 8)) {
                    left.@ivar = Ivar;
                }
            } else {
                lex.set(getLex());
                if ((lex.get() != 1)) {
                    Err = 1;
                    return null;
                }
                Bc++;
                left = newNode();
                left.@left = getTree();
                left.@opcode = opcode;
            }
        }
        lex.set(getLex());
        if ((lex.get() == 31)) {
            node = newNode();
            node.@left = left;
            node.@opcode = lex.get();
            lex.set(getLex());
            node.@right = getSingleOp(lex);
            left = node;
        }
        return left;
    }

    // mathexpr.c:397
    static ExprTree getOp(IRef lex) {
        int opcode = 0;
        ExprTree left = null;
        ExprTree right = null;
        ExprTree node = null;
        int neg = 0;
        lex.set(getLex());
        if (((PrevLex == 0) || (PrevLex == 1))) {
            if ((lex.get() == 4)) {
                neg = 1;
                lex.set(getLex());
            } else if ((lex.get() == 3)) {
                lex.set(getLex());
            }
        }
        left = getSingleOp(lex);
        L_w1: while (((lex.get() == 5) || (lex.get() == 6))) {
            opcode = lex.get();
            lex.set(getLex());
            right = getSingleOp(lex);
            node = newNode();
            if ((Err != 0)) {
                return null;
            }
            node.@left = left;
            node.@right = right;
            node.@opcode = opcode;
            left = node;
        }
        if ((neg != 0)) {
            node = newNode();
            if ((Err != 0)) {
                return null;
            }
            node.@left = left;
            node.@right = (null);
            node.@opcode = 9;
            left = node;
        }
        return left;
    }

    // mathexpr.c:442
    static ExprTree getTree() {
        IBox lex = new IBox(0);
        int opcode = 0;
        ExprTree left = null;
        ExprTree right = null;
        ExprTree node = null;
        left = getOp(lex);
        L_f1: for (; ; ) {
            if (((lex.v == 0) || (lex.v == 2))) {
                if ((lex.v == 2)) {
                    Bc--;
                }
                break L_f1;
            }
            if (((lex.v != 3) && (lex.v != 4))) {
                Err = 1;
                break L_f1;
            }
            opcode = lex.v;
            right = getOp(lex);
            node = newNode();
            if ((Err != 0)) {
                break L_f1;
            }
            node.@left = left;
            node.@right = right;
            node.@opcode = opcode;
            left = node;
        }
        return left;
    }

    // mathexpr.c:479
    static void traverseTree(ExprTree tree, ORef<MathExpr> expr) {
        MathExpr node = null;
        if ((tree == null)) {
            return;
        }
        traverseTree(tree.left, expr);
        traverseTree(tree.right, expr);
        node = new MathExpr();
        if ((node != null)) {
            node.@fvalue = tree.fvalue;
            node.@opcode = tree.opcode;
            node.@ivar = tree.ivar;
            node.@next = (null);
            node.@prev = (expr.get());
        }
        if ((expr.get() != null)) {
            (expr.get()).@next = node;
        }
        expr.set(node);
    }

    // mathexpr.c:501
    static void deleteTree(ExprTree tree) {
        if ((tree != null)) {
            if ((tree.left != null)) {
                deleteTree(tree.left);
            }
            if ((tree.right != null)) {
                deleteTree(tree.right);
            }
            R.free(tree);
        }
    }

    // mathexpr.c:516
    static double mathexpr_eval(MathExpr expr, FP4 getVariableValue) {
        double[] ExprStack = new double[1024];
        MathExpr node = expr;
        double r1 = 0.0d;
        double r2 = 0.0d;
        int stackindex = 0;
        ExprStack[0] = 0.0d;
        L_w1: while (((node != null) && (stackindex >= 0))) {
            int __sw1 = node.opcode;
            L_s2: do {
                if (__sw1 == 3) {
                    r1 = ExprStack[stackindex];
                    stackindex--;
                    if ((stackindex < 0)) {
                        break L_s2;
                    }
                    r2 = ExprStack[stackindex];
                    ExprStack[stackindex] = (r2 + r1);
                } else if (__sw1 == 4) {
                    r1 = ExprStack[stackindex];
                    stackindex--;
                    if ((stackindex < 0)) {
                        break L_s2;
                    }
                    r2 = ExprStack[stackindex];
                    ExprStack[stackindex] = (r2 - r1);
                } else if (__sw1 == 5) {
                    r1 = ExprStack[stackindex];
                    stackindex--;
                    if ((stackindex < 0)) {
                        break L_s2;
                    }
                    r2 = ExprStack[stackindex];
                    ExprStack[stackindex] = (r2 * r1);
                } else if (__sw1 == 6) {
                    r1 = ExprStack[stackindex];
                    stackindex--;
                    if ((stackindex < 0)) {
                        break L_s2;
                    }
                    r2 = ExprStack[stackindex];
                    ExprStack[stackindex] = (r2 / r1);
                } else if (__sw1 == 7) {
                    stackindex++;
                    if ((stackindex >= 1024)) {
                        break L_s2;
                    }
                    ExprStack[stackindex] = node.fvalue;
                } else if (__sw1 == 8) {
                    if ((getVariableValue != null)) {
                        r1 = getVariableValue.call(node.ivar);
                    } else {
                        r1 = 0.0d;
                    }
                    stackindex++;
                    if ((stackindex >= 1024)) {
                        break L_s2;
                    }
                    ExprStack[stackindex] = r1;
                } else if (__sw1 == 9) {
                    ExprStack[stackindex] = (-ExprStack[stackindex]);
                } else if (__sw1 == 10) {
                    r1 = ExprStack[stackindex];
                    r2 = Glibm.cos(r1);
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 11) {
                    r1 = ExprStack[stackindex];
                    r2 = Glibm.sin(r1);
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 12) {
                    r1 = ExprStack[stackindex];
                    r2 = Glibm.tan(r1);
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 13) {
                    r1 = ExprStack[stackindex];
                    if ((r1 == 0.0d)) {
                        r2 = 0.0d;
                    } else {
                        r2 = (1.0d / Glibm.tan(r1));
                    }
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 14) {
                    r1 = ExprStack[stackindex];
                    r2 = Math.abs(r1);
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 15) {
                    r1 = ExprStack[stackindex];
                    if ((r1 < 0.0d)) {
                        r2 = (-1.0d);
                    } else if (R.dgt(r1, 0.0d)) {
                        r2 = 1.0d;
                    } else {
                        r2 = 0.0d;
                    }
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 16) {
                    r1 = ExprStack[stackindex];
                    if ((r1 < 0.0d)) {
                        r2 = 0.0d;
                    } else {
                        r2 = Math.sqrt(r1);
                    }
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 17) {
                    r1 = ExprStack[stackindex];
                    if ((r1 <= 0)) {
                        r2 = 0.0d;
                    } else {
                        r2 = Glibm.log(r1);
                    }
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 18) {
                    r1 = ExprStack[stackindex];
                    r2 = Glibm.exp(r1);
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 19) {
                    r1 = ExprStack[stackindex];
                    r2 = Glibm.asin(r1);
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 20) {
                    r1 = ExprStack[stackindex];
                    r2 = Glibm.acos(r1);
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 21) {
                    r1 = ExprStack[stackindex];
                    r2 = Glibm.atan(r1);
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 22) {
                    r1 = ExprStack[stackindex];
                    r2 = (1.57079632679489661923d - Glibm.atan(r1));
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 23) {
                    r1 = ExprStack[stackindex];
                    r2 = (((Glibm.exp(r1) - Glibm.exp((-r1)))) / 2.0d);
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 24) {
                    r1 = ExprStack[stackindex];
                    r2 = (((Glibm.exp(r1) + Glibm.exp((-r1)))) / 2.0d);
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 25) {
                    r1 = ExprStack[stackindex];
                    r2 = (((Glibm.exp(r1) - Glibm.exp((-r1)))) / ((Glibm.exp(r1) + Glibm.exp((-r1)))));
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 26) {
                    r1 = ExprStack[stackindex];
                    r2 = (((Glibm.exp(r1) + Glibm.exp((-r1)))) / ((Glibm.exp(r1) - Glibm.exp((-r1)))));
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 27) {
                    r1 = ExprStack[stackindex];
                    if ((r1 == 0.0d)) {
                        r2 = 0.0d;
                    } else {
                        r2 = Glibm.log10(r1);
                    }
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 28) {
                    r1 = ExprStack[stackindex];
                    if ((r1 <= 0.0d)) {
                        r2 = 0.0d;
                    } else {
                        r2 = 1.0d;
                    }
                    ExprStack[stackindex] = r2;
                } else if (__sw1 == 31) {
                    r1 = ExprStack[stackindex];
                    stackindex--;
                    if ((stackindex < 0)) {
                        break L_s2;
                    }
                    r2 = ExprStack[stackindex];
                    if ((r2 <= 0.0d)) {
                        r2 = 0.0d;
                    } else {
                        r2 = Glibm.pow(r2, r1);
                    }
                    ExprStack[stackindex] = r2;
                }
            } while (false);
            node = node.next;
        }
        if ((stackindex >= 0)) {
            r1 = ExprStack[stackindex];
        } else {
            r1 = 0.0d;
        }
        if ((r1 != r1)) {
            r1 = 0.0d;
        }
        return r1;
    }

    // mathexpr.c:735
    static void mathexpr_delete(MathExpr expr) {
        if ((expr != null)) {
            mathexpr_delete(expr.next);
        }
        R.free(expr);
    }

    // mathexpr.c:743
    static MathExpr mathexpr_create(CPtr formula, FP3 getVar) {
        ExprTree tree = null;
        OBox<MathExpr> expr = new OBox<>((null));
        MathExpr result = (null);
        mathexpr_getVariableIndex = getVar;
        Err = 0;
        PrevLex = 0;
        CurLex = 0;
        S = formula;
        Len = (int) (R.strlen(S));
        Pos = 0;
        Bc = 0;
        tree = getTree();
        if (((Bc == 0) && (Err == 0))) {
            traverseTree(tree, expr);
            L_w1: while ((expr.v != null)) {
                result = expr.v;
                expr.@v = expr.v.prev;
            }
        }
        deleteTree(tree);
        return result;
    }

}
