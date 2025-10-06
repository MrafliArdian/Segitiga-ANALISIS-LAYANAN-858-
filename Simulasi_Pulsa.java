// Simulasi *858# berlevel
// #coder: adamleon

import java.text.NumberFormat;
import java.util.*;

public class Simulasi_Pulsa {

    // Ini Untuk data Dummy nya
    static class Akun {
        String msisdn, pin; int saldo; boolean aktif = true;
        List<String> paket = new ArrayList<>();
        Akun(String n, String p, int s){ msisdn=n; pin=p; saldo=s; }
    }
    static class Paket {
        int kode, harga; String nama, masa;
        Paket(int k, String n, int h, String m){ kode=k; nama=n; harga=h; masa=m; }
    }

    static final Map<String, Akun> DB = new HashMap<>();
    static final Map<Integer, Paket> KATALOG = new LinkedHashMap<>();
    static final List<String> LOG = new ArrayList<>();
    static final NumberFormat RUPIAH = NumberFormat.getInstance(Locale.of("id", "ID"));


    //ini Untuk Memvalidasi nomor dan code *858# nya
    public static void main(String[] a){
        seed(); 
        Scanner sc = new Scanner(System.in);

        String msisdn = ask(sc,"Masukkan nomor Anda: ");
        if (!DB.containsKey(msisdn) || !DB.get(msisdn).aktif){ System.out.println("Nomor tidak aktif/terdaftar."); return; }

        if (!"*858#".equals(ask(sc,"Ketik *858# untuk mulai: "))) { System.out.println("Dial salah."); return; }

        while (true){
            switch (menu(sc)) {
                case 1 -> cekPulsa(sc, msisdn);
                case 2 -> transfer(sc, msisdn);
                case 3 -> beliPaket(sc, msisdn);
                case 0 -> { System.out.println("Sesi ditutup. Terima kasih."); return; }
                default -> System.out.println("Pilihan tidak dikenal.");
            }
        }
    }

    // Menu Utama
    static int menu(Scanner sc){
        System.out.println("\n--- *858# MENU UTAMA ---");
        System.out.println("1. Cek Pulsa\n2. Transfer Pulsa\n3. Beli Paket\n0. Keluar");
        return askInt(sc,"Pilih: ");
    }

    // Cek Pulsa
    static void cekPulsa(Scanner sc, String msisdn){
        Akun a = DB.get(msisdn);
        System.out.println("\n--- CEK PULSA ---");
        System.out.println("Nomor      : " + a.msisdn);
        System.out.println("Sisa Saldo : Rp " + RUPIAH.format(a.saldo));
        System.out.println("Paket aktif: " + (a.paket.isEmpty()? "-" : String.join(", ", a.paket)));
        back(sc);
    }

    // Transfer Pulsa
    static void transfer(Scanner sc, String from){
        final int FEE = 1_000, SALDO_MIN = 5_000, MIN = 1_000, MAX = 1_000_000;

        while (true){
            System.out.println("\n--- TRANSFER PULSA ---\n0. Kembali");
            String to = ask(sc,"Nomor tujuan: "); if (to.equals("0")) return;
            int nom = parseMoney(ask(sc,"Nominal (Rp): ")); if (nom == -1) { System.out.println("Nominal tidak valid."); continue; }
            String pin = ask(sc,"PIN: "); if (pin.equals("0")) return;

            if (!DB.containsKey(to) || !DB.get(to).aktif){ System.out.println("E01: Nomor tujuan tidak aktif/terdaftar."); continue; }
            if (from.equals(to))                      { System.out.println("E01: Tidak boleh ke nomor sendiri.");     continue; }
            if (nom < MIN || nom > MAX || nom % 1000 != 0){
                System.out.println("E03: Nominal 1.000–1.000.000 & kelipatan 1.000."); continue;
            }

            Akun A = DB.get(from), B = DB.get(to);
            if (!A.pin.equals(pin)) { System.out.println("E04: PIN salah."); continue; }
            int total = nom + FEE;
            if (A.saldo - total < SALDO_MIN) { 
                System.out.println("E02: Saldo kurang (wajib sisa Rp " + RUPIAH.format(SALDO_MIN) + ")."); 
                continue; 
            }

            A.saldo -= total; B.saldo += nom;
            String tx = UUID.randomUUID().toString().substring(0,8).toUpperCase();
            LOG.add(String.format("TX:%s FROM:%s TO:%s NOM:%d FEE:%d SISA:%d", tx, from, to, nom, FEE, A.saldo));

            System.out.println("\nBerhasil transfer Rp " + RUPIAH.format(nom) + " ke " + to + 
                " (Fee Rp " + RUPIAH.format(FEE) + ").");
            System.out.println("Sisa saldo: Rp " + RUPIAH.format(A.saldo));
            System.out.println("ID Transaksi: " + tx);
            back(sc);
            return;
        }
    }

