package edu.smc.data;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Database {
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
    private int generateID(){
        boolean IDExist = false;
        Random random = new Random();
        int randomNumber;

        do{
            randomNumber = random.nextInt(9000000) + 1000000;
            for(Student student: studentList){
                if(student.getStudentID() == randomNumber){
                    IDExist = true;
                }
            }
        } while(IDExist);

        return randomNumber;
    }
     public void loadData(){
         File dataFile = new File("data.txt");
         try {
             Scanner scanner = new Scanner(dataFile);
             String line;
             while(scanner.hasNextLine()){
                 line = scanner.nextLine();
                 studentList.add(parseData(line.split(",")));

             }
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }
     }
    private Student parseData(String[] data){
        String firstname = data[0];
        String lastname = data[1];
        int studentID = Integer.valueOf(data[2]);
        String phoneNumber = data[3];
        String address = data[4];
        String major = data[5];
        String username = data[6];
        String password = data[7];
        return new Student(firstname, lastname, studentID, phoneNumber, address, major, username, password);
    }

}
