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
  Serial.begin(115200);
  nfc.begin();
  nfc.SAMConfig();
  pinMode(9, OUTPUT);
  pinMode(8, OUTPUT);
}

  String cards [] = {"24078149124", "1102111259" };
void loop(void) {
  boolean success; 
  boolean dostep;
  uint8_t uid[] = { 0, 0, 0, 0, 0, 0, 0 };  // Tablica do przechowywania UID
  uint8_t uidLength; 

 
  // uid zawiera 4 bajty (Mifare Classic) lub 7 bajtow (Mifare Ultralight)
  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);
  
  if (success) 
  {
    // Wyświetlenie informacji o karcie
    String hex_value = "";
    for(uint8_t i=0; i<uidLength; i++)
    {
      hex_value += (String)uid[i];
    }
    Serial.println("ID karty="+hex_value);
    for(uint8_t j=0; j<=2; j++)
    {
      if(hex_value == cards[j])
      {
          digitalWrite(9, HIGH);
          delay(2000);
          digitalWrite(9, LOW);
      }
    }
  }
}

