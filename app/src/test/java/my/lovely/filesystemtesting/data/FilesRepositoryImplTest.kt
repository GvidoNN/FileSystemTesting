package my.lovely.filesystemtesting.data

import my.lovely.filesystemtesting.data.database.HashFileDao
import my.lovely.filesystemtesting.data.repository.FilesRepositoryImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class FilesRepositoryImplTest {

    private val hashFileDao = mock<HashFileDao>()
    private val filesRepositoryImpl = FilesRepositoryImpl(hashFileDao)

    @Test
    fun `get DAO in Repository`(){
        assertEquals(hashFileDao, filesRepositoryImpl.getDaoDb())
    }
}