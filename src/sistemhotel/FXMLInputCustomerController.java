/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemhotel;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author daffa
 */
public class FXMLInputCustomerController implements Initializable {

    @FXML
    private TextField txtid;
    @FXML
    private TextField txtidentitas;
    @FXML
    private TextField txtnama;
    @FXML
    private TextField txttelepon;
    @FXML
    private TextField txtemail;
    @FXML
    private TextArea txtalamat;
    @FXML
    private Button btnSimpan;
    @FXML
    private Button btnBatal;

    private boolean editdata = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inisialisasi
    }

    public void execute(CustomerModel d) {
        if (d != null && d.getId_customer() != null && !d.getId_customer().isEmpty()) {
            editdata = true;
            txtid.setText(d.getId_customer());
            txtidentitas.setText(d.getNomor_identitas());
            txtnama.setText(d.getNama_lengkap());
            txttelepon.setText(d.getNo_telepon());
            txtemail.setText(d.getEmail());
            txtalamat.setText(d.getAlamat());
            txtid.setEditable(false);
            txtidentitas.requestFocus();
        }
    }

    @FXML
    private void btnSimpanKlik(ActionEvent event) {
        if (txtid.getText().trim().isEmpty() || txtidentitas.getText().trim().isEmpty() || txtnama.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING, "ID Customer, Nomor Identitas, dan Nama tidak boleh kosong!", ButtonType.OK);
            a.showAndWait();
            return;
        }

        CustomerModel n = new CustomerModel();
        n.setId_customer(txtid.getText().trim());
        n.setNomor_identitas(txtidentitas.getText().trim());
        n.setNama_lengkap(txtnama.getText().trim());
        n.setNo_telepon(txttelepon.getText().trim());
        n.setEmail(txtemail.getText().trim());
        n.setAlamat(txtalamat.getText().trim());

        FXMLDocumentController.dbcustomer.setCustomerModel(n);

        if (editdata) {
            if (FXMLDocumentController.dbcustomer.validasiNIK(n.getNomor_identitas(), n.getId_customer()) > 0) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Nomor Identitas (NIK) sudah digunakan oleh customer lain!", ButtonType.OK);
                a.showAndWait();
                txtidentitas.requestFocus();
                return;
            }
            if (FXMLDocumentController.dbcustomer.update()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil diubah", ButtonType.OK);
                a.showAndWait();
                btnBatalKlik(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } else if (FXMLDocumentController.dbcustomer.validasi(n.getId_customer()) <= 0) {
            if (FXMLDocumentController.dbcustomer.validasiNIK(n.getNomor_identitas()) > 0) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Nomor Identitas (NIK) sudah terdaftar!", ButtonType.OK);
                a.showAndWait();
                txtidentitas.requestFocus();
                return;
            }
            if (FXMLDocumentController.dbcustomer.insert()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
                a.showAndWait();
                btnBatalKlik(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "ID Customer sudah terdaftar!", ButtonType.OK);
            a.showAndWait();
            txtid.requestFocus();
        }
    }

    @FXML
    private void btnBatalKlik(ActionEvent event) {
        btnBatal.getScene().getWindow().hide();
    }
}
