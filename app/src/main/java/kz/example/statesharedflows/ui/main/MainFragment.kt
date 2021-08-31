package kz.example.statesharedflows.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.main_fragment.*
import kz.example.statesharedflows.R

class MainFragment : Fragment(R.layout.main_fragment) {

    //region Vars
    private val viewModel: MainViewModel by viewModels()
    //endregion

    //region Overridden Methods
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        viewModel.observeText().observe(this.viewLifecycleOwner, { message.text = it })
        switcher.setOnCheckedChangeListener { _, isChecked -> viewModel.onSwitchClicked(isChecked) }
    }


    private fun setupNavigation() {
        btnNext.setOnClickListener {
            viewModel.onBtnNextClicked()
        }

        viewModel.observeNavigation().observe(this.viewLifecycleOwner, {
            Log.i("myMainFragment", "navigate")
            if (it == Navigation.TO_SECOND_FRAGMENT) {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToSecondFragment())
            }
        })
    }
    //endregion
}