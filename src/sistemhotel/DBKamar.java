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
public class DBKamar {

    private KamarModel dt = new KamarModel();

    public KamarModel getKamarModel() {
        return dt;
    }

    public void setKamarModel(KamarModel s) {
        dt = s;
    }

    public ObservableList<KamarModel> Load() throws SQLException {
        try {
            ObservableList<KamarModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery(
                    "select nomor_kamar, id_tipe, lantai, status from kamar order by lantai, nomor_kamar");

            while (rs.next()) {
                KamarModel d = new KamarModel();
                d.setNomor_kamar(rs.getString("nomor_kamar"));
                d.setId_tipe(rs.getString("id_tipe"));
                d.setLantai(rs.getInt("lantai"));
                d.setStatus(rs.getString("status"));
                tableData.add(d);
            }
            con.tutupKoneksi();
            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<KamarModel> CariKamar(String keyword) throws SQLException {
        try {
            ObservableList<KamarModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(
                    "select nomor_kamar, id_tipe, lantai, status from kamar where nomor_kamar like ? or id_tipe like ? or status like ?");
            con.preparedStatement.setString(1, "%" + keyword + "%");
            con.preparedStatement.setString(2, "%" + keyword + "%");
            con.preparedStatement.setString(3, "%" + keyword + "%");
            ResultSet rs = con.preparedStatement.executeQuery();

            while (rs.next()) {
                KamarModel d = new KamarModel();
                d.setNomor_kamar(rs.getString("nomor_kamar"));
                d.setId_tipe(rs.getString("id_tipe"));
                d.setLantai(rs.getInt("lantai"));
                d.setStatus(rs.getString("status"));
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
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from kamar where nomor_kamar = '" + nomor + "'");
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
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from kamar where nomor_kamar = ?");
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
                    "insert into kamar (nomor_kamar, id_tipe, lantai, status) values (?,?,?,?)");
            con.preparedStatement.setString(1, getKamarModel().getNomor_kamar());
            con.preparedStatement.setString(2, getKamarModel().getId_tipe());
            con.preparedStatement.setInt(3, getKamarModel().getLantai());
            con.preparedStatement.setString(4, getKamarModel().getStatus());
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
                    "update kamar set id_tipe = ?, lantai = ?, status = ? where nomor_kamar = ?");
            con.preparedStatement.setString(1, getKamarModel().getId_tipe());
            con.preparedStatement.setInt(2, getKamarModel().getLantai());
            con.preparedStatement.setString(3, getKamarModel().getStatus());
            con.preparedStatement.setString(4, getKamarModel().getNomor_kamar());
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
