package com.arttseng.homeexam.airplane.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.arttseng.homeexam.airplane.databinding.FragmentFirstBinding
import com.arttseng.homeexam.airplane.tools.Const
import com.arttseng.homeexam.airplane.tools.Utils
import com.arttseng.homeexam.airplane.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.concurrent.fixedRateTimer


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel_Airplane: MyViewModel by activityViewModels()
    private lateinit var fragmentAdapter: FragmentAdapter

    var currentCount = Const.CountDown

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVM()
        initView()
    }
    private fun initVM() {
        //viewModel.getNews()
        //viewModel.getAttract()
        //viewModel_Airplane.getDeparture()
        //viewModel_Airplane.getArrival()
    }
    private fun initView() {

        fragmentAdapter = FragmentAdapter(this)
        binding.pager.adapter = fragmentAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            setTabHostLabel(tab, position)
        }.attach()

        binding.progressFirstCountDown.max = Const.CountDown
        fixedRateTimer("timer", false, 0L, 60 * 1000) {
            if(isAdded) {
                requireActivity().runOnUiThread {
                    binding.tvFirstTime.text = "目前時刻：" + SimpleDateFormat("HH:mm", Locale.US).format(Date())
                }
            }
        }

        fixedRateTimer("timer2", false, 0L,   1000) {
            if(isAdded) {
                requireActivity().runOnUiThread {
                    currentCount--
                    binding.progressFirstCountDown.progress = currentCount
                    if(currentCount==0) {
                        currentCount = Const.CountDown
                        viewModel_Airplane.setReload(true)
                    }
                    Utils.log("currentCount:" + currentCount)
                }
            }
        }
    }



    fun setTabHostLabel(tab: TabLayout.Tab, position: Int) {
        Utils.log("tab set "+position + ", tab.Position:" + tab.position)
        tab.text = when(position) {
            0 -> "起飛班機"
            else -> "抵達班機"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding?.pager?.adapter = null
        _binding = null
    }

}

class FragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    //var myData: List<Any> = data
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = when(position) {
            0 -> {
                DepartureFragment()
            }
            else -> {
                ArrivalFragment()
            }
        }
        return fragment
    }
}


