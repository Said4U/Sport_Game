package com.example.sportgame.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sportgame.R
import com.example.sportgame.databinding.FragmentStakeBinding

class StakeFragment : Fragment() {

    private lateinit var binding: FragmentStakeBinding
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var pref: SharedPreferences
    private var ourTeam = 0
    private var stake = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentStakeBinding.inflate(inflater, container, false)

        pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        editor = pref.edit()

        binding.moneyTextViewStake.text = pref.getInt("money", 0).toString()

        ourTeam = pref.getInt("ourTeam", 0)
        var opponent = pref.getInt("opponent", 0)

        if (opponent == 0) {
            opponent = (1..6).random()
        }

        binding.startMatchBtn.setOnClickListener {
            stake = binding.stakeEdit.text.toString()

            if (check(ourTeam)){
                editor.putInt("money", pref.getInt("money", 0) - stake.toInt())
                setGameFragment(
                    opponent = opponent,
                    ourTeam = ourTeam,
                    stake = stake.toInt(),
                    sportType = requireArguments().getString("sportType").toString()
                )
            }
        }

        binding.countryBtn.setOnClickListener {
            setCountryFragment(opponent)
        }

        binding.ourTeamCard.setOnClickListener {
            setCountryFragment(opponent)
        }

        binding.opponentImage.setImageResource(getDrawableFlag(opponent))

        binding.ourTeamImage.setImageResource(getDrawableFlag(ourTeam))

        editor.remove("ourTeam")
        editor.apply()

        return binding.root
    }

    private fun setGameFragment(opponent: Int, ourTeam: Int, stake: Int, sportType: String) {
        editor.remove("opponent")
        editor.apply()

        val bundle = Bundle()
        bundle.putInt("opponent", opponent)
        bundle.putInt("ourTeam", ourTeam)
        bundle.putInt("stake", stake)
        bundle.putString("sportType", sportType)

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.placeholder, GameFragment.newInstance(bundle))
            commit()
        }
    }

    private fun setCountryFragment(opponent: Int) {
        editor.putInt("opponent", opponent)
        editor.apply()

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.placeholder, CountryFragment())
            addToBackStack("stake")
            commit()
        }
    }

    private fun getDrawableFlag(number: Int): Int {
        val country = when (number) {
            1 -> R.drawable.flag_1
            2 -> R.drawable.flag_2
            3 -> R.drawable.flag_3
            4 -> R.drawable.flag_4
            5 -> R.drawable.flag_5
            6 -> R.drawable.flag_6
            else -> R.drawable.default_team
        }

        return country
    }

    private fun check(ourTeam: Int): Boolean {
        // Проверка ставки на корректность
        var result = false
        if (stake == "") {
            Toast.makeText(requireContext(), getString(R.string.needStake), Toast.LENGTH_SHORT).show()
        } else {
            if (stake.toInt() > pref.getInt("money", 0)) {
                Toast.makeText(requireContext(), getString(R.string.lessStake), Toast.LENGTH_SHORT).show()
            } else {
                // Проверка на то, что команда выбрана
                if (ourTeam == 0) {
                    Toast.makeText(requireContext(), getString(R.string.team), Toast.LENGTH_SHORT).show()
                } else {
                        result = true
                }

            }
        }
        return result
    }

    companion object {
        fun newInstance(bundle: Bundle) : StakeFragment {

            val stakeFragment = StakeFragment()
            stakeFragment.arguments = bundle
            return stakeFragment
        }
    }
}