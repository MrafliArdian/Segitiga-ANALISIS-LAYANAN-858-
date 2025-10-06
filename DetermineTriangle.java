
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
public class SegitigaNada {
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
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this
 * template
 */
package determinetriangle_novelshiffa;

/**
 *
 * @author novel
 */
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
