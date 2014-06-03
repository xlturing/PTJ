function [newPos,boundary] = newMove(pos, speedS, dir, area)
% Function:
% calculate the new x and y of sensor
% Input:
% -pos the current position of sensor
% -dir the current direciton of sensor
% Output:
% -newPos new position
% -boundary arrive boundary? <0,1> <no,yes>
newPos = pos;
newPos(1) = pos(1) + speedS * cos(dir);
newPos(2) = pos(2) + speedS * sin(dir);
boundary = 0;
% boundary explore
if(ceil(newPos(1))>=area(1)||floor(newPos(1))<=0||ceil(newPos(2))>=area(2)||floor(newPos(2))<=0)
    if(ceil(newPos(1))>=area(1))
        newPos(1) = area(1)-3;
    elseif(floor(newPos(1))<=0)
        newPos(1) = 0;
    elseif(ceil(newPos(2))>=area(2))
        newPos(2) = area(2)-3;
    elseif(floor(newPos(2))<=0)
        newPos(2) = 0;
    end
    boundary = 1;
end
end