/*
  D0 = 16;
  D1 = 5;
  D2 = 4;
  D3 = 0;
  D4 = 2;
  D5 = 14;
  D6 = 12;
  D7 = 13;
  D8 = 15;
  D9 = 3;
  D10 = 1;
*/

#include <ESP8266WiFi.h>

int STBY = D4; //standby

//Motor A
int PWMA = D7; //Speed control
int AIN1 = D5; //Direction
int AIN2 = D6; //Direction

//Motor B
int PWMB = D1; //Speed control
int BIN1 = D3; //Direction
int BIN2 = D2; //Direction

int testLED = D8;

int i = 0;
int val;
int sensorValue = 512;
String commant;
char c;

//Schul W-Lan
/*const char* ssid = "andi-hotspot";
const char* password = "oscaryim";*/

//Daham
const char* ssid = "3WebCube6352";
const char* password = "1D3fcf8d";

WiFiServer server(1337);
static WiFiClient client;
IPAddress ip(192, 168, 1, 128); //Node static IP
IPAddress gateway(192, 168, 1, 1);
IPAddress subnet(255, 255, 255, 0);

void setup() {
  Serial.begin(115200);

  WiFi.begin(ssid, password);
  WiFi.config(ip, gateway, subnet);
  pinMode(STBY, OUTPUT);

  pinMode(PWMA, OUTPUT);
  pinMode(AIN1, OUTPUT);
  pinMode(AIN2, OUTPUT);

  pinMode(PWMB, OUTPUT);
  pinMode(BIN1, OUTPUT);
  pinMode(BIN2, OUTPUT);

  //pinMode(testLED, OUTPUT);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  server.begin();
  Serial.println(WiFi.localIP());

}

void loop() {

  client = server.available();
  if (!client) {
    return;
  }
  //Serial.println("new client");
  //digitalWrite(testLED, HIGH);
  while (!client.available()) {
    delay(1);
  }
  //digitalWrite(testLED, HIGH);
  // Read the first line of the request
  commant = client.readStringUntil('\n');
 // Serial.println(req);



  sensorValue = analogRead(A0);
  //Serial.println(sensorValue);
  if (val == sensorValue) {
    digitalWrite(BIN1, LOW);
    digitalWrite(BIN2, LOW);
    Serial.println("fertig");
  }
  /*if (Serial.available() > 0) {*/
    //commant = Serial.readStringUntil('\n');
    c = commant[0];
    Serial.println(commant);
    //commant = Serial.read();
    digitalWrite(STBY, HIGH);
    if (c == 'd') {
      val = commant.substring(1).toInt();
      Serial.println(val);
      Serial.println(sensorValue);
            Serial.println("___________");
      if (val > sensorValue) {
        digitalWrite(BIN1, HIGH);
        digitalWrite(BIN2, LOW);
        analogWrite(PWMB, 1023);
        Serial.println("left");
      }
      else if (val < sensorValue) {
        digitalWrite(BIN1, LOW);
        digitalWrite(BIN2, HIGH);
        analogWrite(PWMB, 1023);
        Serial.println("right");
      }
    }
    if (c == 'w') {
      Serial.println(commant.substring(1).toInt());
      digitalWrite(AIN1, HIGH);
      digitalWrite(AIN2, LOW);
      analogWrite(PWMA, commant.substring(1).toInt());
    }
    if (c == 's') {
      Serial.println(commant.substring(1).toInt());
      digitalWrite(AIN1, LOW);
      digitalWrite(AIN2, HIGH);
      analogWrite(PWMA, commant.substring(1).toInt());
    }
    /*else {
      digitalWrite(STBY, LOW);
      }*/

    //Serial.println("Succesfully received.");
  //}
    client.flush();

  delay(1);
  client.stop();

}


void stop() {
  //enable standby
  digitalWrite(STBY, LOW);
}


