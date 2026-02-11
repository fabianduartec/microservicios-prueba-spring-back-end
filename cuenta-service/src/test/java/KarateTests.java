import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KarateTests {

    @Karate.Test
    @Order(1)
    Karate testCuentas() {
        return Karate.run("cuentas").relativeTo(getClass());
    }

    @Karate.Test
    @Order(2)
    Karate testMovimientos() {
        return Karate.run("movimientos").relativeTo(getClass());
    }

    @Karate.Test
    @Order(3)
    Karate testReportes() {
        return Karate.run("reportes").relativeTo(getClass());
    }
}
