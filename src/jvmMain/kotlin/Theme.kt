import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val primaryColor = Color(0xFF02ad08)

@Composable
internal fun MKTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = primaryColor)
    ) {
        content()
    }
}