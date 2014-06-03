% Author: Wanping Bai(u4988984) & Zhongbo Yao(u5201381).
% Date created: May 8th.
% Functionality: returns the change of coordinate of the cop.

function [dx, dy, warn, who_knows] = c_move(N, size, t, r_cop, crd_C, crd_T, dir, who_knows)
warn = 0;

crd_T_prev = cell2mat(crd_T(t-1)); % Thief's coordinate
x_T = crd_T_prev(1);
y_T = crd_T_prev(2);
crd_C_prev = cell2mat(crd_C(N, t-1)); % His own coordinate
x_C = crd_C_prev(1);
y_C = crd_C_prev(2);
if who_knows ~= 0 % If there exists another cop who locates the thief
    crd_C_knows = cell2mat(crd_C(who_knows, t-1));
    x_C_knows = crd_C_knows(1);
    y_C_knows = crd_C_knows(2);
    C_dist = sqrt((x_C_knows - x_C)^2 + (y_C_knows - y_C)^2);
end

if sqrt((x_T-x_C)^2 + (y_T-y_C)^2) <= r_cop(N) % He locates the thief
    who_knows = N;
    [dx, dy, warn] = find_thief(x_T, y_T, x_C, y_C);
elseif who_knows ~= 0 % He doesnt locate thief but another cop knows
    if C_dist <= r_cop(N) && C_dist <= r_cop(who_knows) % Two cops in each other's range
        [dx, dy, warn] = find_thief(x_T, y_T, x_C, y_C);
    else
        [dx, dy] = c_random(dir, x_C, y_C, size);
    end
else
    [dx, dy] = c_random(dir, x_C, y_C, size);
end
end
