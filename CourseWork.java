import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CourseWork {
    // массив входных сигналов X
    public static boolean[] x = new boolean[24];
    // массив выходных сигналов Y
    public static boolean[] y = new boolean[26];
    // массив закодированных состояний S
    public static boolean[] a = new boolean[5];

    public static boolean[] w = new boolean[5];

    public static boolean[] u = new boolean[5];

    public static StringBuilder totalRoute = new StringBuilder("S0/- => ");

    // массив для того, чтобы проверки на цикл (был ли в ланном состоянии автомат или нет)
    public static boolean[] visitedStates = new boolean[10];


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String xsString = scanner.nextLine();
        char[] arr = xsString.toCharArray();
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (String.valueOf(c).matches("[0-9]")) {
                out.add("[");
                while (String.valueOf(c).matches("[0-9]")) {
                    out.add(String.valueOf(c));
                    i++;
                    c = arr[i];
                }
                out.add("]");
                out.add(" &&");
            }
            out.add(String.valueOf(c));

        }
        out.remove(out.size() - 1);
        out.remove(out.size() - 1);
        StringBuilder str = new StringBuilder("");
        for (String s : out) {
            str.append(s);
        }
        System.out.println(str.toString());
//        initializateArraysByZero();
//
//        if (!fetchInputx()) {
//            return;
//        }
//        if (chooseLogicVar()) {
//            v3Miliv3_Logic();
//        } else {
//            v3Miliv3_Graph();
//        }

    }


    public static boolean chooseLogicVar() {
        System.out.println("Выберите вариант обхода: логические выражения [1], граф-схема [0]");
        Scanner scanner = new Scanner(System.in);
        return scanner.next().equals("1");
    }


    public static void v3Miliv3_Logic() {
        int state;
        logStates(0);
        while (true) {
            refreshLogicFunction();
            state = fetchStateInDecimal();
            logStates(state);
            if (visitedStates[state]) {
                System.out.println("Зацикливание!!!");
                totalRoute.delete(totalRoute.length() - 4, totalRoute.length());
                break;
            }
            visitedStates[state] = true;
            if (state == 0)
                break;
        }
        System.out.printf("Полный маршрут: %s", totalRoute.toString());
    }


    public static void logStates(int state) {

        StringBuilder Ys = new StringBuilder("");
        System.out.printf("Текущее состояние: %d\n", state);
        System.out.printf("Код текущего состояния: %s\n", fetchStateInBinaryString());
        for (int i = 1; i <= 25; i++) {
            System.out.printf("y%d ", i);
        }
        System.out.println();
        for (int i = 1; i <= 9; i++) {
            System.out.printf("%d  ", y[i] ? 1 : 0);
            if (y[i]) {
                Ys.append("Y");
                Ys.append(i);
            }
        }
        for (int i = 10; i < y.length; i++) {
            System.out.printf(" %d  ", y[i] ? 1 : 0);
            if (y[i]) {
                Ys.append("Y");
                Ys.append(i);
            }
        }
        if (Ys.length() == 0)
            Ys.append("-");
        System.out.println();
        totalRoute.append("S");
        totalRoute.append(state);
        totalRoute.append("/");
        totalRoute.append(Ys);
        totalRoute.append(" ");
        if (state != 0)
            totalRoute.append("=> ");


    }


    public static void initializateArraysByZero() {
        Arrays.fill(visitedStates, false);
        Arrays.fill(y, false);
        Arrays.fill(x, false);
        Arrays.fill(a, false);
        Arrays.fill(w, false);
        Arrays.fill(u, false);
        Arrays.fill(a, false);


    }

    public static void refreshLogicFunction() {
        // Подсчет всех лог. функций
        y[1] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && !x[4] && !x[5] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && !x[4] && !x[5] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && !x[15] && x[18] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && !x[20] && x[21];
        y[2] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && !x[4] && !x[5] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && !x[4] && !x[5] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && !x[15] && x[18] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && !x[15] && x[18];
        y[3] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && !x[4] && !x[5] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && !x[4] && !x[5];
        y[4] = a[1] && !a[2] && !a[3] && !a[4] && x[11];
        y[5] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && !x[14] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && !x[14] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && !x[14] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && x[13] && x[9] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && !x[14] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && !x[13] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && x[13] && !x[9] || a[1] && !a[2] && a[3] && !a[4] && x[10] && x[7];
        y[6] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && !x[14] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && !x[14] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && !x[14] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && !x[14] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && !x[13] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && x[13] && !x[9] || !a[1] && !a[2] && !a[3] && a[4];
        y[7] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && !x[14] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && !x[14] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && !x[14] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && x[13] && x[9] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && !x[14] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && !x[13] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && x[13] && !x[9] || a[1] && !a[2] && a[3] && !a[4] && x[10] && x[7] || !a[1] && !a[2] && !a[3] && a[4];
        y[8] = !a[1] && a[2] && !a[3] && !a[4] && x[12] && x[13] && x[9];
        y[9] = !a[1] && a[2] && !a[3] && !a[4] && x[12] && x[13] && x[9] || a[1] && !a[2] && a[3] && !a[4] && x[10] && x[7];
        y[10] = !a[1] && !a[2] && a[3] && !a[4] && x[17] && !x[19] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && !x[20] && !x[21] && !x[22];
        y[11] = !a[1] && !a[2] && a[3] && !a[4] && x[17] && !x[19] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && !x[20] && !x[21] && !x[22];
        y[12] = a[1] && !a[2] && a[3] && !a[4] && x[10] && !x[7];
        y[13] = a[1] && !a[2] && a[3] && !a[4] && x[10] && x[7];
        y[14] = a[1] && a[2] && a[3] && !a[4] && x[8];
        y[15] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && !x[15] && x[18] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && !x[20] && x[21];
        y[16] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && x[15] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && x[15] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && x[15] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && x[15];
        y[17] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && x[15] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && x[15] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && x[15] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && x[15] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && x[20];
        y[18] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && x[15] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && x[15] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && x[15] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && x[15] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && x[20];
        y[19] = !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && !x[20] && x[21];
        y[20] = !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && x[20];
        y[21] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && !x[15] && !x[18] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && !x[15] && !x[18] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && !x[15] && !x[18] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && !x[15] && !x[18];
        y[22] = !a[1] && a[2] && a[3] && !a[4];
        y[23] = !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && !x[20] && !x[21] && x[22];
        y[24] = !a[1] && !a[2] && !a[3] && a[4];
        y[25] = a[1] && !a[2] && !a[3] && a[4] && x[23];
        w[1] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && !x[4] && !x[5] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && !x[4] && !x[5] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && x[15] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && x[15] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && x[15] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && !x[15] && x[18] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && x[13] && x[9] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && x[15] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && x[20] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && !x[20] && x[21] || !a[1] && !a[2] && !a[3] && a[4];
        w[2] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && !x[4] && x[5] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && !x[4] && x[5] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && x[6] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && x[6] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && x[3] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && !x[15] && x[18] || a[1] && !a[2] && !a[3] && !a[4] && x[11] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && !x[19] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && !x[20] && !x[21] && !x[22] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && !x[20] && !x[21] && x[22] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && !x[20] && x[21] || a[1] && !a[2] && a[3] && !a[4] && x[10] && x[7] || a[1] && !a[2] && a[3] && !a[4] && x[10] && !x[7];
        w[3] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && !x[14] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && !x[14] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && !x[14] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && x[15] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && x[15] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && x[15] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && x[6] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && x[6] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && x[3] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && !x[15] && x[18] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && !x[15] && x[18] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && !x[14] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && !x[13] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && x[13] && !x[9] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && x[15] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && !x[15] && x[18] || a[1] && a[2] && !a[3] && !a[4] && x[16];
        w[4] = !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && !x[2] && x[4] && !x[6] && x[14] && !x[15] && !x[18] || !a[1] && !a[2] && !a[3] && !a[4] && !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && !x[15] && !x[18] || !a[1] && !a[2] && !a[3] && !a[4] && x[1] && x[14] && !x[15] && !x[18] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && !x[15] && !x[18] || !a[1] && a[2] && a[3] && !a[4];
        u[1] = a[1] && !a[2] && !a[3] && !a[4] && x[11] || a[1] && a[2] && !a[3] && !a[4] && x[16] || a[1] && !a[2] && a[3] && !a[4] && x[10] && !x[7] || a[1] && a[2] && a[3] && !a[4] && x[8] || a[1] && !a[2] && !a[3] && a[4] && x[23];
        u[2] = !a[1] && a[2] && !a[3] && !a[4] && !x[12] && !x[14] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && !x[13] || !a[1] && a[2] && !a[3] && !a[4] && x[12] && x[13] && !x[9] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && x[15] || !a[1] && a[2] && !a[3] && !a[4] && !x[12] && x[14] && !x[15] && !x[18] || !a[1] && a[2] && a[3] && !a[4];
        u[3] = !a[1] && !a[2] && a[3] && !a[4] && x[17] && !x[19] || !a[1] && !a[2] && a[3] && !a[4] && x[17] && x[19] && !x[20] && !x[21] && !x[22] || a[1] && !a[2] && a[3] && !a[4] && x[10] && x[7] || !a[1] && a[2] && a[3] && !a[4];
        u[4] = a[1] && !a[2] && !a[3] && a[4] && x[23];
        // выделение нового состояния из функции переходов
        // w - установление 1 в позиции кода состояний 00000->00001
        // нумерация позиций кода справа налево
        if (w[1]) {
            a[1] = true;
        }
        if (w[2]) {
            a[2] = true;
        }
        if (w[3]) {
            a[3] = true;
        }
        if (w[4]) {
            a[4] = true;
        }
        // u - установление 0 в позиции кода состояний 00001->00000
        if (u[1]) {
            a[1] = false;
        }
        if (u[2]) {
            a[2] = false;
        }
        if (u[3]) {
            a[3] = false;
        }
        if (u[4]) {
            a[4] = false;
        }
    }

    public static int fetchStateInDecimal() {
        String binaryCodeOfState = fetchStateInBinaryString();
        return Integer.parseInt(binaryCodeOfState.toString(), 2);
    }

    public static String fetchStateInBinaryString() {
        StringBuilder binaryCodeOfState = new StringBuilder("");
        boolean position;
        for (int i = a.length - 1; i >= 1; i--) {
            position = a[i];
            if (position)
                binaryCodeOfState.append('1');
            else
                binaryCodeOfState.append('0');
        }
        return binaryCodeOfState.toString();
    }

    public static boolean fetchInputx() {
        System.out.println("Введите строку из 1 и 0 для Х1-Х23");
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.next();
        if (inputStr.length() != 23) {
            System.out.println("Введено неверное количество Х-ов");
            return false;
        }
        char oneChar;
        for (int i = 1; i <= inputStr.length(); i++) {
            oneChar = inputStr.charAt(i - 1);
            if (oneChar == '0') {
                x[i] = false;
            } else if (oneChar == '1') {
                x[i] = true;
            } else {
                System.out.println("Данные в неверном формате");
                return false;
            }
        }
        System.out.println();
//        for (boolean xs : x) {
//            System.out.print(xs ? 1 : 0);
//        }
//        System.out.println();
        return true;
    }


    public static void v3Miliv3_Graph() {

    }

    public static int s0() {
        Arrays.fill(y, false);
        if ((!x[1] && !x[2] && !x[4] && !x[5]) || (!x[1] && x[2] && !x[3] && !x[4] && !x[5])) {
            y[1] = true;
            y[2] = true;
            y[3] = true;
            logStates(0);
            return 1;
        } else if ((!x[1] && !x[2] && !x[4] && x[5]) || (!x[1] && x[2] && !x[3] && !x[4] && x[5])) {
            return 2;
        } else if ((!x[1] && !x[2] && x[4] && !x[6] && x[14] && x[15]) || (!x[1] && x[2] && !x[3] && x[4] && !x[6] && !x[14])
                || (x[1] && !x[14])) {
            y[5] = true;
            y[6] = true;
            y[7] = true;
            return 4;
        } else if ((!x[1] && !x[2] && x[4] && x[6]) || (!x[1] && x[2] && !x[3] && x[4] && x[6])
                || (!x[1] && x[2] && x[3])) {
            return 6;
        } else if ((!x[1] && !x[2] && x[4] && !x[6] && x[14] && !x[15] && x[18])
                || (!x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && !x[15] && x[18])
                || (x[1] && x[14] && !x[15] && x[18])) {
            y[1] = true;
            y[2] = true;
            y[15] = true;
            return 7;
        } else if ((!x[1] && !x[2] && x[4] && !x[6] && x[14] && !x[15] && x[18])
                || (!x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && !x[15] && x[18])
                || (x[1] && x[14] && !x[15] && x[18])) {
            y[21] = true;
            return 8;
        } else
            return -1;

    }

    public static int s1() {
        Arrays.fill(y, false);
        if (!x[11]) {
            return 1;
        } else if (x[11]) {
            y[4] = true;
            return 2;
        } else return -1;
    }

    public static int s2() {
        Arrays.fill(y, false);
        if (x[12] && x[13] && x[9]) {
            y[5] = true;
            y[7] = true;
            y[8] = true;
            y[9] = true;
            return 3;
        } else if (!x[12] && !x[13] || x[12] && !x[13] || x[12] && x[13] && !x[9]) {
            y[5] = true;
            y[6] = true;
            y[7] = true;
            return 4;
        } else if (!x[12] && x[14] && x[15]) {
            y[16] = true;
            y[17] = true;
            y[18] = true;
            return 5;

        } else if (!x[12] && x[14] && !x[15] && x[18]) {
            y[1] = true;
            y[2] = true;
            y[15] = true;
            return 7;
        } else if (!x[12] && x[14] && !x[15] && !x[18]) {
            y[21] = true;
            return 8;
        } else return -1;
    }


    public static int s3() {
        Arrays.fill(y, false);
        if (!x[16]) {
            return 3;
        } else if (x[16]) {
            return 6;
        } else return -1;
    }

    public static int s4() {
        Arrays.fill(y, false);
        if (x[12] && x[13] && x[9]) {
            y[5] = true;
            y[7] = true;
            y[8] = true;
            y[9] = true;
            return 3;
        } else if (!x[12] && !x[13] || x[12] && !x[13] || x[12] && x[13] && !x[9]) {
            y[5] = true;
            y[6] = true;
            y[7] = true;
            return 4;
        } else if (!x[12] && x[14] && x[15]) {
            y[16] = true;
            y[17] = true;
            y[18] = true;
            return 5;

        } else if (!x[12] && x[14] && !x[15] && x[18]) {
            y[1] = true;
            y[2] = true;
            y[15] = true;
            return 7;
        } else if (!x[12] && x[14] && !x[15] && !x[18]) {
            y[21] = true;
            return 8;
        } else return -1;
    }

}
