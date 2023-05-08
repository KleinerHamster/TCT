package com.example.tct.customer.account

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tct.R
import com.example.tct.model.CustomerOrder
import com.example.tct.model.OfficeInfo
import com.google.firebase.firestore.*

class HistoryOrderFragment : Fragment() {
    private lateinit var viewOfLayout: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderArrayList: ArrayList<CustomerOrder>
    private lateinit var myAdapter: CustomerOrderAdapter
    private lateinit var db: FirebaseFirestore
    var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_history_order, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("user data", Context.MODE_PRIVATE)
        //кнопка возвращения назад
        val backToProfile = viewOfLayout.findViewById<ImageView>(R.id.backToProfile)
        backToProfile.setOnClickListener {
            loadFragment(MainProfileFragment())
        }

        //иницилизируем RecyclerView
        recyclerView = viewOfLayout.findViewById(R.id.RVOder)
        recyclerView.layoutManager = LinearLayoutManager(viewOfLayout.context)
        recyclerView.setHasFixedSize(true)
        val noOrders = viewOfLayout.findViewById<TextView>(R.id.noOrders)
        orderArrayList = arrayListOf()//иницилизируем ArrayList новостей
        //вызываем метод для получения данных из Firestore Database
        EventChangeListener(sharedPreferences!!.getString("userId", "true").toString(),noOrders)
        myAdapter = CustomerOrderAdapter(orderArrayList)
        recyclerView.adapter = myAdapter

        return viewOfLayout
    }

    //метод для загрузки фрагмента
    private  fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fl_wrapper_account,fragment)
        transaction?.commit()
    }

    //метод для получения данных из Firestore Database
    private fun EventChangeListener(userID:String, noOrder:TextView){
        db= FirebaseFirestore.getInstance()//подлкючение к Firestore
        val addSnapshotListener = db.collection("users").document(userID).collection("orders")
            .orderBy("DateOrder", Query.Direction.DESCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("FireStore error", error.message.toString())
                        return
                    }
                    var f =0
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            f=1
                            orderArrayList.add(dc.document.toObject(CustomerOrder::class.java))
                        }
                    }
                   if(f==0)
                       noOrder.visibility=View.VISIBLE
                   else
                       noOrder.visibility=View.INVISIBLE
                  myAdapter.notifyDataSetChanged()
                }
            })
    }

}