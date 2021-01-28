package com.example.e_clinic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class DoctorADP(ctx: Context,_docName: List<String>, _docQual: List<String>, _docMail: List<String>,message:String,_pid: String): RecyclerView.Adapter<DoctorADP.DocViewHolder>() {
    var context=ctx
    var docName=_docName
    var docQual=_docQual
    var docMail=_docMail
    var msg=" "
    var p_id=_pid
    class DocViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var nameView: TextView =itemView.findViewById(R.id.nameView)
        var qualView: TextView =itemView.findViewById(R.id.qualView)
        var mailView: TextView =itemView.findViewById(R.id.mailView)
        var docCon: TextView =itemView.findViewById(R.id.ContactDoc)
        var P_msg:EditText=itemView.findViewById(R.id.p_msg)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorADP.DocViewHolder {

        var itemView= LayoutInflater.from(context).inflate(R.layout.doctoradapter, parent, false)

        return DocViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return docName.size
    }
    //in it i put the message in doctor data which is sent by a patient with the id of patient
    override fun onBindViewHolder(holder: DoctorADP.DocViewHolder, position: Int) {
        holder.nameView.text = docName[position]
        holder.qualView.text = docQual[position]
        holder.mailView.text = docMail[position]
        holder.docCon.setOnClickListener {
            msg=holder.P_msg.text.toString()
            val database = Firebase.database
            val myRef = database.getReference("Doctor Data")
            myRef.child(docMail[position]).child(""+p_id.toString()).setValue(msg.toString())
        }
    }
}