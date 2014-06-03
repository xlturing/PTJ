function handleRange = getSensorRange(sensor, range)
% Function:
% create a object which represent the range of a sensor
% Input:
% -sensor the sensor object
% -range the range of sensors
% Output:
% -handleRange the handle of new created object
position = get(sensor.handle,'Position'); % get the sensor position info by the handle
range_x = position(1) - range + position(3)/2;
range_y = position(2) - range + position(3)/2;
handleRange = rectangle('Position',[range_x,range_y,range*2,range*2],'Curvature',[1,1],'LineWidth',0.5,'LineStyle','--');
end