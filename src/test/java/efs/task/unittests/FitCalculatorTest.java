package efs.task.unittests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class FitCalculatorTest
{
    @Test
    void shouldReturnTrue_whenDietRecommended()
    {
        // Given
        double weight = 89.2;
        double height = 1.72;

        // When
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertTrue(recommended);
    }

    @Test
    void shouldReturnFalse_whenDietNotRecommended()
    {
        // Given
        double weight = 69.5;
        double height = 1.73;

        // When
        boolean notRecommended = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertFalse(notRecommended);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenHeightIsZero()
    {
        // Given
        double weight = 69.5;
        double height = 0.0;

        // When

        // Then
        assertThrows(IllegalArgumentException.class, () -> FitCalculator.isBMICorrect(weight, height));
    }

    @ParameterizedTest(name = "{0} Weight")
    @ValueSource(doubles = {85.0, 87.5, 89.7})
    void shouldReturnTrue_whenBMICorrect(double weight)
    {
        // Given
        double height = 1.79;

        // When

        boolean bmiState = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertTrue(bmiState);
    }

    @ParameterizedTest(name = "{0} Weight, {1} Height")
    @CsvSource({"50.5,2.0", "17.9,1.75", "33.9,1.97"})
    void shouldReturnFalse_whenBMIIncorrect_forEachWeightAndHeightPairFromCsvSource(double weight, double height)
    {
        // Given

        // When
        boolean bmiState = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertFalse(bmiState);
    }

    @ParameterizedTest(name = "{0} Weight, {1} Height")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void shouldReturnFalse_whenBMIIncorrect_forEachWeightAndHeightPairFromCSVFileSource(double weight, double height)
    {
        // Given

        // When
        boolean bmiState = FitCalculator.isBMICorrect(weight, height);

        // Then
        assertFalse(bmiState);
    }

    @Test
    void shouldReturnUserWithTheWorstBMI()
    {
        // Given
        double weight = 97.3;
        double height = 1.79;

        // When
        User user = FitCalculator.findUserWithTheWorstBMI(TestConstants.TEST_USERS_LIST);

        // Then
        assertAll("Should Return User With The Worst BMI",
                () -> assertEquals(weight, user.getWeight()),
                () -> assertEquals(height, user.getHeight())
        );
    }

    @Test
    void shouldReturnNull_whenEmptyUsersList()
    {
        // Given

        // When
        User user = FitCalculator.findUserWithTheWorstBMI(Collections.emptyList());

        // Then
        assertNull(user);
    }

    @Test
    void shouldReturnExpectedBMIScoreForListOfUsers()
    {
        // Given

        // When
        double[] usersBMI = FitCalculator.calculateBMIScore(TestConstants.TEST_USERS_LIST);

        // Then
        assertArrayEquals(TestConstants.TEST_USERS_BMI_SCORE, usersBMI);
    }
}