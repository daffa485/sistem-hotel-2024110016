/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemhotel;

/**
 *
 * @author daffa
 */
public class KamarModel {
    private String nomor_kamar, id_tipe, status;
    private int lantai;

    public KamarModel() {
    }

    public KamarModel(String nomor_kamar, String id_tipe, int lantai, String status) {
        this.nomor_kamar = nomor_kamar;
        this.id_tipe = id_tipe;
        this.lantai = lantai;
        this.status = status;
    }

    public String getNomor_kamar() {
        return nomor_kamar;
    }

    public void setNomor_kamar(String nomor_kamar) {
        this.nomor_kamar = nomor_kamar;
    }

    public String getId_tipe() {
        return id_tipe;
    }

    public void setId_tipe(String id_tipe) {
        this.id_tipe = id_tipe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLantai() {
        return lantai;
    }

    public void setLantai(int lantai) {
        this.lantai = lantai;
    }

    @Override
    public String toString() {
        return nomor_kamar;
    }
}
