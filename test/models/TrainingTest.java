package models;

import org.junit.jupiter.api.BeforeEach;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrainingTest {
    static Training training;
    static Training training2;

    @BeforeEach
    static void setup() {
        training = new Training("001", "Test Training 1", "no description", "-", 2f, 1200.00f, 20, new Date(12121212), "Java");
        training2 = new Training("002", "Test Training 2", "no description", "-", 2f, 1200.00f, 20, new Date(12121212), "JAVA");
    }

    @org.junit.jupiter.api.Test
    void getCategory() {
        assertEquals("Java", training.getCategory());
        assertEquals("Java", training2.getCategory());
    }

}