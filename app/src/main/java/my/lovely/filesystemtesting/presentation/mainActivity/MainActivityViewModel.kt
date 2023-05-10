package my.lovely.filesystemtesting.presentation.mainActivity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.lovely.filesystemtesting.domain.model.FileHash
import my.lovely.filesystemtesting.domain.usecase.GetDaoDbUseCase
import my.lovely.filesystemtesting.domain.usecase.GetFilesUseCase
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getFilesUseCase: GetFilesUseCase,
    private val getDaoDbUseCase: GetDaoDbUseCase,
) : ViewModel() {

    fun saveAllFiles() = viewModelScope.launch(Dispatchers.IO) {
        var result = getFilesUseCase.getAllFiles()
        for(i in result){
            getDaoDbUseCase.getDaoDb().insertHashFile(
                FileHash(
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
    }
}