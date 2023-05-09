package my.lovely.filesystemtesting.domain.usecase

import my.lovely.booksearcher2.data.database.HashFileDao
import my.lovely.filesystemtesting.domain.repository.FilesRepository
import javax.inject.Inject

class GetDaoDbUseCase @Inject constructor(private val filesRepository: FilesRepository) {

    fun getDaoDb(): HashFileDao {
        return filesRepository.getDaoDb()
    }

}