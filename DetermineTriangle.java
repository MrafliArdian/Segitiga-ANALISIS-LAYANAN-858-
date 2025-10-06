
import java.util.*;

// #Coder : Adam Leonard C Munthe
public class DetermineTriangle {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Program Penentu Jenis Segitiga ===");

        double a = askDouble(sc, "Masukkan sisi a: ");
        double b = askDouble(sc, "Masukkan sisi b: ");
        double c = askDouble(sc, "Masukkan sisi c: ");

        sc.close();

        // Validasi segitiga
        if (!isValidTriangle(a, b, c)) {
            System.out.println("❌ Tidak dapat membentuk segitiga.");
            return;
        }

        // Klasifikasi segitiga
        String jenis = classifyTriangle(a, b, c);
        System.out.println("\nJenis segitiga: " + jenis);
    }

    // Membaca input double (menerima 3,5 atau 3.5)
    private static double askDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return parseFlexible(s);
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Contoh: 3,5 atau 3.5");
            }
        }
    }

    // Parser fleksibel untuk koma atau titik
    private static double parseFlexible(String s) {
        s = s.trim().replace(',', '.');
        return Double.parseDouble(s);
    }

    // Mengecek apakah sisi-sisi dapat membentuk segitiga
    public static boolean isValidTriangle(double a, double b, double c) {
        return (a > 0 && b > 0 && c > 0) &&
               (a + b > c && a + c > b && b + c > a);
    }

    // Menentukan jenis segitiga
   public static String classifyTriangle(double a, double b, double c) {
    boolean sameAB = isSame(a, b);
    boolean sameBC = isSame(b, c);
    boolean sameCA = isSame(c, a);

    // 1) Sama Sisi
    if (sameAB && sameBC && sameCA) 
        return "Segitiga Sama Sisi (Equilateral)";

    // 2) Sama Kaki
    if (sameAB || sameBC || sameCA) 
        return "Segitiga Sama Kaki (Isosceles)";

    // 3) Siku-Siku (pakai sisi terbesar)
    double[] s = {a, b, c};
    Arrays.sort(s);              // s[2] terbesar
    double x2 = s[0]*s[0], y2 = s[1]*s[1], z2 = s[2]*s[2];
    if (isSame(x2 + y2, z2)) return "Segitiga Siku-Siku (Right Triangle)";

    // 4) Sembarang
    return "Segitiga Sembarang (Scalene)";
}

    // Membandingkan dua nilai dengan toleransi 1%
    private static boolean isSame(double x, double y) {
        double avg = (x + y) / 2.0;
        double diff = Math.abs(x - y);
        return diff <= 0.01 * avg; // toleransi 1%
    }
}
