package com.arttseng.homeexamtravel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.arttseng.homeexamtravel.adapter.AttractAdapter
import com.arttseng.homeexamtravel.adapter.NewsAdapter
import com.arttseng.homeexamtravel.databinding.FragmentFirstBinding
import com.arttseng.homeexamtravel.tools.Utils
import com.arttseng.homeexamtravel.tools.getLocaleString
import com.arttseng.homeexamtravel.viewmodel.MyViewModel
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

        initView()
        initVM()
    }
    private fun initVM() {
        viewModel.getNews()
        viewModel.getAttract()
//        viewModel.attractData.observe(viewLifecycleOwner) { list ->
//            print("art fragment list size:" + list.size)
//            //fragmentAdapter.myData = list
//        }
        viewModel.userLang.observe(viewLifecycleOwner) { userLang ->
            viewModel.getAttract(userLang)
            viewModel.getNews(userLang)
            //activity?.getLocaleString(userLang, R.string.first_fragment_label)
            //setActivityTitle(R.string.first_fragment_label)
        }
    }
    private fun initView() {
//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }

        fragmentAdapter = FragmentAdapter(this)
        binding.pager.adapter = fragmentAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
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
                Utils.log("fragment 0")
//                val list = arrayListOf<Attraction>()
//                myData.forEach { list.add(it as Attraction) }
                AttractFragment()
            }
            else -> {
//                val list = arrayListOf<NewsData>()
//                myData.forEach { list.add(it as NewsData) }
                NewsFragment()
            }
        }
//        val fragment = if(myData.get(0) is NewsData) {
//            val list = arrayListOf<NewsData>()
//            myData.forEach { list.add(it as NewsData) }
//            NewsFragment(list)
//        }
//        else if(myData.get(0) is Attraction) {
//            val list = arrayListOf<Attraction>()
//            myData.forEach { list.add(it as Attraction) }
//            AttractFragment(list)
//        }
//        else {
//            NewsFragment(listOf())
//        }
//        val fragment =
//        fragment.arguments = Bundle().apply {
//            // The object is just an integer.
//            putInt(ARG_OBJECT, position + 1)
//        }
        return fragment
    }
}

// Instances of this class are fragments representing a single
// object in the collection.
class NewsFragment: Fragment() {

    lateinit var newsAdapter : NewsAdapter
    private val viewModel: MyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_collection_object, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.userLang.observe(viewLifecycleOwner) { userLang ->
            viewModel.getNews(userLang)
            //activity?.getLocaleString(userLang, R.string.first_fragment_label)
            //setActivityTitle(R.string.first_fragment_label)
        }
        viewModel.newsData.observe(viewLifecycleOwner) { list ->
            print("art fragment list size:" + list.size)
            newsAdapter.updateList(ArrayList(list))
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        newsAdapter = NewsAdapter {
            val position = (it as View).tag as Int
            val bundle = Bundle()
            bundle.putString("UserURL", newsAdapter.unAssignList[position].url)
            findNavController().navigate(R.id.action_FirstFragment_to_WebViewFragment, bundle)
        }
        recyclerView.adapter = newsAdapter
    }
}

class AttractFragment : Fragment() {

    lateinit var attractAdapter: AttractAdapter
    private val viewModel: MyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_collection_object, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.attractData.observe(viewLifecycleOwner) { list ->
            Utils.log("observe  attractData size:" + list.size)
            attractAdapter.updateList(ArrayList(list))
        }
//            viewModel.userLang.observe(viewLifecycleOwner) { userLang ->
//                viewModel.getAttract(userLang)
//                viewModel.getNews(userLang)
//                activity?.getLocaleString(userLang, R.string.first_fragment_label)
//                //setActivityTitle(R.string.first_fragment_label)
//            }
//            viewModel.newsData.observe(viewLifecycleOwner) { list ->
//                print("art fragment list size:" + list.size)
//                //fragmentAdapter.myData = list
//            }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        attractAdapter = AttractAdapter {
            val position = (it as View).tag as Int
            val bundle = Bundle()
            bundle.putString("UserURL", attractAdapter.unAssignList[position].url)
            findNavController().navigate(R.id.action_FirstFragment_to_WebViewFragment, bundle)
        }
        recyclerView.adapter = attractAdapter
    }
}
