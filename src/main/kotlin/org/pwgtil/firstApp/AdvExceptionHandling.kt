package org.pwgtil.firstApp

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime
import java.util.*

class FlightInfo(var id: Long, var from: String, var to: String, var gate: String)

class FlightNotFoundException(message: String) : RuntimeException(message)

class CustomErrorMessage(
    val statusCode: Int,
    val timestamp: LocalDateTime,
    val message: String?,
    val description: String
)

@ControllerAdvice
class ControllerExceptionHandler {
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