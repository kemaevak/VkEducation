package io.mmaltsev.vkeducation.data.appdetails

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AppDetailsMapperTest {

    private val mapper = AppDetailsMapper()

    @Test
    fun `toDomain maps basic fields correctly`() {
        val dto = createDto()
        val result = mapper.toDomain(dto)
        assertEquals("id-1", result.id)
        assertEquals("App Name", result.name)
        assertEquals("Dev", result.developer)
        assertEquals("Games", result.category)
    }

    @Test
    fun `toDomain converts size from Double to Float`() {
        val dto = createDto(size = 223.7)
        val result = mapper.toDomain(dto)
        assertEquals(223.7f, result.size, 0.01f)
    }

    @Test
    fun `toDomain maps ageRating correctly`() {
        val dto = createDto(ageRating = 18)
        val result = mapper.toDomain(dto)
        assertEquals(18, result.ageRating)
    }

    @Test
    fun `toDomain maps screenshotUrlList correctly`() {
        val screenshots = listOf("url1", "url2", "url3")
        val dto = createDto(screenshotUrlList = screenshots)
        val result = mapper.toDomain(dto)
        assertEquals(screenshots, result.screenshotUrlList)
    }

    @Test
    fun `toDomain maps null screenshotUrlList`() {
        val dto = createDto(screenshotUrlList = null)
        val result = mapper.toDomain(dto)
        assertNull(result.screenshotUrlList)
    }

    @Test
    fun `toDomain maps description correctly`() {
        val dto = createDto(description = "Long description text here")
        val result = mapper.toDomain(dto)
        assertEquals("Long description text here", result.description)
    }

    private fun createDto(
        id: String = "id-1",
        name: String = "App Name",
        developer: String = "Dev",
        category: String = "Games",
        ageRating: Int = 12,
        size: Double = 100.0,
        iconUrl: String = "https://icon.url",
        screenshotUrlList: List<String>? = null,
        description: String = "Description",
    ) = AppDetailsDto(
        id = id,
        name = name,
        developer = developer,
        category = category,
        ageRating = ageRating,
        size = size,
        iconUrl = iconUrl,
        screenshotUrlList = screenshotUrlList,
        description = description,
    )
}
