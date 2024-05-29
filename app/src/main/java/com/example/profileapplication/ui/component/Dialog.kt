package com.example.profileapplication.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.profileapplication.R

@Composable
fun ShowMessageDialog2(
    title: String,
    content: String,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(title) },
        text = { Text(content) },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text(stringResource(id = R.string.okay))
            }
        }
    )

}