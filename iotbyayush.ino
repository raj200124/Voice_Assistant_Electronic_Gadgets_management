#include <WiFi.h>
#include <FirebaseESP32.h>
#define FIREBASE_HOST "https://voice-7b421.firebaseio.com/"
#define FIREBASE_AUTH "cxNZtfKc2g4IzpAnz22A3vM6y7OCurp4x12eZNqx"
FirebaseData firebaseData;
int led=13;
int fan=12;
int ac=14;
int fridge=27;
void setup()
{
  
Serial.begin(9600);
pinMode(13, OUTPUT);
pinMode(12, OUTPUT);
pinMode(14, OUTPUT);
pinMode(27, OUTPUT);

  WiFi.disconnect();
  delay(3000);
  Serial.println("START");
  WiFi.begin("Realme3","123456789");
  while ((!(WiFi.status() == WL_CONNECTED))){
    delay(300);
    Serial.print("..");

  }
  Serial.println("Connected");
  Serial.println("Your IP is");
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
  Serial.println((WiFi.localIP()));

}


void loop()
{

    if (Firebase.getString(firebaseData, "/object")) {
      Serial.println("String Variable");
      Serial.println((firebaseData.stringData()));
      if((firebaseData.stringData())=="light on")
      {
        Serial.println("Light Off");
         digitalWrite(led, LOW);   
          delay(1000);
        
      }
      else if((firebaseData.stringData())=="light off")
      {
             Serial.println("Light On");
             digitalWrite(led, HIGH);  
             delay(1000);
      }
        else if((firebaseData.stringData())=="fan on")
      {
             Serial.println("fan Off");
             digitalWrite(fan, LOW);  
             delay(1000);
      }
        else if((firebaseData.stringData())=="fan off")
      {
             Serial.println("fan on");
             digitalWrite(fan, HIGH);  
             delay(1000);
      }
      else if((firebaseData.stringData())=="AC on")
      {
        Serial.println("AC Off");
             digitalWrite(ac, LOW);  
             delay(1000);
      }
      else if((firebaseData.stringData())=="AC off")
      {
         Serial.println("AC On");
             digitalWrite(ac, HIGH);  
             delay(1000);
      }
      else if((firebaseData.stringData())=="refrigerator on")
      {
         Serial.println("Fridge Off");
             digitalWrite(fridge, LOW);  
             delay(1000);
      }
      else if((firebaseData.stringData())=="refrigerator off")
      {
        Serial.println("Fridge On");
             digitalWrite(fridge, HIGH);  
             delay(1000);
      }
      else
      Serial.println("Repeat");

    }
    else
    {
      Serial.println("Error");
    }

}
