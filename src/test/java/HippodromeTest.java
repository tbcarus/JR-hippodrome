import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Test
    void nullException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
    }

    @Test
    void nullExceptionMessage() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException exc) {
            Assertions.assertEquals("Horses cannot be null.", exc.getMessage());
        }
    }

    @Test
    void blankException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    void blankExceptionMessage() {
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException exc) {
            Assertions.assertEquals("Horses cannot be empty.", exc.getMessage());
        }
    }

    @Test
    void getHorses() {
        List<Horse> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new Horse("name-" + i, i + 1, i));
        }
        List<Horse> listExp = new ArrayList<>(list);
        Hippodrome hippodrome = new Hippodrome(list);
        Assertions.assertIterableEquals(listExp, hippodrome.getHorses());
    }

    @Test
    void move() {
        List<Horse> mockList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mockList.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(mockList);
        hippodrome.move();
        for (Horse horse : mockList) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new Horse("name-" + i, i + 1, i));
        }
        Hippodrome hippodrome = new Hippodrome(list);
        Assertions.assertEquals(list.get(29), hippodrome.getWinner());
    }
}