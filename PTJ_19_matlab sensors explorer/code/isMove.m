function [flag, indexObst] = isMove(handleRange, handleObsts)
% Function:
% decide wether the sensor can move in its direction
% Input:
% -sensor the sensor
% -handleWalls the boundary of area
% -handleObsts the obstacles in area
% Output:
% -flag <0,1> <no,yes>
% -indexObst if explore a obstacles, return its index
flag = 1;
indexObst = -1;
if(handleObsts(1) == -1)
    return;
end
pos = get(handleRange,'Position');
for i = 1:size(handleObsts,2)
    posObst = get(handleObsts(i),'Position');
    if(isIntersect(pos,posObst) == 1)
        flag = 0;
        indexObst = i;
        break;
    end
end
end