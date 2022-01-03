import io.opentelemetry.api.trace.Tracer
import javax.ws.rs.core.MediaType
import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.rest.client.inject.RestClient

import org.slf4j.LoggerFactory
import javax.ws.rs.*

@Path("/hello")
class HelloController(
    private val tracer: Tracer,

    @RestClient
    private val repeaterClient: RepeaterClient
) {
    companion object  {
        private val log = LoggerFactory.getLogger(HelloController::class.java)
    }

    @Path("sync")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun getsync(@QueryParam("name") name: String): String {
        val span = tracer.spanBuilder("inside getsync").startSpan()
        log.info("inside getsync - ${span.spanContext.traceId}")

        try {
            val repeat = repeaterClient.sync(name)
            return "Hello $name $repeat - getsync"
        } finally {
            span.end()
        }
    }

    @Path("sync")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    fun postsync(name: String): String {
        val span = tracer.spanBuilder("inside postsync").startSpan()
        log.info("inside postsync - ${span.spanContext.traceId}")

        try {
            val repeat = repeaterClient.sync(name)
            return "Hello $name $repeat - postsync"
        } finally {
            span.end()
        }
    }

    @Path("async")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun getasync(@QueryParam("name") name: String): Uni<String> {
        val span = tracer.spanBuilder("inside getasync").startSpan()
        log.info("inside getasync - ${span.spanContext.traceId}")

        try {
            return repeaterClient.async(name).onItem()
                .transform { repeat -> "Hello $name $repeat - getasync" } //.await().indefinitely()
        } finally {
            span.end()
        }
    }

    @Path("async")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    fun postasync(name: String): Uni<String> {
        val span = tracer.spanBuilder("inside postasync").startSpan()
        log.info("inside postasync - ${span.spanContext.traceId}")

        try {
            return repeaterClient.async(name).onItem()
                .transform { repeat -> "Hello $name $repeat - postasync" } //.await().indefinitely()
        } finally {
            span.end()
        }
    }

    @Path("coroutine")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    suspend fun getcoroutine(@QueryParam("name") name: String): String {
        val span = tracer.spanBuilder("inside getcoroutine").startSpan()
        log.info("inside getcoroutine - ${span.spanContext.traceId}")

        try {
            val repeat = repeaterClient.coroutine(name)

            return "Hello $name $repeat - getcoroutine"
        } finally {
            span.end()
        }
    }

    @Path("coroutine")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    suspend fun postcoroutine(name: String): String {
        val span = tracer.spanBuilder("inside postcoroutine").startSpan()
        log.info("inside postcoroutine - ${span.spanContext.traceId}")

        try {
            val repeat = repeaterClient.coroutine(name)

            return "Hello $name $repeat - postcoroutine"
        } finally {
            span.end()
        }
    }
}