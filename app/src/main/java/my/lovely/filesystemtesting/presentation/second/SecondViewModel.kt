package my.lovely.filesystemtesting.presentation.second

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
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    private val getFilesUseCase: GetFilesUseCase,
    private val openFileUseCase: OpenFileUseCase
) : ViewModel() {

    private var filesSecondLiveData = MutableLiveData<List<FileModel>>()

    val filesSecond: LiveData<List<FileModel>>
        get() = filesSecondLiveData

    fun getSecondFiles(path: String, typeSorted: Int) = viewModelScope.launch(Dispatchers.IO) {
        var result = getFilesUseCase.getMainFiles(path = path, typeSorted = typeSorted)
        filesSecondLiveData.postValue(result)
    }

    fun openFile(path: String, context: Context, type: String): Intent {
        var intent = openFileUseCase.openFile(path = path, context = context, type = type)
        return intent

    }

}