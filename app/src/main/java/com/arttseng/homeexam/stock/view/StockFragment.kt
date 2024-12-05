package com.arttseng.homeexam.stock.view

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
import com.arttseng.homeexam.stock.R
import com.arttseng.homeexam.stock.adapter.StockCardAdapter
import com.arttseng.homeexam.stock.databinding.FragmentSecondBinding
import com.arttseng.homeexam.stock.tools.Utils
import com.arttseng.homeexam.stock.viewmodel.MyViewModel

class StockFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var adapter: StockCardAdapter
    private val viewModel: MyViewModel by activityViewModels()

    lateinit var progresBar : ProgressBar
    lateinit var tv_noData : TextView
    lateinit var recyclerView : RecyclerView

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progresBar = view.findViewById(R.id.progresBar)
        tv_noData = view.findViewById(R.id.tv_noData)
        recyclerView = view.findViewById(R.id.recyclerView)


        viewModel.stockDayAllData.observe(viewLifecycleOwner) { list ->
            Utils.log("observe data:" + list)

            progresBar.visibility = View.GONE
            if(list == null) {
                recyclerView.visibility = View.GONE
                tv_noData.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                tv_noData.visibility = View.GONE
            }

            adapter.updateList(ArrayList(list), ArrayList(viewModel.stockDayAvgAllData.value ?: emptyList()))
        }

        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )

        adapter = StockCardAdapter {

        }
        recyclerView.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}