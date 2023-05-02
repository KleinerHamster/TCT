package com.example.tct.customer.account

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.tct.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class CustomerUpdateDataFragment : Fragment() {
    private lateinit var viewOfLayout: View
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    val branch = arrayOf("Санкт-Петербург","Москва", "Екатеринбург","Краснодар",
        "Тюмень", "Пермь", "Новосибирск", "Уфа", "Самара", "Челябинск")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_custome_update_data, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("user data", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        val emailUser = sharedPreferences!!.getString("email", "true")
        val phoneUser = sharedPreferences!!.getString("phone", "true")
        val nameUser = sharedPreferences!!.getString("name", "true")
        val branchUser = sharedPreferences!!.getString("branch", "true")
        val passwordUser = sharedPreferences!!.getString("pas", "true")

        //иницилизируем поля и устанавливаем данные
        val nameChange = viewOfLayout.findViewById<EditText>(R.id.nameChange)
        val phoneChange = viewOfLayout.findViewById<EditText>(R.id.phoneChange)
        val emailChange = viewOfLayout.findViewById<EditText>(R.id.emailChange)
        nameChange.setText(nameUser.toString())
        phoneChange.setText(phoneUser.toString())
        emailChange.setText(emailUser.toString())
        var branchSelected: String = branchUser.toString()

        //инцилизируем выпадающий список
        val spinner = viewOfLayout.findViewById<Spinner>(R.id.spinner_selected_branch)
        //устанавливаем адаптер с данными и разметкой для выпадающего списка
        var adapter = ArrayAdapter(requireContext(), R.layout.selected_item_spinner, branch)
        adapter.setDropDownViewResource(R.layout.drop_down_item_spinner)
        spinner.adapter = adapter
        val spinnerPosition: Int = adapter.getPosition(branchSelected)
        spinner.setSelection(spinnerPosition)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                branchSelected= branch[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        //кнопка возвращения назад
        val btnCancel = viewOfLayout.findViewById<TextView>(R.id.btnCancel)
        btnCancel.setOnClickListener {
            loadFragment(MainProfileFragment())
        }

        val saveUpdate = viewOfLayout.findViewById<ImageView>(R.id.saveUpdate)
        saveUpdate.setOnClickListener {
            val name = nameChange.text.toString()
            val email = emailChange.text.toString()
            val phone = phoneChange.text.toString()
            //создаем патерны для проверки
            val patternPhone = Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}\$")
            //проверка вводимого
            val matcherPhone= patternPhone.matcher(phone)
            val matchPhone = matcherPhone.matches()
            //валидация полей
            if (name == "" || email == "" || phone == "") {
                Toast.makeText(requireActivity(), resources.getString(R.string.msg_error_change), Toast.LENGTH_SHORT).show()
            } else if(!matchPhone){
                Toast.makeText(requireActivity(), resources.getString(R.string.msg_error_change), Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireActivity(), resources.getString(R.string.msg_error_change), Toast.LENGTH_SHORT).show()
            } else {
                updateData( emailUser.toString(), passwordUser.toString(), email, name, branchSelected, phone, sharedPreferences!!.getString("userId", "true").toString())
            }
        }
        return viewOfLayout
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_account,fragment)
        transaction?.commit()
    }

    //метод для обновления данных поль-ля
    private fun updateData(email:String, password:String,newEmail: String, name:String, branchSelected:String, phone:String, userId:String){
        val db = Firebase.firestore
        val docData = hashMapOf(
            "Name" to name,
            "Branch" to branchSelected,
            "Phone" to phone
        )
        db.collection("users").document(userId)
            .set(docData)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                //запись в настройки
                editor!!.putString("phone", phone)
                editor!!.putString("branch", branchSelected)
                editor!!.putString("name", name)
                editor!!.commit()
                if(newEmail != email) {
                    val auth = Firebase.auth
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                auth.currentUser!!.updateEmail(newEmail)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d(TAG, "User email address updated.")
                                            editor!!.putString("email", email)
                                            editor!!.commit()
                                        } else {
                                            Log.d(TAG, "get failed with ", task.exception)
                                        }
                                    }
                            } else {
                                //ошибка при авторизацие
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                            }
                        }
                }
                Toast.makeText(requireActivity(), resources.getString(R.string.msg_successful_change), Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
}