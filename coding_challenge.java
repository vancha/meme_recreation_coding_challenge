
/* this is the ball class that can only move in one dimension.
The dimension it can move in is the perimiter of a circle with a certain radius.
*/
class Ball{
  //the x and y position of the circle
  float x,y;
  //the radius of the circle it travels on
  int radius;
  //the angle that points to the point on the perimiter of the circle (from the circles center) that indicates the position of this ball
  float current_angle;
  //if the ball is moving clockwise or counter clockwise on the circles perimeter
  boolean moving_clockwise;
  //the color of the circle
  int[] circle_color;
  
  //constructor takes the balls color, and the radius of the circle it travels on
  public Ball(int radius,int[] coloru) {
    //sets the balls color
    this.circle_color = coloru;
    //sets the initial angle, same for all balls at creation time
    this.current_angle = 225;
    //set all balls to move clockwise at creation time
    this.moving_clockwise = true;
    //sets the radius of the circle
    this.radius = radius;
    //converts the angle to radians, because otherwise i dont know how to calculate a point on a circles circumference
    float degToRad = this.current_angle * (PI / 180);
    //calculates the x and y position of ball, located at some point on circle
    this.x = 0 + radius * cos(degToRad);
    this.y = 0 + radius * sin(degToRad);
  }
  
  //this function displays the ball
  public void show(){
    //this make sure the circle shows itself in its own color
    fill(this.circle_color[0],this.circle_color[1],this.circle_color[2]);
    //degree to radian thingy again
    float degToRad = this.current_angle * (PI / 180);
    //position calculation again, required to show the circle
    this.x = 0 + this.radius * cos(degToRad);
    this.y = 0 + this.radius * sin(degToRad);
    //finally drawing a circle on the screen
    circle(this.x,this.y,20);
  }
  
  //update the balls position on its circle
  public void update_position_on_circle(){
    if(this.moving_clockwise){
      //update the balls position by updating its angle, make the difference dependent on the radius of it s parent circle (this gives the balls their fancy offset so that they bounce differently)
      this.current_angle += 1+(this.radius * 0.001);//0.001 is chosen at random, no idea why this value seems to work, but it looks alright on this computer
      //if the ball moves further on its parent circle than angle 315, make it bounce, and change its direction
     if(this.current_angle >= 315){
       this.current_angle = 314;
       this.moving_clockwise = false;
     }
    } else {
      //same, if the ball moves past 225 degrees on circle, make it bounce, reverse its heading/direction 
      this.current_angle -= 1+(this.radius * 0.001);
      if(this.current_angle <= 225){
       this.current_angle = 226;
       this.moving_clockwise = true;
     }
    }
  }
}

//the array that holds all the balls
Ball[] balls_array;
int nr_of_balls = 16;

void setup() {
  //screen size
  size(800, 600);
  //theres exactly 16 balls in this array
  balls_array = new Ball[nr_of_balls];
  //all the colors for every individual ball (must match number of balls)
  int[][] colors =  { {255,0,0}, {206,11,0}, {255,17,0}, {255,78,4}, {255,126,0}, {254,181,0}, {255,254,0}, {191,237,42}, {123,228,125}, {58,229,202}, {0,232,255}, {0,175,246}, {0,122,254}, {0,67,255}, {18,9,203}, {18,9,203} };
  //for every ball
  for(int i = 0; i < nr_of_balls;i++){
    //create a parent circle with a certain radius, 
    int radius = 80 + (i * 15);
    //add a new ball with that circle radius and its color to the balls array
    balls_array[i] = new Ball(radius,colors[i]);
  }
}

void draw() {
  //set color to white
  fill(255);
  //color the background
  rect(0, 0, width, height );
  //move the camera or viewport or whatever its called to make the math easier for things
  translate(640/2, (360/4)*3.9);
  //draw those lines that are visible in the example, the ones that make up that 90 degree corner looking thing
  line(0,0,250,-250);
  line(0,0,-250,-250);
  
  //for all the balls
  for(Ball b : balls_array){
    //update their position
    b.update_position_on_circle();
  }
  //for all the balls, except the last one
  for(int i = 0; i < nr_of_balls-1; i++){
    //connect a ball to another ball with a line, just like in the example
     line(balls_array[i].x, balls_array[i].y, balls_array[i+1].x, balls_array[i+1].y); 
  }
  //for all the balls
  for(Ball b : balls_array){
    //show the ball
    b.show();
  }
  //have a 
  stroke(0);
}
