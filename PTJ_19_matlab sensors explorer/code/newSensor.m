function sensor = newSensor(area, dirS)
% Function:
% create a new sensor
% Input:
% -area the boundary of area
% -dirS the direction of sensor
% Output:
% -sensor the struct of the new sensor
sensor.handle = rectangle('Position',[area(1)/2-area(1)/200,area(2)/2-area(2)/200,area(1)/100,area(2)/100],'Curvature',[1,1],'FaceColor','r');
sensor.dir = dirS;  % x direction is 0
sensor.avoid_x = 0;
sensor.avoid_y = 0;
sensor.state = 0;

end