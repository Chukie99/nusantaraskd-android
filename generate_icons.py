from PIL import Image, ImageDraw, ImageFont
import os

# Definisikan direktori
res_dir = "F:/hermes/nusantaraskd-android/app/src/main/res"
sizes = {
    "mipmap-hdpi": 72,
    "mipmap-mdpi": 48,
    "mipmap-xhdpi": 96,
    "mipmap-xxhdpi": 144,
    "mipmap-xxxhdpi": 192
}

def create_icon(size, name):
    # Buat icon simpel: background biru gelap, teks 'NS' putih
    img = Image.new('RGB', (size, size), color='#1E3A8A')
    draw = ImageDraw.Draw(img)
    
    # Coba pakai font default, kalau gagal pakai ukuran otomatis
    try:
        font = ImageFont.truetype("arial.ttf", int(size * 0.5))
    except:
        font = ImageFont.load_default()
        
    text = "NS"
    # Hitung posisi teks agar center
    bbox = draw.textbbox((0, 0), text, font=font)
    w = bbox[2] - bbox[0]
    h = bbox[3] - bbox[1]
    draw.text(((size-w)/2, (size-h)/2), text, fill="white", font=font)
    
    path = os.path.join(res_dir, name)
    os.makedirs(path, exist_ok=True)
    img.save(os.path.join(path, "ic_launcher.png"))

print("Generating icons...")
for folder, size in sizes.items():
    create_icon(size, folder)
    print(f"Generated {folder}")

print("Icons generated successfully!")
