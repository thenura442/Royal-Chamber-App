package com.example.royal_chamber_app

import android.os.Parcel
import android.os.Parcelable

data class Cloth(val image:Int,val no:Int):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeInt(no)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cloth> {
        override fun createFromParcel(parcel: Parcel): Cloth {
            return Cloth(parcel)
        }

        override fun newArray(size: Int): Array<Cloth?> {
            return arrayOfNulls(size)
        }
    }
}
