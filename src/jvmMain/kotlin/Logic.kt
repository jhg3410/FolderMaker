import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.nio.file.Files
import java.nio.file.Paths

object BusinessLogic {
    fun makeFolders(start: Int, end: Int, path: String, suffix: String) {
        if (start >= end) {
            CoroutineScope(Dispatchers.IO).launch {
                for (number in start downTo end) {
                    makeFolder(number = number, path = path, suffix = suffix)
                }
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                for (number in start..end) {
                    makeFolder(number = number, path = path, suffix = suffix)
                }
            }
        }
    }

    private suspend fun makeFolder(number: Int, path: String, suffix: String) {
        coroutineScope {
            launch {
                Files.createDirectory(Paths.get("$path/$number$suffix"))
            }.join()
        }
    }
}