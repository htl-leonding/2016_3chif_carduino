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
#include <Servo.h>

int STBY = D4; //standby

//Motor A
int PWMA = D7; //Speed control
int AIN2 = D6; //Direction

//Motor B
int PWMB = D1; //Speed control
int BIN1 = D3; //Direction
int BIN2 = D2; //Direction

Servo SERVO;
int in = 0;

int i = 0;
int val;
int sensorValue = 512;
String commant;
char c;

//Daham
const char* ssid = "Carduino";
const char* password = "carduino";

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
  pinMode(AIN2, OUTPUT);

  pinMode(PWMB, OUTPUT);
  pinMode(BIN1, OUTPUT);
  pinMode(BIN2, OUTPUT);
  SERVO.attach(D8);

  //pinMode(testLED, OUTPUT);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  server.begin();
  Serial.println(WiFi.localIP());

}
void runCommant() {
  c = commant[0];
  Serial.println(commant);
  //commant = Serial.read();
  //digitalWrite(STBY, HIGH);
  if (c == 'd') {
	SERVO.write((int)(commant.substring(1).toInt()/5.69));
  }
  if (c == 'w') {
    Serial.println(commant.substring(1).toInt());
    digitalWrite(AIN2, HIGH);
    analogWrite(PWMA, commant.substring(1).toInt());
  }
  if (c == 's') {
    Serial.println(commant.substring(1).toInt());
    digitalWrite(AIN2, LOW);
    analogWrite(PWMA, commant.substring(1).toInt());
  }
}

void loop() {
  client = server.available();
  delay(10);
  if (client) {
    Serial.println("new client");
    commant = client.readStringUntil('\n');
    runCommant();
    client.flush();
    client.stop();
  }
  if (Serial.available() > 0) {
    commant = Serial.readStringUntil('\n');
    runCommant();
  }
}



void stop() {
  //enable standby
  digitalWrite(STBY, LOW);
}


