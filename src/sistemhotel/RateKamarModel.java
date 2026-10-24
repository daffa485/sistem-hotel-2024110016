/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemhotel;

/**
 *
 * @author daffa
 */
public class RateKamarModel {
    private String id_rate, id_tipe, nama_rate, keterangan;
    private double harga_per_malam;

    public RateKamarModel() {
    }

    public RateKamarModel(String id_rate, String id_tipe, String nama_rate, double harga_per_malam, String keterangan) {
        this.id_rate = id_rate;
        this.id_tipe = id_tipe;
        this.nama_rate = nama_rate;
        this.harga_per_malam = harga_per_malam;
        this.keterangan = keterangan;
    }

    public String getId_rate() {
        return id_rate;
    }

    public void setId_rate(String id_rate) {
        this.id_rate = id_rate;
    }

    public String getId_tipe() {
        return id_tipe;
    }

    public void setId_tipe(String id_tipe) {
        this.id_tipe = id_tipe;
    }

    public String getNama_rate() {
        return nama_rate;
    }

    public void setNama_rate(String nama_rate) {
        this.nama_rate = nama_rate;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public double getHarga_per_malam() {
        return harga_per_malam;
    }

    public void setHarga_per_malam(double harga_per_malam) {
        this.harga_per_malam = harga_per_malam;
    }

    @Override
    public String toString() {
        return id_rate + " - " + nama_rate;
    }
}
