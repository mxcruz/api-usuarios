package karate;

import com.intuit.karate.junit5.Karate;

public class KarateIT {
    @Karate.Test
    Karate runAll() {
        return Karate.run("classpath:features");
    }
}
