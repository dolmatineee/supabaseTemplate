package com.example.myapplication

import android.content.Context
import androidx.lifecycle.ViewModel
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json


class SupabaseClient : ViewModel() {



    private val supabase = createSupabaseClient(
        supabaseUrl = "https://paprxkkudtlnnrxoilet.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBhcHJ4a2t1ZHRsbm5yeG9pbGV0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzczMTY3MzMsImV4cCI6MjA1Mjg5MjczM30.RvQ-AS-C09UbOreWO0-RtMK4odrTLnI2RbTdd91E4Ik"
    ) {
        install(Auth)
        install(Postgrest)
        defaultSerializer = KotlinXSerializer(Json)
    }

    fun signUp(
        context: Context,
        userEmail: String,
        userPassword: String,
        userFullName: String,
        userPhone: String,
        userDate: String
    ) {
        viewModelScope.launch {
            try {
                supabase.auth.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                    data = buildJsonObject {
                        put("full_name", userFullName)
                        put("phone_number", userPhone)
                        put("date_of_birth", userDate)
                    }
                }
                val user = User(
                    uid = supabase.auth.currentUserOrNull()!!.id,
                    name = userFullName,
                    phone = userPhone,
                    date_of_birth = userDate
                )
                supabase.from("User").insert(user)
                saveToken(context)
                _userState.value = UserState.Success("Успешная регистрация!")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Ошибка: ${e.message}")
            }
        }
    }

    fun login(
        context: Context,
        userEmail: String,
        userPassword: String
    ) {
        viewModelScope.launch {
            try {
                supabase.auth.signInWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Успешная авторизация!")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Ошибка: ${e.message}")
            }
        }
    }

    fun sendEmailOtp(
        userEmail: String
    ) {
        viewModelScope.launch {
            try {
                supabase.auth.resetPasswordForEmail(userEmail)
                _userState.value = UserState.Success("Успешно")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Ошибка: ${e.message}")
            }
        }
    }

    fun checkOtp(
        code: String,
        userEmail: String
    ) {
        viewModelScope.launch {
            try {
                supabase.auth.verifyEmailOtp(
                    type = OtpType.Email.RECOVERY,
                    email = userEmail,
                    token = code
                )
                _userState.value = UserState.Success("Успешно")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Ошибка: ${e.message}")
            }
        }
    }

    fun updatePassword(userPassword: String) {
        viewModelScope.launch {
            try {
                supabase.auth.updateUser {
                    password = userPassword
                }
                _userState.value = UserState.Success("Успешно")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Ошибка: ${e.message}")
            }
        }
    }


}