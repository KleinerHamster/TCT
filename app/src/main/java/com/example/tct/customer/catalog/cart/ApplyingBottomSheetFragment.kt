package com.example.tct.customer.catalog.cart

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tct.R
import com.example.tct.customer.catalog.CatalogCustomerFragment
import com.example.tct.model.CartModel
import com.example.tct.model.CommentModel
import com.example.tct.mymail.MyMailAPI
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ApplyingBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var viewOfLayout: View
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    private var modelCom: CommentModel? = null
    private var model: CartModel? = null
    private var hashMapOffice: HashMap<String, String> = HashMap<String, String> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_applying_bottom_sheet, container, false)
        //иницилизируем данные
        sharedPreferences = requireActivity().getSharedPreferences("user data", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        loadMailOffice()
        //устанавливаем данные поль-ля перед отправкой
        val nameUser = viewOfLayout.findViewById<TextView>(R.id.nameUser)
        nameUser.text = sharedPreferences!!.getString("name", "true")
        val phoneUser = viewOfLayout.findViewById<TextView>(R.id.phoneUser)
        phoneUser.text = sharedPreferences!!.getString("phone", "true")
        val emailUser = viewOfLayout.findViewById<TextView>(R.id.emailUser)
        emailUser.text = sharedPreferences!!.getString("email", "true")
        val branch = sharedPreferences!!.getString("branch", "true")

        //получаем данные из ViewModel
        modelCom = ViewModelProvider(requireActivity())[CommentModel::class.java]
        val commentFromModel = modelCom!!.get().value!!
        model = ViewModelProvider(requireActivity())[CartModel::class.java]
        val itemCart: ArrayList<String> = model!!.get().value!! as ArrayList
        //кнопка для оформления заказа
        val btnSendEmail = viewOfLayout.findViewById<Button>(R.id.btnSendEmail)
        btnSendEmail.setOnClickListener {
            sendApplying(sharedPreferences!!.getString("userId", "true").toString(), commentFromModel,
                itemCart, branch.toString(), nameUser.text.toString(), emailUser.text.toString(),
                phoneUser.text.toString())
        }

        return viewOfLayout
    }

    //метод для записи созданного заказа в Firestore
    private fun sendApplying(userID:String, comment:String, itemCart:ArrayList<String>,
                             branch:String, nameUser:String, emailUser:String, phoneUser:String){
        val db = Firebase.firestore
        val docData = hashMapOf(
            "DateOrder" to Timestamp(Calendar.getInstance().time),
            "Comment" to comment,
            "Goods" to itemCart
        )
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        val dateEmail = simpleDateFormat.format(Calendar.getInstance().time).toString()
        val subject = "Новая заявка от $dateEmail"
        val headerMessage = "Новая заявка из мобильного приложения от  $dateEmail"
        val goods =itemCart.joinToString(
            prefix = "",
            separator = ", ",
            postfix = "",
            truncated = "...",
            transform = { it.uppercase() }
        )
        db.collection("users").document(userID).collection("orders")
            .add(docData).addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
               val javaMailAPI = MyMailAPI(requireActivity(), hashMapOffice[branch].toString(),
                   subject, headerMessage, nameUser, emailUser, phoneUser, comment, goods)

                javaMailAPI.execute()
                showDialog()
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e)  }
    }

    //метод вызова диалогового окна для возвращения в каталок
    fun showDialog(){
        //инцилизурем созданное окно для возвращения в каталок
        val dialogConstraintLayout = requireActivity().findViewById<ConstraintLayout>(R.id.dialog_Constraint_Layout_1)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.create_order_dialog, dialogConstraintLayout)
        val btnGood = view.findViewById<TextView>(R.id.btnGood)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)
        val alertDialog = builder.create()
        //кнопка закрыть окно
        btnGood.setOnClickListener {
            val itemCart: ArrayList<String> = model!!.get().value!! as ArrayList
            itemCart.clear()
            modelCom!!.setData("")
            model!!.setData(itemCart)
            editor!!.remove("comment")
            editor!!.remove("order")
            editor!!.commit()

            alertDialog.dismiss()
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            loadFragment(CatalogCustomerFragment())
        }

        if(alertDialog.window!=null){
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        alertDialog.show()
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_basket,fragment)
        transaction?.commit()
    }

    //метод для загрузки соответсвующего филиала и его почты
    private fun loadMailOffice(){
        hashMapOffice["Санкт-Петербург"] = "spb@tst-ur.ru"
        hashMapOffice["Москва"] = "vika.ivashova.04@mail.ru"
        hashMapOffice["Екатеринбург"] = "ural@tst-ur.ru"
        hashMapOffice["Краснодар"] = "artel_ug@mail.ru"
        hashMapOffice["Тюмень"] = "tyumen@tst-ur.ru"
        hashMapOffice["Пермь"] = "perm@tst-ur.ru"
        hashMapOffice["Новосибирск"] = "novosibirsk@tst-ur.ru"
        hashMapOffice["Уфа"] = "ufa@tst-ur.ru"
        hashMapOffice["Самара"] = "samara@tst-ur.ru"
        hashMapOffice["Челябинск"] = "74@tst-ur.ru"
    }
}
