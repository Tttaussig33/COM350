package com.example.fragmentsexample

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.fragmentsexample.databinding.FragmentOneBinding

class FragmentOne : Fragment(), SeekBar.OnSeekBarChangeListener {

    var seekValue = 10
    var activityCallback: FragmentOne.ToolbarListener? = null

    interface ToolbarListener {
        fun onButtonClick(fontSize: Int, text: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            activityCallback = context as ToolbarListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement ToolbarListener")
        }
    }
    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.seekBar1.setOnSeekBarChangeListener(this)
        binding.button1.setOnClickListener {v: View -> buttonClicked(v)}
    }

    private fun buttonClicked(view: View) {
        activityCallback?.onButtonClick(seekValue, binding.editText1.text.toString())
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        seekValue = progress
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}