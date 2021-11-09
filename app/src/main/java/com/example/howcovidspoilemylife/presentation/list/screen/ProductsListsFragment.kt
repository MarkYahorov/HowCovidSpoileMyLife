package com.example.howcovidspoilemylife.presentation.list.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.howcovidspoilemylife.core.CovidApplication
import com.example.howcovidspoilemylife.databinding.FragmentProductsListsFrragmentBinding
import com.example.howcovidspoilemylife.presentation.list.adapters.ProductAdapter
import com.example.howcovidspoilemylife.presentation.list.di.ListComponent
import com.example.howcovidspoilemylife.presentation.list.viewModel.Common
import com.example.howcovidspoilemylife.presentation.list.viewModel.ListViewModel
import dagger.Lazy
import java.text.SimpleDateFormat
import javax.inject.Inject

class ProductsListsFragment : Fragment() {

    @Inject
    lateinit var factory: Lazy<ListViewModel.Factory>
    private val viewModel: ListViewModel by viewModels { factory.get() }
    private lateinit var binding: FragmentProductsListsFrragmentBinding
    private val dateFormat = SimpleDateFormat.getDateInstance()
    private val badProductsAdapter by lazy {
        ProductAdapter(dateFormat, {
            val action =
                ProductsListsFragmentDirections.actionProductsListsFragmentToUpdateProductFragment(
                    it.product
                )
            findNavController().navigate(action)
        }, { createDialog(it) }
        )
    }

    private val goodProductAdapter by lazy {
        ProductAdapter(dateFormat, {
            val action =
                ProductsListsFragmentDirections.actionProductsListsFragmentToUpdateProductFragment(
                    it.product
                )
            findNavController().navigate(action)
        }, { createDialog(it) }
        )
    }

    private fun createDialog(common: Common) {
        AlertDialog.Builder(requireContext())
            .setTitle("ВЫ хотите удалить пункт?")
            .setPositiveButton("Да") { dialog, _ ->
                viewModel.deleteProduct(common.product)
                dialog.dismiss()
            }
            .setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ListComponent.create(CovidApplication.appComponent, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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

    override fun onStart() {
        super.onStart()

        setBtnsListeners()
        viewModel.getProducts()

        viewModel.productLiveData.observe(viewLifecycleOwner, { list ->
            if (list.isNullOrEmpty()) {
                setEmptyStateVisibity(isVisible = true)
            } else {
                setEmptyStateVisibity(isVisible = false)
                val goodProductsList = mutableListOf<Common>()
                val badProductsList = mutableListOf<Common>()
                list.forEach {
                    if (it.product.isGoodProduct == 1) {
                        badProductsList.add(it)
                    } else {
                        goodProductsList.add(it)
                    }
                }
                goodProductAdapter.submitList(goodProductsList)
                badProductsAdapter.submitList(badProductsList)
            }
        })
    }

    private fun setEmptyStateVisibity(isVisible: Boolean) {
        binding.emptyState.imageView.isVisible = isVisible
        binding.emptyState.textView2.isVisible = isVisible
        binding.addBadProductBtn.isVisible = !isVisible
        binding.addGoodProductBtn.isVisible = !isVisible
        binding.listBadProducts.isVisible = !isVisible
        binding.listGoodProducts.isVisible = !isVisible
        binding.listsDivider.isVisible = !isVisible
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