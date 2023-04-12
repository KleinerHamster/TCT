package com.example.tct.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.tct.R
import com.google.android.material.tabs.TabLayout


class NewsCustomerFragment: Fragment() {
    private lateinit var viewOfLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_customer_news, container, false)

        //созданем и заполняем адаптер ViewPager
        val adapter= ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(LatestNewsFragment(), "Новости")
        adapter.addFragment(InformationCompanyFragment(), "О компании")

        //связываем ViewPager и TabLayout с данными
        val viewPager = viewOfLayout.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        val tabLayout =  viewOfLayout.findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
        return viewOfLayout
    }

}