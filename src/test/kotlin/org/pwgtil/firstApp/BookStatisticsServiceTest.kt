package org.pwgtil.firstApp

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean




@ExtendWith(MockitoExtension::class)
class BookStatisticsServiceMockTest(
    @Mock val bookService: BookService
) {
    @InjectMocks
    val bookStatisticsService: BookStatisticsService? = null

    @Test
    fun shouldReturnTotalPagesByAuthor() {

        val defaultReturnValue = bookService.getBooksByAuthor("Jane Austen")

        // by default the mocked method returns an empty collection
        assertThat(defaultReturnValue).hasSize(0)

        // using when..thenReturn statement, we change the default behavior fo the mock object
        val janeAustenBooks = listOf(
            BookT(1, "Pride and Prejudice", "Jane Austen", 259),
            BookT(2, "Emma", "Jane Austen", 218)
        )

        Mockito.`when`(bookService.getBooksByAuthor("Jane Austen")).thenReturn(janeAustenBooks)

        val actual = bookStatisticsService?.getTotalPagesByAuthor("Jane Austen")
        val expected = 218 + 259
        assertThat(actual).isEqualTo(expected)
    }
}


@SpringBootTest
internal class BookStatisticsServiceContextMockTest {
    @MockBean
    private val bookService: BookService? = null

    @Autowired
    private val bookStatisticsService: BookStatisticsService? = null
    @Test
    fun shouldReturnTotalPages() {
        val defaultReturnValue: List<BookT> = bookService!!.getBooksByAuthor("Jane Austen")

        // by default the mocked method returns an empty collection
        assertThat(defaultReturnValue).hasSize(0)

        // using when..thenReturn statement, we change the default behavior of the mock object
        val janeAustenBooks = listOf(
            BookT(1, "Pride and Prejudice", "Jane Austen", 259),
            BookT(2, "Emma", "Jane Austen", 218)
        )
        Mockito.`when`(bookService.getBooksByAuthor("Jane Austen"))
            .thenReturn(janeAustenBooks)
        val actual = bookStatisticsService!!.getTotalPagesByAuthor("Jane Austen")
        val expected = 259 + 218
        assertThat(actual).isEqualTo(expected)
    }
}


class BookStatisticsServiceTest {
    private val bookService = BookService()
    private val bookStatisticsService = BookStatisticsService(bookService)

    @Test
    fun shouldReturnTotalPagesByAuthor() {
        val actual = bookStatisticsService.getTotalPagesByAuthor("Jane Austen")
        val expected = 600
        assertThat(actual).isEqualTo(expected)
    }
}

@SpringBootTest
class BookStatisticsServiceContextTest(@Autowired private val bookStatisticsService: BookStatisticsService) {
    @Test
    fun shouldReturnTotalPagesByAuthor() {
        val actual = bookStatisticsService.getTotalPagesByAuthor("Jane Austen")
        val expected = 600
        assertThat(actual).isEqualTo(expected)
    }
}