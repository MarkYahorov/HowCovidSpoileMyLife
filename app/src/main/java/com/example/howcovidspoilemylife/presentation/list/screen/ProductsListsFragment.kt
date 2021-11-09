package com.example.howcovidspoilemylife.presentation.list.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.howcovidspoilemylife.core.CovidApplication
import com.example.howcovidspoilemylife.databinding.FragmentProductsListsFrragmentBinding
import com.example.howcovidspoilemylife.presentation.list.adapters.BadProductAdapter
import com.example.howcovidspoilemylife.presentation.list.adapters.GoodProductAdapter
import com.example.howcovidspoilemylife.presentation.list.di.ListComponent
import com.example.howcovidspoilemylife.presentation.list.viewModel.ListViewModel
import dagger.Lazy
import javax.inject.Inject

class ProductsListsFragment : Fragment() {

    @Inject
    lateinit var factory: Lazy<ListViewModel.Factory>
    private val viewModel: ListViewModel by viewModels { factory.get() }
    private lateinit var binding: FragmentProductsListsFrragmentBinding
    private val badProductsAdapter by lazy {
        BadProductAdapter {
            val action =
                ProductsListsFragmentDirections.actionProductsListsFragmentToUpdateProductFragment(
                    it
                )
            findNavController().navigate(action)
        }
    }

    private val goodProductAdapter by lazy {
        GoodProductAdapter {
            val action =
                ProductsListsFragmentDirections.actionProductsListsFragmentToUpdateProductFragment(
                    it
                )
            findNavController().navigate(action)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ListComponent.create(CovidApplication.appComponent, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsListsFrragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBadProductsRecycler()
        initGoodProductsRecycler()
    }

    private fun initGoodProductsRecycler() {
        with(binding.listGoodProducts) {
            adapter = goodProductAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initBadProductsRecycler() {
        with(binding.listBadProducts) {
            adapter = badProductsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()

        setBtnsListeners()
        viewModel.getBadProducts()
        viewModel.getGoodProducts()

        viewModel.goodProductLiveData.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {

            } else {
                val list = it.sortedBy { it.time }
                goodProductAdapter.getList().addAll(list)
                goodProductAdapter.notifyDataSetChanged()
            }
        })

        viewModel.badProductLiveData.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {

            } else {
                val list = it.sortedBy { it.time }
                Log.e("key", "$list")
                badProductsAdapter.submitList(list)
            }
        })
    }

    private fun setBtnsListeners() {
        binding.addGoodProductBtn.setOnClickListener {
            val action =
                ProductsListsFragmentDirections.actionProductsListsFragmentToAddProductFragment(
                    isGoodProduct = 0
                )
            findNavController().navigate(action)
        }

        binding.addBadProductBtn.setOnClickListener {
            val action =
                ProductsListsFragmentDirections.actionProductsListsFragmentToAddProductFragment(
                    isGoodProduct = 1
                )
            findNavController().navigate(action)
        }
    }
}