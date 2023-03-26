import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Alfa entity.
 */
class AlfaGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://localhost:8080"""

    val httpConf = http
        .baseUrl(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
        .silentResources // Silence all resources like css or css so they don't clutter the results

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the Alfa entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        ).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJson
        .check(header("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(2)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all alfas")
            .get("/api/alfas")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new alfa")
            .post("/api/alfas")
            .headers(headers_http_authenticated)
            .body(StringBody("""{
                "parcela":"SAMPLE_TEXT"
                , "tipoTitular":"SAMPLE_TEXT"
                , "nomeTitular":"SAMPLE_TEXT"
                , "estadoSocial":"SAMPLE_TEXT"
                , "dataNascimento":"2020-01-01T00:00:00.000Z"
                , "sexo":"SAMPLE_TEXT"
                , "documento":"SAMPLE_TEXT"
                , "numeroDocumento":"SAMPLE_TEXT"
                , "datEmissao":"SAMPLE_TEXT"
                , "localEmissao":"SAMPLE_TEXT"
                , "contactoPrincipal":"SAMPLE_TEXT"
                , "contactoAlternativo":"SAMPLE_TEXT"
                , "estadoCivil":"SAMPLE_TEXT"
                , "nomeConjugue":"SAMPLE_TEXT"
                , "distritoMunicipal":"SAMPLE_TEXT"
                , "bairro":"SAMPLE_TEXT"
                , "quatreirao":"SAMPLE_TEXT"
                , "talhao":"SAMPLE_TEXT"
                , "celula":"SAMPLE_TEXT"
                , "bloco":"SAMPLE_TEXT"
                , "avenida":"SAMPLE_TEXT"
                , "numeroPolicia":"SAMPLE_TEXT"
                , "usoActual":"SAMPLE_TEXT"
                , "formaUso":"SAMPLE_TEXT"
                , "formaObtencao":"SAMPLE_TEXT"
                , "tipo":"SAMPLE_TEXT"
                , "anoOcupacao":"0"
                , "tipoAcesso":"SAMPLE_TEXT"
                , "conflito":null
                , "detalhesConflito":null
                , "construcaoPrecaria":null
                , "pisosAcimaSoleira":"0"
                , "pisosAbaixoSoleira":"0"
                , "materialConstrucaoBarrote":null
                , "materialConstrucaoIBR":null
                , "materialConstrucaoPranchas":null
                , "materialConstrucaoPau":null
                , "materialConstrucaoCanico":null
                , "materialConstrucaoCimento":null
                , "ocupacao":"SAMPLE_TEXT"
                , "TipoContrucao":"SAMPLE_TEXT"
                , "detalhesTipoContrucao":"SAMPLE_TEXT"
                , "infraestruturaExistente":"SAMPLE_TEXT"
                , "dataLevantamento":"2020-01-01T00:00:00.000Z"
                }""")).asJson
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_alfa_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created alfa")
                .get("${new_alfa_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created alfa")
            .delete("${new_alfa_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) during (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
