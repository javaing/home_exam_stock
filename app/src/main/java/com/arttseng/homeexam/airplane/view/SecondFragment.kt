package com.arttseng.homeexam.airplane.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.CheckedTextView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arttseng.homeexam.airplane.R
import com.arttseng.homeexam.airplane.adapter.CurrencyAdapter
import com.arttseng.homeexam.airplane.databinding.FragmentSecondBinding
import com.arttseng.homeexam.airplane.datamodel.CurrencyItem
import com.arttseng.homeexam.airplane.tools.Const
import com.arttseng.homeexam.airplane.tools.Utils
import com.arttseng.homeexam.airplane.viewmodel.MyViewModel

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var adapter: CurrencyAdapter
    private val viewModel: MyViewModel by activityViewModels()

    lateinit var progresBar : ProgressBar
    lateinit var tv_noData : TextView
    lateinit var recyclerView : RecyclerView
    lateinit var spinner : Spinner

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }


    fun initSpinner() {
        val items = Const.currencyList.split(",")
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item, items)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val base : String = parent?.getItemAtPosition(position) as String
                progresBar.visibility = View.VISIBLE
                viewModel.getCurrencies(base)
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progresBar = view.findViewById(R.id.progresBar)
        tv_noData = view.findViewById(R.id.tv_noData)
        recyclerView = view.findViewById(R.id.recyclerView)
        spinner = view.findViewById(R.id.spinner)

        initSpinner()

        viewModel.currenciesData.observe(viewLifecycleOwner) { data ->
            Utils.log("observe data:" + data)

            progresBar.visibility = View.GONE
            if(data == null || data.data == null) {
                recyclerView.visibility = View.GONE
                tv_noData.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                tv_noData.visibility = View.GONE

                val list = arrayListOf<CurrencyItem>()
                Const.currencyList.split(",").forEach {
                    //"CNY,EUR,JPY,KRW,HKD,USD,GBP"
                    val item = when(it) {
                        "AUD" -> CurrencyItem(it, data.data.AUD)
                        "EUR" -> CurrencyItem(it, data.data.EUR)
                        "CNY" -> CurrencyItem(it, data.data.CNY)
                        "JPY" -> CurrencyItem(it, data.data.JPY)
                        "KRW" -> CurrencyItem(it, data.data.KRW)
                        "USD" -> CurrencyItem(it, data.data.USD)
                        "HKD" -> CurrencyItem(it, data.data.HKD)
                        else -> {CurrencyItem("", 1.0)}
                    }
                    list.add(item);
                }
                adapter.updateList(list)
            }



        }

        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )

        adapter = CurrencyAdapter {

        }
        recyclerView.adapter = adapter

        viewModel.getCurrencies("USD")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

abstract class CustomAdapter<T>(val context: Context, val items : List<T>) : BaseAdapter() {

    override fun getItem(i: Int): T {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    protected fun getLayoutView(@LayoutRes layoutResId: Int): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return layoutInflater.inflate(layoutResId, null, false)
    }

}

class CountryAdapter(context: Context, items: List<String>) : CustomAdapter<String>(context, items) {

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val vh: ViewHolder
        if (view == null) {
            view = getLayoutView(android.R.layout.simple_spinner_dropdown_item)
            vh = ViewHolder()
            vh.name = view!!.findViewById(android.R.id.text1)
            view.tag = vh
        } else {
            vh = view.tag as ViewHolder
        }

        vh.name!!.text = getItem(i)
        vh.name!!.setTextColor(Color.WHITE)
        return view
    }

    internal inner class ViewHolder {
        var name: CheckedTextView? = null
    }
}