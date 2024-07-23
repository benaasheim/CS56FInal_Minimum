package edu.smc.data;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.nio.file.Path;

public class Database {
    public static final String AN_ERROR_OCCURRED = "An error occurred: ";
    public static final String write_file_destination = "/datatest.txt";
    public static final String read_file_destination = "data.txt";
    public static final int student_ID_Lower_Bound = 1000000;
    public static final int student_ID_Range = 9000000;
    public static final String REGEX = ",";
    public static final String USER_DIR = "user.dir";

    Set<Student> studentList;





    public Database() {
        this.studentList = new HashSet<>();


    }

    public boolean addStudent(String firstname, String lastname,String phoneNumber, String address, String major){

        return studentList.add(new Student(firstname, lastname, generateID(),phoneNumber,address,major));
    }

    public boolean removeStudent(int studentID){
        return studentList.removeIf(student -> student.getStudentID() == studentID);
    }

    public Student getStudent(int studentID){
        for(Student student: studentList){
            if(student.getStudentID() == studentID){
                return student;
            }
        }
        return null;
    }

    public String listStudents(){
        StringBuilder list = new StringBuilder();
        for(Student i: studentList){
            list.append(i);
        }
        return list.toString();
    }
    public void listStudentsFull() {
        for(Student i: studentList){
            System.out.println(i.toData());
        }
    }
    private int generateID(){
        boolean IDExist = false;
        Random random = new Random();
        int randomNumber;

        do{
            randomNumber = random.nextInt(student_ID_Range) + student_ID_Lower_Bound;
            for(Student student: studentList){
                if(student.getStudentID() == randomNumber){
                    IDExist = true;
                }
            }
        } while(IDExist);

        return randomNumber;
    }
     public void loadData(){
         File dataFile = new File(read_file_destination);
         try {
             Scanner scanner = new Scanner(dataFile);
             String line;
             while(scanner.hasNextLine()){
                 line = scanner.nextLine();
                 studentList.add(new Student(line.split(REGEX)));
             }
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }
     }
     public void saveData() {
         // Assigning the content of the file
         String text = "";
         for (Student student: studentList) {
             text += student.toData();
         }

         // Defining the file name of the file
         Path fileName = Path.of(System.getProperty(USER_DIR) + write_file_destination);

         try {
             // Writing into the file
             Files.writeString(fileName, text);

             // Reading the content of the file
             String fileContent = Files.readString(fileName);

             // Printing the content inside the file
             System.out.println(fileContent);
         } catch (IOException e) {
             // Handling any I/O exceptions
             System.err.println(AN_ERROR_OCCURRED + e.getMessage());
         }
     }
}
