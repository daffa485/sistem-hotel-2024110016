/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemhotel;

/**
 *
 * @author daffa
 */
public class CustomerModel {
    private String id_customer, nomor_identitas, nama_lengkap, no_telepon, email, alamat;

    public CustomerModel() {
    }

    public CustomerModel(String id_customer, String nomor_identitas, String nama_lengkap, String no_telepon, String email, String alamat) {
        this.id_customer = id_customer;
        this.nomor_identitas = nomor_identitas;
        this.nama_lengkap = nama_lengkap;
        this.no_telepon = no_telepon;
        this.email = email;
        this.alamat = alamat;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getNomor_identitas() {
        return nomor_identitas;
    }

    public void setNomor_identitas(String nomor_identitas) {
        this.nomor_identitas = nomor_identitas;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public String toString() {
        return id_customer + " - " + nama_lengkap;
    }
}
