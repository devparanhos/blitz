package br.com.detran.blitz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import br.com.detran.blitz.presentation.navigation.manager.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            this.window.statusBarColor = ContextCompat.getColor(this, R.color.primary)

            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                Navigation()
            }
        }
    }
}