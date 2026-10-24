/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemhotel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author daffa
 */
public class FXMLRateKamarController implements Initializable {

    @FXML
    private TextField txtCari;
    @FXML
    private Button btnCari;
    @FXML
    private Button btnTambah;
    @FXML
    private Button btnUbah;
    @FXML
    private Button btnHapus;
    @FXML
    private TableView<RateKamarModel> tbvratekamar;
    @FXML
    private TableColumn<RateKamarModel, String> colIdRate;
    @FXML
    private TableColumn<RateKamarModel, String> colIdTipe;
    @FXML
    private TableColumn<RateKamarModel, String> colNamaRate;
    @FXML
    private TableColumn<RateKamarModel, Double> colHarga;
    @FXML
    private TableColumn<RateKamarModel, String> colKeterangan;
    @FXML
    private Button btnFirst;
    @FXML
    private Button btnPrev;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnLast;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            colIdRate.setCellValueFactory(new PropertyValueFactory<>("id_rate"));
            colIdTipe.setCellValueFactory(new PropertyValueFactory<>("id_tipe"));
            colNamaRate.setCellValueFactory(new PropertyValueFactory<>("nama_rate"));
            colHarga.setCellValueFactory(new PropertyValueFactory<>("harga_per_malam"));
            colKeterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));

            showdata();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLRateKamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showdata() throws SQLException {
        ObservableList<RateKamarModel> data = FXMLDocumentController.dbratekamar.Load();
        if (data != null) {
            tbvratekamar.setItems(data);
            if (!data.isEmpty()) {
                tbvratekamar.getSelectionModel().selectFirst();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void txtCariKeyTyped(KeyEvent event) {
        cariData();
    }

    @FXML
    private void btnCariKlik(ActionEvent event) {
        cariData();
    }

    private void cariData() {
        String keyword = txtCari.getText().trim();
        try {
            ObservableList<RateKamarModel> hasil;
            if (keyword.isEmpty()) {
                hasil = FXMLDocumentController.dbratekamar.Load();
            } else {
                hasil = FXMLDocumentController.dbratekamar.CariRateKamar(keyword);
            }
            tbvratekamar.setItems(hasil);
            if (hasil != null && !hasil.isEmpty()) {
                tbvratekamar.getSelectionModel().selectFirst();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLRateKamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnTambahKlik(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputRateKamar.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Tambah Rate Kamar");
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showdata();
        btnFirstKlik(event);
    }

    @FXML
    private void btnUbahKlik(ActionEvent event) throws SQLException {
        RateKamarModel s = tbvratekamar.getSelectionModel().getSelectedItem();
        if (s == null) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih data rate kamar yang akan diubah!", ButtonType.OK);
            a.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputRateKamar.fxml"));
            Parent root = (Parent) loader.load();
            FXMLInputRateKamarController isidt = (FXMLInputRateKamarController) loader.getController();
            isidt.execute(s);
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Ubah Rate Kamar");
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showdata();
        btnFirstKlik(event);
    }

    @FXML
    private void btnHapusKlik(ActionEvent event) throws SQLException {
        RateKamarModel s = tbvratekamar.getSelectionModel().getSelectedItem();
        if (s == null) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih data rate kamar yang akan dihapus!", ButtonType.OK);
            a.showAndWait();
            return;
        }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus rate kamar: " + s.getNama_rate() + "?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLDocumentController.dbratekamar.delete(s.getId_rate())) {
                Alert b = new Alert(Alert.AlertType.INFORMATION, "Data berhasil dihapus", ButtonType.OK);
                b.showAndWait();
            } else {
                Alert b = new Alert(Alert.AlertType.ERROR, "Data gagal dihapus", ButtonType.OK);
                b.showAndWait();
            }
            showdata();
            btnFirstKlik(event);
        }
    }

    @FXML
    private void btnFirstKlik(ActionEvent event) {
        tbvratekamar.getSelectionModel().selectFirst();
        tbvratekamar.requestFocus();
    }

    @FXML
    private void btnPrevKlik(ActionEvent event) {
        tbvratekamar.getSelectionModel().selectAboveCell();
        tbvratekamar.requestFocus();
    }

    @FXML
    private void btnNextKlik(ActionEvent event) {
        tbvratekamar.getSelectionModel().selectBelowCell();
        tbvratekamar.requestFocus();
    }

    @FXML
    private void btnLastKlik(ActionEvent event) {
        tbvratekamar.getSelectionModel().selectLast();
        tbvratekamar.requestFocus();
    }
}
