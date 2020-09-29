import java.util.ArrayList;
import java.util.Scanner;

public class m {
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
        out.remove(out.size()-1);
        out.remove(out.size()-1);
        StringBuilder str = new StringBuilder("");
        for (String s : out) {
            str.append(s);
        }
        System.out.println(str.toString());
    }
}
