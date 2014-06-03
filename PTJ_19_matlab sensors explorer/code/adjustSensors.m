function [sensors, adEnd] = adjustSensors(sensors, area, rangeS, speedS, handleObsts)
% Function:
% adjust sensors and make sensors cover the area as much as possible
% Input:
% -remainTime the time limit remain
% Output:
% -sensors adjusted sensors
% -end adjust end? <0,1> <no,yes>
adEnd = 0;
num = size(sensors,2);
% explore area
for i = 1:num
    handleRange(i) = getSensorRange(sensors(i),rangeS);
end
% adjust all sensors
flag = 0; % record modify
for i = 1:num
    % adjust by clockwise
    pre = i-1;  % the previous node
    if(pre == 0)
        pre = num;
    end
    if(isCircleIntersect(get(handleRange(pre),'Position'),get(handleRange(i),'Position'))==1)
        continue;
    else                                                        % move to far distance node        
        pos = get(sensors(i).handle,'Position');
        pos_pre = get(sensors(pre).handle,'Position');
        sensors(i).state = 0;                                   % set this sensor state to move state
        dir = sensorsDis(pos,pos_pre);
        [newPos,boundary] = newMove(pos, speedS, dir, area);
        % obstacles explore
        [flag, indexObst] = isMove(sensors(i).handle, handleObsts);
        % can't move, then find bypass
        if(flag == 0)
            %[newPos,boundary] = newMove(pos, speedS, 3*pi/2, area);
            newPos(2) = newPos(2) - 10;
            set(sensors(i).handle,'Position',newPos);
            delete(handleRange(i));
            handleRange(i) = getSensorRange(sensors(i),rangeS);
            pause(0.1)
            newPos(1) = area(1) - 3;
            sensors(i).dir = 0;
        else
            % change pos direction
            if(newPos(1)<=3)
                sensors(i).dir = pi;
            elseif(newPos(2)<=3)
                sensors(i).dir = 3*pi/2;
            elseif(newPos(2)>=area(2)-6)
                sensors(i).dir = pi/2;
            elseif(newPos(1)>=area(1)-6)
                sensors(i).dir = 0;
            end
        end
        set(sensors(i).handle,'Position',newPos);
        delete(handleRange(i));
        handleRange(i) = getSensorRange(sensors(i),rangeS);
        flag = 1;
    end 
end
if(flag == 0)
    adEnd = 1;
else
    % explore end
    pause(0.1);
    for i = 1:num
        delete(handleRange(i));
    end
end

end