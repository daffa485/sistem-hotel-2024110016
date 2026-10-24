/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemhotel;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author daffa
 */
public class FXMLTipeKamarController implements Initializable {

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
    private TableView<TipeKamarModel> tbvtipekamar;
    @FXML
    private TableColumn<TipeKamarModel, String> colIdTipe;
    @FXML
    private TableColumn<TipeKamarModel, String> colNamaTipe;
    @FXML
    private TableColumn<TipeKamarModel, Integer> colKapasitas;
    @FXML
    private TableColumn<TipeKamarModel, String> colDeskripsi;
    @FXML
    private ImageView imgtipekamar;
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
            colIdTipe.setCellValueFactory(new PropertyValueFactory<>("id_tipe"));
            colNamaTipe.setCellValueFactory(new PropertyValueFactory<>("nama_tipe"));
            colKapasitas.setCellValueFactory(new PropertyValueFactory<>("kapasitas"));
            colDeskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi_fasilitas"));

            tbvtipekamar.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                tampilkanGambar(newVal);
            });

            showdata();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLTipeKamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showdata() throws SQLException {
        ObservableList<TipeKamarModel> data = FXMLDocumentController.dbtipekamar.Load();
        if (data != null) {
            tbvtipekamar.setItems(data);
            if (!data.isEmpty()) {
                tbvtipekamar.getSelectionModel().selectFirst();
            } else {
                imgtipekamar.setImage(null);
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();
        }
    }

    private void tampilkanGambar(TipeKamarModel model) {
        if (model != null && model.getGambar() != null && !model.getGambar().trim().isEmpty()) {
            File f = new File("images/" + model.getGambar());
            if (!f.exists()) {
                f = new File(model.getGambar());
            }
            if (f.exists()) {
                try {
                    imgtipekamar.setImage(new Image(f.toURI().toString()));
                    return;
                } catch (Exception e) {
                    imgtipekamar.setImage(null);
                }
            }
        }
        imgtipekamar.setImage(null);
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
            ObservableList<TipeKamarModel> hasil;
            if (keyword.isEmpty()) {
                hasil = FXMLDocumentController.dbtipekamar.Load();
            } else {
                hasil = FXMLDocumentController.dbtipekamar.CariTipeKamar(keyword);
            }
            tbvtipekamar.setItems(hasil);
            if (hasil != null && !hasil.isEmpty()) {
                tbvtipekamar.getSelectionModel().selectFirst();
            } else {
                imgtipekamar.setImage(null);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLTipeKamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnTambahKlik(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputTipeKamar.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Tambah Tipe Kamar");
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
        TipeKamarModel s = tbvtipekamar.getSelectionModel().getSelectedItem();
        if (s == null) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih data tipe kamar yang akan diubah!", ButtonType.OK);
            a.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputTipeKamar.fxml"));
            Parent root = (Parent) loader.load();
            FXMLInputTipeKamarController isidt = (FXMLInputTipeKamarController) loader.getController();
            isidt.execute(s);
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Ubah Tipe Kamar");
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
        TipeKamarModel s = tbvtipekamar.getSelectionModel().getSelectedItem();
        if (s == null) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih data tipe kamar yang akan dihapus!", ButtonType.OK);
            a.showAndWait();
            return;
        }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus data tipe kamar: " + s.getNama_tipe() + "?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLDocumentController.dbtipekamar.delete(s.getId_tipe())) {
                Alert b = new Alert(Alert.AlertType.INFORMATION, "Data berhasil dihapus", ButtonType.OK);
                b.showAndWait();
            } else {
                Alert b = new Alert(Alert.AlertType.ERROR, "Data gagal dihapus (mungkin masih direferensikan tabel kamar/rate)", ButtonType.OK);
                b.showAndWait();
            }
            showdata();
            btnFirstKlik(event);
        }
    }

    @FXML
    private void btnFirstKlik(ActionEvent event) {
        tbvtipekamar.getSelectionModel().selectFirst();
        tbvtipekamar.requestFocus();
    }

    @FXML
    private void btnPrevKlik(ActionEvent event) {
        tbvtipekamar.getSelectionModel().selectAboveCell();
        tbvtipekamar.requestFocus();
    }

    @FXML
    private void btnNextKlik(ActionEvent event) {
        tbvtipekamar.getSelectionModel().selectBelowCell();
        tbvtipekamar.requestFocus();
    }

    @FXML
    private void btnLastKlik(ActionEvent event) {
        tbvtipekamar.getSelectionModel().selectLast();
        tbvtipekamar.requestFocus();
    }
}
