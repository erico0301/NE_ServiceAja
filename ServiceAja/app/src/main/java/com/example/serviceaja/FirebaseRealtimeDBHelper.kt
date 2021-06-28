package com.example.serviceaja

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.serviceaja.classes.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRealtimeDBHelper(val context: Context) {
    private val database = Firebase.database
    private val ref = database.reference

    fun addUser(user: User) {
        val key = ref.child("users").push().key

        if (key == null) {
            Log.e("Gagal melakukan registrasi", "Failed to register")
            return
        }

        ref.child("users").child(key).setValue(user).apply {
            addOnSuccessListener {
                ref.child("phone_nums").child(user.noTelp).child("user_key").setValue(key).apply {
                    addOnSuccessListener {
                        ref.child("email_addresses").child(user.email.replace(".", "(@)")).child("user_key").setValue(key).apply {
                            addOnSuccessListener { Log.e("Operasi Insert Database", "Berhasil melakukan input data!") }
                            addOnFailureListener { Log.e("Gagal di email", "Failed") }
                        }
                    }
                    addOnFailureListener { Log.e("Gagal di phone_num", "Failed") }
                }
            }
            addOnFailureListener { Log.e("Gagal di users", "Failed") }
        }
    }

    fun findUserByPhoneNumber(phone_num: String) {
        var user: User?
        ref.child("phone_nums").child(phone_num).child("user_key").get().addOnCompleteListener {
            if (it.result!!.exists()) {
                Log.e("Phone Number exists", "it's exists")
                ref.child("users").child(it.result!!.getValue(String::class.java)!!).get().addOnCompleteListener { result ->
                    user = result.result!!.getValue(User::class.java)
                    val intent = Intent(PHONE_NUM_USED)
                    intent.putExtra(EXTRA_USER, user)
                    context.sendBroadcast(intent)
                }
            }
            else
                context.sendBroadcast(Intent(PHONE_NUM_AVAILABLE))
        }.addOnFailureListener {
            Log.e("Result", "Failed to Read DB")
        }
    }

    fun findUserByEmailAddress(email: String) {
        var user: User?
        ref.child("email_addresses").child(email.replace(".", "(@)")).child("user_key").get().addOnCompleteListener {
            if (it.result!!.exists()) {
                Log.e("E-mail address exists", "it's exists")
                ref.child("users").child(it.result!!.getValue(String::class.java)!!).get().addOnCompleteListener { result ->
                    user = result.result!!.getValue(User::class.java)
                    val intent = Intent(EMAIL_ADDRESS_USED)
                    intent.putExtra(EXTRA_USER, user)
                    context.sendBroadcast(intent)
                }
            }
            else
                context.sendBroadcast(Intent(EMAIL_ADDRESS_AVAILABLE))
        }.addOnFailureListener {
            Log.e("Result", "Failed to Read DB")
        }
    }

    fun updateUser(userOldValue: User, userNewValue: User) {
        ref.child("phone_nums").child(userOldValue.noTelp).child("user_key").get().addOnCompleteListener {
            if (it.result!!.exists()) {
                Log.e("updateUser", "update")
                val key = it.result!!.value as String
                ref.child("users").child(key).setValue(userNewValue)
                if (userOldValue.email != userNewValue.email) {
                    ref.child("email_addresses").child(userOldValue.email.replace(".", "(@)")).removeValue()
                    ref.child("email_addresses").child(userNewValue.email.replace(".", "(@)")).child("user_key").setValue(key)
                }
                if (userOldValue.noTelp != userNewValue.noTelp) {
                    ref.child("phone_nums").child(userOldValue.noTelp).removeValue()
                    ref.child("phone_nums").child(userNewValue.noTelp).child("user_key").setValue(key)
                }
            }
        }
    }

    fun deleteUser(user: User) {
        ref.child("phone_nums").child(user.noTelp).child("user_key").get().addOnSuccessListener {
            if (it.exists()) {
                val key = it.getValue(String::class.java)!!
                ref.child("users").child(key).removeValue()
                ref.child("phone_nums").child(user.noTelp).removeValue()
                ref.child("email_addresses").child(user.email.replace(".", "(@)")).removeValue()
            }
        }
    }
    /*
        val pushValues = mutableMapOf(
                "/users/$key" to user,
                "/phone_nums/${user.noTelp}/user_key" to key,
                "/emails/${user.email}/user_key" to key
        )
        Log.e("Success registration", "Registration succeed")
        ref.updateChildren(pushValues).apply {
            addOnSuccessListener { Toast.makeText(context, "Akun Berhasil Terdaftar", Toast.LENGTH_SHORT).show() }
            addOnFailureListener { Toast.makeText(context, "Gagal melakukan Registrasi. Coba sesaat lagi.", Toast.LENGTH_SHORT).show() }
        }*/
}