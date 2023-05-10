package my.lovely.filesystemtesting.domain.model

data class FileModel(
    val name: String,
    var image: Int,
    val changeDate: String,
    val size: Long,
    val path: String,
    val extension: String,
    var type: String,
    var hash: String)
//    var content: String)
