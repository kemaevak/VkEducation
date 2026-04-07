package io.mmaltsev.vkeducation.domain.applist

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetAppListUseCaseTest {

    private val repository = mockk<AppListRepository>()
    private val useCase = GetAppListUseCase(repository)

    @Test
    fun `invoke delegates to repository`() = runTest {
        coEvery { repository.getAppList() } returns emptyList()

        useCase()

        coVerify { repository.getAppList() }
    }

    @Test
    fun `invoke returns empty list from repository`() = runTest {
        coEvery { repository.getAppList() } returns emptyList()

        val result = useCase()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `invoke returns list from repository`() = runTest {
        val apps = listOf(
            AppSummary("1", "App1", "Desc1", "Cat1", "icon1"),
            AppSummary("2", "App2", "Desc2", "Cat2", "icon2"),
        )
        coEvery { repository.getAppList() } returns apps

        val result = useCase()

        assertEquals(apps, result)
    }

    @Test
    fun `invoke returns exact items from repository`() = runTest {
        val apps = listOf(AppSummary("1", "Name", "Desc", "Cat", "icon"))
        coEvery { repository.getAppList() } returns apps

        val result = useCase()

        assertEquals(1, result.size)
        assertEquals("1", result[0].id)
        assertEquals("Name", result[0].name)
    }

    @Test(expected = RuntimeException::class)
    fun `invoke throws when repository fails`() = runTest {
        coEvery { repository.getAppList() } throws RuntimeException("Error")

        useCase()
    }
}
