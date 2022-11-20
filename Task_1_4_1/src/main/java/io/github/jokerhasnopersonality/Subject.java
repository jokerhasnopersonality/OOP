package io.github.jokerhasnopersonality;

import java.util.ArrayList;

public class Subject {
    private ArrayList<Subject> grades;
    private String subject;
    private int cnt;
    private double average;
    private int semester;

    public Subject(String subject, int semester) {
        this.subject = subject;
        grades = new ArrayList<>();
        this.semester = semester;
    }

    public void addGrade(Grade grade) {
        return;
    }

    public Grade getGrade(int index) {
        return null;
    }

    public double getAverage() {
        return average;
    }

    public int getCnt() {
        return cnt;
    }
}
