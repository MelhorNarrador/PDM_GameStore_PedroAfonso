package pt.iade.gamestore.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.gamestore.data.SampleData
import pt.iade.gamestore.models.Game
import pt.iade.gamestore.models.PurchasableItem
import pt.iade.gamestore.ui.theme.GameStoreTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.clip

class GameDetailActivity : ComponentActivity() {

    companion object {
        const val EXTRA_GAME = "extra_game"
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val game = intent.getSerializableExtra(EXTRA_GAME) as? Game

        setContent {
            GameStoreTheme {
                if (game != null) {
                    GameDetailScreen(
                        game = game,
                        onBack = { finish() }
                    )
                } else {
                    Text("Game not found")
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    game: Game,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var selectedItem by remember { mutableStateOf<PurchasableItem?>(null) }
    var isBottomSheetOpen by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = { Text(text = game.name) },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ){
                        Image(
                            painter = painterResource(id = game.imageRes),
                            contentDescription = game.name,
                            modifier = Modifier.size(150.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.FillBounds
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = game.description,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                item {
                    Text(
                        text = "Purchasable Items",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                items(game.items) { item ->
                    ItemRow(
                        item = item,
                        onClick = {
                            selectedItem = item
                            isBottomSheetOpen = true
                        }
                    )
                }
            }
            if (isBottomSheetOpen && selectedItem != null) {
                ModalBottomSheet(
                    onDismissRequest = {
                        isBottomSheetOpen = false
                        selectedItem = null
                    },
                    sheetState = bottomSheetState
                ) {
                    ItemBottomSheetContent(
                        item = selectedItem!!,
                        onBuyClick = { boughtItem ->
                            val priceText = String.format("%.2f", boughtItem.price)
                            Toast.makeText(
                                context,
                                "Acabou de comprar o item ${boughtItem.name} por $$priceText",
                                Toast.LENGTH_LONG
                            ).show()

                            isBottomSheetOpen = false
                            selectedItem = null
                        }
                    )
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun ItemRow(
    item: PurchasableItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ){
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier.size(100.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = String.format("$%.2f", item.price),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Bottom)
                )
        }
    }
}
@SuppressLint("DefaultLocale")
@Composable
fun ItemBottomSheetContent(
    item: PurchasableItem,
    onBuyClick: (PurchasableItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
            .height(140.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .width(130.dp),
                contentScale = ContentScale.FillBounds

            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = String.format("$%.2f", item.price),
                style = MaterialTheme.typography.titleLarge,
            )
            Button(
                onClick = { onBuyClick(item) },
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE),
                contentColor = Color.White)
            ) {
                Text(
                    text = "Buy with 1-click",
                    style = MaterialTheme.typography.labelLarge
                )

            }
        }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemRowPreview() {
    GameStoreTheme {
        ItemRow(
            item = SampleData.games.first().items.first(),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemBottomSheetContentPreview() {
    GameStoreTheme {
        ItemBottomSheetContent(
            item = SampleData.games.first().items.first(),
            onBuyClick = {}
        )
    }
}
@Preview(showBackground = true)
@Composable
fun GameDetailScreenPreview() {
    GameStoreTheme {
        GameDetailScreen(
            game = SampleData.games.first(),
            onBack = {}
        )
    }
}
