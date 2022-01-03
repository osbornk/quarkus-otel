import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.Path
import javax.ws.rs.POST

@Path("/repeat")
@RegisterRestClient(configKey = "repeater-api")
interface RepeaterClient {
    @POST
    fun sync(repeat: String): String

    @POST
    fun async(repeat: String): Uni<String>

    @POST
    suspend fun coroutine(repeat: String): String
}