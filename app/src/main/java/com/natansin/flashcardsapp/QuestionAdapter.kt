package com.natansin.flashcardsapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionAdapter(private var questions: MutableList<Pair<String, String>>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewQuestion: TextView = itemView.findViewById(R.id.textViewQuestion)
        val btnViewAnswer: Button = itemView.findViewById(R.id.btnViewAnswer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (question, answer) = questions[position]
        holder.textViewQuestion.text = question

        holder.btnViewAnswer.text = "VER RESPOSTA"
        holder.btnViewAnswer.setOnClickListener {
            holder.btnViewAnswer.text = answer
        }

    }

    override fun getItemCount(): Int {
        return questions.size
    }

    fun setData(newData: List<Pair<String, String>>) {
        questions.clear() // Limpa a lista atual
        questions.addAll(newData) // Adiciona todos os elementos da nova lista
        notifyDataSetChanged()
    }

    fun removeItemAtPosition(position: Int) {
        questions.removeAt(position)
        notifyItemRemoved(position)
    }
    fun getItemIdAtPosition(position: Int): Long {
        return position.toLong()
    }



    fun getItemAtPosition(position: Int): Pair<String, String>? {
        return if (position != RecyclerView.NO_POSITION && position < questions.size) {
            questions[position]
        } else {
            null
        }
    }


}
