package com.example.recycle

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.folder_item.view.*
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.helper.ItemTouchHelper



class MainActivity : AppCompatActivity() {

    // Шаг 1. Обьявили список, создали Layout файл для одного елемента списка RecycleView и присвоили ему ID.
    // Добавили RecyclerView Widget в Мейн Активити XML и дали ему тоже ID

    private var animals =  mutableListOf("dog", "Cat", "Fish", "Owl")
    // объявляем переменную для цвета нашего прямоугольника
    private val p = ColorDrawable(Color.parseColor("#FF0000"))

    //Объявляем переменную для иконки
    private lateinit var deleteIcon: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Добавляем данные в массив


        // Шаг 2. Обьявили Ресайкл вью и Лейаут менеджер и присоединили адаптер

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = RecyclerAdapter(animals, this)

        //Красивенькое подчеркивание снизу у елементов списка

        recycleView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // Прикрепляем наш Тачхелпер

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recycleView)

        //Получаем нашу иконку

        deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete)!!


    }
    // Обьявляем класс Адаптера с 2 параметрами (Наш массив и контекст) который расширяет наш класс ViewHolder.
    // На ошибки закрываем глаза, как только напишем свой класс ViewHolder, ошибка исчезнет.

    class RecyclerAdapter(private val items: MutableList<String>, val context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

        //Переменные для хранения удаленных елементов(Что бы их можно было вернуть).

        private var removedPosition: Int = 0
        private var removedItem: String = ""


        // Переписываем 3 метода

        // Получаем для каждого елемента View*шку через inflater, указываем ID xml, где наш один елемент списка RecyclerView

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.folder_item, p0, false))
        }
        // Получаем размер массива
        override fun getItemCount(): Int {
            return items.size
        }
        // Получаем Текст из TextView в ViewHolder
        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            p0.itemName.text = items[p1]
        }
        // Функция для удаления елемента по свайпу.(и возврата)
        fun removeItem(viewHolder: RecyclerView.ViewHolder) {

            removedPosition = viewHolder.adapterPosition
            removedItem = items[viewHolder.adapterPosition]

            items.removeAt(viewHolder.adapterPosition)
            notifyItemRemoved(viewHolder.adapterPosition)

            //Собственно снекбар с возвратом

            Snackbar.make(viewHolder.itemView, "$removedItem deleted", Snackbar.LENGTH_LONG).setAction("UNDO") {
                items.add(removedPosition, removedItem)
                notifyItemInserted(removedPosition)
            }.show()
        }

        // Создаем наш класс ViewHolder и получаем туда нашу View по ID
        class ViewHolder (view:View) : RecyclerView.ViewHolder(view) {
            val itemName = view.itemName!!
        }

    }

    //Создаем класс и объявляем экземпляр


    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){

        //Переписываем функции класса SimpleCallback(onMove - для драга, onSwipe  - для свайпа)
        override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
            return false
        }
        // В методе onSwipe реализуем функцию удаления из списка(прописали ее в адаптере) (ключевое слово "as" используется для каста(узнать подробнее)).
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, postition: Int) {
            (recycleView.adapter as RecyclerAdapter).removeItem(viewHolder)
        }
        //Метод для отрисвки нашего красного прямоугольника при свайпе с иконкой
        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            //Получаем елемент списка
            val itemView = viewHolder.itemView
            //Узнаем разница между высотой елемента и иконки для последующего выравнивания
            val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
            //Задаем границы прямоугольника и икноки(тут все пздц сложно)
            p.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            deleteIcon.setBounds(itemView.right - iconMargin - deleteIcon.intrinsicWidth, itemView.top + iconMargin, itemView.right - iconMargin, itemView.bottom - iconMargin )
            //Отрисовываем прямоугольник
            p.draw(c)
            //Сохраняем
            c.save()
            //Прилепляем иконку к прямоугольнику
            c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            //рисуем иконку
            deleteIcon.draw(c)
            //востанавливаем после иконки.
            c.restore()


            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

    }








}
