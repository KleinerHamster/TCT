package com.example.tct.customer.account

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tct.R
import com.example.tct.model.CartModel
import com.example.tct.model.CommentModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class MainProfileFragment : Fragment() {

    private lateinit var viewOfLayout: View
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    private var modelCom: CommentModel? = null
    private var model: CartModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_main_profile, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("user data", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        val emailUser = sharedPreferences!!.getString("email", "true")
        val phoneUser = sharedPreferences!!.getString("phone", "true")
        val nameUser = sharedPreferences!!.getString("name", "true")
        val branchUser = sharedPreferences!!.getString("branch", "true")

        //кнопка выхода из аккаунта
        val singOut = viewOfLayout.findViewById<TextView>(R.id.singOut)
        singOut.setOnClickListener {
            modelCom = ViewModelProvider(requireActivity())[CommentModel::class.java]
            model = ViewModelProvider(requireActivity())[CartModel::class.java]
            val itemCart: ArrayList<String> = model!!.get().value!! as ArrayList
            itemCart.clear()
            modelCom!!.setData("")
            model!!.setData(itemCart)
            editor!!.clear()
            editor!!.commit()
            loadFragment(SingInCustomerFragment())
        }

        //кнопка выхода из аккаунта
        val goToUpdate = viewOfLayout.findViewById<TextView>(R.id.goToUpdate)
        goToUpdate.setOnClickListener {
            loadFragment(CustomerUpdateDataFragment())
        }
        //кнопка просмотра контактной информации
        val showContacts = viewOfLayout.findViewById<TextView>(R.id.showContacts)
        showContacts.setOnClickListener {
            loadFragment(OfficeInformationFragment())
        }
        val nameShow = viewOfLayout.findViewById<TextView>(R.id.nameShow)
        val phoneShow = viewOfLayout.findViewById<TextView>(R.id.phoneShow)
        val emailShow= viewOfLayout.findViewById<TextView>(R.id.emailShow)
        val branchShow= viewOfLayout.findViewById<TextView>(R.id.branchShow)
        //устанавливаем данные для просмотра
        emailShow.text=emailUser.toString()
        phoneShow.text=phoneUser.toString()
        nameShow.text=nameUser.toString()
        branchShow.text=branchUser.toString()

        //кнопка просмотра истории заказов
        val watchHistoryOrder = viewOfLayout.findViewById<TextView>(R.id.watchHistoryOrder)
        watchHistoryOrder.setOnClickListener {
            loadFragment(HistoryOrderFragment())
        }
        return viewOfLayout
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_account,fragment)
        transaction?.commit()
    }
}