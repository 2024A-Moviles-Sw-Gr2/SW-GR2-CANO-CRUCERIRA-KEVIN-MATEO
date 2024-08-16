package com.example.deber_02

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

class EquipoEntity (
    var id: Int,
    var nombre: String,
    var fechaCreacion: String,
    var ciudad: String,
    var ubicacionEstadio: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(fechaCreacion)
        parcel.writeString(ciudad)
        parcel.writeString(ubicacionEstadio)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return nombre
    }

    companion object CREATOR : Parcelable.Creator<EquipoEntity> {
        override fun createFromParcel(parcel: Parcel): EquipoEntity {
            return EquipoEntity(parcel)
        }

        override fun newArray(size: Int): Array<EquipoEntity?> {
            return arrayOfNulls(size)
        }
    }

}