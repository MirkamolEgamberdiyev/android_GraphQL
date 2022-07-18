package com.mirkamol.android_graphqi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.mirkamol.android_graphqi.DeleteUserMutation
import com.mirkamol.android_graphqi.UpdateUserMutation
import com.mirkamol.android_graphqi.databinding.ActivityUpdateBinding
import com.mirkamol.android_graphqi.network.GrapgQL
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {
    lateinit var binding:ActivityUpdateBinding
    private var id: String = ""
    private var name: String = ""
    private var rocket: String = ""
    private var twitter: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        id = intent.getStringExtra("id") ?: "Empty"
        name = intent.getStringExtra("name") ?: "Empty"
        rocket = intent.getStringExtra("rocket") ?: "Empty"
        twitter = intent.getStringExtra("twitter") ?: "Empty"
        binding.apply {
            tvName.setText(name)
            tvRocket.setText(rocket)
            tvTwitter.setText(twitter)

            tvName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    name = tvName.text.toString()
                }
            })
            tvRocket.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    rocket = tvRocket.text.toString()
                }
            })
            tvTwitter.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    twitter = tvTwitter.text.toString()
                }
            })

            btnUpdate.setOnClickListener {
                if (name != "" && rocket != "" && twitter != "") {
                    updateUser(id, name, rocket, twitter)
                    finish()
                } else {
                    Toast.makeText(
                        this@UpdateActivity,
                        "Please fill the fields first",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            btnDelete.setOnClickListener {
                deleteUser(id)
                finish()
            }
        }
    }

    private fun updateUser(id: String, name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GrapgQL.get().mutation(UpdateUserMutation(id, name, rocket, twitter)).execute()
            } catch (e: ApolloException) {
                Log.d("UpdateActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("UpdateActivity", result.toString())
        }
    }

    private fun deleteUser(id: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GrapgQL.get().mutation(DeleteUserMutation(id)).execute()
            } catch (e: ApolloException) {
                Log.d("UpdateActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("UpdateActivity", result.toString())
        }
    }
}