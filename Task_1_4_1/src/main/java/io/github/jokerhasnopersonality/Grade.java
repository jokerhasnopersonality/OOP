package io.github.jokerhasnopersonality;

public class Grade {
    /* For EXAM and CREDIT types evaluation system is:
       5 - Excellent,
       4 - Good,
       3 - Satisfactory,
       2 - Unsatisfactory.
       For PASS type evaluation system is:
       1 - Pass,
       0 - Fail.
     */
    public enum EvaluationType {
        EXAM,
        CREDIT,
        PASS
    }
    private EvaluationType type;
    private int grade;
    public Grade(EvaluationType type, int grade) throws IllegalStateException {
        if ((grade < 0 || grade > 5) || (type == null)
                || (type == EvaluationType.PASS && grade > 1)
                || (type != EvaluationType.PASS && grade < 2)) {
            throw new IllegalStateException("Grade or Evaluation type is incorrect.");
        }
        this.type = type;
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) throws IllegalStateException {
        if ((grade < 0 || grade > 5)
                || (type == EvaluationType.PASS && grade > 1)
                || (type != EvaluationType.PASS && grade < 2)) {
            throw new IllegalStateException("Grade type is incorrect.");
        }
        this.grade = grade;
    }
}
