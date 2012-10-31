// Rotery encoder pins
int ENCODER_A = 2;
int ENCODER_B = 3;

// Led pins
int LED_YELLOW= 10;
int LED_RED = 11;
int LED_GREEN = 12;

// Vars for realtime measurement
volatile int lastEncoded = 0;
volatile long encoderValue = 0;
volatile long intervalEncoderValue = 0;
volatile long lastIntervalEncoderValue = 0;

// Vars for direction
boolean clockWiseDirection = false;

// Speed
int rotaryPosition = 0;
int lastRotaryPosition = 0;

void setup() {
  Serial.begin (9600);
  pinMode(ENCODER_A, INPUT); 
  pinMode(ENCODER_B, INPUT);
  digitalWrite(ENCODER_A, HIGH);
  digitalWrite(ENCODER_B, HIGH);
  attachInterrupt(0, updateEncoder, CHANGE); 
  attachInterrupt(1, updateEncoder, CHANGE);
}

void loop(){
  // Calculate speed
  rotaryPosition = encoderValue;
  int rotarySpeed = abs(rotaryPosition - lastRotaryPosition);
  lastRotaryPosition = rotaryPosition;
  
  // Calculate speed led brightness
  int maxValue = 30;
  //if(rotarySpeed > maxValue) rotarySpeed = maxValue;
  int brightness = rotarySpeed * ( 255 / maxValue);
  analogWrite(LED_RED, brightness);
  
  // Display direction
  if(brightness){
    Serial.print("{direction: '");
    Serial.print(clockWiseDirection);
    Serial.print("', speed: '");
    Serial.print(rotarySpeed);
    Serial.print("'}");
    if(clockWiseDirection){
      analogWrite(LED_GREEN, 255);
      analogWrite(LED_YELLOW, 0);
    } else {
      analogWrite(LED_GREEN, 0);
      analogWrite(LED_YELLOW, 255);
    }
  } else {
    //Serial.print("{direction: '");
    //Serial.print(clockWiseDirection);
    //Serial.print("', speed: '");
    //Serial.print(0);
    //Serial.println("'}");
    //analogWrite(LED_GREEN, 0);
    //analogWrite(LED_YELLOW, 0);
  }
  //delay(50);
}

void updateEncoder(){
  // Read rotery encoder
  int MSB = digitalRead(ENCODER_A); //MSB = most significant bit
  int LSB = digitalRead(ENCODER_B); //LSB = least significant bit
  int encoded = (MSB << 1) |LSB; //converting the 2 pin value to single number
  int sum  = (lastEncoded << 2) | encoded; //adding it to the previous encoded value
  if(sum == 0b1101 || sum == 0b0100 || sum == 0b0010 || sum == 0b1011) encoderValue ++;
  if(sum == 0b1110 || sum == 0b0111 || sum == 0b0001 || sum == 0b1000) encoderValue --;

  // Calculate direction
  intervalEncoderValue = encoderValue;
  int difference = abs(intervalEncoderValue - lastIntervalEncoderValue);
  if(difference >= 2){
    clockWiseDirection = intervalEncoderValue <= lastIntervalEncoderValue;
    lastIntervalEncoderValue = intervalEncoderValue;
  }
  lastEncoded = encoded;
}
