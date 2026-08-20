# Hepsiburada Web Otomasyon Testi

Bu proje, Hepsiburada web uygulaması üzerinde belirlenen kullanıcı senaryosunun otomatik olarak test edilmesi amacıyla geliştirilmiştir.

Test otomasyonu **Java, Selenium WebDriver, Gauge Framework ve Page Object Model (POM)** kullanılarak hazırlanmıştır.

## Test Senaryosu

**HB-TC01**

Senaryo kapsamında:

1. Tarayıcı açılır.
2. Hepsiburada ana sayfasına gidilir.
3. Çerez bildirimi kabul edilir.
4. Kullanıcı hesabına giriş yapılır.
5. `"bilgisayar"` kelimesi aranır.
6. Arama sonuçlarının görüntülendiği doğrulanır.
7. İkinci satırdaki ilk ürün seçilir.
8. Seçilen ürün ile ürün detay sayfasındaki ürün bilgilerinin eşleştiği doğrulanır.
9. Ürün sepete eklenir.
10. Ürünün sepete eklendiğine dair onay kontrol edilir.
11. Sepet açılır.
12. Sepette bulunan ürün ile seçilen ürünün eşleştiği doğrulanır.
13. Test sonunda sepet temizlenir.

## Kullanılan Teknolojiler

- Java 17
- Selenium WebDriver
- Gauge Framework
- Maven
- JUnit 5
- Jackson
- SLF4J / Logback
- Page Object Model (POM)

## Proje Yapısı

```text
Hepsiburada_Otomasyon
│
├── env/
│   └── default/
│
├── specs/
│   ├── concepts/
│   └── hepsiburada.spec
│
├── src/test/
│   ├── java/
│   │   ├── pages/
│   │   ├── steps/
│   │   └── utils/
│   │
│   └── resources/
│       ├── locators.json
│       └── logback-test.xml
│
├── libs/
├── manifest.json
└── pom.xml
```

### `pages`

Web sayfalarına ait elementler ve bu elementler üzerinde gerçekleştirilen işlemler bulunur.

### `steps`

Gauge specification ve concept dosyalarında kullanılan step implementasyonlarını içerir.

### `utils`

Driver oluşturma, konfigürasyon okuma, locator okuma ve test yaşam döngüsü gibi ortak işlemleri içerir.

### `locators.json`

Web elementlerine ait locator bilgileri merkezi olarak tutulur. Böylece locator değişiklikleri Page sınıflarına müdahale edilmeden güncellenebilir.

## Konfigürasyon

Test ortamına ait ayarlar `env/default/default.properties` üzerinden yönetilmektedir.

Örnek:

```properties
base_url=https://www.hepsiburada.com
default_wait_seconds=10
cookie_wait_seconds=5
```

## Kullanıcı Bilgileri

Güvenlik nedeniyle kullanıcı adı ve şifre proje içerisinde tutulmamaktadır.

Testi çalıştırmadan önce aşağıdaki environment variable'ların tanımlanması gerekir:

```text
HB_USERNAME
HB_PASSWORD
```

Her kullanıcı bu değişkenlere kendi test hesabına ait bilgileri tanımlamalıdır.

## Testin Çalıştırılması

Proje dizininde öncelikle Maven ile test kaynakları derlenir ve gerekli bağımlılıklar hazırlanır:

```bash
mvn test-compile
```

Ardından Gauge testi çalıştırılır:

```bash
gauge run specs/hepsiburada.spec
```

## Raporlama

Test çalışması tamamlandıktan sonra Gauge HTML raporu otomatik olarak oluşturulur.

Rapor:

```text
reports/html-report/index.html
```

konumundan görüntülenebilir.

## Logging

Test sırasında önemli işlemler SLF4J ve Logback kullanılarak loglanmaktadır.

Loglarda;

- seçilen ürün,
- ürün fiyatı,
- ürün detay sayfasındaki ürün,
- expected/actual ürün karşılaştırmaları,
- sepet ürün doğrulaması,
- hata durumları

takip edilebilir.

Log dosyası çalışma sırasında:

```text
logs/test.log
```

altında oluşturulur.

## Not

Hepsiburada giriş sistemi otomasyon trafiğine bağlı olarak zaman zaman güvenlik kontrolleri uygulayabilir. Bu nedenle giriş adımı bazı çalıştırmalarda site tarafından engellenebilir.