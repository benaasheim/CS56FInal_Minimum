package edu.smc.javafx;

import edu.smc.data.Student;
import edu.smc.network.Client;
import edu.smc.network.Server;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;

public class JavaFXController {
    private static final String CMD_LOGIN = "login#";
    private static final String CMD_ADD = "add#";
    private static final String CMD_REMOVE = "remove#";
    private static final String CMD_LIST = "list";
    private static final String SUCCESS = "true";
    private static final String Fail = "false";
    public static final int DELAY = 2;
    public static final String STUDENT_REMOVED_SUCCESS = "Student removed";
    public static final String STUDENT_REMOVED_FAIL = "Student does not exist";
    public static final String SERVER_ADDRESS = "localhost";

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;
    @FXML
    private TextField studentID;

    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;
    @FXML
    private TextField major;


    @FXML
    private Label message;
    private Client client;

    //initial view controller
    @FXML
    protected void onLoginAction(ActionEvent event) {
        try {
            this.client = new Client("localhost", 5000);
            StringBuilder request = new StringBuilder(CMD_LOGIN);
            request.append(username.getText() + "#");
            request.append(password.getText());
            client.sendToServer(request.toString());
            if (client.readFromServer().equals("true")) {
                loadAdminView(((Node) event.getSource()).getScene().getWindow());
            } else {
                message.setText("login failed");
            }
            client.close();
        } catch (IOException e) {

        }
    }

    private void loadAdminView(Window window) {
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("admin-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 320, 240);
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //admin view controller
    @FXML
    public void onModifyAction(ActionEvent event) {
        loadModifyView(((Node) event.getSource()).getScene().getWindow());
    }

    private void loadModifyView(Window window) {
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("modify-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 320, 240);
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onRemoveAction(ActionEvent event){
        loadRemoveView(((Node)event.getSource()).getScene().getWindow());
    }

    public void loadRemoveView(Window window){
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("remove-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(),320, 240);
            ((Stage)window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //modify view controller
    public void onAddAction(ActionEvent event) {

        loadAddView(((Node) event.getSource()).getScene().getWindow());
    }

    private void loadAddView(Window window) {
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("add-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 320, 240);
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onListAction(ActionEvent event) {
        loadListView(((Node) event.getSource()).getScene().getWindow());
    }

    private void loadListView(Window window) {

        try {
            this.client = new Client("localhost", Server.PORT);
            client.sendToServer(CMD_LIST.trim());
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(3));
            String[] students = client.readFromServer().split("#");
            for(String student: students) {
                Label label = new Label(student);
                vbox.getChildren().add(label);
            }
            client.close();
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(vbox);
            VBox mainLayout = new VBox(scrollPane);
            Button button = new Button("Back");
            button.setOnAction(e -> loadAdminView(window));
            mainLayout.getChildren().add(button);
            Scene scene = new Scene(mainLayout, 320, 240);
            ((Stage)window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //add student view controller
    public void onAddStudentAction(ActionEvent event) {
        try {
            this.client = new Client("localhost", Server.PORT);
            StringBuilder request = new StringBuilder();
            request.append(CMD_ADD);
            request.append(firstName.getText() + "#");
            request.append(lastName.getText() + "#");
            request.append(phoneNumber.getText() + "#");
            request.append(address.getText() + "#");
            request.append(major.getText());
            client.sendToServer(request.toString());
            if (client.readFromServer().equals(SUCCESS)) {
                message.setText("Student added");
            } else {
                message.setText("Student already exist");
            }
            client.close();
            PauseTransition pause = new PauseTransition(Duration.seconds(DELAY)); //Delay for 2 seconds
            pause.setOnFinished(e -> loadAdminView(((Node) event.getSource()).getScene().getWindow()));
            pause.play();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onRemoveStudentAction(ActionEvent event) {
        try{
            this.client = new Client(SERVER_ADDRESS, Server.PORT);
            StringBuilder request = new StringBuilder();
            request.append(CMD_REMOVE);
            request.append(studentID.getText());
            client.sendToServer(request.toString());
            if(client.readFromServer().equals(SUCCESS)){
                message.setText(STUDENT_REMOVED_SUCCESS);
            }else{
                message.setText(STUDENT_REMOVED_FAIL);
            }
            client.close();
            PauseTransition pause = new PauseTransition(Duration.seconds(DELAY)); //Delay for 2 seconds
            pause.setOnFinished(e -> loadAdminView(((Node) event.getSource()).getScene().getWindow()));
            pause.play();
        }catch(IOException e){

        }



    }
}