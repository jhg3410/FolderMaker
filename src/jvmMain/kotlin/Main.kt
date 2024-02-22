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
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker

@Composable
@Preview
fun App() {
    var startNumber: String by remember { mutableStateOf("") }
    var endNumber: String by remember { mutableStateOf("") }
    var path: String by remember { mutableStateOf("") }
    var suffix: String by remember { mutableStateOf("번") }
    val focusManager = LocalFocusManager.current
    var fileDialogVisible by remember { mutableStateOf(false) }

    MKTheme {
        DirectoryPicker(show = fileDialogVisible, title = "경로 선택") {
            path = it ?: ""
            fileDialogVisible = false
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
                    append("Make")
                    withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                        append("Folder")
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
                    onClick = { fileDialogVisible = true }
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
                    makeFolders(
                        start = startNumber.toIntOrNull() ?: throw IllegalArgumentException(ERROR_MESSAGE),
                        end = endNumber.toIntOrNull() ?: throw IllegalArgumentException(ERROR_MESSAGE),
                        path = path,
                        suffix = suffix
                    )
                }) {
                Text("MAKE!")
            }
        }
    }
}

fun main() = application {
    val state = rememberWindowState(
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
