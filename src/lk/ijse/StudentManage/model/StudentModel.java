package lk.ijse.StudentManage.model;


import javafx.scene.control.Alert;
import lk.ijse.StudentManage.Controller.StudentFormController;
import lk.ijse.StudentManage.to.Student;
import lk.ijse.StudentManage.util.CrudUtil;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class StudentModel {


    public static boolean add(Student student) throws SQLException, ClassNotFoundException {
        String sql = "insert into student values (?,?,?,?,?,?)";
        return CrudUtil.execute(sql, student.getStudentID(), student.getStudentName(), student.getEmail(), student.getContact(), student.getAddress(), student.getNic());
    }

    public static Student search(String studentID) throws SQLException, ClassNotFoundException {
        String sql = "select * from student where student_id = ?";
        ResultSet result = CrudUtil.execute(sql, studentID);
        if (result.next()) {
            return new Student(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6)

            );
        }
        return null;
    }


    public static boolean update(Student student) throws SQLException, ClassNotFoundException {

        String sql = "update student set student_name=? ,email=?,contact=?,address=?,nic=?where student_id=?";
        if (CrudUtil.execute(sql,student.getStudentName(),student.getEmail(),student.getContact(),student.getAddress(),student.getNic(),student.getStudentID())){
            new Alert(Alert.AlertType.INFORMATION,"Updated").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
        return true;
    }


   public static boolean delete(Student student) throws SQLException, ClassNotFoundException {
       String sql = "delete from student where student_id = ?";
        return CrudUtil.execute(sql,student.getStudentID());
    }

    public static ArrayList<Student> getAllStudent() throws SQLException, ClassNotFoundException {
        String sql = "select * from Student";
        ResultSet result = CrudUtil.execute(sql);
        ArrayList<Student> students = new ArrayList<>();
        while (result.next()){
            students.add(new Student(
                  result.getString(1),
                  result.getString(2),
                  result.getString(3),
                  result.getString(4),
                  result.getString(5),
                  result.getString(6)
            ));
        }
        return students;
    }
}

