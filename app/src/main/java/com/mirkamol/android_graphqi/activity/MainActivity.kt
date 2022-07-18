package com.mirkamol.android_graphqi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.mirkamol.android_graphqi.UsersListQuery
import com.mirkamol.android_graphqi.adapter.UserAdapter
import com.mirkamol.android_graphqi.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import com.mirkamol.android_graphqi.network.GrapgQL

class MainActivity : AppCompatActivity() {
    lateinit var adapter: UserAdapter
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()


    }

    private fun initViews() {
        getUserList()
        // insertUser("Egamberdiyev", "TATU", "Amazon")\
        adapter = UserAdapter()

        binding.addFab.setOnClickListener {
            openSecondActivity()
        }
        openUpdateActivity()

    }

    private fun openUpdateActivity() {
        adapter.itemClick = { user ->
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("id", user.id.toString())
            intent.putExtra("name", user.name)
            intent.putExtra("rocket", user.rocket)
            intent.putExtra("twitter", user.twitter)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getUserList()
    }

    private fun openSecondActivity() {
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)

    }

    private fun getUserList() {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GrapgQL.get().query(UsersListQuery(10)).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            val users = response.data?.users
            response.data?.let { adapter.submitList(it.users) }
            binding.recyclerView.adapter = adapter

            Log.d("MainActivity", users!!.toString())
        }

    }


}