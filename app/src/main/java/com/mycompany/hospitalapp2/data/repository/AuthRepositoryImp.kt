package com.mycompany.hospitalapp2.data.repository

import android.content.ContentValues
import android.content.SharedPreferences
import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.mycompany.hospitalapp2.data.model.User
import com.mycompany.hospitalapp2.util.FireStoreCollection
import com.mycompany.hospitalapp2.util.FirestoreTables
import com.mycompany.hospitalapp2.util.SharedPrefConstants
import com.mycompany.hospitalapp2.util.UiState
import java.util.ArrayList

class AuthRepositoryImp(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore,
    val appPreferences: SharedPreferences,
    val gson: Gson
) : AuthRepository {
    override fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    user.id = it.result.user?.uid ?: ""
                    updateUserInfo(user){ state ->
                        when(state){
                            is UiState.Success -> {
                                storeSession(id = it.result.user?.uid ?: "") {
                                    if (it == null){
                                        result.invoke(UiState.Failure("Пользователь успешно зарегистрирован, но сессия не сохранена "))
                                    }else{
                                        result.invoke(
                                            UiState.Success("Пользователь успешно зарегистрирован")
                                        )
                                    }
                                }
                            }
                            is UiState.Failure ->{
                                result.invoke(UiState.Failure(state.error))
                            }
                        }
                    }
                }else{
                    try {
                        throw it.exception ?: java.lang.Exception("Ошибка входа")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure("Ошибка входа, Пароль должен содержать как минимум 6 символов"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure("Ошибка входа, Неверная почта"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure("Ошибка входа, Почта уже зарегистрирована"))
                    } catch (e: Exception) {
                        result.invoke(UiState.Failure(e.message))
                    }
                }
            }
            .addOnFailureListener{
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun updateUserInfo(user: User, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.USER).document(user.id)
        document
            .set(user)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Информация о пользователе успешно обновлена")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storeSession(id = task.result.user?.uid ?: ""){
                        if (it == null){
                            result.invoke(UiState.Failure("Ошибка сессии"))
                        }else{
                            result.invoke(UiState.Success("Вход выполнен успешно"))
                        }
                    }
                }
            }.addOnFailureListener {
                result.invoke(UiState.Failure("Ошибка входа"))
            }
    }

    override fun logout(result: () -> Unit) {
        auth.signOut()
        appPreferences.edit().putString(SharedPrefConstants.USER_SESSION,null).apply()
        result.invoke()
    }

    override fun storeSession(id: String, result: (User?) -> Unit) {
        database.collection(FireStoreCollection.USER).document(id)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val user = it.result.toObject(User::class.java)
                    appPreferences.edit().putString(SharedPrefConstants.USER_SESSION,gson.toJson(user)).apply()
                    result.invoke(user)
                }else{
                    result.invoke(null)
                }
            }
            .addOnFailureListener {
                result.invoke(null)
            }
    }

    override fun getSession(result: (User?) -> Unit) {
        val user_str = appPreferences.getString(SharedPrefConstants.USER_SESSION,null)
        if (user_str == null){
            result.invoke(null)
        }else{
            val user = gson.fromJson(user_str,User::class.java)
            result.invoke(user)
        }
    }
/*
   override fun getUserName(result: (String) -> Unit){
       val auth = FirebaseAuth.getInstance()
       val cu = auth.currentUser!!.uid
       database.collection(FirestoreTables.USER).whereEqualTo("id", cu )
           .get()
           .addOnSuccessListener {
               val users = arrayListOf<User>()
               for (document in it){
                   val user = document.toObject(User::class.java)
                   users.add(user)
               }
               result.invoke(
                   UiState.Success(users)
               )
           }
           .addOnFailureListener(){
               result.invoke(
                   UiState.Failure(it.localizedMessage)
               )
           }
   }

 */
    override fun getUserName(result: (UiState<List<User>>) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        val cu = auth.currentUser!!.uid
        Log.d(ContentValues.TAG, cu)
        database.collection(FirestoreTables.USER).whereEqualTo("id", cu)
            .get()
            .addOnSuccessListener {
                val users = arrayListOf<User>()
                for (document in it){
                    val user = document.toObject(User::class.java)
                    Log.d(ContentValues.TAG, user.toString())
                    users.add(user)
                }
                result.invoke(
                    UiState.Success(users)
                )
            }
            .addOnFailureListener(){
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }
    override fun updateUser(user: User, result: (UiState<String>) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        val cu = auth.currentUser!!.uid
        val document = database.collection(FirestoreTables.USER).document(cu)
        document
            .update(
                "name",user.name,
                "birth_date", user.birth_date,
                "policy", user.policy,
                "phone", user.phone,
                "email", user.email,
                "passport", user.passport
            )
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Информация успешно обновлена")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }
}

