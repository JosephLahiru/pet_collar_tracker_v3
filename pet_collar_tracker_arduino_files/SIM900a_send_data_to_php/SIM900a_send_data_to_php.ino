#include <TinyGPS++.h>
#include <AltSoftSerial.h>

AltSoftSerial neogps;

TinyGPSPlus gps;

unsigned long previousMillis = 0;
long interval = 6000;

void init_gsm();
void gprs_connect();
boolean gprs_disconnect();
boolean is_gprs_connected();
void post_to_firebase();
boolean waitResponse(String expected_answer = "OK", unsigned int timeout = 2000);
void sendGpsToServer();

#include <SoftwareSerial.h>
SoftwareSerial SIM900a(4, 5);

const String APN  = "mobitel";
const String USER = "";
const String PASS = "";

String latitude = "0", longitude = "0";

#define USE_SSL true
#define DELAY_MS 500

void setup() {

  Serial.begin(9600);
  SIM900a.begin(9600);
  neogps.begin(9600);
  Serial.println("Initializing SIM900a...");
  init_gsm();
}


void loop() {

  unsigned long currentMillis = millis();
  if (currentMillis - previousMillis > interval) {
    previousMillis = currentMillis;
    sendGpsToServer();
    post_to_firebase();
  }

  if (!is_gprs_connected()) {
    gprs_connect();
  }
}


void post_to_firebase()
{

  SIM900a.println("AT+HTTPINIT");
  waitResponse();
  delay(DELAY_MS);

  if (USE_SSL == true) {
    SIM900a.println("AT+HTTPSSL=1");
    waitResponse();
    delay(DELAY_MS);
  }

  SIM900a.println("AT+HTTPPARA=\"CID\",1");
  waitResponse();
  delay(DELAY_MS);

  SIM900a.println("AT+HTTPPARA=\"URL\", https://travelmanagementsystem2021.000webhostapp.com/location.php?id=1&long="+ longitude +"&lati=" + latitude);
  waitResponse();
  delay(DELAY_MS);

  SIM900a.println("AT+HTTPPARA=\"REDIR\",1");
  waitResponse();
  delay(DELAY_MS);

  SIM900a.println("AT+HTTPACTION=1");

  for (uint32_t start = millis(); millis() - start < 20000;) {
    while (!SIM900a.available());
    String response = SIM900a.readString();
    if (response.indexOf("+HTTPACTION:") > 0)
    {
      Serial.println(response);
      break;
    }
  }

  delay(DELAY_MS);

  SIM900a.println("AT+HTTPREAD");
  waitResponse("OK");
  delay(DELAY_MS);

  SIM900a.println("AT+HTTPTERM");
  waitResponse("OK", 1000);
  delay(DELAY_MS);
}

void init_gsm()
{

  SIM900a.println("AT");
  waitResponse();
  delay(DELAY_MS);

  SIM900a.println("AT+CPIN?");
  waitResponse("+CPIN: READY");
  delay(DELAY_MS);

  SIM900a.println("AT+CFUN=1");
  waitResponse();
  delay(DELAY_MS);

  SIM900a.println("AT+CMEE=2");
  waitResponse();
  delay(DELAY_MS);

  SIM900a.println("AT+CBATCHK=1");
  waitResponse();
  delay(DELAY_MS);

  SIM900a.println("AT+CREG?");
  waitResponse("+CREG: 0,");
  delay(DELAY_MS);

  SIM900a.print("AT+CMGF=1\r");
  waitResponse("OK");
  delay(DELAY_MS);

}

void gprs_connect()
{

  SIM900a.println("AT+SAPBR=0,1");
  waitResponse("OK", 60000);
  delay(DELAY_MS);

  SIM900a.println("AT+SAPBR=3,1,\"Contype\",\"GPRS\"");
  waitResponse();
  delay(DELAY_MS);

  SIM900a.println("AT+SAPBR=3,1,\"APN\"," + APN);
  waitResponse();
  delay(DELAY_MS);

  if (USER != "") {
    SIM900a.println("AT+SAPBR=3,1,\"USER\"," + USER);
    waitResponse();
    delay(DELAY_MS);
  }

  if (PASS != "") {
    SIM900a.println("AT+SAPBR=3,1,\"PASS\"," + PASS);
    waitResponse();
    delay(DELAY_MS);
  }

  SIM900a.println("AT+SAPBR=1,1");
  waitResponse("OK", 30000);
  delay(DELAY_MS);

  SIM900a.println("AT+SAPBR=2,1");
  waitResponse("OK");
  delay(DELAY_MS);
}

boolean gprs_disconnect()
{

  SIM900a.println("AT+CGATT=0");
  waitResponse("OK", 60000);
  //delay(DELAY_MS);

  //DISABLE GPRS
  //SIM900a.println("AT+SAPBR=0,1");
  //waitResponse("OK",60000);
  //delay(DELAY_MS);


  return true;
}

boolean is_gprs_connected()
{
  SIM900a.println("AT+CGATT?");
  if (waitResponse("+CGATT: 1", 6000) == 1) {
    return false;
  }

  return true;
}

boolean waitResponse(String expected_answer, unsigned int timeout)
{
  uint8_t x = 0, answer = 0;
  String response;
  unsigned long previous;

  while ( SIM900a.available() > 0) SIM900a.read();

  previous = millis();
  do {
    if (SIM900a.available() != 0) {
      char c = SIM900a.read();
      response.concat(c);
      x++;
      if (response.indexOf(expected_answer) > 0) {
        answer = 1;
      }
    }
  } while ((answer == 0) && ((millis() - previous) < timeout));

  Serial.println(response);
  return answer;
}

void sendGpsToServer()
{
  boolean newData = false;
  for (unsigned long start = millis(); millis() - start < 2000;) {
    while (neogps.available()) {
      if (gps.encode(neogps.read())) {
        newData = true;
        break;
      }
    }
  }
  
  if (newData == true) {
    newData = false;

    float altitude;
    unsigned long date, time, speed, satellites;

    latitude = String(gps.location.lat(), 6); // Latitude in degrees (double)
    longitude = String(gps.location.lng(), 6); // Longitude in degrees (double)
    altitude = gps.altitude.meters(); // Altitude in meters (double)
    date = gps.date.value(); // Raw date in DDMMYY format (u32)
    time = gps.time.value(); // Raw time in HHMMSSCC format (u32)
    speed = gps.speed.kmph();

    Serial.print("Latitude= ");
    Serial.print(latitude);
    Serial.print(" Longitude= ");
    Serial.println(longitude);
  }
}
