package org.kaylan;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        String url="jdbc:postgresql://localhost:5432/school_Java12";
        String username="postgres";
        String password="1234";

        Connection connection = null;

        try {
           connection = DriverManager.getConnection(url,username,password);
            System.out.println("Bağlantı başarılı...");
           // String sql="INSERT INTO student ( name, surname, city)  VALUES ('Kral','KAYLAN','IZMIR');";
           // execute(connection,sql);
           // createStudent(connection,new Student("Hilal","Sönmez","Izmir"));
          //  updateStudent(connection,new Student(2,"Mahmut","Mersin","Malatya"));
            deleteById(connection,2);
        } catch (SQLException e) {
            System.out.println("Bağlantı hatalı..."+e);
        }finally {
            try {
                connection.close();
                System.out.println("Kapatma başarılı...");
            } catch (SQLException e) {
                System.out.println("Kapatma hatalı..."+e);
            }
        }
    }

    public static   void execute(Connection connection,String sql){

        try {
            Statement statement=connection.createStatement();
            statement.execute(sql);
            System.out.println("İşlem başarılı..");
        } catch (SQLException e) {
            System.out.println("İşlem başarılı değil..");
        }


    }
    public  static void createStudent(Connection connection,Student student){

        String sqlQuery="INSERT INTO student(name, surname, city) VALUES ( ?, ?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,student.getSurname());
            preparedStatement.setString(3,student.getCity());

            //Sorgu çalıştırma:
            int etkilenenSatirSayisi=preparedStatement.executeUpdate();
            if(etkilenenSatirSayisi>0) System.out.println("Güncelleme başarıyla gerçekleştirildi...");
            else System.out.println("Güncellemede  hata meydana geldi.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public  static void updateStudent(Connection connection,Student student){

        String sqlQuery="UPDATE student SET  name=?, surname=?, city=? WHERE id=?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,student.getSurname());
            preparedStatement.setString(3,student.getCity());
            preparedStatement.setInt(4,student.getId());

            //Sorgu çalıştırma:
            int etkilenenSatirSayisi=preparedStatement.executeUpdate();
            if(etkilenenSatirSayisi>0) System.out.println("Ekleme başarıyla gerçekleştirildi...");
            else System.out.println("Eklemede hata meydana geldi.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public  static void findByStudentCityName(Connection connection,String cityName){

        String sqlQuery="SELECT * FROM student WHERE city=? ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1,cityName);
            ResultSet resultSet=preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String city = resultSet.getString("city");

                System.out.println("Name: " + name + " Surname: " + surname + " City: " + city);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public  static void deleteById(Connection connection,int id){

        String sqlQuery= "DELETE FROM student WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,id);

            int etkilenenSatirSayisi=preparedStatement.executeUpdate();

            if(etkilenenSatirSayisi>0) System.out.println("Silme başarıyla gerçekleştirildi...");
            else System.out.println("Silmede hata meydana geldi.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}