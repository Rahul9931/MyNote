package com.rahul.mynotes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.mynotes.model.NoteRequest
import com.rahul.mynotes.model.NoteResponse
import com.rahul.mynotes.repository.NoteRepository
import com.rahul.mynotes.repository.UserRepository
import com.rahul.mynotes.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository): ViewModel() {

    val notesLiveData: LiveData<NetworkResult<List<NoteResponse>>>
        get() = noteRepository.notesLiveData

    fun getNotes(){
        viewModelScope.launch {
            noteRepository.getNotes()
        }
    }

    fun createNote(noteRequest: NoteRequest){

    }

    fun updateNote(noteId: String, noteRequest: NoteRequest){

    }

    fun deleteNote(noteId: String){

    }
}