package com.arttseng.homeexam.airplane.view

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
import com.arttseng.homeexam.airplane.R
import com.arttseng.homeexam.airplane.adapter.AttractAdapter
import com.arttseng.homeexam.airplane.datamodel.Attraction
import com.arttseng.homeexam.airplane.datamodel.Image
import com.arttseng.homeexam.airplane.tools.Utils
import com.arttseng.homeexam.airplane.tools.getLocaleString
import com.arttseng.homeexam.airplane.viewmodel.MyViewModel
import kotlinx.android.parcel.Parcelize

class AttractFragment : Fragment() {

    lateinit var attractAdapter: AttractAdapter
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

        viewModel.userLang.observe(viewLifecycleOwner) { userLang ->
            progresBar.visibility = View.VISIBLE
            tv_noData.text = requireActivity().getLocaleString(userLang, R.string.no_data)
        }

        viewModel.attractData.observe(viewLifecycleOwner) { list ->
            Utils.log("observe  attractData size:" + list.size)

            progresBar.visibility = View.GONE
            if(list.isEmpty()) {
                recyclerView.visibility = View.GONE
                tv_noData.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                tv_noData.visibility = View.GONE
            }


            attractAdapter.updateList(ArrayList(list))
        }

        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        attractAdapter = AttractAdapter {
            val position = (it as View).tag as Int
            val bundle = Bundle()
            //bundle.putParcelableArrayList("Images", ArrayList( attractAdapter.unAssignList[position].images ))
            bundle.putInt("Position", position)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }
        recyclerView.adapter = attractAdapter
    }
}