package com.coolya.daunick.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coolya.daunick.MainActivity
import kotlinx.android.synthetic.main.fragment_diary_list.*

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (toolbar != null && !title().isNullOrEmpty()) {
            toolbar.title = title()
        }
    }


    abstract fun layout(): Int
    abstract fun title(): String?


    val baseActivity: MainActivity?
        get() = if (activity is MainActivity) activity as MainActivity? else null

}