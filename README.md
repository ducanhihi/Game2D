# Game 2D - Java RPG Adventure

Một game RPG 2D được phát triển bằng Java Swing với đồ họa pixel art và gameplay kinh điển.

## 🎮 Tính năng chính

### Gameplay
<img width="927" height="561" alt="image" src="https://github.com/user-attachments/assets/0ad59fbd-6999-4e33-b56a-f8405943052f" />

- **Điều khiển 4 hướng**: Di chuyển nhân vật bằng phím WASD
- **Hệ thống combat**: Tấn công quái vật bằng phím Enter, bắn projectile bằng phím F
- **Hệ thống level**: Nhân vật có thể lên cấp, tăng stats khi tiêu diệt quái vật
- **Inventory system**: Quản lý vật phẩm, trang bị vũ khí và giáp
- **Multiple maps**: Khám phá nhiều khu vực khác nhau với hệ thống teleport

### Nhân vật & Stats
<img width="1053" height="644" alt="image" src="https://github.com/user-attachments/assets/4984e093-ed7b-4453-b8b9-c1027f9b1041" />
- **Level**: Bắt đầu từ level 1, có thể lên cấp bằng cách tiêu diệt quái vật
- **HP/Mana**: Hệ thống máu và mana với khả năng hồi phục
- **Stats**: Strength (sức mạnh), Dexterity (khéo léo), Attack, Defense
- **Equipment**: Trang bị kiếm, rìu, khiên với các chỉ số khác nhau
- **Projectiles**: Bắn fireball hoặc đá với hệ thống ammo

### Quái vật
- **Green Slime**: Quái cơ bản, dễ tiêu diệt
- **Orc**: Quái mạnh hơn với sát thương cao<img width="82" height="108" alt="image" src="https://github.com/user-attachments/assets/9657fd84-e578-4128-9ef4-ccc7781b9ddf" />

- **Skeleton**: Quái xương với khả năng tấn công từ xa

### NPC & Tương tác
<img width="760" height="395" alt="image" src="https://github.com/user-attachments/assets/bc2ceef4-e3bb-4764-b590-09d292e744fa" />

- **Old Man**: NPC cung cấp thông tin và nhiệm vụ
- **Ninja Sale**: Thương gia bán vũ khí và vật phẩm
- **Dialogue system**: Hệ thống hội thoại với NPC

### Hệ thống vật phẩm
<img width="269" height="167" alt="image" src="https://github.com/user-attachments/assets/41ff0c33-c66b-4c1d-b96e-a6f76f430c82" />
- **Keys**: Chìa khóa để mở cửa thường
- **Green Stone**: Đá xanh để mở cửa sắt
- **Weapons**: Kiếm, rìu với damage và knockback khác nhau
- **Shields**: Khiên gỗ, khiên xanh với defense khác nhau
- **Consumables**: Potion đỏ hồi máu, mana crystal hồi mana

### Tính năng đặc biệt
- **Healing Pool**: Hồ nước hồi phục hoàn toàn HP/Mana
- **Damage Pit**: Khu vực gây sát thương khi đi qua
- **Interactive Tiles**: Cây khô có thể phá hủy
- **Particle Effects**: Hiệu ứng khi tấn công, phá hủy vật thể
- **Sound System**: Âm thanh nền và hiệu ứng âm thanh

## 🎯 Điều khiển

### Di chuyển
- **W**: Di chuyển lên
- **A**: Di chuyển trái  
- **S**: Di chuyển xuống
- **D**: Di chuyển phải

### Hành động
- **Enter**: Tấn công/Tương tác với NPC/Xác nhận
- **F**: Bắn projectile
- **C**: Mở inventory/character stats
- **P**: Tạm dừng game
- **Escape**: Mở menu options

### Debug (chỉ dành cho developer)
- **T**: Bật/tắt debug info
- **Y**: Reload map

## 🗺️ Maps & Khu vực

Game có nhiều map được kết nối với nhau:
- **Map 0**: Khu vực bắt đầu
- **Map 1**: Dungeon với skeleton và cửa sắt
- **Map 2**: Khu vực chính với NPC, shop và healing pool

## 🎨 Đồ họa & Âm thanh

- **Pixel Art**: Đồ họa 16x16 pixel được scale lên 48x48
- **Animations**: Sprite animation cho di chuyển và tấn công
- **Particle System**: Hiệu ứng máu, lửa, bụi khi combat
- **Sound Effects**: Âm thanh cho tất cả hành động
- **Background Music**: Nhạc nền tạo không khí


## 🚀 Cách chạy game
1. Đảm bảo đã cài đặt Java JDK
2. Compile tất cả file .java
3. Chạy file Main.java
4. Game sẽ mở trong cửa sổ mới



## 📁 Cấu trúc project
\`\`\`
src/
├── main/           # Core game engine
├── entity/         # Player, NPC, Monster classes  
├── object/         # Items, weapons, consumables
├── monster/        # Monster AI và behavior
├── tile/           # Map tiles và tile manager
├── ai/             # Pathfinding cho AI
└── res/            # Resources (images, sounds, maps)
\`\`\`

## 🎯 Mục tiêu game

- Khám phá tất cả các map
- Lên level cao nhất có thể
- Thu thập tất cả vũ khí và trang bị
- Tiêu diệt tất cả boss monsters
- Hoàn thành tất cả nhiệm vụ từ NPC

Game này là một dự án học tập về game development với Java, thể hiện các khái niệm cơ bản của lập trình game như game loop, collision detection, sprite animation, và state management.
