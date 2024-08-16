package com.example.deber_02

import android.os.Parcel
import android.os.Parcelable

class JugadorEntity (
    val id: Int,
    val nombre: String,
    val edad: Int,
    val altura: Double,
    val equipoId: Int
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return nombre
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeInt(edad)
        parcel.writeDouble(altura)
        parcel.writeInt(equipoId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<JugadorEntity> {
        override fun createFromParcel(parcel: Parcel): JugadorEntity {
            return JugadorEntity(parcel)
        }

        override fun newArray(size: Int): Array<JugadorEntity?> {
            return arrayOfNulls(size)
        }
    }

}