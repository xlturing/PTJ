function [sensors,allSensorsStop] = moveSensor(sensors, rangeS, numS, speedS, area, handleObsts)
% Function:
% move the sensor by its direction, and it will avoid colliding with obstacles and other 
% obstacles. The sensors must move in the area.
% Input:
% -sensors the structs of all sensors(include: handle, direction<N,S,W,E>)
% -area the boundary of area
% -handleObsts the handles of all obstacles
% -numS the number of sensors in the area now
% Output:
% -handleSensors the new handles of all sensors
% -allSensorsState if all sensors stop 1, or 0
allSensorsStop = 0;
numState = 0;
% explore area
for i = 1:numS
    handleRange(i) = getSensorRange(sensors(i),rangeS);
end
% move sensors
for i = 1:numS
    % the sensor arrive boudary
    if(sensors(i).state == 1)
        numState = numState + 1;
        continue
    end
    
    % move sensor
    pos = get(sensors(i).handle,'Position');
    [newPos,boundary] = newMove(pos,speedS,sensors(i).dir,area);
    % this sensor arrive boundary
    if(boundary==1)
        set(sensors(i).handle,'Position',newPos);
        sensors(i).state = 1;
        delete(handleRange(i));
        handleRange(i) = getSensorRange(sensors(i),rangeS);
        continue;
    end

    % obstacles explore
    [flag, indexObst] = isMove(handleRange(i), handleObsts);
    % can't move, then find bypass
    if(flag == 0)
        posObst = get(handleObsts(indexObst),'Position');
        % find a short route to bypass the obstacle
        newPos = findBypass(sensors(i),posObst,speedS,area,handleRange(i));
    end
    set(sensors(i).handle,'Position',newPos);
    delete(handleRange(i));
    handleRange(i) = getSensorRange(sensors(i),rangeS);
end
% explore end
pause(0.1);
for i = 1:numS
    delete(handleRange(i));
end
if(numState == numS)
    allSensorsStop = 1;
end

end