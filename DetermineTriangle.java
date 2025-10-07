import java.util.*;

// #Coder : Adam Leonard C Munthe
public class DetermineTriangle_Adam {

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
        double[] s = { a, b, c };
        Arrays.sort(s); // s[2] terbesar
        double x2 = s[0] * s[0], y2 = s[1] * s[1], z2 = s[2] * s[2];
        if (isSame(x2 + y2, z2))
            return "Segitiga Siku-Siku (Right Triangle)";

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

// Punyanya Nada Nabilah - 103012300202
public class Segitiga_Nada {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Masukkan nilai a: ");
        double a = input.nextDouble();
        System.out.println("Masukkan nilai b: ");
        double b = input.nextDouble();
        System.out.println("Masukkan nilai c: ");
        double c = input.nextDouble();

        segitiga(a, b, c);
        input.close();
    }

    // Fungsi untuk mengecek apakah dua sisi dianggap sama (toleransi 1%)
    static boolean sama(double x, double y) {
        return Math.abs(x - y) <= 0.01 * Math.max(x, y);
    }

    static void segitiga(double a, double b, double c) {
        // Mnegurutkan sisi dari terkecil ke terbesar
        double[] sisi = { a, b, c };
        java.util.Arrays.sort(sisi);
        a = sisi[0];
        b = sisi[1];
        c = sisi[2];

        // 1. Validasi Awal
        if (a <= 0 || b <= 0 || c <= 0) {
            System.out.println("Tidak ada segitiga yang dapat dibangun.");
            return;
        }
        if (c >= a + b) {
            System.out.println("Tidak ada segitiga yang dibangun karena sisi terbesar >= penjumlahan kedua sisi");
            return;
        }

        // 2. Klasifikasi Segitiga
        if (sama(a, b) && sama(b, c)) {
            System.out.println("Segitiga Sama Sisi (Equilateral)");
        } else if (sama(a, b) || sama(b, c) || sama(a, c)) {
            System.out.println("Segitiga Sama Kaki (Isosceles)");
        } else if (Math.abs(c * c - (a * a + b * b)) <= 0.01 * (c * c)) {
            System.out.println("Segitiga Siku-Siku (Right Triangle)");
        } else {
            System.out.println("Segitiga bebas (Scalene)");
        }
    }
}

// Punyanya Novel Shiffa Octaviani - 103012300383
package determinetriangle_novelshiffa;


import java.util.Scanner;

public class DetermineTriangle_NovelShiffa {

    static void segitiga(double a, double b, double c) {
        if (a > 0 && b > 0 && c > 0) {
            if (a + b > c && a + c > b && b + c > a) {
                if (a == b && b == c)
                    System.out.println("Segitiga Sama Sisi");
                else if (a == b || a == c || b == c)
                    System.out.println("Segitiga Sama Kaki");
                else
                    System.out.println("Segitiga Sembarang");
            } else {
                System.out.println("Bukan segitiga (tidak memenuhi syarat panjang sisi)");
            }
        } else {
            System.out.println("Bukan segitiga (ada sisi <= 0)");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== PROGRAM PENENTUAN JENIS SEGITIGA ===");
        System.out.print("Masukkan sisi a (atau -1 untuk keluar): ");
        double a = sc.nextDouble();

        // kalau user mau berhenti
        if (a == -1) {
            System.out.println("Program selesai. Terima kasih!");
            return; // langsung keluar dari main()
        }

        System.out.print("Masukkan sisi b: ");
        double b = sc.nextDouble();
        System.out.print("Masukkan sisi c: ");
        double c = sc.nextDouble();

        System.out.println("\n=== HASIL PENENTUAN SEGITIGA ===");
        segitiga(a, b, c);
    }
}

// property of Fathir Ahmad Lidzikri - 103012300259
public class JenisSegitiga_Fathir {

    // Fungsi pembanding
    public static boolean hampirSama(double x, double y) {
        return Math.abs(x - y) <= 0.01 * Math.max(x, y);
    }

