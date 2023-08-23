package nyc.nycschool.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nyc.nycschool.app.ERROR_MESSAGE_SAT
import nyc.nycschool.ui.component.DisplayLoadingSpinner
import nyc.nycschool.ui.component.DisplayMessage
import nyc.nycschool.viewmodel.NycSchoolViewModel

@Composable
fun NycSchoolSat(
    viewModel: NycSchoolViewModel
) {
    if (viewModel.loading) {
        DisplayLoadingSpinner()
    } else {
        if(viewModel.apiCallError){
            DisplayMessage(message = ERROR_MESSAGE_SAT)
        } else {
            DisplaySatScores(viewModel = viewModel)
        }
    }
}

@Composable
fun DisplaySatScores(viewModel: NycSchoolViewModel) {

    Column() {
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Text(text = "SAT Scores",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            fontSize = 30.sp)
        }

        Text(text = viewModel.satModel?.schoolName ?: "",
        modifier = Modifier.padding(10.dp))

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(text = "Average Reading Score",
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
                modifier = Modifier.padding(10.dp)
            )
            Text(text = viewModel.satModel?.readingScore ?: "",
            modifier = Modifier.padding(10.dp))
        }

        Row() {
            Text(text = "Average Math Score  ",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.padding(10.dp)
            )
            Text(text = viewModel.satModel?.mathScore ?: "",
                modifier = Modifier.padding(10.dp))
        }

        Row() {
            Text(text = "Average Writing Score",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.padding(10.dp)
            )
            Text(text = viewModel.satModel?.writingScore ?: "",
                modifier = Modifier.padding(10.dp))
        }
    }
}