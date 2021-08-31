package kz.example.statesharedflows.ui.second

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.main_fragment.*
import kz.example.statesharedflows.R

class SecondFragment : Fragment(R.layout.second_fragment) {

    //region Companion
    companion object {
        fun newInstance() = SecondFragment()
    }
    //endregion

    private val viewModel: SecondViewModel by viewModels()

    //region Overridden Methods
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    //endregion


}