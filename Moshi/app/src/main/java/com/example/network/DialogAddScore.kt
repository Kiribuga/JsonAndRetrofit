package com.example.network

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.network.adapterRecyclerView.AdapterMovie
import com.example.network.data.RemoteMovie
import com.example.network.utils.autoCleared
import kotlinx.android.synthetic.main.add_score_dialog.*

class DialogAddScore : DialogFragment(R.layout.add_score_dialog) {

    private val viewModelMovie: ViewModelMovie by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addScoreButton.isEnabled = false

        sourceScore.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                addScoreButton.isEnabled = s?.isNotEmpty() ?: false && valueScore.length() != 0
            }
        })

        valueScore.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                addScoreButton.isEnabled = s?.isNotEmpty() ?: false && sourceScore.length() != 0
            }
        })

        addScoreButton.setOnClickListener {
            viewModelMovie.serializeMovie(
                mutableMapOf(Pair(sourceScore.text.toString(), valueScore.text.toString()))
            )
            dismiss()
        }

        cancelButton.setOnClickListener { dismiss() }
    }
}