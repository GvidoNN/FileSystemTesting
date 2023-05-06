package my.lovely.filesystemtesting.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.domain.usecase.GetFilesUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFilesUseCase: GetFilesUseCase
) : ViewModel() {

//    private var progressBarLiveData = MutableLiveData<Boolean>()
    private val filesLiveData = MutableLiveData<List<FileModel>>()

    val files: LiveData<List<FileModel>>
        get() = filesLiveData

//    val progressBar: LiveData<Boolean>
//        get() = progressBarLiveData

    fun getMainFiles() = viewModelScope.launch(Dispatchers.IO) {
//        progressBarLiveData.postValue(true)
        var result = getFilesUseCase.getMainFiles()
        filesLiveData.postValue(result)
//        progressBarLiveData.postValue(false)
    }

}