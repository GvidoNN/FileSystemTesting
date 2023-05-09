package my.lovely.filesystemtesting.presentation.change

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.lovely.filesystemtesting.domain.model.FileHash
import my.lovely.filesystemtesting.domain.usecase.*
import javax.inject.Inject

@HiltViewModel
class ChangeViewModel @Inject constructor(
    private val getDaoDbUseCase: GetDaoDbUseCase
) : ViewModel() {

    var allFiles = getDaoDbUseCase.getDaoDb().getAllHashFiles()

}