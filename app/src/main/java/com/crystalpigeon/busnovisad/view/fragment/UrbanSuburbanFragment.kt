package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.crystalpigeon.busnovisad.Const
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.adapter.LaneAdapter
import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import kotlinx.android.synthetic.main.fragment_urban_suburban.*
import kotlinx.android.synthetic.main.fragment_urban_suburban.view.*

class UrbanSuburbanFragment : Fragment() {

    private val viewModel: LanesViewModel by viewModels()

    lateinit var adapter: LaneAdapter
    var type: String? = null

    companion object {
        fun newInstance(type: String) = UrbanSuburbanFragment().apply {
            arguments = Bundle().apply { putString(Const.TYPE, type) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_urban_suburban, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LaneAdapter(arrayListOf(), requireContext(), viewModel)
        rv_lines.layoutManager = LinearLayoutManager(context)
        rv_lines.adapter = adapter

        if (arguments != null) type = arguments?.getString(Const.TYPE)

        viewModel.getLanes(type!!).observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                view.tv_no_lines.visibility = View.VISIBLE
            } else {
                view.tv_no_lines.visibility = View.GONE
                adapter.lanes = ArrayList(it)
                adapter.notifyDataSetChanged()
            }
        })
    }
}