package com.example.crud_firebase

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter (private var listMahasiswa: ArrayList<data_mahasiswa>,
                           context: Context) :
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    private var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val NIM: TextView
        val Nama: TextView
        val Jurusan: TextView
        val ListItem: LinearLayout

        init {
            NIM = itemView.findViewById(R.id.nimx)
            Nama = itemView.findViewById(R.id.namax)
            Jurusan = itemView.findViewById(R.id.jurusanx)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V: View = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.view_design, parent, false)
        return ViewHolder(V)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val NIM: String? = listMahasiswa.get(position).nim
        val Nama: String? = listMahasiswa.get(position).nama
        val Jurusan: String? = listMahasiswa.get(position).jurusan

        holder.NIM.text = "NIM: $NIM"
        holder.Nama.text = "Nama: $Nama"
        holder.Jurusan.text = "Jurusan: $Jurusan"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean{

                holder.ListItem.setOnLongClickListener {view ->
                    val action = arrayOf("update", "delete")
                    val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                    alert.setItems(action, DialogInterface.OnClickListener { dialog, i ->
                        when(i){
                        0 ->{
                        val bundle = Bundle()
                            bundle.putString("dataNIM", listMahasiswa[position].nim)
                            bundle.putString("dataNama", listMahasiswa[position].nama)
                            bundle.putString("dataJurusan", listMahasiswa[position].jurusan)
                            bundle.putString("getPrimaryKey", listMahasiswa[position].key)
                            val intent = Intent(view.context, UpdateData::class.java)
                            intent.putExtras(bundle)
                            context.startActivity(intent)
                        }
                            1 -> {
                                listener?.onDeleteData(listMahasiswa.get(position), position)
                            }
                            }
                        })
                    alert.create()
                    alert.show()
                    true
                    }
                return true
            }
        })

    }

    interface dataListener{
        fun onDeleteData(data: data_mahasiswa?, position: Int)
    }
    var listener:dataListener? = null
    fun RecyclerViewAdapter(listMahasiswa: ArrayList<data_mahasiswa>?, context: Context?){
        this.listMahasiswa = listMahasiswa!!
        this.context = context!!
        listener = context as MyListData?
    }
    override fun getItemCount(): Int {
        return listMahasiswa.size
    }
    init {
        this.context = context
    }

}