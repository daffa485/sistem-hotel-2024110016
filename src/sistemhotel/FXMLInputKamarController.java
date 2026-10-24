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
public class FXMLInputKamarController implements Initializable {

    @FXML
    private TextField txtnomor;
    @FXML
    private ComboBox<String> cbTipe;
    @FXML
    private TextField txtlantai;
    @FXML
    private ComboBox<String> cbStatus;
    @FXML
    private Button btnSimpan;
    @FXML
    private Button btnBatal;

    private boolean editdata = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbStatus.setItems(FXCollections.observableArrayList("AVAILABLE", "OCCUPIED", "CLEANING", "MAINTENANCE"));
        cbStatus.getSelectionModel().selectFirst();
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
            Logger.getLogger(FXMLInputKamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void execute(KamarModel d) {
        if (d != null && d.getNomor_kamar() != null && !d.getNomor_kamar().isEmpty()) {
            editdata = true;
            txtnomor.setText(d.getNomor_kamar());
            cbTipe.setValue(d.getId_tipe());
            txtlantai.setText(String.valueOf(d.getLantai()));
            cbStatus.setValue(d.getStatus());
            txtnomor.setEditable(false);
            cbTipe.requestFocus();
        }
    }

    @FXML
    private void btnSimpanKlik(ActionEvent event) {
        if (txtnomor.getText().trim().isEmpty() || cbTipe.getValue() == null) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Nomor kamar dan Tipe kamar wajib dipilih!", ButtonType.OK);
            a.showAndWait();
            return;
        }

        int lantai = 1;
        try {
            lantai = Integer.parseInt(txtlantai.getText().trim());
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Lantai harus berupa angka bulat!", ButtonType.OK);
            a.showAndWait();
            txtlantai.requestFocus();
            return;
        }

        KamarModel n = new KamarModel();
        n.setNomor_kamar(txtnomor.getText().trim());
        n.setId_tipe(cbTipe.getValue());
        n.setLantai(lantai);
        n.setStatus(cbStatus.getValue() != null ? cbStatus.getValue() : "AVAILABLE");

        FXMLDocumentController.dbkamar.setKamarModel(n);

        if (editdata) {
            if (FXMLDocumentController.dbkamar.update()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data kamar berhasil diubah", ButtonType.OK);
                a.showAndWait();
                btnBatalKlik(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data kamar gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } else if (FXMLDocumentController.dbkamar.validasi(n.getNomor_kamar()) <= 0) {
            if (FXMLDocumentController.dbkamar.insert()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data kamar berhasil disimpan", ButtonType.OK);
                a.showAndWait();
                btnBatalKlik(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data kamar gagal disimpan", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Nomor Kamar sudah ada!", ButtonType.OK);
            a.showAndWait();
            txtnomor.requestFocus();
        }
    }

    @FXML
    private void btnBatalKlik(ActionEvent event) {
        btnBatal.getScene().getWindow().hide();
    }
}
