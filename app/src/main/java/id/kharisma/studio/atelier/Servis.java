package id.kharisma.studio.atelier;

public class Servis {
        private String nama, harga;

        public Servis(String nama, String harga) {
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
            return harga;
        }

        public void setHarga(String harga) {
            this.harga = harga;
        }

}
