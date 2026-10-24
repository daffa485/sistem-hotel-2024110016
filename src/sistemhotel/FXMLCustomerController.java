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
public class FXMLCustomerController implements Initializable {

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
    private TableView<CustomerModel> tbvcustomer;
    @FXML
    private TableColumn<CustomerModel, String> colIdCustomer;
    @FXML
    private TableColumn<CustomerModel, String> colIdentitas;
    @FXML
    private TableColumn<CustomerModel, String> colNama;
    @FXML
    private TableColumn<CustomerModel, String> colTelepon;
    @FXML
    private TableColumn<CustomerModel, String> colEmail;
    @FXML
    private TableColumn<CustomerModel, String> colAlamat;
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
            colIdCustomer.setCellValueFactory(new PropertyValueFactory<>("id_customer"));
            colIdentitas.setCellValueFactory(new PropertyValueFactory<>("nomor_identitas"));
            colNama.setCellValueFactory(new PropertyValueFactory<>("nama_lengkap"));
            colTelepon.setCellValueFactory(new PropertyValueFactory<>("no_telepon"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));

            showdata();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showdata() throws SQLException {
        ObservableList<CustomerModel> data = FXMLDocumentController.dbcustomer.Load();
        if (data != null) {
            tbvcustomer.setItems(data);
            if (!data.isEmpty()) {
                tbvcustomer.getSelectionModel().selectFirst();
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
            ObservableList<CustomerModel> hasil;
            if (keyword.isEmpty()) {
                hasil = FXMLDocumentController.dbcustomer.Load();
            } else {
                hasil = FXMLDocumentController.dbcustomer.CariCustomer(keyword);
            }
            tbvcustomer.setItems(hasil);
            if (hasil != null && !hasil.isEmpty()) {
                tbvcustomer.getSelectionModel().selectFirst();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnTambahKlik(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputCustomer.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Tambah Tamu / Customer");
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
        CustomerModel s = tbvcustomer.getSelectionModel().getSelectedItem();
        if (s == null) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih data customer yang akan diubah!", ButtonType.OK);
            a.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputCustomer.fxml"));
            Parent root = (Parent) loader.load();
            FXMLInputCustomerController isidt = (FXMLInputCustomerController) loader.getController();
            isidt.execute(s);
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Ubah Tamu / Customer");
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
        CustomerModel s = tbvcustomer.getSelectionModel().getSelectedItem();
        if (s == null) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Pilih data customer yang akan dihapus!", ButtonType.OK);
            a.showAndWait();
            return;
        }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus data customer: " + s.getNama_lengkap() + "?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLDocumentController.dbcustomer.delete(s.getId_customer())) {
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
        tbvcustomer.getSelectionModel().selectFirst();
        tbvcustomer.requestFocus();
    }

    @FXML
    private void btnPrevKlik(ActionEvent event) {
        tbvcustomer.getSelectionModel().selectAboveCell();
        tbvcustomer.requestFocus();
    }

    @FXML
    private void btnNextKlik(ActionEvent event) {
        tbvcustomer.getSelectionModel().selectBelowCell();
        tbvcustomer.requestFocus();
    }

    @FXML
    private void btnLastKlik(ActionEvent event) {
        tbvcustomer.getSelectionModel().selectLast();
        tbvcustomer.requestFocus();
    }
}
