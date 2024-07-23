package edu.smc.data;

import edu.smc.base.Person;
import edu.smc.base.User;

import java.util.Objects;

/**
 * Represents a Student, which is a specific type of User.
 * Implements the Person interface for personal information.
 */
public class Student extends User implements Person {
    public static final String STUDENT = "student\n";
    public static final String DOMAIN = "@smc.edu";
    private String firstName;
    private String lastName;
    private int studentID;
    private Major major;
    private String phoneNumber;
    private String address;

    private static final String DEFAULT_PASSWORD = "student";

    /**
     * Constructs a new Student with the specified details.
     *
     * @param firstName The first name of the student.
     * @param lastName The last name of the student.
     * @param studentID The student ID.
     * @param phoneNumber The phone number of the student.
     * @param address The address of the student.
     * @param major The major of the student.
     */
    public Student(String firstName, String lastName, int studentID, String phoneNumber, String address, String major) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.phoneNumber = phoneNumber;
        this.address = address;
        System.out.println(major);
        this.major = Major.fromString(major);
        setUsername(generateStudentEmail(studentID));
        setPassword(DEFAULT_PASSWORD);
    }

    public Student(String[] data) {
        this(data[0], data[1], Integer.valueOf(data[2]), data[3], data[4], data[5]);
        setUsername(data[6]);
        setPassword(data[7]);
    }

    /**
     * Generates an email address for the student based on their student ID.
     *
     * @param studentID The student ID.
     * @return The generated email address.
     */
    private String generateStudentEmail(int studentID) {
        return Integer.toString(studentID) + DOMAIN;
    }

    /**
     * Gets the student ID.
     *
     * @return The student ID.
     */
    public int getStudentID() {
        return studentID;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the major of the student.
     *
     * @return The major of the student.
     */
    public Major getMajor() {
        return major;
    }

    /**
     * Sets the major of the student.
     *
     * @param major The new major of the student.
     */
    public void setMajor(String major) {
        this.major = Major.fromString(major);
    }

    @Override
    public String toString() {
        return "Student: " + firstName + " " + lastName + ", ID: " + studentID + "#";
    }

    public String toData() {
        return firstName + Database.REGEX + lastName + Database.REGEX + studentID + Database.REGEX + phoneNumber + Database.REGEX + address + Database.REGEX + phoneNumber + Database.REGEX + getUsername() + Database.REGEX + STUDENT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        if (!super.equals(o)) return false;
        return studentID == student.studentID &&
                Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName) &&
                Objects.equals(major, student.major) &&
                Objects.equals(phoneNumber, student.phoneNumber) &&
                Objects.equals(address, student.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, studentID, major, phoneNumber, address);
    }
}
