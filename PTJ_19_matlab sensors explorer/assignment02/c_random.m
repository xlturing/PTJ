% Author: Wanping Bai(u4988984) & Zhongbo Yao(u5201381).
% Date created: May 10th.
% Functionality: returns the change of coordinate of the cop while he is
% moving randomly.

function [dx, dy] = c_random(dir, x_C, y_C, size)
if dir == 0 || dir == 5
    dx = 0;
    dy = 0;
elseif dir == 1 % North
    if y_C == size/2
        dx = 0;
        dy = -1;
    else
        dx = 0;
        dy = 1;
    end
elseif dir == 2 % South
    if y_C == -size/2
        dx = 0;
        dy = 1;
    else
        dx = 0;
        dy = -1;
    end
elseif dir == 3 % West
    if x_C == -size/2
        dx = 1;
        dy = 0;
    else
        dx = -1;
        dy = 0;
    end
else % East
    if x_C == size/2
        dx = -1;
        dy = 0;
    else
        dx = 1;
        dy = 0;
    end
end
end