package com.sora.iadapter.simple

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.sora.iadapter.simple.databinding.ActivityMainBinding
import com.sora.iadapter.simple.databinding.ItemMainBinding
import com.sora.library.IAdapter
import com.sora.library.ItemNavigator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),ItemNavigator<String> {
    val data = ObservableArrayList<String>().apply {
        add("history")
    }

    override fun itemDetail(item: String?) {
        Toast.makeText(this, item, Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.activity = this
        setSupportActionBar(toolbar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = object : IAdapter<String, ItemMainBinding>() {
            override fun getLayoutId(viewType: Int): Int {
                return R.layout.item_main
            }

            override fun getDataBRId(itemViewType: Int): Int {
                return BR.item
            }

            override fun convert(binding: ItemMainBinding, position: Int, t: String?) {
                super.convert(binding, position, t)
                binding.itemNavigator = this@MainActivity
            }
        }
        fab.setOnClickListener {
            data.add(0, "Add new")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}
