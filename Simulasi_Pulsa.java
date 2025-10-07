import java.text.NumberFormat;
import java.util.*;

public class Simulasi_Masa_Aktif {

    // ==== MODEL & STORAGE (tetap sederhana, pakai yang sudah ada) ====

    static class Akun {
        String msisdn, pin; int saldo; boolean aktif = true;
        List<String> paket = new ArrayList<>();
        Akun(String n, String p, int s){ msisdn=n; pin=p; saldo=s; }
    }

    static final Map<String, Akun> DB = new HashMap<>();
    static final List<String> LOG = new ArrayList<>();
    static final NumberFormat RUPIAH = NumberFormat.getInstance(new Locale("id", "ID"));

    // ==== ENTRYPOINT ====

    public static void main(String[] a){
        seed();

        Scanner sc = new Scanner(System.in);

        // PSPEC 1.0 — Memulai Layanan Beli Masa Aktif
        // Pre: Pengguna kirim *858# dari kartu aktif
        String msisdn = ask(sc,"Masukkan nomor Anda: ");
        if (!DB.containsKey(msisdn) || !DB.get(msisdn).aktif){
            System.out.println("Nomor tidak aktif/terdaftar."); return;
        }
        if (!"*858#".equals(ask(sc,"Ketik *858# untuk mulai: "))) {
            System.out.println("Dial salah."); return;
        }
        // Post: Sistem tampilkan menu utama layanan (persis PSPEC)
        loopMenuUtama(sc, msisdn);
    }

    // ==== MENU UTAMA (PSPEC 1.0, 1.1) ====

    // #comment coder: rafli — PSPEC 1.0/1.1
    static void loopMenuUtama(Scanner sc, String msisdn){
        while (true){
            System.out.println("\n--- *858# MENU UTAMA ---");
            System.out.println("1. Transfer Pulsa");
            System.out.println("2. Masa Aktif");          // target PSPEC 1.1 -> user pilih 2
            System.out.println("3. Minta Pulsa");
            System.out.println("4. Auto TP");
            System.out.println("5. Delete Auto TP");
            System.out.println("6. List Auto TP");
            System.out.println("7. Cek Kupon Undian TP");
            System.out.println("0. Keluar");
            int pilih = askInt(sc,"Pilih: ");

            switch (pilih){
                case 0 -> { System.out.println("Sesi ditutup. Terima kasih."); return; }
                case 2 -> { // PSPEC 1.1 — Memilih Menu Masa Aktif
                    // Pre: Sistem tampilkan menu utama
                    // Post: User pilih 2 dan sistem proses
                    menuBeliMasaAktif_Hal1(sc, msisdn);
                }
                default -> {
                    System.out.println("Fitur belum tersedia di simulasi. Pilih 2 untuk 'Masa Aktif'.");
                }
            }
        }
    }

    // ==== BELI MASA AKTIF — HALAMAN 1 (PSPEC 1.2, 1.3) ====

    // #comment coder: rafli — PSPEC 1.2: Menampilkan Menu Beli Masa Aktif (Hal.1)
    static void menuBeliMasaAktif_Hal1(Scanner sc, String msisdn){
        while (true){
            System.out.println("\n--- BELI MASA AKTIF (Hal.1) ---");
            System.out.println("1. 5hr/Rp2500");
            System.out.println("2. 10hr/Rp4rb");
            System.out.println("3. 15hr/Rp6rb");
            System.out.println("4. 30hr/Rp14rb");
            System.out.println();
            System.out.println("5. 90hr/Rp33rb");
            System.out.println("6. 180hr/Rp62rb");
            System.out.println("7. Next");     // PSPEC 1.3 — Memilih Menu Next
            System.out.println("0. Home");     // kembali ke menu utama
            System.out.println("10. Gift");
            int pilih = askInt(sc,"Pilih: ");

            if (pilih == 0) return;           // balik ke menu utama (Post-condition 1.2 tercapai)
            if (pilih == 7) {
                // PSPEC 1.3 — Post: sistem memproses permintaan Next
                menuBeliMasaAktif_Hal2(sc, msisdn);
                return; // setelah selesai Hal.2, balik ke Home (bisa diubah sesuai preferensi)
            } else if (pilih == 10){
                System.out.println("Gift belum disimulasikan. Pilih 7 untuk Next sesuai PSPEC.");
            } else if (pilih >=1 && pilih <=6){
                System.out.println("Untuk tugas ini, jalur utama: 7 (Next) -> 1 (300hr/Rp97rb).");
            } else {
                System.out.println("Pilihan tidak dikenal.");
            }
        }
    }

    // ==== BELI MASA AKTIF — HALAMAN 2 (PSPEC 1.4, 1.5) ====

