package my.lovely.filesystemtesting.domain.usecase

import android.util.Log
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.domain.repository.FilesRepository
import javax.inject.Inject

class GetFilesUseCase @Inject constructor(private val filesRepository: FilesRepository) {

    suspend fun getMainFiles(path: String): List<FileModel> {
        val result = filesRepository.getMainFiles(path = path).sortedBy { it.name }
        for(i in result){
            when {
                i.name.endsWith(".txt") -> {
                    i.type = "txt"
                    i.image = R.drawable.txt
                }
                i.name.endsWith(".pdf") -> {
                    i.type = "pdf"
                    i.image = R.drawable.pdf
                }
                i.name.endsWith(".png") -> {
                    i.type = "png"
                    i.image = R.drawable.png
                }
                i.name.endsWith(".jpg") or i.name.endsWith(".jpeg") -> {
                    i.type = "jpg"
                    i.image = R.drawable.jpeg
                }
                i.name.endsWith(".doc") or  i.name.endsWith(".docx") -> {
                    i.type = "doc"
                    i.image = R.drawable.doc
                }
                i.name.endsWith(".mov") or  i.name.endsWith(".mp4") -> {
                    i.type = "mp4"
                    i.image = R.drawable.mp4
                }
                i.name.endsWith(".mp3")-> {
                    i.type = "mp3"
                    i.image = R.drawable.mp3
                }
                i.type == "directory" -> {
                    i.type = "directory"
                    i.image = R.drawable.directory
                }
                else -> {
                    i.type = "none"
                    i.image = R.drawable.file
                }

            }
        }
        return result
    }

}