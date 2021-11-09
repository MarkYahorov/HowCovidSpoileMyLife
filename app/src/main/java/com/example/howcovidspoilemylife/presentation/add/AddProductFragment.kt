package com.example.howcovidspoilemylife.presentation.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.howcovidspoilemylife.R
import com.example.howcovidspoilemylife.core.CovidApplication
import com.example.howcovidspoilemylife.databinding.FragmentAddProductBinding
import com.example.howcovidspoilemylife.presentation.add.di.AddComponent
import com.example.howcovidspoilemylife.presentation.add.viewModel.AddViewModel
import com.example.howcovidspoilemylife.presentation.models.Product
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.Lazy
import java.text.SimpleDateFormat
import javax.inject.Inject

class AddProductFragment : Fragment() {

    @Inject
    lateinit var factory: Lazy<AddViewModel.Factory>
    private val viewModel: AddViewModel by viewModels { factory.get() }
    private val args by navArgs<AddProductFragmentArgs>()
    private lateinit var binding: FragmentAddProductBinding
    private val dateFormat = SimpleDateFormat.getDateInstance()
    private var currentDate: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AddComponent.create(CovidApplication.appComponent, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.calendarImage.setOnClickListener {
            getTime()
        }

        binding.button.setOnClickListener {
            if (binding.nameOfProductTextView.text.isNullOrBlank() && currentDate == 0L) {
                Toast.makeText(
                    requireContext(),
                    "ВВЕДИТЕ ПРОДУКТ ИЛИ ДАТУ УПОТРЕБЛЕНИЯ",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val list = binding.nameOfProductTextView.text.toString().split("\n")
                list.forEach {  string ->
                    viewModel.insert(
                        Product(
                            0,
                            string,
                            args.isGoodProduct,
                            currentDate
                        )
                    )
                }
                findNavController().navigate(R.id.action_addProductFragment_to_productsListsFragment)
            }
        }
    }

    private fun getTime() {
        val picker = createPicker()
        picker.show(childFragmentManager, "DATE_PICKER")
        picker.addOnPositiveButtonClickListener {
            currentDate = picker.selection!!
            binding.dateTextView.text = dateFormat.format(currentDate)
            picker.dismiss()
        }
        picker.addOnNegativeButtonClickListener {
            picker.dismiss()
        }
    }

    private fun createPicker(): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker().build()
    }
}