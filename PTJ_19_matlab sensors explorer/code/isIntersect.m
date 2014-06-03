function flag = isIntersect(pos1, pos2)
% Function:
% decide whether the two objection intersect or not.
% using the centre of object1 and object2
% Input:
% -pos1 the positon of object1 <x,y,w,h>
% -pos2 the positon of object2 <x,y,w,h>
% Output:
% -flag <0,1> <no,yes>
% centre of object a
x_c1 = pos1(1) + pos1(3)/2;
y_c1 = pos1(2) + pos1(4)/2;
% centre of object b
x_c2 = pos2(1) + pos2(3)/2;
y_c2 = pos2(2) + pos2(4)/2;
% the two object intersected
if(abs(x_c1-x_c2)<(pos1(3)/2+pos2(3)/2)&&abs(y_c1-y_c2)<(pos1(4)/2+pos2(4)/2))
    flag = 1;
else
    flag = 0;
end
end