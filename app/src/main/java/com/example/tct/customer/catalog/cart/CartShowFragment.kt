package com.example.tct.customer.catalog.cart

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.customer.account.AdapterOfficeInfo
import com.example.tct.customer.catalog.CatalogCustomerFragment
import com.example.tct.customer.catalog.MainCatalogFragment
import com.example.tct.model.CartModel
import com.example.tct.model.CommentModel
import com.google.gson.Gson
import org.w3c.dom.Text
import java.util.ArrayList


class CartShowFragment : Fragment() {
    private lateinit var viewOfLayout: View
    private var model: CartModel? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: AdapterCart
    private var modelCom: CommentModel? = null
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_cart_show, container, false)
        val commentForApplyEditText = viewOfLayout.findViewById<EditText>(R.id.commentForApplyEditText)
        val emptyCart = viewOfLayout.findViewById<TextView>(R.id.emptyCart)
        val label1 =  viewOfLayout.findViewById<TextView>(R.id.label1)
        sharedPreferences = requireActivity().getSharedPreferences("user data", Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        val gson = Gson()

        //иницилизируем RecyclerView
        recyclerView = viewOfLayout.findViewById(R.id.cart_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(viewOfLayout.context)
        recyclerView.setHasFixedSize(true)

        //получаем данные из ViewModel
        modelCom = ViewModelProvider(requireActivity())[CommentModel::class.java]
        //через паттерн observe подписываемся на изменения
        modelCom!!.commentForApplying.observe(requireActivity(), Observer{
            val commentFromModel = modelCom!!.get().value!!
            commentForApplyEditText.setText(commentFromModel)
        })

        //получаем данные из ViewModel
        model = ViewModelProvider(requireActivity())[CartModel::class.java]
        //через паттерн observe подписываемся на изменения
        model!!.cartDataList.observe(requireActivity(), Observer {
            val itemCart: ArrayList<String> = model!!.get().value!! as ArrayList
            if (itemCart.size>0) {
                recyclerView.visibility=View.VISIBLE
                label1.visibility=View.VISIBLE
                emptyCart.visibility=View.INVISIBLE
                myAdapter= AdapterCart(itemCart as ArrayList<String>)
                recyclerView.adapter = myAdapter
                myAdapter.setOnClickListener(object: AdapterCart.onItemClickListener{
                    @SuppressLint("NotifyDataSetChanged")
                    override fun deleteItem(position: Int) {
                        itemCart.removeAt(position)
                        myAdapter.notifyDataSetChanged()
                        model!!.setData(itemCart)
                        val json: String = gson.toJson(itemCart)
                        editor!!.putString("order", json)
                        editor!!.commit()
                    }
                })
            } else{
                recyclerView.visibility=View.INVISIBLE
                label1.visibility=View.INVISIBLE
                emptyCart.visibility=View.VISIBLE
                commentForApplyEditText.setText("")
                saveComment(commentForApplyEditText, gson)
            }
        })

        //кнопка возвращения к каталогу
        val backToMainCategory = viewOfLayout.findViewById<ImageView>(R.id.backToMainCategory)
        backToMainCategory.setOnClickListener {
            saveComment(commentForApplyEditText, gson)
            loadFragment(CatalogCustomerFragment())
        }
        return viewOfLayout
    }

    //метод для сохранения комментария
    private fun saveComment(commentForApplyEditText:EditText, gson:Gson){
        modelCom!!.setData(commentForApplyEditText.text.toString())
        val json: String = gson.toJson(commentForApplyEditText.text.toString())
        editor!!.putString("comment", json)
        editor!!.commit()
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_basket,fragment)
        transaction?.commit()
    }
}
