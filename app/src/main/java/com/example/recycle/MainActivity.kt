package com.example.recycle

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.folder_item.view.*

class MainActivity : AppCompatActivity() {

    // Шаг 1. Обьявили список

    var animals: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addAnimals()

        // Шаг 2. Обьявили Ресайкл вью и Лейаут менеджер и присоединили адаптер

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = RecyclerAdapter(animals, this)


    }
    // Обьявляем класс Адаптера с 2 параметрами (Наш массив и контекст) который расширяет наш класс ViewHolder

    class RecyclerAdapter(val items: ArrayList<String>, val context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

        // Переписываем 3 метода

        // Получаем для каждого елемента View*шку через inflater

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.folder_item, p0, false))
        }
        // Получаем размер массива
        override fun getItemCount(): Int {
            return items.size
        }
        // Получаем Текст из TextView в ViewHolder
        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            p0.itemName?.text = items.get(p1)
        }

        // Создаем наш класс ViewHolder и получаем туда нашу View по ID
        class ViewHolder (view:View) : RecyclerView.ViewHolder(view) {
            val itemName = view.itemName
        }





    }

    private fun addAnimals() {
        animals.add("dog")
        animals.add("cat")
        animals.add("owl")
        animals.add("cheetah")
        animals.add("raccoon")
        animals.add("bird")
        animals.add("snake")
        animals.add("lizard")
        animals.add("hamster")
        animals.add("bear")
        animals.add("lion")
        animals.add("tiger")
        animals.add("horse")
        animals.add("frog")
        animals.add("fish")
        animals.add("shark")
        animals.add("turtle")
        animals.add("elephant")
        animals.add("cow")
        animals.add("beaver")
        animals.add("bison")
        animals.add("porcupine")
        animals.add("rat")
        animals.add("mouse")
        animals.add("goose")
        animals.add("deer")
        animals.add("fox")
        animals.add("moose")
        animals.add("buffalo")
        animals.add("monkey")
        animals.add("penguin")
        animals.add("parrot")
    }
}
