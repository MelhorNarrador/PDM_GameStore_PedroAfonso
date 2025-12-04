package pt.iade.gamestore.data


import pt.iade.gamestore.R
import pt.iade.gamestore.models.Game
import pt.iade.gamestore.models.PurchasableItem

object SampleData {

    val games: List<Game> = listOf(
        Game(
            id = 1,
            name = "Elden Ring",
            description = "Um RPG de ação desenvolvido pela FromSoftware, com mundo aberto e grande liberdade de exploração.",
            imageRes = R.drawable.elden_ring,
            items = listOf(
                PurchasableItem(
                    id = 1,
                    name = "Shadow of the Erdtree",
                    description = "A expansão oficial de Elden Ring com novas áreas, bosses e história.",
                    price = 39.99,
                    imageRes = R.drawable.er_shadow_of_the_erdtree
                ),
                PurchasableItem(
                    id = 2,
                    name = "Digital Artbook & OST",
                    description = "Livro de arte digital e banda sonora oficial.",
                    price = 9.99,
                    imageRes = R.drawable.er_artbook_ost
                ),
                PurchasableItem(
                    id = 3,
                    name = "Adventure Guide",
                    description = "Guia digital com informações adicionais e lore.",
                    price = 4.99,
                    imageRes = R.drawable.er_adventure_guide
                )
            )
        ),
        Game(
            id = 2,
            name = "Cyberpunk 2077",
            description = "Um RPG futurista em Night City, com narrativa profunda e mundo aberto vibrante.",
            imageRes = R.drawable.cyberpunk2077,
            items = listOf(
                PurchasableItem(
                    id = 4,
                    name = "Phantom Liberty",
                    description = "Expansão com nova história, área de Dogtown e atualizações ao gameplay.",
                    price = 29.99,
                    imageRes = R.drawable.cp2077_phantom_liberty
                ),
                PurchasableItem(
                    id = 5,
                    name = "Redmod Tools",
                    description = "Ferramentas oficiais de modding. Gratuito.",
                    price = 0.0,
                    imageRes = R.drawable.cp2077_redmod
                ),
                PurchasableItem(
                    id = 6,
                    name = "GOG Bonus Content",
                    description = "Livros de arte, wallpapers, trilha sonora e conteúdo extra da versão GOG.",
                    price = 0.0,
                    imageRes = R.drawable.cp2077_bonus_content
                )
            )
        ),
    )
}