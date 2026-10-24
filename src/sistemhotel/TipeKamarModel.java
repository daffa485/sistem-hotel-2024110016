/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemhotel;

/**
 *
 * @author daffa
 */
public class TipeKamarModel {
    private String id_tipe, nama_tipe, deskripsi_fasilitas, gambar;
    private int kapasitas;

    public TipeKamarModel() {
    }

    public TipeKamarModel(String id_tipe, String nama_tipe) {
        this.id_tipe = id_tipe;
        this.nama_tipe = nama_tipe;
    }

    public TipeKamarModel(String id_tipe, String nama_tipe, int kapasitas, String deskripsi_fasilitas, String gambar) {
        this.id_tipe = id_tipe;
        this.nama_tipe = nama_tipe;
        this.kapasitas = kapasitas;
        this.deskripsi_fasilitas = deskripsi_fasilitas;
        this.gambar = gambar;
    }

    public String getId_tipe() {
        return id_tipe;
    }

    public void setId_tipe(String id_tipe) {
        this.id_tipe = id_tipe;
    }

    public String getNama_tipe() {
        return nama_tipe;
    }

    public void setNama_tipe(String nama_tipe) {
        this.nama_tipe = nama_tipe;
    }

    public String getDeskripsi_fasilitas() {
        return deskripsi_fasilitas;
    }

    public void setDeskripsi_fasilitas(String deskripsi_fasilitas) {
        this.deskripsi_fasilitas = deskripsi_fasilitas;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    @Override
    public String toString() {
        return id_tipe + " - " + nama_tipe;
    }
}
