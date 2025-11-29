package com.rahul.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.rahul.mynotes.R
import com.rahul.mynotes.databinding.FragmentNoteBinding
import com.rahul.mynotes.model.NoteRequest
import com.rahul.mynotes.model.NoteResponse
import com.rahul.mynotes.utils.NetworkResult
import com.rahul.mynotes.viewModel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    val binding get() = _binding!!

    private var note: NoteResponse? = null

    private val noteViewModel by viewModels<NoteViewModel>()

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
        bindHandler()
        bindObserver()
    }

    private fun bindObserver() {
        noteViewModel.noteStatusLiveData.observe(viewLifecycleOwner, Observer{
            when(it){
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
            }
        })
    }

    private fun bindHandler() {
        binding.imgDelete.setOnClickListener {
            noteViewModel.deleteNote(note!!._id)
        }

        binding.btnSubmit.setOnClickListener {
            var title = binding.edtTitle.text.toString().trim()
            var description = binding.edtDesc.text.toString().trim()

            if (note != null){
                noteViewModel.updateNote(note!!._id, NoteRequest(description, title))
            }
            else{
                noteViewModel.createNote(NoteRequest(description, title))
            }
        }
    }

    private fun initializeData() {
        var jsonNote = arguments?.getString("note")
        if (jsonNote != null){
            note= Gson().fromJson(jsonNote, NoteResponse::class.java)
            note?.let {
                binding.edtTitle.setText(note!!.title)
                binding.edtDesc.setText(note!!.description)
            }
        }
        else{
            binding.imgDelete.visibility = View.GONE
            binding.txtAddeditTitle.text = resources.getString(R.string.add_note)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}