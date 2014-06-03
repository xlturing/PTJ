function dir = sensorsDis(pos1,pos2)
% Function:
% caculate pos1 move direction which is to pos2
x1 = pos1(1);
y1 = pos1(2);
x2 = pos2(1);
y2 = pos2(2);
v_pos = [x2-x1,y2-y1];   % vector from pos1 to pos2
v_base = [1,0];          % based vector x axis
% caculate direction base pos1
% dot product
d = dot(v_pos, v_base);
% angle between v_pos and v_base
dir = acos(d/(norm(v_pos)*norm(v_base)));
% third and forth quadrant process
if(v_pos(2) < 0)
    dir = 2*pi-dir;
end