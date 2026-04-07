package io.mmaltsev.vkeducation.data.appdetails

import app.cash.turbine.test
import io.mmaltsev.vkeducation.data.appdetails.local.AppDetailsDao
import io.mmaltsev.vkeducation.data.appdetails.local.AppDetailsEntity
import io.mmaltsev.vkeducation.data.appdetails.local.AppDetailsEntityMapper
import io.mmaltsev.vkeducation.domain.appdetails.AppDetails
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppDetailsRepositoryImplTest {

    private val appApi = mockk<AppApi>()
    private val dao = mockk<AppDetailsDao>(relaxed = true)
    private val mapper = mockk<AppDetailsMapper>()
    private val entityMapper = mockk<AppDetailsEntityMapper>()
    private val repository = AppDetailsRepositoryImpl(appApi, dao, mapper, entityMapper)

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAppDetails returns cached data when entity exists`() = runTest {
        val entity = createEntity()
        val domain = createDomain()

        every { dao.getAppDetails("id-1") } returns flowOf(entity)
        every { entityMapper.toDomain(entity) } returns domain

        repository.getAppDetails("id-1").test {
            assertEquals(domain, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getAppDetails fetches from api when entity is null`() = runTest {
        val dto = createDto()
        val domain = createDomain()
        val entity = createEntity()

        every { dao.getAppDetails("id-1") } returns flowOf(null)
        coEvery { appApi.getAppDetails("id-1") } returns dto
        every { mapper.toDomain(dto) } returns domain
        every { entityMapper.toEntity(domain) } returns entity

        repository.getAppDetails("id-1").test {
            assertEquals(domain, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getAppDetails saves to database when fetched from api`() = runTest {
        val dto = createDto()
        val domain = createDomain()
        val entity = createEntity()

        every { dao.getAppDetails("id-1") } returns flowOf(null)
        coEvery { appApi.getAppDetails("id-1") } returns dto
        every { mapper.toDomain(dto) } returns domain
        every { entityMapper.toEntity(domain) } returns entity

        repository.getAppDetails("id-1").test {
            awaitItem()
            awaitComplete()
        }

        coVerify { dao.insertAppDetails(entity) }
    }

    @Test
    fun `observeAppDetails emits domain when entity exists`() = runTest {
        val entity = createEntity()
        val domain = createDomain()

        every { dao.getAppDetails("id-1") } returns flowOf(entity)
        every { entityMapper.toDomain(entity) } returns domain

        repository.observeAppDetails("id-1").test {
            assertEquals(domain, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `observeAppDetails skips null entities`() = runTest {
        every { dao.getAppDetails("id-1") } returns flowOf(null)

        repository.observeAppDetails("id-1").test {
            awaitComplete()
        }
    }

    @Test
    fun `toggleWishlist flips wishlist status`() = runTest {
        val entity = createEntity(isInWishlist = false)
        every { dao.getAppDetails("id-1") } returns flowOf(entity)

        repository.toggleWishlist("id-1")

        coVerify { dao.updateWishlistStatus("id-1", true) }
    }

    @Test
    fun `toggleWishlist does nothing when entity is null`() = runTest {
        every { dao.getAppDetails("id-1") } returns flowOf(null)

        repository.toggleWishlist("id-1")

        coVerify(exactly = 0) { dao.updateWishlistStatus(any(), any()) }
    }

    private fun createEntity(
        id: String = "id-1",
        isInWishlist: Boolean = false,
    ) = AppDetailsEntity(
        id = id, name = "App", developer = "Dev", category = "Games",
        ageRating = 12, size = 100f, iconUrl = "icon", screenshots = null,
        description = "Desc", isInWishlist = isInWishlist,
    )

    private fun createDomain(
        id: String = "id-1",
        isInWishlist: Boolean = false,
    ) = AppDetails(
        id = id, name = "App", developer = "Dev", category = "Games",
        ageRating = 12, size = 100f, iconUrl = "icon", screenshotUrlList = null,
        description = "Desc", isInWishlist = isInWishlist,
    )

    private fun createDto(id: String = "id-1") = AppDetailsDto(
        id = id, name = "App", developer = "Dev", category = "Games",
        ageRating = 12, size = 100.0, iconUrl = "icon", screenshotUrlList = null,
        description = "Desc",
    )
}
