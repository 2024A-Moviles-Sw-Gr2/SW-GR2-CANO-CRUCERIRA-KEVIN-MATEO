package com.example.deber_02

class UbicacionEstadio(
    val nombre: String,
    val latitud: Double,
    val longitud: Double
) {
    companion object {
        private val listUbicacionEstadio = arrayListOf<UbicacionEstadio>()

        init {
            listUbicacionEstadio.add(UbicacionEstadio("Spotify Camp Nou", 41.38077707719879, 2.1229734693948714))
            listUbicacionEstadio.add(UbicacionEstadio("Santiago Bernabéu", 40.44868401225792, -3.692270905948001))
            listUbicacionEstadio.add(UbicacionEstadio("Parque De Los Príncipes", 48.84128188624758, 2.2531790137677516))
            listUbicacionEstadio.add(UbicacionEstadio("Etihad Stadium", 53.48308483612204, -2.2005241036670737))
            listUbicacionEstadio.add(UbicacionEstadio("Allianz Arena", 48.21835323502699, 11.624885773093506))
            listUbicacionEstadio.add(UbicacionEstadio("Estadio Juventus", 45.11000091698978, 7.649845312071816))
        }

        fun getUbicaciones(): List<UbicacionEstadio> {
            return listUbicacionEstadio
        }
    }
}
