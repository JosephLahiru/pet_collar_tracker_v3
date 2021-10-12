#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <Arduino.h>
#include <TinyGPS++.h>
#include <SoftwareSerial.h>

#define FIREBASE_HOST "petcollartracker-4af2b-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "swAwtmUeRA8vmm3lVAbhmBW362lEK79hj01dUVTS"
#define WIFI_SSID "Joe's_Home_Net_AP1"
#define WIFI_PASSWORD "TheAlphaGuardian1234!"

TinyGPSPlus gps;
SoftwareSerial gpsSerial(D1, D2); // RX, TX
char buffer[100];

int previous_key = 0;
bool json_pharse_failed = false;

int del = 0;
int id = 0;
int add_hour = 0;

double latitude, longitude;
String date_time = "00:00:00";
String prev_date_time = "";

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

    minute = minute + 30;
    if (minute > 59) {
      minute = minute - 60;
      add_hour = 1;
    }
    hour = hour + 5 + add_hour;
    if (hour > 23) {
      hour = hour - 24;
    }

    date_time = String(hour) + ":" + String(minute) + ":" + String(second);

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

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

void loop() {

  while (gpsSerial.available() > 0) {
    if (gps.encode(gpsSerial.read())) {
      printData();

      Serial.println("id = " + String(id) + ", latitude = " + String(latitude, 7) + ", longitude = " + String(longitude, 7) + ", date_time = " + date_time);

      if (prev_date_time != date_time) {

        prev_date_time = date_time;

        String latitude_out = Firebase.pushString("Location/dev" + String(id) + "/latitude", String(latitude, 7));
        String longitude_out = Firebase.pushString("Location/dev" + String(id) + "/longitude", String(longitude, 7));
        String date_time_out = Firebase.pushString("Location/dev" + String(id) + "/time", date_time);

      }

      Firebase.setString("liveLocation/dev" + String(id) + "/latitude", String(latitude, 7));
      Firebase.setString("liveLocation/dev" + String(id) + "/longitude", String(longitude, 7));
      Firebase.setString("liveLocation/dev" + String(id) + "/time", date_time);

      if (Firebase.failed()) {
        Serial.print("pushing /data failed:");
        Serial.println(Firebase.error());
        return;
      }
      Serial.println("pushed: /data/");

      Firebase.setString("liveLocation/dev" + String(id) + "/ping", String(random(0, 1000)));
      Firebase.setString("Location/dev" + String(id) + "/ping", String(random(0, 1000)));

      if (Firebase.failed()) {
        Serial.print("pushing /ping failed:");
        Serial.println(Firebase.error());
        return;
      }
      Serial.println("pushed: /ping/");

      delay(5000);
      del++;
    }
  }
}
