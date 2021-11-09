package com.example.howcovidspoilemylife.presentation.update.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.howcovidspoilemylife.R
import com.example.howcovidspoilemylife.databinding.FragmentUpdateProductBinding
import com.example.howcovidspoilemylife.presentation.models.Product
import com.example.howcovidspoilemylife.presentation.update.viewModel.UpdateViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.Lazy
import java.text.SimpleDateFormat
import javax.inject.Inject

class UpdateProductFragment : Fragment() {

    @Inject
    lateinit var factory: Lazy<UpdateViewModel.Factory>
    private val viewModel: UpdateViewModel by viewModels { factory.get() }
    private val args by navArgs<UpdateProductFragmentArgs>()
    private lateinit var binding: FragmentUpdateProductBinding
    private val dateFormat = SimpleDateFormat.getDateInstance()
    private var currentDate: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameOfProductTextView.setText(args.currentTask.name)
        binding.dateTextView.text = dateFormat.format(args.currentTask.time)
    }

    override fun onStart() {
        super.onStart()

        setBtnClickListener()
        binding.calendarImage.setOnClickListener {
            getTime()
        }
    }

    private fun createPicker(): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker().build()
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

    private fun setBtnClickListener() {
        binding.button.setOnClickListener {

            if (binding.nameOfProductTextView.text.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    "ВВЕДИТЕ ПРОДУКТ",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                viewModel.update(
                    Product(
                        args.currentTask.id,
                        binding.nameOfProductTextView.text.toString(),
                        args.currentTask.isGoodProduct,
                        getDate()
                    )
                )
                findNavController().navigate(R.id.action_addProductFragment_to_productsListsFragment)
            }
        }
    }

    private fun getDate(): Long {
        return if (currentDate == 0L) {
            args.currentTask.time
        } else {
            currentDate
        }
    }
}