#include <Pixy2.h>

// This is the main Pixy object 
Pixy2 pixy;

String command;
int distanceFromCenter;

void setup()
{
  Serial.begin(115200);  
  Serial.setTimeout(50);
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
    int distanceFromCenter = center - 158;
    if (center < 158 - (rangePixels / 2)) {
      command = "L";
      //Serial.println("L");
    } else if (center > 158 + (rangePixels / 2)) {
      command = "R";
      //Serial.println("R");
    } else {
      command = "C";
      //Serial.println("C");
    }
  }
  else {
    command = "X";
    //Serial.println("X");
  }

  if (Serial.readString().equals("I"))
  {
    Serial.print(command);
  }
}
