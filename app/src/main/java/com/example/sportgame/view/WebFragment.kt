package com.example.sportgame.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import com.example.sportgame.databinding.FragmentGameBinding
import com.example.sportgame.databinding.FragmentWebBinding
import com.example.sportgame.viewmodel.MainViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WebFragment : Fragment() {

    private lateinit var binding: FragmentWebBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding = FragmentWebBinding.inflate(inflater, container, false)

        binding.webView.settings.javaScriptEnabled = true

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }


        binding.closeWebBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewModel.apply {
            getPage()

            page.observe(viewLifecycleOwner){page ->
                binding.webView.loadUrl(page)

            }
        }

        return binding.root
    }

}