    // #comment coder: rafli — PSPEC 1.4: Menampilkan Menu Beli Masa Aktif Lanjutan (Hal.2)
    static void menuBeliMasaAktif_Hal2(Scanner sc, String msisdn){
        while (true){
            System.out.println("\n--- BELI MASA AKTIF (Hal.2) ---");
            System.out.println("1. 300hr/Rp97rb");   // PSPEC 1.5 — Menu yang dipilih
            System.out.println("2. 330hr/Rp106rb");
            System.out.println("3. 306hr/Rp115rb");
            System.out.println("9. Back");
            int pilih = askInt(sc,"Pilih: ");

            if (pilih == 9) {
                // Back ke Hal.1
                return;
            } else if (pilih == 1) {
                // PSPEC 1.5 — Pre: Hal.2 tampil, Post: user pilih 1 dan sistem proses
                konfirmasiBeliMasaAktif(sc, msisdn, 300, 97_000);
                return;
            } else if (pilih == 2) {
                konfirmasiBeliMasaAktif(sc, msisdn, 330, 106_000);
                return;
            } else if (pilih == 3) {
                konfirmasiBeliMasaAktif(sc, msisdn, 306, 115_000);
                return;
            } else {
                System.out.println("Pilihan tidak dikenal.");
            }
        }
    }

    // ==== KONFIRMASI (PSPEC 1.6, 1.7) ====

    // #comment coder: rafli — PSPEC 1.6: Menampilkan Konfirmasi Pembelian 300hr/97rb
    static void konfirmasiBeliMasaAktif(Scanner sc, String msisdn, int hari, int harga){
        System.out.println("\n--- KONFIRMASI BELI MASA AKTIF ---");
        System.out.println(hari + "hr/Rp" + RUPIAH.format(harga));
        System.out.println("1. Ya");   // PSPEC 1.7 — Memilih Menu Ya
        System.out.println("9. Back");
        System.out.println("0. Home");

        int pilih = askInt(sc,"Pilih: ");
        if (pilih == 9) {
            // kembali ke Hal.2
            return;
        }
        if (pilih == 0) {
            // kembali ke Home
            return;
        }
        if (pilih != 1){
            System.out.println("Dibatalkan."); return;
        }

        // ==== EKSEKUSI (PSPEC 1.8 & 1.9) ====
        // PSPEC 1.8 — Pembelian: kirim ke “server operator” (simulasi: cek saldo)
        // PSPEC 1.9 — Berhasil/Gagal: tampilkan hasil dari “server operator”
        prosesPembelianMasaAktif(sc, msisdn, hari, harga);
    }

    // ==== PROSES KE “SERVER OPERATOR” (PSPEC 1.8, 1.9) ====

    // #comment coder: rafli — PSPEC 1.8/1.9: Proses & hasil
    static void prosesPembelianMasaAktif(Scanner sc, String msisdn, int hari, int harga){
        Akun a = DB.get(msisdn);

        if (a.saldo < harga){
            // GAGAL
            String tx = UUID.randomUUID().toString().substring(0,8).toUpperCase();
            LOG.add(String.format("TX:%s BUY:MASA_AKTIF FAIL MSISDN:%s HARGA:%d SISA:%d DURASI:%d",
                    tx, msisdn, harga, a.saldo, hari));
            System.out.println("\nTransaksi GAGAL: Saldo tidak mencukupi.");
            System.out.println("Saldo Anda: Rp " + RUPIAH.format(a.saldo));
            back(sc);
            return;
        }

        // BERHASIL
        a.saldo -= harga;
        String tx = UUID.randomUUID().toString().substring(0,8).toUpperCase();
        LOG.add(String.format("TX:%s BUY:MASA_AKTIF OK MSISDN:%s HARGA:%d SISA:%d DURASI:%d",
                tx, msisdn, harga, a.saldo, hari));

        System.out.println("\nPembelian berhasil!");
        System.out.println("Masa aktif bertambah " + hari + " hari (simulasi).");
        System.out.println("Sisa saldo: Rp " + RUPIAH.format(a.saldo));
        System.out.println("ID Transaksi: " + tx);
        back(sc);
    }


    static String ask(Scanner sc, String p){ System.out.print(p); return sc.nextLine().trim(); }


    static int askInt(Scanner sc, String p){
        while (true){
            try { return Integer.parseInt(ask(sc,p)); }
            catch(Exception e){ System.out.println("Input angka tidak valid."); }
        }
    }

    static void back(Scanner sc){
        System.out.println("\n0. Home");
        while (askInt(sc,"Pilih: ") != 0) System.out.println("Tekan 0 untuk kembali ke Home.");
    }

    static void seed(){
        DB.put("081234567890", new Akun("081234567890","1234",120_000));
        DB.put("089876543210", new Akun("089876543210","0000", 25_000));
        DB.put("081111111111", new Akun("081111111111","9999",  7_500));
    }
}
