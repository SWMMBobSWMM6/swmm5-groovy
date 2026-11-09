// Generated from EPA SWMM 5.2.4 (toposort.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_toposort {
    public static int[] InDegree = null;
    public static int[] StartPos = null;
    public static int[] AdjList = null;
    public static int[] Stack_ = null;
    public static int First = 0;
    public static int Last = 0;
    public static CPtr Examined = null;
    public static CPtr InTree = null;
    public static int[] LoopLinks = null;
    public static int LoopLinksLast = 0;

    // toposort.c:57
    static void toposort_sortLinks(int[] sortedLinks) {
        int i = 0;
        int n = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            sortedLinks[i] = i;
        }
        if ((F_swmm5.RouteModel == DW)) {
            checkDummyLinks();
            if ((F_swmm5.ErrorCode != 0)) {
                return;
            }
            L_f2: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
                F_swmm5.Node_[i].@degree = 0;
            }
            L_f3: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
                n = F_swmm5.Link[i].node1;
                if ((F_swmm5.Link[i].direction < 0)) {
                    n = F_swmm5.Link[i].node2;
                }
                if ((F_swmm5.Node_[n].type == OUTFALL)) {
                    if ((F_swmm5.Link[i].direction < 0)) {
                        n = F_swmm5.Link[i].node1;
                    } else {
                        n = F_swmm5.Link[i].node2;
                    }
                    F_swmm5.Node_[n].@degree = F_swmm5.Node_[n].degree + 1;
                } else {
                    F_swmm5.Node_[n].@degree = F_swmm5.Node_[n].degree + 1;
                }
            }
            return;
        }
        if ((F_swmm5.ErrorCode != 0)) {
            return;
        }
        InDegree = new int[(int) (F_swmm5.Nobjects[NODE])];
        StartPos = new int[(int) (F_swmm5.Nobjects[NODE])];
        AdjList = new int[(int) (F_swmm5.Nobjects[LINK])];
        Stack_ = new int[(int) (F_swmm5.Nobjects[NODE])];
        if (((((InDegree == null) || (StartPos == null)) || (AdjList == null)) || (Stack_ == null))) {
            F_report.report_writeErrorMsg(ERR_MEMORY, L0);
        } else {
            createAdjList(DIRECTED);
            adjustAdjList();
            L_f4: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
                InDegree[i] = 0;
            }
            L_f5: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
                InDegree[F_swmm5.Link[i].node2]++;
            }
            n = topoSort(sortedLinks);
        }
        if ((InDegree != null)) {
            R.free(InDegree);
            InDegree = (null);
        }
        if ((StartPos != null)) {
            R.free(StartPos);
            StartPos = (null);
        }
        if ((AdjList != null)) {
            R.free(AdjList);
            AdjList = (null);
        }
        if ((Stack_ != null)) {
            R.free(Stack_);
            Stack_ = (null);
        }
        if (((!(F_swmm5.ErrorCode != 0)) && (n != F_swmm5.Nobjects[LINK]))) {
            F_report.report_writeErrorMsg(ERR_LOOP, L0);
            findCycles();
        }
    }

    // toposort.c:142
    static void createAdjList(int listType) {
        int i = 0;
        int j = 0;
        int k = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            F_swmm5.Node_[i].@degree = 0;
        }
        L_f2: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            F_swmm5.Node_[F_swmm5.Link[j].node1].@degree = F_swmm5.Node_[F_swmm5.Link[j].node1].degree + 1;
            if ((listType == UNDIRECTED)) {
                F_swmm5.Node_[F_swmm5.Link[j].node2].@degree = F_swmm5.Node_[F_swmm5.Link[j].node2].degree + 1;
            }
        }
        StartPos[0] = 0;
        L_f3: for (i = 0; (i < (F_swmm5.Nobjects[NODE] - 1)); i++) {
            StartPos[(i + 1)] = (StartPos[i] + F_swmm5.Node_[i].degree);
            F_swmm5.Node_[i].@degree = 0;
        }
        (F_swmm5.Node_[(F_swmm5.Nobjects[NODE] - 1)]).@degree = 0;
        L_f4: for (j = 0; (j < F_swmm5.Nobjects[LINK]); j++) {
            i = F_swmm5.Link[j].node1;
            k = (StartPos[i] + F_swmm5.Node_[i].degree);
            AdjList[k] = j;
            F_swmm5.Node_[i].@degree = F_swmm5.Node_[i].degree + 1;
            if ((listType == UNDIRECTED)) {
                i = F_swmm5.Link[j].node2;
                k = (StartPos[i] + F_swmm5.Node_[i].degree);
                AdjList[k] = j;
                F_swmm5.Node_[i].@degree = F_swmm5.Node_[i].degree + 1;
            }
        }
    }

    // toposort.c:193
    static void adjustAdjList() {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((F_swmm5.Node_[i].type != DIVIDER)) {
                continue L_f1;
            }
            if ((F_swmm5.Node_[i].degree != 2)) {
                continue L_f1;
            }
            k = F_swmm5.Node_[i].subIndex;
            m = StartPos[i];
            j = AdjList[m];
            if ((j == F_swmm5.Divider[k].link)) {
                AdjList[m] = AdjList[(m + 1)];
                AdjList[(m + 1)] = j;
            }
        }
    }

    // toposort.c:225
    static int topoSort(int[] sortedLinks) {
        int i = 0;
        int j = 0;
        int k = 0;
        int n = 0;
        int i1 = 0;
        int i2 = 0;
        int k1 = 0;
        int k2 = 0;
        First = 0;
        Last = (-1);
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
            if ((InDegree[i] == 0)) {
                Last++;
                Stack_[Last] = i;
            }
        }
        n = 0;
        L_w2: while ((First <= Last)) {
            i1 = Stack_[First];
            k1 = StartPos[i1];
            k2 = (k1 + F_swmm5.Node_[i1].degree);
            L_f3: for (k = k1; (k < k2); k++) {
                j = AdjList[k];
                sortedLinks[n] = j;
                n++;
                i2 = F_swmm5.Link[j].node2;
                InDegree[i2]--;
                if ((InDegree[i2] == 0)) {
                    Last++;
                    Stack_[Last] = i2;
                }
            }
            First++;
        }
        return n;
    }

    // toposort.c:284
    static void findCycles() {
        int i = 0;
        AdjList = new int[(int) ((2 * F_swmm5.Nobjects[LINK]))];
        StartPos = new int[(int) (F_swmm5.Nobjects[NODE])];
        Stack_ = new int[(int) (F_swmm5.Nobjects[NODE])];
        Examined = R.buf((int) (F_swmm5.Nobjects[NODE]));
        InTree = R.buf((int) (F_swmm5.Nobjects[LINK]));
        LoopLinks = new int[(int) (F_swmm5.Nobjects[LINK])];
        if (((((((StartPos != null) && (AdjList != null)) && (Stack_ != null)) && (Examined != null)) && (InTree != null)) && (LoopLinks != null))) {
            createAdjList(UNDIRECTED);
            L_f1: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
                Examined.s(i, 0);
            }
            L_f2: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
                InTree.s(i, 0);
            }
            L_f3: for (i = 0; (i < F_swmm5.Nobjects[NODE]); i++) {
                if ((Examined.g(i) != 0)) {
                    continue L_f3;
                }
                Last = (-1);
                findSpanningTree(i);
            }
        }
        if ((StartPos != null)) {
            R.free(StartPos);
            StartPos = (null);
        }
        if ((AdjList != null)) {
            R.free(AdjList);
            AdjList = (null);
        }
        if ((Stack_ != null)) {
            R.free(Stack_);
            Stack_ = (null);
        }
        if ((Examined != null)) {
            R.free(Examined);
            Examined = null;
        }
        if ((InTree != null)) {
            R.free(InTree);
            InTree = null;
        }
        if ((LoopLinks != null)) {
            R.free(LoopLinks);
            LoopLinks = (null);
        }
    }

    // toposort.c:330
    static void findSpanningTree(int startNode) {
        int nextNode = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        L_f1: for (m = StartPos[startNode]; (m < (StartPos[startNode] + F_swmm5.Node_[startNode].degree)); m++) {
            k = AdjList[m];
            if ((F_swmm5.Link[k].node1 == startNode)) {
                j = F_swmm5.Link[k].node2;
            } else {
                j = F_swmm5.Link[k].node1;
            }
            if ((InTree.g(k) == 0)) {
                if ((Examined.g(j) != 0)) {
                    InTree.s(k, 2);
                    evalLoop(k);
                } else {
                    Examined.s(j, 1);
                    Last++;
                    Stack_[Last] = j;
                    InTree.s(k, 1);
                }
            }
        }
        if ((Last >= 0)) {
            nextNode = Stack_[Last];
            Last--;
            findSpanningTree(nextNode);
        }
    }

    // toposort.c:385
    static void evalLoop(int startLink) {
        int i = 0;
        int i1 = 0;
        int i2 = 0;
        int j = 0;
        int kount = 0;
        int isCycle = 0;
        LoopLinksLast = 0;
        LoopLinks[0] = startLink;
        i1 = F_swmm5.Link[startLink].node1;
        i2 = F_swmm5.Link[startLink].node2;
        if (!(traceLoop(i1, i2, startLink) != 0)) {
            return;
        }
        isCycle = 1;
        j = LoopLinks[0];
        i2 = F_swmm5.Link[j].node2;
        L_f1: for (i = 1; (i <= LoopLinksLast); i++) {
            j = LoopLinks[i];
            i1 = F_swmm5.Link[j].node1;
            if ((i1 == i2)) {
                i2 = F_swmm5.Link[j].node2;
            } else {
                isCycle = 0;
                break L_f1;
            }
        }
        if ((isCycle != 0)) {
            kount = 0;
            L_f2: for (i = 0; (i <= LoopLinksLast); i++) {
                if (((kount % 5) == 0)) {
                    R.fprintf(F_swmm5.Frpt.file, L276);
                }
                kount++;
                R.fprintf(F_swmm5.Frpt.file, L651, F_swmm5.Link[LoopLinks[i]].ID);
                if ((i < LoopLinksLast)) {
                    R.fprintf(F_swmm5.Frpt.file, L652);
                }
            }
        }
    }

    // toposort.c:440
    static int traceLoop(int i1, int i2, int k1) {
        int j = 0;
        int k = 0;
        int m = 0;
        if ((i1 == i2)) {
            return 1;
        }
        L_f1: for (m = StartPos[i1]; (m < (StartPos[i1] + F_swmm5.Node_[i1].degree)); m++) {
            k = AdjList[m];
            if (((k == k1) || (InTree.g(k) != 1))) {
                continue L_f1;
            }
            if ((F_swmm5.Link[k].node1 == i1)) {
                j = F_swmm5.Link[k].node2;
            } else {
                j = F_swmm5.Link[k].node1;
            }
            if ((traceLoop(j, i2, k) != 0)) {
                LoopLinksLast++;
                LoopLinks[LoopLinksLast] = k;
                return 1;
            }
        }
        return 0;
    }

    // toposort.c:482
    static void checkDummyLinks() {
        int i = 0;
        int j = 0;
        int[] marked = null;
        marked = new int[(int) (F_swmm5.Nobjects[NODE])];
        if ((marked == null)) {
            F_report.report_writeErrorMsg(ERR_MEMORY, L0);
            return;
        }
        L_f1: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            j = F_swmm5.Link[i].node2;
            if ((F_swmm5.Link[i].direction < 0)) {
                j = F_swmm5.Link[i].node1;
            }
            if (((((F_swmm5.Link[i].type == CONDUIT) && (F_swmm5.Link[i].xsect.type == DUMMY))) || (((F_swmm5.Link[i].type == PUMP) && (F_swmm5.Pump[F_swmm5.Link[i].subIndex].type == IDEAL_PUMP))))) {
                if ((marked[j] == 0)) {
                    marked[j] = 1;
                }
            } else {
                marked[j] = (-1);
            }
        }
        L_f2: for (i = 0; (i < F_swmm5.Nobjects[LINK]); i++) {
            if (((((F_swmm5.Link[i].type == CONDUIT) && (F_swmm5.Link[i].xsect.type == DUMMY))) || (((F_swmm5.Link[i].type == PUMP) && (F_swmm5.Pump[F_swmm5.Link[i].subIndex].type == IDEAL_PUMP))))) {
                j = F_swmm5.Link[i].node1;
                if ((marked[j] > 0)) {
                    F_report.report_writeErrorMsg(ERR_DUMMY_LINK, F_swmm5.Node_[j].ID);
                }
            }
        }
        if ((marked != null)) {
            R.free(marked);
            marked = (null);
        }
    }

}
