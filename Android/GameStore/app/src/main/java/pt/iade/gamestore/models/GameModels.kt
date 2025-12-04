package pt.iade.gamestore.models

import androidx.annotation.DrawableRes
import java.io.Serializable

data class PurchasableItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    @DrawableRes val imageRes: Int
) : Serializable

data class Game(
    val id: Int,
    val name: String,
    val description: String,
    @DrawableRes val imageRes: Int,
    val items: List<PurchasableItem>
) : Serializable