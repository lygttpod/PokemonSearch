package com.lygttpod.android.pokemonsearch.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lygttpod.android.pokemonsearch.PokemonQuery
import com.lygttpod.android.pokemonsearch.databinding.FragmentDetailBinding
import com.lygttpod.android.pokemonsearch.utils.ColorUtils
import com.lygttpod.android.pokemonsearch.viewmodel.SearchViewModel

/**
 * <pre>
 *      author  : Allen
 *      date    : 2023/10/26
 *      desc    :
 * </pre>
 */
class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = SearchViewModel.clickItemData
        showData(data)
    }

    private fun showData(data: PokemonQuery.Pokemon_v2_pokemonspecy?) {
        data?.let { pokemonData ->
            val pokemonColor = ColorUtils.formatColor(pokemonData.pokemon_v2_pokemoncolor?.name)
            binding?.let {
                it.root.setBackgroundColor(Color.parseColor(pokemonColor.value))
                it.tvName.text = pokemonData.pokemon_v2_pokemons.firstOrNull()?.name
                it.tvAbilities.text = getAbilities(pokemonData)
                val textColor = getTextColor(pokemonColor)
                it.tvName.setTextColor(textColor)
                it.tvAbilities.setTextColor(textColor)
            }
        }
    }

    private fun getAbilities(pokemonData: PokemonQuery.Pokemon_v2_pokemonspecy): CharSequence {
        val ssb = StringBuilder()
        pokemonData.pokemon_v2_pokemons.firstOrNull()?.pokemon_v2_pokemonabilities?.forEach {
            it.pokemon_v2_ability?.name?.let { ssb.append("$it\n") }
        }
        return ssb
    }

    private fun getTextColor(pokemonColor: ColorUtils.Colors): Int {
        return if (pokemonColor == ColorUtils.Colors.WHITE) {
            Color.parseColor(ColorUtils.Colors.BLACK.value)
        } else {
            Color.parseColor(ColorUtils.Colors.WHITE.value)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}