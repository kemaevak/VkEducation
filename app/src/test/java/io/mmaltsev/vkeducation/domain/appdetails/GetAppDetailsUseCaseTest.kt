package io.mmaltsev.vkeducation.domain.appdetails

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetAppDetailsUseCaseTest {

    private val repository = mockk<AppDetailsRepository>()
    private val useCase = GetAppDetailsUseCase(repository)

    @Test
    fun `invoke delegates to repository with correct id`() = runTest {
        coEvery { repository.getAppDetails("id-1") } returns flowOf(createDomain())

        useCase("id-1")

        coVerify { repository.getAppDetails("id-1") }
    }

    @Test
    fun `invoke returns flow with app details`() = runTest {
        val domain = createDomain()
        coEvery { repository.getAppDetails("id-1") } returns flowOf(domain)

        useCase("id-1").test {
            assertEquals(domain, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns correct app name`() = runTest {
        val domain = createDomain(name = "My App")
        coEvery { repository.getAppDetails("id-1") } returns flowOf(domain)

        useCase("id-1").test {
            assertEquals("My App", awaitItem().name)
            awaitComplete()
        }
    }

    @Test
    fun `invoke passes different ids to repository`() = runTest {
        coEvery { repository.getAppDetails("id-2") } returns flowOf(createDomain(id = "id-2"))

        useCase("id-2")

        coVerify { repository.getAppDetails("id-2") }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke throws when repository fails`() = runTest {
        coEvery { repository.getAppDetails(any()) } throws RuntimeException("Error")

        useCase("id-1")
    }

    private fun createDomain(
        id: String = "id-1",
        name: String = "App",
    ) = AppDetails(
        id = id, name = name, developer = "Dev", category = "Games",
        ageRating = 12, size = 100f, iconUrl = "icon", screenshotUrlList = null,
        description = "Desc",
    )
}
