import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Paths

object BusinessLogic {
    suspend fun makeFolders(start: Int, end: Int, path: String, suffix: String): Boolean {
        var result = true
        if (start >= end) {
            for (number in start downTo end) {
                result = makeFolder(number = number, path = path, suffix = suffix)
            }
        } else {
            for (number in start..end) {
                result = makeFolder(number = number, path = path, suffix = suffix)
            }
        }

        return result
    }

    private suspend fun makeFolder(number: Int, path: String, suffix: String): Boolean {
        var result = true
        try {
            withContext(Dispatchers.IO) {
                Files.createDirectory(Paths.get("$path/$number$suffix"))
            }
        } catch (e: Exception) {
            result = false
        }
        return result
    }
}