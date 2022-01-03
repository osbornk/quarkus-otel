import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class HelloControllerTest {

    @Test
    fun testSyncHelloEndpoint() {
        given()
            .contentType("application/json")
            .body("fred")
            .`when`().post("/hello/sync")
            .then()
            .statusCode(200)
            .body(`is`("Hello fred fred - sync"))
    }

}