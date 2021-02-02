package com.example.e_clinic

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class DoctorADP(
    ctx: Context,
    _docName: ArrayList<String>,
    _docQual: ArrayList<String>,
    _docMail: ArrayList<String>,
    _pid: String
): RecyclerView.Adapter<DoctorADP.DocViewHolder>() {
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
        var msgView1: TextView =itemView.findViewById(R.id.docReply)
        var docCon: Button =itemView.findViewById(R.id.ContactDoc)
        var P_msg:EditText=itemView.findViewById(R.id.p_msg)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorADP.DocViewHolder {

        var itemView= LayoutInflater.from(context).inflate(R.layout.doctoradapter, parent, false)

        return DoctorADP.DocViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return docName.size
    }
    //in it i put the message in doctor data which is sent by a patient with the id of patient
    override fun onBindViewHolder(holder: DocViewHolder, position: Int) {
        var database = Firebase.database
        holder.nameView.text = docName[position]
        holder.qualView.text = docQual[position]
        holder.mailView.text = docMail[position]
        var idRef1:String=p_id.toString()
        var idRef2="D_${docMail[position].toString()}".toString()
        var db = database.getReference("Patient Data/$idRef1/$idRef2/")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var temp = snapshot.getValue<String>()
                holder.msgView1.text = temp.toString()
                Log.d("listener","dd")
                db.removeEventListener(this)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        holder.docCon.setOnClickListener {
            msg=holder.P_msg.text.toString()
            var myRef = database.getReference("Doctor Data/")
            myRef.child(docMail[position]).child("P_"+p_id.toString()).setValue(msg.toString())
        }
    }
}
