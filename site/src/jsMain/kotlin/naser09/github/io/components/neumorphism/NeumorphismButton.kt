package naser09.github.io.components.neumorphism

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.style.toModifier

@Composable
fun NeumorphismButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier.then(NeuMorphismButtonStyle.toModifier()),
        onClick = { onClick() }
    ) {
        content()
    }
}