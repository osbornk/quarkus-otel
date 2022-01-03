import io.opentelemetry.api.trace.Tracer
import org.slf4j.LoggerFactory
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Path("repeat")
class RepeaterController(
    private val tracer: Tracer
) {
    companion object  {
        private val log = LoggerFactory.getLogger(RepeaterController::class.java)
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun repeat(@QueryParam("repeat") repeat: String): String {
        val span = tracer.spanBuilder("inside repeat").startSpan()
        log.info("inside repeat - ${span.spanContext.traceId}")

        try {
            return repeat
        } finally {
            span.end()
        }
    }
}