package com.example.sportgame.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sportgame.R
import com.example.sportgame.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        val pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref.edit()


        if (requireArguments().getBoolean("flag")){
            val reward = requireArguments().getInt("stake") * 2
            binding.resultText.text = getString(R.string.win)
            binding.resultTextAdd.text = "+" + reward.toString() + " " + getString(R.string.money)
            binding.presentBtn.visibility = View.VISIBLE

            editor.putInt("money", reward + pref.getInt("money", 0))
            editor.apply()
        } else {
            binding.resultTextAdd.text = getString(R.string.loseAddText)
            binding.resultTextAdd.textSize = 20F
            binding.resultText.text = getString(R.string.lose)
        }

        binding.moneyTextViewResult.text = pref.getInt("money", 0).toString()

        binding.toMainFragmentBtn.setOnClickListener {
            setMainFragment()
        }

        binding.presentBtn.setOnClickListener {
            setWebFragment()
        }

        return binding.root

    }

    companion object {
        fun newInstance(bundle: Bundle) : ResultFragment {

            val resultFragment = ResultFragment()
            resultFragment.arguments = bundle
            return resultFragment
        }
    }

    private fun setWebFragment() {

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.placeholder, WebFragment())
            addToBackStack("result")
            commit()
        }
    }
    private fun setMainFragment() {

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.placeholder, SportType())
            commit()
        }
    }
}
