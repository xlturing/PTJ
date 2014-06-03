% Author: Wanping Bai(u4988984) & Zhongbo Yao(u5201381).
% Date created: May 10th.
% Functionality: returns the change of coordinate of the cop while he is
% chasing the thief.

function [dx, dy, warn] = find_thief(x_T, y_T, x_C, y_C)
warn = 0;
if x_T > x_C
    dx = 1;
    dy = 0;
elseif y_T > y_C
    dx = 0;
    dy = 1;
elseif x_T < x_C
    dx = -1;
    dy = 0;
elseif y_T < y_C
    dx = 0;
    dy = -1;
else % He catches the thief
    warn = 1; % Indicates game stop
    dx = 0;
    dy = 0;
end