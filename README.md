# NusaLestari

NusaLestari adalah aplikasi Android untuk mengenal hewan dan tumbuhan endemik Indonesia. Aplikasi menampilkan informasi spesies berdasarkan kategori hewan dan tumbuhan, dilengkapi pencarian, filter region, favorit, serta mode terang dan gelap.

## Fitur Utama

* Menampilkan data hewan endemik Indonesia
* Menampilkan data tumbuhan endemik Indonesia
* Mengambil data dari API GitHub dan menyimpannya ke Room Database
* Halaman detail spesies
* Pencarian berdasarkan nama spesies atau nama latin
* Filter region berdasarkan asal dan sebaran spesies
* Menambahkan dan menghapus data favorit
* Mode light dan dark
* Halaman profil pengembang

## Teknologi yang Digunakan

* Android Studio
* Java
* XML Layout
* Retrofit dan Gson
* Room Database
* RecyclerView
* Glide
* SharedPreferences

## Sumber Data

Data spesies endemik diperoleh dari:

https://hendroprwk08.github.io/data_endemik/endemik.json 

## Struktur Database

Aplikasi menggunakan dua tabel Room Database:

* "endemik" untuk menyimpan data hewan dan tumbuhan endemik
* "favorit" untuk menyimpan spesies yang dipilih pengguna

## Pengembang

**Nama:** Glenn Ronaldo Tambunan
**NIM:** 2410501044

## Cara Menjalankan Aplikasi

1. Clone atau download repository ini.
2. Buka project menggunakan Android Studio.
3. Tunggu proses Gradle Sync selesai.
4. Hubungkan perangkat Android atau jalankan emulator.
5. Jalankan aplikasi.
6. Pastikan perangkat memiliki koneksi internet saat pertama kali membuka aplikasi agar data dapat diambil dan disimpan ke Room Database.

## Tampilan Aplikasi

### Splash Screen

![Splash Screen](screenshots/01_splash.jpeg)

### Home Hewan — Light Mode

![Home Hewan Light](screenshots/02_home_hewan_light.jpeg)

### Home Hewan — Dark Mode

![Home Hewan Dark](screenshots/03_home_hewan_dark.jpeg)

### Home Tumbuhan — Light Mode

![Home Tumbuhan Light](screenshots/04_home_tumbuhan_light.jpeg)

### Home Tumbuhan — Dark Mode

![Home Tumbuhan Dark](screenshots/05_home_tumbuhan_dark.jpeg)

### Detail Spesies Hewan

![Detail Hewan](screenshots/06_detail_hewan.jpeg)

### Detail Spesies Tumbuhan

![Detail Tumbuhan](screenshots/07_detail_tumbuhan.jpeg)

### Pencarian dan Filter Region

![Pencarian Region](screenshots/08_pencarian_region.jpeg)

### Favorit

![Favorit](screenshots/09_favorit.jpeg)

### Profil Pengembang

![Profil Pengembang](screenshots/10_profil_pengembang.jpeg)

### Ikon Aplikasi

![Ikon Aplikasi](screenshots/11_ikon_aplikasi.jpeg)
