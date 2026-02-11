import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KarateTests {
    @Karate.Test
    Karate clientes() {
        return Karate.run("clientes").relativeTo(getClass());
    }
}
