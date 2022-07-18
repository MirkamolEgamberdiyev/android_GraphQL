package com.mirkamol.android_graphqi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.mirkamol.android_graphqi.InsertUserMutation
import com.mirkamol.android_graphqi.databinding.ActivityCreateBinding
import com.mirkamol.android_graphqi.network.GrapgQL
import kotlinx.coroutines.launch

class CreateActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateBinding
    private var name: String = ""
    private var rocket: String = ""
    private var twitter: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }
    private fun initViews() {
        binding.apply {
            tvName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    name = tvName.text.toString().trim()
                }

            })

            tvRocket.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    rocket = tvRocket.text.toString().trim()
                }

            })
            tvTwitter.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    twitter = tvTwitter.text.toString().trim()
                }

            })
        }
        binding.tvSave.setOnClickListener {
            insertUser(name, rocket, twitter)
            finish()
        }
    }

    private fun insertUser(name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GrapgQL.get().mutation(InsertUserMutation(name, rocket, twitter)).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("MainActivity", result.toString())
        }
    }
}