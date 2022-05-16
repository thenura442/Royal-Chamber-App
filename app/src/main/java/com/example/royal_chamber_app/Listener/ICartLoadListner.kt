package com.example.royal_chamber_app.Listener

import com.example.royal_chamber_app.Model.CartModel
import com.example.royal_chamber_app.Model.MenModel

interface ICartLoadListner {
    fun onCartLoadSuccess(cartModelList : List<CartModel>?)
    fun onCartLoadFailed(message: String?)
}