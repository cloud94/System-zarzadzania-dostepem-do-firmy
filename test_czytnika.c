/**************************************************************************
 * Projekt Zespołowy
 * Autorzy: Mateusz Chmurzyński, Dawid Branicki
 * Program testowy sprawdzajacy poprawnosc dzialania sprzętu do czytania
 * tagoów RFID oraz NFC (na podstawie przykładowego programu dołączonego
 * do biblioteki Adafruit_PN532.h udostępnianego przez firmę Adafruit
**************************************************************************/
#include <Wire.h>
#include <Adafruit_PN532.h>

#define PN532_IRQ   (6) // Przepiete z 2 na 6 (ponieważ używamy płytki Arduino Leonardo)
#define PN532_RESET (3)

// deklaracja zmiennej do komunikacji I2C
Adafruit_PN532 nfc(PN532_IRQ, PN532_RESET);

#if defined(ARDUINO_ARCH_SAMD)
   #define Serial SerialUSB
#endif

void setup(void) {
  #ifndef ESP8266
    while (!Serial);
  #endif
  Serial.begin(115200);
  Serial.println("Rozpoczynam prace");

  nfc.begin();

  uint32_t versiondata = nfc.getFirmwareVersion();
  if (! versiondata) {
    Serial.print("Czytnik nie jest podlaczony. Sprawdz podlaczenie i zresetuj program.");
    while (1); // halt
  }
  Serial.print("Znaleziono czytnik PN5"); Serial.println((versiondata>>24) & 0xFF, HEX); 
  Serial.print("Wersja: "); Serial.print((versiondata>>16) & 0xFF, DEC); 
  Serial.print('.'); Serial.println((versiondata>>8) & 0xFF, DEC);
  
  // configure board to read RFID tags
  nfc.SAMConfig();
  
  Serial.println("Czekam na zblizenie karty RFID/NFC...");
}


void loop(void) {
  uint8_t success;
  uint8_t uid[] = { 0, 0, 0, 0, 0, 0, 0 };  // Tablica do przechowywania UID
  uint8_t uidLength;                        // Długość UID (4 lub 7 bajtów w zależności od rodzaju karty)
    
  // uid zawiera 4 bajty (Mifare Classic) lub 7 bajtow (Mifare Ultralight)
  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);
  
  if (success) {
    // Wyświetlenie informacji o karcie
    Serial.println("Znaleziono karte zgodna z ISO14443A.");
    Serial.print("  Dlugosc UID: ");Serial.print(uidLength, DEC);Serial.println(" bytes");
    Serial.print("  Wartosc UID: ");
    nfc.PrintHex(uid, uidLength);
    Serial.println("");
    
    if (uidLength == 4)
    {
      Serial.println("Wyglada na karte Mifare Classic (4-bajtowy UID)");
	  
      Serial.println("Probuje autoryzowac blok 4 za pomoca domyslnej wartosci klucza KeyA");
      uint8_t keya[6] = { 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF };
	  
      success = nfc.mifareclassic_AuthenticateBlock(uid, uidLength, 4, 0, keya);
	  
      if (success)
      {
        Serial.println("Sektor 1 (Blocks 4..7) zostal autoryzowany");
        uint8_t data[16];
		
        success = nfc.mifareclassic_ReadDataBlock(4, data);
		
        if (success)
        {
          Serial.println("Czytam blok 4:");
          nfc.PrintHexChar(data, 16);
          Serial.println("");
	        delay(1000);
        }
        else
        {
          Serial.println("Odczyt nie udal sie. Sprobuj innej karty");
        }
      }
      else
      {
        Serial.println("Autoryzacja nie udala sie. Sprobuj innej karty");
      }
    }
    
    if (uidLength == 7)
    {
      Serial.println("Wyglada na tag Mifare Ultralight (7-bajtowy UID)");
	  
      Serial.println("Czytam strone 4");
      uint8_t data[32];
      success = nfc.mifareultralight_ReadPage (4, data);
      if (success)
      {
        // Data seems to have been read ... spit it out
        nfc.PrintHexChar(data, 4);
        Serial.println("");
		
        // Sekundowe opóźnienie
        delay(1000);
      }
      else
      {
        Serial.println("Nie udalo sie.");
      }
    }
  }
}
