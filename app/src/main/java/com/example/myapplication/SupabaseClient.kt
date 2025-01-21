package com.example.myapplication

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Count
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put


class SupabaseClient : ViewModel() {



    private val supabase = createSupabaseClient(
        supabaseUrl = "https://paprxkkudtlnnrxoilet.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBhcHJ4a2t1ZHRsbm5yeG9pbGV0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzczMTY3MzMsImV4cCI6MjA1Mjg5MjczM30.RvQ-AS-C09UbOreWO0-RtMK4odrTLnI2RbTdd91E4Ik"
    ) {
        install(Auth)
        install(Postgrest)
        defaultSerializer = KotlinXSerializer(Json)
    }

    private val _sneakers = MutableLiveData<List<Sneaker>>()
    val sneakers: LiveData<List<Sneaker>> get() = _sneakers

    private val _photos = MutableLiveData<Map<Int, String>>()
    val photos: LiveData<Map<Int, String>> get() = _photos

    fun loadSneakers() {
        viewModelScope.launch {
            val sneakers = supabase.from("sneakers").select().decodeList<Sneaker>()
            val photos = supabase.from("sneaker_photos").select().decodeList<SneakerPhoto>()
            val photoMap = photos.associate { it.sneaker_id to it.photo_url }
            _sneakers.value = sneakers
            _photos.value = photoMap
        }
    }


}