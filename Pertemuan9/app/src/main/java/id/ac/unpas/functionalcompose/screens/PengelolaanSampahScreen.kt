package id.ac.unpas.functionalcompose.screens
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import id.ac.unpas.functionalcompose.model.SetoranSampah
import kotlinx.coroutines.launch
@Composable
fun PengelolaanSampahScreen(navController : NavHostController, modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<PengelolaanSampahViewModel>()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val items: List<SetoranSampah> by
    viewModel.list.observeAsState(initial = listOf())
    Column(modifier = modifier.fillMaxWidth()) {
        Button(onClick = {
            navController.navigate("tambah-pengelolaan-sampah")
        }) {
            Text(text = "Tambah")
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = items, itemContent = { item ->
                Row(modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth().clickable {
                        navController.navigate("edit-pengelolaan-sampah/${item.id}")
                    }) {
                Column(modifier = Modifier.weight(3f)) {
                        Text(text = "Tanggal", fontSize = 14.sp)
                        Text(text = item.tanggal, fontSize =
                        16.sp, fontWeight = FontWeight.Bold)
                    }
                    Column(modifier = Modifier.weight(3f)) {
                        Text(text = "Nama", fontSize = 14.sp)
                        Text(text = item.nama, fontSize = 16.sp,
                            fontWeight = FontWeight.Bold)
                    }
                    Column(modifier = Modifier.weight(3f)) {
                        Text(text = "Berat", fontSize = 14.sp)
                        Text(text = "${item.berat} Kg", fontSize
                        = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Divider(modifier = Modifier.fillMaxWidth())
            })
        }
    }
    LaunchedEffect(scope) {
        viewModel.loadItems()
    }
    viewModel.success.observe(LocalLifecycleOwner.current) {
        if (it) {
            scope.launch {
                viewModel.loadItems()
            }
        }
    }
    viewModel.toast.observe(LocalLifecycleOwner.current) {
        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
    }
}