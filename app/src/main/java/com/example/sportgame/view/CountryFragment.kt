package com.example.sportgame.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sportgame.databinding.FragmentCountryBinding


class CountryFragment : Fragment() {

    private lateinit var binding: FragmentCountryBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryBinding.inflate(inflater, container, false)

        val pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)

        binding.moneyTextViewCountry.text = pref.getInt("money", 0).toString()

        binding.flagCardView1.setOnClickListener {
            choiceCountry(1)
        }
        binding.flagCardView2.setOnClickListener {
            choiceCountry(2)
        }
        binding.flagCardView3.setOnClickListener {
            choiceCountry(3)
        }
        binding.flagCardView4.setOnClickListener {
            choiceCountry(4)
        }
        binding.flagCardView5.setOnClickListener {
            choiceCountry(5)
        }
        binding.flagCardView6.setOnClickListener {
            choiceCountry(6)
        }

        return binding.root
    }

    private fun choiceCountry(number: Int){
        val pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE).edit()
        pref.putInt("ourTeam", number)
        pref.apply()
        parentFragmentManager.popBackStack()
    }

}