package com.example.teamproj

import android.content.ClipData
import android.content.ClipData.Item
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproj.databinding.RowBinding

class listAdapter (val items:ArrayList<checkData>)
    : RecyclerView.Adapter<listAdapter.viewHolder>(){

    interface AdapterListener { //수정 함수
        fun onValueReturned(data: checkData,pos: Int,value: String)
    }
    var onChange:AdapterListener?=null

    interface CheckBoxChangeListener {
        fun onCheckBoxChanged(data: checkData,pos: Int,holder: viewHolder)
    }
    var onCheck:CheckBoxChangeListener?=null

    inner class viewHolder(val binding: RowBinding)
        : RecyclerView.ViewHolder(binding.root){
            init {
                binding.checkimage.setOnClickListener {
                    onCheck?.onCheckBoxChanged(items[adapterPosition],adapterPosition,this)
                }

                binding.deleteCheck.setOnClickListener {//item삭제
                    removeItem(adapterPosition)
                }
                binding.changeCheck.setOnClickListener {//item수정
                    onChange?.onValueReturned(items[adapterPosition],adapterPosition,binding.checktext.text.toString())
                }
            }
        }


    fun removeItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }
    fun deleteItem(){
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listAdapter.viewHolder {
        var view = RowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: listAdapter.viewHolder, position: Int) {
        holder.binding.checktext.text = items[position].text
        if (items[position].check){
            holder.binding.checkimage.setBackgroundResource(R.drawable.baseline_check_box_24)
            holder.binding.checktext.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            holder.binding.checkimage.setBackgroundResource(R.drawable.baseline_check_box_outline_blank_24)
            holder.binding.checktext.setPaintFlags(0)
        }
    }
}