import BusinessLogic.makeFolders
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    var startNumber: String by remember { mutableStateOf("") }
    var endNumber: String by remember { mutableStateOf("") }
    var path: String by remember { mutableStateOf("") }
    var suffix: String by remember { mutableStateOf("번") }
    val focusManager = LocalFocusManager.current
    var showDirectoryPicker by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }
    var resultDialogTitle = ""
    var resultDialogMessage = ""

    MKTheme {
        DirectoryPicker(show = showDirectoryPicker, title = "경로 선택") {
            path = it ?: path
            showDirectoryPicker = false
        }

        if (showResultDialog) {
            MKDialog(title = resultDialogTitle, message = resultDialogMessage, onDismiss = { showResultDialog = false })
        }

        Column(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { focusManager.clearFocus() }
                )
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically)
        ) {
            Text(
                buildAnnotatedString {
                    append("Folder")
                    withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                        append("Maker")
                    }
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    label = { Text("시작 번호", fontSize = 11.sp) },
                    modifier = Modifier.weight(1f),
                    value = startNumber,
                    onValueChange = { value ->
                        if (value.isEmpty()) {
                            startNumber = value
                        } else {
                            value.toIntOrNull()?.let {
                                startNumber = value
                            }
                        }
                    },
                )
                Text("~")
                OutlinedTextField(
                    label = { Text("끝 번호", fontSize = 11.sp) },
                    modifier = Modifier.weight(1f),
                    value = endNumber,
                    onValueChange = { value ->
                        if (value.isEmpty()) {
                            endNumber = value
                        } else {
                            value.toIntOrNull()?.let {
                                endNumber = value
                            }
                        }
                    },
                )
                OutlinedTextField(
                    label = { Text("Suffix") },
                    modifier = Modifier.weight(0.7f),
                    value = suffix,
                    onValueChange = {
                        suffix = it
                    }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { showDirectoryPicker = true }
                ) {
                    Text("경로 선택")
                }
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(text = "선택된 경로: ")
                        }
                        append(path)
                    }
                )
            }
            Button(
                enabled = startNumber != "" && endNumber != "" && path != "",
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val result = makeFolders(
                            start = startNumber.toIntOrNull() ?: throw IllegalArgumentException(ERROR_MESSAGE),
                            end = endNumber.toIntOrNull() ?: throw IllegalArgumentException(ERROR_MESSAGE),
                            path = path,
                            suffix = suffix
                        )
                        if (result) {
                            resultDialogTitle = "성공"
                            resultDialogMessage = ""
                        } else {
                            resultDialogTitle = "실패"
                            resultDialogMessage = "해당 경로에 동일한 이름의 폴더가 있으면 안됩니다"
                        }
                        showResultDialog = true
                    }
                }) {
                Text("MAKE!")
            }
        }
    }
}

fun main() = application {
    val state = rememberWindowState(
        position = WindowPosition.Aligned(Alignment.Center),
        size = DpSize(width = 400.dp, height = 320.dp)
    )
    Window(
        title = TITLE,
        state = state,
        onCloseRequest = ::exitApplication,
        icon = painterResource("green_folder.ico")
    ) {
        App()
    }
}
