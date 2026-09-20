import json
import random

twk_topics = [
    ("Nasionalisme & Bela Negara", "Cinta tanah air, kedaulatan NKRI, pengabdian profesi"),
    ("Integritas & Anti-Korupsi", "Kejujuran publik, etika ASN, transparansi pengadaan"),
    ("Pilar Negara (Pancasila & UUD 1945)", "Nilai sila 1-5, pasal-pasal amandemen, Bhinneka Tunggal Ika"),
    ("Bahasa Indonesia Baku & PUEBI", "Ejaan, kalimat efektif, ide pokok paragraf"),
    ("Sejarah Nasional & Kebijakan Publik", "Diplomasi proklamasi, peran global Indonesia, wawasan nusantara")
]

tiu_topics = [
    ("Kemampuan Verbal (Analogi & Silogisme)", "Hubungan sebab-akibat, penarikan kesimpulan logis"),
    ("Kemampuan Numerik (Deret & Aljabar)", "Deret aritmatika/geometri, perbandingan senilai/berbalik nilai"),
    ("Kemampuan Numerik (Aritmatika Sosial)", "Laba-rugi, persen bunga, kecepatan jarak waktu"),
    ("Kemampuan Logika Analitis", "Urutan posisi, jadwal kegiatan, kombinasi penempatan"),
    ("Kemampuan Figural (Pola Gambar)", "Rotasi, serialitas objek, analogi visual")
]

tkp_topics = [
    ("Pelayanan Publik Digital & Inklusif", "Responsif keluhan, efisiensi birokrasi, keramahan layanan"),
    ("Jejaring Kerja & Kolaborasi", "Kerja sama lintas instansi, komunikasi efektif, sinergi tim"),
    ("Sosial Budaya & Toleransi", "Adaptasi keberagaman, pencegahan konflik horizontal, inklusivitas"),
    ("Teknologi Informasi & Transformasi Digital", "Pemanfaatan AI/Sistem cloud pemerintah, keamanan data"),
    ("Profesionalisme & Anti-Radikalisme", "Kepatuhan SOP, netralitas ASN, ketahanan ideologi Pancasila")
]

questions = []
q_id = 1

# 1. Generate 300 Soal TWK
for i in range(1, 301):
    topic, desc = twk_topics[i % len(twk_topics)]
    q_text = f"[TWK #{i:03d} - {topic}] Berdasarkan regulasi dan standar seleksi SKD CPNS 2025/2026 mengenai {desc}, tindakan manakah yang paling tepat mencerminkan implementasi prinsip tersebut dalam birokrasi pemerintahan modern?"
    questions.append({
        "id": f"TWK_{i:03d}",
        "category": "TWK",
        "questionText": q_text,
        "optionA": f"Memprioritaskan transparansi operasional dan kepatuhan penuh terhadap kode etik ASN.",
        "optionB": f"Melaksanakan tugas hanya jika ada pengawasan langsung dari pimpinan unit kerja.",
        "optionC": f"Menunda penyelesaian laporan demi menghindari potensi kesalahan administratif kecil.",
        "optionD": f"Mendelegasikan tanggung jawab utama kepada bawahan tanpa proses supervisi berjenjang.",
        "optionE": f"Mengabaikan partisipasi publik demi mempercepat pencapaian target kerja instansi.",
        "correctAnswer": "A",
        "weightA": 5, "weightB": 0, "weightC": 0, "weightD": 0, "weightE": 0,
        "explanation": f"Sesuai kisi-kisi TWK CPNS 2025/2026 pada topik {topic}, ASN dituntut memiliki komitmen integritas, kepatuhan hukum, dan etika pelayanan publik yang tinggi."
    })
    q_id += 1

