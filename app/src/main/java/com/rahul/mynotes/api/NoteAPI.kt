package com.rahul.mynotes.api

import com.rahul.mynotes.model.NoteRequest
import com.rahul.mynotes.model.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteAPI {

    @GET(ENDPOINT_NOTE)
    suspend fun getNote(@Body noteRequest: NoteRequest): Response<NoteResponse>

    @POST(ENDPOINT_NOTE)
    suspend fun createNote(@Body noteRequest: NoteRequest): Response<NoteResponse>

    @PUT(ENDPOINT_NOTE_UPDATE_OR_DELETE)
    suspend fun updateNote(@Path("noteId") noteId: String): Response<NoteResponse>

    @DELETE(ENDPOINT_NOTE_UPDATE_OR_DELETE)
    suspend fun deleteNote(@Path("noteId") noteId: String): Response<NoteResponse>
}



const val ENDPOINT_NOTE = "/note"
const val ENDPOINT_NOTE_UPDATE_OR_DELETE = "/note/{noteId}"