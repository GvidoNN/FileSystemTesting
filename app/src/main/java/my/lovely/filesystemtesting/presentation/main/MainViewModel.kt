package my.lovely.filesystemtesting.presentation.main

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.domain.usecase.GetFilesUseCase
import my.lovely.filesystemtesting.domain.usecase.OpenFileUseCase
import my.lovely.filesystemtesting.domain.usecase.ShareFileUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFilesUseCase: GetFilesUseCase,
    private val openFileUseCase: OpenFileUseCase,
    private val shareFileUseCase: ShareFileUseCase
) : ViewModel() {

    private var filesLiveData = MutableLiveData<List<FileModel>>()

    val files: LiveData<List<FileModel>>
        get() = filesLiveData

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

}