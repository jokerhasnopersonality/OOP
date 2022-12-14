package io.github.jokerhasnopersonality;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private final List<Grade> grades;
    private String subject;
    private int cnt;
    private double average;
    private int semester;

    public Subject(String subject, int semester) {
        this.subject = subject;
        grades = new ArrayList<>();
        this.semester = semester;
        cnt = 0;
        average = 0;
    }

    public void addGrade(Grade.EvaluationType type, int grade) {
        Grade add = new Grade(type, grade);
        grades.add(add);
        if (type != Grade.EvaluationType.PASS) {
            average = grades.stream().toArray().
        }
    }

    public Grade getGrade(int index) {
        return grades.get(index);
    }

    public double getAverage() {
        return average;
    }

    public int getCnt() {
        return cnt;
    }
}
