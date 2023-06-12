package com.example.sportgame.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.sportgame.R
import com.example.sportgame.databinding.FragmentGameBinding
import com.example.sportgame.viewmodel.MainViewModel

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var time: String
    private var isWin = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)

        when(requireArguments().getString("sportType")){
            "football" ->  binding.gameFragment.setBackgroundResource(R.drawable.football_background)
            "tennis" ->  binding.gameFragment.setBackgroundResource(R.drawable.tennis_backgroud)
            "hockey" ->  binding.gameFragment.setBackgroundResource(R.drawable.hockey_background)
            else -> binding.gameFragment.setBackgroundResource(R.drawable.boxing_background)

        }

        val pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)

        binding.moneyTextViewGame.text = pref.getInt("money", 0).toString()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        initObservers()

        binding.continueBtn.setOnClickListener {
            setCurrentFragment(
                isWin =  isWin,
                stake = requireArguments().getInt("stake")
            )
        }

        binding.opponentImageGame.setImageResource(getDrawableFlag(requireArguments().getInt("opponent")))

        binding.ourTeamImageGame.setImageResource(getDrawableFlag(requireArguments().getInt("ourTeam")))

        return binding.root
    }


    private fun initObservers() {

        viewModel.apply {
            startTimer()
            var opponentScore = 0
            var ourScore = 0

            while (opponentScore == ourScore){
                opponentScore = (0..5).random()
                ourScore = (0..5).random()
            }

            val totalScore = "$opponentScore:$ourScore"
            isWin = ourScore > opponentScore

            seconds.observe(viewLifecycleOwner) { seconds ->
                time = seconds.toString()

                if (seconds < 10) time = "0$seconds"

                if (seconds == 0L){
                    binding.score.text = totalScore
                    binding.scoreText.visibility = View.VISIBLE
                    binding.continueBtn.visibility = View.VISIBLE
                }
                binding.timer2.text = time
            }
        }
    }

    private fun setCurrentFragment(isWin: Boolean, stake: Int) {
        val bundle = Bundle()
        bundle.putBoolean("flag", isWin)
        bundle.putInt("stake", stake)

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.placeholder, ResultFragment.newInstance(bundle))
            commit()
        }

    }

    companion object {
        fun newInstance(bundle: Bundle) : GameFragment {

            val gameFragment = GameFragment()
            gameFragment.arguments = bundle
            return gameFragment
        }
    }

    private fun getDrawableFlag(number: Int): Int {
        val country = when(number){
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

}