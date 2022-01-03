import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.POST
import javax.ws.rs.QueryParam

@Path("/repeat")
@RegisterRestClient(configKey = "repeater-api")
interface RepeaterClient {
    @GET
    fun sync(@QueryParam("repeat") repeat: String): String

    @GET
    fun async(@QueryParam("repeat") repeat: String): Uni<String>

    @GET
    suspend fun coroutine(@QueryParam("repeat") repeat: String): String
}