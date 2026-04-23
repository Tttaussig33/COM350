package com.example.pa2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pa2.databinding.BookItemBinding

class BookAdapter(
    private var books: List<Book>,
    private val onItemClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]

        holder.binding.tvTitle.text = book.title
        holder.binding.tvAuthor.text = book.author

        val context = holder.itemView.context
        val resId = context.resources.getIdentifier(book.imageId, "drawable", context.packageName)

        if (resId != 0) {
            holder.binding.ivBook.setImageResource(resId)
        } else {
            holder.binding.ivBook.setImageResource(R.drawable.generic_book)
        }

        holder.binding.root.setOnClickListener {
            onItemClick(book)
        }
    }

    override fun getItemCount(): Int = books.size

    fun updateData(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}