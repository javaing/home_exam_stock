package com.arttseng.homeexamtravel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.arttseng.homeexamtravel.R
import com.arttseng.homeexamtravel.adapter.AttractAdapter
import com.arttseng.homeexamtravel.adapter.NewsAdapter
import com.arttseng.homeexamtravel.databinding.FragmentFirstBinding
import com.arttseng.homeexamtravel.tools.Utils
import com.arttseng.homeexamtravel.tools.getLocaleString
import com.arttseng.homeexamtravel.tools.setActivityTitle
import com.arttseng.homeexamtravel.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MyViewModel by activityViewModels()
    private lateinit var fragmentAdapter: FragmentAdapter
    //private lateinit var attractAdapter: AttractAdapter

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

        viewModel.userLang.observe(viewLifecycleOwner) { userLang ->
            viewModel.getAttract(userLang)
            viewModel.getNews(userLang)

            try {
                TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
                    //tab.text = "OBJECT ${(position + 1)}"
                    setTabHostLabel(tab, position)
                }.attach()
            } catch (e: Exception) {
                e.message?.let { Utils.log(it) }
            }
            Utils.log("change title to:" + requireActivity().getLocaleString(userLang, R.string.first_fragment_label))
            setActivityTitle(requireActivity().getLocaleString(userLang, R.string.first_fragment_label) )

        }
    }
    private fun initView() {

        fragmentAdapter = FragmentAdapter(this)
        binding.pager.adapter = fragmentAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            setTabHostLabel(tab, position)
        }.attach()
    }

    fun setTabHostLabel(tab: TabLayout.Tab, position: Int) {
        Utils.log("tab set "+position + ", tab.Position:" + tab.position)
        tab.text = when(position) {
            0 -> requireActivity().getLocaleString(viewModel.userLang.value, R.string.second_fragment_label)
            else -> requireActivity().getLocaleString(viewModel.userLang.value, R.string.lastest_news)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class FragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    //var myData: List<Any> = data
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = when(position) {
            0 -> {
                AttractFragment()
            }
            else -> {
                NewsFragment()
            }
        }
        return fragment
    }
}


