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
public class DBRateKamar {

    private RateKamarModel dt = new RateKamarModel();

    public RateKamarModel getRateKamarModel() {
        return dt;
    }

    public void setRateKamarModel(RateKamarModel s) {
        dt = s;
    }

    public ObservableList<RateKamarModel> Load() throws SQLException {
        try {
            ObservableList<RateKamarModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery(
                    "select id_rate, id_tipe, nama_rate, harga_per_malam, keterangan from rate_kamar");

            while (rs.next()) {
                RateKamarModel d = new RateKamarModel();
                d.setId_rate(rs.getString("id_rate"));
                d.setId_tipe(rs.getString("id_tipe"));
                d.setNama_rate(rs.getString("nama_rate"));
                d.setHarga_per_malam(rs.getDouble("harga_per_malam"));
                d.setKeterangan(rs.getString("keterangan"));
                tableData.add(d);
            }
            con.tutupKoneksi();
            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<RateKamarModel> CariRateKamar(String keyword) throws SQLException {
        try {
            ObservableList<RateKamarModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(
                    "select id_rate, id_tipe, nama_rate, harga_per_malam, keterangan from rate_kamar where id_rate like ? or id_tipe like ? or nama_rate like ?");
            con.preparedStatement.setString(1, "%" + keyword + "%");
            con.preparedStatement.setString(2, "%" + keyword + "%");
            con.preparedStatement.setString(3, "%" + keyword + "%");
            ResultSet rs = con.preparedStatement.executeQuery();

            while (rs.next()) {
                RateKamarModel d = new RateKamarModel();
                d.setId_rate(rs.getString("id_rate"));
                d.setId_tipe(rs.getString("id_tipe"));
                d.setNama_rate(rs.getString("nama_rate"));
                d.setHarga_per_malam(rs.getDouble("harga_per_malam"));
                d.setKeterangan(rs.getString("keterangan"));
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
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from rate_kamar where id_rate = '" + nomor + "'");
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
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from rate_kamar where id_rate = ?");
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
                    "insert into rate_kamar (id_rate, id_tipe, nama_rate, harga_per_malam, keterangan) values (?,?,?,?,?)");
            con.preparedStatement.setString(1, getRateKamarModel().getId_rate());
            con.preparedStatement.setString(2, getRateKamarModel().getId_tipe());
            con.preparedStatement.setString(3, getRateKamarModel().getNama_rate());
            con.preparedStatement.setDouble(4, getRateKamarModel().getHarga_per_malam());
            con.preparedStatement.setString(5, getRateKamarModel().getKeterangan());
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
                    "update rate_kamar set id_tipe = ?, nama_rate = ?, harga_per_malam = ?, keterangan = ? where id_rate = ?");
            con.preparedStatement.setString(1, getRateKamarModel().getId_tipe());
            con.preparedStatement.setString(2, getRateKamarModel().getNama_rate());
            con.preparedStatement.setDouble(3, getRateKamarModel().getHarga_per_malam());
            con.preparedStatement.setString(4, getRateKamarModel().getKeterangan());
            con.preparedStatement.setString(5, getRateKamarModel().getId_rate());
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
