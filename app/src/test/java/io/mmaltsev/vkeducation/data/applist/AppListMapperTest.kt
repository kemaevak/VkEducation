package io.mmaltsev.vkeducation.data.applist

import org.junit.Assert.assertEquals
import org.junit.Test

class AppListMapperTest {

    private val mapper = AppListMapper()

    @Test
    fun `toDomain maps id correctly`() {
        val dto = createDto(id = "test-id-123")
        val result = mapper.toDomain(dto)
        assertEquals("test-id-123", result.id)
    }

    @Test
    fun `toDomain maps name correctly`() {
        val dto = createDto(name = "My App")
        val result = mapper.toDomain(dto)
        assertEquals("My App", result.name)
    }

    @Test
    fun `toDomain maps description correctly`() {
        val dto = createDto(description = "Some description")
        val result = mapper.toDomain(dto)
        assertEquals("Some description", result.description)
    }

    @Test
    fun `toDomain maps category correctly`() {
        val dto = createDto(category = "Games")
        val result = mapper.toDomain(dto)
        assertEquals("Games", result.category)
    }

    @Test
    fun `toDomain maps iconUrl correctly`() {
        val dto = createDto(iconUrl = "https://example.com/icon.png")
        val result = mapper.toDomain(dto)
        assertEquals("https://example.com/icon.png", result.iconUrl)
    }

    @Test
    fun `toDomain maps all fields together`() {
        val dto = AppSummaryDto(
            id = "id-1",
            name = "App Name",
            description = "Description",
            category = "Category",
            iconUrl = "https://icon.url",
        )
        val result = mapper.toDomain(dto)
        assertEquals("id-1", result.id)
        assertEquals("App Name", result.name)
        assertEquals("Description", result.description)
        assertEquals("Category", result.category)
        assertEquals("https://icon.url", result.iconUrl)
    }

    private fun createDto(
        id: String = "default-id",
        name: String = "Default Name",
        description: String = "Default Description",
        category: String = "Default Category",
        iconUrl: String = "https://default.icon",
    ) = AppSummaryDto(
        id = id,
        name = name,
        description = description,
        category = category,
        iconUrl = iconUrl,
    )
}
