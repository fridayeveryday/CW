import java.io.FileWriter;
import java.io.IOException;
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

    public static ArrayList<String> tests = new ArrayList<>(Arrays.asList(
            "01011100010101010100111",
            "11111100000100001001000",
            "10010111000011001001010",
            "11100111110000001011101",
            "01000100101001011100110",
            "11100011011111000111101",
            "00000001111010010000000",
            "01011001101110001110110",
            "00101110100111110110011",
            "11111111010111110110101"

    ));

    public static StringBuilder totalRoute = new StringBuilder("");
    // массив для того, чтобы проверки на цикл (был ли в ланном состоянии автомат или нет)
    public static boolean[] visitedStates = new boolean[10];

    public static boolean zeroStateFirstTime = true;

    public static String inputMethodType;

    public static void main(String[] args) throws IOException {

//        tests();
        initializateArraysByZero();
        long m;
        long end;
        System.out.println("Введите строку из 1 и 0 для Х1-Х23");
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.next();
        if (!fetchInputx(inputStr)) {
            return;
        }
        if (chooseLogicVar()) {
            if (inputMethodType.equals("y")) {
                m = System.nanoTime();
                v3Miliv3_Logic();
            } else {
                m = System.nanoTime();
                v3Miliv3_Graph();
            }

        } else {
            System.out.println("Выбран неверный метод.");
            return;
        }
        end = System.nanoTime() - m;
        totalRoute.delete(totalRoute.length() - 4, totalRoute.length());
        System.out.printf("Полный маршрут: %s", totalRoute.toString());
        System.out.print("\nВремя работы программы: ");
        System.out.print(end);
        System.out.print(" нс");
    }

    public static void tests() throws IOException {
        initializateArraysByZero();
        FileWriter writerL = new FileWriter("logic.txt", false);
        FileWriter writerG = new FileWriter("graph.txt", false);

        for (String st : tests) {

            fetchInputx(st);
            long m = System.nanoTime();
            v3Miliv3_Logic();
            long time = System.nanoTime() - m;
            writerL.write(String.valueOf(time));
            writerL.write("\n");
            writerL.flush();
            System.out.printf("Полный маршрут: %s", totalRoute.toString());

            m = System.nanoTime();
            v3Miliv3_Graph();
            time = System.nanoTime() - m;
            writerG.write(String.valueOf(time));
            writerG.write("\n");
            writerG.flush();
            System.out.printf("Полный маршрут: %s", totalRoute.toString());

            System.out.println();
            System.out.printf("Xs %s", st);
        }
    }

    public static boolean chooseLogicVar() {
        System.out.println("Выберите вариант обхода: логические выражения [y], граф-схема [n]");
        Scanner scanner = new Scanner(System.in);
        inputMethodType = scanner.next();
        return inputMethodType.equals("y") || inputMethodType.equals("n");
    }


    public static void v3Miliv3_Logic() {
        int state;
        logStates(0);
        while (true) {
            refreshLogicFunction();
            state = fetchStateInDecimal();
            logStates(state);
            if (state == 0)
                break;
            if (visitedStates[state]) {
                System.out.println("Зацикливание!!!");
                break;
            }
            visitedStates[state] = true;
        }
    }

    public static void logStates(int state) {
        StringBuilder Ys = new StringBuilder("");
        System.out.printf("Текущее состояние: %d\n", state);
        String s = fetchStateInBinaryString();
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
        for (int i = a.length - 1; i >= 1; i--) {
            if (a[i])
                binaryCodeOfState.append('1');
            else
                binaryCodeOfState.append('0');
        }
        return binaryCodeOfState.toString();
    }

    public static boolean fetchInputx(String st) {
        if (st.length() != 23) {
            System.out.println("Введено неверное количество Х-ов");
            return false;
        }
        char oneChar;
        for (int i = 1; i <= st.length(); i++) {
            oneChar = st.charAt(i - 1);
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


    public static void convertDecimalStatesIntoBooleanType(int state) {
        char[] binaryStrByChars = Integer.toBinaryString(state).toCharArray();
        Arrays.fill(a, false);
        for (int i = binaryStrByChars.length - 1, j = 1; i >= 0; i--, j++) {
            a[j] = binaryStrByChars[i] == '1';
        }
    }

    public static void v3Miliv3_Graph() {
        logStates(0);
        int state = 0;
        while (true) {
            switch (state) {
                case 0: {
                    state = s0();
                    convertDecimalStatesIntoBooleanType(state);
                    logStates(state);
                    if (state == 0)
                        return;
                    if (visitedStates[state]) {
                        System.out.println("Зацикливание!!!");
                        return;
                    }
                    visitedStates[state] = true;
                    break;
                }
                case 1: {
                    state = s1();
                    convertDecimalStatesIntoBooleanType(state);
                    logStates(state);
                    if (state == 0)
                        return;
                    if (visitedStates[state]) {
                        System.out.println("Зацикливание!!!");
                        return;
                    }
                    visitedStates[state] = true;
                    break;
                }
                case 2: {
                    state = s2();
                    convertDecimalStatesIntoBooleanType(state);
                    logStates(state);
                    if (state == 0)
                        return;
                    if (visitedStates[state]) {
                        System.out.println("Зацикливание!!!");
                        return;
                    }
                    visitedStates[state] = true;
                    break;
                }
                case 3: {
                    state = s3();
                    convertDecimalStatesIntoBooleanType(state);
                    logStates(state);
                    if (state == 0)
                        return;
                    if (visitedStates[state]) {
                        System.out.println("Зацикливание!!!");
                        return;
                    }
                    visitedStates[state] = true;
                    break;
                }
                case 4: {
                    state = s4();
                    convertDecimalStatesIntoBooleanType(state);
                    logStates(state);
                    if (state == 0)
                        return;
                    if (visitedStates[state]) {
                        System.out.println("Зацикливание!!!");
                        return;
                    }
                    visitedStates[state] = true;
                    break;
                }
                case 5: {
                    state = s5();
                    convertDecimalStatesIntoBooleanType(state);
                    logStates(state);
                    if (state == 0)
                        return;
                    if (visitedStates[state]) {
                        System.out.println("Зацикливание!!!");
                        return;
                    }
                    visitedStates[state] = true;
                    break;
                }
                case 6: {
                    state = s6();
                    convertDecimalStatesIntoBooleanType(state);
                    logStates(state);
                    if (state == 0)
                        return;
                    if (visitedStates[state]) {
                        System.out.println("Зацикливание!!!");
                        return;
                    }
                    visitedStates[state] = true;
                    break;
                }
                case 7: {
                    state = s7();
                    convertDecimalStatesIntoBooleanType(state);
                    logStates(state);
                    if (state == 0)
                        return;
                    if (visitedStates[state]) {
                        System.out.println("Зацикливание!!!");
                        return;
                    }
                    visitedStates[state] = true;
                    break;
                }
                case 8: {
                    state = s8();
                    convertDecimalStatesIntoBooleanType(state);
                    logStates(state);
                    if (state == 0)
                        return;
                    if (visitedStates[state]) {
                        System.out.println("Зацикливание!!!");
                        return;
                    }
                    visitedStates[state] = true;
                    break;
                }
                case 9: {
                    state = s9();
                    convertDecimalStatesIntoBooleanType(state);
                    logStates(state);
                    if (state == 0)
                        return;
                    if (visitedStates[state]) {
                        System.out.println("Зацикливание!!!");
                        return;
                    }
                    visitedStates[state] = true;
                    break;
                }
            }
        }
    }

    public static int s0() {
        Arrays.fill(y, false);
        if ((!x[1] && !x[2] && !x[4] && !x[5]) || (!x[1] && x[2] && !x[3] && !x[4] && !x[5])) {
            y[1] = true;
            y[2] = true;
            y[3] = true;
            return 1;
        } else if ((!x[1] && !x[2] && !x[4] && x[5]) || (!x[1] && x[2] && !x[3] && !x[4] && x[5])) {
            return 2;
        } else if ((!x[1] && !x[2] && x[4] && !x[6] && !x[14]) || (!x[1] && x[2] && !x[3] && x[4] && !x[6] && !x[14])
                || (x[1] && !x[14])) {
            y[5] = true;
            y[6] = true;
            y[7] = true;
            return 4;
        } else if (!x[1] && !x[2] && x[4] && !x[6] && x[14] && x[15] || !x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && x[15]
                || x[1] && x[14] && x[15]) {
            y[16] = true;
            y[17] = true;
            y[18] = true;
            return 5;

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
        } else

        //            if ((!x[1] && !x[2] && x[4] && !x[6] && x[14] && !x[15] && x[18])
//                || (!x[1] && x[2] && !x[3] && x[4] && !x[6] && x[14] && !x[15] && x[18])
//                || (x[1] && x[14] && !x[15] && x[18]))
        {
            y[21] = true;
            return 8;
        }

    }

    public static int s1() {
        Arrays.fill(y, false);
        if (!x[11]) {
            return 1;
        } else
//            if (x[11])
        {
            y[4] = true;
            return 2;
        }
    }

    public static int s2() {
        Arrays.fill(y, false);
        if (x[12] && x[13] && x[9]) {
            y[5] = true;
            y[7] = true;
            y[8] = true;
            y[9] = true;
            return 3;
        } else if (!x[12] && !x[14] || x[12] && !x[13] || x[12] && x[13] && !x[9]) {
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
        } else
//            if (!x[12] && x[14] && !x[15] && !x[18])
        {
            y[21] = true;
            return 8;
        }
    }


    public static int s3() {
        Arrays.fill(y, false);
        if (!x[16]) {
            return 3;
        } else
//            if (x[16])
        {
            return 6;
        }
    }

    public static int s4() {
        Arrays.fill(y, false);
        if (x[17] && !x[19] || x[17] && x[19] && !x[20] && !x[21] && !x[22]) {
            y[10] = true;
            y[11] = true;
            return 2;
        } else if (!x[17]) {
            return 4;
        } else if (x[17] && x[19] && x[20]) {
            y[17] = true;
            y[18] = true;
            y[20] = true;
            return 5;

        } else if (x[17] && x[19] && !x[20] && !x[21] && x[22]) {
            y[23] = true;
            return 6;
        } else
//            if (x[17] && x[19] && !x[20] && x[21])
        {
            y[1] = true;
            y[15] = true;
            y[19] = true;
            return 7;
        }
    }


    public static int s5() {
        Arrays.fill(y, false);
        if (x[10] && x[7]) {
            y[5] = true;
            y[7] = true;
            y[9] = true;
            y[13] = true;
            return 3;
        } else if (!x[10]) {
            return 5;
        } else
//            if (x[10] && !x[7])
        {
            y[12] = true;
            return 6;
        }
    }

    public static int s6() {
        Arrays.fill(y, false);
        y[22] = true;
        return 8;
    }

    public static int s7() {
        Arrays.fill(y, false);
        if (x[8]) {
            y[14] = true;
            return 6;
        } else
//            if (!x[8])
        {
            return 7;
        }
    }

    public static int s8() {
        y[6] = true;
        y[7] = true;
        y[24] = true;
        return 9;
    }

    public static int s9() {
        Arrays.fill(y, false);
        if (x[23]) {
            y[25] = true;
            return 0;
        } else
//            if (!x[23])
        {
            return 9;
        }
    }

}
