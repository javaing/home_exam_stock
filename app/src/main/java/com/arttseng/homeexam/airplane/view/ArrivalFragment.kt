package com.arttseng.homeexam.airplane.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arttseng.homeexam.airplane.R
import com.arttseng.homeexam.airplane.adapter.ArrivalAdapter
import com.arttseng.homeexam.airplane.tools.Utils
import com.arttseng.homeexam.airplane.viewmodel.MyViewModel

class ArrivalFragment : Fragment() {

    lateinit var adapter: ArrivalAdapter
    private val viewModel: MyViewModel by activityViewModels()

    lateinit var progresBar : ProgressBar
    lateinit var tv_noData : TextView
    lateinit var recyclerView :RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        progresBar = view.findViewById(R.id.progresBar)
        tv_noData = view.findViewById(R.id.tv_noData)
        recyclerView = view.findViewById(R.id.recyclerView)

        viewModel.arrivalPlaneData.observe(viewLifecycleOwner) { list ->
            Utils.log("observe arrivalPlaneData size:" + list.size)

            progresBar.visibility = View.GONE
            if(list.isEmpty()) {
                recyclerView.visibility = View.GONE
                tv_noData.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                tv_noData.visibility = View.GONE
            }


            adapter.updateList(ArrayList(list), viewModel.airportData.value ?: emptyList())
        }

        viewModel.airportData.observe(viewLifecycleOwner) { list ->
            viewModel.arrivalPlaneData.value?.let { ArrayList(it) }
                ?.let { adapter.updateList(it, list) }
        }

        viewModel.isNeedReload.observe(viewLifecycleOwner) { isNeed ->
            if (isNeed) {
                progresBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                viewModel.getArrival()
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )

        adapter = ArrivalAdapter {

        }
        recyclerView.adapter = adapter

        viewModel.getArrival()
    }
}