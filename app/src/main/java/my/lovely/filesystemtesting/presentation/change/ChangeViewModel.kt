package my.lovely.filesystemtesting.presentation.change

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.domain.model.FileHash
import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.domain.usecase.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChangeViewModel @Inject constructor(
    private val getDaoDbUseCase: GetDaoDbUseCase
) : ViewModel() {

    private var changedFilesLiveData = MutableLiveData<List<FileModel>>()

    val files: LiveData<List<FileModel>>
        get() = changedFilesLiveData

    var allFiles = getDaoDbUseCase.getDaoDb().getAllHashFiles()

    fun changedFiles(list: List<FileHash>){
        val changedFiles = arrayListOf<FileModel>()
        for (fileDB in list) {
            val file = File(fileDB.filePath)
            val hash = file.hashCode().toString()
            val size = file.length()

            if (fileDB.fileHash != hash || fileDB.size != size) {
                changedFiles.add(FileModel(name = file.name,
                    changeDate = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(file.lastModified())).toString(),
                    size = file.length(),
                    image = R.drawable.file,
                    path = file.path,
                    extension = file.extension,
                    type = "file",
                    hash = hash))
            }
        }
        changedFilesLiveData.postValue(changedFiles)
    }

}