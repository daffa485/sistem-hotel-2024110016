/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemhotel;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author daffa
 */
public class DBTipeKamar {
    private TipeKamarModel dt = new TipeKamarModel();

    public TipeKamarModel getTipeKamarModel() {
        return dt;
    }

    public void setTipeKamarModel(TipeKamarModel s) {
        dt = s;
    }

    public ObservableList<TipeKamarModel> Load() throws SQLException {
        try {
            ObservableList<TipeKamarModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select id_tipe, nama_tipe, kapasitas, deskripsi_fasilitas, gambar from tipe_kamar");

            while (rs.next()) {
                TipeKamarModel d = new TipeKamarModel();
                d.setId_tipe(rs.getString("id_tipe"));
                d.setNama_tipe(rs.getString("nama_tipe"));
                d.setKapasitas(rs.getInt("kapasitas"));
                d.setDeskripsi_fasilitas(rs.getString("deskripsi_fasilitas"));
                d.setGambar(rs.getString("gambar"));
                tableData.add(d);
            }
            con.tutupKoneksi();
            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<TipeKamarModel> CariTipeKamar(String keyword) throws SQLException {
        try {
            ObservableList<TipeKamarModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(
                    "select id_tipe, nama_tipe, kapasitas, deskripsi_fasilitas, gambar from tipe_kamar where id_tipe like ? or nama_tipe like ?");
            con.preparedStatement.setString(1, "%" + keyword + "%");
            con.preparedStatement.setString(2, "%" + keyword + "%");
            ResultSet rs = con.preparedStatement.executeQuery();

            while (rs.next()) {
                TipeKamarModel d = new TipeKamarModel();
                d.setId_tipe(rs.getString("id_tipe"));
                d.setNama_tipe(rs.getString("nama_tipe"));
                d.setKapasitas(rs.getInt("kapasitas"));
                d.setDeskripsi_fasilitas(rs.getString("deskripsi_fasilitas"));
                d.setGambar(rs.getString("gambar"));
                tableData.add(d);
            }
            con.tutupKoneksi();
            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int validasi(String nomor) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from tipe_kamar where id_tipe = '" + nomor + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public boolean delete(String nomor) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from tipe_kamar where id_tipe = ?");
            con.preparedStatement.setString(1, nomor);
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean insert() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(
                    "insert into tipe_kamar (id_tipe, nama_tipe, kapasitas, deskripsi_fasilitas, gambar) values (?,?,?,?,?)");
            con.preparedStatement.setString(1, getTipeKamarModel().getId_tipe());
            con.preparedStatement.setString(2, getTipeKamarModel().getNama_tipe());
            con.preparedStatement.setInt(3, getTipeKamarModel().getKapasitas());
            con.preparedStatement.setString(4, getTipeKamarModel().getDeskripsi_fasilitas());
            con.preparedStatement.setString(5, getTipeKamarModel().getGambar());
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean update() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(
                    "update tipe_kamar set nama_tipe = ?, kapasitas = ?, deskripsi_fasilitas = ?, gambar = ? where id_tipe = ?");
            con.preparedStatement.setString(1, getTipeKamarModel().getNama_tipe());
            con.preparedStatement.setInt(2, getTipeKamarModel().getKapasitas());
            con.preparedStatement.setString(3, getTipeKamarModel().getDeskripsi_fasilitas());
            con.preparedStatement.setString(4, getTipeKamarModel().getGambar());
            con.preparedStatement.setString(5, getTipeKamarModel().getId_tipe());
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }
}
