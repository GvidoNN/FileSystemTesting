package my.lovely.filesystemtesting.presentation.second

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
class SecondViewModel @Inject constructor(
    private val getFilesUseCase: GetFilesUseCase
) : ViewModel() {

    private var filesSecondLiveData = MutableLiveData<List<FileModel>>()

    val filesSecond: LiveData<List<FileModel>>
        get() = filesSecondLiveData

    fun getSecondFiles(path: String) = viewModelScope.launch(Dispatchers.IO) {
        var result = getFilesUseCase.getMainFiles(path = path)
        filesSecondLiveData.postValue(result)
    }

}