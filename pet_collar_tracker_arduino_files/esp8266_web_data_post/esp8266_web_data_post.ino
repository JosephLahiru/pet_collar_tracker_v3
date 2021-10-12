#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <Arduino.h>
#include <TinyGPS++.h>
#include <SoftwareSerial.h>

#define WIFI_SSID "HUAWEI nova 7 SE"
#define WIFI_PASSWORD "b71a81458b10"

int id;
double latitude, longitude;

String postData;

String date_time = "00:00:00", previous_date_time;

TinyGPSPlus gps;
SoftwareSerial gpsSerial(D1, D2); // RX, TX
char buffer[100];

void printData()
{
  if (gps.location.isUpdated()) {
    Serial.println("");
    double lat = gps.location.lat();
    double lng = gps.location.lng();

    latitude = lat;
    longitude = lng;

    double altitude = gps.altitude.meters();

    int year = gps.date.year();
    int month = gps.date.month();
    int day = gps.date.day();

    int hour = gps.time.hour();
    int minute = gps.time.minute();
    int second = gps.time.second();

    date_time = (String)hour + ":" + ":" + (String)minute + ":" + (String)second;

    snprintf(buffer, sizeof(buffer),
             "Latitude: %.8f, Longitude: %.8f, Altitude: %.2f m, "
             "Date/Time: %d-%02d-%02d %02d:%02d:%02d",
             lat, lng, altitude, year, month, day, hour, minute, second);

    Serial.println(buffer);
  }
}

void setup() {

  Serial.begin(9600);
  gpsSerial.begin(9600);
  Serial.println("Communication Started \n\n");
  delay(1000);

  pinMode(LED_BUILTIN, OUTPUT);

  WiFi.mode(WIFI_STA);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED)
  { Serial.print(".");
    delay(500);
  }

  Serial.println();
  Serial.print("Connected to ");
  Serial.println(WIFI_SSID);
  Serial.print("IP Address is : ");
  Serial.println(WiFi.localIP());

  delay(3000);
}

void loop() {

  while (gpsSerial.available() > 0) {
    if (gps.encode(gpsSerial.read())) {
      printData();

      HTTPClient http;
      printData();
      id = 1;

      postData = "id=" + (String)id + "&lati=" + String(latitude, 7) + "&long=" + String(longitude, 7);
      Serial.println("Values are, id=" + (String)id + "&lati=" + String(latitude, 7) + "&long=" + String(longitude, 7));

      if (date_time != previous_date_time) {

        previous_date_time = date_time;

        http.begin("http://travelmanagementsystem2021.000webhostapp.com/location.php");
        http.addHeader("Content-Type", "application/x-www-form-urlencoded");
        int httpCode = http.POST(postData);


        if (httpCode == 200) {
          Serial.println("Values uploaded successfully."); Serial.println(httpCode);
          String webpage = http.getString();
          Serial.println(webpage + "\n");
        }

        else {
          Serial.println(httpCode);
          Serial.println("Failed to upload values. \n");
          http.end();
          delay(500);
          return;
        }
      }
      delay(2000);
      digitalWrite(LED_BUILTIN, LOW);
      delay(2000);
      digitalWrite(LED_BUILTIN, HIGH);

    }
  }
}
