package com.lygttpod.android.pokemonsearch.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lygttpod.android.pokemonsearch.PokemonQuery
import com.lygttpod.android.pokemonsearch.R
import com.lygttpod.android.pokemonsearch.databinding.ItemSearchResultBinding
import com.lygttpod.android.pokemonsearch.utils.ColorUtils

/**
 * <pre>
 *      author  : Allen
 *      date    : 2023/10/26
 *      desc    :
 * </pre>
 */
class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    private var list: List<PokemonQuery.Pokemon_v2_pokemonspecy> = listOf()

    var onItemClick: ((PokemonQuery.Pokemon_v2_pokemonspecy) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bindData(list[position])
    }


    fun setData(datas: List<PokemonQuery.Pokemon_v2_pokemonspecy>?) {
        this.list = datas ?: mutableListOf()
        notifyItemRangeChanged(0, itemCount)
    }

    inner class SearchResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemSearchResultBinding.bind(view)
        private var data: PokemonQuery.Pokemon_v2_pokemonspecy? = null

        init {
            binding.root.setOnClickListener {
                data?.let { onItemClick?.invoke(it) }
            }
        }

        fun bindData(data: PokemonQuery.Pokemon_v2_pokemonspecy) {
            this.data = data
            val pokemonColor = ColorUtils.formatColor(data.pokemon_v2_pokemoncolor?.name)
            binding.root.setBackgroundColor(Color.parseColor(pokemonColor.value))
            val textColor = getTextColor(pokemonColor)
            binding.tvName.setTextColor(textColor)
            binding.tvCaptureRate.setTextColor(textColor)
            binding.tvName.text = data.name
            binding.tvCaptureRate.text = "${data.capture_rate}"
        }

        private fun getTextColor(pokemonColor: ColorUtils.Colors): Int {
            return if (pokemonColor == ColorUtils.Colors.WHITE) {
                Color.parseColor(ColorUtils.Colors.BLACK.value)
            } else {
                Color.parseColor(ColorUtils.Colors.WHITE.value)
            }
        }
    }
}