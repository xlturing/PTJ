function newPos = findBypass(sensor,posObst,speedS,area,handleRange)
% Function:
% find the route to bypass the obstacle
pos = get(sensor.handle,'Position');
posRange = get(handleRange,'Position');
% the coordinate in the pos is s3 or o4
% the coordinate of sensor's four point
x_s1 = posRange(1);
y_s1 = posRange(2) + posRange(3);
x_s2 = posRange(1) + posRange(3);
y_s2 = posRange(2) + posRange(4);
x_s3 = posRange(1);
y_s3 = posRange(2);
x_s4 = posRange(1) + posRange(3);
y_s4 = posRange(2);
% the coordinate of obstacle's four point
x_o1 = posObst(1);
y_o1 = posObst(2) + posObst(3);
x_o2 = posObst(1) + posObst(3);
y_o2 = posObst(2) + posObst(4);
x_o3 = posObst(1);
y_o3 = posObst(2);
x_o4 = posObst(1) + posObst(3);
y_o4 = posObst(2);
if(sin(sensor.dir)>=0&&cos(sensor.dir)>0)    % first quartile
    if(ceil(y_s2) <= y_o3)
        % choice a short route
        if(abs(x_o4-x_s2)>abs(x_s2-x_o3))
            [newPos,boundary] = newMove(pos, speedS, pi, area); % choice left
        else
            [newPos,boundary] = newMove(pos, speedS, 0, area);  % choice right
        end
    else
         % choice a short route
        if(abs(y_o1-y_s2)>abs(y_s2-y_o3))
            [newPos,boundary] = newMove(pos, speedS, 3*pi/2, area); % choice down
        else
            [newPos,boundary] = newMove(pos, speedS, pi/2, area);   % choice up
        end
    end
elseif(sin(sensor.dir)>0&&cos(sensor.dir)<=0)    % second quartile
    if(ceil(y_s1) <= y_o4)
        % choice a short route
        if(abs(x_o3-x_s1)<abs(x_s1-x_o4))
            [newPos,boundary] = newMove(pos, speedS, pi, area); % choice left
        else
            [newPos,boundary] = newMove(pos, speedS, 0, area);  % choice right
        end
    else
         % choice a short route
        if(abs(y_o2-y_s1)>abs(y_s1-y_o4))
            [newPos,boundary] = newMove(pos, speedS, 3*pi/2, area); % choice down
        else
            [newPos,boundary] = newMove(pos, speedS, pi/2, area);   % choice up
        end
    end
elseif(sin(sensor.dir)<=0&&cos(sensor.dir)<0)    % third quartile
    if(abs(ceil(y_s3) - y_o2)<=5)
        % choice a short route
        if(abs(x_o1-x_s3)<abs(x_s3-x_o2))
            [newPos,boundary] = newMove(pos, speedS, pi, area); % choice left
        else
            [newPos,boundary] = newMove(pos, speedS, 0, area);  % choice right
        end
    else
         % choice a short route
        if(abs(y_o2-y_s3)>abs(y_s3-y_o4))
            [newPos,boundary] = newMove(pos, speedS, 3*pi/2, area); % choice down
        else
            [newPos,boundary] = newMove(pos, speedS, pi/2, area);   % choice up
        end
    end
elseif(sin(sensor.dir)<0&&cos(sensor.dir)>=0)    % fourth quartile
    if(abs(ceil(x_s4) - x_o1)<=5)
        % choice a short route
        if(abs(x_o1-x_s4)<abs(x_s4-x_o1))
            [newPos,boundary] = newMove(pos, speedS, pi, area); % choice left
        else
            [newPos,boundary] = newMove(pos, speedS, 0, area);  % choice right
        end
    else
         % choice a short route
        if(abs(y_o1-y_s4)>abs(y_s4-y_o3))
            [newPos,boundary] = newMove(pos, speedS, 3*pi/2, area); % choice down
        else
            [newPos,boundary] = newMove(pos, speedS, pi/2, area);   % choice up
        end
    end
end
end