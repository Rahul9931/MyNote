package com.rahul.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.rahul.mynotes.R
import com.rahul.mynotes.databinding.FragmentNoteBinding
import com.rahul.mynotes.model.NoteResponse

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeData()
    }

    private fun initializeData() {
        var jsonNote = arguments?.getString("note")
        if (jsonNote != null){
            var note = Gson().fromJson(jsonNote, NoteResponse::class.java)
            note?.let {
                binding.edtTitle.setText(note.title)
                binding.edtDesc.setText(note.description)
            }
        }
        else{
            binding.txtAddeditTitle.text = resources.getString(R.string.add_note)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}