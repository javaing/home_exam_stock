package com.arttseng.homeexam.stock.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arttseng.homeexam.stock.R
import com.arttseng.homeexam.stock.adapter.StockCardAdapter
import com.arttseng.homeexam.stock.databinding.FragmentSecondBinding
import com.arttseng.homeexam.stock.datamodel.BWIBBU_ALLItem
import com.arttseng.homeexam.stock.datamodel.Stock_Day_AllItem
import com.arttseng.homeexam.stock.tools.Utils
import com.arttseng.homeexam.stock.tools.toast
import com.arttseng.homeexam.stock.viewmodel.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class StockFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var adapter: StockCardAdapter
    private val viewModel: MyViewModel by activityViewModels()

    lateinit var progresBar : ProgressBar
    lateinit var tv_noData : TextView
    lateinit var recyclerView : RecyclerView
    lateinit var bottomBehavior: BottomSheetBehavior<View>
    lateinit var bottom_sheet: View
    lateinit var toggleBottomSheet: View
    lateinit var AtextView: View
    lateinit var BtextView: View

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
        bottom_sheet = view.findViewById(R.id.bottom_sheet)
        toggleBottomSheet = view.findViewById(R.id.toggleBottomSheet)
        AtextView = view.findViewById(R.id.AtextView)
        BtextView = view.findViewById(R.id.BtextView)

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

            adapter.updateList(ArrayList(list))
        }

        viewModel.stockDayAvgAllData.observe(viewLifecycleOwner) { list ->
            adapter.updateMonthAvg( ArrayList(viewModel.stockDayAvgAllData.value ?: emptyList()))
        }

        recyclerView.layoutManager = LinearLayoutManager(view.context)
        adapter = StockCardAdapter {
            if(!bottomBehavior.isHideable) {
                hideBottomSheet()
                return@StockCardAdapter
            }

            val Code = (it as View).tag as String
            //Utils.log("click Code:" + Code)
            //Utils.log("click viewModel.bwibbuData:" + viewModel.bwibbuData.value)
            if(viewModel.bwibbuData.value?.isNotEmpty() == true) {
                val find = viewModel.bwibbuData.value!!.filter { it.Code.equals(Code)}
                //Utils.log("click find:" + find)
                if(find.isNotEmpty()) {
                    showDialog(find.get(0))
                } else {
                    requireActivity().toast("此商品無本益比，殖利率等資料")
                }
            }

        }
        recyclerView.adapter = adapter

        bottomBehavior = BottomSheetBehavior.from(bottom_sheet)
        hideBottomSheet()
        toggleBottomSheet.setOnClickListener {
            if(bottomBehavior.isHideable) {
                showBottomSheet(toggleBottomSheet)
            } else {
                hideBottomSheet()
            }
        }
        AtextView.setOnClickListener {
            adapter.reverseData(true)
            hideBottomSheet()
        }
        BtextView.setOnClickListener {
            adapter.reverseData(false)
            hideBottomSheet()
        }
    }

    fun hideBottomSheet(){
        bottomBehavior.isHideable=true
        bottomBehavior.state = BottomSheetBehavior.STATE_HIDDEN

    }
    fun showBottomSheet(v: View) {
        bottomBehavior.isHideable=false
        setBottomViewVisible(bottomBehavior.state != BottomSheetBehavior.STATE_EXPANDED)
    }

    private fun setBottomViewVisible(showFlag: Boolean) {

        if (showFlag)
            bottomBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        else
            bottomBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }



    private fun showDialog(data: BWIBBU_ALLItem) {
        val builder = AlertDialog.Builder(requireContext())
            .create()
        val view = layoutInflater.inflate(R.layout.custom_dialog,null)

        val value1 = if (data.PEratio.isNotEmpty()) data.PEratio else "-";
        val value2 = if (data.DividendYield.isNotEmpty()) data.DividendYield + "%" else "-"
        val value3 = if (data.PBratio.isNotEmpty()) data.PBratio else "-"

        view.findViewById<TextView>(R.id.tvPEratio).setText(value1)
        view.findViewById<TextView>(R.id.tvDividendYield).setText(value2)
        view.findViewById<TextView>(R.id.tvPBratio).setText(value3)
        val  button = view.findViewById<Button>(R.id.btn_yes)
        builder.setView(view)
        button.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.setTitle(data.Name + " / " + data.Code)
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}