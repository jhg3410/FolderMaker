import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun MKDialog(title: String, message: String = "", onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(size = 8.dp))
                .padding(20.dp)
        ) {
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = message)
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = onDismiss
                    ) {
                        Text("확인")
                    }
                }
            }
        }
    }
}