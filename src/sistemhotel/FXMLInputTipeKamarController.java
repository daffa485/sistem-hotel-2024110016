/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemhotel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author daffa
 */
public class FXMLInputTipeKamarController implements Initializable {

    @FXML
    private TextField txtid;
    @FXML
    private TextField txtnama;
    @FXML
    private TextField txtkapasitas;
    @FXML
    private TextArea txtdeskripsi;
    @FXML
    private TextField txtgambar;
    @FXML
    private Button btnPilihGambar;
    @FXML
    private ImageView imgPreview;
    @FXML
    private Button btnSimpan;
    @FXML
    private Button btnBatal;

    private boolean editdata = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inisialisasi
    }

    public void execute(TipeKamarModel d) {
        if (d != null && d.getId_tipe() != null && !d.getId_tipe().isEmpty()) {
            editdata = true;
            txtid.setText(d.getId_tipe());
            txtnama.setText(d.getNama_tipe());
            txtkapasitas.setText(String.valueOf(d.getKapasitas()));
            txtdeskripsi.setText(d.getDeskripsi_fasilitas());
            txtgambar.setText(d.getGambar());
            txtid.setEditable(false);
            txtnama.requestFocus();

            if (d.getGambar() != null && !d.getGambar().trim().isEmpty()) {
                loadPreview(d.getGambar());
            }
        }
    }

    private void loadPreview(String pathOrFilename) {
        try {
            File f = new File("images/" + pathOrFilename);
            if (!f.exists()) {
                f = new File(pathOrFilename);
            }
            if (f.exists()) {
                imgPreview.setImage(new Image(f.toURI().toString()));
            } else {
                imgPreview.setImage(null);
            }
        } catch (Exception e) {
            imgPreview.setImage(null);
        }
    }

    @FXML
    private void btnPilihGambarKlik(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Pilih Foto Tipe Kamar");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg")
        );
        File selectedFile = fc.showOpenDialog(btnPilihGambar.getScene().getWindow());
        if (selectedFile != null) {
            try {
                File dirImages = new File("images");
                if (!dirImages.exists()) {
                    dirImages.mkdir();
                }
                File destFile = new File(dirImages, selectedFile.getName());
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                txtgambar.setText(selectedFile.getName());
                loadPreview(selectedFile.getName());
            } catch (IOException e) {
                txtgambar.setText(selectedFile.getAbsolutePath());
                loadPreview(selectedFile.getAbsolutePath());
            }
        }
    }

    @FXML
    private void btnSimpanKlik(ActionEvent event) {
        if (txtid.getText().trim().isEmpty() || txtnama.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING, "ID Tipe dan Nama Tipe tidak boleh kosong!", ButtonType.OK);
            a.showAndWait();
            return;
        }

        int kapasitas = 2;
        try {
            kapasitas = Integer.parseInt(txtkapasitas.getText().trim());
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Kapasitas harus berupa angka bulat!", ButtonType.OK);
            a.showAndWait();
            txtkapasitas.requestFocus();
            return;
        }

        TipeKamarModel n = new TipeKamarModel();
        n.setId_tipe(txtid.getText().trim());
        n.setNama_tipe(txtnama.getText().trim());
        n.setKapasitas(kapasitas);
        n.setDeskripsi_fasilitas(txtdeskripsi.getText().trim());
        n.setGambar(txtgambar.getText().trim());

        FXMLDocumentController.dbtipekamar.setTipeKamarModel(n);

        if (editdata) {
            if (FXMLDocumentController.dbtipekamar.update()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil diubah", ButtonType.OK);
                a.showAndWait();
                btnBatalKlik(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } else if (FXMLDocumentController.dbtipekamar.validasi(n.getId_tipe()) <= 0) {
            if (FXMLDocumentController.dbtipekamar.insert()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
                a.showAndWait();
                btnBatalKlik(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "ID Tipe Kamar sudah ada!", ButtonType.OK);
            a.showAndWait();
            txtid.requestFocus();
        }
    }

    @FXML
    private void btnBatalKlik(ActionEvent event) {
        btnBatal.getScene().getWindow().hide();
    }
}
