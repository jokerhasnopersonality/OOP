package io.github.jokerhasnopersonality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing gradebook of Information Technology Faculty student on a budgetary basis.
 */
public class Gradebook {
    private final int id;
    private List<Map<String, Grade>> semesters;
    private int currentSemester;
    private Grade qualificationWork;

    /**
     * Gradebook constructor. Initiates an empty gradebook with 8 semesters and
     * sets a student ID.
     */
    public Gradebook(int id) {
        this.id = id;
        semesters = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            semesters.add(new HashMap<>());
        }
        currentSemester = 1;
        qualificationWork = null;
    }

    /**
     * Calculates current GPA for all the grades. Does not include "diploma semester" with
     * index = 0 because the diploma marks are duplicates from other semesters.

     * @return Grade Point Average
     */
    public double getGradePointAverage() {
        double gpa = 0;
        int cnt = 0;
        for (Map<String, Grade> semester : semesters.subList(1, 8)) {
            gpa += semester.values().stream().mapToInt(Grade::getValue).sum();
            cnt += semester.size();
        }
        gpa = gpa / cnt;
        return gpa;
    }

    /**
     * Separate method for qualification work grade.
     */
    public void putQualificationWorkGrade(int grade) {
        this.qualificationWork = new Grade(Grade.EvaluationType.CREDIT, grade);
    }

    /**
     * Method for checking criterias for Honours Degree.

     * @return true if a students has a chance to graduate with honors
     */
    public boolean canGraduateWithHonors() {
        if (qualificationWork == null || qualificationWork.getValue() != 5) {
            return false;
        }
        if (getGradePointAverage() < 4.5) {
            return false;
        }
        if ((double) semesters.get(0).values().stream()
                .mapToInt(Grade::getValue).sum() / semesters.get(0).size() < 4.75) {
            return false;
        }
        return true;
    }

    /**
     * Method for checking criteria for increased stipend.
     * Checks if a student has only excellent grades.
     */
    public boolean increasedStipend(int semester) throws IndexOutOfBoundsException {
        if (semester < 1 || semester > 8) {
            throw new IndexOutOfBoundsException();
        }
        for (Grade grade : semesters.get(semester - 1).values()) {
            if (grade.getValue() < 5) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks criteria for increased stipend in current semester.
     */
    public boolean increasedStipend() {
        return increasedStipend(currentSemester);
    }

    /**
     * Sets current semester.
     */
    public void setCurrentSemester(int semester) throws IndexOutOfBoundsException {
        if (semester < 1 || semester > 8) {
            throw new IndexOutOfBoundsException();
        }
        currentSemester = semester;
    }

    /**
     * Returns current semester value.
     */
    public int getCurrentSemester() {
        return currentSemester;
    }

    /**
     * Sets grade with given Evaluation type and value given subject into specified semester.
     */
    public void putGrade(int semester, String subject, Grade.EvaluationType type, int grade)
            throws IndexOutOfBoundsException {
        if (semester < 1 || semester > 8) {
            throw new IndexOutOfBoundsException();
        }
        Grade add = new Grade(type, grade);
        semesters.get(semester).put(subject, add);
    }

    /**
     * Puts grade for current semester.
     */
    public void putGrade(String subject, Grade.EvaluationType type, int grade) {
        Grade add = new Grade(type, grade);
        semesters.get(currentSemester).put(subject, add);
    }

    /**
     * Separate method for diploma grades, which are kept in semester with index = 0 and
     * also duplicated into a specified semester.
     */
    public void putDiplomaGrade(int semester, String subject,
                                Grade.EvaluationType type, int grade) {
        putGrade(semester, subject, type, grade);
        Grade add = new Grade(type, grade);
        semesters.get(0).put(subject, add);
    }

    /**
     * Returns grade for specified semester and subject.
     */
    public Grade getGrade(int semester, String subject) throws IndexOutOfBoundsException {
        if (semester < 1 || semester > 8) {
            throw new IndexOutOfBoundsException();
        }
        return semesters.get(semester).get(subject);
    }

    public Grade getDiplomaGrade(String subject) {
        return semesters.get(0).get(subject);
    }
}
