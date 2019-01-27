package id.restabayu.clientsatu;

public class Classified {
    private String eventId;
    private String kategori;
    private String tempat;
    private String deskripsi;
    private String tanggal;
    private String waktu;
    private String tglnotif;
    private String wktnotif;
    private String nama;
    private String interval;

    public Classified(String eventId, String kategori, String tempat, String deskripsi, String tanggal, String waktu, String tglnotif, String wktnotif, String nama) {
        this.eventId = eventId;
        this.kategori = kategori;
        this.tempat = tempat;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.tglnotif = tglnotif;
        this.wktnotif = wktnotif;
        this.nama = nama;
    }

    public Classified() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getTglnotif() {
        return tglnotif;
    }

    public void setTglnotif(String tglnotif) {
        this.tglnotif = tglnotif;
    }

    public String getWktnotif() {
        return wktnotif;
    }

    public void setWktnotif(String wktnotif) {
        this.wktnotif = wktnotif;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}
