package kz.example.statesharedflows.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.flow.collect
import kz.example.statesharedflows.R

class MainFragment : Fragment(R.layout.main_fragment) {

    //region Vars
    private val viewModel: MainViewModel by viewModels()
    //endregion

    //region Overridden Methods
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        //TODO: рассказать про сравнение через equals
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel
                .observeText()
                .collect {
                    Log.i("myMainFragment", "text = $it")
                    message.text = it
                }
        }


        switcher.setOnCheckedChangeListener { _, isChecked -> viewModel.onSwitchClicked(isChecked) }
    }


    private fun setupNavigation() {
        btnNext.setOnClickListener {
            viewModel.onBtnNextClicked()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.observeNavigation().collect {
                Log.i("myMainFragment", "$it")
                if (it == Navigation.TO_SECOND_FRAGMENT) {
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToSecondFragment())
                }
            }

        }
    }
    //endregion
}