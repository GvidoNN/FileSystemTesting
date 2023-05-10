package my.lovely.filesystemtesting.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hash_file_data_table")
data class FileHash(
    @PrimaryKey()
    @ColumnInfo(name = "path")
    var filePath: String,
    @ColumnInfo(name = "hash")
    var fileHash: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "image")
    var image: Int,
    @ColumnInfo(name = "change_date")
    val changeDate: String,
    @ColumnInfo(name = "size")
    val size: Long,
    @ColumnInfo(name = "extension")
    val extension: String,
    @ColumnInfo(name = "type")
    var type: String
//    @ColumnInfo(name = "content")
//    var content: String
)
