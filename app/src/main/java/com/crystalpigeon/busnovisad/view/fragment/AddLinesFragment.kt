package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.view.adapter.UrbanSuburbanPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_lines.*

class AddLinesFragment : Fragment() {

    companion object Test {
        fun newInstance(): AddLinesFragment {
            return AddLinesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_lines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setActionBarTitle(R.string.add)
        viewpager_add_lines.adapter = UrbanSuburbanPagerAdapter(childFragmentManager,context!!)
        tablayout.setupWithViewPager(viewpager_add_lines)
    }
}
