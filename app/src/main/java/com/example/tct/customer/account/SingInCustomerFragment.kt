package com.example.tct.customer.account

import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.tct.R
import com.example.tct.customer.news.NewsCustomerFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SingInCustomerFragment: Fragment() {
    private lateinit var viewOfLayout: View
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_sing_in_up, container, false)

        //иницилизация аутентификация в Firebase и sharedPreferences
        auth = Firebase.auth
        fStore = FirebaseFirestore.getInstance()//подключению к FireStore
        sharedPreferences = requireActivity().getSharedPreferences("user data", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        //переход к регистрации
        val goToSingUp = viewOfLayout.findViewById<TextView>(R.id.goToSingUp)
        goToSingUp.setOnClickListener {
            loadFragment(SingUpFragment())
        }
        //иницилизируем поля ввода
        val  email_input = viewOfLayout.findViewById<TextInputLayout>(R.id.email_input)
        val  password_input = viewOfLayout.findViewById<TextInputLayout>(R.id.password_input)
        val email_edit = viewOfLayout.findViewById<TextInputEditText>(R.id.email_edit)
        val password_edit = viewOfLayout.findViewById<TextInputEditText>(R.id.password_edit)

        //иницилизация кнопки для авторизации и метод при нажатие
        val singIn= viewOfLayout.findViewById<Button>(R.id.button_sing_in) as Button
        singIn.setOnClickListener{
            //получаем введенные данные
            val email = email_edit.text.toString()
            val password = password_edit.text.toString()

           // email_input.isErrorEnabled = false
            password_input.isErrorEnabled = false
            email_input.isErrorEnabled = false
            //валидация полей
            if (email == "") {
                email_input.error = resources.getString(R.string.sing_in_hint_email)
            } else if (password == "") {
                password_input.error = resources.getString(R.string.sing_in_hint_password)
            } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                email_input.error = resources.getString(R.string.error_msg_email_in)
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "signInWithEmail:success")
                            val userId = auth.currentUser!!.uid
                            //подключаемся и получаем информацию из FireStore
                            val dr: DocumentReference = fStore.collection("users").document(userId)
                            dr.get().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val document = task.result
                                    if (document.exists()) {
                                        //запись в настройки
                                        editor!!.putString("LOGIN", "true")
                                        editor!!.putString("userId", userId)
                                        editor!!.putString("email", email)
                                        editor!!.putString("phone", document.getString("Phone"))
                                        editor!!.putString("name", document.getString("Name"))
                                        editor!!.putString("branch", document.getString("Branch"))
                                        editor!!.putString("pas", password)
                                        editor!!.commit()
                                        loadFragment(MainProfileFragment())
                                    } else {
                                        Log.d(TAG, "No such document")
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.exception)
                                }
                            }
                        } else {
                            //ошибка при авторизацие
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            email_input.error = resources.getString(R.string.error_msg_email)
                            password_input.error = resources.getString(R.string.error_msg_phone)
                        }
                    }
            }
        }

        //кнопка для востановления пароля
        val btnResetPassword = viewOfLayout.findViewById<TextView>(R.id.reset_password_label)
        btnResetPassword.setOnClickListener {
            showDialog()
        }
        return viewOfLayout
    }

    //метод вызова диалогового окна для сброса пароля
    fun showDialog(){
        //инцилизурем созданное окно для сброса пароля
        val dialogConstraintLayout = requireActivity().findViewById<ConstraintLayout>(R.id.dialog_Constraint_Layout)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.reset_password_dialog, dialogConstraintLayout)
        val closeBth = view.findViewById<TextView>(R.id.close_btn)
        val resetBth = view.findViewById<TextView>(R.id.reset_btn)
        val email_rest_input = view.findViewById<TextInputLayout>(R.id.email_rest_input)
        val email_reset_edit = view.findViewById<TextInputEditText>(R.id.email_reset_edit)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)
        val alertDialog = builder.create()
        //кнопка закрыть окно
        closeBth.setOnClickListener {
            alertDialog.dismiss()
        }
        //кнопка для подтверждения сбора
        resetBth.setOnClickListener {
            //получаем введенные данные
            val email = email_reset_edit.text.toString()
            val pattern = Pattern.compile("[a-z0-9.]+[@][a-z0-9]+[.][a-z]{1,3}")
            //проверка вводимого
            val matcher = pattern.matcher(email)
            val match = matcher.matches()
            email_rest_input.isErrorEnabled = false
            //валидация полей
            if (email == "") {
                email_rest_input.error = resources.getString(R.string.sing_in_hint_email)
            } else if(!match){
                email_rest_input.error = resources.getString(R.string.error_msg_email_in)
            } else {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent.")
                            Toast.makeText(requireActivity(), resources.getString(R.string.msg_reset_true), Toast.LENGTH_LONG).show()
                        }
                        else{
                            Toast.makeText(requireActivity(), resources.getString(R.string.msg_reset_false), Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
        if(alertDialog.window!=null){
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        alertDialog.show()
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_account,fragment)
        transaction?.commit()
    }
}