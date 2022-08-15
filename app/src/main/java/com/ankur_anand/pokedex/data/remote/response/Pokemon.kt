package com.ankur_anand.pokedex.data.remote.response

import com.squareup.moshi.Json


data class Pokemon(
    @Json(name = "abilities")
    val abilities: List<Ability>,
    @Json(name = "base_experience")
    val baseExperience: Int,
    @Json(name = "forms")
    val forms: List<Form>,
    @Json(name = "game_indices")
    val gameIndices: List<GameIndice>,
    @Json(name = "height")
    val height: Int,
    @Json(name = "held_items")
    val heldItems: List<HeldItem>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_default")
    val isDefault: Boolean,
    @Json(name = "location_area_encounters")
    val locationAreaEncounters: String,
    @Json(name = "moves")
    val moves: List<Move>,
    @Json(name = "name")
    val name: String,
    @Json(name = "order")
    val order: Int,
    @Json(name = "past_types")
    val pastTypes: List<Any>,
    @Json(name = "species")
    val species: Species,
    @Json(name = "sprites")
    val sprites: Sprites,
    @Json(name = "stats")
    val stats: List<Stat>,
    @Json(name = "types")
    val types: List<Type>,
    @Json(name = "weight")
    val weight: Int
) {
    data class Ability(
        @Json(name = "ability")
        val ability: Ability,
        @Json(name = "is_hidden")
        val isHidden: Boolean,
        @Json(name = "slot")
        val slot: Int
    ) {
        data class Ability(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }

    data class Form(
        @Json(name = "name")
        val name: String,
        @Json(name = "url")
        val url: String
    )

    data class GameIndice(
        @Json(name = "game_index")
        val gameIndex: Int,
        @Json(name = "version")
        val version: Version
    ) {
        data class Version(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }

    data class HeldItem(
        @Json(name = "item")
        val item: Item,
        @Json(name = "version_details")
        val versionDetails: List<VersionDetail>
    ) {
        data class Item(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )

        data class VersionDetail(
            @Json(name = "rarity")
            val rarity: Int,
            @Json(name = "version")
            val version: Version
        ) {
            data class Version(
                @Json(name = "name")
                val name: String,
                @Json(name = "url")
                val url: String
            )
        }
    }

    data class Move(
        @Json(name = "move")
        val move: Move,
        @Json(name = "version_group_details")
        val versionGroupDetails: List<VersionGroupDetail>
    ) {
        data class Move(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )

        data class VersionGroupDetail(
            @Json(name = "level_learned_at")
            val levelLearnedAt: Int,
            @Json(name = "move_learn_method")
            val moveLearnMethod: MoveLearnMethod,
            @Json(name = "version_group")
            val versionGroup: VersionGroup
        ) {
            data class MoveLearnMethod(
                @Json(name = "name")
                val name: String,
                @Json(name = "url")
                val url: String
            )

            data class VersionGroup(
                @Json(name = "name")
                val name: String,
                @Json(name = "url")
                val url: String
            )
        }
    }

    data class Species(
        @Json(name = "name")
        val name: String,
        @Json(name = "url")
        val url: String
    )

    data class Sprites(
        @Json(name = "back_default")
        val backDefault: String,
        @Json(name = "back_female")
        val backFemale: Any,
        @Json(name = "back_shiny")
        val backShiny: String,
        @Json(name = "back_shiny_female")
        val backShinyFemale: Any,
        @Json(name = "front_default")
        val frontDefault: String,
        @Json(name = "front_female")
        val frontFemale: Any,
        @Json(name = "front_shiny")
        val frontShiny: String,
        @Json(name = "front_shiny_female")
        val frontShinyFemale: Any,
        @Json(name = "other")
        val other: Other,
        @Json(name = "versions")
        val versions: Versions
    ) {
        data class Other(
            @Json(name = "dream_world")
            val dreamWorld: DreamWorld,
            @Json(name = "home")
            val home: Home,
            @Json(name = "official-artwork")
            val officialArtwork: OfficialArtwork
        ) {
            data class DreamWorld(
                @Json(name = "front_default")
                val frontDefault: String,
                @Json(name = "front_female")
                val frontFemale: Any
            )

            data class Home(
                @Json(name = "front_default")
                val frontDefault: String,
                @Json(name = "front_female")
                val frontFemale: Any,
                @Json(name = "front_shiny")
                val frontShiny: String,
                @Json(name = "front_shiny_female")
                val frontShinyFemale: Any
            )

            data class OfficialArtwork(
                @Json(name = "front_default")
                val frontDefault: String
            )
        }

        data class Versions(
            @Json(name = "generation-i")
            val generationI: GenerationI,
            @Json(name = "generation-ii")
            val generationIi: GenerationIi,
            @Json(name = "generation-iii")
            val generationIii: GenerationIii,
            @Json(name = "generation-iv")
            val generationIv: GenerationIv,
            @Json(name = "generation-v")
            val generationV: GenerationV,
            @Json(name = "generation-vi")
            val generationVi: GenerationVi,
            @Json(name = "generation-vii")
            val generationVii: GenerationVii,
            @Json(name = "generation-viii")
            val generationViii: GenerationViii
        ) {
            data class GenerationI(
                @Json(name = "red-blue")
                val redBlue: RedBlue,
                @Json(name = "yellow")
                val yellow: Yellow
            ) {
                data class RedBlue(
                    @Json(name = "back_default")
                    val backDefault: String,
                    @Json(name = "back_gray")
                    val backGray: String,
                    @Json(name = "back_transparent")
                    val backTransparent: String,
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_gray")
                    val frontGray: String,
                    @Json(name = "front_transparent")
                    val frontTransparent: String
                )

                data class Yellow(
                    @Json(name = "back_default")
                    val backDefault: String,
                    @Json(name = "back_gray")
                    val backGray: String,
                    @Json(name = "back_transparent")
                    val backTransparent: String,
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_gray")
                    val frontGray: String,
                    @Json(name = "front_transparent")
                    val frontTransparent: String
                )
            }

            data class GenerationIi(
                @Json(name = "crystal")
                val crystal: Crystal,
                @Json(name = "gold")
                val gold: Gold,
                @Json(name = "silver")
                val silver: Silver
            ) {
                data class Crystal(
                    @Json(name = "back_default")
                    val backDefault: String,
                    @Json(name = "back_shiny")
                    val backShiny: String,
                    @Json(name = "back_shiny_transparent")
                    val backShinyTransparent: String,
                    @Json(name = "back_transparent")
                    val backTransparent: String,
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_shiny")
                    val frontShiny: String,
                    @Json(name = "front_shiny_transparent")
                    val frontShinyTransparent: String,
                    @Json(name = "front_transparent")
                    val frontTransparent: String
                )

                data class Gold(
                    @Json(name = "back_default")
                    val backDefault: String,
                    @Json(name = "back_shiny")
                    val backShiny: String,
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_shiny")
                    val frontShiny: String,
                    @Json(name = "front_transparent")
                    val frontTransparent: String
                )

                data class Silver(
                    @Json(name = "back_default")
                    val backDefault: String,
                    @Json(name = "back_shiny")
                    val backShiny: String,
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_shiny")
                    val frontShiny: String,
                    @Json(name = "front_transparent")
                    val frontTransparent: String
                )
            }

            data class GenerationIii(
                @Json(name = "emerald")
                val emerald: Emerald,
                @Json(name = "firered-leafgreen")
                val fireredLeafgreen: FireredLeafgreen,
                @Json(name = "ruby-sapphire")
                val rubySapphire: RubySapphire
            ) {
                data class Emerald(
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_shiny")
                    val frontShiny: String
                )

                data class FireredLeafgreen(
                    @Json(name = "back_default")
                    val backDefault: String,
                    @Json(name = "back_shiny")
                    val backShiny: String,
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_shiny")
                    val frontShiny: String
                )

                data class RubySapphire(
                    @Json(name = "back_default")
                    val backDefault: String,
                    @Json(name = "back_shiny")
                    val backShiny: String,
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_shiny")
                    val frontShiny: String
                )
            }

            data class GenerationIv(
                @Json(name = "diamond-pearl")
                val diamondPearl: DiamondPearl,
                @Json(name = "heartgold-soulsilver")
                val heartgoldSoulsilver: HeartgoldSoulsilver,
                @Json(name = "platinum")
                val platinum: Platinum
            ) {
                data class DiamondPearl(
                    @Json(name = "back_default")
                    val backDefault: String,
                    @Json(name = "back_female")
                    val backFemale: Any,
                    @Json(name = "back_shiny")
                    val backShiny: String,
                    @Json(name = "back_shiny_female")
                    val backShinyFemale: Any,
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_female")
                    val frontFemale: Any,
                    @Json(name = "front_shiny")
                    val frontShiny: String,
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: Any
                )

                data class HeartgoldSoulsilver(
                    @Json(name = "back_default")
                    val backDefault: String,
                    @Json(name = "back_female")
                    val backFemale: Any,
                    @Json(name = "back_shiny")
                    val backShiny: String,
                    @Json(name = "back_shiny_female")
                    val backShinyFemale: Any,
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_female")
                    val frontFemale: Any,
                    @Json(name = "front_shiny")
                    val frontShiny: String,
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: Any
                )

                data class Platinum(
                    @Json(name = "back_default")
                    val backDefault: String,
                    @Json(name = "back_female")
                    val backFemale: Any,
                    @Json(name = "back_shiny")
                    val backShiny: String,
                    @Json(name = "back_shiny_female")
                    val backShinyFemale: Any,
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_female")
                    val frontFemale: Any,
                    @Json(name = "front_shiny")
                    val frontShiny: String,
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: Any
                )
            }

            data class GenerationV(
                @Json(name = "black-white")
                val blackWhite: BlackWhite
            ) {
                data class BlackWhite(
                    @Json(name = "animated")
                    val animated: Animated,
                    @Json(name = "back_default")
                    val backDefault: String,
                    @Json(name = "back_female")
                    val backFemale: Any,
                    @Json(name = "back_shiny")
                    val backShiny: String,
                    @Json(name = "back_shiny_female")
                    val backShinyFemale: Any,
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_female")
                    val frontFemale: Any,
                    @Json(name = "front_shiny")
                    val frontShiny: String,
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: Any
                ) {
                    data class Animated(
                        @Json(name = "back_default")
                        val backDefault: String,
                        @Json(name = "back_female")
                        val backFemale: Any,
                        @Json(name = "back_shiny")
                        val backShiny: String,
                        @Json(name = "back_shiny_female")
                        val backShinyFemale: Any,
                        @Json(name = "front_default")
                        val frontDefault: String,
                        @Json(name = "front_female")
                        val frontFemale: Any,
                        @Json(name = "front_shiny")
                        val frontShiny: String,
                        @Json(name = "front_shiny_female")
                        val frontShinyFemale: Any
                    )
                }
            }

            data class GenerationVi(
                @Json(name = "omegaruby-alphasapphire")
                val omegarubyAlphasapphire: OmegarubyAlphasapphire,
                @Json(name = "x-y")
                val xY: XY
            ) {
                data class OmegarubyAlphasapphire(
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_female")
                    val frontFemale: Any,
                    @Json(name = "front_shiny")
                    val frontShiny: String,
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: Any
                )

                data class XY(
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_female")
                    val frontFemale: Any,
                    @Json(name = "front_shiny")
                    val frontShiny: String,
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: Any
                )
            }

            data class GenerationVii(
                @Json(name = "icons")
                val icons: Icons,
                @Json(name = "ultra-sun-ultra-moon")
                val ultraSunUltraMoon: UltraSunUltraMoon
            ) {
                data class Icons(
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_female")
                    val frontFemale: Any
                )

                data class UltraSunUltraMoon(
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_female")
                    val frontFemale: Any,
                    @Json(name = "front_shiny")
                    val frontShiny: String,
                    @Json(name = "front_shiny_female")
                    val frontShinyFemale: Any
                )
            }

            data class GenerationViii(
                @Json(name = "icons")
                val icons: Icons
            ) {
                data class Icons(
                    @Json(name = "front_default")
                    val frontDefault: String,
                    @Json(name = "front_female")
                    val frontFemale: Any
                )
            }
        }
    }

    data class Stat(
        @Json(name = "base_stat")
        val baseStat: Int,
        @Json(name = "effort")
        val effort: Int,
        @Json(name = "stat")
        val stat: Stat
    ) {
        data class Stat(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }

    data class Type(
        @Json(name = "slot")
        val slot: Int,
        @Json(name = "type")
        val type: Type
    ) {
        data class Type(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }
}