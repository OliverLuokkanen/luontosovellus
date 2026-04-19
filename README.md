# Luontosovellus

Android-sovellus luontoretkeilyyn, askeleiden laskentaan ja kasvien tunnistukseen tekoälyllä.

## Ominaisuudet
- **Kartta & Reitit**: Seuraa kuljettua reittiä GPS:n ja osmdroid-kartan avulla.
- **Kamera & Tekoäly**: Ota kuvia luontokohteista ja tunnista lajit on-device ML Kitillä.
- **Tilastot**: Askelmittari (SensorManager) ja kävelysessioiden seuranta.
- **Pilvisynkkaus**: Firebase offline-first -arkkitehtuurilla, tallentaa ensin lokaalisti Roomiin.

## Arkkitehtuuri
- 100% Kotlin & Jetpack Compose
- MVVM + Dagger Hilt
- Room Database
