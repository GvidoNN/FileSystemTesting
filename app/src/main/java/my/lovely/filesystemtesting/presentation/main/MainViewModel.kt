package my.lovely.filesystemtesting.presentation.main

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
    private var filesLiveData = MutableLiveData<List<FileModel>>()

    var filesBackLiveData = MutableLiveData<List<FileModel>>()

    val files: LiveData<List<FileModel>>
        get() = filesLiveData

//    val progressBar: LiveData<Boolean>
//        get() = progressBarLiveData

    fun getMainFiles(path: String) = viewModelScope.launch(Dispatchers.IO) {
//        progressBarLiveData.postValue(true)
        var result = getFilesUseCase.getMainFiles(path = path)
        filesLiveData.postValue(result)
//        progressBarLiveData.postValue(false)
    }

}