package com.readdrivefiles.readdrivefiles.DTO;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name="DriveAttendance")
public class Drive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Emp_id;
    private String Email;

    private String firstName;

    private String lastName;

    private String Duration;

    private String Time_joined;

    private String Time_exited;

    public Drive() {
    }

    public Drive(String email, String firstName, String lastName, String duration, String time_joined, String time_exited) {
        Email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        Duration = duration;
        Time_joined = time_joined;
        Time_exited = time_exited;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getTime_joined() {
        return Time_joined;
    }

    public void setTime_joined(String time_joined) {
        Time_joined = time_joined;
    }

    public String getTime_exited() {
        return Time_exited;
    }

    public void setTime_exited(String time_exited) {
        Time_exited = time_exited;
    }

    @Override
    public String toString() {
        return "Drive{" +
                "Email='" + Email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", Duration='" + Duration + '\'' +
                ", Time_joined='" + Time_joined + '\'' +
                ", Time_exited='" + Time_exited + '\'' +
                '}';
    }
}
