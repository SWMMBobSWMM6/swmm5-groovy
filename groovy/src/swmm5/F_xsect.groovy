// Generated from EPA SWMM 5.2.4 (xsect.c) by tools/c2x/emit_groovy.py. Do not edit by hand;
// fix the translator, R.groovy or Overrides.groovy instead.
package swmm5

import groovy.transform.CompileStatic
import static swmm5.Consts.*

@CompileStatic
final class F_xsect {
    public static int N_A_Circ = 51;
    public static double[] A_Circ = new double[] { 0.0d, 0.00471d, 0.0134d, 0.024446d, 0.0374d, 0.05208d, 0.0680d, 0.08505d, 0.1033d, 0.12236d, 0.1423d, 0.16310d, 0.1845d, 0.20665d, 0.2292d, 0.25236d, 0.2759d, 0.29985d, 0.3242d, 0.34874d, 0.3736d, 0.39878d, 0.4237d, 0.44907d, 0.4745d, 0.500d, 0.5255d, 0.55093d, 0.5763d, 0.60135d, 0.6264d, 0.65126d, 0.6758d, 0.70015d, 0.7241d, 0.74764d, 0.7708d, 0.79335d, 0.8154d, 0.83690d, 0.8576d, 0.87764d, 0.8967d, 0.91495d, 0.9320d, 0.94792d, 0.9626d, 0.97555d, 0.9866d, 0.99516d, 1.000d };
    public static int N_R_Circ = 51;
    public static double[] R_Circ = new double[] { 0.0100d, 0.0528d, 0.1048d, 0.1556d, 0.2052d, 0.254d, 0.3016d, 0.3484d, 0.3944d, 0.4388d, 0.4824d, 0.5248d, 0.5664d, 0.6064d, 0.6456d, 0.6836d, 0.7204d, 0.7564d, 0.7912d, 0.8244d, 0.8568d, 0.888d, 0.9176d, 0.9464d, 0.9736d, 1.00d, 1.024d, 1.048d, 1.070d, 1.0912d, 1.110d, 1.1272d, 1.144d, 1.1596d, 1.174d, 1.1848d, 1.194d, 1.2024d, 1.210d, 1.2148d, 1.217d, 1.2172d, 1.215d, 1.2104d, 1.203d, 1.192d, 1.178d, 1.1584d, 1.132d, 1.094d, 1.000d };
    public static int N_Y_Circ = 51;
    public static double[] Y_Circ = new double[] { 0.0d, 0.05236d, 0.08369d, 0.11025d, 0.13423d, 0.15643d, 0.17755d, 0.19772d, 0.21704d, 0.23581d, 0.25412d, 0.27194d, 0.28948d, 0.30653d, 0.32349d, 0.34017d, 0.35666d, 0.37298d, 0.38915d, 0.40521d, 0.42117d, 0.43704d, 0.45284d, 0.46858d, 0.4843d, 0.50000d, 0.51572d, 0.53146d, 0.54723d, 0.56305d, 0.57892d, 0.59487d, 0.61093d, 0.62710d, 0.64342d, 0.65991d, 0.67659d, 0.69350d, 0.71068d, 0.72816d, 0.74602d, 0.76424d, 0.78297d, 0.80235d, 0.82240d, 0.84353d, 0.86563d, 0.88970d, 0.91444d, 0.94749d, 1.0d };
    public static int N_S_Circ = 51;
    public static double[] S_Circ = new double[] { 0.0d, 0.00529d, 0.01432d, 0.02559d, 0.03859d, 0.05304d, 0.06877d, 0.08551d, 0.10326d, 0.12195d, 0.14144d, 0.16162d, 0.18251d, 0.2041d, 0.22636d, 0.24918d, 0.27246d, 0.29614d, 0.32027d, 0.34485d, 0.36989d, 0.39531d, 0.42105d, 0.44704d, 0.47329d, 0.4998d, 0.52658d, 0.55354d, 0.58064d, 0.60777d, 0.63499d, 0.66232d, 0.68995d, 0.7177d, 0.74538d, 0.77275d, 0.79979d, 0.82658d, 0.8532d, 0.87954d, 0.90546d, 0.93095d, 0.95577d, 0.97976d, 1.00291d, 1.02443d, 1.04465d, 1.06135d, 1.08208d, 1.07662d, 1.0d };
    public static int N_W_Circ = 51;
    public static double[] W_Circ = new double[] { 0.0d, 0.2800d, 0.3919d, 0.4750d, 0.5426d, 0.6000d, 0.6499d, 0.6940d, 0.7332d, 0.7684d, 0.8000d, 0.8285d, 0.8542d, 0.8773d, 0.8980d, 0.9165d, 0.9330d, 0.9474d, 0.9600d, 0.9708d, 0.9798d, 0.9871d, 0.9928d, 0.9968d, 0.9992d, 1.000d, 0.9992d, 0.9968d, 0.9928d, 0.9871d, 0.9798d, 0.9708d, 0.9600d, 0.9474d, 0.9330d, 0.9165d, 0.8980d, 0.8773d, 0.8542d, 0.8285d, 0.8000d, 0.7684d, 0.7332d, 0.6940d, 0.6499d, 0.6000d, 0.5426d, 0.4750d, 0.3919d, 0.2800d, 0.0d };
    public static int N_A_Egg = 26;
    public static double[] A_Egg = new double[] { 0.0000d, 0.0116d, 0.0316d, 0.0550d, 0.0850d, 0.1200d, 0.1555d, 0.1900d, 0.2250d, 0.2750d, 0.3200d, 0.3700d, 0.4200d, 0.4700d, 0.5150d, 0.5700d, 0.6200d, 0.6800d, 0.7300d, 0.7800d, 0.8350d, 0.8850d, 0.9250d, 0.9550d, 0.9800d, 1.000d };
    public static int N_R_Egg = 26;
    public static double[] R_Egg = new double[] { 0.0100d, 0.1300d, 0.2440d, 0.3020d, 0.3860d, 0.4650d, 0.5360d, 0.6110d, 0.6760d, 0.7350d, 0.7910d, 0.8540d, 0.9040d, 0.9410d, 1.008d, 1.045d, 1.076d, 1.115d, 1.146d, 1.162d, 1.186d, 1.193d, 1.186d, 1.162d, 1.107d, 1.000d };
    public static int N_Y_Egg = 51;
    public static double[] Y_Egg = new double[] { 0.0d, 0.04912d, 0.08101d, 0.11128d, 0.14161d, 0.16622d, 0.18811d, 0.21356d, 0.23742d, 0.25742d, 0.27742d, 0.29741d, 0.31742d, 0.33742d, 0.35747d, 0.37364d, 0.4d, 0.41697d, 0.43372d, 0.45d, 0.46374d, 0.47747d, 0.49209d, 0.50989d, 0.53015d, 0.55d, 0.56429d, 0.57675d, 0.58834d, 0.6d, 0.61441d, 0.62967d, 0.64582d, 0.66368d, 0.68209d, 0.7d, 0.71463d, 0.72807d, 0.74074d, 0.75296d, 0.765d, 0.77784d, 0.79212d, 0.80945d, 0.82936d, 0.85d, 0.86731d, 0.88769d, 0.914d, 0.95d, 1.0d };
    public static int N_S_Egg = 51;
    public static double[] S_Egg = new double[] { 0.0d, 0.00295d, 0.01331d, 0.02629d, 0.04d, 0.05657d, 0.075d, 0.09432d, 0.11473d, 0.13657d, 0.15894d, 0.1803d, 0.20036d, 0.22d, 0.23919d, 0.25896d, 0.28d, 0.30504d, 0.33082d, 0.35551d, 0.37692d, 0.39809d, 0.42d, 0.44625d, 0.47321d, 0.5d, 0.52255d, 0.54481d, 0.56785d, 0.59466d, 0.62485d, 0.65518d, 0.68181d, 0.70415d, 0.72585d, 0.74819d, 0.77482d, 0.80515d, 0.83534d, 0.86193d, 0.88465d, 0.9069d, 0.93d, 0.95866d, 0.98673d, 1.01238d, 1.03396d, 1.05d, 1.06517d, 1.0538d, 1.0d };
    public static int N_W_Egg = 26;
    public static double[] W_Egg = new double[] { 0.0d, 0.3250d, 0.4270d, 0.5080d, 0.5820d, 0.6420d, 0.6960d, 0.7460d, 0.7910d, 0.8360d, 0.8660d, 0.8960d, 0.9260d, 0.9560d, 0.9700d, 0.9850d, 1.000d, 0.9850d, 0.9700d, 0.9400d, 0.8960d, 0.8360d, 0.7640d, 0.6420d, 0.3100d, 0.0d };
    public static int N_A_Horseshoe = 26;
    public static double[] A_Horseshoe = new double[] { 0.0000d, 0.0181d, 0.0508d, 0.0908d, 0.1326d, 0.1757d, 0.2201d, 0.2655d, 0.3118d, 0.3587d, 0.4064d, 0.4542d, 0.5023d, 0.5506d, 0.5987d, 0.6462d, 0.6931d, 0.7387d, 0.7829d, 0.8253d, 0.8652d, 0.9022d, 0.9356d, 0.9645d, 0.9873d, 1.000d };
    public static int N_R_Horseshoe = 26;
    public static double[] R_Horseshoe = new double[] { 0.0100d, 0.1040d, 0.2065d, 0.3243d, 0.4322d, 0.5284d, 0.6147d, 0.6927d, 0.7636d, 0.8268d, 0.8873d, 0.9417d, 0.9905d, 1.036d, 1.077d, 1.113d, 1.143d, 1.169d, 1.189d, 1.202d, 1.208d, 1.206d, 1.195d, 1.170d, 1.126d, 1.000d };
    public static int N_Y_Horseshoe = 51;
    public static double[] Y_Horseshoe = new double[] { 0.0d, 0.04146d, 0.07033d, 0.09098d, 0.10962d, 0.12921d, 0.14813d, 0.16701d, 0.18565d, 0.20401d, 0.22211d, 0.23998d, 0.25769d, 0.27524d, 0.29265d, 0.3099d, 0.32704d, 0.34406d, 0.36101d, 0.3779d, 0.39471d, 0.41147d, 0.42818d, 0.44484d, 0.46147d, 0.47807d, 0.49468d, 0.51134d, 0.52803d, 0.54474d, 0.56138d, 0.57804d, 0.59478d, 0.61171d, 0.62881d, 0.64609d, 0.6635d, 0.68111d, 0.69901d, 0.71722d, 0.73583d, 0.7549d, 0.77447d, 0.79471d, 0.81564d, 0.83759d, 0.86067d, 0.88557d, 0.91159d, 0.9452d, 1.0d };
    public static int N_S_Horseshoe = 51;
    public static double[] S_Horseshoe = new double[] { 0.0d, 0.00467d, 0.01237d, 0.02268d, 0.03515d, 0.04943d, 0.06525d, 0.08212d, 0.10005d, 0.11891d, 0.13856d, 0.15896d, 0.18004d, 0.20172d, 0.22397d, 0.24677d, 0.27006d, 0.2938d, 0.3179d, 0.34237d, 0.3672d, 0.39239d, 0.41792d, 0.44374d, 0.46984d, 0.49619d, 0.52276d, 0.5495d, 0.5764d, 0.60345d, 0.63065d, 0.65795d, 0.68531d, 0.71271d, 0.74009d, 0.76738d, 0.79451d, 0.82144d, 0.84814d, 0.8745d, 0.90057d, 0.92652d, 0.95244d, 0.97724d, 0.99988d, 1.02048d, 1.03989d, 1.05698d, 1.07694d, 1.07562d, 1.0d };
    public static int N_W_Horseshoe = 26;
    public static double[] W_Horseshoe = new double[] { 0.0d, 0.5878d, 0.8772d, 0.8900d, 0.9028d, 0.9156d, 0.9284d, 0.9412d, 0.9540d, 0.9668d, 0.9798d, 0.9928d, 0.9992d, 0.9992d, 0.9928d, 0.9798d, 0.9600d, 0.9330d, 0.8980d, 0.8542d, 0.8000d, 0.7332d, 0.6499d, 0.5426d, 0.3919d, 0.0d };
    public static int N_Y_Gothic = 51;
    public static double[] Y_Gothic = new double[] { 0.0d, 0.04522d, 0.07825d, 0.10646d, 0.12645d, 0.14645d, 0.16787d, 0.18641d, 0.20129d, 0.22425d, 0.24129d, 0.25624d, 0.27344d, 0.29097d, 0.30529d, 0.32607d, 0.33755d, 0.35073d, 0.36447d, 0.37558d, 0.4d, 0.4181d, 0.43648d, 0.45374d, 0.46805d, 0.48195d, 0.49626d, 0.51352d, 0.5319d, 0.55d, 0.56416d, 0.57787d, 0.59224d, 0.6095d, 0.62941d, 0.65d, 0.67064d, 0.69055d, 0.70721d, 0.72031d, 0.73286d, 0.74632d, 0.76432d, 0.78448d, 0.80421d, 0.82199d, 0.84363d, 0.87423d, 0.90617d, 0.93827d, 1.0d };
    public static int N_S_Gothic = 51;
    public static double[] S_Gothic = new double[] { 0.0d, 0.005d, 0.0174d, 0.03098d, 0.04272d, 0.055d, 0.0698d, 0.0862d, 0.10461d, 0.12463d, 0.145d, 0.16309d, 0.18118d, 0.2d, 0.22181d, 0.24487d, 0.26888d, 0.2938d, 0.31901d, 0.34389d, 0.36564d, 0.38612d, 0.4072d, 0.43d, 0.45868d, 0.48895d, 0.52d, 0.55032d, 0.5804d, 0.61d, 0.63762d, 0.66505d, 0.6929d, 0.72342d, 0.75467d, 0.785d, 0.81165d, 0.83654d, 0.86d, 0.88253d, 0.90414d, 0.925d, 0.94486d, 0.96475d, 0.98567d, 1.00833d, 1.03d, 1.0536d, 1.065d, 1.055d, 1.0d };
    public static int N_W_Gothic = 21;
    public static double[] W_Gothic = new double[] { 0.0d, 0.286d, 0.643d, 0.762d, 0.833d, 0.905d, 0.952d, 0.976d, 0.976d, 1.0d, 1.0d, 0.976d, 0.976d, 0.952d, 0.905d, 0.833d, 0.762d, 0.667d, 0.524d, 0.357d, 0.0d };
    public static int N_Y_Catenary = 51;
    public static double[] Y_Catenary = new double[] { 0.0d, 0.02974d, 0.06439d, 0.08433d, 0.10549d, 0.12064d, 0.13952d, 0.1556d, 0.17032d, 0.18512d, 0.20057d, 0.21995d, 0.24011d, 0.25892d, 0.27595d, 0.29214d, 0.30802d, 0.32372d, 0.33894d, 0.35315d, 0.36557d, 0.37833d, 0.3923d, 0.4097d, 0.42982d, 0.45d, 0.46769d, 0.48431d, 0.5d, 0.51466d, 0.52886d, 0.54292d, 0.55729d, 0.57223d, 0.5878d, 0.60428d, 0.62197d, 0.64047d, 0.6598d, 0.67976d, 0.7d, 0.71731d, 0.73769d, 0.76651d, 0.8d, 0.8209d, 0.84311d, 0.87978d, 0.91576d, 0.95d, 1.0d };
    public static int N_S_Catenary = 51;
    public static double[] S_Catenary = new double[] { 0.0d, 0.00605d, 0.01455d, 0.0254d, 0.03863d, 0.0543d, 0.07127d, 0.08778d, 0.10372d, 0.12081d, 0.14082d, 0.16375d, 0.18779d, 0.21157d, 0.23478d, 0.25818d, 0.28244d, 0.30741d, 0.33204d, 0.35505d, 0.37465d, 0.39404d, 0.41426d, 0.43804d, 0.46531d, 0.49357d, 0.52187d, 0.54925d, 0.57647d, 0.60321d, 0.62964d, 0.65639d, 0.68472d, 0.71425d, 0.74303d, 0.76827d, 0.79168d, 0.815d, 0.84094d, 0.86707d, 0.89213d, 0.91607d, 0.94d, 0.96604d, 0.99d, 1.00714d, 1.02158d, 1.03814d, 1.05d, 1.05d, 1.0d };
    public static int N_W_Catenary = 21;
    public static double[] W_Catenary = new double[] { 0.0d, 0.6667d, 0.8222d, 0.9111d, 0.9778d, 1.0000d, 1.0000d, 0.9889d, 0.9778d, 0.9556d, 0.9333d, 0.8889d, 0.8444d, 0.8000d, 0.7556d, 0.7000d, 0.6333d, 0.5556d, 0.4444d, 0.3333d, 0.0d };
    public static int N_Y_SemiEllip = 51;
    public static double[] Y_SemiEllip = new double[] { 0.0d, 0.03075d, 0.05137d, 0.07032d, 0.09d, 0.11323d, 0.13037d, 0.14519d, 0.15968d, 0.18459d, 0.19531d, 0.21354d, 0.22694d, 0.23947d, 0.25296d, 0.265d, 0.27784d, 0.29212d, 0.3097d, 0.32982d, 0.35d, 0.36738d, 0.3839d, 0.4d, 0.41667d, 0.43333d, 0.45d, 0.46697d, 0.48372d, 0.5d, 0.51374d, 0.52747d, 0.54209d, 0.5595d, 0.57941d, 0.6d, 0.62d, 0.64d, 0.66d, 0.68d, 0.7d, 0.71843d, 0.73865d, 0.76365d, 0.7926d, 0.82088d, 0.85d, 0.88341d, 0.90998d, 0.93871d, 1.0d };
    public static int N_S_SemiEllip = 51;
    public static double[] S_SemiEllip = new double[] { 0.0d, 0.00438d, 0.01227d, 0.02312d, 0.03638d, 0.05145d, 0.06783d, 0.085d, 0.10093d, 0.11752d, 0.1353d, 0.15626d, 0.17917d, 0.20296d, 0.22654d, 0.24962d, 0.27269d, 0.29568d, 0.31848d, 0.34152d, 0.365d, 0.38941d, 0.41442d, 0.44d, 0.46636d, 0.49309d, 0.52d, 0.54628d, 0.57285d, 0.6d, 0.62949d, 0.65877d, 0.68624d, 0.71017d, 0.73304d, 0.75578d, 0.77925d, 0.80368d, 0.83114d, 0.8595d, 0.88592d, 0.90848d, 0.93d, 0.95292d, 0.97481d, 0.99374d, 1.01084d, 1.02858d, 1.04543d, 1.05d, 1.0d };
    public static int N_W_SemiEllip = 21;
    public static double[] W_SemiEllip = new double[] { 0.0d, 0.7000d, 0.9800d, 1.0000d, 1.0000d, 1.0000d, 0.9900d, 0.9800d, 0.9600d, 0.9400d, 0.9100d, 0.8800d, 0.8400d, 0.8000d, 0.7500d, 0.7000d, 0.6400d, 0.5600d, 0.4600d, 0.3400d, 0.0d };
    public static int N_A_Baskethandle = 26;
    public static double[] A_Baskethandle = new double[] { 0.0000d, 0.0173d, 0.0457d, 0.0828d, 0.1271d, 0.1765d, 0.2270d, 0.2775d, 0.3280d, 0.3780d, 0.4270d, 0.4765d, 0.5260d, 0.5740d, 0.6220d, 0.6690d, 0.7160d, 0.7610d, 0.8030d, 0.8390d, 0.8770d, 0.9110d, 0.9410d, 0.9680d, 0.9880d, 1.000d };
    public static int N_R_Baskethandle = 26;
    public static double[] R_Baskethandle = new double[] { 0.0100d, 0.0952d, 0.1890d, 0.2730d, 0.3690d, 0.4630d, 0.5600d, 0.6530d, 0.7430d, 0.8220d, 0.8830d, 0.9490d, 0.9990d, 1.055d, 1.095d, 1.141d, 1.161d, 1.188d, 1.206d, 1.206d, 1.206d, 1.205d, 1.196d, 1.168d, 1.127d, 1.000d };
    public static int N_Y_BasketHandle = 51;
    public static double[] Y_BasketHandle = new double[] { 0.0d, 0.04112d, 0.0738d, 0.1d, 0.12236d, 0.14141d, 0.15857d, 0.17462d, 0.18946d, 0.20315d, 0.21557d, 0.22833d, 0.2423d, 0.25945d, 0.27936d, 0.3d, 0.3204d, 0.34034d, 0.35892d, 0.37595d, 0.39214d, 0.40802d, 0.42372d, 0.43894d, 0.45315d, 0.46557d, 0.47833d, 0.4923d, 0.50945d, 0.52936d, 0.55d, 0.57d, 0.59d, 0.61023d, 0.63045d, 0.65d, 0.66756d, 0.68413d, 0.7d, 0.71481d, 0.72984d, 0.74579d, 0.76417d, 0.78422d, 0.80477d, 0.82532d, 0.85d, 0.88277d, 0.915d, 0.95d, 1.0d };
    public static int N_S_BasketHandle = 51;
    public static double[] S_BasketHandle = new double[] { 0.0d, 0.00758d, 0.01812d, 0.03d, 0.03966d, 0.04957d, 0.0623d, 0.07849d, 0.09618d, 0.11416d, 0.13094d, 0.14808d, 0.16583d, 0.18381d, 0.20294d, 0.225d, 0.2547d, 0.28532d, 0.31006d, 0.32804d, 0.34555d, 0.36944d, 0.40032d, 0.43203d, 0.46004d, 0.47849d, 0.49591d, 0.51454d, 0.5381d, 0.56711d, 0.6d, 0.64092d, 0.68136d, 0.71259d, 0.73438d, 0.755d, 0.78625d, 0.8188d, 0.85d, 0.8679d, 0.88483d, 0.90431d, 0.9369d, 0.97388d, 1.00747d, 1.033d, 1.05d, 1.05464d, 1.06078d, 1.055d, 1.0d };
    public static int N_W_BasketHandle = 26;
    public static double[] W_BasketHandle = new double[] { 0.0d, 0.49d, 0.667d, 0.82d, 0.93d, 1.00d, 1.00d, 1.00d, 0.997d, 0.994d, 0.988d, 0.982d, 0.967d, 0.948d, 0.928d, 0.904d, 0.874d, 0.842d, 0.798d, 0.75d, 0.697d, 0.637d, 0.567d, 0.467d, 0.342d, 0.0d };
    public static int N_Y_SemiCirc = 51;
    public static double[] Y_SemiCirc = new double[] { 0.0d, 0.04102d, 0.07407d, 0.1d, 0.11769d, 0.13037d, 0.14036d, 0.15d, 0.16546d, 0.18213d, 0.2d, 0.22018d, 0.2403d, 0.25788d, 0.27216d, 0.285d, 0.29704d, 0.30892d, 0.32128d, 0.33476d, 0.35d, 0.36927d, 0.38963d, 0.41023d, 0.43045d, 0.45d, 0.46769d, 0.48431d, 0.5d, 0.51443d, 0.52851d, 0.54271d, 0.55774d, 0.57388d, 0.59101d, 0.60989d, 0.63005d, 0.65d, 0.66682d, 0.68318d, 0.7d, 0.71675d, 0.73744d, 0.76651d, 0.8d, 0.8209d, 0.84311d, 0.87978d, 0.91576d, 0.95d, 1.0d };
    public static int N_S_SemiCirc = 51;
    public static double[] S_SemiCirc = new double[] { 0.0d, 0.00757d, 0.01815d, 0.03d, 0.0358d, 0.04037d, 0.04601d, 0.055d, 0.07475d, 0.09834d, 0.125d, 0.1557d, 0.18588d, 0.20883d, 0.223d, 0.23472d, 0.24667d, 0.26758d, 0.29346d, 0.32124d, 0.35d, 0.3772d, 0.4054d, 0.43541d, 0.46722d, 0.5d, 0.53532d, 0.56935d, 0.6d, 0.61544d, 0.62811d, 0.6417d, 0.66598d, 0.7001d, 0.73413d, 0.76068d, 0.78027d, 0.8d, 0.82891d, 0.85964d, 0.89d, 0.9127d, 0.93664d, 0.96677d, 1.0d, 1.02661d, 1.04631d, 1.05726d, 1.06637d, 1.06d, 1.0d };
    public static int N_W_SemiCirc = 21;
    public static double[] W_SemiCirc = new double[] { 0.0d, 0.5488d, 0.8537d, 1.0000d, 1.0000d, 0.9939d, 0.9878d, 0.9756d, 0.9634d, 0.9451d, 0.9207d, 0.8902d, 0.8537d, 0.8171d, 0.7683d, 0.7073d, 0.6463d, 0.5732d, 0.4756d, 0.3354d, 0.0d };
    public static int NumCodesEllipse = 23;
    public static double[] MinorAxis_Ellipse = new double[] { 14, 19, 22, 24, 27, 29, 32, 34, 38, 43, 48, 53, 58, 63, 68, 72, 77, 82, 87, 92, 97, 106, 116 };
    public static double[] MajorAxis_Ellipse = new double[] { 23, 30, 34, 38, 42, 45, 49, 53, 60, 68, 76, 83, 91, 98, 106, 113, 121, 128, 136, 143, 151, 166, 180 };
    public static double[] Afull_Ellipse = new double[] { 1.80d, 3.30d, 4.10d, 5.10d, 6.30d, 7.40d, 8.80d, 10.20d, 12.90d, 16.60d, 20.50d, 24.80d, 29.50d, 34.60d, 40.10d, 46.10d, 52.40d, 59.20d, 66.40d, 74.00d, 82.00d, 99.20d, 118.60d };
    public static double[] Rfull_Ellipse = new double[] { 0.367d, 0.490d, 0.546d, 0.613d, 0.686d, 0.736d, 0.812d, 0.875d, 0.969d, 1.106d, 1.229d, 1.352d, 1.475d, 1.598d, 1.721d, 1.845d, 1.967d, 2.091d, 2.215d, 2.340d, 2.461d, 2.707d, 2.968d };
    public static int N_A_HorizEllipse = 26;
    public static double[] A_HorizEllipse = new double[] { 0.0000d, 0.0150d, 0.0400d, 0.0650d, 0.0950d, 0.1300d, 0.1650d, 0.2050d, 0.2500d, 0.3000d, 0.3550d, 0.4150d, 0.4800d, 0.5200d, 0.5850d, 0.6450d, 0.7000d, 0.7500d, 0.7950d, 0.8350d, 0.8700d, 0.9050d, 0.9350d, 0.9600d, 0.9850d, 1.000d };
    public static int N_R_HorizEllipse = 26;
    public static double[] R_HorizEllipse = new double[] { 0.0100d, 0.0764d, 0.1726d, 0.2389d, 0.3274d, 0.4191d, 0.5120d, 0.5983d, 0.6757d, 0.7630d, 0.8326d, 0.9114d, 0.9702d, 1.030d, 1.091d, 1.146d, 1.185d, 1.225d, 1.257d, 1.274d, 1.290d, 1.282d, 1.274d, 1.257d, 1.185d, 1.000d };
    public static int N_W_HorizEllipse = 26;
    public static double[] W_HorizEllipse = new double[] { 0.0d, 0.3919d, 0.5426d, 0.6499d, 0.7332d, 0.8000d, 0.8542d, 0.8980d, 0.9330d, 0.9600d, 0.9798d, 0.9928d, 0.9992d, 0.9992d, 0.9928d, 0.9798d, 0.9600d, 0.9330d, 0.8980d, 0.8542d, 0.8000d, 0.7332d, 0.6499d, 0.5426d, 0.3919d, 0.0d };
    public static int N_A_VertEllipse = 26;
    public static double[] A_VertEllipse = new double[] { 0.0000d, 0.0100d, 0.0400d, 0.0700d, 0.1000d, 0.1400d, 0.1850d, 0.2300d, 0.2800d, 0.3300d, 0.3800d, 0.4300d, 0.4800d, 0.5200d, 0.5700d, 0.6200d, 0.6700d, 0.7200d, 0.7700d, 0.8150d, 0.8600d, 0.9000d, 0.9300d, 0.9600d, 0.9900d, 1.000d };
    public static int N_R_VertEllipse = 26;
    public static double[] R_VertEllipse = new double[] { 0.0100d, 0.1250d, 0.2436d, 0.3536d, 0.4474d, 0.5484d, 0.6366d, 0.7155d, 0.7768d, 0.8396d, 0.8969d, 0.9480d, 0.9925d, 1.023d, 1.053d, 1.084d, 1.107d, 1.130d, 1.154d, 1.170d, 1.177d, 1.177d, 1.170d, 1.162d, 1.122d, 1.000d };
    public static int N_W_VertEllipse = 26;
    public static double[] W_VertEllipse = new double[] { 0.0d, 0.3919d, 0.5426d, 0.6499d, 0.7332d, 0.8000d, 0.8542d, 0.8980d, 0.9330d, 0.9600d, 0.9798d, 0.9928d, 0.9992d, 0.9992d, 0.9928d, 0.9798d, 0.9600d, 0.9330d, 0.8980d, 0.8542d, 0.8000d, 0.7332d, 0.6499d, 0.5426d, 0.3919d, 0.0d };
    public static int NumCodesArch = 102;
    public static double[] Yfull_Arch = new double[] { 11, 13.5d, 15.5d, 18, 22.5d, 26.625d, 31.3125d, 36, 40, 45, 54, 62, 72, 77.5d, 87.125d, 96.875d, 106.5d, 13, 15, 18, 20, 24, 29, 33, 38, 43, 47, 52, 57, 31, 36, 41, 46, 51, 55, 59, 63, 67, 71, 75, 79, 83, 87, 91, 55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75, 77, 79, 81, 83, 85, 87, 89, 91, 93, 95, 97, 100, 101, 103, 105, 107, 109, 111, 113, 115, 118, 119, 121, 112, 114, 116, 118, 120, 122, 124, 126, 128, 130, 132, 134, 136, 138, 140, 142, 144, 146, 148, 150, 152, 154, 156, 158 };
    public static double[] Wmax_Arch = new double[] { 18, 22, 26, 28.5d, 36.25d, 43.75d, 51.125d, 58.5d, 65, 73, 88, 102, 115, 122, 138, 154, 168.75d, 17, 21, 24, 28, 35, 42, 49, 57, 64, 71, 77, 83, 40, 46, 53, 60, 66, 73, 81, 87, 95, 103, 112, 117, 128, 137, 142, 73, 76, 81, 84, 87, 92, 95, 98, 103, 106, 112, 114, 117, 123, 128, 131, 137, 139, 142, 148, 150, 152, 154, 161, 167, 169, 171, 178, 184, 186, 188, 190, 197, 199, 159, 162, 168, 170, 173, 179, 184, 187, 190, 195, 198, 204, 206, 209, 215, 217, 223, 225, 231, 234, 236, 239, 245, 247 };
    public static double[] Afull_Arch = new double[] { 1.1d, 1.65d, 2.2d, 2.8d, 4.4d, 6.4d, 8.8d, 11.4d, 14.3d, 17.7d, 25.6d, 34.6d, 44.5d, 51.7d, 66, 81.8d, 99.1d, 1.1d, 1.6d, 2.2d, 2.9d, 4.5d, 6.5d, 8.9d, 11.6d, 14.7d, 18.1d, 21.9d, 26, 7, 9.4d, 12.3d, 15.6d, 19.3d, 23.2d, 27.4d, 32.1d, 37, 42.4d, 48, 54.2d, 60.5d, 67.4d, 74.5d, 22, 24, 26, 28, 31, 33, 35, 38, 40, 43, 46, 49, 52, 55, 58, 61, 64, 67, 71, 74, 78, 81, 85, 89, 93, 97, 101, 105, 109, 113, 118, 122, 126, 131, 97, 102, 105, 109, 114, 118, 123, 127, 132, 137, 142, 146, 151, 157, 161, 167, 172, 177, 182, 188, 194, 200, 205, 211 };
    public static double[] Rfull_Arch = new double[] { 0.25d, 0.3d, 0.36d, 0.45d, 0.56d, 0.68d, 0.8d, 0.9d, 1.01d, 1.13d, 1.35d, 1.57d, 1.77d, 1.92d, 2.17d, 2.42d, 2.65d, 0.324d, 0.374d, 0.449d, 0.499d, 0.598d, 0.723d, 0.823d, 0.947d, 1.072d, 1.171d, 1.296d, 1.421d, 0.773d, 0.773d, 1.022d, 1.147d, 1.271d, 1.371d, 1.471d, 1.570d, 1.670d, 1.770d, 1.869d, 1.969d, 2.069d, 2.168d, 2.268d, 1.371d, 1.421d, 1.471d, 1.520d, 1.570d, 1.620d, 1.670d, 1.720d, 1.770d, 1.820d, 1.869d, 1.919d, 1.969d, 2.019d, 2.069d, 2.119d, 2.168d, 2.218d, 2.268d, 2.318d, 2.368d, 2.418d, 2.493d, 2.517d, 2.567d, 2.617d, 2.667d, 2.717d, 2.767d, 2.817d, 2.866d, 2.941d, 2.966d, 3.016d, 2.792d, 2.841d, 2.891d, 2.941d, 2.991d, 3.041d, 3.091d, 3.141d, 3.190d, 3.240d, 3.290d, 3.340d, 3.390d, 3.440d, 3.490d, 3.539d, 3.589d, 3.639d, 3.689d, 3.739d, 3.789d, 3.838d, 3.888d, 3.938d };
    public static int N_A_Arch = 26;
    public static double[] A_Arch = new double[] { 0.0000d, 0.0200d, 0.0600d, 0.1000d, 0.1400d, 0.1900d, 0.2400d, 0.2900d, 0.3400d, 0.3900d, 0.4400d, 0.4900d, 0.5400d, 0.5900d, 0.6400d, 0.6900d, 0.7350d, 0.7800d, 0.8200d, 0.8600d, 0.8950d, 0.9300d, 0.9600d, 0.9850d, 0.9950d, 1.000d };
    public static int N_R_Arch = 26;
    public static double[] R_Arch = new double[] { 0.0100d, 0.0983d, 0.1965d, 0.2948d, 0.3940d, 0.4962d, 0.5911d, 0.6796d, 0.7615d, 0.8364d, 0.9044d, 0.9640d, 1.018d, 1.065d, 1.106d, 1.142d, 1.170d, 1.192d, 1.208d, 1.217d, 1.220d, 1.213d, 1.196d, 1.168d, 1.112d, 1.000d };
    public static int N_W_Arch = 26;
    public static double[] W_Arch = new double[] { 0.0d, 0.6272d, 0.8521d, 0.9243d, 0.9645d, 0.9846d, 0.9964d, 0.9988d, 0.9917d, 0.9811d, 0.9680d, 0.9515d, 0.9314d, 0.9101d, 0.8864d, 0.8592d, 0.8284d, 0.7917d, 0.7527d, 0.7065d, 0.6544d, 0.5953d, 0.5231d, 0.4355d, 0.3195d, 0.0d };
    public static double[] Amax = new double[] { 1.0d, 0.9756d, 0.9756d, 0.97d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 0.98d, 0.98d, 0.96d, 0.96d, 0.96d, 0.92d, 0.96d, 0.96d, 0.96d, 0.98d, 0.98d, 0.96d, 0.96d, 1.0d, 0.96d, 0.9756d, 1.0d };

