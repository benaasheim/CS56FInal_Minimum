package edu.smc.tester;

import edu.smc.data.Database;

public class Test {
    public static void main(String[] args){
        Database data = new Database();
        data.addStudent("Alexander", "Alcazar", "8058409576","205 S. McKinley Ave. Oxnard, CA", "Computer Science");
        System.out.println();
    }
}
