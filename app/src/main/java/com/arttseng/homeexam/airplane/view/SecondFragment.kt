package com.arttseng.homeexam.airplane.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.arttseng.homeexam.airplane.adapter.ImageAdapter
import com.arttseng.homeexam.airplane.databinding.FragmentSecondBinding
import com.arttseng.homeexam.airplane.datamodel.Attraction
import com.arttseng.homeexam.airplane.tools.openLink
import com.arttseng.homeexam.airplane.tools.toast
import com.arttseng.homeexam.airplane.viewmodel.MyViewModel

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var imageAdapter: ImageAdapter
    private val viewModel: MyViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    /*
    *"address": "104 台北市中山区玉門街1号",
      "official_site": "http://www.majisquare.com/",
      "facebook": "https://www.facebook.com/majisquare/",
      "category": [
        {
          "id": 15,
          "name": "アート展示・公演スペース"
        },
        {
          "id": 18,
          "name": "パブリックアート"
        },
        {
          "id": 24,
          "name": "テーマの商店街"
        }
      ],
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pos: Int? = arguments?.getInt("Position")
        pos?.let {
            val attraction = viewModel.attractData.value?.get(it) as Attraction

            attraction.apply {
                imageAdapter = ImageAdapter(this.images)
                binding.viewpagerDetail.adapter = imageAdapter
                binding.indicatorDetail.setViewPager(binding.viewpagerDetail)

                binding.tvDetailTitle.text = this.name
                binding.tvDetailDesc.text = this.introduction
                binding.tvDetailAddress.text = this.address
                binding.imgDetailOfficalsite.setOnClickListener {
                    if(this.officialSite.isNotEmpty()) {
                        requireActivity().openLink(this.officialSite)
                    } else {
                        requireActivity().toast("此景點無官網")
                    }

                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}