    // Fungsi untuk menentukan jenis segitiga
    public static String jenisSegitiga(double a, double b, double c) {
        double x = a, y = b, z = c;

        // Tentukan sisi terbesar secara manual
        double max = x;
        if (y > max) max = y;
        if (z > max) max = z;

        // Tentukan dua sisi lainnya
        double s1 = 0, s2 = 0;
        if (max == x) { s1 = y; s2 = z; }
        else if (max == y) { s1 = x; s2 = z; }
        else { s1 = x; s2 = y; }

        // Validasi awal
        if ((x <= 0 || y <= 0 || z <= 0) || (max >= s1 + s2))
            return "Tidak dapat membentuk segitiga";

        // Klasifikasi
        if (hampirSama(a, b) && hampirSama(b, c))
            return "Segitiga Sama Sisi";
        else if (hampirSama(a, b) || hampirSama(b, c) || hampirSama(a, c)) {
            if (hampirSama(max * max, s1 * s1 + s2 * s2))
                return "Segitiga Sama Kaki dan Siku-Siku";
            return "Segitiga Sama Kaki";
        }
        else if (hampirSama(max * max, s1 * s1 + s2 * s2))
            return "Segitiga Siku-Siku";
        else
            return "Segitiga Bebas";
    }

    // Main (contoh uji tanpa input pengguna)
    public static void main(String[] args) {
        System.out.println(jenisSegitiga(3, 3, 3));      // Sama Sisi
        System.out.println(jenisSegitiga(3, 4, 5));      // Siku-Siku
        System.out.println(jenisSegitiga(5, 5, 7));      // Sama Kaki
        System.out.println(jenisSegitiga(2, 2, 3.02));   // Sama Kaki (karena beda <1%)
        System.out.println(jenisSegitiga(1, 2, 3));      // Tidak valid
        System.out.println(jenisSegitiga(3, 4.03, 5.05)); // Siku-Siku
    }
}

// Punyanya Muhammad Rafli Ardiansyah - 103012300082
public class DetermineTriangle_Rafli {
    private static String invalidMsg = "Tidak ada segitiga dapat dibangun.";


    private static double[] sorted(double a, double b, double c) {
        double[] s = {a, b, c};
        Arrays.sort(s);
        return s;
    }

    public static String classifyInt(int a, int b, int c) {
        if (a <= 0 || b <= 0 || c <= 0) return invalidMsg;
        int[] s = {a, b, c};
        Arrays.sort(s);
        int s1 = s[0], s2 = s[1], s3 = s[2];

        if (s3 >= s1 + s2) return invalidMsg;

        if (a == b && b == c) return "Segitiga Sama Sisi";
        else if (a == b || b == c || a == c) return "Segitiga Sama Kaki";
        else if (s1*s1 + s2*s2 == s3*s3) return "Segitiga Siku-Siku";
        else return "Segitiga Bebas";
    }

    private static boolean approxEqual(double x, double y, double relTol) {
        double scale = Math.max(Math.abs(x), Math.abs(y));
        if (scale < 1e-12) return Math.abs(x - y) <= 1e-12; 
        return Math.abs(x - y) <= relTol * scale;
    }

    public static String classifyDecimal(double a, double b, double c, double relTol) {
        if (a <= 0 || b <= 0 || c <= 0) return invalidMsg;

        double[] s = sorted(a, b, c);
        double s1 = s[0], s2 = s[1], s3 = s[2];

        if (s3 >= s1 + s2 || approxEqual(s3, s1 + s2, relTol)) {
            return invalidMsg;
        }

        boolean abEqual = approxEqual(a, b, relTol);
        boolean bcEqual = approxEqual(b, c, relTol);
        boolean acEqual = approxEqual(a, c, relTol);

        if (abEqual && bcEqual) return "Segitiga Sama Sisi";
        else if (abEqual || bcEqual || acEqual) return "Segitiga Sama Kaki";
        else {
            double lhs = s1*s1 + s2*s2;
            double rhs = s3*s3;
            if (approxEqual(lhs, rhs, relTol)) return "Segitiga Siku-Siku";
            else return "Segitiga Bebas";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("--- Determine Triangle ---");
        System.out.println("Pilih mode: 1 = Bilangan bulat, 2 = Pecahan (toleransi 1%)");
        System.out.print("Mode: ");
        int mode = sc.nextInt();

        if (mode == 1) {
            System.out.print("Masukkan a (int): ");
            int a = sc.nextInt();
            System.out.print("Masukkan b (int): ");
            int b = sc.nextInt();
            System.out.print("Masukkan c (int): ");
            int c = sc.nextInt();
            System.out.println("Hasil: " + classifyInt(a, b, c));
        } else if (mode == 2) {
            System.out.print("Masukkan a (double): ");
            double a = sc.nextDouble();
            System.out.print("Masukkan b (double): ");
            double b = sc.nextDouble();
            System.out.print("Masukkan c (double): ");
            double c = sc.nextDouble();
            System.out.println("Hasil: " + classifyDecimal(a, b, c, 0.01));
        } else {
            System.out.println("Mode tidak dikenal. Bye~");
        }
        sc.close();
    }
}
