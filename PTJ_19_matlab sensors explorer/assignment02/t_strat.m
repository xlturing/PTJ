% Author: Wanping Bai(u4988984) & Zhongbo Yao(u5201381).
% Date created: May 23th.
% Functionality: returns the change of coordinate of the thief while he is
% moving to the start point of strategy route.

function [dx, dy, warn_T] = t_strat(size ,crd_T, crd_C, t, r_anticl, r_thief, x_trs, y_trs)
warn_T = 2; % Thief is in "strategy mode"
% Thief's previous coords:
crd_T_prev = cell2mat(crd_T(t-1));
x_T = crd_T_prev(1);
y_T = crd_T_prev(2);
south_dist = y_T + size/2;

crd_C_prev = cell2mat(crd_C(:, t-1)); % All cops coords at previous step
x_C = crd_C_prev(:,1); % All cops x coord
y_C = crd_C_prev(:,2); % All cops y coord
C_dist = sqrt((x_C-x_T).^2 + (y_C-y_T).^2);
C_found = find(C_dist == min(C_dist));

if sqrt((x_trs - x_T)^2 + (y_trs - y_T)^2) <= r_anticl % Finds trs & whatever cops
    [dx, dy, warn_T] = find_treasure(x_T, y_T, x_trs, y_trs);
    if warn_T ~= 1 && warn_T ~= 3 && warn_T ~= 4
        warn_T = 2;
    end
elseif  min(C_dist) <= r_thief % Finds cop
    [dx, dy, warn_T] = find_cop(size, x_T, y_T, C_found, x_C, y_C);
    if warn_T ~= 1 && warn_T ~= 3
        warn_T = 2;
    end
else
    if mod(south_dist, 4*r_anticl) == r_anticl
        if x_T == -size/2
            dx = 0;
            dy = 1;
            warn_T = 2;
        else
            dx = -1;
            dy = 0;
            warn_T = 2;
        end
    elseif mod(south_dist, 4*r_anticl) == 3*r_anticl
        if x_T == size/2
            dx = 0;
            dy = 1;
            warn_T = 2;
        else
            dx = 1;
            dy = 0;
            warn_T = 2;
        end
    else
        dx = 0;
        dy = 1;
        warn_T = 2;
    end
end
end