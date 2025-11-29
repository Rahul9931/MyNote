package com.rahul.mynotes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rahul.mynotes.api.NoteAPI
import com.rahul.mynotes.model.NoteRequest
import com.rahul.mynotes.model.NoteResponse
import com.rahul.mynotes.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteApi: NoteAPI) {

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val notesLiveData: LiveData<NetworkResult<List<NoteResponse>>>
        get() = _notesLiveData

    private val _noteStatusLiveData = MutableLiveData<NetworkResult<Pair<Boolean, String>>>()
    val noteStatusLiveData: LiveData<NetworkResult<Pair<Boolean, String>>>
        get() = _noteStatusLiveData

    suspend fun getNotes(){
        try {
            _notesLiveData.postValue(NetworkResult.Loading())
            var response = noteApi.getNote()
            if (response.isSuccessful && response.body() != null){
                _notesLiveData.postValue(NetworkResult.Success(response.body()!!))
            }
            else if(response.errorBody() != null){
                var errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _notesLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
            }
            else{
                _notesLiveData.postValue(NetworkResult.Error("Something went wrong"))
            }
        }
        catch (e: Exception){
            _notesLiveData.postValue(NetworkResult.Error(e.localizedMessage))
        }

    }

    suspend fun createNote(noteRequest: NoteRequest){
        _noteStatusLiveData.postValue(NetworkResult.Loading())
        var response = noteApi.createNote(noteRequest)
        handleResponse(response, "Note Created")
    }

    suspend fun updateNote(noteId: String, noteRequest: NoteRequest){
        _noteStatusLiveData.postValue(NetworkResult.Loading())
        var response = noteApi.updateNote(noteId,noteRequest)
        handleResponse(response, "Note Updated")
    }

    suspend fun deleteNote(noteId: String){
        _noteStatusLiveData.postValue(NetworkResult.Loading())
        var response = noteApi.deleteNote(noteId)
        handleResponse(response, "Note Deleted")
    }

    private fun handleResponse(response: Response<NoteResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _noteStatusLiveData.postValue(NetworkResult.Success(Pair(true, message)))
        }
        else{
            _noteStatusLiveData.postValue(NetworkResult.Success(Pair(false, "Something went wrong")))
        }
    }

}