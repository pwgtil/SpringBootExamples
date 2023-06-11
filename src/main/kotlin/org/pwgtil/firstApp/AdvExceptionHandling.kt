package org.pwgtil.firstApp


import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.LinkedHashMap

class FlightInfo(@field:Min(1) var id: Long, var from: String, var to: String, var gate: String)

class FlightNotFoundException(message: String) : RuntimeException(message)

class CustomErrorMessage(
    val statusCode: Int,
    val timestamp: LocalDateTime,
    val message: String?,
    val description: String
)

@ControllerAdvice
class ControllerExceptionHandler : ResponseEntityExceptionHandler(){
    @ExceptionHandler(FlightNotFoundException::class)
    fun handleFlightNotFound(e: FlightNotFoundException, request: WebRequest): ResponseEntity<CustomErrorMessage> {
        val body = CustomErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now(),
            e.message,
            request.getDescription(false)
        )
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        // just like a POJO, a Map is also converted to a JSON key-value structure
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["status"] = status.value()
        body["timestamp"] = LocalDateTime.now()
        body["exception"] = ex.javaClass
        body["customMessage"] = "This is a test message"

        return ResponseEntity(body, headers, status)
    }

}

@RestController
class FlightController {
    private val flightInfoList = Collections.synchronizedList(ArrayList<FlightInfo>())

    init {
        flightInfoList.add(
            FlightInfo(
                1,
                "Delhi Indira Gandhi",
                "Stuttgart",
                "D80"
            )
        )
        flightInfoList.add(
            FlightInfo(
                2,
                "Tokyo Haneda",
                "Frankfurt",
                "110"
            )
        )
    }

    @GetMapping("flights/{id}")
    fun getFlightInfo(@PathVariable id: Long): FlightInfo {
        for (flightInfo in flightInfoList) {
            if (flightInfo.id == id) {
                return flightInfo
            }
        }
        throw FlightNotFoundException("Flight info not found id=$id")
    }

    @PostMapping("/flights/new")
    fun addNewFlightInfo(@Valid @RequestBody flightInfo: FlightInfo) {
        flightInfoList.add(flightInfo)
    }

//    // alternative to ControllerAdvice class. This can be even in a class that throws exceptions!!!!!!!
//    @ExceptionHandler(FlightNotFoundException::class)
//    fun handleFlightNotFound(e: FlightNotFoundException, request: WebRequest): ResponseEntity<CustomErrorMessage> {
//        val body = CustomErrorMessage(
//            HttpStatus.NOT_FOUND.value(),
//            LocalDateTime.now(),
//            e.message,
//            request.getDescription(false)
//        )
//        return ResponseEntity(body,HttpStatus.NOT_FOUND)
//    }

}