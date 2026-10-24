/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemhotel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author daffa
 */
public class FXMLInputRateKamarController implements Initializable {

    @FXML
    private TextField txtidrate;
    @FXML
    private ComboBox<String> cbTipe;
    @FXML
    private TextField txtnama;
    @FXML
    private TextField txtharga;
    @FXML
    private TextField txtketerangan;
    @FXML
    private Button btnSimpan;
    @FXML
    private Button btnBatal;

    private boolean editdata = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTipeKamar();
    }

    private void loadTipeKamar() {
        try {
            ObservableList<TipeKamarModel> list = FXMLDocumentController.dbtipekamar.Load();
            ObservableList<String> listIdTipe = FXCollections.observableArrayList();
            if (list != null) {
                for (TipeKamarModel t : list) {
                    listIdTipe.add(t.getId_tipe());
                }
            }
            cbTipe.setItems(listIdTipe);
            if (!listIdTipe.isEmpty()) {
                cbTipe.getSelectionModel().selectFirst();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLInputRateKamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void execute(RateKamarModel d) {
        if (d != null && d.getId_rate() != null && !d.getId_rate().isEmpty()) {
            editdata = true;
            txtidrate.setText(d.getId_rate());
            cbTipe.setValue(d.getId_tipe());
            txtnama.setText(d.getNama_rate());
            txtharga.setText(String.valueOf(d.getHarga_per_malam()));
            txtketerangan.setText(d.getKeterangan());
            txtidrate.setEditable(false);
            cbTipe.requestFocus();
        }
    }

    @FXML
    private void btnSimpanKlik(ActionEvent event) {
        if (txtidrate.getText().trim().isEmpty() || cbTipe.getValue() == null || txtnama.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING, "ID Rate, Tipe Kamar, dan Nama Rate wajib diisi!", ButtonType.OK);
            a.showAndWait();
            return;
        }

        double harga = 0;
        try {
            harga = Double.parseDouble(txtharga.getText().trim());
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Harga per malam harus berupa angka desimal/nominal valid!", ButtonType.OK);
            a.showAndWait();
            txtharga.requestFocus();
            return;
        }

        RateKamarModel n = new RateKamarModel();
        n.setId_rate(txtidrate.getText().trim());
        n.setId_tipe(cbTipe.getValue());
        n.setNama_rate(txtnama.getText().trim());
        n.setHarga_per_malam(harga);
        n.setKeterangan(txtketerangan.getText().trim());

        FXMLDocumentController.dbratekamar.setRateKamarModel(n);

        if (editdata) {
            if (FXMLDocumentController.dbratekamar.update()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data rate kamar berhasil diubah", ButtonType.OK);
                a.showAndWait();
                btnBatalKlik(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data rate kamar gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } else if (FXMLDocumentController.dbratekamar.validasi(n.getId_rate()) <= 0) {
            if (FXMLDocumentController.dbratekamar.insert()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data rate kamar berhasil disimpan", ButtonType.OK);
                a.showAndWait();
                btnBatalKlik(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data rate kamar gagal disimpan", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "ID Rate sudah ada!", ButtonType.OK);
            a.showAndWait();
            txtidrate.requestFocus();
        }
    }

    @FXML
    private void btnBatalKlik(ActionEvent event) {
        btnBatal.getScene().getWindow().hide();
    }
}