    // xsect.c:204
    static int xsect_isOpen(int type) {
        return (((R.dge(Amax[type], 1.0d)) ? 1 : 0));
    }

    // xsect.c:216
    static int xsect_setParams(TXsect xsect, int type, double[] p, double ucf) {
        int index = 0;
        double aMax = 0.0d;
        double theta = 0.0d;
        if (((type != DUMMY) && (p[0] <= 0.0d))) {
            return 0;
        }
        xsect.@type = type;
        int __sw1 = xsect.type;
        if (__sw1 == DUMMY) {
            xsect.@yFull = 1e-06d;
            xsect.@wMax = 1e-06d;
            xsect.@aFull = 1e-06d;
            xsect.@rFull = 1e-06d;
            xsect.@sFull = 1e-06d;
            xsect.@sMax = 1e-06d;
        } else if (__sw1 == CIRCULAR) {
            xsect.@yFull = (p[0] / ucf);
            xsect.@wMax = xsect.yFull;
            xsect.@aFull = (((3.141592654d / 4.0d) * xsect.yFull) * xsect.yFull);
            xsect.@rFull = (0.2500d * xsect.yFull);
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = (1.08d * xsect.sFull);
            xsect.@ywMax = (0.5d * xsect.yFull);
        } else if (__sw1 == FORCE_MAIN) {
            xsect.@yFull = (p[0] / ucf);
            xsect.@wMax = xsect.yFull;
            xsect.@aFull = (((3.141592654d / 4.0d) * xsect.yFull) * xsect.yFull);
            xsect.@rFull = (0.2500d * xsect.yFull);
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, 0.63d));
            xsect.@sMax = (1.06949d * xsect.sFull);
            xsect.@ywMax = (0.5d * xsect.yFull);
            xsect.@rBot = p[1];
        } else if (__sw1 == FILLED_CIRCULAR) {
            if (R.dge(p[1], p[0])) {
                return 0;
            }
            xsect.@yFull = (p[0] / ucf);
            xsect.@wMax = xsect.yFull;
            xsect.@aFull = (((3.141592654d / 4.0d) * xsect.yFull) * xsect.yFull);
            xsect.@rFull = (0.2500d * xsect.yFull);
            xsect.@yBot = (p[1] / ucf);
            xsect.@aBot = circ_getAofY(xsect, xsect.yBot);
            xsect.@sBot = xsect_getWofY(xsect, xsect.yBot);
            xsect.@rBot = (xsect.aBot / ((xsect.rFull * lookup((xsect.yBot / xsect.yFull), R_Circ, N_R_Circ))));
            xsect.@aFull = (xsect.aFull - xsect.aBot);
            xsect.@rFull = (xsect.aFull / ((((3.141592654d * xsect.yFull) - xsect.rBot) + xsect.sBot)));
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = (1.08d * xsect.sFull);
            xsect.@yFull = (xsect.yFull - xsect.yBot);
            xsect.@ywMax = (0.5d * xsect.yFull);
        } else if (__sw1 == EGGSHAPED) {
            xsect.@yFull = (p[0] / ucf);
            xsect.@aFull = ((0.5105d * xsect.yFull) * xsect.yFull);
            xsect.@rFull = (0.1931d * xsect.yFull);
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = (1.065d * xsect.sFull);
            xsect.@wMax = ((2.0d / 3.0d) * xsect.yFull);
            xsect.@ywMax = (0.64d * xsect.yFull);
        } else if (__sw1 == HORSESHOE) {
            xsect.@yFull = (p[0] / ucf);
            xsect.@aFull = ((0.8293d * xsect.yFull) * xsect.yFull);
            xsect.@rFull = (0.2538d * xsect.yFull);
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = (1.077d * xsect.sFull);
            xsect.@wMax = (1.0d * xsect.yFull);
            xsect.@ywMax = (0.5d * xsect.yFull);
        } else if (__sw1 == GOTHIC) {
            xsect.@yFull = (p[0] / ucf);
            xsect.@aFull = ((0.6554d * xsect.yFull) * xsect.yFull);
            xsect.@rFull = (0.2269d * xsect.yFull);
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = (1.065d * xsect.sFull);
            xsect.@wMax = (0.84d * xsect.yFull);
            xsect.@ywMax = (0.45d * xsect.yFull);
        } else if (__sw1 == CATENARY) {
            xsect.@yFull = (p[0] / ucf);
            xsect.@aFull = ((0.70277d * xsect.yFull) * xsect.yFull);
            xsect.@rFull = (0.23172d * xsect.yFull);
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = (1.05d * xsect.sFull);
            xsect.@wMax = (0.9d * xsect.yFull);
            xsect.@ywMax = (0.25d * xsect.yFull);
        } else if (__sw1 == SEMIELLIPTICAL) {
            xsect.@yFull = (p[0] / ucf);
            xsect.@aFull = ((0.785d * xsect.yFull) * xsect.yFull);
            xsect.@rFull = (0.242d * xsect.yFull);
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = (1.045d * xsect.sFull);
            xsect.@wMax = (1.0d * xsect.yFull);
            xsect.@ywMax = (0.15d * xsect.yFull);
        } else if (__sw1 == BASKETHANDLE) {
            xsect.@yFull = (p[0] / ucf);
            xsect.@aFull = ((0.7862d * xsect.yFull) * xsect.yFull);
            xsect.@rFull = (0.2464d * xsect.yFull);
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = (1.06078d * xsect.sFull);
            xsect.@wMax = (0.944d * xsect.yFull);
            xsect.@ywMax = (0.2d * xsect.yFull);
        } else if (__sw1 == SEMICIRCULAR) {
            xsect.@yFull = (p[0] / ucf);
            xsect.@aFull = ((1.2697d * xsect.yFull) * xsect.yFull);
            xsect.@rFull = (0.2946d * xsect.yFull);
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = (1.06637d * xsect.sFull);
            xsect.@wMax = (1.64d * xsect.yFull);
            xsect.@ywMax = (0.15d * xsect.yFull);
        } else if (__sw1 == RECT_CLOSED) {
            if ((p[1] <= 0.0d)) {
                return 0;
            }
            xsect.@yFull = (p[0] / ucf);
            xsect.@wMax = (p[1] / ucf);
            xsect.@aFull = (xsect.yFull * xsect.wMax);
            xsect.@rFull = (xsect.aFull / ((2.0d * ((xsect.yFull + xsect.wMax)))));
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            aMax = (0.97d * xsect.aFull);
            xsect.@sMax = (aMax * Glibm.pow(rect_closed_getRofA(xsect, aMax), (2.0d / 3.0d)));
            xsect.@ywMax = xsect.yFull;
        } else if (__sw1 == RECT_OPEN) {
            if ((p[1] <= 0.0d)) {
                return 0;
            }
            xsect.@yFull = (p[0] / ucf);
            xsect.@wMax = (p[1] / ucf);
            if (((p[2] < 0.0d) || (R.dgt(p[2], 2.0d)))) {
                return 0;
            }
            xsect.@sBot = p[2];
            xsect.@aFull = (xsect.yFull * xsect.wMax);
            xsect.@rFull = (xsect.aFull / (((((2.0d - xsect.sBot)) * xsect.yFull) + xsect.wMax)));
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = xsect.sFull;
            xsect.@ywMax = xsect.yFull;
        } else if (__sw1 == RECT_TRIANG) {
            if (((p[1] <= 0.0d) || (p[2] <= 0.0d))) {
                return 0;
            }
            xsect.@yFull = (p[0] / ucf);
            xsect.@wMax = (p[1] / ucf);
            xsect.@yBot = (p[2] / ucf);
            xsect.@ywMax = xsect.yFull;
            xsect.@aBot = ((xsect.yBot * xsect.wMax) / 2.0d);
            xsect.@sBot = ((xsect.wMax / xsect.yBot) / 2.0d);
            xsect.@rBot = Math.sqrt((1.0d + (xsect.sBot * xsect.sBot)));
            xsect.@aFull = (xsect.wMax * ((xsect.yFull - (xsect.yBot / 2.0d))));
            xsect.@rFull = (xsect.aFull / (((((2.0d * xsect.yBot) * xsect.rBot) + (2.0d * ((xsect.yFull - xsect.yBot)))) + xsect.wMax)));
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            aMax = (0.98d * xsect.aFull);
            xsect.@sMax = (aMax * Glibm.pow(rect_triang_getRofA(xsect, aMax), (2.0d / 3.0d)));
        } else if (__sw1 == RECT_ROUND) {
            if ((p[1] <= 0.0d)) {
                return 0;
            }
            if ((p[2] < (p[1] / 2.0d))) {
                p[2] = (p[1] / 2.0d);
            }
            xsect.@yFull = (p[0] / ucf);
            xsect.@wMax = (p[1] / ucf);
            xsect.@rBot = (p[2] / ucf);
            theta = (2.0d * Glibm.asin(((xsect.wMax / 2.0d) / xsect.rBot)));
            xsect.@aBot = (((xsect.rBot * xsect.rBot) / 2.0d) * ((theta - Glibm.sin(theta))));
            xsect.@sBot = (((3.141592654d * xsect.rBot) * xsect.rBot) * Glibm.pow((xsect.rBot / 2.0d), (2.0d / 3.0d)));
            xsect.@yBot = (xsect.rBot * ((1.0d - Glibm.cos((theta / 2.0d)))));
            if (R.dgt(xsect.yBot, xsect.yFull)) {
                return 0;
            }
            xsect.@ywMax = xsect.yFull;
            xsect.@aFull = ((xsect.wMax * ((xsect.yFull - xsect.yBot))) + xsect.aBot);
            xsect.@rFull = (xsect.aFull / ((((xsect.rBot * theta) + (2.0d * ((xsect.yFull - xsect.yBot)))) + xsect.wMax)));
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            aMax = (0.98d * xsect.aFull);
            xsect.@sMax = (aMax * Glibm.pow(rect_round_getRofA(xsect, aMax), (2.0d / 3.0d)));
        } else if (__sw1 == MOD_BASKET) {
            if ((p[1] <= 0.0d)) {
                return 0;
            }
            if ((p[2] < (p[1] / 2.0d))) {
                p[2] = (p[1] / 2.0d);
            }
            xsect.@yFull = (p[0] / ucf);
            xsect.@wMax = (p[1] / ucf);
            xsect.@rBot = (p[2] / ucf);
            theta = (2.0d * Glibm.asin(((xsect.wMax / 2.0d) / xsect.rBot)));
            xsect.@sBot = theta;
            xsect.@yBot = (xsect.rBot * ((1.0d - Glibm.cos((theta / 2.0d)))));
            if (R.dgt(xsect.yBot, xsect.yFull)) {
                return 0;
            }
            xsect.@ywMax = (xsect.yFull - xsect.yBot);
            xsect.@aBot = (((xsect.rBot * xsect.rBot) / 2.0d) * ((theta - Glibm.sin(theta))));
            xsect.@aFull = ((((xsect.yFull - xsect.yBot)) * xsect.wMax) + xsect.aBot);
            xsect.@rFull = (xsect.aFull / ((((xsect.rBot * theta) + (2.0d * ((xsect.yFull - xsect.yBot)))) + xsect.wMax)));
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = xsect_getSofA(xsect, (Amax[MOD_BASKET] * xsect.aFull));
        } else if (__sw1 == TRAPEZOIDAL) {
            if ((((p[1] < 0.0d) || (p[2] < 0.0d)) || (p[3] < 0.0d))) {
                return 0;
            }
            xsect.@yFull = (p[0] / ucf);
            xsect.@ywMax = xsect.yFull;
            xsect.@yBot = (p[1] / ucf);
            xsect.@sBot = (((p[2] + p[3])) / 2.0d);
            if (((xsect.yBot == 0.0d) && (xsect.sBot == 0.0d))) {
                return 0;
            }
            xsect.@rBot = (Math.sqrt((1.0d + (p[2] * p[2]))) + Math.sqrt((1.0d + (p[3] * p[3]))));
            xsect.@wMax = (xsect.yBot + (xsect.yFull * ((p[2] + p[3]))));
            xsect.@aFull = (((xsect.yBot + (xsect.sBot * xsect.yFull))) * xsect.yFull);
            xsect.@rFull = (xsect.aFull / ((xsect.yBot + (xsect.yFull * xsect.rBot))));
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = xsect.sFull;
        } else if (__sw1 == TRIANGULAR) {
            if ((p[1] <= 0.0d)) {
                return 0;
            }
            xsect.@yFull = (p[0] / ucf);
            xsect.@wMax = (p[1] / ucf);
            xsect.@ywMax = xsect.yFull;
            xsect.@sBot = ((xsect.wMax / xsect.yFull) / 2.0d);
            xsect.@rBot = Math.sqrt((1.0d + (xsect.sBot * xsect.sBot)));
            xsect.@aFull = ((xsect.yFull * xsect.yFull) * xsect.sBot);
            xsect.@rFull = (xsect.aFull / (((2.0d * xsect.yFull) * xsect.rBot)));
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = xsect.sFull;
        } else if (__sw1 == PARABOLIC) {
            if ((p[1] <= 0.0d)) {
                return 0;
            }
            xsect.@yFull = (p[0] / ucf);
            xsect.@wMax = (p[1] / ucf);
            xsect.@ywMax = xsect.yFull;
            xsect.@rBot = ((xsect.wMax / 2.0d) / Math.sqrt(xsect.yFull));
            xsect.@aFull = ((((2.0d / 3.0d)) * xsect.yFull) * xsect.wMax);
            xsect.@rFull = xsect_getRofY(xsect, xsect.yFull);
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = xsect.sFull;
        } else if (__sw1 == POWERFUNC) {
            if (((p[1] <= 0.0d) || (p[2] <= 0.0d))) {
                return 0;
            }
            xsect.@yFull = (p[0] / ucf);
            xsect.@wMax = (p[1] / ucf);
            xsect.@ywMax = xsect.yFull;
            xsect.@sBot = (1.0d / p[2]);
            xsect.@rBot = ((xsect.wMax / ((xsect.sBot + 1))) / Glibm.pow(xsect.yFull, xsect.sBot));
            xsect.@aFull = ((xsect.yFull * xsect.wMax) / ((xsect.sBot + 1)));
            xsect.@rFull = xsect_getRofY(xsect, xsect.yFull);
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = xsect.sFull;
        } else if (__sw1 == HORIZ_ELLIPSE) {
            if ((p[1] == 0.0d)) {
                p[2] = p[0];
            }
            if (R.dgt(p[2], 0.0d)) {
                index = (R.D2I(Math.floor(p[2])) - 1);
                if (((index < 0) || (index >= NumCodesEllipse))) {
                    return 0;
                }
                xsect.@yFull = (MinorAxis_Ellipse[index] / 12.0d);
                xsect.@wMax = (MajorAxis_Ellipse[index] / 12.0d);
                xsect.@aFull = Afull_Ellipse[index];
                xsect.@rFull = Rfull_Ellipse[index];
            } else {
                xsect.@yFull = (p[0] / ucf);
                if ((p[1] < 0.0d)) {
                    return 0;
                }
                xsect.@wMax = (p[1] / ucf);
                xsect.@aFull = ((1.2692d * xsect.yFull) * xsect.yFull);
                xsect.@rFull = (0.3061d * xsect.yFull);
            }
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = xsect.sFull;
            xsect.@ywMax = (0.48d * xsect.yFull);
        } else if (__sw1 == VERT_ELLIPSE) {
            if ((p[1] == 0.0d)) {
                p[2] = p[0];
            }
            if (R.dgt(p[2], 0.0d)) {
                index = (R.D2I(Math.floor(p[2])) - 1);
                if (((index < 0) || (index >= NumCodesEllipse))) {
                    return 0;
                }
                xsect.@yFull = (MajorAxis_Ellipse[index] / 12.0d);
                xsect.@wMax = (MinorAxis_Ellipse[index] / 12.0d);
                xsect.@aFull = Afull_Ellipse[index];
                xsect.@rFull = Rfull_Ellipse[index];
            } else {
                if ((p[1] < 0.0d)) {
                    return 0;
                }
                xsect.@yFull = (p[0] / ucf);
                xsect.@wMax = (p[1] / ucf);
                xsect.@aFull = ((1.2692d * xsect.wMax) * xsect.wMax);
                xsect.@rFull = (0.3061d * xsect.wMax);
            }
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = xsect.sFull;
            xsect.@ywMax = (0.48d * xsect.yFull);
        } else if (__sw1 == ARCH) {
            if ((p[1] == 0.0d)) {
                p[2] = p[0];
            }
            if (R.dgt(p[2], 0.0d)) {
                index = (R.D2I(Math.floor(p[2])) - 1);
                if (((index < 0) || (index >= NumCodesArch))) {
                    return 0;
                }
                xsect.@yFull = (Yfull_Arch[index] / 12.0d);
                xsect.@wMax = (Wmax_Arch[index] / 12.0d);
                xsect.@aFull = Afull_Arch[index];
                xsect.@rFull = Rfull_Arch[index];
            } else {
                if ((p[1] < 0.0d)) {
                    return 0;
                }
                xsect.@yFull = (p[0] / ucf);
                xsect.@wMax = (p[1] / ucf);
                xsect.@aFull = ((0.7879d * xsect.yFull) * xsect.wMax);
                xsect.@rFull = (0.2991d * xsect.yFull);
            }
            xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
            xsect.@sMax = xsect.sFull;
            xsect.@ywMax = (0.28d * xsect.yFull);
        }
        return 1;
    }

    // xsect.c:638
    static void xsect_setIrregXsectParams(TXsect xsect) {
        int index = xsect.transect;
        getTransectParams(xsect, F_swmm5.Transect[index]);
    }

    // xsect.c:651
    static void xsect_setStreetXsectParams(TXsect xsect) {
        int index = xsect.transect;
        getTransectParams(xsect, F_swmm5.Street[index].transect);
    }

    // xsect.c:664
    static void xsect_setCustomXsectParams(TXsect xsect) {
        int index = F_swmm5.Curve[xsect.transect].refersTo;
        double yFull = xsect.yFull;
        int i = 0;
        int iMax = 0;
        double wMax = 0.0d;
        double[] wTbl = F_swmm5.Shape[index].widthTbl;
        xsect.@wMax = (F_swmm5.Shape[index].wMax * yFull);
        xsect.@aFull = ((F_swmm5.Shape[index].aFull * yFull) * yFull);
        xsect.@rFull = (F_swmm5.Shape[index].rFull * yFull);
        xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
        xsect.@sMax = (((F_swmm5.Shape[index].sMax * yFull) * yFull) * Glibm.pow(yFull, (2.0d / 3.0d)));
        xsect.@aBot = ((F_swmm5.Shape[index].aMax * yFull) * yFull);
        iMax = 0;
        wMax = wTbl[0];
        L_f1: for (i = 1; (i < 51); i++) {
            if ((wTbl[i] < wMax)) {
                break L_f1;
            }
            wMax = wTbl[i];
            iMax = i;
        }
        xsect.@ywMax = ((yFull * ((double) (iMax))) / ((double) (((51 - 1)))));
    }

    // xsect.c:700
    static double xsect_getAmax(TXsect xsect) {
        if ((xsect.type == IRREGULAR)) {
            return xsect.aBot;
        } else if ((xsect.type == CUSTOM)) {
            return xsect.aBot;
        } else {
            return (Amax[xsect.type] * xsect.aFull);
        }
        throw new IllegalStateException('xsect_getAmax: no return');
    }

    // xsect.c:714
    static double xsect_getSofA(TXsect xsect, double a) {
        double alpha = (a / xsect.aFull);
        double r = 0.0d;
        int __sw1 = xsect.type;
        if (__sw1 == FORCE_MAIN || __sw1 == CIRCULAR) {
            return circ_getSofA(xsect, a);
        } else if (__sw1 == EGGSHAPED) {
            return (xsect.sFull * lookup(alpha, S_Egg, N_S_Egg));
        } else if (__sw1 == HORSESHOE) {
            return (xsect.sFull * lookup(alpha, S_Horseshoe, N_S_Horseshoe));
        } else if (__sw1 == GOTHIC) {
            return (xsect.sFull * lookup(alpha, S_Gothic, N_S_Gothic));
        } else if (__sw1 == CATENARY) {
            return (xsect.sFull * lookup(alpha, S_Catenary, N_S_Catenary));
        } else if (__sw1 == SEMIELLIPTICAL) {
            return (xsect.sFull * lookup(alpha, S_SemiEllip, N_S_SemiEllip));
        } else if (__sw1 == BASKETHANDLE) {
            return (xsect.sFull * lookup(alpha, S_BasketHandle, N_S_BasketHandle));
        } else if (__sw1 == SEMICIRCULAR) {
            return (xsect.sFull * lookup(alpha, S_SemiCirc, N_S_SemiCirc));
        } else if (__sw1 == RECT_CLOSED) {
            return rect_closed_getSofA(xsect, a);
        } else if (__sw1 == RECT_OPEN) {
            return rect_open_getSofA(xsect, a);
        } else if (__sw1 == RECT_TRIANG) {
            return rect_triang_getSofA(xsect, a);
        } else if (__sw1 == RECT_ROUND) {
            return rect_round_getSofA(xsect, a);
        } else {
            if ((a == 0.0d)) {
                return 0.0d;
            }
            r = xsect_getRofA(xsect, a);
            if ((r < 1e-06d)) {
                return 0.0d;
            }
            return (a * Glibm.pow(r, (2.0d / 3.0d)));
        }
        throw new IllegalStateException('xsect_getSofA: no return');
    }

    // xsect.c:773
    static double xsect_getYofA(TXsect xsect, double a) {
        double alpha = (a / xsect.aFull);
        int __sw1 = xsect.type;
        if (__sw1 == FORCE_MAIN || __sw1 == CIRCULAR) {
            return circ_getYofA(xsect, a);
        } else if (__sw1 == FILLED_CIRCULAR) {
            return filled_circ_getYofA(xsect, a);
        } else if (__sw1 == EGGSHAPED) {
            return (xsect.yFull * lookup(alpha, Y_Egg, N_Y_Egg));
        } else if (__sw1 == HORSESHOE) {
            return (xsect.yFull * lookup(alpha, Y_Horseshoe, N_Y_Horseshoe));
        } else if (__sw1 == GOTHIC) {
            return (xsect.yFull * lookup(alpha, Y_Gothic, N_Y_Gothic));
        } else if (__sw1 == CATENARY) {
            return (xsect.yFull * lookup(alpha, Y_Catenary, N_Y_Catenary));
        } else if (__sw1 == SEMIELLIPTICAL) {
            return (xsect.yFull * lookup(alpha, Y_SemiEllip, N_Y_SemiEllip));
        } else if (__sw1 == BASKETHANDLE) {
            return (xsect.yFull * lookup(alpha, Y_BasketHandle, N_Y_BasketHandle));
        } else if (__sw1 == SEMICIRCULAR) {
            return (xsect.yFull * lookup(alpha, Y_SemiCirc, N_Y_SemiCirc));
        } else if (__sw1 == HORIZ_ELLIPSE) {
            return (xsect.yFull * invLookup(alpha, A_HorizEllipse, N_A_HorizEllipse));
        } else if (__sw1 == VERT_ELLIPSE) {
            return (xsect.yFull * invLookup(alpha, A_VertEllipse, N_A_VertEllipse));
        } else if (__sw1 == IRREGULAR) {
            return (xsect.yFull * invLookup(alpha, F_swmm5.Transect[xsect.transect].areaTbl, 51));
        } else if (__sw1 == CUSTOM) {
            return (xsect.yFull * invLookup(alpha, F_swmm5.Shape[F_swmm5.Curve[xsect.transect].refersTo].areaTbl, 51));
        } else if (__sw1 == STREET_XSECT) {
            return (xsect.yFull * invLookup(alpha, F_swmm5.Street[xsect.transect].transect.areaTbl, F_swmm5.Street[xsect.transect].transect.nTbl));
        } else if (__sw1 == ARCH) {
            return (xsect.yFull * invLookup(alpha, A_Arch, N_A_Arch));
        } else if (__sw1 == RECT_CLOSED) {
            return (a / xsect.wMax);
        } else if (__sw1 == RECT_TRIANG) {
            return rect_triang_getYofA(xsect, a);
        } else if (__sw1 == RECT_ROUND) {
            return rect_round_getYofA(xsect, a);
        } else if (__sw1 == RECT_OPEN) {
            return (a / xsect.wMax);
        } else if (__sw1 == MOD_BASKET) {
            return mod_basket_getYofA(xsect, a);
        } else if (__sw1 == TRAPEZOIDAL) {
            return trapez_getYofA(xsect, a);
        } else if (__sw1 == TRIANGULAR) {
            return triang_getYofA(xsect, a);
        } else if (__sw1 == PARABOLIC) {
            return parab_getYofA(xsect, a);
        } else if (__sw1 == POWERFUNC) {
            return powerfunc_getYofA(xsect, a);
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('xsect_getYofA: no return');
    }

    // xsect.c:857
    static double xsect_getAofY(TXsect xsect, double y) {
        double yNorm = (y / xsect.yFull);
        if ((y <= 0.0d)) {
            return 0.0d;
        }
        int __sw1 = xsect.type;
        if (__sw1 == FORCE_MAIN || __sw1 == CIRCULAR) {
            return (xsect.aFull * lookup(yNorm, A_Circ, N_A_Circ));
        } else if (__sw1 == FILLED_CIRCULAR) {
            return filled_circ_getAofY(xsect, y);
        } else if (__sw1 == EGGSHAPED) {
            return (xsect.aFull * lookup(yNorm, A_Egg, N_A_Egg));
        } else if (__sw1 == HORSESHOE) {
            return (xsect.aFull * lookup(yNorm, A_Horseshoe, N_A_Horseshoe));
        } else if (__sw1 == GOTHIC) {
            return (xsect.aFull * invLookup(yNorm, Y_Gothic, N_Y_Gothic));
        } else if (__sw1 == CATENARY) {
            return (xsect.aFull * invLookup(yNorm, Y_Catenary, N_Y_Catenary));
        } else if (__sw1 == SEMIELLIPTICAL) {
            return (xsect.aFull * invLookup(yNorm, Y_SemiEllip, N_Y_SemiEllip));
        } else if (__sw1 == BASKETHANDLE) {
            return (xsect.aFull * lookup(yNorm, A_Baskethandle, N_A_Baskethandle));
        } else if (__sw1 == SEMICIRCULAR) {
            return (xsect.aFull * invLookup(yNorm, Y_SemiCirc, N_Y_SemiCirc));
        } else if (__sw1 == HORIZ_ELLIPSE) {
            return (xsect.aFull * lookup(yNorm, A_HorizEllipse, N_A_HorizEllipse));
        } else if (__sw1 == VERT_ELLIPSE) {
            return (xsect.aFull * lookup(yNorm, A_VertEllipse, N_A_VertEllipse));
        } else if (__sw1 == ARCH) {
            return (xsect.aFull * lookup(yNorm, A_Arch, N_A_Arch));
        } else if (__sw1 == IRREGULAR) {
            return (xsect.aFull * lookup(yNorm, F_swmm5.Transect[xsect.transect].areaTbl, 51));
        } else if (__sw1 == CUSTOM) {
            return (xsect.aFull * lookup(yNorm, F_swmm5.Shape[F_swmm5.Curve[xsect.transect].refersTo].areaTbl, 51));
        } else if (__sw1 == STREET_XSECT) {
            return (xsect.aFull * lookup(yNorm, F_swmm5.Street[xsect.transect].transect.areaTbl, F_swmm5.Street[xsect.transect].transect.nTbl));
        } else if (__sw1 == RECT_CLOSED) {
            return (y * xsect.wMax);
        } else if (__sw1 == RECT_TRIANG) {
            return rect_triang_getAofY(xsect, y);
        } else if (__sw1 == RECT_ROUND) {
            return rect_round_getAofY(xsect, y);
        } else if (__sw1 == RECT_OPEN) {
            return (y * xsect.wMax);
        } else if (__sw1 == MOD_BASKET) {
            return mod_basket_getAofY(xsect, y);
        } else if (__sw1 == TRAPEZOIDAL) {
            return trapez_getAofY(xsect, y);
        } else if (__sw1 == TRIANGULAR) {
            return triang_getAofY(xsect, y);
        } else if (__sw1 == PARABOLIC) {
            return parab_getAofY(xsect, y);
        } else if (__sw1 == POWERFUNC) {
            return powerfunc_getAofY(xsect, y);
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('xsect_getAofY: no return');
    }

    // xsect.c:943
    static double xsect_getWofY(TXsect xsect, double y) {
        double yNorm = (y / xsect.yFull);
        int __sw1 = xsect.type;
        if (__sw1 == FORCE_MAIN || __sw1 == CIRCULAR) {
            return (xsect.wMax * lookup(yNorm, W_Circ, N_W_Circ));
        } else if (__sw1 == FILLED_CIRCULAR) {
            yNorm = (((y + xsect.yBot)) / ((xsect.yFull + xsect.yBot)));
            return (xsect.wMax * lookup(yNorm, W_Circ, N_W_Circ));
        } else if (__sw1 == EGGSHAPED) {
            return (xsect.wMax * lookup(yNorm, W_Egg, N_W_Egg));
        } else if (__sw1 == HORSESHOE) {
            return (xsect.wMax * lookup(yNorm, W_Horseshoe, N_W_Horseshoe));
        } else if (__sw1 == GOTHIC) {
            return (xsect.wMax * lookup(yNorm, W_Gothic, N_W_Gothic));
        } else if (__sw1 == CATENARY) {
            return (xsect.wMax * lookup(yNorm, W_Catenary, N_W_Catenary));
        } else if (__sw1 == SEMIELLIPTICAL) {
            return (xsect.wMax * lookup(yNorm, W_SemiEllip, N_W_SemiEllip));
        } else if (__sw1 == BASKETHANDLE) {
            return (xsect.wMax * lookup(yNorm, W_BasketHandle, N_W_BasketHandle));
        } else if (__sw1 == SEMICIRCULAR) {
            return (xsect.wMax * lookup(yNorm, W_SemiCirc, N_W_SemiCirc));
        } else if (__sw1 == HORIZ_ELLIPSE) {
            return (xsect.wMax * lookup(yNorm, W_HorizEllipse, N_W_HorizEllipse));
        } else if (__sw1 == VERT_ELLIPSE) {
            return (xsect.wMax * lookup(yNorm, W_VertEllipse, N_W_VertEllipse));
        } else if (__sw1 == ARCH) {
            return (xsect.wMax * lookup(yNorm, W_Arch, N_W_Arch));
        } else if (__sw1 == IRREGULAR) {
            return (xsect.wMax * lookup(yNorm, F_swmm5.Transect[xsect.transect].widthTbl, 51));
        } else if (__sw1 == CUSTOM) {
            return (xsect.wMax * lookup(yNorm, F_swmm5.Shape[F_swmm5.Curve[xsect.transect].refersTo].widthTbl, 51));
        } else if (__sw1 == STREET_XSECT) {
            return (xsect.wMax * lookup(yNorm, F_swmm5.Street[xsect.transect].transect.widthTbl, F_swmm5.Street[xsect.transect].transect.nTbl));
        } else if (__sw1 == RECT_CLOSED) {
            if ((yNorm == 1.0d)) {
                return 0.0d;
            }
            return xsect.wMax;
        } else if (__sw1 == RECT_TRIANG) {
            return rect_triang_getWofY(xsect, y);
        } else if (__sw1 == RECT_ROUND) {
            return rect_round_getWofY(xsect, y);
        } else if (__sw1 == RECT_OPEN) {
            return xsect.wMax;
        } else if (__sw1 == MOD_BASKET) {
            return mod_basket_getWofY(xsect, y);
        } else if (__sw1 == TRAPEZOIDAL) {
            return trapez_getWofY(xsect, y);
        } else if (__sw1 == TRIANGULAR) {
            return triang_getWofY(xsect, y);
        } else if (__sw1 == PARABOLIC) {
            return parab_getWofY(xsect, y);
        } else if (__sw1 == POWERFUNC) {
            return powerfunc_getWofY(xsect, y);
        } else {
            return 0.0d;
        }
        throw new IllegalStateException('xsect_getWofY: no return');
    }

    // xsect.c:1031
    static double xsect_getRofY(TXsect xsect, double y) {
        double yNorm = (y / xsect.yFull);
        int __sw1 = xsect.type;
        if (__sw1 == FORCE_MAIN || __sw1 == CIRCULAR) {
            return (xsect.rFull * lookup(yNorm, R_Circ, N_R_Circ));
        } else if (__sw1 == FILLED_CIRCULAR) {
            if ((xsect.yBot == 0.0d)) {
                return (xsect.rFull * lookup(yNorm, R_Circ, N_R_Circ));
            }
            return filled_circ_getRofY(xsect, y);
        } else if (__sw1 == EGGSHAPED) {
            return (xsect.rFull * lookup(yNorm, R_Egg, N_R_Egg));
        } else if (__sw1 == HORSESHOE) {
            return (xsect.rFull * lookup(yNorm, R_Horseshoe, N_R_Horseshoe));
        } else if (__sw1 == BASKETHANDLE) {
            return (xsect.rFull * lookup(yNorm, R_Baskethandle, N_R_Baskethandle));
        } else if (__sw1 == HORIZ_ELLIPSE) {
            return (xsect.rFull * lookup(yNorm, R_HorizEllipse, N_R_HorizEllipse));
        } else if (__sw1 == VERT_ELLIPSE) {
            return (xsect.rFull * lookup(yNorm, R_VertEllipse, N_R_VertEllipse));
        } else if (__sw1 == ARCH) {
            return (xsect.rFull * lookup(yNorm, R_Arch, N_R_Arch));
        } else if (__sw1 == IRREGULAR) {
            return (xsect.rFull * lookup(yNorm, F_swmm5.Transect[xsect.transect].hradTbl, 51));
        } else if (__sw1 == CUSTOM) {
            return (xsect.rFull * lookup(yNorm, F_swmm5.Shape[F_swmm5.Curve[xsect.transect].refersTo].hradTbl, 51));
        } else if (__sw1 == STREET_XSECT) {
            return (xsect.rFull * lookup(yNorm, F_swmm5.Street[xsect.transect].transect.hradTbl, F_swmm5.Street[xsect.transect].transect.nTbl));
        } else if (__sw1 == RECT_TRIANG) {
            return rect_triang_getRofY(xsect, y);
        } else if (__sw1 == RECT_ROUND) {
            return rect_round_getRofY(xsect, y);
        } else if (__sw1 == TRAPEZOIDAL) {
            return trapez_getRofY(xsect, y);
        } else if (__sw1 == TRIANGULAR) {
            return triang_getRofY(xsect, y);
        } else if (__sw1 == PARABOLIC) {
            return parab_getRofY(xsect, y);
        } else if (__sw1 == POWERFUNC) {
            return powerfunc_getRofY(xsect, y);
        } else {
            return xsect_getRofA(xsect, xsect_getAofY(xsect, y));
        }
        throw new IllegalStateException('xsect_getRofY: no return');
    }

    // xsect.c:1100
    static double xsect_getRofA(TXsect xsect, double a) {
        double cathy = 0.0d;
        if ((a <= 0.0d)) {
            return 0.0d;
        }
        int __sw1 = xsect.type;
        if (__sw1 == HORIZ_ELLIPSE || __sw1 == VERT_ELLIPSE || __sw1 == ARCH || __sw1 == IRREGULAR || __sw1 == FILLED_CIRCULAR || __sw1 == CUSTOM || __sw1 == STREET_XSECT) {
            return xsect_getRofY(xsect, xsect_getYofA(xsect, a));
        } else if (__sw1 == RECT_CLOSED) {
            return rect_closed_getRofA(xsect, a);
        } else if (__sw1 == RECT_OPEN) {
            return (a / ((xsect.wMax + ((((2.0d - xsect.sBot)) * a) / xsect.wMax))));
        } else if (__sw1 == RECT_TRIANG) {
            return rect_triang_getRofA(xsect, a);
        } else if (__sw1 == RECT_ROUND) {
            return rect_round_getRofA(xsect, a);
        } else if (__sw1 == MOD_BASKET) {
            return mod_basket_getRofA(xsect, a);
        } else if (__sw1 == TRAPEZOIDAL) {
            return trapez_getRofA(xsect, a);
        } else if (__sw1 == TRIANGULAR) {
            return triang_getRofA(xsect, a);
        } else if (__sw1 == PARABOLIC) {
            return parab_getRofA(xsect, a);
        } else if (__sw1 == POWERFUNC) {
            return powerfunc_getRofA(xsect, a);
        } else {
            cathy = xsect_getSofA(xsect, a);
            if (((cathy < 1e-06d) || (a < 1e-06d))) {
                return 0.0d;
            }
            return Glibm.pow((cathy / a), (3.0d / 2.0d));
        }
        throw new IllegalStateException('xsect_getRofA: no return');
    }

    // xsect.c:1149
    static double xsect_getAofS(TXsect xsect, double s) {
        double psi = (s / xsect.sFull);
        if ((s <= 0.0d)) {
            return 0.0d;
        }
        if (R.dgt(s, xsect.sMax)) {
            s = xsect.sMax;
        }
        int __sw1 = xsect.type;
        if (__sw1 == DUMMY) {
            return 0.0d;
        } else if (__sw1 == FORCE_MAIN || __sw1 == CIRCULAR) {
            return circ_getAofS(xsect, s);
        } else if (__sw1 == EGGSHAPED) {
            return (xsect.aFull * invLookup(psi, S_Egg, N_S_Egg));
        } else if (__sw1 == HORSESHOE) {
            return (xsect.aFull * invLookup(psi, S_Horseshoe, N_S_Horseshoe));
        } else if (__sw1 == GOTHIC) {
            return (xsect.aFull * invLookup(psi, S_Gothic, N_S_Gothic));
        } else if (__sw1 == CATENARY) {
            return (xsect.aFull * invLookup(psi, S_Catenary, N_S_Catenary));
        } else if (__sw1 == SEMIELLIPTICAL) {
            return (xsect.aFull * invLookup(psi, S_SemiEllip, N_S_SemiEllip));
        } else if (__sw1 == BASKETHANDLE) {
            return (xsect.aFull * invLookup(psi, S_BasketHandle, N_S_BasketHandle));
        } else if (__sw1 == SEMICIRCULAR) {
            return (xsect.aFull * invLookup(psi, S_SemiCirc, N_S_SemiCirc));
        } else {
            return generic_getAofS(xsect, s);
        }
        throw new IllegalStateException('xsect_getAofS: no return');
    }

    // xsect.c:1194
    static double xsect_getdSdA(TXsect xsect, double a) {
        int __sw1 = xsect.type;
        if (__sw1 == FORCE_MAIN || __sw1 == CIRCULAR) {
            return circ_getdSdA(xsect, a);
        } else if (__sw1 == EGGSHAPED) {
            return tabular_getdSdA(xsect, a, S_Egg, N_S_Egg);
        } else if (__sw1 == HORSESHOE) {
            return tabular_getdSdA(xsect, a, S_Horseshoe, N_S_Horseshoe);
        } else if (__sw1 == GOTHIC) {
            return tabular_getdSdA(xsect, a, S_Gothic, N_S_Gothic);
        } else if (__sw1 == CATENARY) {
            return tabular_getdSdA(xsect, a, S_Catenary, N_S_Catenary);
        } else if (__sw1 == SEMIELLIPTICAL) {
            return tabular_getdSdA(xsect, a, S_SemiEllip, N_S_SemiEllip);
        } else if (__sw1 == BASKETHANDLE) {
            return tabular_getdSdA(xsect, a, S_BasketHandle, N_S_BasketHandle);
        } else if (__sw1 == SEMICIRCULAR) {
            return tabular_getdSdA(xsect, a, S_SemiCirc, N_S_SemiCirc);
        } else if (__sw1 == RECT_CLOSED) {
            return rect_closed_getdSdA(xsect, a);
        } else if (__sw1 == RECT_OPEN) {
            return rect_open_getdSdA(xsect, a);
        } else if (__sw1 == RECT_TRIANG) {
            return rect_triang_getdSdA(xsect, a);
        } else if (__sw1 == RECT_ROUND) {
            return rect_round_getdSdA(xsect, a);
        } else if (__sw1 == MOD_BASKET) {
            return mod_basket_getdSdA(xsect, a);
        } else if (__sw1 == TRAPEZOIDAL) {
            return trapez_getdSdA(xsect, a);
        } else if (__sw1 == TRIANGULAR) {
            return triang_getdSdA(xsect, a);
        } else {
            return generic_getdSdA(xsect, a);
        }
        throw new IllegalStateException('xsect_getdSdA: no return');
    }

    // xsect.c:1257
    static double xsect_getYcrit(TXsect xsect, double q) {
        double q2g = ((((q) * (q))) / 32.2d);
        double y = 0.0d;
        double r = 0.0d;
        if ((q2g == 0.0d)) {
            return 0.0d;
        }
        int __sw1 = xsect.type;
        if (__sw1 == DUMMY) {
            return 0.0d;
        } else if (__sw1 == RECT_OPEN || __sw1 == RECT_CLOSED) {
            y = Glibm.pow((q2g / (((xsect.wMax) * (xsect.wMax)))), (1.0d / 3.0d));
        } else if (__sw1 == TRIANGULAR) {
            y = Glibm.pow(((2.0d * q2g) / (((xsect.sBot) * (xsect.sBot)))), (1.0d / 5.0d));
        } else if (__sw1 == PARABOLIC) {
            y = Glibm.pow((((27.0d / 32.0d) * q2g) / (((xsect.rBot) * (xsect.rBot)))), (1.0d / 4.0d));
        } else if (__sw1 == POWERFUNC) {
            y = (1.0d / (((2.0d * xsect.sBot) + 3.0d)));
            y = Glibm.pow(((q2g * ((xsect.sBot + 1.0d))) / (((xsect.rBot) * (xsect.rBot)))), y);
        } else {
            y = (1.01d * Glibm.pow((q2g / xsect.yFull), (1.0d / 4.0d)));
            if (R.dge(y, xsect.yFull)) {
                y = (0.97d * xsect.yFull);
            }
            r = (xsect.aFull / (((3.141592654d / 4.0d) * (((xsect.yFull) * (xsect.yFull))))));
            if (((R.dge(r, 0.5d)) && (r <= 2.0d))) {
                y = getYcritEnum(xsect, q, y);
            } else {
                y = getYcritRidder(xsect, q, y);
            }
        }
        return (((((y) <= (xsect.yFull))) ? (y) : (xsect.yFull)));
    }

    // xsect.c:1323
    static void getTransectParams(TXsect xsect, TTransect transect) {
        int i = 0;
        int iMax = 0;
        double wMax = 0.0d;
        double[] wTbl = transect.widthTbl;
        xsect.@yFull = transect.yFull;
        xsect.@wMax = transect.wMax;
        xsect.@aFull = transect.aFull;
        xsect.@rFull = transect.rFull;
        xsect.@sFull = (xsect.aFull * Glibm.pow(xsect.rFull, (2.0d / 3.0d)));
        xsect.@sMax = transect.sMax;
        xsect.@aBot = transect.aMax;
        iMax = 0;
        wMax = wTbl[0];
        L_f1: for (i = 1; (i < transect.nTbl); i++) {
            if ((wTbl[i] < wMax)) {
                break L_f1;
            }
            wMax = wTbl[i];
            iMax = i;
        }
        xsect.@ywMax = ((xsect.yFull * ((double) (iMax))) / ((((double) ((transect.nTbl))) - 1)));
    }

    // xsect.c:1359
    static double generic_getAofS(TXsect xsect, double s) {
        DBox a = new DBox(0.0d);
        double a1 = 0.0d;
        double a2 = 0.0d;
        double tol = 0.0d;
        TXsectStar xsectStar = new TXsectStar();
        if ((s <= 0.0d)) {
            return 0.0d;
        }
        if (((((s <= xsect.sMax) && (R.dge(s, xsect.sFull)))) && (xsect.sMax != xsect.sFull))) {
            a1 = xsect.aFull;
            a2 = xsect_getAmax(xsect);
        } else {
            a1 = 0.0d;
            a2 = xsect_getAmax(xsect);
        }
        xsectStar.@xsect = xsect;
        xsectStar.@s = s;
        a.@v = (0.5d * ((a1 + a2)));
        tol = (0.0001d * xsect.aFull);
        F_findroot.findroot_Newton(a1, a2, a, tol, F_xsect::evalSofA, xsectStar);
        return a.v;
    }

    // xsect.c:1404
    static void evalSofA(double a, DRef f, DRef df, Object p) {
        TXsectStar xsectStar = null;
        double s = 0.0d;
        xsectStar = ((TXsectStar) p);
        s = xsect_getSofA(xsectStar.xsect, a);
        f.set((s - xsectStar.s));
        df.set(xsect_getdSdA(xsectStar.xsect, a));
    }

    // xsect.c:1424
    static double tabular_getdSdA(TXsect xsect, double a, double[] table, int nItems) {
        int i = 0;
        double alpha = (a / xsect.aFull);
        double delta = (1.0d / ((((double) (nItems)) - 1)));
        double dSdA = 0.0d;
        i = R.D2I(((alpha / delta)));
        if ((i >= (nItems - 1))) {
            i = (nItems - 2);
        }
        dSdA = (((table[(i + 1)] - table[i])) / delta);
        return ((dSdA * xsect.sFull) / xsect.aFull);
    }

    // xsect.c:1453
    static double generic_getdSdA(TXsect xsect, double a) {
        double a1 = 0.0d;
        double a2 = 0.0d;
        double alpha = (a / xsect.aFull);
        double alpha1 = (alpha - 0.001d);
        double alpha2 = (alpha + 0.001d);
        if ((alpha1 < 0.0d)) {
            alpha1 = 0.0d;
        }
        a1 = (alpha1 * xsect.aFull);
        a2 = (alpha2 * xsect.aFull);
        return (((xsect_getSofA(xsect, a2) - xsect_getSofA(xsect, a1))) / ((a2 - a1)));
    }

    // xsect.c:1474
    static double lookup(double x, double[] table, int nItems) {
        double delta = 0.0d;
        double x0 = 0.0d;
        double x1 = 0.0d;
        double y = 0.0d;
        double y2 = 0.0d;
        int i = 0;
        delta = (1.0d / ((((double) (nItems)) - 1)));
        i = R.D2I(((x / delta)));
        if ((i >= (nItems - 1))) {
            return table[(nItems - 1)];
        }
        x0 = (i * delta);
        x1 = (((((double) (i)) + 1)) * delta);
        y = (table[i] + ((((x - x0)) * ((table[(i + 1)] - table[i]))) / delta));
        if ((i < 2)) {
            y2 = (y + (((((x - x0)) * ((x - x1))) / ((delta * delta))) * ((((table[i] / 2.0d) - table[(i + 1)]) + (table[(i + 2)] / 2.0d)))));
            if (R.dgt(y2, 0.0d)) {
                y = y2;
            }
        }
        if ((y < 0.0d)) {
            y = 0.0d;
        }
        return y;
    }

    // xsect.c:1511
    static double invLookup(double y, double[] table, int nItems) {
        double dx = 0.0d;
        double x = 0.0d;
        double x0 = 0.0d;
        double dy = 0.0d;
        int n = 0;
        int i = 0;
        dx = (1.0d / ((((double) (nItems)) - 1)));
        n = nItems;
        if (R.dgt(table[(n - 3)], table[(n - 1)])) {
            n = (n - 2);
        }
        if (((n < nItems) && (R.dgt(y, table[(nItems - 1)])))) {
            if (R.dge(y, table[(nItems - 3)])) {
                return (((((double) (n)) - 1)) * dx);
            }
            if ((y <= table[(nItems - 2)])) {
                i = (nItems - 2);
            } else {
                i = (nItems - 3);
            }
        } else {
            i = locate(y, table, (n - 1));
        }
        if ((i >= (n - 1))) {
            return (((((double) (n)) - 1)) * dx);
        }
        x0 = (i * dx);
        dy = (table[(i + 1)] - table[i]);
        if ((dy == 0.0d)) {
            x = x0;
        } else {
            x = (x0 + ((((y - table[i])) * dx) / dy));
        }
        if ((x < 0.0d)) {
            x = 0.0d;
        }
        if (R.dgt(x, 1.0d)) {
            x = 1.0d;
        }
        return x;
    }

    // xsect.c:1571
    static int locate(double y, double[] table, int jLast) {
        int j = 0;
        int j1 = 0;
        int j2 = jLast;
        if ((y <= table[0])) {
            return 0;
        }
        if (R.dge(y, table[jLast])) {
            return jLast;
        }
        L_w1: while (((j2 - j1) > 1)) {
            j = (((j1 + j2)) >> 1);
            if (R.dge(y, table[j])) {
                j1 = j;
            } else {
                j2 = j;
            }
        }
        return j1;
    }

    // xsect.c:1612
    static double getQcritical(double yc, Object p) {
        double a = 0.0d;
        double w = 0.0d;
        double qc = 0.0d;
        TXsectStar xsectStar = null;
        xsectStar = ((TXsectStar) p);
        a = xsect_getAofY(xsectStar.xsect, yc);
        w = xsect_getWofY(xsectStar.xsect, yc);
        qc = (-xsectStar.qc);
        if (R.dgt(w, 0.0d)) {
            qc = ((a * Math.sqrt(((32.2d * a) / w))) - xsectStar.qc);
        }
        return qc;
    }

    // xsect.c:1634
    static double getYcritEnum(TXsect xsect, double q, double y0) {
        double q0 = 0.0d;
        double dy = 0.0d;
        double qc = 0.0d;
        double yc = 0.0d;
        int i1 = 0;
        int i = 0;
        TXsectStar xsectStar = new TXsectStar();
        dy = (xsect.yFull / 25.0d);
        i1 = R.D2I(((y0 / dy)));
        xsectStar.@xsect = xsect;
        xsectStar.@qc = 0.0d;
        q0 = getQcritical((i1 * dy), xsectStar);
        if ((q0 < q)) {
            yc = xsect.yFull;
            L_f1: for (i = (i1 + 1); (i <= 25); i++) {
                qc = getQcritical((i * dy), xsectStar);
                if (R.dge(qc, q)) {
                    yc = ((((((q - q0)) / ((qc - q0))) + ((((double) (i)) - 1)))) * dy);
                    break L_f1;
                }
                q0 = qc;
            }
        } else {
            yc = 0.0d;
            L_f2: for (i = (i1 - 1); (i >= 0); i--) {
                qc = getQcritical((i * dy), xsectStar);
                if ((qc < q)) {
                    yc = ((((((q - qc)) / ((q0 - qc))) + ((double) (i)))) * dy);
                    break L_f2;
                }
                q0 = qc;
            }
        }
        return yc;
    }

    // xsect.c:1700
    static double getYcritRidder(TXsect xsect, double q, double y0) {
        double y1 = 0.0d;
        double y2 = (0.99d * xsect.yFull);
        double yc = 0.0d;
        double q0 = 0.0d;
        double q1 = 0.0d;
        double q2 = 0.0d;
        TXsectStar xsectStar = new TXsectStar();
        xsectStar.@xsect = xsect;
        xsectStar.@qc = 0.0d;
        q2 = getQcritical(y2, xsectStar);
        if ((q2 < q)) {
            return xsect.yFull;
        }
        q0 = getQcritical(y0, xsectStar);
        q1 = getQcritical((0.5d * xsect.yFull), xsectStar);
        if (R.dgt(q0, q)) {
            y2 = y0;
            if ((q1 < q)) {
                y1 = (0.5d * xsect.yFull);
            }
        } else {
            y1 = y0;
            if (R.dgt(q1, q)) {
                y2 = (0.5d * xsect.yFull);
            }
        }
        xsectStar.@qc = q;
        yc = F_findroot.findroot_Ridder(y1, y2, 0.001d, F_xsect::getQcritical, xsectStar);
        return yc;
    }

    // xsect.c:1755
    static double rect_closed_getSofA(TXsect xsect, double a) {
        double alfMax = 0.97d;
        if (R.dgt((a / xsect.aFull), alfMax)) {
            return (xsect.sMax + ((((xsect.sFull - xsect.sMax)) * (((a / xsect.aFull) - alfMax))) / ((1.0d - alfMax))));
        }
        return (a * Glibm.pow(xsect_getRofA(xsect, a), (2.0d / 3.0d)));
    }

    // xsect.c:1770
    static double rect_closed_getdSdA(TXsect xsect, double a) {
        double alpha = 0.0d;
        double alfMax = 0.0d;
        double r = 0.0d;
        alfMax = 0.97d;
        alpha = (a / xsect.aFull);
        if (R.dgt(alpha, alfMax)) {
            return (((xsect.sFull - xsect.sMax)) / ((((1.0d - alfMax)) * xsect.aFull)));
        }
        if ((alpha <= 1.0e-30d)) {
            return generic_getdSdA(xsect, a);
        }
        r = xsect_getRofA(xsect, a);
        return ((((5.0d / 3.0d) - ((((2.0d / 3.0d)) * ((2.0d / xsect.wMax))) * r))) * Glibm.pow(r, (2.0d / 3.0d)));
    }

    // xsect.c:1793
    static double rect_closed_getRofA(TXsect xsect, double a) {
        double p = 0.0d;
        if ((a <= 0.0d)) {
            return 0.0d;
        }
        p = (xsect.wMax + ((2.0d * a) / xsect.wMax));
        if (R.dgt((a / xsect.aFull), 0.97d)) {
            p += (((((a / xsect.aFull) - 0.97d)) / ((1.0d - 0.97d))) * xsect.wMax);
        }
        return (a / p);
    }

    // xsect.c:1810
    static double rect_open_getSofA(TXsect xsect, double a) {
        double y = (a / xsect.wMax);
        double r = (a / (((((2.0d - xsect.sBot)) * y) + xsect.wMax)));
        return (a * Glibm.pow(r, (2.0d / 3.0d)));
    }

    // xsect.c:1818
    static double rect_open_getdSdA(TXsect xsect, double a) {
        double r = 0.0d;
        double dPdA = 0.0d;
        if (((a / xsect.aFull) <= 1.0e-30d)) {
            return generic_getdSdA(xsect, a);
        }
        r = xsect_getRofA(xsect, a);
        dPdA = (((2.0d - xsect.sBot)) / xsect.wMax);
        return ((((5.0d / 3.0d) - ((((2.0d / 3.0d)) * dPdA) * r))) * Glibm.pow(r, (2.0d / 3.0d)));
    }

    // xsect.c:1837
    static double rect_triang_getYofA(TXsect xsect, double a) {
        if ((a <= xsect.aBot)) {
            return Math.sqrt((a / xsect.sBot));
        } else {
            return (xsect.yBot + (((a - xsect.aBot)) / xsect.wMax));
        }
        throw new IllegalStateException('rect_triang_getYofA: no return');
    }

    // xsect.c:1846
    static double rect_triang_getRofA(TXsect xsect, double a) {
        double y = 0.0d;
        double p = 0.0d;
        double alf = 0.0d;
        if ((a <= 0.0d)) {
            return 0.0d;
        }
        y = rect_triang_getYofA(xsect, a);
        if ((y <= xsect.yBot)) {
            return (a / (((2.0d * y) * xsect.rBot)));
        }
        p = (((2.0d * xsect.yBot) * xsect.rBot) + (2.0d * ((y - xsect.yBot))));
        alf = (((a / xsect.aFull)) - 0.98d);
        if (R.dgt(alf, 0.0d)) {
            p += ((alf / ((1.0d - 0.98d))) * xsect.wMax);
        }
        return (a / p);
    }

    // xsect.c:1866
    static double rect_triang_getSofA(TXsect xsect, double a) {
        double alfMax = 0.98d;
        if (R.dgt((a / xsect.aFull), alfMax)) {
            return (xsect.sMax + ((((xsect.sFull - xsect.sMax)) * (((a / xsect.aFull) - alfMax))) / ((1.0d - alfMax))));
        } else {
            return (a * Glibm.pow(rect_triang_getRofA(xsect, a), (2.0d / 3.0d)));
        }
        throw new IllegalStateException('rect_triang_getSofA: no return');
    }

    // xsect.c:1879
    static double rect_triang_getdSdA(TXsect xsect, double a) {
        double alpha = 0.0d;
        double alfMax = 0.0d;
        double dPdA = 0.0d;
        double r = 0.0d;
        alfMax = 0.98d;
        alpha = (a / xsect.aFull);
        if (R.dgt(alpha, alfMax)) {
            return (((xsect.sFull - xsect.sMax)) / ((((1.0d - alfMax)) * xsect.aFull)));
        }
        if ((alpha <= 1.0e-30d)) {
            return generic_getdSdA(xsect, a);
        }
        if (R.dgt(a, xsect.aBot)) {
            dPdA = (2.0d / xsect.wMax);
        } else {
            dPdA = (xsect.rBot / Math.sqrt((a * xsect.sBot)));
        }
        r = rect_triang_getRofA(xsect, a);
        return ((((5.0d / 3.0d) - ((((2.0d / 3.0d)) * dPdA) * r))) * Glibm.pow(r, (2.0d / 3.0d)));
    }

    // xsect.c:1902
    static double rect_triang_getAofY(TXsect xsect, double y) {
        if ((y <= xsect.yBot)) {
            return ((y * y) * xsect.sBot);
        } else {
            return (xsect.aBot + (((y - xsect.yBot)) * xsect.wMax));
        }
        throw new IllegalStateException('rect_triang_getAofY: no return');
    }

    // xsect.c:1908
    static double rect_triang_getRofY(TXsect xsect, double y) {
        double p = 0.0d;
        double a = 0.0d;
        double alf = 0.0d;
        if ((y <= xsect.yBot)) {
            return ((y * xsect.sBot) / ((2.0d * xsect.rBot)));
        }
        a = (xsect.aBot + (((y - xsect.yBot)) * xsect.wMax));
        p = (((2.0d * xsect.yBot) * xsect.rBot) + (2.0d * ((y - xsect.yBot))));
        alf = (((a / xsect.aFull)) - 0.98d);
        if (R.dgt(alf, 0.0d)) {
            p += ((alf / ((1.0d - 0.98d))) * xsect.wMax);
        }
        return (a / p);
    }

    // xsect.c:1927
    static double rect_triang_getWofY(TXsect xsect, double y) {
        if ((y <= xsect.yBot)) {
            return ((2.0d * xsect.sBot) * y);
        } else {
            return xsect.wMax;
        }
        throw new IllegalStateException('rect_triang_getWofY: no return');
    }

    // xsect.c:1938
    static double rect_round_getYofA(TXsect xsect, double a) {
        double alpha = 0.0d;
        if (R.dgt(a, xsect.aBot)) {
            return (xsect.yBot + (((a - xsect.aBot)) / xsect.wMax));
        }
        alpha = (a / (((3.141592654d * xsect.rBot) * xsect.rBot)));
        if ((alpha < 0.04d)) {
            return (((2.0d * xsect.rBot)) * getYcircular(alpha));
        }
        return (((2.0d * xsect.rBot)) * lookup(alpha, Y_Circ, N_Y_Circ));
    }

    // xsect.c:1952
    static double rect_round_getRofA(TXsect xsect, double a) {
        double y1 = 0.0d;
        double theta1 = 0.0d;
        double p = 0.0d;
        double arg = 0.0d;
        if ((a <= 0.0d)) {
            return 0.0d;
        }
        if (R.dgt(a, xsect.aBot)) {
            y1 = (((a - xsect.aBot)) / xsect.wMax);
            theta1 = (2.0d * Glibm.asin(((xsect.wMax / 2.0d) / xsect.rBot)));
            p = ((xsect.rBot * theta1) + (2.0d * y1));
            arg = (((a / xsect.aFull)) - 0.98d);
            if (R.dgt(arg, 0.0d)) {
                p += ((arg / ((1.0d - 0.98d))) * xsect.wMax);
            }
            return (a / p);
        }
        y1 = rect_round_getYofA(xsect, a);
        theta1 = (2.0d * Glibm.acos((1.0d - (y1 / xsect.rBot))));
        p = (xsect.rBot * theta1);
        return (a / p);
    }

    // xsect.c:1978
    static double rect_round_getSofA(TXsect xsect, double a) {
        double alpha = 0.0d;
        double aFull = 0.0d;
        double sFull = 0.0d;
        double alfMax = 0.98d;
        if (R.dgt((a / xsect.aFull), alfMax)) {
            return (xsect.sMax + ((((xsect.sFull - xsect.sMax)) * (((a / xsect.aFull) - alfMax))) / ((1.0d - alfMax))));
        } else if (R.dgt(a, xsect.aBot)) {
            return (a * Glibm.pow(xsect_getRofA(xsect, a), (2.0d / 3.0d)));
        } else {
            aFull = ((3.141592654d * xsect.rBot) * xsect.rBot);
            alpha = (a / aFull);
            sFull = xsect.sBot;
            if ((alpha < 0.04d)) {
                return (sFull * getScircular(alpha));
            } else {
                return (sFull * lookup(alpha, S_Circ, N_S_Circ));
            }
        }
        throw new IllegalStateException('rect_round_getSofA: no return');
    }

    // xsect.c:2013
    static double rect_round_getdSdA(TXsect xsect, double a) {
        double alfMax = 0.0d;
        double r = 0.0d;
        double dPdA = 0.0d;
        alfMax = 0.98d;
        if (R.dgt((a / xsect.aFull), alfMax)) {
            return (((xsect.sFull - xsect.sMax)) / ((((1.0d - alfMax)) * xsect.aFull)));
        } else if (R.dgt(a, xsect.aBot)) {
            r = rect_round_getRofA(xsect, a);
            dPdA = (2.0d / xsect.wMax);
            return ((((5.0d / 3.0d) - ((((2.0d / 3.0d)) * dPdA) * r))) * Glibm.pow(r, (2.0d / 3.0d)));
        } else {
            return generic_getdSdA(xsect, a);
        }
        throw new IllegalStateException('rect_round_getdSdA: no return');
    }

    // xsect.c:2038
    static double rect_round_getAofY(TXsect xsect, double y) {
        double theta1 = 0.0d;
        if (R.dgt(y, xsect.yBot)) {
            return (xsect.aBot + (((y - xsect.yBot)) * xsect.wMax));
        }
        theta1 = (2.0d * Glibm.acos((1.0d - (y / xsect.rBot))));
        return (((0.5d * xsect.rBot) * xsect.rBot) * ((theta1 - Glibm.sin(theta1))));
    }

    // xsect.c:2051
    static double rect_round_getRofY(TXsect xsect, double y) {
        double theta1 = 0.0d;
        if ((y <= 0.0d)) {
            return 0.0d;
        }
        if (R.dgt(y, xsect.yBot)) {
            return rect_round_getRofA(xsect, rect_round_getAofY(xsect, y));
        }
        theta1 = (2.0d * Glibm.acos((1.0d - (y / xsect.rBot))));
        return (((0.5d * xsect.rBot) * ((1.0d - Glibm.sin(theta1)))) / theta1);
    }

    // xsect.c:2065
    static double rect_round_getWofY(TXsect xsect, double y) {
        if (R.dgt(y, xsect.yBot)) {
            return xsect.wMax;
        }
        return (2.0d * Math.sqrt((y * (((2.0d * xsect.rBot) - y)))));
    }

    // xsect.c:2082
    static double mod_basket_getYofA(TXsect xsect, double a) {
        double alpha = 0.0d;
        double y1 = 0.0d;
        if ((a <= (xsect.aFull - xsect.aBot))) {
            return (a / xsect.wMax);
        }
        alpha = (((xsect.aFull - a)) / (((3.141592654d * xsect.rBot) * xsect.rBot)));
        if ((alpha < 0.04d)) {
            y1 = getYcircular(alpha);
        } else {
            y1 = lookup(alpha, Y_Circ, N_Y_Circ);
        }
        y1 = ((2.0d * xsect.rBot) * y1);
        return (xsect.yFull - y1);
    }

    // xsect.c:2101
    static double mod_basket_getRofA(TXsect xsect, double a) {
        double y1 = 0.0d;
        double p = 0.0d;
        double theta1 = 0.0d;
        if ((a <= (xsect.aFull - xsect.aBot))) {
            return (a / ((xsect.wMax + ((2.0d * a) / xsect.wMax))));
        }
        y1 = (xsect.yFull - mod_basket_getYofA(xsect, a));
        theta1 = (2.0d * Glibm.acos((1.0d - (y1 / xsect.rBot))));
        p = (((xsect.sBot - theta1)) * xsect.rBot);
        y1 = (xsect.yFull - xsect.yBot);
        p = ((p + (2.0d * y1)) + xsect.wMax);
        return (a / p);
    }

    // xsect.c:2128
    static double mod_basket_getdSdA(TXsect xsect, double a) {
        double r = 0.0d;
        double dPdA = 0.0d;
        if (((a <= (xsect.aFull - xsect.aBot)) && (R.dgt((a / xsect.aFull), 1.0e-30d)))) {
            r = (a / ((xsect.wMax + ((2.0d * a) / xsect.wMax))));
            dPdA = (2.0d / xsect.wMax);
            return ((((5.0d / 3.0d) - ((((2.0d / 3.0d)) * dPdA) * r))) * Glibm.pow(r, (2.0d / 3.0d)));
        } else {
            return generic_getdSdA(xsect, a);
        }
        throw new IllegalStateException('mod_basket_getdSdA: no return');
    }

    // xsect.c:2145
    static double mod_basket_getAofY(TXsect xsect, double y) {
        double a1 = 0.0d;
        double theta1 = 0.0d;
        double y1 = 0.0d;
        if ((y <= (xsect.yFull - xsect.yBot))) {
            return (y * xsect.wMax);
        }
        y1 = (xsect.yFull - y);
        theta1 = (2.0d * Glibm.acos((1.0d - (y1 / xsect.rBot))));
        a1 = (((0.5d * xsect.rBot) * xsect.rBot) * ((theta1 - Glibm.sin(theta1))));
        return (xsect.aFull - a1);
    }

    // xsect.c:2162
    static double mod_basket_getWofY(TXsect xsect, double y) {
        double y1 = 0.0d;
        if ((y <= 0.0d)) {
            return 0.0d;
        }
        if ((y <= (xsect.yFull - xsect.yBot))) {
            return xsect.wMax;
        }
        y1 = (xsect.yFull - y);
        return (2.0d * Math.sqrt((y1 * (((2.0d * xsect.rBot) - y1)))));
    }

    // xsect.c:2184
    static double trapez_getYofA(TXsect xsect, double a) {
        if ((xsect.sBot == 0.0d)) {
            return (a / xsect.yBot);
        }
        return (((Math.sqrt(((xsect.yBot * xsect.yBot) + ((4.0d * xsect.sBot) * a))) - xsect.yBot)) / ((2.0d * xsect.sBot)));
    }

    // xsect.c:2191
    static double trapez_getRofA(TXsect xsect, double a) {
        return (a / ((xsect.yBot + (trapez_getYofA(xsect, a) * xsect.rBot))));
    }

    // xsect.c:2196
    static double trapez_getdSdA(TXsect xsect, double a) {
        double r = 0.0d;
        double dPdA = 0.0d;
        if (((a / xsect.aFull) <= 1.0e-30d)) {
            return generic_getdSdA(xsect, a);
        }
        r = trapez_getRofA(xsect, a);
        dPdA = (xsect.rBot / Math.sqrt(((xsect.yBot * xsect.yBot) + ((4.0d * xsect.sBot) * a))));
        return ((((5.0d / 3.0d) - ((((2.0d / 3.0d)) * dPdA) * r))) * Glibm.pow(r, (2.0d / 3.0d)));
    }

    // xsect.c:2210
    static double trapez_getAofY(TXsect xsect, double y) {
        return (((xsect.yBot + (xsect.sBot * y))) * y);
    }

    // xsect.c:2215
    static double trapez_getRofY(TXsect xsect, double y) {
        if ((y == 0.0d)) {
            return 0.0d;
        }
        return (trapez_getAofY(xsect, y) / ((xsect.yBot + (y * xsect.rBot))));
    }

    // xsect.c:2221
    static double trapez_getWofY(TXsect xsect, double y) {
        return (xsect.yBot + ((2.0d * y) * xsect.sBot));
    }

    // xsect.c:2231
    static double triang_getYofA(TXsect xsect, double a) {
        return Math.sqrt((a / xsect.sBot));
    }

    // xsect.c:2236
    static double triang_getRofA(TXsect xsect, double a) {
        return (a / (((2.0d * triang_getYofA(xsect, a)) * xsect.rBot)));
    }

    // xsect.c:2241
    static double triang_getdSdA(TXsect xsect, double a) {
        double r = 0.0d;
        double dPdA = 0.0d;
        if (((a / xsect.aFull) <= 1.0e-30d)) {
            return generic_getdSdA(xsect, a);
        }
        r = triang_getRofA(xsect, a);
        dPdA = (xsect.rBot / Math.sqrt((a * xsect.sBot)));
        return ((((5.0d / 3.0d) - ((((2.0d / 3.0d)) * dPdA) * r))) * Glibm.pow(r, (2.0d / 3.0d)));
    }

    // xsect.c:2253
    static double triang_getAofY(TXsect xsect, double y) {
        return ((y * y) * xsect.sBot);
    }

    // xsect.c:2258
    static double triang_getRofY(TXsect xsect, double y) {
        return (((y * xsect.sBot)) / ((2.0d * xsect.rBot)));
    }

    // xsect.c:2263
    static double triang_getWofY(TXsect xsect, double y) {
        return ((2.0d * xsect.sBot) * y);
    }

    // xsect.c:2273
    static double parab_getYofA(TXsect xsect, double a) {
        return Glibm.pow(((((3.0d / 4.0d)) * a) / xsect.rBot), (2.0d / 3.0d));
    }

    // xsect.c:2278
    static double parab_getRofA(TXsect xsect, double a) {
        if ((a <= 0.0d)) {
            return 0.0d;
        }
        return (a / parab_getPofY(xsect, parab_getYofA(xsect, a)));
    }

    // xsect.c:2284
    static double parab_getPofY(TXsect xsect, double y) {
        double x = ((2.0d * Math.sqrt(y)) / xsect.rBot);
        double t = Math.sqrt((1.0d + (x * x)));
        return (((0.5d * xsect.rBot) * xsect.rBot) * (((x * t) + Glibm.log((x + t)))));
    }

    // xsect.c:2291
    static double parab_getAofY(TXsect xsect, double y) {
        return (((((4.0d / 3.0d) * xsect.rBot) * y) * Math.sqrt(y)));
    }

    // xsect.c:2296
    static double parab_getRofY(TXsect xsect, double y) {
        if ((y <= 0.0d)) {
            return 0.0d;
        }
        return (parab_getAofY(xsect, y) / parab_getPofY(xsect, y));
    }

    // xsect.c:2302
    static double parab_getWofY(TXsect xsect, double y) {
        return ((2.0d * xsect.rBot) * Math.sqrt(y));
    }

    // xsect.c:2312
    static double powerfunc_getYofA(TXsect xsect, double a) {
        return Glibm.pow((a / xsect.rBot), (1.0d / ((xsect.sBot + 1.0d))));
    }

    // xsect.c:2317
    static double powerfunc_getRofA(TXsect xsect, double a) {
        if ((a <= 0.0d)) {
            return 0.0d;
        }
        return (a / powerfunc_getPofY(xsect, powerfunc_getYofA(xsect, a)));
    }

    // xsect.c:2323
    static double powerfunc_getPofY(TXsect xsect, double y) {
        double dy1 = (0.02d * xsect.yFull);
        double h = ((((xsect.sBot + 1.0d)) * xsect.rBot) / 2.0d);
        double m = xsect.sBot;
        double p = 0.0d;
        double y1 = 0.0d;
        double x1 = 0.0d;
        double x2 = 0.0d;
        double y2 = 0.0d;
        double dx = 0.0d;
        double dy = 0.0d;
        L_d1: do {
            y2 = (y1 + dy1);
            if (R.dgt(y2, y)) {
                y2 = y;
            }
            x2 = (h * Glibm.pow(y2, m));
            dx = (x2 - x1);
            dy = (y2 - y1);
            p += (Math.sqrt(((dx * dx) + (dy * dy))));
            x1 = x2;
            y1 = y2;
        } while ((y2 < y));
        return (2.0d * p);
    }

    // xsect.c:2346
    static double powerfunc_getAofY(TXsect xsect, double y) {
        return (xsect.rBot * Glibm.pow(y, (xsect.sBot + 1.0d)));
    }

    // xsect.c:2351
    static double powerfunc_getRofY(TXsect xsect, double y) {
        if ((y <= 0.0d)) {
            return 0.0d;
        }
        return (powerfunc_getAofY(xsect, y) / powerfunc_getPofY(xsect, y));
    }

    // xsect.c:2357
    static double powerfunc_getWofY(TXsect xsect, double y) {
        return ((((xsect.sBot + 1.0d)) * xsect.rBot) * Glibm.pow(y, xsect.sBot));
    }

    // xsect.c:2367
    static double circ_getYofA(TXsect xsect, double a) {
        double alpha = (a / xsect.aFull);
        if ((alpha < 0.04d)) {
            return (xsect.yFull * getYcircular(alpha));
        } else {
            return (xsect.yFull * lookup(alpha, Y_Circ, N_Y_Circ));
        }
        throw new IllegalStateException('circ_getYofA: no return');
    }

    // xsect.c:2378
    static double circ_getAofS(TXsect xsect, double s) {
        double psi = (s / xsect.sFull);
        if ((psi == 0.0d)) {
            return 0.0d;
        }
        if (R.dge(psi, 1.0d)) {
            return xsect.aFull;
        }
        if ((psi <= 0.015d)) {
            return (xsect.aFull * getAcircular(psi));
        } else {
            return (xsect.aFull * invLookup(psi, S_Circ, N_S_Circ));
        }
        throw new IllegalStateException('circ_getAofS: no return');
    }

    // xsect.c:2391
    static double circ_getSofA(TXsect xsect, double a) {
        double alpha = (a / xsect.aFull);
        if ((alpha < 0.04d)) {
            return (xsect.sFull * getScircular(alpha));
        } else {
            return (xsect.sFull * lookup(alpha, S_Circ, N_S_Circ));
        }
        throw new IllegalStateException('circ_getSofA: no return');
    }

    // xsect.c:2403
    static double circ_getdSdA(TXsect xsect, double a) {
        double alpha = 0.0d;
        double theta = 0.0d;
        double p = 0.0d;
        double r = 0.0d;
        double dPdA = 0.0d;
        alpha = (a / xsect.aFull);
        if ((alpha <= 1.0e-30d)) {
            return 1.0e-30d;
        } else if ((alpha < 0.04d)) {
            theta = getThetaOfAlpha(alpha);
            p = ((theta * xsect.yFull) / 2.0d);
            r = (a / p);
            dPdA = ((4.0d / xsect.yFull) / ((1.0d - Glibm.cos(theta))));
            return ((((5.0d / 3.0d) - ((((2.0d / 3.0d)) * dPdA) * r))) * Glibm.pow(r, (2.0d / 3.0d)));
        } else {
            return tabular_getdSdA(xsect, a, S_Circ, N_S_Circ);
        }
        throw new IllegalStateException('circ_getdSdA: no return');
    }

    // xsect.c:2450
    static double circ_getAofY(TXsect xsect, double y) {
        double yNorm = 0.0d;
        yNorm = (y / xsect.yFull);
        return (xsect.aFull * lookup(yNorm, A_Circ, N_A_Circ));
    }

    // xsect.c:2462
    static double filled_circ_getYofA(TXsect xsect, double a) {
        double y = 0.0d;
        xsect.@yFull = (xsect.yFull + xsect.yBot);
        xsect.@aFull = (xsect.aFull + xsect.aBot);
        a += xsect.aBot;
        y = circ_getYofA(xsect, a);
        y -= xsect.yBot;
        xsect.@yFull = (xsect.yFull - xsect.yBot);
        xsect.@aFull = (xsect.aFull - xsect.aBot);
        return y;
    }

    // xsect.c:2481
    static double filled_circ_getAofY(TXsect xsect, double y) {
        double a = 0.0d;
        xsect.@yFull = (xsect.yFull + xsect.yBot);
        xsect.@aFull = (xsect.aFull + xsect.aBot);
        y += xsect.yBot;
        a = circ_getAofY(xsect, y);
        a -= xsect.aBot;
        xsect.@yFull = (xsect.yFull - xsect.yBot);
        xsect.@aFull = (xsect.aFull - xsect.aBot);
        return a;
    }

    // xsect.c:2500
    static double filled_circ_getRofY(TXsect xsect, double y) {
        double a = 0.0d;
        double r = 0.0d;
        double p = 0.0d;
        xsect.@yFull = (xsect.yFull + xsect.yBot);
        xsect.@aFull = (xsect.aFull + xsect.aBot);
        y += xsect.yBot;
        a = circ_getAofY(xsect, y);
        r = ((0.25d * xsect.yFull) * lookup((y / xsect.yFull), R_Circ, N_R_Circ));
        p = ((a / r));
        a = (a - xsect.aBot);
        p = ((p - xsect.rBot) + xsect.sBot);
        r = (a / p);
        xsect.@yFull = (xsect.yFull - xsect.yBot);
        xsect.@aFull = (xsect.aFull - xsect.aBot);
        return r;
    }

    // xsect.c:2531
    static double getYcircular(double alpha) {
        double theta = 0.0d;
        if (R.dge(alpha, 1.0d)) {
            return 1.0d;
        }
        if ((alpha <= 0.0d)) {
            return 0.0d;
        }
        if ((alpha <= 1.0e-5d)) {
            theta = Glibm.pow((37.6911d * alpha), (1.0d / 3.0d));
            return ((theta * theta) / 16.0d);
        }
        theta = getThetaOfAlpha(alpha);
        return (((1.0d - Glibm.cos((theta / 2.0d)))) / 2.0d);
    }

    // xsect.c:2545
    static double getScircular(double alpha) {
        double theta = 0.0d;
        if (R.dge(alpha, 1.0d)) {
            return 1.0d;
        }
        if ((alpha <= 0.0d)) {
            return 0.0d;
        }
        if ((alpha <= 1.0e-5d)) {
            theta = Glibm.pow((37.6911d * alpha), (1.0d / 3.0d));
            return (Glibm.pow(theta, (13.0d / 3.0d)) / 124.4797d);
        }
        theta = getThetaOfAlpha(alpha);
        return ((Glibm.pow(((theta - Glibm.sin(theta))), (5.0d / 3.0d)) / ((2.0d * 3.141592654d))) / Glibm.pow(theta, (2.0d / 3.0d)));
    }

    // xsect.c:2559
    static double getAcircular(double psi) {
        double theta = 0.0d;
        if (R.dge(psi, 1.0d)) {
            return 1.0d;
        }
        if ((psi <= 0.0d)) {
            return 0.0d;
        }
        if ((psi <= 1.0e-6d)) {
            theta = Glibm.pow((124.4797d * psi), (3.0d / 13.0d));
            return (((theta * theta) * theta) / 37.6911d);
        }
        theta = getThetaOfPsi(psi);
        return (((theta - Glibm.sin(theta))) / ((2.0d * 3.141592654d)));
    }

    // xsect.c:2573
    static double getThetaOfAlpha(double alpha) {
        int k = 0;
        double theta = 0.0d;
        double theta1 = 0.0d;
        double ap = 0.0d;
        double d = 0.0d;
        if (R.dgt(alpha, 0.04d)) {
            theta = (1.2d + ((5.08d * ((alpha - 0.04d))) / 0.96d));
        } else {
            theta = ((0.031715d - (12.79384d * alpha)) + (8.28479d * Math.sqrt(alpha)));
        }
        theta1 = theta;
        ap = (((2.0d * 3.141592654d)) * alpha);
        L_f1: for (k = 1; (k <= 40); k++) {
            d = ((-(((ap - theta) + Glibm.sin(theta)))) / ((1.0d - Glibm.cos(theta))));
            if (R.dgt(d, 1.0d)) {
                d = ((R.dge((d), 0.0d) ? Math.abs(1.0d) : (-(Math.abs(1.0d)))));
            }
            theta = (theta - d);
            if ((Math.abs(d) <= 0.0001d)) {
                return theta;
            }
        }
        return theta1;
    }

    // xsect.c:2593
    static double getThetaOfPsi(double psi) {
        int k = 0;
        double theta = 0.0d;
        double theta1 = 0.0d;
        double ap = 0.0d;
        double tt = 0.0d;
        double tt23 = 0.0d;
        double t3 = 0.0d;
        double d = 0.0d;
        if (R.dgt(psi, 0.90d)) {
            theta = (4.17d + ((1.12d * ((psi - 0.90d))) / 0.176d));
        } else if (R.dgt(psi, 0.5d)) {
            theta = (3.14d + ((1.03d * ((psi - 0.5d))) / 0.4d));
        } else if (R.dgt(psi, 0.015d)) {
            theta = (1.2d + ((1.94d * ((psi - 0.015d))) / 0.485d));
        } else {
            theta = ((0.12103d - (55.5075d * psi)) + (15.62254d * Math.sqrt(psi)));
        }
        theta1 = theta;
        ap = (((2.0d * 3.141592654d)) * psi);
        L_f1: for (k = 1; (k <= 40); k++) {
            theta = Math.abs(theta);
            tt = (theta - Glibm.sin(theta));
            tt23 = Glibm.pow(tt, (2.0d / 3.0d));
            t3 = Glibm.pow(theta, (1.0d / 3.0d));
            d = (((ap * theta) / t3) - (tt * tt23));
            d = (d / ((((ap * ((2.0d / 3.0d))) / t3) - ((((5.0d / 3.0d)) * tt23) * ((1.0d - Glibm.cos(theta)))))));
            theta = (theta - d);
            if ((Math.abs(d) <= 0.0001d)) {
                return theta;
            }
        }
        return theta1;
    }

}
