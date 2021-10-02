#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>

#define FIREBASE_HOST "petcollartracker-4af2b-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "swAwtmUeRA8vmm3lVAbhmBW362lEK79hj01dUVTS"
#define WIFI_SSID "Joe's_Home_Net_AP1"
#define WIFI_PASSWORD "TheAlphaGuardian1234!"

int previous_key = 0;
bool json_pharse_failed = false;

void setup() {
  Serial.begin(9600);

  // connect to wifi.
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

int n = 0;

void loop() {

  HTTPClient http;
  String payload = "{}";
  http.begin("http://petcollartrackerfirebase.000webhostapp.com/read_live_location.php");

  int httpCode = http.GET();

  if (httpCode == 200) {
    Serial.println(httpCode);
    payload = http.getString();
    Serial.println(payload + "\n");
  }

  else {
    Serial.println(httpCode);
    Serial.println("Failed to retrieve values. \n");
    http.end();
    delay(1000);
    return;
  }
  StaticJsonBuffer<220> jsonBuffer;
  JsonObject& root = jsonBuffer.parseObject(payload);

  if (!root.success()) {
    Serial.println("parseObject() failed");
    json_pharse_failed = true;
  }
  else {
    json_pharse_failed = false;
  }

  int key = root["key"];
  int id = root["id"];
  String latitude = root["long"];
  String longitude = root["lat"];
  String date_time = root["time"];

  int dev_id = id-1;

  if (key != previous_key && !json_pharse_failed) {

    previous_key = key;

    Serial.println("id = " + String(id) + ", latitude = " + latitude + ", longitude = " + longitude + ", date_time = " + date_time);

    String latitude_out = Firebase.pushString("Location/Dev" + String(dev_id) + "/latitude", latitude);
    String longitude_out = Firebase.pushString("Location/Dev" + String(dev_id) + "/longitude", longitude);
    String date_time_out = Firebase.pushString("Location/Dev" + String(dev_id) + "/time", date_time);

    Firebase.setString("liveLocation/Dev" + String(dev_id) + "/latitude", latitude);
    Firebase.setString("liveLocation/Dev" + String(dev_id) + "/longitude", longitude);
    Firebase.setString("liveLocation/Dev" + String(dev_id) + "/time", date_time);

    // handle error
    if (Firebase.failed()) {
      Serial.print("pushing /logs failed:");
      Serial.println(Firebase.error());
      return;
    }
    Serial.print("pushed: /logs/");
    delay(1000);
  }

  delay(5000);
}
