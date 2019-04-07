import processing.serial.*;
Serial mySerial;
//import java.text.*;
//import java.util.*;
PrintWriter output;  
long startMillis;  //some global variables available anywhere in the program
long currentMillis;
long period = 3600000;  //the value is a number of milliseconds

void setup() {
  printArray(Serial.list());
  size(720,700);
  background(255);
  mySerial = new Serial( this, Serial.list()[2], 9600 );
  mySerial.bufferUntil ( '\n' );
  output = createWriter("test5.txt");
}

void draw() {
if (mySerial.available() > 0 ) {
     delay(2000);
     String value = mySerial.readString();
     if ( value != null ) {
        fill(50);
        text(value,10,10,700,700);
        println(value);
        output.print(year() + "-");
        output.print(month() + "-");
        output.print(day() + "|");
        output.print(hour() + ":");
        output.print(minute() + ":");
        output.print(second() + " + ");
        output.println(value);
        delay(2000);
     } 
     
     currentMillis = millis();
     if (currentMillis - startMillis >= period) {
        output.flush(); // Writes the remaining data to the file
        startMillis = currentMillis;
     }
  } 
}

void keyPressed() {
  
}
