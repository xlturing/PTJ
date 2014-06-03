% Author: Wanping Bai(u4988984) & Zhongbo Yao(u5201381).
% Date created: May 23th.
% Functionality: returns the change of coordinate of the thief while he is
% moving in strategic route.

function [dx, dy, warn_T] = t_to_start(size ,crd_T, crd_C, t, r_anticl, r_thief, x_trs, y_trs)
warn_T = 0;
x_st = size/2;
y_st = -size/2 + r_anticl;
crd_T_prev = cell2mat(crd_T(t-1));
x_T = crd_T_prev(1);
y_T = crd_T_prev(2);

crd_C_prev = cell2mat(crd_C(:, t-1)); % All cops coords at previous step
x_C = crd_C_prev(:,1); % All cops x coord
y_C = crd_C_prev(:,2); % All cops y coord
C_dist = sqrt((x_C-x_T).^2 + (y_C-y_T).^2); % All cop-to-thief distances
C_found = find(C_dist == min(C_dist)); % Which cop is nearest

trs_dist = sqrt((x_trs - x_T)^2 + (y_trs - y_T)^2);

if trs_dist <= r_anticl % Finds trs & whatever cops
    [dx, dy, warn_T] = find_treasure(x_T, y_T, x_trs, y_trs);
elseif  min(C_dist) <= r_thief % Finds cop
    [dx, dy, warn_T] = find_cop(size, x_T, y_T, C_found, x_C, y_C);
else
    if x_T ~= x_st
        dx = 1;
        dy = 0;
    elseif y_T ~= y_st
        if trs_dist <= r_anticl
            [dx, dy, warn_T] = find_treasure(x_T, y_T, x_trs, y_trs);
        else
            dx = 0;
            dy = -1;
        end
    else % Thief is at the strategy starting point
        warn_T = 2;
        dx = -1;
        dy = 0;
    end
end
end