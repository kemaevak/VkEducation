package io.mmaltsev.vkeducation.data.appdetails

import io.mmaltsev.vkeducation.data.appdetails.local.AppDetailsEntity
import io.mmaltsev.vkeducation.data.appdetails.local.AppDetailsEntityMapper
import io.mmaltsev.vkeducation.domain.appdetails.AppDetails
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AppDetailsEntityMapperTest {

    private val mapper = AppDetailsEntityMapper()

    @Test
    fun `toEntity maps all fields correctly`() {
        val domain = createDomain()
        val entity = mapper.toEntity(domain)
        assertEquals("id-1", entity.id)
        assertEquals("App", entity.name)
        assertEquals("Dev", entity.developer)
        assertEquals("Games", entity.category)
        assertEquals(12, entity.ageRating)
        assertEquals(100.0f, entity.size, 0.01f)
        assertEquals("https://icon.url", entity.iconUrl)
        assertEquals("Desc", entity.description)
    }

    @Test
    fun `toEntity sets screenshots to null`() {
        val domain = createDomain(screenshotUrlList = listOf("url1", "url2"))
        val entity = mapper.toEntity(domain)
        assertNull(entity.screenshots)
    }

    @Test
    fun `toEntity maps isInWishlist true`() {
        val domain = createDomain(isInWishlist = true)
        val entity = mapper.toEntity(domain)
        assertTrue(entity.isInWishlist)
    }

    @Test
    fun `toDomain maps all fields correctly`() {
        val entity = createEntity()
        val domain = mapper.toDomain(entity)
        assertEquals("id-1", domain.id)
        assertEquals("App", domain.name)
        assertEquals("Dev", domain.developer)
        assertEquals("Games", domain.category)
        assertEquals(12, domain.ageRating)
        assertEquals(100.0f, domain.size, 0.01f)
        assertEquals("https://icon.url", domain.iconUrl)
        assertEquals("Desc", domain.description)
    }

    @Test
    fun `toDomain maps screenshotUrlList as null`() {
        val entity = createEntity(screenshots = "some_data")
        val domain = mapper.toDomain(entity)
        assertNull(domain.screenshotUrlList)
    }

    @Test
    fun `toDomain maps isInWishlist correctly`() {
        val entityFalse = createEntity(isInWishlist = false)
        assertFalse(mapper.toDomain(entityFalse).isInWishlist)

        val entityTrue = createEntity(isInWishlist = true)
        assertTrue(mapper.toDomain(entityTrue).isInWishlist)
    }

    @Test
    fun `roundtrip toEntity then toDomain preserves data`() {
        val original = createDomain(isInWishlist = true)
        val result = mapper.toDomain(mapper.toEntity(original))
        assertEquals(original.id, result.id)
        assertEquals(original.name, result.name)
        assertEquals(original.developer, result.developer)
        assertEquals(original.size, result.size, 0.01f)
        assertEquals(original.isInWishlist, result.isInWishlist)
    }

    private fun createDomain(
        id: String = "id-1",
        name: String = "App",
        developer: String = "Dev",
        category: String = "Games",
        ageRating: Int = 12,
        size: Float = 100.0f,
        iconUrl: String = "https://icon.url",
        screenshotUrlList: List<String>? = null,
        description: String = "Desc",
        isInWishlist: Boolean = false,
    ) = AppDetails(
        id = id,
        name = name,
        developer = developer,
        category = category,
        ageRating = ageRating,
        size = size,
        iconUrl = iconUrl,
        screenshotUrlList = screenshotUrlList,
        description = description,
        isInWishlist = isInWishlist,
    )

    private fun createEntity(
        id: String = "id-1",
        name: String = "App",
        developer: String = "Dev",
        category: String = "Games",
        ageRating: Int = 12,
        size: Float = 100.0f,
        iconUrl: String = "https://icon.url",
        screenshots: String? = null,
        description: String = "Desc",
        isInWishlist: Boolean = false,
    ) = AppDetailsEntity(
        id = id,
        name = name,
        developer = developer,
        category = category,
        ageRating = ageRating,
        size = size,
        iconUrl = iconUrl,
        screenshots = screenshots,
        description = description,
        isInWishlist = isInWishlist,
    )
}
