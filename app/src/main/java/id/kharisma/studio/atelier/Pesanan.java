package id.kharisma.studio.atelier;

public class Pesanan {
    private String pesanan, harga;

    public Pesanan(){

    }
    public Pesanan(String pesanan, String harga) {
        this.pesanan = pesanan;
        this.harga = harga;
    }

    public String getPesanan() {
        return pesanan;
    }

    public String getHarga() {
        return harga;
    }
}
