% Author: Wanping Bai(u4988984) & Zhongbo Yao(u5201381).
% Date created: May 10th.
% Functionality: returns the change of coordinate of the thief while he is
% moving to get the treasure.

function [dx, dy, warn] = find_treasure(x_T, y_T, x_trs, y_trs)
warn = 0;
if x_trs == x_T && y_trs == y_T
    dx = 0;
    dy = 0;
elseif x_trs > x_T
    dx = 1;
    dy = 0;
elseif y_trs > y_T
    dx = 0;
    dy = 1;
elseif x_trs < x_T
    dx = -1;
    dy = 0;
else
    dx = 0;
    dy = -1;
end
end