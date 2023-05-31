# Házi feladat specifikáció

## Mobil- és webes szoftverek
### 2022. 10. 23.
### Secret Meeting
### Dancs Balázs - (AXDRVK)
### dancs.balazs01@gmail.com 
### Laborvezető: Reiter Márton

## Bemutatás

Az alkalmazás célja, hogy egy közösség számára titkos találkozók megszervezésében nyújtson segítséget. Az alkalmazás biztonsága a tagok megbízhatóságában rejlik, amint valaki kiszivárogtatja az App-ot a körön kívülre, értelmét veszti az.

## Főbb funkciók

- A főoldalon egy listában lehet megtekinteni a találkozókat, amik az időpontjuk alapján vannak rendezve.
    - Egy találkozóra koppintva megtekinthetőek annak az adatai, mint pl. az időpont, helyszín, téma, max létszám stb.
- Bárki aki hozzáfér az App-hoz képes új találkozók felvételére, meglévők törlésére.
    - Az alkalmazás egy előre beégetett API kulcsot fog tartalmazni, ami a backend-hez való hozzáférést fogja biztosítani.
- Az alkalmazás használata csak internet eléréssel lehetséges, nem tartalmaz perzisztens adattárolást.
- Az alkalmazás backendje az egy NodeJs-ben írt REST API, amit a saját szerveremen szolgáltatok a dancs.org domain alatt.
    - (Ennek a forráskódját is csatolni fogom)

## Választott technológiák:

- UI
- Fragmentek
- RecyclerView
- Retrofit