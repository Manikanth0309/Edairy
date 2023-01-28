package e.diary;

import e.dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 *
 *
 *
 *
 *
 */
public class DiaryFXMLController implements Initializable {

    public static int uid = -1;
    
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnExit;
    @FXML
    private ComboBox<String> cmbUsrNm;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPasswd;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtUserName.setDisable(true);
        txtPasswd.setDisable(true);
        cmbUsrNm.getItems().addAll(UserDAO.getUnames());
        btnLogin.setDisable(true);
    }

    @FXML
    @SuppressWarnings("CallToPrintStackTrace")
    private void handleButtonEvent(ActionEvent event) {
        if (event.getSource() == btnLogin) {
            try {
                uid = UserDAO.validate(txtUserName.getText(), txtPasswd.getText());
                if (uid != -1) {
                    
                    Stage stage = (Stage) btnExit.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } else if(cmbUsrNm.getValue().equalsIgnoreCase("New/Existing User")){
                    UserDAO.registerUser(txtUserName.getText(), txtPasswd.getText());
                    uid = UserDAO.validate(txtUserName.getText(), txtPasswd.getText());
                    JOptionPane.showMessageDialog(null, " Registered "+txtUserName.getText()+" Successfully"+" "+uid);
                    Stage stage = (Stage) btnExit.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } else {
                    JOptionPane.showMessageDialog(null, "INVALID USERNAME/PASSWORD!!!");
                }
            } catch (HeadlessException | IOException e) {
                e.printStackTrace();
            }

        }
        if (event.getSource() == btnExit) {
            System.exit(0);
        }
    }

    @FXML
    private void handleCmb(ActionEvent event) {
        if (cmbUsrNm.getValue().equalsIgnoreCase("New/Existing User")) {
            txtUserName.clear();
            txtUserName.setDisable(false);
            txtPasswd.setDisable(false);
            btnLogin.setDisable(false);
            btnLogin.setText("REGISTER/LOGIN");
        } else {
            txtUserName.setText(cmbUsrNm.getValue());
            txtUserName.setDisable(true);
            txtPasswd.setDisable(false);
            btnLogin.setDisable(false);
            btnLogin.setText("LOGIN");
        }
    }
}