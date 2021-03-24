package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.helper.ItemTouchHelperCallback
import com.crystalpigeon.busnovisad.helper.OnStartDragListener
import com.crystalpigeon.busnovisad.model.entity.Lane
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.view.adapter.SortFavoritesAdapter
import com.crystalpigeon.busnovisad.viewmodel.SortViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.fragment_sort_favorites.*
import kotlinx.android.synthetic.main.fragment_sort_favorites.view.*
import kotlinx.coroutines.*

class SortFavoritesFragment : Fragment(), OnStartDragListener,
    SortFavoritesAdapter.UpdateOrderListener {

    private val viewModel: SortViewModel by viewModels()
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sort_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
        (activity as MainActivity).setActionBarTitle(R.string.sort_favorites)
        val layoutManager = LinearLayoutManager(context)
        val adapter = SortFavoritesAdapter(arrayListOf(), this, this)
        val callback = ItemTouchHelperCallback(adapter)
        itemTouchHelper = ItemTouchHelper(callback)

        rvFavorites.layoutManager = layoutManager
        rvFavorites.adapter = adapter
        itemTouchHelper.attachToRecyclerView(rvFavorites)

        coroutineScope.launch {
            val favorites = viewModel.getAllFavorites()
            withContext(Dispatchers.Main) {
                if (favorites.isEmpty()) {
                    view.tv_no_favorites.visibility = View.VISIBLE
                } else {
                    view.tv_no_favorites.visibility = View.GONE
                    adapter.favorites = favorites
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
        (activity as MainActivity).hideSortButton()
        (activity as MainActivity).getBackButton().setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun updateOrder(favorites: List<Lane>) {
        firebaseAnalytics.logEvent("sort_favorite_lanes", null)
        coroutineScope.launch {
            viewModel.updateOrder(favorites)
        }
    }
}

