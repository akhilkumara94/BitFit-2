package com.akhil.bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


class LogsFragment : Fragment() {
    private lateinit var bitfitRV: RecyclerView
    lateinit var bitFitAdapter: BitFitAdapter
    val bitfits = mutableListOf<BitFit>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_logs, container, false)

        // Setup the recycler view and it's data
        bitfitRV = view.findViewById(R.id.bitFitRV)
        bitFitAdapter = BitFitAdapter(view.context, bitfits)
        bitfitRV.adapter = bitFitAdapter
        bitfitRV.layoutManager = LinearLayoutManager(view.context).also {
            val dividerItemDecorator = DividerItemDecoration(view.context, it.orientation)
            bitfitRV.addItemDecoration(dividerItemDecorator)
        }

        lifecycleScope.launch{
            (activity?.application as BitFitApplication).db.bitFitDao().getAll().collect{
                    databaseList -> databaseList.map { entity ->
                BitFit(
                    entity.foodName,
                    entity.calorieCount
                )
            }.also { mappedList ->
                bitfits.clear()
                bitfits.addAll(mappedList)
                bitFitAdapter.notifyDataSetChanged() }
            }
        }

        return view
    }
}