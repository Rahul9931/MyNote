package com.rahul.mynotes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rahul.mynotes.adapter.NoteAdapter
import com.rahul.mynotes.databinding.FragmentMainBinding
import com.rahul.mynotes.model.NoteResponse
import com.rahul.mynotes.utils.NetworkResult
import com.rahul.mynotes.viewModel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    private val noteViewModel by viewModels<NoteViewModel>()

    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        noteAdapter = NoteAdapter(::onNoteItemClicked)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel.getNotes()
        binding.rvNote.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvNote.adapter = noteAdapter
        bindObserver()
    }

    private fun bindObserver() {
        noteViewModel.notesLiveData.observe(viewLifecycleOwner, Observer{
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success -> {
                    Log.d("check_noteres", "note api res -> ${it.data}")
                    noteAdapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    Log.d("check_noteres", "note api error -> ${it.message}")
                    Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    fun onNoteItemClicked(note: NoteResponse){
        Toast.makeText(requireContext(), "${note.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}