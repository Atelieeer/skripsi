package id.kharisma.studio.atelier;

public class Servis {
        private String nama;
        long harga;
        public Servis(){}
        public Servis(String nama, long harga) {
            this.nama = nama;
            this.harga = harga;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getNama() {
            return nama;
        }

        public String getHarga() {
            return ("Estimasi Harga Rp. "+String.valueOf(harga));
        }

        public void setHarga(long harga) {
            this.harga = harga;
        }
}
