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
public class FXMLKamarController implements Initializable {

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
    private TableView<KamarModel> tbvkamar;
    @FXML
    private TableColumn<KamarModel, String> colNomorKamar;
    @FXML
    private TableColumn<KamarModel, String> colIdTipe;
    @FXML
    private TableColumn<KamarModel, Integer> colLantai;
    @FXML
    private TableColumn<KamarModel, String> colStatus;
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
            colNomorKamar.setCellValueFactory(new PropertyValueFactory<>("nomor_kamar"));
            colIdTipe.setCellValueFactory(new PropertyValueFactory<>("id_tipe"));
            colLantai.setCellValueFactory(new PropertyValueFactory<>("lantai"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            showdata();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLKamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showdata() throws SQLException {
        ObservableList<KamarModel> data = FXMLDocumentController.dbkamar.Load();
        if (data != null) {
            tbvkamar.setItems(data);
            if (!data.isEmpty()) {
                tbvkamar.getSelectionModel().selectFirst();
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
            ObservableList<KamarModel> hasil;
            if (keyword.isEmpty()) {
                hasil = FXMLDocumentController.dbkamar.Load();
            } else {
                hasil = FXMLDocumentController.dbkamar.CariKamar(keyword);
            }
            tbvkamar.setItems(hasil);
            if (hasil != null && !hasil.isEmpty()) {
                tbvkamar.getSelectionModel().selectFirst();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLKamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnTambahKlik(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputKamar.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Tambah Data Kamar");
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
        KamarModel s = tbvkamar.getSelectionModel().getSelectedItem();
        if (s == null) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih data kamar yang akan diubah!", ButtonType.OK);
            a.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputKamar.fxml"));
            Parent root = (Parent) loader.load();
            FXMLInputKamarController isidt = (FXMLInputKamarController) loader.getController();
            isidt.execute(s);
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Ubah Data Kamar");
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
        KamarModel s = tbvkamar.getSelectionModel().getSelectedItem();
        if (s == null) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih data kamar yang akan dihapus!", ButtonType.OK);
            a.showAndWait();
            return;
        }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus data kamar nomor: " + s.getNomor_kamar() + "?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLDocumentController.dbkamar.delete(s.getNomor_kamar())) {
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
        tbvkamar.getSelectionModel().selectFirst();
        tbvkamar.requestFocus();
    }

    @FXML
    private void btnPrevKlik(ActionEvent event) {
        tbvkamar.getSelectionModel().selectAboveCell();
        tbvkamar.requestFocus();
    }

    @FXML
    private void btnNextKlik(ActionEvent event) {
        tbvkamar.getSelectionModel().selectBelowCell();
        tbvkamar.requestFocus();
    }

    @FXML
    private void btnLastKlik(ActionEvent event) {
        tbvkamar.getSelectionModel().selectLast();
        tbvkamar.requestFocus();
    }
}
