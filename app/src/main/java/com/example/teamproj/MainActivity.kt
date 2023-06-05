package com.example.teamproj

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.net.ParseException
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproj.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var array: ArrayList<checkData> = ArrayList<checkData>()
    lateinit var adapter:listAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        binding.recycler.layoutManager= LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        adapter=listAdapter(array)

        adapter.onCheck=object:listAdapter.CheckBoxChangeListener{ //체크박스 선택
            override fun onCheckBoxChanged(
                data: checkData, pos: Int, holder: listAdapter.viewHolder
            ) {
                data.check = !data.check
                adapter.notifyItemChanged(pos)
            }
        }
        binding.recycler.adapter=adapter

        adapter.onChange=object:listAdapter.AdapterListener{ //체크리스트 수정하기
            override fun onValueReturned(data: checkData,pos:Int,value: String) {
                binding.textcheck.setText(value)
                adapter.removeItem(pos)
            }
        }

        binding.addButton.setOnClickListener {//체크리스트 추가하기
            if(binding.textcheck.length()>=1){
                array.add(checkData(binding.textcheck.text.toString(),false))
                adapter.notifyDataSetChanged()
                binding.textcheck.setText("")
            }
            else Toast.makeText(this, "한글자 이상 입력해주세요", Toast.LENGTH_SHORT).show()
        }
        binding.alldelete.setOnClickListener {//체크리스트 모두 지우기
            //val count:Int=adapter.itemCount
            adapter.deleteItem()
        }


        binding.addlinkbtn.setOnClickListener { //링크 추가하기

            binding.linktext.append("\n"+binding.linkaddtext.text.toString())
            binding.linkaddtext.setText("")
            val pattern = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$"
            val linkcheck = binding.linktext.text.toString().matches(pattern.toRegex())

            if (linkcheck){
                binding.linktext.paintFlags=Paint.UNDERLINE_TEXT_FLAG
                binding.linktext.setTextColor(Color.BLUE)
                binding.linktext.setTypeface(null, Typeface.BOLD)
            }

            Toast.makeText(this, "텍스트가 링크로 저장되었습니다.", Toast.LENGTH_SHORT).show()

        }

        binding.linktext.setOnClickListener {//링크를 눌렀을때
            val link:String=binding.linktext.getText().toString()
            if (!link.isEmpty()) {
                try {
                    val uri: Uri = Uri.parse(link)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                catch (e:ParseException){
                    Toast.makeText(this, "유효하지 않은 링크", Toast.LENGTH_SHORT).show()
                }catch (e:java.lang.Exception){
                    Toast.makeText(this, "유효하지 않은 링크", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.linktextdelete.setOnClickListener {//link 삭제
            binding.linktext.setText("")
        }
        binding.changelinktext.setOnClickListener {//link 수정
            binding.linkaddtext.setText(binding.linktext.text.toString())
            binding.linktext.setText("")
        }
    }

}