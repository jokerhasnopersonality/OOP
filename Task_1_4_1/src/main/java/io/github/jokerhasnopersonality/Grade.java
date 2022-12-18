package io.github.jokerhasnopersonality;

/**
 * Class representing a grade in a student gradebook.
 */
public class Grade {
    /**
     * For EXAM and CREDIT types evaluation system is:
     *        5 - Excellent,
     *        4 - Good,
     *        3 - Satisfactory,
     *        2 - Unsatisfactory.
     *        For PASS type evaluation system is:
     *        5 - Pass,
     *        0 - Fail.
     */
    public enum EvaluationType {
        EXAM,
        CREDIT,
        PASS
    }

    private EvaluationType type;
    private int grade;

    /**
     * Grade constructor. Initiates a grade with the specified evaluation type and value.
     */
    public Grade(EvaluationType type, int grade) throws IllegalStateException {
        if ((grade < 0 || grade > 5) || (type == null)
                || (type == EvaluationType.PASS && grade != 0 && grade != 5)
                || (type != EvaluationType.PASS && grade < 2)) {
            throw new IllegalStateException("Grade or Evaluation type is incorrect.");
        }
        this.type = type;
        this.grade = grade;
    }

    public int getValue() {
        return grade;
    }

    public EvaluationType getEvaluationType() {
        return type;
    }
}
