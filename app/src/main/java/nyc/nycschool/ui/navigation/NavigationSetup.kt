package nyc.nycschool.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nyc.nycschool.ui.layout.NycSchool
import nyc.nycschool.ui.layout.NycSchoolSat
import nyc.nycschool.viewmodel.NycSchoolViewModel

@Composable
fun NavigationSetup(nycViewModel: NycSchoolViewModel) {

    val myVM = hiltViewModel<NycSchoolViewModel>()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainScreen") {
        composable("mainScreen") {
            NycSchool(navController, myVM)
        }
        composable(
            "itemDetailsScreen/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            // itemId is not used in this challenge. But left here to show how to pass parameter between screens
            val itemId = backStackEntry.arguments?.getString("itemId")
            itemId?.let {
                NycSchoolSat(myVM)
            }
        }
    }
}
