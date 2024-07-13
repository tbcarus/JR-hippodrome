import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    void nullException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 20, 20));
    }

    @Test
    void nullExceptionMessage() {
        try {
            new Horse(null, 20, 20);
        } catch (IllegalArgumentException exc) {
            Assertions.assertEquals("Name cannot be null.", exc.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "  "})
    void blankException(String str) {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse(str, 20, 20));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "  "})
    void blankExceptionMessage(String str) {
        try {
            new Horse(str, 20, 20);
        } catch (IllegalArgumentException exc) {
            Assertions.assertEquals("Name cannot be blank.", exc.getMessage());
        }
    }

    @Test
    void negativeSpeedException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse("First", -20, 20));
    }

    @Test
    void negativeSpeedExceptionMessage() {
        try {
            new Horse("First", -20, 20);
        } catch (IllegalArgumentException exc) {
            Assertions.assertEquals("Speed cannot be negative.", exc.getMessage());
        }
    }

    @Test
    void negativeDistException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Horse("First", 20, -20));
    }

    @Test
    void negativeDistExceptionMessage() {
        try {
            new Horse("First", 20, -20);
        } catch (IllegalArgumentException exc) {
            Assertions.assertEquals("Distance cannot be negative.", exc.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = "HorseName")
    void getName(String str) {
        Horse horse = new Horse(str, 20, 20);
        Assertions.assertEquals(str, horse.getName());
    }

    @ParameterizedTest
    @ValueSource(ints = 15)
    void getSpeed(int speed) {
        Horse horse = new Horse("qwer", speed, 20);
        Assertions.assertEquals(speed, horse.getSpeed());
    }

    @ParameterizedTest
    @ValueSource(ints = 10)
    void getDistance(int dist) {
        Horse horse = new Horse("qwer", 20, dist);
        Assertions.assertEquals(dist, horse.getDistance());
    }

    @Test
    void getDistance() {
        Horse horse = new Horse("qwer", 20);
        Assertions.assertEquals(0, horse.getDistance());
    }

    @Spy
    Horse horse = new Horse("qwe", 10, 20);

    @Test
    void move() {
        try (MockedStatic<Horse> horseStat = Mockito.mockStatic(Horse.class)) {
            horse.move();
            horseStat.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = 0.4)
    void getRandomDouble(double random) {
        try (MockedStatic<Horse> horseStat = Mockito.mockStatic(Horse.class)) {
            horseStat.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            double exp = horse.getDistance() + horse.getSpeed() * random;
            horse.move();
            Assertions.assertEquals(exp, horse.getDistance());
        }
    }
}