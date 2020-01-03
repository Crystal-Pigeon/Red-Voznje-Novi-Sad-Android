package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.helper.ItemTouchHelperCallback
import com.crystalpigeon.busnovisad.helper.OnStartDragListener
import com.crystalpigeon.busnovisad.model.entity.Lane
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.view.adapter.SortFavoritesAdapter
import com.crystalpigeon.busnovisad.viewmodel.SortViewModel
import kotlinx.android.synthetic.main.fragment_sort_favorites.*
import kotlinx.android.synthetic.main.fragment_sort_favorites.view.*
import kotlinx.coroutines.*
import javax.inject.Inject


class SortFavoritesFragment : Fragment(), OnStartDragListener,
    SortFavoritesAdapter.UpdateOrderListener {

    @Inject
    lateinit var viewModel: SortViewModel
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)
    private lateinit var itemTouchHelper: ItemTouchHelper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BusNsApp.app.component.inject(this)
        return inflater.inflate(R.layout.fragment_sort_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setActionBarTitle(R.string.sort_favorites)
        val layoutManager = LinearLayoutManager(context)
        val adapter = SortFavoritesAdapter(arrayListOf(), this, this)
        val callback = ItemTouchHelperCallback(adapter)
        itemTouchHelper = ItemTouchHelper(callback)

        rvFavorites.layoutManager = layoutManager
        rvFavorites.adapter = adapter
        itemTouchHelper.attachToRecyclerView(rvFavorites)

        coroutineScope.launch {
            if (viewModel.getAllFavorites().isEmpty()) {
                view.tv_no_favorites.visibility = View.VISIBLE
            } else {
                view.tv_no_favorites.visibility = View.GONE
                adapter.favorites = viewModel.getAllFavorites()
                withContext(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                }
            }
        }
        val navController =
            Navigation.findNavController(
                activity as MainActivity,
                R.id.nav_host_fragment
            )
        (activity as MainActivity).getSettingsButton().visibility = View.GONE
        (activity as MainActivity).showBackButton()
        (activity as MainActivity).getBackButton().setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun updateOrder(favorites: List<Lane>) {
        coroutineScope.launch {
            viewModel.updateOrder(favorites)
        }
    }
}

