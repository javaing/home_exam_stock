package com.arttseng.homeexam.airplane.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arttseng.homeexam.airplane.databinding.FragmentWebviewBinding
import com.arttseng.homeexam.airplane.tools.setActivityTitle

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class WebViewFragment : Fragment() {

    private var _binding: FragmentWebviewBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userUrl = arguments?.getString("UserURL")
        if(userUrl.isNullOrBlank()) userUrl = "www.google.com"
        binding.webView.settings.apply {
            javaScriptEnabled = true
        }
        binding.webView.loadUrl(userUrl)
        setActivityTitle("")

//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}