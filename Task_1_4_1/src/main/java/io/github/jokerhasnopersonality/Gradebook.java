package io.github.jokerhasnopersonality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing gradebook of Information Technology Faculty student.
 */
public class Gradebook {
    private final int id;
    private Map<Integer, Map<String, Subject>> subjects;
    private double gradePointAverage;
    int currentSemester;

    public Gradebook(int id) {
        this.id = id;
        subjects = new HashMap<>(8);
    }

    public double getGradePointAverage() {
        return gradePointAverage;
    }

    public void addSubject(Subject subject, int semester) {
        return;
    }

    public boolean canGraduateWithHonors() {
        return false;
    }

    public boolean increasedStipend() {
        return false;
    }

    public void setGrade() {

    }
}
