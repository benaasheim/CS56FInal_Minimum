package edu.smc.tester;

import edu.smc.data.Database;

public class Test {
    public static void test1() {
        Database data = new Database();
        data.addStudent("Alexander", "Alcazar", "8058409576","205 S. McKinley Ave. Oxnard, CA", "Computer Science");
        System.out.println();
        data.saveData();
    }
    public static void test2() {
        Database data = new Database();
        data.loadData();
        data.listStudentsFull();
    }
    public static void test3() {
        Database data = new Database();
        data.loadData();
        data.listStudentsFull();
        data.saveData();
    }
    public static void main(String[] args){
        test3();
    }
}
