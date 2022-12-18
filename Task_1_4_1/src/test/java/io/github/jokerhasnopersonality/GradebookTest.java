package io.github.jokerhasnopersonality;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

/**
 * Tests to check Gradebook realization.
 */
public class GradebookTest {
    public Gradebook gradebook;

    @Test
    public void test0() {
        gradebook = new Gradebook(0);
        Assertions.assertThrows(IllegalStateException.class,
                () -> gradebook.putGrade(1, "Imperative programming",
                        Grade.EvaluationType.CREDIT, 100));
        Assertions.assertThrows(IllegalStateException.class,
                () -> gradebook.putGrade("Imperative programming",
                        Grade.EvaluationType.CREDIT, 101));
        Assertions.assertThrows(IllegalStateException.class,
                () -> gradebook.putDiplomaGrade(1, "Imperative programming",
                        Grade.EvaluationType.CREDIT, -98));
        Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> gradebook.getGrade(-3, "Imperative programming"));
        Assertions.assertEquals(1, gradebook.getCurrentSemester());

    }
    public void testData() {
        gradebook = new Gradebook(210637);
        gradebook.putGrade(1, "Physical education",
                Grade.EvaluationType.PASS, 5);
        gradebook.putGrade(1, "Imperative programming",
                Grade.EvaluationType.CREDIT, 4);
        gradebook.putDiplomaGrade(1, "History",
                Grade.EvaluationType.CREDIT, 5);
        gradebook.putGrade("English", Grade.EvaluationType.PASS, 5);
        gradebook.putDiplomaGrade(1, "Culture of speech",
                Grade.EvaluationType.CREDIT, 5);
        gradebook.putGrade("Digital platforms", Grade.EvaluationType.PASS, 5);
        gradebook.putGrade("Declarative programming",
                Grade.EvaluationType.CREDIT, 4);
        gradebook.putGrade("Calculus", Grade.EvaluationType.EXAM, 5);
        gradebook.putGrade("Discrete Math", Grade.EvaluationType.EXAM, 5);

        gradebook.setCurrentSemester(2);

        gradebook.putDiplomaGrade(2, "Measuring workshop",
                Grade.EvaluationType.PASS, 5);
        gradebook.putGrade("Physical education", Grade.EvaluationType.PASS, 5);
        gradebook.putDiplomaGrade(2, "Digital platforms",
                Grade.EvaluationType.CREDIT, 4);
        gradebook.putGrade("English", Grade.EvaluationType.CREDIT, 5);
        gradebook.putDiplomaGrade(2, "Declarative programming ",
                Grade.EvaluationType.CREDIT, 4);
        gradebook.putDiplomaGrade(2, "Imperative programming",
                Grade.EvaluationType.EXAM, 4);
        gradebook.putGrade("Calculus", Grade.EvaluationType.EXAM, 4);
        gradebook.putDiplomaGrade(2, "Discrete Math",
                Grade.EvaluationType.EXAM, 4);
    }
    @Test
    public void simpleTest() {
        testData();
        Assertions.assertEquals(2, gradebook.getCurrentSemester());

        Assertions.assertEquals(5, gradebook.getGrade(1, "Discrete Math").getValue());
        Assertions.assertEquals(4, gradebook.getGrade(2, "Discrete Math").getValue());
        Assertions.assertEquals(Grade.EvaluationType.PASS,
                gradebook.getDiplomaGrade("Measuring workshop").getEvaluationType());

        Assertions.assertEquals((double) 78 / 17, gradebook.getGradePointAverage());
        Assertions.assertFalse(gradebook.increasedStipend());
        Assertions.assertFalse(gradebook.increasedStipend(1));
    }

    @Test
    public void testChange() {
        testData();
        gradebook.putGrade(1, "Potions", Grade.EvaluationType.CREDIT, 3);
        Assertions.assertFalse(gradebook.increasedStipend(2));
        gradebook.putDiplomaGrade(3, "Potions", Grade.EvaluationType.EXAM, 2);
        Assertions.assertFalse(gradebook.canGraduateWithHonors());

        gradebook.putDiplomaGrade(1, "Potions", Grade.EvaluationType.CREDIT, 5);
        gradebook.putDiplomaGrade(3, "Potions", Grade.EvaluationType.EXAM, 5);
        gradebook.putDiplomaGrade(2,
                "Digital platforms", Grade.EvaluationType.CREDIT, 5);
        Assertions.assertFalse(gradebook.canGraduateWithHonors());
        gradebook.putDiplomaGrade(2,
                "Imperative programming", Grade.EvaluationType.EXAM, 5);

        Assertions.assertFalse(gradebook.canGraduateWithHonors());
        gradebook.putQualificationWorkGrade(5);
        Assertions.assertTrue(gradebook.canGraduateWithHonors());

        gradebook.putGrade(1,
                "Imperative programming", Grade.EvaluationType.CREDIT, 5);
        gradebook.putGrade(1,
                "Declarative programming", Grade.EvaluationType.CREDIT, 5);
        Assertions.assertTrue(gradebook.increasedStipend());
    }
}