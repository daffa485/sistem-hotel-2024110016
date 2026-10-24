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
public class DBCustomer {

    private CustomerModel dt = new CustomerModel();

    public CustomerModel getCustomerModel() {
        return dt;
    }

    public void setCustomerModel(CustomerModel s) {
        dt = s;
    }

    public ObservableList<CustomerModel> Load() throws SQLException {
        try {
            ObservableList<CustomerModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery(
                    "select id_customer, nomor_identitas, nama_lengkap, no_telepon, email, alamat from customer");

            while (rs.next()) {
                CustomerModel d = new CustomerModel();
                d.setId_customer(rs.getString("id_customer"));
                d.setNomor_identitas(rs.getString("nomor_identitas"));
                d.setNama_lengkap(rs.getString("nama_lengkap"));
                d.setNo_telepon(rs.getString("no_telepon"));
                d.setEmail(rs.getString("email"));
                d.setAlamat(rs.getString("alamat"));
                tableData.add(d);
            }
            con.tutupKoneksi();
            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<CustomerModel> CariCustomer(String keyword) throws SQLException {
        try {
            ObservableList<CustomerModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(
                    "select id_customer, nomor_identitas, nama_lengkap, no_telepon, email, alamat from customer where id_customer like ? or nama_lengkap like ? or nomor_identitas like ?");
            con.preparedStatement.setString(1, "%" + keyword + "%");
            con.preparedStatement.setString(2, "%" + keyword + "%");
            con.preparedStatement.setString(3, "%" + keyword + "%");
            ResultSet rs = con.preparedStatement.executeQuery();

            while (rs.next()) {
                CustomerModel d = new CustomerModel();
                d.setId_customer(rs.getString("id_customer"));
                d.setNomor_identitas(rs.getString("nomor_identitas"));
                d.setNama_lengkap(rs.getString("nama_lengkap"));
                d.setNo_telepon(rs.getString("no_telepon"));
                d.setEmail(rs.getString("email"));
                d.setAlamat(rs.getString("alamat"));
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
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from customer where id_customer = '" + nomor + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public int validasiNIK(String nik) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from customer where nomor_identitas = '" + nik + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public int validasiNIK(String nik, String idCustomer) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from customer where nomor_identitas = '" + nik + "' and id_customer <> '" + idCustomer + "'");
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
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from customer where id_customer = ?");
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
                    "insert into customer (id_customer, nomor_identitas, nama_lengkap, no_telepon, email, alamat) values (?,?,?,?,?,?)");
            con.preparedStatement.setString(1, getCustomerModel().getId_customer());
            con.preparedStatement.setString(2, getCustomerModel().getNomor_identitas());
            con.preparedStatement.setString(3, getCustomerModel().getNama_lengkap());
            con.preparedStatement.setString(4, getCustomerModel().getNo_telepon());
            con.preparedStatement.setString(5, getCustomerModel().getEmail());
            con.preparedStatement.setString(6, getCustomerModel().getAlamat());
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
                    "update customer set nomor_identitas = ?, nama_lengkap = ?, no_telepon = ?, email = ?, alamat = ? where id_customer = ?");
            con.preparedStatement.setString(1, getCustomerModel().getNomor_identitas());
            con.preparedStatement.setString(2, getCustomerModel().getNama_lengkap());
            con.preparedStatement.setString(3, getCustomerModel().getNo_telepon());
            con.preparedStatement.setString(4, getCustomerModel().getEmail());
            con.preparedStatement.setString(5, getCustomerModel().getAlamat());
            con.preparedStatement.setString(6, getCustomerModel().getId_customer());
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
