package io.mmaltsev.vkeducation.data.applist

import io.mmaltsev.vkeducation.data.appdetails.AppApi
import io.mmaltsev.vkeducation.domain.applist.AppSummary
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppListRepositoryImplTest {

    private val appApi = mockk<AppApi>()
    private val mapper = mockk<AppListMapper>()
    private val repository = AppListRepositoryImpl(appApi, mapper)

    @Test
    fun `getAppList calls api`() = runTest {
        coEvery { appApi.getAppList() } returns emptyList()

        repository.getAppList()

        coVerify { appApi.getAppList() }
    }

    @Test
    fun `getAppList returns empty list when api returns empty`() = runTest {
        coEvery { appApi.getAppList() } returns emptyList()

        val result = repository.getAppList()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAppList maps each dto to domain`() = runTest {
        val dto1 = AppSummaryDto("1", "App1", "Desc1", "Cat1", "icon1")
        val dto2 = AppSummaryDto("2", "App2", "Desc2", "Cat2", "icon2")
        val domain1 = AppSummary("1", "App1", "Desc1", "Cat1", "icon1")
        val domain2 = AppSummary("2", "App2", "Desc2", "Cat2", "icon2")

        coEvery { appApi.getAppList() } returns listOf(dto1, dto2)
        every { mapper.toDomain(dto1) } returns domain1
        every { mapper.toDomain(dto2) } returns domain2

        val result = repository.getAppList()

        assertEquals(listOf(domain1, domain2), result)
    }

    @Test
    fun `getAppList returns correct number of items`() = runTest {
        val dtos = (1..5).map { AppSummaryDto("$it", "App$it", "Desc", "Cat", "icon") }
        val domains = (1..5).map { AppSummary("$it", "App$it", "Desc", "Cat", "icon") }

        coEvery { appApi.getAppList() } returns dtos
        dtos.forEachIndexed { index, dto ->
            every { mapper.toDomain(dto) } returns domains[index]
        }

        val result = repository.getAppList()

        assertEquals(5, result.size)
    }

    @Test(expected = Exception::class)
    fun `getAppList throws when api fails`() = runTest {
        coEvery { appApi.getAppList() } throws RuntimeException("Network error")

        repository.getAppList()
    }
}
