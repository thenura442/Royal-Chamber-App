package com.example.royal_chamber_app.Listener

import com.example.royal_chamber_app.Model.MenModel

interface IClothLoadListenerMen {
    fun onLoadSuccess(clothModelList:List<MenModel>?)
    fun onLoadFailed(message:String?)
}