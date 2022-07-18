package com.mirkamol.android_graphqi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mirkamol.android_graphqi.UsersListQuery
import com.mirkamol.android_graphqi.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.VH>() {
    private val dif = AsyncListDiffer(this, UserAdapter.ITEM_DIFF)
    lateinit var itemClick: ((UsersListQuery.User) -> Unit)

    inner class VH(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val books = dif.currentList[adapterPosition]
            binding.apply {
                tvName.text = books.name
                tvRocket.text = books.rocket
                tvTwitter.text = books.twitter
                tvTime.text = books.timestamp.toString()

                userItem.setOnClickListener {
                    itemClick.invoke(books)
                }

            }


        }

    }

    fun submitList(list: List<UsersListQuery.User>) {
        dif.submitList(list)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): UserAdapter.VH {
        return VH(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserAdapter.VH, position: Int) =
        holder.bind()

    override fun getItemCount(): Int = dif.currentList.size


    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<UsersListQuery.User>() {
            override fun areItemsTheSame(
                oldItem: UsersListQuery.User,
                newItem: UsersListQuery.User
            ): Boolean =
                oldItem.id == newItem.id && oldItem.name == newItem.name
                        && oldItem.rocket == newItem.rocket
                        && oldItem.timestamp == newItem.timestamp &&
                        oldItem.twitter == newItem.twitter


            override fun areContentsTheSame(
                oldItem: UsersListQuery.User,
                newItem: UsersListQuery.User
            ): Boolean =
                oldItem == newItem
        }
    }
}
