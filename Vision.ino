//
// begin license header
//
// This file is part of Pixy CMUcam5 or "Pixy" for short
//
// All Pixy source code is provided under the terms of the
// GNU General Public License v2 (http://www.gnu.org/licenses/gpl-2.0.html).
// Those wishing to use Pixy source code, software and/or
// technologies under different licensing terms should contact us at
// cmucam@cs.cmu.edu. Such licensing terms are available for
// all portions of the Pixy codebase presented here.
//
// end license header
//
// This sketch is a good place to start if you're just getting started with 
// Pixy and Arduino.  This program simply prints the detected object blocks 
// (including color codes) through the serial console.  It uses the Arduino's 
// ICSP SPI port.  For more information go here:
//
// https://docs.pixycam.com/wiki/doku.php?id=wiki:v2:hooking_up_pixy_to_a_microcontroller_-28like_an_arduino-29
//
  
#include <Pixy2.h>

// This is the main Pixy object 
Pixy2 pixy;

void setup()
{
  Serial.begin(115200);
  Serial.print("Starting...\n");
  
  pixy.init();
}

void loop()
{ 
  int i; 
  // grab blocks!
  pixy.ccc.getBlocks();
  
  if (pixy.ccc.numBlocks == 2) {
    int tapePixelDistance;
    if (pixy.ccc.blocks[1].m_x > pixy.ccc.blocks[0].m_x) {
      tapePixelDistance = (pixy.ccc.blocks[1].m_x - pixy.ccc.blocks[1].m_width / 2) - (pixy.ccc.blocks[0].m_x + pixy.ccc.blocks[0].m_width / 2);
    } else if (pixy.ccc.blocks[0].m_x > pixy.ccc.blocks[1].m_x) {
      tapePixelDistance = (pixy.ccc.blocks[0].m_x - pixy.ccc.blocks[0].m_width / 2) - (pixy.ccc.blocks[1].m_x + pixy.ccc.blocks[1].m_width / 2);
    }
    int rangeConstInch = 2;
    const int tapeConstDistance = 8;
    float pixelsPerInch = tapePixelDistance/tapeConstDistance;
    int rangePixels = pixelsPerInch*rangeConstInch;
    
    int center = (pixy.ccc.blocks[0].m_x + pixy.ccc.blocks[1].m_x) / 2;
    if (center < 158 - (rangePixels / 2)) {
      Serial.print("Left\n");
    } else if (center > (158 + rangePixels / 2)) {
      Serial.print("Right\n");
    } else {
      Serial.print("Center\n");
    }
  }
 /* 
  if (pixy.ccc.numBlocks)
  {
    Serial.print("Detected ");
    Serial.println(pixy.ccc.numBlocks);
    for (i=0; i<pixy.ccc.numBlocks; i++)
    {
      Serial.print("  block ");
      Serial.print(i);
      Serial.print(": ");
      pixy.ccc.blocks[i].print();
      
    }
  }
  */  
}
