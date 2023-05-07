package my.lovely.filesystemtesting.domain.model

data class FileModel(val name: String,
                     var image: Int,
                     val changeDate: String,
                     val size: String,
                     val path: String,
                     var type: String)
