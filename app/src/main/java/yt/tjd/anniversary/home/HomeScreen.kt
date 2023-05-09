@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package yt.tjd.anniversary.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen() {

  val viewModel: HomeScreenViewModel = viewModel()
  val state by viewModel.state.collectAsState()

  Scaffold {
    LazyVerticalStaggeredGrid(
      modifier = Modifier
        .padding(it)
        .fillMaxSize(),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalItemSpacing = 12.dp,
      contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
      columns = StaggeredGridCells.Adaptive(150.dp)
    ) {

      items(state) {
        var expanded by rememberSaveable { mutableStateOf(false) }

        Card(modifier = Modifier.fillMaxWidth(), onClick = { expanded = !expanded }) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(text = it.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

            val rollUp by remember(it) {
              derivedStateOf {
                if (it.monthsLeft > 0) RollUp.MONTH
                else if (it.weeksLeft > 0) RollUp.WEEK
                else if (it.daysLeft > 0) RollUp.DAY
                else if (it.hoursLeft > 0) RollUp.HOUR
                else if (it.minutesLeft > 0) RollUp.MINUTE
                else RollUp.SECOND
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
              visible = rollUp == RollUp.MONTH || expanded,
              enter = expandVertically(expandFrom = Alignment.Top),
              exit = shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
              Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${it.monthsLeft} Months")
              }
            }

            AnimatedVisibility(
              visible = rollUp == RollUp.WEEK || expanded,
              enter = expandVertically(expandFrom = Alignment.Top),
              exit = shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
              Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${it.weeksLeft} Weeks")
              }
            }

            AnimatedVisibility(
              visible = rollUp == RollUp.DAY || expanded,
              enter = expandVertically(expandFrom = Alignment.Top),
              exit = shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
              Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${it.daysLeft} Days")
              }
            }

            AnimatedVisibility(
              visible = rollUp == RollUp.HOUR || expanded,
              enter = expandVertically(expandFrom = Alignment.Top),
              exit = shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
              Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${it.hoursLeft} Hours")
              }
            }

            AnimatedVisibility(
              visible = rollUp == RollUp.MINUTE || expanded,
              enter = expandVertically(expandFrom = Alignment.Top),
              exit = shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
              Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${it.minutesLeft} Minutes")
              }
            }

            AnimatedVisibility(
              visible = rollUp == RollUp.SECOND || expanded,
              enter = expandVertically(expandFrom = Alignment.Top),
              exit = shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
              Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${it.secondsLeft} Seconds")
              }
            }
          }
        }
      }
    }
  }
}

enum class RollUp {
  MONTH,
  WEEK,
  DAY,
  HOUR,
  MINUTE,
  SECOND
}