package com.lygttpod.android.pokemonsearch.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lygttpod.android.pokemonsearch.R
import com.lygttpod.android.pokemonsearch.adapter.SearchResultAdapter
import com.lygttpod.android.pokemonsearch.databinding.FragmentSearchBinding
import com.lygttpod.android.pokemonsearch.viewmodel.SearchViewModel

/**
 * <pre>
 *      author  : Allen
 *      date    : 2023/10/26
 *      desc    :
 * </pre>
 */
class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private var searchAdapter: SearchResultAdapter? = null

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initRecycleView()
        initEditText()
        initObserver()
    }

    private fun initObserver() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                showLoading()
            } else {
                showContent()
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            searchAdapter?.setData(it?.pokemon_v2_pokemonspecies)
        }
    }

    private fun initRecycleView() {
        searchAdapter = SearchResultAdapter().apply {
            onItemClick = {
                SearchViewModel.clickItemData = it
                findNavController().navigate(R.id.DetailFragment)
            }
        }
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }

    private fun initEditText() {
        binding?.etSearch?.addTextChangedListener(afterTextChanged = { text: Editable? ->
            val input = text?.toString()?.trim() ?: ""
            viewModel.search(input)
        })
    }

    private fun showContent() {
        binding?.clLoading?.visibility = View.GONE
        binding?.recyclerView?.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding?.clLoading?.visibility = View.VISIBLE
        binding?.recyclerView?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}