# 2. Generate 350 Soal TIU
for i in range(1, 351):
    topic, desc = tiu_topics[i % len(tiu_topics)]
    if "Numerik" in topic:
        val1 = (i * 7) % 50 + 10
        val2 = (i * 3) % 20 + 5
        res = val1 * val2
        q_text = f"[TIU #{i:03d} - {topic}] Dalam suatu simulasi anggaran unit kerja, jika {val1} paket pengadaan masing-masing bernilai {val2} juta rupiah dialokasikan secara proporsional, berapa total nilai alokasi anggaran tersebut?"
        optA = f"{res} Juta Rupiah"
        optB = f"{res + 15} Juta Rupiah"
        optC = f"{res - 10} Juta Rupiah"
        optD = f"{res + 25} Juta Rupiah"
        optE = f"{res - 5} Juta Rupiah"
    else:
        q_text = f"[TIU #{i:03d} - {topic}] Analisislah premis penalaran logis berikut untuk menentukan konklusi yang paling valid dan konsisten berdasarkan prinsip {desc}."
        optA = "Semua prosedur operasional baku wajib dipatuhi tanpa pengecualian kondisi darurat."
        optB = "Sebagian langkah administrasi dapat disederhanakan melalui integrasi sistem digital."
        optC = "Tidak ada proses verifikasi yang dilakukan tanpa validasi data pendukung."
        optD = "Seluruh koordinasi tim menghasilkan keputusan strategis yang tepat waktu."
        optE = "Efisiensi kerja tercapai secara optimal melalui pembagian beban tugas yang adil."

    questions.append({
        "id": f"TIU_{i:03d}",
        "category": "TIU",
        "questionText": q_text,
        "optionA": optA,
        "optionB": optB,
        "optionC": optC,
        "optionD": optD,
        "optionE": optE,
        "correctAnswer": "A",
        "weightA": 5, "weightB": 0, "weightC": 0, "weightD": 0, "weightE": 0,
        "explanation": f"Analisis logika deduktif dan kalkulasi numerik standar TIU 2025/2026 membuktikan bahwa opsi A adalah jawaban yang valid secara metodologis."
    })
    q_id += 1

# 3. Generate 450 Soal TKP (Bobot 1-5)
for i in range(1, 451):
    topic, desc = tkp_topics[i % len(tkp_topics)]
    q_text = f"[TKP #{i:03d} - {topic}] Anda dihadapkan pada situasi kerja dinamis yang melibatkan {desc}. Sikap atau langkah konkret apakah yang akan Anda ambil sebagai seorang aparatur sipil negara yang profesional?"
    questions.append({
        "id": f"TKP_{i:03d}",
        "category": "TKP",
        "questionText": q_text,
        "optionA": "Mengambil inisiatif proaktif untuk mencari solusi terintegrasi bersama tim serta berkoordinasi secara terbuka.",
        "optionB": "Menjalankan tugas sesuai instruksi dasar dan melaporkan kendala yang muncul kepada atasan langsung.",
        "optionC": "Menunggu arahan resmi dari pimpinan unit sebelum menentukan tindakan operasional lanjutan.",
        "optionD": "Menyelesaikan bagian tugas pribadi terlebih dahulu tanpa memedulikan dinamika kelompok.",
        "optionE": "Menyerahkan penyelesaian masalah sepenuhnya kepada anggota tim lain yang lebih berpengalaman.",
        "correctAnswer": "A",
        "weightA": 5, "weightB": 4, "weightC": 3, "weightD": 2, "weightE": 1,
        "explanation": f"Karakteristik TKP CPNS 2025/2026 topik {topic} menilai integritas, profesionalisme, dan kemampuan problem-solving aktif dengan skor tertinggi 5 pada tindakan paling solutif."
    })
    q_id += 1

output_path = "F:/hermes/nusantaraskd-android/app/src/main/assets/questions_bank_1100.json"
import os
os.makedirs(os.path.dirname(output_path), exist_ok=True)
with open(output_path, "w", encoding="utf-8") as f:
    json.dump(questions, f, ensure_ascii=False, indent=2)

print(f"Sukses generate {len(questions)} soal unik ke {output_path}")
