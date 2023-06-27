package id.kharisma.studio.atelier;

public class User {
    public String nama, noHp;

    public User(){

    }
    public User(String nama, String noHp){
        this.nama = nama;
        this.noHp = noHp;
    }

    public String getNama() {
        return nama;
    }

    public String getNoHp() {
        return noHp;
    }
}
