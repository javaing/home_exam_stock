package com.arttseng.homeexamtravel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arttseng.homeexamtravel.R
import com.arttseng.homeexamtravel.adapter.NewsAdapter
import com.arttseng.homeexamtravel.tools.getLocaleString
import com.arttseng.homeexamtravel.viewmodel.MyViewModel

class NewsFragment: Fragment() {

    private lateinit var newsAdapter : NewsAdapter
    private val viewModel: MyViewModel by activityViewModels()

    private lateinit var progresBar : ProgressBar
    private lateinit var tv_noData :TextView
    private lateinit var recyclerView :RecyclerView

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

        viewModel.userLang.observe(viewLifecycleOwner) { userLang ->
            progresBar.visibility = View.VISIBLE
            tv_noData.text = requireActivity().getLocaleString(userLang, R.string.no_data)
        }
        viewModel.newsData.observe(viewLifecycleOwner) { list ->
            print("art fragment list size:" + list.size)

            progresBar.visibility = View.GONE
            if(list.isEmpty()) {
                recyclerView.visibility = View.GONE
                tv_noData.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                tv_noData.visibility = View.GONE
            }

            newsAdapter.updateList(ArrayList(list))
        }

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