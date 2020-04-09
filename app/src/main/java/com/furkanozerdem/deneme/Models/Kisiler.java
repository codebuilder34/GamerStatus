package com.furkanozerdem.deneme.Models;

public class Kisiler {
    private String kisi_key;
    private String isim;
    private  boolean katilim;

    public Kisiler(String kisi_key, String isim, boolean katilim) {
        this.kisi_key = kisi_key;
        this.isim = isim;
        this.katilim = katilim;
    }

    public Kisiler() {
    }

    public String getKisi_key() {
        return kisi_key;
    }

    public void setKisi_key(String kisi_key) {
        this.kisi_key = kisi_key;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public boolean isKatilim() {
        return katilim;
    }

    public void setKatilim(boolean katilim) {
        this.katilim = katilim;
    }
}
