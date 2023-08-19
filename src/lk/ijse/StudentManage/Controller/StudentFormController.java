package lk.ijse.StudentManage.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.StudentManage.model.StudentModel;
import lk.ijse.StudentManage.to.Student;


import java.sql.SQLException;
import java.util.ArrayList;

public class StudentFormController {
    public TextField txtStuID;
    public TextField txtStuName;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtNic;
    public Button CancelButton;
    public TableView <Student> tabletAll;

    public void initialize () throws SQLException, ClassNotFoundException {

        tabletAll.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("StudentID"));
        tabletAll.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        tabletAll.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("email"));
        tabletAll.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tabletAll.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("address"));
        tabletAll.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("nic"));

        getAllData();
    }

    public void btnAddStudent(ActionEvent actionEvent) {
            String studentID = txtStuID.getText();
            String studentName = txtStuName.getText();
            String email = txtEmail.getText();
            String contact = txtContact.getText();
            String address = txtAddress.getText();
            String nic = txtNic.getText();

        Student student = new Student(studentID,studentName,email,contact,address,nic);
        try {
            boolean isAdded = StudentModel.add(student);

            if(isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
                emptyTextField(student);
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

   public void btnSearchStudent(ActionEvent actionEvent) {

       String studentId = txtStuID.getText();

       try {
           Student student = StudentModel.search(studentId);
           if(student != null) {
               fillData(student);

           }
       } catch (SQLException | ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
    }

    public void btnUpdateStudent(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String studentID = txtStuID.getText();
        String studentName = txtStuName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String nic = txtNic.getText();

      Student student = new Student(studentID,studentName,email,contact,address,nic);
            boolean isUpdated = StudentModel.update(student);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Update Student").show();
                emptyTextField(student);
            }else {
                new Alert(Alert.AlertType.WARNING,"Something Happened").show();
                emptyTextField(student);
            }
    }

    public void btnDeleteStudent(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String studentId = txtStuID.getText();
        Student student = new Student(studentId);
        StudentModel studentModel = new StudentModel();

            boolean isDeleted = studentModel.delete(student);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Delete!").show();
                emptyTextField(student);
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
                emptyTextField(student);
            }
    }

    public void btnCancel(ActionEvent actionEvent) {
        Stage stage = (Stage)CancelButton.getScene().getWindow();
        stage.close();
    }
    private void fillData(Student student){
        txtStuID.setText(student.getStudentID());
        txtStuName.setText(student.getStudentName());
        txtEmail.setText(student.getEmail());
        txtContact.setText(student.getContact());
        txtAddress.setText(student.getAddress());
        txtNic.setText(student.getNic());
    }

    public Student getData(Student student){
        String studentID = txtStuID.getText();
        String studentName = txtStuName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String nic = txtNic.getText();
        return student;
    }

    public void getEnterKeyData(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (keyEvent.getCode()== KeyCode.ENTER){
            String studentId = txtStuID.getText();


            try {
                Student student = StudentModel.search(studentId);
                if(student != null) {
                    fillData(student);

                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void getAllData() throws SQLException, ClassNotFoundException {
        ArrayList<Student> AllData = StudentModel.getAllStudent();
        ObservableList<Student> observableList = FXCollections.observableArrayList();
        for (Student student :
                AllData){
            observableList.add(new Student(
                    student.getStudentID(),
                    student.getStudentName(),
                    student.getEmail(),
                    student.getContact(),
                    student.getAddress(),
                    student.getNic()
            ));
        }
        tabletAll.setItems(observableList);
    }
    public Student emptyTextField(Student student){
        getData(student);
        txtStuID. clear();
        txtStuName.clear();
        txtEmail.clear();
        txtContact.clear();
        txtAddress.clear();
        txtNic.clear();
        return student;
    }


}