    // Beli Paket
    static void beliPaket(Scanner sc, String msisdn){
        Akun a = DB.get(msisdn);
        while (true){
            System.out.println("\n--- BELI PAKET ---");
            KATALOG.values().forEach(p -> System.out.printf("%d. %s - Rp %s (%s)%n",
                    p.kode, p.nama, RUPIAH.format(p.harga), p.masa));
            System.out.println("0. Kembali");
            int k = askInt(sc,"Pilih paket: "); if (k==0) return;

            Paket p = KATALOG.get(k);
            if (p == null){ System.out.println("Kode paket tidak ada."); continue; }
            if (a.saldo < p.harga){ System.out.println("Saldo kurang. Saldo: Rp " + RUPIAH.format(a.saldo)); continue; }

            int y = askInt(sc, "Konfirmasi beli " + p.nama + " (Rp " + RUPIAH.format(p.harga) + ")? 1=Ya/0=Back: ");
            if (y != 1) continue;

            a.saldo -= p.harga; a.paket.add(p.nama + " (" + p.masa + ")");
            String tx = UUID.randomUUID().toString().substring(0,8).toUpperCase();
            LOG.add(String.format("TX:%s BUY:%s MSISDN:%s HARGA:%d SISA:%d", tx, p.nama, msisdn, p.harga, a.saldo));

            System.out.println("\nPembelian berhasil!");
            System.out.println("Paket aktif: " + p.nama + " (" + p.masa + ")");
            System.out.println("Sisa saldo : Rp " + RUPIAH.format(a.saldo));
            System.out.println("ID Transaksi: " + tx);
            back(sc);
            return;
        }
    }

    // Code Tambahan
    static String ask(Scanner sc, String p){ System.out.print(p); return sc.nextLine().trim(); }
    static int askInt(Scanner sc, String p){
        while (true){ try { return Integer.parseInt(ask(sc,p)); } catch(Exception e){ System.out.println("Input angka tidak valid."); } }
    }
    static int parseMoney(String s){ try { return Integer.parseInt(s.replace(".","").replace(",","")); } catch(Exception e){ return -1; } }
    static void back(Scanner sc){ System.out.println("\n0. Kembali"); while (askInt(sc,"Pilih: ") != 0) System.out.println("Tekan 0 untuk kembali."); }

    static void seed(){
        DB.put("081234567890", new Akun("081234567890","1234",120_000));
        DB.put("089876543210", new Akun("089876543210","0000", 25_000));
        DB.put("081111111111", new Akun("081111111111","9999",  7_500));

        KATALOG.put(1,new Paket(1,"Paket Data 5GB",      25_000,"7 hari"));
        KATALOG.put(2,new Paket(2,"Paket Data 12GB",     50_000,"30 hari"));
        KATALOG.put(3,new Paket(3,"Paket Nelpon 200mnt", 15_000,"7 hari"));
        KATALOG.put(4,new Paket(4,"Combo 10GB+200mnt",   60_000,"30 hari"));
    }
}
