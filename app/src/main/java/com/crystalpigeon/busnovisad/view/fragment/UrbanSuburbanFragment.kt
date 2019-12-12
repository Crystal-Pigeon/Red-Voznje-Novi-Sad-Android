package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.adapter.LaneAdapter
import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import kotlinx.android.synthetic.main.fragment_urban_suburban.*
import javax.inject.Inject

class UrbanSuburbanFragment : Fragment(){

    var adapter: LaneAdapter? = null
    var type: String? = null

    @Inject
    lateinit var viewModel: LanesViewModel

    init {
        BusNsApp.app.component.inject(this)
    }

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
        return inflater.inflate(R.layout.fragment_urban_suburban,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) type = arguments?.getString(Const.TYPE)
        viewModel.getLanes(type!!).observe(this, Observer {
            adapter?.lanes = ArrayList(it)
            adapter?.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LaneAdapter(arrayListOf(), context!!)
        rv_lines.layoutManager = LinearLayoutManager(context)
        rv_lines.adapter = adapter
    }
}