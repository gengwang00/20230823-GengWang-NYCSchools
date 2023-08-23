package nyc.nycschool.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import nyc.nycschool.R
import nyc.nycschool.app.ERROR_MESSAGE
import nyc.nycschool.network.model.SchoolModel
import nyc.nycschool.ui.component.DisplayLoadingSpinner
import nyc.nycschool.ui.component.DisplayMessage
import nyc.nycschool.viewmodel.NycSchoolViewModel

@Composable
fun NycSchool(navController: NavHostController, viewModel: NycSchoolViewModel) {
    if (viewModel.loading) {
        DisplayLoadingSpinner()
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (viewModel.apiCallError) {
                DisplayMessage(ERROR_MESSAGE)
            } else {
                DisplayList(navController, viewModel.snapshotStateList, viewModel)
            }
        }
    }
}

@Composable
fun DisplayList(
    navController: NavHostController,
    aList: SnapshotStateList<SchoolModel>,
    viewModel: NycSchoolViewModel
) {
    Column {
        LazyColumn(
            Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        ) {
            items(
                items = aList,
                itemContent = {
                    DisplayOneSchool(navController, it, viewModel)
                })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DisplayOneSchool(
    navHostController: NavHostController,
    schoolModel: SchoolModel,
    viewModel: NycSchoolViewModel
) {
    Card(modifier = Modifier
        .padding(8.dp),
        onClick = {
            viewModel.getSATScore(schoolModel.id)
            navHostController.navigate("itemDetailsScreen/{schoolModel.id}")
        }) {
        Row {
            // Should display mascot of each school. For this challenge, hardcoded one mascot
            Image(
                painter = painterResource(id = R.drawable.mascot),
                contentDescription = "",
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(70.dp)
                    .padding(8.dp)
            )

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f), text = schoolModel.title
            )
        }
    }
}