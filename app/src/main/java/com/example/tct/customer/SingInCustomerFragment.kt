package com.example.tct.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tct.R
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern

class SingInCustomerFragment: Fragment() {

    private lateinit var viewOfLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_sing_in_up, container, false)

        val singIn= viewOfLayout.findViewById<Button>(R.id.button_sing_in) as Button
        singIn.setOnClickListener{
            val email = viewOfLayout.findViewById<TextInputEditText>(R.id.email_edit) as TextInputEditText
            val password = viewOfLayout.findViewById<TextInputEditText>(R.id.password_edit) as TextInputEditText

            if(email.text.toString()==""){
                Toast.makeText(requireActivity(), "Введите почту!", Toast.LENGTH_LONG).show()
            }
            else if(password.text.toString()==""){
                Toast.makeText(requireActivity(), "Введите пароль!", Toast.LENGTH_LONG).show()

            }
            else if(!isEmailValid(email.text.toString())){
                Toast.makeText(requireActivity(), "Неверная почта!", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(requireActivity(), " УРА!", Toast.LENGTH_LONG).show()
            }
        }
        return viewOfLayout
    }

    //метод проферки почты
    fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }
}