package delft;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.within;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import java.time.*;


class AutoAssignerTest {

    private ZonedDateTime date(int y, int m, int d, int h, int min) {
        return ZonedDateTime.of(y, m, d, h, min, 0, 0, ZoneId.systemDefault());
    }

    private final ZonedDateTime d1 = date(2025, 11, 5, 14, 0);
    private final ZonedDateTime d2 = date(2025, 11, 6, 14, 0);

    private Map<ZonedDateTime, Integer> spotsTest = new HashMap<>();


    private Workshop workshop;

    private Student s1, s2;

    @BeforeEach
    void setUp() {
        s1 = new Student(1, "Azerty", "azertye@gmail.com");
        s2 = new Student(2, "Nemo", "nemo@gmail.com");

        spotsTest.put(d1, 1);
        spotsTest.put(d2, 1);
        workshop  = new Workshop(10, "test", spotsTest);
    }

    @Test
    void basicTestStudent(){

        assertThat(s1.getName()).isEqualTo("Azerty");
        assertThat(s1.getEmail()).isEqualTo("azertye@gmail.com");
        assertThat(s2.getId()).isEqualTo(2);
    }

    @Test
    void basicTestWorkshop(){

        assertThat(workshop.getName()).isEqualTo("test");
        assertThat(workshop.getId()).isEqualTo(10);
        //assertThat(workshop.getSpotsPerDate()).isEqualTo("test,Azerty,05/11/2025 14:00");

    }



    @Test
    void assignsStudentsWhenSpotsAvailable() {
        ZonedDateTime d1 = date(2025, 11, 5, 14, 0);
        ZonedDateTime d2 = date(2025, 11, 6, 14, 0);

        Map<ZonedDateTime, Integer> spots = new HashMap<>();
        spots.put(d1, 1);
        spots.put(d2, 1);

        Workshop w1 = new Workshop(10, "test", spots);
        List<Student> students = List.of(s1, s2);
        List<Workshop> workshops = List.of(w1);

        AutoAssigner assigner = new AutoAssigner();

        AssignmentsLogger result = assigner.assign(students, workshops);

        assertThat(result.getAssignments()).containsExactlyInAnyOrder(
                String.format("test,Azerty,%s", d1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))),
                String.format("test,Nemo,%s", d2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
        );

        assertThat(w1.hasAvailableDate()).isFalse();
    }

    @Test
    void logsErrorWhenNoSpotsAvailable() {
        ZonedDateTime d1 = date(2025, 11, 5, 14, 0);
        Map<ZonedDateTime, Integer> spots = new HashMap<>();
        spots.put(d1, 0);
        Workshop full = new Workshop(2, "Test2", spots);

        AutoAssigner assigner = new AutoAssigner();


        AssignmentsLogger result = assigner.assign(List.of(s1), List.of(full));

        assertThat(result.getErrors()).containsExactly("Test2,Azerty");
        assertThat(result.getAssignments()).isEmpty();
    }

    @Test
    void picksEarliestAvailableDate() {
        ZonedDateTime d1 = date(2025, 11, 6, 14, 0);
        ZonedDateTime d2 = date(2025, 11, 5, 10, 0);

        Map<ZonedDateTime, Integer> spots = new HashMap<>();
        spots.put(d1, 2);
        spots.put(d2, 1);

        Workshop w = new Workshop(3, "wk", spots);

        AutoAssigner assigner = new AutoAssigner();

        AssignmentsLogger result = assigner.assign(List.of(s1), List.of(w));

        assertThat(result.getAssignments()).containsExactly(
                String.format("wk,Azerty,%s", d2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
        );

        assertThat(w.getSpotsPerDate().get(d2)).isEqualTo(0);
    }

    @Test
    void handlesMultipleWorkshopsMixedAvailability() {
        ZonedDateTime d1 = date(2025, 11, 5, 14, 0);
        ZonedDateTime d2 = date(2025, 11, 6, 14, 0);

        Workshop w1 = new Workshop(1, "w1", new HashMap<>(Map.of(d1, 1)));
        Workshop w2 = new Workshop(2, "w2", new HashMap<>(Map.of(d2, 0)));

        AutoAssigner assigner = new AutoAssigner();

        AssignmentsLogger log = assigner.assign(List.of(s1), List.of(w1, w2));

        assertThat(log.getAssignments()).containsExactly(
                String.format("w1,Azerty,%s", d1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
        );
        assertThat(log.getErrors()).containsExactly("w2,Azerty");
    }
}
