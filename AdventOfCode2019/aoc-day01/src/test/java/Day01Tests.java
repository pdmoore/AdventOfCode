import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day01Tests {

    @Test
    public void FindFuelRequired_DivisorDoesNotRequireRounding() {
        int actual = fuelRequiredForMass(12);
        assertEquals(2, actual);
    }

    @Test
    public void FindFuelRequired_DivisorRequiresRounding() {
        int actual = fuelRequiredForMass(14);
        assertEquals(2, actual);
    }

    @Test
    public void FindFuelRequired_LargerMass() {
        int actual = fuelRequiredForMass(1969);
        assertEquals(654, actual);
    }

    @Test
    public void FindFuelRequired_EvenLargerMass() {
        int actual = fuelRequiredForMass(100756);
        assertEquals(33583, actual);
    }

    @Test
    public void Solution_PartOne() {
        String puzzleInputFile = "day01input.txt";

        List<Integer> masses = Utilities.fileToIntegerList(puzzleInputFile);
        int sum = calcSumOfBasicFuelRequirements(masses);

        assertEquals(3320816, sum);
    }

    @Test
    public void PartTwo_Fuel_RequiresNoFurtherFuel() {
        int actual = fuelRequiredForMassAndThatFuel(14);
        assertEquals(2, actual);
    }

    @Test
    public void PartTwo_Fuel_RequiresFurtherFuel() {
        int actual = fuelRequiredForMassAndThatFuel(1969);
        assertEquals(966, actual);
    }

    @Test
    public void PartTwo_Fuel_RequireFurtherFuel_LargerExample() {
        int actual = fuelRequiredForMassAndThatFuel(100756);
        assertEquals(50346, actual);
    }

    @Test
    public void Solution_PartTwo() {
        String puzzleInputFile = "day01input.txt";

        List<Integer> masses = Utilities.fileToIntegerList(puzzleInputFile);
        int sum = calcSumOfMassAndFuelRequirements(masses);

        assertEquals(4978360, sum);
    }

    /*
    Following code is solution, not tests
     */
    private int fuelRequiredForMassAndThatFuel(int initialMass) {
        int remainingMass = initialMass;
        int fuelSum = 0;
        boolean keepReducing = true;
        while (fuelRequiredForMass(remainingMass) > 0) {
            int nextFuel = fuelRequiredForMass(remainingMass);
            fuelSum += nextFuel;
            remainingMass = nextFuel;
        }
        return fuelSum;
    }


    private int fuelRequiredForMass(int mass) {
        int massDividedByThree = mass / 3;
        return massDividedByThree - 2;
    }

    private int calcSumOfBasicFuelRequirements(List<Integer> masses) {
        int sum = 0;
        for (Integer mass :
                masses) {
            int fuelRequired = fuelRequiredForMass(mass);
            sum += fuelRequired;
        }
        return sum;
    }

    private int calcSumOfMassAndFuelRequirements(List<Integer> masses) {
        int sum = 0;
        for (Integer mass :
                masses) {
            int fuelRequired = fuelRequiredForMassAndThatFuel(mass);
            sum += fuelRequired;
        }
        return sum;
    }
}
