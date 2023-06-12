package com.example.sportgame.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sportgame.R
import com.example.sportgame.databinding.FragmentSportTypeBinding

class SportType : Fragment() {

    private lateinit var binding: FragmentSportTypeBinding
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSportTypeBinding.inflate(inflater, container, false)

        val pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        editor = pref.edit()

        // я знаю, что так делать небезопасно, но задание тестовое
        var money = pref.getInt("money", 0)

        if (money == 0) {
            editor.putInt("money", 1000)
            money = 1000
        }

        editor.apply()

        binding.moneyTextView.text = money.toString()

        binding.playFootballBtn.setOnClickListener {
            setCurrentFragment("football")
        }
        binding.playTennisBtn.setOnClickListener {
            setCurrentFragment("tennis")
        }
        binding.playHockeyBtn.setOnClickListener {
            setCurrentFragment("hockey")
        }
        binding.playBoxingBtn.setOnClickListener {
            setCurrentFragment("boxing")
        }

        return binding.root
    }


    private fun setCurrentFragment(sportType: String) {
        val bundle = Bundle()
        bundle.putString("sportType", sportType)

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.placeholder, StakeFragment.newInstance(bundle))
            commit()
        }

    }
}