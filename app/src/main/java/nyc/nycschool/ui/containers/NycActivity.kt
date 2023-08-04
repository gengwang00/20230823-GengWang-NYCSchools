package nyc.nycschool.ui.containers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import nyc.nycschool.R
import nyc.nycschool.app.theme.NycTheme
import nyc.nycschool.ui.component.ToolbarWidget
import nyc.nycschool.ui.layout.NycSchool
import nyc.nycschool.ui.navigation.NavigationSetup
import nyc.nycschool.viewmodel.NycSchoolViewModel

@AndroidEntryPoint
class NycActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nycViewModel: NycSchoolViewModel by viewModels()
        setContent {
            NycTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ToolbarWidget(getString(R.string.app_name)) {
                        NavigationSetup(nycViewModel)
                    }
                }
            }
        }
        lifecycle.addObserver(nycViewModel)
    }
}

