package my.lovely.filesystemtesting.domain.usecase

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

class ShareFileUseCase {

    fun shareFile(path: String, context: Context): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "application/pdf"
        val file = File(path)
        val uri = FileProvider.getUriForFile(
            context,
            "my.lovely.filesystemtesting.fileprovider",
            file
        )
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)

        return shareIntent
    }


}