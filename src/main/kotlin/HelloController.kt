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

    @Path("getsync")
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

    @Path("qpsync")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    fun qpsync(@QueryParam("name") name: String): String {
        val span = tracer.spanBuilder("inside qpsync").startSpan()
        log.info("inside qpsync - ${span.spanContext.traceId}")

        try {
            val repeat = repeaterClient.sync(name)
            return "Hello $name $repeat - qpsync"
        } finally {
            span.end()
        }
    }

    @Path("bodysync")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    fun postsync(name: String): String {
        val span = tracer.spanBuilder("inside bodysync").startSpan()
        log.info("inside bodysync - ${span.spanContext.traceId}")

        try {
            val repeat = repeaterClient.sync(name)
            return "Hello $name $repeat - bodysync"
        } finally {
            span.end()
        }
    }

    @Path("getasync")
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

    @Path("qpasync")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    fun qpasync(@QueryParam("name") name: String): Uni<String> {
        val span = tracer.spanBuilder("inside qpasync").startSpan()
        log.info("inside qpasync - ${span.spanContext.traceId}")

        try {
            return repeaterClient.async(name).onItem()
                .transform { repeat -> "Hello $name $repeat - qpasync" } //.await().indefinitely()
        } finally {
            span.end()
        }
    }

    @Path("bodyasync")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    fun bodyasync(name: String): Uni<String> {
        val span = tracer.spanBuilder("inside bodyasync").startSpan()
        log.info("inside bodyasync - ${span.spanContext.traceId}")

        try {
            return repeaterClient.async(name).onItem()
                .transform { repeat -> "Hello $name $repeat - bodyasync" } //.await().indefinitely()
        } finally {
            span.end()
        }
    }

    @Path("getcoroutine")
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

    @Path("qpcoroutine")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    suspend fun qpcoroutine(@QueryParam("name") name: String): String {
        val span = tracer.spanBuilder("inside qpcoroutine").startSpan()
        log.info("inside qpcoroutine - ${span.spanContext.traceId}")

        try {
            val repeat = repeaterClient.coroutine(name)

            return "Hello $name $repeat - qpcoroutine"
        } finally {
            span.end()
        }
    }

    @Path("bodycoroutine")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    suspend fun bodycoroutine(name: String): String {
        val span = tracer.spanBuilder("inside bodycoroutine").startSpan()
        log.info("inside bodycoroutine - ${span.spanContext.traceId}")

        try {
            val repeat = repeaterClient.coroutine(name)

            return "Hello $name $repeat - bodycoroutine"
        } finally {
            span.end()
        }
    }
}