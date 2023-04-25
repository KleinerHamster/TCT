package com.example.tct.customer.account

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.tct.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


class SingUpFragment : Fragment() {
    private lateinit var viewOfLayout: View
    var mAuth: FirebaseAuth? = null
    var userId: String? = null
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    val branch = arrayOf("Выберите филиал","Санкт-Петербург","Москва", "Екатеринбург","Краснодар",
        "Тюмень", "Пермь", "Новосибирск", "Уфа", "Самара", "Челябинск")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_sing_up, container, false)
        var branchSelected:String = branch[0]
        //инцилизируем выпадающий список
        val spinner = viewOfLayout.findViewById<Spinner>(R.id.spinner_branch)
        //устанавливаем адаптер с данными и разметкой для выпадающего списка
        var adapter = ArrayAdapter(requireContext(), R.layout.selected_item_spinner, branch)
        adapter.setDropDownViewResource(R.layout.drop_down_item_spinner)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                branchSelected= branch[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        //иницилизируем подключения к БД и sharedPreferences
        mAuth = FirebaseAuth.getInstance()
        sharedPreferences = requireActivity().getSharedPreferences("user data", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        //иницилизируем поля ввода
        val  name_edit = viewOfLayout.findViewById<TextInputEditText>(R.id.name_edit)
        val  email_edit = viewOfLayout.findViewById<TextInputEditText>(R.id.email_edit)
        val  phone_edit = viewOfLayout.findViewById<TextInputEditText>(R.id.phone_edit)
        val  password_edit = viewOfLayout.findViewById<TextInputEditText>(R.id.password_edit)
        val  password_repeat_edit = viewOfLayout.findViewById<TextInputEditText>(R.id.password_repeat_edit)

        val  name_input = viewOfLayout.findViewById<TextInputLayout>(R.id.name_input)
        val  email_input = viewOfLayout.findViewById<TextInputLayout>(R.id.email_input)
        val  phone_input = viewOfLayout.findViewById<TextInputLayout>(R.id.phone_input)
        val  password_input = viewOfLayout.findViewById<TextInputLayout>(R.id.password_input)
        val  password_repeat_input = viewOfLayout.findViewById<TextInputLayout>(R.id.password_repeat_input)

        //иницилизация кнопки для регистарции и метод при нажатие
        val  button_sing_up = viewOfLayout.findViewById<Button>(R.id.button_sing_up)
        button_sing_up.setOnClickListener {
            //получаем введенные данные
            val name = name_edit.text.toString()
            val email = email_edit.text.toString()
            val phone = phone_edit.text.toString()
            val password = password_edit.text.toString()
            val confirmPassword: String = password_repeat_edit.text.toString()

            name_input.isErrorEnabled = false
            email_input.isErrorEnabled = false
            password_input.isErrorEnabled = false
            password_repeat_input.isErrorEnabled = false
            phone_input.isErrorEnabled = false
            //создаем патерны для проверки
            val pattern = Pattern.compile("[a-z0-9.]+[@][a-z0-9]+[.][a-z]{1,3}")
            val patternPhone = Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}\$")
            //проверка вводимого
            val matcher = pattern.matcher(email)
            val match = matcher.matches()
            val matcherPhone= patternPhone.matcher(phone)
            val matchPhone = matcherPhone.matches()

            adapter = ArrayAdapter(requireContext(), R.layout.selected_item_spinner, branch)
            adapter.setDropDownViewResource(R.layout.drop_down_item_spinner)
            spinner.adapter = adapter
            val spinnerPosition: Int = adapter.getPosition(branchSelected)
            spinner.setSelection(spinnerPosition)
            spinner.setBackgroundResource(R.drawable.bg_spinner_branch)

            //валидность вводимых данных
            if (name == "") {
                name_input.error = resources.getString(R.string.error_msg_name)
            } else if (email == "") {
                email_input.error = resources.getString(R.string.sing_in_hint_email)
            } else if (phone  == "") {
                phone_input.error = resources.getString(R.string.sing_in_hint_phone)
            } else if (password == "") {
                password_input.error = resources.getString(R.string.sing_in_hint_password)
            } else if (confirmPassword == "") {
                password_repeat_input.error = resources.getString(R.string.error_msg_confirm_password)
            } else if (password.length < 6) {
                password_input.error = resources.getString(R.string.error_msg_password_length)
            } else if (password != confirmPassword) {
                password_input.error = resources.getString(R.string.error_msg_password_not)
                password_repeat_input.error = resources.getString(R.string.error_msg_password_not)
            } else if (!match) {
                email_input.error = resources.getString(R.string.error_msg_email_in)
            } else if (!matchPhone) {
                phone_input.error = resources.getString(R.string.error_msg_phone_in)
            } else if (branchSelected.equals("Выберите филиал")) {
                spinner.setBackgroundResource(R.drawable.bg_spinner_not_selected)
                adapter = ArrayAdapter(requireContext(), R.layout.selected_item_spinner_not, branch)
                adapter.setDropDownViewResource(R.layout.drop_down_item_spinner)
                spinner.adapter = adapter
            } else {
                //регистрация
                registration(email, password, name, branchSelected, phone)
            }
        }
        return viewOfLayout
    }

    //метод для регистрации
    fun registration(email:String, password:String,name:String, branchSelected:String, phone:String){
        val db = Firebase.firestore
        mAuth!!.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    userId = mAuth!!.currentUser!!.uid
                    val docData = hashMapOf(
                        "Name" to name,
                        "Branch" to branchSelected,
                        "Phone" to phone
                    )
                    db.collection("users").document(userId!!)
                        .set(docData)
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!")
                            //запись в настройки
                            editor!!.putString("LOGIN", "true")
                            editor!!.putString("userId", userId)
                            editor!!.commit()
                            loadFragment(MainProfileFragment())
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                } else {
                    // если регистрация провалилась, то сообщение об ошибке
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireActivity(),
                        resources.getString(R.string.error_user_create),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_account,fragment)
        transaction?.commit()
    }

}