package my.lovely.filesystemtesting.domain.usecase

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

class OpenFileUseCase {

    fun openFile(path: String, context: Context, type: String): Intent {
        val file = File(path)
        Log.d("MyLog",type)
        val uri = FileProvider.getUriForFile(context, "my.lovely.filesystemtesting.fileprovider", file)
        val intent = Intent(Intent.ACTION_VIEW)
        when(type){
            "txt" -> intent.setDataAndType(uri, "text/plain")
            "pdf" -> intent.setDataAndType(uri, "application/pdf")
            "png" -> intent.setDataAndType(uri, "image/*")
            "jpg" -> intent.setDataAndType(uri, "image/*")
            "doc" -> intent.setDataAndType(uri, "application/msword")
            "mp4" -> intent.setDataAndType(uri, "video/*")
            "mp3" -> intent.setDataAndType(uri, "audio/*")
            else -> {
                intent.setDataAndType(uri, "*/*")
            }
        }
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        return intent
    }
}