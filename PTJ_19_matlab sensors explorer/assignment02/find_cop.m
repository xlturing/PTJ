% Author: Wanping Bai(u4988984) & Zhongbo Yao(u5201381).
% Date created: May 8th.
% Functionality: returns the change of coordinate of the thief while he is
% escaping from the cop(s).

function [dx, dy, warn] = find_cop(size, x_T, y_T, C_found, x_C, y_C)
warn = 0; % Initially everything's ok
x_C_near = x_C(C_found(1)); % The x-coord for the nearest cop
y_C_near = y_C(C_found(1)); % The y-coord for the nearest cop
if x_C_near > x_T
    if x_T == -size/2 && y_T == size/2 % North-west corner
        dx = 0;
        dy = -1;
    elseif x_T == -size/2 && y_T == -size/2 % South-west corner
        dx = 0;
        dy = 1;
    elseif x_T == -size/2 && abs(y_T) ~= size/2 % West wall
        dx = 0;
        dy = -1;
    else % No geographical restriction
        dx = -1;
        dy = 0;
    end
elseif x_C_near < x_T
    if x_T == size/2 && y_T == size/2 % North-east corner
        dx = 0;
        dy = -1;
    elseif x_T == size/2 && y_T == -size/2 % South-east corner
        dx = 0;
        dy = 1;
    elseif x_T == size/2 && abs(y_T) ~= size/2 % East wall
        dx = 0;
        dy = -1;
    else % No geographical restriction
        dx = 1;
        dy = 0;
    end
elseif y_C_near > y_T
    if y_T == -size/2 && x_T == -size/2
        dx = 1;
        dy = 0;
    elseif y_T == -size/2 && x_T == size/2
        dx = -1;
        dy = 0;
    elseif y_T == -size/2 && abs(x_T) ~= size/2
        dx = 1;
        dy = 0;
    else % No geographical restriction
        dx = 0;
        dy = -1;
    end
elseif y_C_near < y_T
    if y_T == size/2 && x_T == size/2
        dx = -1;
        dy = 0;
    elseif y_T == size/2 && x_T == size/2
        dx = -1;
        dy = 0;
    elseif y_T == size/2 && abs(x_T) ~= size/2
        dx = -1;
        dy = 0;
    else % No geographical restriction
        if x_T >= 0
            dx = -1;
            dy = 0;
        else
            dx = 1;
            dy = 0;
        end
    end
else % x_C == x_T, y_C == y_T
    warn = 1; % Caught!
    dx = 0;
    dy = 0;
end