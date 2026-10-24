/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package sistemhotel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author daffa
 */
public class FXMLDocumentController implements Initializable {

    public static DBTipeKamar dbtipekamar = new DBTipeKamar();
    public static DBCustomer dbcustomer = new DBCustomer();
    public static DBKamar dbkamar = new DBKamar();
    public static DBRateKamar dbratekamar = new DBRateKamar();

    @FXML
    private MenuItem menuDataCustomer;
    @FXML
    private MenuItem menuDataTipeKamar;
    @FXML
    private MenuItem menuDataKamar;
    @FXML
    private MenuItem menuDataRateKamar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inisialisasi controller menu utama
    }

    @FXML
    private void menuDataCustomerClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCustomer.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Data Tamu / Customer");
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void menuDataTipeKamarClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLTipeKamar.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Data Tipe Kamar");
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void menuDataKamarClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLKamar.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Data Kamar");
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void menuDataRateKamarClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLRateKamar.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("Data Rate Kamar");
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
