package my.lovely.filesystemtesting.presentation.main

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.lovely.filesystemtesting.domain.model.FileHash
import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.domain.usecase.*
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFilesUseCase: GetFilesUseCase,
    private val openFileUseCase: OpenFileUseCase,
    private val shareFileUseCase: ShareFileUseCase,
    private val getDaoDbUseCase: GetDaoDbUseCase,
    private val getAllFilesUseCase: GetAllFilesUseCase
) : ViewModel() {

    private var filesLiveData = MutableLiveData<List<FileModel>>()
    private var allFilesLiveData = MutableLiveData<List<FileModel>>()

    val files: LiveData<List<FileModel>>
        get() = filesLiveData

    val allFiles: LiveData<List<FileModel>>
        get() = allFilesLiveData

    fun getMainFiles(path: String, sorted: Int) = viewModelScope.launch(Dispatchers.IO) {
        var result = getFilesUseCase.getMainFiles(path = path, typeSorted = sorted)
        filesLiveData.postValue(result)
    }
    fun openFile(path: String, context: Context, type: String): Intent {
        var intent = openFileUseCase.openFile(path = path, context = context, type = type)
        return intent
    }

    fun shareFile(path: String, context: Context): Intent {
        var shareIntent = shareFileUseCase.shareFile(path = path, context = context)
        return shareIntent
    }

    fun saveAllFiles() = viewModelScope.launch(Dispatchers.IO) {
        var result = getFilesUseCase.getAllFiles()
        for(i in result){
            getDaoDbUseCase.getDaoDb().insertHashFile(
                FileHash(
                    id = 0,
                    filePath = i.path,
                    fileHash = i.hash,
                    name = i.name,
                    image = i.image,
                    changeDate = i.changeDate,
                    size = i.size,
                    extension = i.extension,
                    type = i.type
                )
            )
        }
        Log.d("MyLog","Database added")
        allFilesLiveData.postValue(result)
    }

}